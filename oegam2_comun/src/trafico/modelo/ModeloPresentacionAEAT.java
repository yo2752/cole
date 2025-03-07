package trafico.modelo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.displaytag.properties.SortOrderEnum;
import org.gestoresmadrid.core.vehiculo.model.vo.MarcaDgtVO;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import comunicaciones.http.HttpsProtocol;
import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import escrituras.beans.ResultBean;
import escrituras.modelo.ModeloBase;
import general.utiles.BasicText;
import general.utiles.UtilHttpClient;
import oegam.constantes.ConstantesSession;
import trafico.beans.TramiteTraficoMatriculacionBean;
import trafico.beans.matriculacion.modelo576.Respuesta;
import trafico.beans.matriculacion.modelo576.RespuestaJsonRecibida;
import trafico.utiles.UtilesConversiones;
import trafico.utiles.constantes.Constantes;
import trafico.utiles.constantes.ConstantesAEAT;
import trafico.utiles.enumerados.Combustible;
import trafico.utiles.enumerados.Fabricacion;
import trafico.utiles.enumerados.LugarAdquisicion;
import trafico.utiles.enumerados.PaisFabricacion;
import utilidades.estructuras.Fecha;
import utilidades.estructuras.ListaRegistros;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class ModeloPresentacionAEAT extends ModeloBase {

	private static ILoggerOegam log = LoggerOegam.getLogger(ModeloPresentacionAEAT.class);
	private static final String KEY_STORE_CLAVE_PRIVADA_URL="aeat.keystore.url";
	private static final String KEY_STORE_CLAVE_PRIVADA_PASSWORD = "aeat.keystore.password";

	private ModeloTrafico modeloTrafico;

	@Autowired
	private GestorDocumentos gestorDocumentos;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	private UtilHttpClient utilHttpClient;

	@Autowired
	Utiles utiles;

	@Autowired
	private UtilesConversiones utilesConversiones;

	@Override
	public ListaRegistros listarTabla(Integer pagina,
			Integer numeroElementosPagina, SortOrderEnum orden,
			String columnaOrden) {
		return null;
	}

	/**
	 * Realiza la invocacion a la AEAT a traves de un formulario usando el method POST.
	 * @param tramiteBean
	 * @param tramite
	 * @param modelo
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ResultBean invocacionPostModeloAEAT(TramiteTraficoMatriculacionBean tramite, String modelo,	
			LinkedHashMap<String, String> request) throws Exception{

		log.info("Se va a crear el protocolo de comunicacion");
		log.info("El modelo a presentar: "+modelo);
		ResultBean result = new ResultBean();
		try{
			String host = gestorPropiedades.valorPropertie("trafico.576.host");

			if(host == null || host.equals("")){
				log.error("Error :" + " No se ha configurado la propiedad que indica el host de la AEAT para la presentacion del 576");
				result = new ResultBean();
				result.setMensaje("Error :" + " No se ha configurado la propiedad que indica el host de la AEAT para la presentacion del 576");
				result.setError(Boolean.TRUE);
			}

			result = utilHttpClient.executeMethod(host,gestorPropiedades.valorPropertie(KEY_STORE_CLAVE_PRIVADA_URL), gestorPropiedades.valorPropertie(KEY_STORE_CLAVE_PRIVADA_PASSWORD), request);

			String numExpediente = tramite.getTramiteTraficoBean().getNumExpediente().toString();

			guardarRespuesta576((String) result.getAttachment("respuestaRecibidaSintratrar") == null ? result.getMensaje() : (String) result.getAttachment("respuestaRecibidaSintratrar"), numExpediente);

			if(!result.getError().booleanValue()){
				// Todavia puede contener errores la respuesta. Segun documentacion, en caso de error, la respuesta JSON tendrá un campo 'errores'
				Respuesta respuesta = ((RespuestaJsonRecibida) result.getAttachment("respuestaJsonRecibida")).getRespuesta();
				if (!CollectionUtils.isEmpty(respuesta.getErrores())) {
					result.setError(true);
					result.addAttachment(ConstantesAEAT.DELIMITADOR_ERRORES, respuesta.getErrores());
				} else {
					result.setError(Boolean.FALSE);
					byte[] bytesPdf = descargarPdfADisco(respuesta.getCorrecta().getUrlpPdf(), numExpediente, tramite.getTramiteTraficoBean().getFechaCreacion());
					result.addAttachment(ConstantesSession.RESPUESTA576, bytesPdf);
					if (bytesPdf == null){
						result.setError(Boolean.TRUE);
						result.setMensaje("Error durante la descarga del pdf");
					}
				}
			}else{
				result.setError(Boolean.TRUE);
				result.setMensaje(null);
				result.addAttachment(ConstantesSession.RESPUESTA576, result.getMensaje());
			}
			result.addAttachment(ConstantesSession.TRAMITE_EN_CURSO, tramite);

		}catch(Throwable e){
			log.error("Error :" + e);
			result = new ResultBean();
			result.setMensaje(e.getMessage());
			result.setError(Boolean.TRUE);
		}

		return result;
	}

	private void guardarRespuesta576(String mensaje, String numExpediente) throws Exception, OegamExcepcion {
		FicheroBean ficheroBean = new FicheroBean();
		ficheroBean.setFicheroByte(mensaje.getBytes());
		ficheroBean.setTipoDocumento(ConstantesGestorFicheros.TIPO576RESPTXT);
		ficheroBean.setExtension(ConstantesGestorFicheros.EXTENSION_TXT);
		ficheroBean.setFecha(Utilidades.transformExpedienteFecha(numExpediente));
		ficheroBean.setSobreescribir(true);
		ficheroBean.setNombreDocumento(numExpediente + "_576_RESP");

		gestorDocumentos.guardarByte(ficheroBean);
	}

	private void guardarTxtEnvio576(String mensaje, String numExpediente) throws Exception, OegamExcepcion {
		FicheroBean ficheroBean = new FicheroBean();
		ficheroBean.setFicheroByte(mensaje.getBytes());
		ficheroBean.setTipoDocumento(ConstantesGestorFicheros.TIPO576ENVTXT);
		ficheroBean.setExtension(ConstantesGestorFicheros.EXTENSION_TXT);
		ficheroBean.setFecha(Utilidades.transformExpedienteFecha(numExpediente));
		ficheroBean.setSobreescribir(true);
		ficheroBean.setNombreDocumento(numExpediente + "_576_ENV");

		gestorDocumentos.guardarByte(ficheroBean);
	}

	private byte[] descargarPdfADisco(String urlPDF, String numExpediente, Fecha fechaCreacion) throws Exception, OegamExcepcion{
		byte[] bytesPdf = null;
		int codigoRespuesta;
		HttpsProtocol protocolo = new HttpsProtocol();
		protocolo.establecerHttps();
		GetMethod getMethod = new GetMethod(urlPDF);
		HttpClient httpClient = new HttpClient();
		try {
			codigoRespuesta = httpClient.executeMethod(getMethod);
		} catch (Exception e) {
			getMethod = new GetMethod(urlPDF.replace("www1", "www2"));
			codigoRespuesta = httpClient.executeMethod(getMethod);
		}

		// Comprueba el codigo de respuesta recibido:
		if (codigoRespuesta != HttpStatus.SC_OK){
			return null;
		}
		bytesPdf = getMethod.getResponseBody();
		getMethod.releaseConnection();
		//
		String nombreFichero = numExpediente + "_576";

		FicheroBean fichero = new FicheroBean();
		fichero.setTipoDocumento(ConstantesGestorFicheros.TIPO576);
		fichero.setFecha(Utilidades.transformExpedienteFecha(numExpediente));
		fichero.setNombreDocumento(nombreFichero);
		fichero.setExtension(ConstantesGestorFicheros.EXTENSION_PDF);
		fichero.setSobreescribir(false);
		fichero.setFicheroByte(bytesPdf);

		gestorDocumentos.guardarByte(fichero);

		return bytesPdf;
	}

	public byte[] descargarPdf(String href) throws Exception, OegamExcepcion {
		byte[] bytesPdf = null;
		HttpsProtocol protocolo = new HttpsProtocol();
		protocolo.establecerHttps();
		GetMethod getMethod = null;
		try {
			getMethod = new GetMethod(href);
			HttpClient httpClient = new HttpClient();
			int codigoRespuesta = httpClient.executeMethod(getMethod);
			// Comprueba el codigo de respuesta recibido:
			if(codigoRespuesta != HttpStatus.SC_OK){
				return null;
			}
			bytesPdf = getMethod.getResponseBody();
		} finally {
			if (getMethod != null) {
				getMethod.releaseConnection();
			}
		}
		return bytesPdf;
	}

	public Map<String,Object> tramitar576(TramiteTraficoMatriculacionBean tramiteMatriculacionBean, String alias) throws Exception{

		// Rellena con ceros los campos requeridos para la presentacion que valen null:
		tramiteMatriculacionBean = rellenarRequeridosPresentacion(tramiteMatriculacionBean);

		HashMap<String,Object> mapaReturn = new HashMap<String,Object>();
		ArrayList<String> datosRequeridos = new ArrayList<String>();
		// Lista donde se añaden los parametros de la peticion.
		LinkedHashMap<String, String> request = new LinkedHashMap<String, String>();

		// En mayusculas los nombres de las variables del formulario segun documentacion 'Modelo 576'

		// CAMPO MODELO
		request.put(ConstantesAEAT.MODELO, ConstantesAEAT.ID_MODELO);
		// CAMPO EJERCICIO
		request.put(ConstantesAEAT.EJERCICIO, String.valueOf(tramiteMatriculacionBean.getEjercicioDevengo576()));
		// CAMPO PERIODO
		request.put(ConstantesAEAT.PERIODO_, ConstantesAEAT.PERIODO);
		// CAMPO NRC
		if(tramiteMatriculacionBean.getCuotaIngresar576() == null || tramiteMatriculacionBean.getCuotaIngresar576().intValue() <= 0){
			request.put(ConstantesAEAT.NRC, ConstantesAEAT.VALOR_NRC_VACIO);
		} else {
			if (tramiteMatriculacionBean.getNrc576() == null || tramiteMatriculacionBean.getNrc576().isEmpty()) {
				datosRequeridos.add(ConstantesAEAT.NRC);
			} else {
				request.put(ConstantesAEAT.NRC, tramiteMatriculacionBean.getNrc576());
			}
		}
		// CAMPO IDI
		request.put(ConstantesAEAT.IDI, ConstantesAEAT.IDI_VALOR);

		if(tramiteMatriculacionBean.getTitularBean() == null ||
				tramiteMatriculacionBean.getTitularBean().getPersona() == null ||
				tramiteMatriculacionBean.getTitularBean().getPersona().getNif() == null){
			datosRequeridos.add("Titular");
		}

		// VARIABLE ING
		if(tramiteMatriculacionBean.getCuotaIngresar576() != null && tramiteMatriculacionBean.getCuotaIngresar576().intValue() > 0){
			// Es ingreso, debe llevar el importe ingresado:
			if(tramiteMatriculacionBean.getImporte576() == null || tramiteMatriculacionBean.getImporte576().toString().isEmpty()){
				datosRequeridos.add("Cuota a ingresar");
			}
		}

		// CAMPO F01
		HashMap<String,Object> mapa = construirVariableF01(tramiteMatriculacionBean);
		@SuppressWarnings("unchecked")
		ArrayList<String> datosRequeridosF01 = (ArrayList<String>)mapa.get("datosRequeridosF01");
		@SuppressWarnings("unchecked")
		ArrayList<String> erroresFormato = (ArrayList<String>)mapa.get("erroresFormato");
		// Comprueba si el metodo de construccion de la variable f01 devuelve error por faltar datos requeridos:
		if(datosRequeridosF01 != null && datosRequeridosF01.size() > 0){
			for(String datoRequerido : datosRequeridosF01){
				datosRequeridos.add(datoRequerido);
			}
		}
		boolean errores = false;
		// Comprueba si faltan datos requeridos:
		if(datosRequeridos.size() > 0){
			mapaReturn.put("datosRequeridos", datosRequeridos);
			errores = true;
		}
		// Comprueba si hay errores de formato:
		if(erroresFormato != null && erroresFormato.size() > 0){
			mapaReturn.put("erroresFormato", erroresFormato);
			errores = true;
		}
		if(errores){
			return mapaReturn;
		}
		String f01 = (String)mapa.get(ConstantesAEAT.F01);
		if(f01.length() != ConstantesAEAT.TAM_F01_COMPLETA_NUEVA){
			log.error("ERROR PRESENTACION 576. La variable f01 tiene una longitud incorrecta: " + f01.length());
			log.error("Numero de expediente: " + tramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente());
			log.error(f01);
			throw new Exception("ERROR PRESENTACION 576. La variable f01 tiene una longitud incorrecta: " + f01.length());
		}
		request.put(ConstantesAEAT.F01, (f01));

		// CAMPO FIR
		request.put(ConstantesAEAT.FIR, (ConstantesAEAT.FIR_VALOR));
		// CAMPO FIRNIF
		request.put(ConstantesAEAT.FIRNIF, (ConstantesAEAT.FIRNIF_VALOR));
		// CAMPO FIRNOMBRE
		request.put(ConstantesAEAT.FIRNOMBRE, (ConstantesAEAT.FIRNOMBRE_VALOR));

		ResultBean resultadoInvocacion = invocacionPostModeloAEAT(tramiteMatriculacionBean,
				Constantes.VALOR_TRAMITE_AEAT576, request);

		mapaReturn.put("resultadoPresentacion", resultadoInvocacion);

		try {
			guardarTxtEnvio576(resultadoInvocacion.getAttachment("jsonEnviado").toString(), tramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente().toString());
		} catch (OegamExcepcion e) {
			log.error("Ha sucedido un error a la hora de guardar el txt de 576, error: ",e);
		}
		return mapaReturn;

	}

	/**
	 * Construye la cadena de datos de la variable F01 (se construye a partir de las especificaciones del documento:
	 * 'DISEÃ‘O DE REGISTRO'. Segun ese diseÃ±o la cadena debe tener una longitud de 1517 caracteres)
	 * @param tramiteMatriculacionBean
	 * @return : La cadena de datos.
	 * @throws Exception
	 */
	private HashMap<String,Object> construirVariableF01(TramiteTraficoMatriculacionBean tramiteMatriculacionBean) throws Exception{

		HashMap<String,Object> mapa = new HashMap<String,Object>();
		ArrayList<String> datosRequeridosF01 = new ArrayList<String>();
		ArrayList<String> erroresFormato = new ArrayList<String>();
		String cabecera = null;

		// CABECERA
		cabecera = ConstantesAEAT.TAG_CABECERA_DATOS_INICIO;
		if(tramiteMatriculacionBean.getEjercicioDevengo576() != null){
			cabecera += tramiteMatriculacionBean.getEjercicioDevengo576();
		}else{
			datosRequeridosF01.add("Ejercicio de devengo");
		}
		cabecera += ConstantesAEAT.TAG_CABECERA_DATOS_FIN;
		cabecera += ConstantesAEAT.TAG_INICIO_AUX+BasicText.changeSize("", ConstantesAEAT.TAM_BLANCOS_AUX) + ConstantesAEAT.TAG_FIN_AUX;

		// En MAYUSCULAS el nombre de los 'campos' descritos en la documentacion: DISEÃ‘O DE REGISTRO

		String cadenaDeDatos = null;

		// ETIQUETA DE INICIO
		cadenaDeDatos = ConstantesAEAT.TAG_INICIO_DATOS;

		// IDENTIFICADOR DE MODELO
		cadenaDeDatos += ConstantesAEAT.ID_MODELO;

		// TIPO DE DECLARACION + RESERVADOS AEAT DE 9
		if(tramiteMatriculacionBean.getCuotaIngresar576() == null){
			datosRequeridosF01.add("Cuota a ingresar");
		}else if(tramiteMatriculacionBean.getCuotaIngresar576().intValue() > 0){
			cadenaDeDatos += BasicText.changeSize(ConstantesAEAT.INGRESO, 1);
		}else{
			cadenaDeDatos += BasicText.changeSize(ConstantesAEAT.NEGATIVA, 1);
		}
		// RESERVADO AEAT 9
		cadenaDeDatos = BasicText.changeSize(cadenaDeDatos, cadenaDeDatos.length()+9);

		// EJERCICIO DE DEVENGO + RESERVADOS AEAT DE 4
		if(tramiteMatriculacionBean.getEjercicioDevengo576() != null && !tramiteMatriculacionBean.getEjercicioDevengo576().toString().equals("-1")){
			cadenaDeDatos += BasicText.changeSize(String.valueOf(tramiteMatriculacionBean.getEjercicioDevengo576()), 8);
		}else{
			 datosRequeridosF01.add("Ejercicio de devengo");
		}
		// MANTIS: 0022719 Validacion Modelo576 Año Devengo tiene que venir con un año seleccionado

		// PERIODO
		cadenaDeDatos += ConstantesAEAT.PERIODO;

		// TIPO DE TRANSPORTE
		cadenaDeDatos += ConstantesAEAT.TIPO_TRANSPORTE;

		String datosPersonales = null;
		if(tramiteMatriculacionBean.getTitularBean().getPersona() != null){
			if(utilesConversiones.isNifNie(tramiteMatriculacionBean.getTitularBean().getPersona().getNif())){
				datosPersonales = tramiteMatriculacionBean.getTitularBean().getPersona().getNif();
				if(tramiteMatriculacionBean.getTitularBean().getPersona().getApellido1RazonSocial() != null){
					datosPersonales += tramiteMatriculacionBean.getTitularBean().getPersona().getApellido1RazonSocial() + " ";
				}
				if(tramiteMatriculacionBean.getTitularBean().getPersona().getApellido2() != null){
					datosPersonales += tramiteMatriculacionBean.getTitularBean().getPersona().getApellido2() + " ";
				}
				if(tramiteMatriculacionBean.getTitularBean().getPersona().getNombre() != null){
					datosPersonales += tramiteMatriculacionBean.getTitularBean().getPersona().getNombre();
				}
			}else{
				datosPersonales = tramiteMatriculacionBean.getTitularBean().getPersona().getNif();
				datosPersonales += tramiteMatriculacionBean.getTitularBean().getPersona().getApellido1RazonSocial();
			}
			// Comprueba la longitud de 'datosPersonales' para rellenar con espacios la longitud requerida (49)
			if(datosPersonales.length() > 49){
				erroresFormato.add("La longitud del nombre y los apellidos o la razon social no puede superar los 49 caracteres");
			}else if(datosPersonales.length() < 49){
				datosPersonales = BasicText.changeSize(datosPersonales, 49); 
			}

			// NIF/CIF NOMBRE,APELLIDOS/RAZON SOCIAL
			cadenaDeDatos += datosPersonales;
		}

		// RESERVADOS AEAT 279
		cadenaDeDatos = BasicText.changeSize(cadenaDeDatos, cadenaDeDatos.length() + 279);

		int ejercicio = 0;
		if(tramiteMatriculacionBean.getEjercicioDevengo576() != null){
			// MEDIO DE TRANSPORTE NUEVO/USADO (marca longitud cadenaDeDatos : 360)
			ejercicio = tramiteMatriculacionBean.getEjercicioDevengo576().intValue();
			if(ejercicio < 2008){
				// Segun documentacion: si ejercicio < 2008 establecer siempre 'nuevo'.
				cadenaDeDatos += ConstantesAEAT.NUEVO;
			}else{
				if(tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getVehiUsado() != null){
					if(tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getVehiUsado().equals("false")){
						cadenaDeDatos += ConstantesAEAT.NUEVO;
					}else{
						cadenaDeDatos += ConstantesAEAT.USADO;
					}
				}else{
					datosRequeridosF01.add("Nuevo o usado");
				}
			}

		}

		// LUGAR DE ADQUISICION DEL VEHICULO
		LugarAdquisicion lugarAdquisicion = null;
		String idFabricacion = null;
		if(tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getFabricacionBean() != null){
			idFabricacion = tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getFabricacionBean().getIdFabricacion();
		}
		String idPaisFabricacion = null;
		if(tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getPaisFabricacionBean() != null){
			idPaisFabricacion = tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getPaisFabricacionBean().getIdPaisFabricacion();
		}
		if((idFabricacion == null || idFabricacion.equals("")) && (idPaisFabricacion == null || idPaisFabricacion.equals(""))){
			datosRequeridosF01.add("Pais de fabricacion");
		}else if(idPaisFabricacion != null && !idPaisFabricacion.equals("")){
			lugarAdquisicion = PaisFabricacion.equivalenciaLugarAdquisicion(idPaisFabricacion);
		}else if(idFabricacion != null && !idFabricacion.equals("")){
			lugarAdquisicion = Fabricacion.equivalenciaLugarAdquisicion(idFabricacion);
		}

		if(lugarAdquisicion != null){
			if(ejercicio < 2008 && (lugarAdquisicion == LugarAdquisicion.OtrosEstadosMiembrosDeLaUE ||
					lugarAdquisicion == LugarAdquisicion.EstadoNoMiembroDeLaUE)){
				// Segun documentacion: si ejercicio < 2008 si es de fuera de espaÃ±a -> 2
				cadenaDeDatos += LugarAdquisicion.OtrosEstadosMiembrosDeLaUE.getValorEnum();
			}else{
				cadenaDeDatos += lugarAdquisicion.getValorEnum();
			}
		}

		// RESERVADO AEAT 1
		cadenaDeDatos = BasicText.changeSize(cadenaDeDatos,cadenaDeDatos.length() + 1);

		// FECHA DE PRIMERA MATRICULACION (VEHICULOS USADOS)
		if(tramiteMatriculacionBean.getTramiteTraficoBean().
				getVehiculo().getVehiUsado().equals("true")){
			if(tramiteMatriculacionBean.getTramiteTraficoBean().
					getVehiculo().getFechaPrimMatri() != null &&
					!tramiteMatriculacionBean.getTramiteTraficoBean().
					getVehiculo().getFechaPrimMatri().isfechaNula()){
				Fecha fecha = tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getFechaPrimMatri();
				// Construye una cadena acorde al formato del 576 -> DDMMYYYY
				String fechaPara576 = fecha.getDia();
				fechaPara576 += fecha.getMes();
				fechaPara576 += fecha.getAnio();
				cadenaDeDatos += fechaPara576;
			}else{
				datosRequeridosF01.add("Fecha de la primera matriculacion (vehiculos usados)");
			}
		}else{
			// Campo no obligatorio. Rellena con blancos 8 posiciones:
			// cadenaDeDatos = BasicText.changeSize(cadenaDeDatos, cadenaDeDatos.length() + 8);
			cadenaDeDatos += dameStringCeros(8);
		}

		// MARCA DEL VEHICULO
		if(tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMarcaBean() != null &&
				tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMarcaBean().getCodigoMarca() != null){
			// Recupera la descripcion con el codigo:
			MarcaDgtVO marcaDgtVO = getModeloTrafico().obtenerDescMarca(tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMarcaBean().getCodigoMarca());
			if(marcaDgtVO != null && !marcaDgtVO.getMarca().equals("")){
				// Comprueba que no exceda la longitud permitida (40)
				if(marcaDgtVO.getMarca().length() > 40){
					erroresFormato.add("La longitud de la marca excede el maximo de 40 caracteres"); 
				}else{
					String marca = marcaDgtVO.getMarca();
					// Rellena con blancos hasta 40 (longitud requerida)
					if(marca.length() < 40){
						marca = BasicText.changeSize(marca, 40);
					}
					cadenaDeDatos += marca;
				}
			}else{ 
				datosRequeridosF01.add("Marca");
			}
		}else{
			datosRequeridosF01.add("Marca");
		}

		// MODELO-TIPO
		if(tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getModelo() != null &&
				!tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getModelo().equals("")){
			// Comprueba que no exceda la longitud permitida (80)
			if(tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getModelo().length() > 80){
				erroresFormato.add("La longitud de modelo-tipo excede el maximo de 80 caracteres");
			}else{
				String modeloTipo = tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getModelo();
				// Rellena con blancos hasta 80 (longitud requerida)
				if(modeloTipo.length() < 80){
					modeloTipo = BasicText.changeSize(modeloTipo, 80);
				}
				cadenaDeDatos += modeloTipo;
			}
		}else{
			datosRequeridosF01.add("Modelo");
		}

		// N DE IDENTIFICACION (BASTIDOR)
		if(tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getBastidor() != null &&
				!tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getBastidor().equals("")){
			// Comprueba que no exceda la longitud permitida (22)
			if(tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getBastidor().length() > 22){
				erroresFormato.add("La longitud del bastidor excede el maximo de 22 caracteres");
			}else{
				String bastidor = tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getBastidor();
				// Rellena con blancos hasta 22 (longitud requerida)
				if(bastidor.length() < 22){
					bastidor = BasicText.changeSize(bastidor, 22);
				}
				cadenaDeDatos += bastidor;
			}
		}else{
			datosRequeridosF01.add("Bastidor");
		}

		// CLASIFICACION POR CRITERIO DE CONSTRUCCION Y DE UTILIZACION
		if(tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCriterioConstruccionBean() != null &&
				tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCriterioConstruccionBean().getIdCriterioConstruccion() != null &&
				!tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCriterioConstruccionBean().getIdCriterioConstruccion().equals("")){
			String criterioConstruccion = tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCriterioConstruccionBean().getIdCriterioConstruccion();
			// Comprueba la longitud:
			if(criterioConstruccion.length() > 2){
				erroresFormato.add("La longitud del criterio de construccion excede el maximo de 2 caracteres");
			}else{
				if(criterioConstruccion.length() < 2){
					criterioConstruccion = BasicText.changeSize(criterioConstruccion, 2);
				}
				cadenaDeDatos += criterioConstruccion;
			}
		}else{
			datosRequeridosF01.add("Criterio de construccion");
		}
		if(tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCriterioUtilizacionBean() != null &&
				tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCriterioUtilizacionBean().getIdCriterioUtilizacion() != null &&
				!tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCriterioUtilizacionBean().getIdCriterioUtilizacion().equals("")){
			String criterioUtilizacion = tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCriterioUtilizacionBean().getIdCriterioUtilizacion();
			// Comprueba la longitud:
			if(criterioUtilizacion.length() > 2){
				erroresFormato.add("La longitud del criterio de utilizacion excede el maximo de 2 caracteres");
			}else{
				if(criterioUtilizacion.length() < 2){
					criterioUtilizacion = BasicText.changeSize(criterioUtilizacion, 2);
				}
				cadenaDeDatos += criterioUtilizacion;
			}
		}else{
			datosRequeridosF01.add("Criterio de utilizacion");
		}

		// CLASIFICACION SEGUN DIRECTIVA CEE
		if(tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getHomologacionBean() != null &&
				tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getHomologacionBean().getIdHomologacion() != null &&
				!tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getHomologacionBean().getIdHomologacion().equals("")){
			String idDirectivaCEE = tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getHomologacionBean().getIdHomologacion();
			// Comprueba la longitud:
			if(idDirectivaCEE.length() > 2){
				// Si la longitud es mayor que 2, no se envia
				cadenaDeDatos += "  ";
//				erroresFormato.add("La longitud del identificador de la homologacion EU excede el maximo de 2 caracteres");
			}else{
				if(idDirectivaCEE.length() < 2){
					idDirectivaCEE = BasicText.changeSize(idDirectivaCEE, 2);
				}
				cadenaDeDatos += idDirectivaCEE;
			}
		}else{
			datosRequeridosF01.add("Clasificacion segun homologacion EU");
		}

		// RESERVADOS AEAT 8
		cadenaDeDatos = BasicText.changeSize(cadenaDeDatos,cadenaDeDatos.length() + 8);

		// EMISIONES CO2
		if(tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCo2() != null &&
				!tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCo2().equals("")){
			String co2 = tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCo2();
			// Segun documentacion, debe ir en gramos por kilometro, 5 posiciones, sin decimales y rellenando con ceros por la izquierda:
			// Elimina, si tiene los decimales y su notacion
			if(co2.contains(".")){
				co2 = co2.substring(0,co2.indexOf("."));
			}
			// Comprueba la longitud:
			if(co2.length() > 5){
				// Longitud de la parte entera mayor de la permitida:
				erroresFormato.add("La longitud de la emisión de CO2 excede el máximo de 5 dígitos enteros");
			}else{
				// Completa con ceros si la longitud del numero es menor que la requerida
				if(co2.length() < 5){
					co2 = utiles.rellenarCeros(co2.replace(".", ""), 5);
				}
				cadenaDeDatos += co2;
			}
		}else{
			datosRequeridosF01.add("Emision de co2");
		}

		// EPIGRAFE
		if(tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getEpigrafeBean() != null &&
				tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getEpigrafeBean().getIdEpigrafe() != null &&
				!tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getEpigrafeBean().getIdEpigrafe().equals("")){
			String epigrafe = tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getEpigrafeBean().getIdEpigrafe();
			// Comprueba la longitud:
			if(epigrafe.length() > 2){
				erroresFormato.add("La longitud del epígrafe excede el máximo de 2 caracteres");
			}else{
				if(epigrafe.length() < 2){
					epigrafe = BasicText.changeSize(epigrafe, 2,'0', false);
				}
				cadenaDeDatos += epigrafe;
			}
		}else{
			datosRequeridosF01.add("Epigrafe");
		}

		// KILOMETROS
		if(tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getVehiUsado().equals("true")){
			if(tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getKmUso() != null){
				String cadKilometros = tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getKmUso().toString();
				// Comprueba la longitud:
				if(cadKilometros.length() > 6){
					erroresFormato.add("La longitud de los kilometros de uso excede el maximo de 6 caracteres");
				}else{
					// Completa con ceros si la longitud del numero es menor que la requerida
					if(cadKilometros.length() < 6){
						cadKilometros = utiles.rellenarCeros(cadKilometros, 6);
					}
					cadenaDeDatos += cadKilometros;
				}
			}else{
				datosRequeridosF01.add("Kilometros de uso");
			}
		}else{
			// Campo no obligatorio. Rellena con blancos 6 posiciones:
			//cadenaDeDatos = BasicText.changeSize(cadenaDeDatos, cadenaDeDatos.length() + 6);
			cadenaDeDatos += dameStringCeros(6);
		}

		// NUMERO DE SERIE ITV
		if(tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getNumSerie() != null){
			String numSerieItv = tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getNumSerie();
			// Comprueba la longitud:
			if(numSerieItv.length() > 12){
				erroresFormato.add("La longitud del número de serie de la tarjeta ITV excede el máximo de 12 caracteres");
			}else{
				if(numSerieItv.length() < 12){
					numSerieItv = BasicText.changeSize(numSerieItv, 12);
				}
				cadenaDeDatos += numSerieItv;
			}
		}else{
			datosRequeridosF01.add("Numero de serie de la tarjeta ITV");
		}

		// TIPO DE TARJETA ITV
		String tipoTarjetaItv = null;
		if(tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getTipoTarjetaItvBean() != null &&
				tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getTipoTarjetaItvBean().getIdTipoTarjetaItv() != null &&
				!tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getTipoTarjetaItvBean().getIdTipoTarjetaItv().equals("")){
			tipoTarjetaItv = tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getTipoTarjetaItvBean().getIdTipoTarjetaItv();
			
			// Mantis 19403. David Sierra. Segun documentacion solo admite A y B como tipo de tarjeta itv:
			if(tipoTarjetaItv.equals("A") || tipoTarjetaItv.equals("B")|| tipoTarjetaItv.equals("C") || tipoTarjetaItv.equals("D")) {
				cadenaDeDatos += tipoTarjetaItv;
			}
			else if(tipoTarjetaItv.equals("AL") || tipoTarjetaItv.equals("AR") || tipoTarjetaItv.equals("AT")) {
				tipoTarjetaItv = "A";
				cadenaDeDatos += tipoTarjetaItv;
			}
			else if(tipoTarjetaItv.equals("BL") || tipoTarjetaItv.equals("BR") || tipoTarjetaItv.equals("BT")) {
				 tipoTarjetaItv = "B";
				 cadenaDeDatos += tipoTarjetaItv;
			}
			else if(!tipoTarjetaItv.equals("A") && !tipoTarjetaItv.equals("B")&& !tipoTarjetaItv.equals("C")&& !tipoTarjetaItv.equals("D")) {
				erroresFormato.add("Solo se consideran validas el tipo A, B, C o D para la tarjeta ITV");
			}
		}else{
			datosRequeridosF01.add("Tipo de la tarjeta ITV");
		}

		// COMBUSTIBLE DEL VEHICULO
		if(tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCarburanteBean() != null &&
				tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCarburanteBean().getIdCarburante() != null &&
				!tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCarburanteBean().getIdCarburante().equals("")){
			String idCarburante = tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCarburanteBean().getIdCarburante();
			Combustible combustible = Combustible.convertir(idCarburante);
			if(combustible == Combustible.Gasolina){
				idCarburante = ConstantesAEAT.GASOLINA;
			}else if(combustible == Combustible.Diesel){
				idCarburante = ConstantesAEAT.DIESEL;
			}else{
				// Segun documentacion solo admite gasolina, diesel y otros
				idCarburante = ConstantesAEAT.OTROS;
			}
			cadenaDeDatos += idCarburante;
		}else{
			datosRequeridosF01.add("Tipo de combustible");
		}

		// CILINDRADA
		if(tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCilindrada() != null &&
				!tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCilindrada().equals("")){
			String cilindrada = tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCilindrada();
			// Comprueba la longitud:
			if(cilindrada.length() > 5){
				erroresFormato.add("La longitud de la cilindrada excede el máximo de 5 caracteres");
			}else{
				//Convertimos posibles valores decimales a enteros
				cilindrada = String.valueOf(new Double(cilindrada).intValue());
				// Completa con ceros si la longitud del numero es menor que la requerida
				if(cilindrada.length() < 5){
					cilindrada = utiles.rellenarCeros(cilindrada, 5);
				}
				cadenaDeDatos += cilindrada;
			}
		}else{
			datosRequeridosF01.add("Cilindrada");
		}

		// RESERVADOS AEAT 303
		//cadenaDeDatos = BasicText.changeSize(cadenaDeDatos,cadenaDeDatos.length() + 303);

		cadenaDeDatos = BasicText.changeSize(cadenaDeDatos, cadenaDeDatos.length() + 142);

		cadenaDeDatos += dameStringCeros(5);

		cadenaDeDatos = BasicText.changeSize(cadenaDeDatos, cadenaDeDatos.length() + 142);

		cadenaDeDatos += dameStringCeros(14);

		// CÓDIGO ITV
		if(tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCodItv() != null &&
				!tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCodItv().equals("")){
			String codigoItv = tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCodItv();
			// Comprueba la longitud:
			if(codigoItv.length() > 9 || codigoItv.length() < 8){
				erroresFormato.add("La longitud del codigo itv debe ser 8 o 9 caracteres");
			}else{
				if(codigoItv.length() < 9){
					codigoItv = BasicText.changeSize(codigoItv, 9);
				}
				cadenaDeDatos += codigoItv;
			}
		}else{
			// Campo no obligatorio. Rellena con blancos 9 posiciones:
			cadenaDeDatos = BasicText.changeSize(cadenaDeDatos, cadenaDeDatos.length() + 9);
		}

		// RESERVADOS AEAT 223 (marca longitud cadenaDeDatos : 1094)
		cadenaDeDatos = BasicText.changeSize(cadenaDeDatos,cadenaDeDatos.length() + 223);

		// LIQUIDACION. BASE IMPONIBLE
		if(tramiteMatriculacionBean.getBaseImponible576() != null /*&&
			tramiteMatriculacionBean.getBaseImponible576() != BigDecimal.ZERO*/){
			// Segun documentacion debe ir con 11 enteros y dos decimales
			BigDecimal baseImponible = tramiteMatriculacionBean.getBaseImponible576();
			// Establece la parte decimal:
			baseImponible = utiles.getDecimales(2, baseImponible);
			// Establece la parte entera:
			String cadBaseImponible = utiles.rellenarCeros(baseImponible.toString().replace(".", ""), 13);
			// Quita el punto de los decimales porque excede la longitud:
			cadBaseImponible = cadBaseImponible.replace(".","");
			// AÃ±ade el nuevo dato a la cadena de datos:
			cadenaDeDatos += cadBaseImponible;
		}else{
			datosRequeridosF01.add("Base imponible");
		}

		// LIQUIDACION. BASE IMPONIBLE REDUCIDA
		if(tramiteMatriculacionBean.getBaseImponibleReducida576() != null /*&&
			tramiteMatriculacionBean.getBaseImponibleReducida576() != BigDecimal.ZERO*/){
			// Segun documentacion debe ir con 11 enteros y dos decimales
			BigDecimal baseImponibleReducida = tramiteMatriculacionBean.getBaseImponibleReducida576();
			// Establece la parte decimal:
			baseImponibleReducida = utiles.getDecimales(2, baseImponibleReducida);
			// Establece la parte entera:
			String cadBaseImponibleReducida = utiles.rellenarCeros(baseImponibleReducida.toString().replace(".",""), 13);
			// Quita el punto de los decimales porque excede la longitud:
			cadBaseImponibleReducida = cadBaseImponibleReducida.replace(".","");
			// AÃ±ade el nuevo dato a la cadena de datos:
			cadenaDeDatos += cadBaseImponibleReducida;
		}else{
			datosRequeridosF01.add("Base imponible reducida");
		}

		// LIQUIDACION. TIPO DE GRAVAMEN
		if(tramiteMatriculacionBean.getTipoGravamen() != null /*&&
			tramiteMatriculacionBean.getTipoGravamen() != BigDecimal.ZERO*/){
			// Segun documentacion debe ir con 3 enteros y 2 decimales
			BigDecimal tipoGravamen = tramiteMatriculacionBean.getTipoGravamen();
			// Establece la parte decimal:
			tipoGravamen = utiles.getDecimales(2, tipoGravamen);
			// Establece la parte entera:
			if(tipoGravamen.intValue() < 100){
				// Faltan enteros. Rellena con ceros:
				String cadTipoGravamen = utiles.rellenarCeros(tipoGravamen.toString().replace(".",""), 5);
				// Quita el punto de los decimales porque excede la longitud:
				cadTipoGravamen = cadTipoGravamen.replace(".","");
				cadenaDeDatos += cadTipoGravamen;
			}else{
				// Quita el punto de los decimales porque excede la longitud:
				cadenaDeDatos += tipoGravamen.toString().replace(".","");
			}

		}else{
			datosRequeridosF01.add("Tipo de gravamen");
		}

		// LIQUIDACION. CUOTA
		if(tramiteMatriculacionBean.getCuota576() != null /*&&
			tramiteMatriculacionBean.getCuota576() != BigDecimal.ZERO*/){
			// Segun documentacion debe ir con 11 enteros y 2 decimales
			BigDecimal cuota = tramiteMatriculacionBean.getCuota576();
			// Establece la parte decimal:
			cuota = utiles.getDecimales(2, cuota);
			// Establece la parte entera:
			String cadCuota = utiles.rellenarCeros(cuota.toString().replace(".", ""), 13);
			// Quita el punto de los decimales porque excede la longitud:
			cadCuota = cadCuota.replace(".","");
			// AÃ±ade el nuevo dato a la cadena de datos:
			cadenaDeDatos += cadCuota;
		}else{
			datosRequeridosF01.add("Cuota");
		}

		// LIQUIDACION. DEDUCCION LINEAL
		if(tramiteMatriculacionBean.getDeduccionLineal576() != null /*&&
			tramiteMatriculacionBean.getDeduccionLineal576() != BigDecimal.ZERO*/){
			// Segun documentacion debe ir con 11 enteros y 2 decimales
			BigDecimal deduccionLineal = tramiteMatriculacionBean.getDeduccionLineal576();
			// Establece la parte decimal:
			deduccionLineal = utiles.getDecimales(2, deduccionLineal);
			// Establece la parte entera:
			String cadDeduccionLineal = utiles.rellenarCeros(deduccionLineal.toString().replace(".", ""), 13);
			// Quita el punto de los decimales porque excede la longitud:
			cadDeduccionLineal = cadDeduccionLineal.replace(".","");
			// AÃ±ade el nuevo dato a la cadena de datos:
			cadenaDeDatos += cadDeduccionLineal;
		}else{
			datosRequeridosF01.add("Deduccion lineal");
		}

		// LIQUIDACION. CUOTA A INGRESAR
		if(tramiteMatriculacionBean.getCuotaIngresar576() != null /*&&
			tramiteMatriculacionBean.getCuotaIngresar576() != BigDecimal.ZERO*/){
			// Segun documentacion debe ir con 11 enteros y 2 decimales
			BigDecimal cuotaAIngresar = tramiteMatriculacionBean.getCuotaIngresar576();
			// Establece la parte decimal:
			cuotaAIngresar = utiles.getDecimales(2, cuotaAIngresar);
			// Establece la parte entera:
			String cadCuotaAIngresar = utiles.rellenarCeros(cuotaAIngresar.toString().replace(".", ""), 13);
			// Quita el punto de los decimales porque excede la longitud:
			cadCuotaAIngresar = cadCuotaAIngresar.replace(".","");
			// AÃ±ade el nuevo dato a la cadena de datos:
			cadenaDeDatos += cadCuotaAIngresar;
		}else{
			datosRequeridosF01.add("Cuota a ingresar");
		}

		// LIQUIDACION. A DEDUCIR
		if(tramiteMatriculacionBean.getDeducir576() != null /*&&
			tramiteMatriculacionBean.getDeducir576() != BigDecimal.ZERO*/){
			// Segun documentacion debe ir con 11 enteros y 2 decimales
			BigDecimal aDeducir = tramiteMatriculacionBean.getDeducir576();
			// Establece la parte decimal:
			aDeducir = utiles.getDecimales(2, aDeducir);
			// Establece la parte entera:
			String cadADeducir = utiles.rellenarCeros(aDeducir.toString().replace(".", ""), 13);
			// Quita el punto de los decimales porque excede la longitud:
			cadADeducir = cadADeducir.replace(".","");
			// AÃ±ade el nuevo dato a la cadena de datos:
			cadenaDeDatos += cadADeducir;
		}else{
			datosRequeridosF01.add("Cantidad a deducir");
		}

		// LIQUIDACION. RESULTADO DE LA LIQUIDACION
		if(tramiteMatriculacionBean.getLiquidacion576() != null /*&&
			tramiteMatriculacionBean.getLiquidacion576() != BigDecimal.ZERO*/){
			// Segun documentacion debe ir con 11 enteros y 2 decimales
			BigDecimal liquidacion = tramiteMatriculacionBean.getLiquidacion576();
			// Establece la parte decimal:
			liquidacion = utiles.getDecimales(2, liquidacion);
			// Establece la parte entera:
			String cadLiquidacion = utiles.rellenarCeros(liquidacion.toString().replace(".", ""), 13);
			// Quita el punto de los decimales porque excede la longitud:
			cadLiquidacion = cadLiquidacion.replace(".","");
			// AÃ±ade el nuevo dato a la cadena de datos:
			cadenaDeDatos += cadLiquidacion;
		}else{
			datosRequeridosF01.add("Resultado de la liquidacion");
		}

		// DECLARACION COMPLEMENTARIA
		if(tramiteMatriculacionBean.getNumDeclaracionComplementaria576() != null &&
				!tramiteMatriculacionBean.getNumDeclaracionComplementaria576().equals("")){
			String declaracionComplementaria = tramiteMatriculacionBean.getNumDeclaracionComplementaria576();
			// Comprueba la longitud de 'declaracionComplementaria' para rellenar con espacios la longitud requerida (13)
			if(declaracionComplementaria.length() > 13){
				erroresFormato.add("La longitud del numero de la declaracion complementaria no puede superar los 13 caracteres");
			}else if(declaracionComplementaria.length() < 13){
				declaracionComplementaria = BasicText.changeSize(declaracionComplementaria, 13);
			}
			cadenaDeDatos += declaracionComplementaria;
		}else{
			// Campo no obligatorio. Rellena con blancos 13 posiciones:
			//cadenaDeDatos = BasicText.changeSize(cadenaDeDatos, cadenaDeDatos.length() + 13);
			cadenaDeDatos += dameStringCeros(13);
		}

		// RESERVADOS AEAT 136 (marca longitud cadenaDeDatos : 1190)
		cadenaDeDatos = BasicText.changeSize(cadenaDeDatos,cadenaDeDatos.length() + 136);

		// CAUSA DEL HECHO IMPONIBLE
		if(tramiteMatriculacionBean.getCausaHechoImponible576() != null &&
				!tramiteMatriculacionBean.getCausaHechoImponible576().getValorEnum().equals("")){
			cadenaDeDatos += tramiteMatriculacionBean.getCausaHechoImponible576().getValorEnum();
		}else{
			datosRequeridosF01.add("Causa hecho imponible");
		}

		// RESERVADOS AEAT 8
		cadenaDeDatos = BasicText.changeSize(cadenaDeDatos,cadenaDeDatos.length() + 8);

		// NIF Y NOMBRE DEL INTRODUCTOR DEL VEHICULO EN ESPAÃ‘A (SI TIPO TARJETA ITV 'A')
		if(tipoTarjetaItv != null && tipoTarjetaItv.equalsIgnoreCase("A")){
			if(tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getIntegradorBean() == null){
				datosRequeridosF01.add("Para tipo tarjeta itv 'A' requerido datos del introductor del vehiculo en EspaÃ±a");
			}
			if(tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getIntegradorBean().getNif() == null){
				datosRequeridosF01.add("Para tipo tarjeta itv 'A' falta nif/cif del introductor del vehIculo en EspaÃ±a");
			}
			String datosIntegrador = null;
			if(utilesConversiones.isNifNie(tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getIntegradorBean().getNif())){
				datosIntegrador = tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getIntegradorBean().getNif();
				if(tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getIntegradorBean().getNombre() != null){
					datosIntegrador += tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getIntegradorBean().getNombre() + " ";
				}
				if(tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getIntegradorBean().getApellido1RazonSocial() != null){
					datosIntegrador += tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getIntegradorBean().getApellido1RazonSocial() + " ";
				}
				if(tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getIntegradorBean().getApellido2() != null){
					datosIntegrador += tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getIntegradorBean().getApellido2();
				}
			}else{
				datosIntegrador = tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getIntegradorBean().getNif();
				datosIntegrador += tramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getIntegradorBean().getApellido1RazonSocial();
			}
			// Comprueba la longitud de 'datosIntegrador' para rellenar con espacios la longitud requerida (49)
			if(datosIntegrador.length() > 49){
				erroresFormato.add("Error formato: La longitud del nombre y los apellidos o la razon social " +
						"no puede superar los 40 caracteres");
			}else if(datosIntegrador.length() < 49){
				datosIntegrador = BasicText.changeSize(datosIntegrador, 49); 
			}
			cadenaDeDatos += datosIntegrador;
		}else{
			// El tipo de tarjeta es distinto de A. Rellena los 49 espacios del integrador con blancos:
			cadenaDeDatos = BasicText.changeSize(cadenaDeDatos,cadenaDeDatos.length() + 49);
		}

		// RESERVADOS AEAT 84
		cadenaDeDatos = BasicText.changeSize(cadenaDeDatos,cadenaDeDatos.length() + 84);

		// VEHICULO OBSERVACIONES
		if(tramiteMatriculacionBean.getObservaciones576() != null){
			cadenaDeDatos += tramiteMatriculacionBean.getObservaciones576().getValorEnum();
		}else{
			datosRequeridosF01.add("Observaciones");
		}

		// RESERVADOS AEAT 25
		cadenaDeDatos = BasicText.changeSize(cadenaDeDatos,cadenaDeDatos.length() + 25);

		// Establece el pie de la cadena de datos:
		String pie = "</T57601>";
		cadenaDeDatos += pie;

		mapa.put(ConstantesAEAT.FICHERO_DECLARACION, cadenaDeDatos);

		cadenaDeDatos += ConstantesAEAT.TAG_PIE_DATOS_INICIO;
		if(tramiteMatriculacionBean.getEjercicioDevengo576() != null){
			cadenaDeDatos += String.valueOf(tramiteMatriculacionBean.getEjercicioDevengo576());
		}
		cadenaDeDatos += ConstantesAEAT.TAG_PIE_DATOS_FIN;

		// Le añado la cabecera:
		cabecera += cadenaDeDatos;

		mapa.put(ConstantesAEAT.F01, cabecera);

		// Mete la lista de los datos requeridos que faltan en el mapa del return:
		mapa.put("datosRequeridosF01", datosRequeridosF01);
		mapa.put("erroresFormato", erroresFormato);
		return mapa;

	}

	public ResultBean generarFichero576(
			TramiteTraficoMatriculacionBean traficoTramiteMatriculacionBean, String ficheroDeclaracion) {

		ResultBean resultadoGenerar576 = new ResultBean();

		if(null!=ficheroDeclaracion){
			resultadoGenerar576.addAttachment(ConstantesSession.FICHERO576, ficheroDeclaracion.getBytes());
			resultadoGenerar576.addAttachment(ConstantesSession.NOMBRE_FICH, "Mod576_" + traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente());
			resultadoGenerar576.setError(false);
			resultadoGenerar576.setMensaje("Correcto");
		}else{
			resultadoGenerar576.setError(true);
			resultadoGenerar576.setMensaje("No se ha podido obtener la linea");
		}

		return resultadoGenerar576;
	}

	/**
	 * Devuelve una cadena de tantos ceros como indique el parametro
	 * @param num de ceros de la cadena
	 * @return la cadena de ceros
	 */
	private String dameStringCeros(Integer num){
		String ceros = "";
		if(num == null || num < 1){
			return null;
		}else{
			for(int i = 1; i <= num; i++){
				ceros += "0";
			}
			return ceros;
		}
	}

	/**
	 * Rellena los campos que se dejan en blanco cuando se trata de un cuota cero
	 * y que sin embargo son requeridos como cero para la presentacion en la AEAT
	 * @param tramiteTraficoMatriculacionBean
	 * @return tramiteTraficoMatriculacionBean
	 */
	private TramiteTraficoMatriculacionBean rellenarRequeridosPresentacion(TramiteTraficoMatriculacionBean tramiteTraficoMatriculacionBean){
		// Cuota a ingresar
		if(tramiteTraficoMatriculacionBean.getCuotaIngresar576() == null){
			tramiteTraficoMatriculacionBean.setCuotaIngresar576(BigDecimal.ZERO);
		}
		// Base imponible reducida
		if(tramiteTraficoMatriculacionBean.getBaseImponibleReducida576() == null){
			tramiteTraficoMatriculacionBean.setBaseImponibleReducida576(BigDecimal.ZERO);
		}
		// Tipo de gravamen
		if(tramiteTraficoMatriculacionBean.getTipoGravamen() == null){
			tramiteTraficoMatriculacionBean.setTipoGravamen(BigDecimal.ZERO);
		}
		// Cuota
		if(tramiteTraficoMatriculacionBean.getCuota576() == null){
			tramiteTraficoMatriculacionBean.setCuota576(BigDecimal.ZERO);
		}
		// Deduccion lineal
		if(tramiteTraficoMatriculacionBean.getDeduccionLineal576() == null){
			tramiteTraficoMatriculacionBean.setDeduccionLineal576(BigDecimal.ZERO);
		}
		// Importe total
		if(tramiteTraficoMatriculacionBean.getImporte576() == null){
			tramiteTraficoMatriculacionBean.setImporte576(BigDecimal.ZERO);
		}
		// Cantidad a deducir
		if(tramiteTraficoMatriculacionBean.getDeducir576() == null){
			tramiteTraficoMatriculacionBean.setDeducir576(BigDecimal.ZERO);
		}
		// Resultado de la liquidacion
		if(tramiteTraficoMatriculacionBean.getLiquidacion576() == null){
			tramiteTraficoMatriculacionBean.setLiquidacion576(BigDecimal.ZERO);
		}
		return tramiteTraficoMatriculacionBean;
	}

	/* *********************************************************************** */
	/* MODELOS *************************************************************** */
	/* *********************************************************************** */
	// FIXME Si se implementa inyeccion por Spring quitar los ifs de los getters
	/* *********************************************************************** */

	public ModeloTrafico getModeloTrafico() {
		if (modeloTrafico == null) {
			modeloTrafico = new ModeloTrafico();
		}
		return modeloTrafico;
	}

	public void setModeloTrafico(ModeloTrafico modeloTrafico) {
		this.modeloTrafico = modeloTrafico;
	}
}