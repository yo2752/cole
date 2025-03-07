package trafico.modelo;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.displaytag.properties.SortOrderEnum;
import org.gestoresmadrid.core.direccion.model.vo.TipoViaVO;
import org.gestoresmadrid.core.model.enumerados.TipoSINO;
import org.gestoresmadrid.core.personas.model.enumerados.TipoPersona;
import org.gestoresmadrid.core.trafico.model.vo.JefaturaTraficoVO;
import org.gestoresmadrid.core.vehiculo.model.vo.MarcaDgtVO;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioTipoVia;
import org.gestoresmadrid.oegam2comun.sega.matw.view.xml.DatosEspecificos;
import org.gestoresmadrid.oegam2comun.sega.matw.view.xml.DatosEspecificos.Arrendatario;
import org.gestoresmadrid.oegam2comun.sega.matw.view.xml.DatosEspecificos.ConductorHabitual;
import org.gestoresmadrid.oegam2comun.sega.matw.view.xml.DatosEspecificos.DatosTecnicos;
import org.gestoresmadrid.oegam2comun.sega.matw.view.xml.DatosEspecificos.DatosTecnicos.Caracteristicas;
import org.gestoresmadrid.oegam2comun.sega.matw.view.xml.DatosEspecificos.DatosVehiculo;
import org.gestoresmadrid.oegam2comun.sega.matw.view.xml.DatosEspecificos.Impuestos;
import org.gestoresmadrid.oegam2comun.sega.matw.view.xml.DatosEspecificos.Titular;
import org.gestoresmadrid.oegam2comun.sega.matw.view.xml.DatosEspecificos.Tutor;
import org.gestoresmadrid.oegam2comun.sega.matw.view.xml.DatosGenericos;
import org.gestoresmadrid.oegam2comun.sega.matw.view.xml.DatosGenericos.Interesados;
import org.gestoresmadrid.oegam2comun.sega.matw.view.xml.Documentos;
import org.gestoresmadrid.oegam2comun.sega.matw.view.xml.ObjectFactory;
import org.gestoresmadrid.oegam2comun.sega.matw.view.xml.SolicitudRegistroEntrada;
import org.gestoresmadrid.oegam2comun.sega.matw.view.xml.TipoCambioDomicilio;
import org.gestoresmadrid.oegam2comun.sega.matw.view.xml.TipoDatoAsuntoMATG;
import org.gestoresmadrid.oegam2comun.sega.matw.view.xml.TipoDatoColegio;
import org.gestoresmadrid.oegam2comun.sega.matw.view.xml.TipoDatoColegio.DocumentoIdentificacion;
import org.gestoresmadrid.oegam2comun.sega.matw.view.xml.TipoDatoConductor;
import org.gestoresmadrid.oegam2comun.sega.matw.view.xml.TipoDatoDestino;
import org.gestoresmadrid.oegam2comun.sega.matw.view.xml.TipoDatoDireccion;
import org.gestoresmadrid.oegam2comun.sega.matw.view.xml.TipoDatoGestoria;
import org.gestoresmadrid.oegam2comun.sega.matw.view.xml.TipoDatoNombre;
import org.gestoresmadrid.oegam2comun.sega.matw.view.xml.TipoDatoTitular;
import org.gestoresmadrid.oegam2comun.sega.matw.view.xml.TipoDocumentoIdentificacion;
import org.gestoresmadrid.oegam2comun.sega.matw.view.xml.TipoInspeccion;
import org.gestoresmadrid.oegam2comun.sega.matw.view.xml.TipoSN;
import org.gestoresmadrid.oegam2comun.sega.utiles.XMLMatwSegaFactory;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoMatriculacion;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import colas.constantes.ConstantesProcesos;
import colas.modelo.ModeloSolicitud;
import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import escrituras.beans.ColegioBean;
import escrituras.beans.Direccion;
import escrituras.beans.Persona;
import escrituras.beans.ResultBean;
import escrituras.modelo.ModeloColegiado;
import general.modelo.ModeloBasePQ;
import general.utiles.BasicText;
import general.utiles.UtilesCadenaCaracteres;
import oegam.constantes.ConstantesPQ;
import trafico.beans.IntervinienteTrafico;
import trafico.beans.TramiteTraficoMatriculacionBean;
import trafico.beans.daos.BeanPQDatosColegio;
import trafico.beans.jaxb.matw.dgt.tipos.complejos.TipoVehiculoDGT;
import trafico.utiles.UtilesConversiones;
import trafico.utiles.XMLMatwFactory;
import trafico.utiles.enumerados.TipoTramiteMatriculacion;
import trafico.utiles.enumerados.TipoTutela;
import utilidades.estructuras.Fecha;
import utilidades.estructuras.ListaRegistros;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import viafirma.utilidades.UtilesViafirma;

public class ModeloMatriculacionSega extends ModeloBasePQ {

	private static final ILoggerOegam log = LoggerOegam.getLogger(ModeloMatriculacionSega.class);

	private static final String CODIGO_ASUNTO = "MATW";
	private static final String XML_VALIDO = "CORRECTO";
	private static final String DESCRIPCION_ASUNTO = "Solicitud de Matriculación Online para Gestores con Datos Técnicos";
	private static final String CODIGO_DESTINO = "101001";
	private static final String DESCRIPCION_DESTINO = "DGT - Vehículos";
	private static final String PAIS = "ESP";
	private static int _18 = 18;
	private static int _45 = 45;
	private static final String SIN_CODIG = "SINCODIG";

	private static final String _0 = "0";
	private static final String ERROR_AL_FIRMAR_EL_COLEGIO2 = "Error al firmar el Colegio";
	private static final String ERROR_AL_OBTENER_BYTES_UTF_8 = "Error al obtener bytes UTF-8";
	private static final String HA_OCURRIDO_UN_ERROR_RECUPERANDO_EL_XML_FIRMADO_A_TRAVES_DEL_ID_DE_FIRMA = "Ha ocurrido un error recuperando el xml firmado a traves del id de firma";
	private static final String HA_OCURRIDO_UN_ERROR_PROBANDO_LA_CADUCIDAD_DEL_CERTIFICADO = "Ha ocurrido un error probando la caducidad del certificado";
	private static final String ERROR_AL_FIRMAR_EL_COLEGIO = ERROR_AL_FIRMAR_EL_COLEGIO2;
	private static final String ERROR = "ERROR";
	private static final String VERSION_REGISTRO_ENTRADA = "3.0";
	private static final String VERSION = "1.1";
	private static final String ORGANISMO = "DGT";

	private ModeloSolicitud modeloSolicitud;
	private ModeloInterviniente modeloInterviniente;
	private ModeloCreditosTrafico modeloCreditosTrafico;
	private ModeloColegiado modeloColegiado;
	private ModeloTrafico modeloTrafico;
	private ModeloMatriculacion modeloMatriculacion;

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	ServicioTramiteTraficoMatriculacion servicioTramiteTraficoMatriculacion;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	ServicioTipoVia servicioTipoVia;

	@Autowired
	private UtilesConversiones utilesConversiones;

	@Autowired
	private UtilesColegiado utilesColegiado;

	@Override
	public ListaRegistros listarTabla(Integer pagina, Integer numeroElementosPagina, SortOrderEnum orden, String columnaOrden) {
		return null;
	}

	public ResultBean tramitarSega(TramiteTraficoMatriculacionBean traficoTramiteMatriculacionBean, BigDecimal idUsuario, BigDecimal idContrato) throws Throwable {
		ResultBean resultBean = new ResultBean(Boolean.FALSE);
		SolicitudRegistroEntrada solicitudRegistroEntrada = obtenerSolicitudRegistroEntrada(traficoTramiteMatriculacionBean);
		// Almacenará el XML firmado o el mensaje de error
		resultBean = anhadirFirmasHsm(solicitudRegistroEntrada);
		if (!resultBean.getError()){
			resultBean= completarSolicitud(solicitudRegistroEntrada, resultBean.getMensaje());
			if (!resultBean.getError()){
				resultBean = servicioTramiteTraficoMatriculacion.crearSolcitudMatriculacionConCambioEstado(solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().getNumeroExpedienteColegio(),idUsuario,idContrato,org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico.Pendiente_Envio, org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico.convertir(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getEstado().getValorEnum()));
			}
		}
		return resultBean;
	}

	private ResultBean completarSolicitud(SolicitudRegistroEntrada solicitudRegistroEntrada, String firmado) throws Throwable {
		ResultBean resultSolicitudCompleta = new ResultBean();

		FicheroBean ficheroBean = new FicheroBean();

		try{
			String nombre = ConstantesProcesos.PROCESO_MATW+solicitudRegistroEntrada.getDatosFirmados().getDatosGenericos().getNumeroExpediente();
			ficheroBean.setTipoDocumento(ConstantesGestorFicheros.MATE);
			ficheroBean.setSubTipo(ConstantesGestorFicheros.ENVIO);
			ficheroBean.setSobreescribir(true);
			ficheroBean.setExtension(ConstantesGestorFicheros.EXTENSION_XML);
			ficheroBean.setNombreDocumento(nombre);
			ficheroBean.setFecha(Utilidades.transformExpedienteFecha(solicitudRegistroEntrada.getDatosFirmados().getDatosGenericos().getNumeroExpediente()));

			gestorDocumentos.crearFicheroXml(ficheroBean, XMLMatwFactory.NAME_CONTEXT, solicitudRegistroEntrada, firmado, null);

		}catch (OegamExcepcion oe){
			resultSolicitudCompleta.setError(true);
			resultSolicitudCompleta.setMensaje(oe.getMessage());
			return resultSolicitudCompleta;
		}

		String xmlResultadoValidar = new XMLMatwFactory().validarXMLMATW(ficheroBean.getFichero());

		if (xmlResultadoValidar.equals(XML_VALIDO)) {
			resultSolicitudCompleta.setError(false);
			resultSolicitudCompleta.setMensaje(solicitudRegistroEntrada.getDatosFirmados().getDatosGenericos().getNumeroExpediente());
		} else {
			resultSolicitudCompleta.setError(true);
			resultSolicitudCompleta.setMensaje(" La petición no es válida debido al siguiente error: " + xmlResultadoValidar);
			try{
				log.error("Error validación XML:" + xmlResultadoValidar +" del expediente: " + solicitudRegistroEntrada.getDatosFirmados().getDatosGenericos().getNumeroExpediente());
			}catch (Exception e) {
				log.error("Excepcion al sacar el numExpediente del xml por error de validación. Detalle: " + e.getMessage());
				log.error("Error validación XML:" + xmlResultadoValidar);
			}
		}

		return resultSolicitudCompleta;
	}

	private ResultBean anhadirFirmasHsm(SolicitudRegistroEntrada solicitudRegistroEntrada) {
		ResultBean resultFirmasBean=new ResultBean();

		String aliasColegio= gestorPropiedades.valorPropertie("trafico.claves.colegiado.aliasHsm");

		ResultBean resultFirmaColegioHsm = realizarFirmaDatosFirmadosHsm(solicitudRegistroEntrada, aliasColegio);
		String xmlFirmadoColegio = resultFirmaColegioHsm.getMensaje();

		log.debug("XML firmado en servidor:" + xmlFirmadoColegio);

		if(!resultFirmaColegioHsm.getError()) {
			try{
				resultFirmasBean.setError(false);
				resultFirmasBean.setMensaje(xmlFirmadoColegio);
			}catch(Exception e){
				log.error(ERROR_AL_OBTENER_BYTES_UTF_8);
			}
		}else{
			resultFirmasBean.setError(true);
			resultFirmasBean.setMensaje(ERROR_AL_FIRMAR_EL_COLEGIO);
		}
		return resultFirmasBean;
	}

	private ResultBean realizarFirmaDatosFirmadosHsm(SolicitudRegistroEntrada solicitudRegistroEntrada, String aliasColegio) {
		XMLMatwSegaFactory xmlMatwFactory = new XMLMatwSegaFactory();
		String xml = xmlMatwFactory.toXML(solicitudRegistroEntrada);

		return firmarHsm(xml, aliasColegio);
	}

	private ResultBean firmarHsm(String cadenaXML,String alias) {
		UtilesViafirma utilesViafirma=new UtilesViafirma();
		ResultBean resultBean = new ResultBean();

		if(utilesViafirma.firmarPruebaCertificadoCaducidad(cadenaXML, alias)==null){
			// Ha fallado la prueba de caducidad del certificado
			log.error(HA_OCURRIDO_UN_ERROR_PROBANDO_LA_CADUCIDAD_DEL_CERTIFICADO);
			resultBean.setMensaje(HA_OCURRIDO_UN_ERROR_PROBANDO_LA_CADUCIDAD_DEL_CERTIFICADO);
			resultBean.setError(true);
		}else{
			String xmlFirmado= utilesViafirma.firmarMensajeMatriculacionServidor_MATW(cadenaXML,alias);

			if(xmlFirmado.equals(ERROR)) {
				// Ha fallado el proceso de firma en servidor
				log.error(HA_OCURRIDO_UN_ERROR_RECUPERANDO_EL_XML_FIRMADO_A_TRAVES_DEL_ID_DE_FIRMA);
				resultBean.setMensaje(HA_OCURRIDO_UN_ERROR_RECUPERANDO_EL_XML_FIRMADO_A_TRAVES_DEL_ID_DE_FIRMA);
				resultBean.setError(true);
			} else{// Firma correctamente
				resultBean.setError(false);
				resultBean.setMensaje(xmlFirmado);
			}
		}
		return resultBean;
	}

	private SolicitudRegistroEntrada obtenerSolicitudRegistroEntrada(TramiteTraficoMatriculacionBean traficoTramiteMatriculacionBean) {
		SolicitudRegistroEntrada solicitudRegistroEntrada = null;

		ObjectFactory objFactory = new ObjectFactory();

		Persona colegiadoCompleto = getModeloColegiado().obtenerColegiadoCompleto(utilesColegiado.getIdContratoSessionBigDecimal());
		//obtenemos datos del colegio
		BeanPQDatosColegio beanPQDatosColegio = new BeanPQDatosColegio();
		beanPQDatosColegio.setP_COLEGIO(utilesColegiado.getColegioDelContrato());
		ColegioBean colegioBean = (ColegioBean) getModeloTrafico().obtenerDatosColegio(beanPQDatosColegio).get(ConstantesPQ.BEANPANTALLA);

		//-----------------ELEMENTO RAIZ----------------------
		solicitudRegistroEntrada= (SolicitudRegistroEntrada) objFactory.createSolicitudRegistroEntrada();

		solicitudRegistroEntrada.setDatosFirmados(objFactory.createDatosFirmados());
		solicitudRegistroEntrada.setVersion(VERSION_REGISTRO_ENTRADA);

		//--------------------------------------------------DATOS FIRMADOS----------------------------------------------------------------------------

		DatosEspecificos datosEspecificos = objFactory.createDatosEspecificos();
		Documentos documentos = objFactory.createDocumentos();
		DatosGenericos datosGenericos = objFactory.createDatosGenericos();

		solicitudRegistroEntrada.getDatosFirmados().setDocumentos(documentos);
		solicitudRegistroEntrada.getDatosFirmados().setDatosGenericos(datosGenericos);
		solicitudRegistroEntrada.getDatosFirmados().setDatosEspecificos(datosEspecificos);

		//----------------------------------------------------DOCUMENTOS------------------------------------------

		//--------------------------------------------------DATOS GENERICOS---------------------------------------------------------------------------

		//TIPO DATO ASUNTO:
		TipoDatoAsuntoMATG tipoDatoAsunto = new TipoDatoAsuntoMATG();
		tipoDatoAsunto.setCodigo(CODIGO_ASUNTO);
		tipoDatoAsunto.setDescripcion(DESCRIPCION_ASUNTO);
		//TIPO DATO DESTINO
		TipoDatoDestino tipoDatoDestino = new TipoDatoDestino();
		tipoDatoDestino.setCodigo(CODIGO_DESTINO);
		tipoDatoDestino.setDescripcion(DESCRIPCION_DESTINO);
		//TIPO DATO COLEGIO
		TipoDatoColegio tipoDatoColegioRemitente = new TipoDatoColegio();
		tipoDatoColegioRemitente.setApellidos("");
		/* INICIO Mantis 0011046: (ihgl) se incluye el correo electrónico del colegio para alinear el XML con el de CTIT */
		String correoElectronico = (colegioBean.getCorreoElectronico() != null) ? colegioBean.getCorreoElectronico() : "";
		tipoDatoColegioRemitente.setCorreoElectronico(correoElectronico);
		/* FIN Mantis 0011046 (ihgl) */
		tipoDatoColegioRemitente.setNombre(colegioBean.getNombre());
		DocumentoIdentificacion documentoIdentificacion = new DocumentoIdentificacion();
		documentoIdentificacion.setNumero(colegioBean.getCif());
		tipoDatoColegioRemitente.setDocumentoIdentificacion(documentoIdentificacion);

		// Datos genéricos
		datosGenericos.setAsunto(tipoDatoAsunto);
		datosGenericos.setDestino(tipoDatoDestino);

		//INTERESADOS
		Interesados interesados = objFactory.createDatosGenericosInteresados();
		datosGenericos.setInteresados(interesados);
		TipoDocumentoIdentificacion tipoDocumentoIdentificacion = new TipoDocumentoIdentificacion();
		tipoDocumentoIdentificacion.setNumero(colegiadoCompleto.getNif());

		//TIPODATOGESTORIA -> para interesados
		TipoDatoGestoria tipoDatoGestoria = new TipoDatoGestoria();
		tipoDatoGestoria.setCorreoElectronico(colegiadoCompleto.getCorreoElectronico());
		tipoDatoGestoria.setDocumentoIdentificacion(tipoDocumentoIdentificacion);

		String nombreUsuario = colegiadoCompleto.getNombre();

		if(nombreUsuario.length() > _18){
			nombreUsuario = nombreUsuario.substring(0, _18);
		}
		tipoDatoGestoria.setNombre(nombreUsuario);

		String apellidosUsuario = colegiadoCompleto.getApellido1RazonSocial();
		if(null != apellidosUsuario && !apellidosUsuario.equals("")){
			apellidosUsuario += " " + colegiadoCompleto.getApellido2();
			if(nombreUsuario.length() > _45 ){
				apellidosUsuario = apellidosUsuario.substring(0, _45);
			}
			tipoDatoGestoria.setApellidos(apellidosUsuario);
		}

		interesados.setInteresado(tipoDatoGestoria);

		//COMPLETAMOS DATOS GENERICOS
		datosGenericos.setOrganismo(ORGANISMO);
		datosGenericos.setInteresados(interesados);
		datosGenericos.setNumeroExpediente(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente().toString());
		datosGenericos.setRemitente(tipoDatoColegioRemitente);

		//--------------------------------------------------DATOS ESPECIFICOS--------------------------------------------------------------------------

		Titular titular = objFactory.createDatosEspecificosTitular();
		Arrendatario arrendatario = objFactory.createDatosEspecificosArrendatario();
		ConductorHabitual conductorHabitual = objFactory.createDatosEspecificosConductorHabitual();
		DatosVehiculo datosVehiculo = objFactory.createDatosEspecificosDatosVehiculo();
		Tutor tutor = objFactory.createDatosEspecificosTutor();

		TipoDatoDireccion tipoDatoDireccionVehiculo = new TipoDatoDireccion();
		if(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getDireccionBean() != null
				&& traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getDireccionBean().getIdDireccion() != null){
			tipoDatoDireccionVehiculo = beanToXmldireccion(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getDireccionBean());
			datosEspecificos.setDomicilioVehiculo(tipoDatoDireccionVehiculo);
		}else{
			// Rellena la dirección del vehículo con la dirección del titular si no hay datos específicos de dirección del vehículo:
			tipoDatoDireccionVehiculo = beanToXmldireccion(traficoTramiteMatriculacionBean.getTitularBean().getPersona().getDireccion());
			datosEspecificos.setDomicilioVehiculo(tipoDatoDireccionVehiculo);
		}

		// datosEspecificos.setDatosTecnicos(completaDatosTecnicos(traficoTramiteMatriculacionBean));

		//----------------NUM EXPEDIENTE COLEGIO
		datosEspecificos.setNumeroExpedienteColegio(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente().toString());

		//----- IMPUESTOS

		//IMPUESTOS
		Impuestos impuestos = objFactory.createDatosEspecificosImpuestos();
		if(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getCemIedtm() != null &&
				!traficoTramiteMatriculacionBean.getTramiteTraficoBean().getCemIedtm().equals("")){
			impuestos.setCEM(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getCemIedtm());
		}
		impuestos.setCEMA(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getCema());
		if(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumRegIedtm() != null &&
				traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumRegIedtm().getValorEnum() != null &&
				!traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumRegIedtm().getValorEnum().equals("")){
			impuestos.setExencionCEM(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumRegIedtm().getValorEnum());
		}

		//JMC CEMU desaparece de la Primera Version de MATW

		//impuestos.setCEMU(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getCemu());

		if(traficoTramiteMatriculacionBean.getJustificado_IVTM() != null && traficoTramiteMatriculacionBean.getJustificado_IVTM().equalsIgnoreCase("true")){
			impuestos.setJustificadoIVTM(TipoSN.S);
		}else if(traficoTramiteMatriculacionBean.getJustificado_IVTM() != null && traficoTramiteMatriculacionBean.getJustificado_IVTM().equalsIgnoreCase("false")){
			impuestos.setJustificadoIVTM(TipoSN.N);
		}

		datosEspecificos.setImpuestos(impuestos);

		//--------------VERSION-------------------
		datosEspecificos.setVersion(VERSION);

		//-----------------------------------------

		datosEspecificos.setTasa(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getTasa().getCodigoTasa());

		JefaturaTraficoVO jefatura = getModeloTrafico().obtenerJefaturaCompleta(traficoTramiteMatriculacionBean.getTramiteTraficoBean().
				getJefaturaTrafico().getJefaturaProvincial());

		String sucursal = jefatura.getSucursal();
		if (!"1".equals(sucursal)) {
			String activarSucursal = gestorPropiedades.valorPropertie("activar.sucursal.alcala.matw.xml");
			if ("2".equals(sucursal) && "SI".equals(activarSucursal)) {
				datosEspecificos.setSucursal(sucursal);
			} else {
				datosEspecificos.setSucursal("");
			}
		} else {
			datosEspecificos.setSucursal(sucursal);
		}

		datosEspecificos.setJefatura(jefatura.getJefatura());

		//JMC id_matricula_anotador Desaparece la Primera version de Matw. No estaba en MATE 2.5
		//datosEspecificos.setIdMatriculaAnotador(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getNumExpediente().toString());

		datosEspecificos.setTipoTramite(traficoTramiteMatriculacionBean.getTipoTramiteMatriculacion().getValorEnum());

		//---------------TITULAR---------------------

		//DOMICILIO -> tipoCambioDomicilio
		TipoCambioDomicilio tipoCambioDomicilioTitular = new TipoCambioDomicilio();

		if (traficoTramiteMatriculacionBean.getTitularBean().getCambioDomicilio()!=null &&
			!("").equals(traficoTramiteMatriculacionBean.getTitularBean().getCambioDomicilio())) {
			if (traficoTramiteMatriculacionBean.getTitularBean().getCambioDomicilio().equalsIgnoreCase("true")){
				tipoCambioDomicilioTitular.setCambioDomicilio(TipoSN.S.value());
			}else{ 
				tipoCambioDomicilioTitular.setCambioDomicilio(TipoSN.N.value());
			}
			tipoCambioDomicilioTitular.setDatosDomicilio(beanToXmldireccion(traficoTramiteMatriculacionBean.getTitularBean().getPersona().getDireccion()));
		}
		else tipoCambioDomicilioTitular.setCambioDomicilio(TipoSN.N.value());

		titular.setDomicilioTitular(tipoCambioDomicilioTitular);

		//TIPO DATO TITULAR -> Identificacion
		TipoDatoTitular tipoDatoTitular = beanToXmlTipoDatoTitular(traficoTramiteMatriculacionBean.getTitularBean());
		if(traficoTramiteMatriculacionBean.getRepresentanteTitularBean().getPersona().getNif() != null &&
				!"".equals(traficoTramiteMatriculacionBean.getRepresentanteTitularBean().getPersona().getNif())){
			// Setea el representante si no se trata de tutor. Si lo fuera irán sus datos en el elemento <Tutor>
			if(traficoTramiteMatriculacionBean.getRepresentanteTitularBean().getIdMotivoTutela() == null){
				TipoDocumentoIdentificacion tipoDocumentoIdentificacionRepresentante = new TipoDocumentoIdentificacion();
				tipoDocumentoIdentificacionRepresentante.setNumero(
					traficoTramiteMatriculacionBean.getRepresentanteTitularBean().getPersona().getNif());
				tipoDatoTitular.setDocumentoIdentidadRepresentante(tipoDocumentoIdentificacionRepresentante);
			}
		}
		titular.setIdentificacion(tipoDatoTitular);
		//SERVICIO AUTONOMO
		DatosEspecificos.Titular.ServicioAutonomo servicioAutonomo = objFactory.createDatosEspecificosTitularServicioAutonomo();

		if (null != traficoTramiteMatriculacionBean.getTitularBean().getCodigoIAE()
				&& !"".equals(traficoTramiteMatriculacionBean.getTitularBean().getCodigoIAE())) {
			servicioAutonomo.setAutonomo(TipoSN.S.value());
			servicioAutonomo.setCodigoIAE(new BigInteger(traficoTramiteMatriculacionBean.getTitularBean().getCodigoIAE()));
		} else {
			servicioAutonomo.setAutonomo(TipoSN.N.value());
			servicioAutonomo.setCodigoIAE(null);
		}
		titular.setServicioAutonomo(servicioAutonomo);

		// Rellenamos titular
		solicitudRegistroEntrada.
		getDatosFirmados().
		getDatosEspecificos().
		setTitular(titular);

		//------------------ARRENDATARIO-----------------

		if (traficoTramiteMatriculacionBean.getArrendatarioBean()!=null)
			if (traficoTramiteMatriculacionBean.getArrendatarioBean().getPersona().getNif()!=null&&
			!traficoTramiteMatriculacionBean.getArrendatarioBean().getPersona().getNif().equals("")) {

				//DOMICILIO -> tipoCambioDomicilio
				TipoCambioDomicilio tipoCambioDomicilioArrendatario = new TipoCambioDomicilio();

				if (traficoTramiteMatriculacionBean.getArrendatarioBean().getCambioDomicilio()!=null &&
						!("".equals(traficoTramiteMatriculacionBean.getArrendatarioBean().getCambioDomicilio()))) {
					if (traficoTramiteMatriculacionBean.getArrendatarioBean().getCambioDomicilio().equalsIgnoreCase("true")){
						tipoCambioDomicilioArrendatario.setCambioDomicilio(TipoSN.S.value());
					}else{
						tipoCambioDomicilioArrendatario.setCambioDomicilio(TipoSN.N.value());
					}
					tipoCambioDomicilioArrendatario.setDatosDomicilio(beanToXmldireccion(traficoTramiteMatriculacionBean.getArrendatarioBean().getPersona().getDireccion()));
				} else
					tipoCambioDomicilioArrendatario.setCambioDomicilio(TipoSN.N.value());

				arrendatario.setDomicilioArrendatario(tipoCambioDomicilioArrendatario);

				Fecha fechaInicioArrendatario = traficoTramiteMatriculacionBean.getArrendatarioBean().getFechaInicio();
				if(fechaInicioArrendatario != null && !fechaInicioArrendatario.isfechaNula()){
					arrendatario.setFechaInicio(utilesFecha.invertirFecha(traficoTramiteMatriculacionBean.getArrendatarioBean().getFechaInicio()));
				}

				Fecha fechaFinArrendatario = traficoTramiteMatriculacionBean.getArrendatarioBean().getFechaFin();
				if (fechaFinArrendatario != null && !fechaFinArrendatario.isfechaNula()){
					arrendatario.setFechaFin(utilesFecha.invertirFecha(traficoTramiteMatriculacionBean.getArrendatarioBean().getFechaFin()));
				}

				if(traficoTramiteMatriculacionBean.getArrendatarioBean().getHoraInicio()!=null) {
					if(!traficoTramiteMatriculacionBean.getArrendatarioBean().getHoraInicio().equals("")) {
						String hora = (traficoTramiteMatriculacionBean.getArrendatarioBean().getHoraInicio());
						hora = hora.replace(":", "");
						arrendatario.setHoraInicio(new BigInteger(hora));
					}
				} else {
					arrendatario.setHoraInicio(null);
				}

				if(traficoTramiteMatriculacionBean.getArrendatarioBean().getHoraFin()!=null) {
					if(!traficoTramiteMatriculacionBean.getArrendatarioBean().getHoraFin().equals("")) {
						String hora = traficoTramiteMatriculacionBean.getArrendatarioBean().getHoraFin();
						hora = hora.replace(":", "");
						arrendatario.setHoraFin(new BigInteger(hora));
					}
				} else {
					arrendatario.setHoraFin(null);
				}

				//TIPO DATO ARRENDATARIO -> Identificacion
				TipoDatoTitular tipoDatoArrendatario = beanToXmlTipoDatoTitular(traficoTramiteMatriculacionBean.getArrendatarioBean());
				if(traficoTramiteMatriculacionBean.getRepresentanteArrendatarioBean().getPersona().getNif() != null &&
						!traficoTramiteMatriculacionBean.getRepresentanteArrendatarioBean().getPersona().getNif().equals("")){
					TipoDocumentoIdentificacion tipoDocumentoIdentificacionRepresentante = new TipoDocumentoIdentificacion();
					tipoDocumentoIdentificacionRepresentante.setNumero(
							traficoTramiteMatriculacionBean.getRepresentanteArrendatarioBean().getPersona().getNif());
					tipoDatoArrendatario.setDocumentoIdentidadRepresentante(tipoDocumentoIdentificacionRepresentante);
				}
				arrendatario.setIdentificacion(tipoDatoArrendatario);

				//SERVICIO AUTÓNOMO
				//no tiene

				// Rellenamos arrendatario

				if (traficoTramiteMatriculacionBean.getArrendatarioBean().getPersona().getNif()==null ||
						!traficoTramiteMatriculacionBean.getArrendatarioBean().getPersona().getNif().equals("")) {
					solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().setArrendatario(arrendatario);
				}

			} else {
				solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().setArrendatario(null);
			}

		//--------------------REPRESENTANTE-TUTOR----------------------

		if (traficoTramiteMatriculacionBean.getRepresentanteTitularBean().getIdMotivoTutela()!=null)
			tutor = completaDatosTutor(tutor,traficoTramiteMatriculacionBean);
		else tutor = null;

		solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().setTutor(tutor);

		//------------------CONDUCTOR HABITUAL----------

		if (traficoTramiteMatriculacionBean.getConductorHabitualBean().getPersona().getNif()!=null &&
				!traficoTramiteMatriculacionBean.getConductorHabitualBean().getPersona().getNif().equals("")) {
			TipoDatoConductor tipoDatoConductor = new TipoDatoConductor();
			TipoDocumentoIdentificacion tipoDocumentoIdentificacionCH = new TipoDocumentoIdentificacion();
			tipoDocumentoIdentificacionCH.setNumero(traficoTramiteMatriculacionBean.getConductorHabitualBean().getPersona().getNif());
			tipoDatoConductor.setDocumentoIdentidad(tipoDocumentoIdentificacionCH);
			conductorHabitual.setIdentificacion(tipoDatoConductor);

			//DOMICILIO -> tipoCambioDomicilio
			TipoCambioDomicilio tipoCambioDomicilioCH = new TipoCambioDomicilio();

			if (traficoTramiteMatriculacionBean.getConductorHabitualBean().getCambioDomicilio()!=null &&
					!("".equals(traficoTramiteMatriculacionBean.getConductorHabitualBean().getCambioDomicilio()))) {
				if (traficoTramiteMatriculacionBean.getConductorHabitualBean().getCambioDomicilio().equalsIgnoreCase("true")){
					tipoCambioDomicilioCH.setCambioDomicilio(TipoSN.S.value());
				}else{ 
					tipoCambioDomicilioCH.setCambioDomicilio(TipoSN.N.value());
				}
				tipoCambioDomicilioCH.setDatosDomicilio(beanToXmldireccion(traficoTramiteMatriculacionBean.getConductorHabitualBean().getPersona().getDireccion()));
			} else
				tipoCambioDomicilioCH.setCambioDomicilio(TipoSN.N.value());

			conductorHabitual.setDomicilioConductor(tipoCambioDomicilioCH);

			if(traficoTramiteMatriculacionBean.getConductorHabitualBean().getHoraFin()!=null){
				if(!traficoTramiteMatriculacionBean.getConductorHabitualBean().getHoraFin().equals("")){
					String hora = traficoTramiteMatriculacionBean.getConductorHabitualBean().getHoraFin();
					hora = hora.replace(":", "");
					conductorHabitual.setHoraFin(new BigInteger(hora));
				}
			}else {
				conductorHabitual.setHoraFin(null);
			}

			if(traficoTramiteMatriculacionBean.getConductorHabitualBean().getFechaFin() != null &&
					!traficoTramiteMatriculacionBean.getConductorHabitualBean().getFechaFin().isfechaNula()){
				conductorHabitual.setFechaFin(utilesFecha.invertirFecha(traficoTramiteMatriculacionBean.getConductorHabitualBean().getFechaFin()));
			}
		} else conductorHabitual =null;

		// Rellenamos conductor habitual
		solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().setConductorHabitual(conductorHabitual);

		//----------------DATOS VEHICULO---------------

		datosVehiculo.setBastidor(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getBastidor());
		if(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getSubasta() !=null &&
				traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getSubasta()){
			datosVehiculo.setSubasta(TipoSN.S.value());
		}else{
			datosVehiculo.setSubasta(TipoSN.N.value());
		}

		//JMB - Cambios MATW 
		//datosVehiculo.setCuentaHoras(null);

		//JMC - Desaparece Diplomática en la última versión de Matw
		//datosVehiculo.setDiplomatica("");
		if(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMotivoBean() != null &&
				traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMotivoBean().getIdMotivo() != null){

			if(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMotivoBean().getIdMotivo().equalsIgnoreCase("M")){
				datosVehiculo.setTipoInspeccionItv(TipoInspeccion.M.value());
			}
			// Mantis 15845. David Sierra: Se ha agregado a tipoInspeccionItv E: Exento ITV.
			else if(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMotivoBean().getIdMotivo().equalsIgnoreCase("E")){
				datosVehiculo.setTipoInspeccionItv(TipoInspeccion.E.value());
			}
		}

		Fecha fechaValidezItv = traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getFechaItv();
		if(fechaValidezItv != null && !fechaValidezItv.isfechaNula()){
			datosVehiculo.setFechaValidezITV(utilesFecha.invertirFecha(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getFechaItv()));
		}
		// Mantis 15845. Mantis 15845. David Sierra: Si tipoInspeccionItv es Exento se vacia fechaValidezItv
		if(fechaValidezItv != null && !fechaValidezItv.isfechaNula() && "E".equals(datosVehiculo.getTipoInspeccionItv())) {
			datosVehiculo.setFechaValidezITV(null);
		}
		// RRR LA ESTACION ITV DESAPARECE DE LA VERSION INICIAL DE MATW
		//datosVehiculo.setEstacionITV(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getEstacionItv());

		// DATOS DE VEHÍCULO USADO
		// MATW. Seteo de los datos de vehículo usado solo si en la jsp se marca el check de 'usado'
		// USADO
		if(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getVehiUsado() != null &&
				traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getVehiUsado().equalsIgnoreCase("true")){
			datosVehiculo.setUsado(TipoSN.S.value());
		}else{
			datosVehiculo.setUsado(TipoSN.N.value());
		}
		if(datosVehiculo.getUsado() != null && datosVehiculo.getUsado().equals(TipoSN.S.value())){
			// FECHA DE LA PRIMERA MATRICULACIÓN
			Fecha fechaPrimeraMatriculacion = traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getFechaPrimMatri();
			if(fechaPrimeraMatriculacion != null && !fechaPrimeraMatriculacion.isfechaNula()){
				datosVehiculo.setFechaPrimeraMatriculacion(utilesFecha.invertirFecha(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().
						getFechaPrimMatri()));
			}
			// MATRÍCULA ORIGEN
			if (traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMatriculaOrigen()!=null &&
					!traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMatriculaOrigen().equals("")
					&& TipoTramiteMatriculacion.REMATRICULAR.equals(traficoTramiteMatriculacionBean.getTipoTramiteMatriculacion())){
				datosVehiculo.setMatriculaOrigen(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMatriculaOrigen());
			}
			// KILÓMETROS DE USO
			if (traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getKmUso()!=null
					&& traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getKmUso().toString().length()<=6){
				datosVehiculo.setKilometraje(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getKmUso().toBigInteger());
			} else if(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getKmUso()!=null 
					&& traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getKmUso().toString().length()>6){
				datosVehiculo.setKilometraje(new BigDecimal(999999).toBigInteger());
			}
			// HORAS DE USO
			if(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getHorasUso()!=null
					&& traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getHorasUso().toString().length()<=6){
				datosVehiculo.setCuentaHoras(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getHorasUso().toBigInteger());
			} else if(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getHorasUso()!=null
					&& traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getHorasUso().toString().length()>6){
				datosVehiculo.setCuentaHoras(new BigDecimal(999999).toBigInteger());
			}
			// IMPORTADO
			if(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getImportado() != null &&
					traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getImportado()){
				datosVehiculo.setImportado(TipoSN.S.value());
				// Matrícula Extranjera

				if (traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo() != null &&
						traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMatriculaOrigExtr() != null &&
						!traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMatriculaOrigExtr().equals("") &&
						TipoTramiteMatriculacion.REMATRICULAR.equals(traficoTramiteMatriculacionBean.getTipoTramiteMatriculacion())){
					datosVehiculo.setMatriculaExtranjera(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMatriculaOrigExtr());
				}

			}else{
				datosVehiculo.setImportado(TipoSN.N.value());
			}
			// PROCEDENCIA. Está dentro de los datos técnicos. ver método 'completaDatosTecnicos('
		}
		// FIN DE DATOS DE VEHÍCULO USADO

		if (null != traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getServicioTraficoBean().getIdServicio() &&
				!traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getServicioTraficoBean().getIdServicio().equals("") &&
				!traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getServicioTraficoBean().getIdServicio().equals("-1"))
		{
			datosVehiculo.setServicio(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getServicioTraficoBean().getIdServicio());
		}

		//Campo nuevo Carsharing, Mantis 22806
		if(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getCarsharing() != null
				&& !traficoTramiteMatriculacionBean.getTramiteTraficoBean().getCarsharing().isEmpty()){
			if("true".equals(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getCarsharing())){
				datosVehiculo.setCarsharing("S");
			}else{
				datosVehiculo.setCarsharing("N");
			}
		}

		//Nuevos Campos Matw

		// Rellenamos vehículo
		solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().setDatosVehiculo(datosVehiculo);
		// Nuevos Matw NIVE jbc
		if(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getNive()!=null &&
				!traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getNive().equals("")){
			datosVehiculo.setNIVE(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getNive());
			solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().setDatosTecnicos(new DatosTecnicos());
			//Tipo tarjeta itv
			if (traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getTipoTarjetaItvBean().getIdTipoTarjetaItv()!=null &&
					!traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getTipoTarjetaItvBean().getIdTipoTarjetaItv().isEmpty()){
				solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().getDatosTecnicos().setTipoTarjetaItv(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getTipoTarjetaItvBean().getIdTipoTarjetaItv());
			}
			//Tipo vehículo
			if (traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getTipoVehiculoBean() != null && traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getTipoVehiculoBean().getTipoVehiculo()!=null &&
					!traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getTipoVehiculoBean().getTipoVehiculo().isEmpty()){
				solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().getDatosTecnicos().setTipoVehiculoDGT(traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getTipoVehiculoBean().getTipoVehiculo());
			}
		} else{
			solicitudRegistroEntrada.getDatosFirmados().getDatosEspecificos().setDatosTecnicos(completaDatosTecnicos(traficoTramiteMatriculacionBean));
		}

		return solicitudRegistroEntrada;
	}

	private DatosTecnicos completaDatosTecnicos(TramiteTraficoMatriculacionBean tramiteTraficoMatriculacionBean) {
		String valor = null;
		ObjectFactory objFactory = new ObjectFactory();
		DatosTecnicos datosTecnicos = objFactory.createDatosEspecificosDatosTecnicos();

		// Prueba blanco a número homologacion si valen null:
		if(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getNumHomologacion()==null){
			datosTecnicos.setNumeroHomologacion("");
		}else{
			datosTecnicos.setNumeroHomologacion(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getNumHomologacion());
		}

		String codITV= tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCodItv();

		if (!SIN_CODIG.equals(codITV)) {
			datosTecnicos.setCodigoITV(codITV);
		}
		valor = tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCriterioConstruccionBean().getIdCriterioConstruccion() +
				tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCriterioUtilizacionBean().getIdCriterioUtilizacion();
		datosTecnicos.setTipoVehiculoIndustria(valor);

		if (tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getTipoVehiculoBean()!=null)
			valor = tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getTipoVehiculoBean().getTipoVehiculo();
		else valor=null;
		if(null != valor && !"".equals(valor.trim()) && !"-1".equals(valor.trim()) && TipoVehiculoDGT.contains(valor)){
			datosTecnicos.setTipoVehiculoDGT(valor);
		}

		MarcaDgtVO marca = getModeloTrafico().obtenerDescMarca(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMarcaBean().getCodigoMarca());
		if (marca!=null){
			datosTecnicos.setMarca(marca.getMarca());
		}
		datosTecnicos.setModelo(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getModelo());

		valor = (null!=tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getColorBean().getValorEnum() ?
				tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getColorBean().getValorEnum() : "");

		if(null != valor && !"".equals(valor.trim())){

			if ("-".equals(valor))
				datosTecnicos.setColor("");
			else {
				datosTecnicos.setColor(valor);
			}
		}
		if(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().
				getTipoItv() != null && !tramiteTraficoMatriculacionBean.getTramiteTraficoBean().
				getVehiculo().getTipoItv().equals("")){
			datosTecnicos.setTipo(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().
					getVehiculo().getTipoItv());
		}

		// Variante:
		if(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().
				getVariante() != null && !tramiteTraficoMatriculacionBean.getTramiteTraficoBean().
				getVehiculo().getVariante().equals("")){
			datosTecnicos.setVariante(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().
					getVehiculo().getVariante());
		}

		// Version:
		if(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().
				getVersion() != null && !tramiteTraficoMatriculacionBean.getTramiteTraficoBean().
				getVehiculo().getVersion().equals("")){
			datosTecnicos.setVersion(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().
					getVehiculo().getVersion());
		}

		// MATE 2.5

		// Procedencia si se trata de vehículo usado e importado.
		if(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getProcedencia() != null &&
				!tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getProcedencia().equals("") &&
				!tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getProcedencia().equals("-1")&&
				tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getVehiUsado() != null &&
				tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getVehiUsado().equals("true") &&
				tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getImportado() != null &&
				tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getImportado()){
			datosTecnicos.setProcedencia(new BigInteger(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getProcedencia()));
		}

		// Matrícula origen Extranjero , Sólo se envia si el tipo de matriculación es 8,Rematricular.

	// Si está habilitada la maatriculación extranjera.
	/* if ("SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("habilitar.matw.matriculacionExtranjera"))) {
		if (tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo() != null &&
				tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMatriculaOrigExtr() != null &&
				!tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMatriculaOrigExtr().equals("") &&
				TipoTramiteMatriculacion.REMATRICULAR.equals(tramiteTraficoMatriculacionBean.getTipoTramiteMatriculacion())){

			datosTecnicos.setMatriculaOrigenExtranjero(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMatriculaOrigExtr());
		}

	}*/

		// Fabricante:
		if(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().
				getFabricante() != null && 
				!tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().
				getFabricante().equals("")){

			datosTecnicos.setFabricante(tramiteTraficoMatriculacionBean.getTramiteTraficoBean()
					.getVehiculo().getFabricante());
		}

		//Tipo tarjeta ITV
		if (tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getTipoTarjetaItvBean().getIdTipoTarjetaItv()!=null &&
				!tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getTipoTarjetaItvBean().getIdTipoTarjetaItv().isEmpty()){
			datosTecnicos.setTipoTarjetaItv(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getTipoTarjetaItvBean().getIdTipoTarjetaItv());
		}

		// FIN MATE 2.5

		datosTecnicos.setCaracteristicas(completaCaracteristicasVehiculo(tramiteTraficoMatriculacionBean));

		// MATW. País de fabricación 
		if(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getPaisFabricacionBean() != null){
			datosTecnicos.setPaisFabricacion(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getPaisFabricacionBean().getIdPaisFabricacion());
		}

		// VEHÍCULOS MULTIFÁSICOS
		if (tramiteTraficoMatriculacionBean != null 
				&& tramiteTraficoMatriculacionBean.getTramiteTraficoBean() != null
				&& tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo() != null) {

			// Marca base
			if (tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMarcaBaseBean() != null
					&& tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMarcaBaseBean().getCodigoMarca() != null) {
				MarcaDgtVO marcaBase = getModeloTrafico().obtenerDescMarca(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMarcaBaseBean().getCodigoMarca());
				datosTecnicos.setMarcaBase(marcaBase != null ? marcaBase.getMarca() : null);
			}

			// Fabricante base
			if(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getFabricanteBase() != null
					&& !tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getFabricanteBase().isEmpty()){
				datosTecnicos.setFabricanteBase(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getFabricanteBase());
			}

			// Tipo base
			if(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getTipoItvBase() != null
					&& !tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getTipoItvBase().isEmpty()){
				datosTecnicos.setTipoBase(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getTipoItvBase());
			}

			// Variante base
			if(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getVarianteBase() != null
					&& !tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getVarianteBase().isEmpty()){
				datosTecnicos.setVarianteBase(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getVarianteBase());
			}

			// Versión base
			if(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getVersionBase() != null
					&& !tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getVersionBase().isEmpty()){
				datosTecnicos.setVersionBase(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getVersionBase());
			}

			// Homologación base; prueba blanco a número homologación si valen null:
			if(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getNumHomologacionBase()!=null &&
					!tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getNumHomologacionBase().isEmpty()){
				datosTecnicos.setNumeroHomologacionBase(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getNumHomologacionBase());
			}

		}

		return datosTecnicos;
	}

	private Caracteristicas completaCaracteristicasVehiculo(TramiteTraficoMatriculacionBean tramiteTraficoMatriculacionBean) {
		String valor = null;
		BigDecimal valorBigDec = new BigDecimal(0);
		ObjectFactory objFactory = new ObjectFactory();

		Caracteristicas vehiculo = objFactory.createDatosEspecificosDatosTecnicosCaracteristicas();

		if(null != tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getPotenciaFiscal() && !"".equals(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getPotenciaFiscal().toString().trim())){
			valor = tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getPotenciaFiscal().toString();
			vehiculo.setPotenciaFiscal(new BigDecimal(valor).setScale(2, BigDecimal.ROUND_DOWN));
		} else {
			vehiculo.setPotenciaFiscal(null);
		}

		if(null != tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCilindrada() && !"".equals(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCilindrada().trim())){
			valor = tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCilindrada();
			vehiculo.setCilindrada(new BigDecimal(valor).setScale(2, BigDecimal.ROUND_DOWN));
		} else if(null==tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCilindrada() || "".equals(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCilindrada().trim())){
			vehiculo.setCilindrada(null);
		} else {
			vehiculo.setCilindrada(new BigDecimal(0));
		}

		if(null != tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getTara()
				&& !equals(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getTara().isEmpty())
				&& Integer.parseInt(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getTara()) > 0){
			vehiculo.setTara(new BigInteger(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getTara()));
		}

		if(null != tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getPesoMma() && !"".equals(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getPesoMma().trim())){
			valor = tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getPesoMma();
			vehiculo.setMasaMaximaAutorizada(new BigInteger(valor));
		} else {
			vehiculo.setMasaMaximaAutorizada(new BigInteger(_0));
		}

		if(null != tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getPlazas() && !"".equals(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getPlazas().toString().trim())){
			valor = tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getPlazas().toString();
			vehiculo.setNumeroMaximoPlazas(new BigInteger(valor));
		} else {
			vehiculo.setNumeroMaximoPlazas(new BigInteger(_0));
		}

		if(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCarburanteBean() != null){
			vehiculo.setTipoCarburante(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCarburanteBean().getIdCarburante());
		}

		if(null != tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getPotenciaPeso() && !"".equals(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getPotenciaPeso())){
			valorBigDec = tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getPotenciaPeso();
			vehiculo.setRelacionPotenciaPeso(valorBigDec.setScale(4, BigDecimal.ROUND_DOWN));
		} else {
			vehiculo.setRelacionPotenciaPeso(new BigDecimal(_0).setScale(4, BigDecimal.ROUND_DOWN));
		}

		if(null != tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getNumPlazasPie() && !"".equals(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getNumPlazasPie())){
			valorBigDec = tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getNumPlazasPie();
			vehiculo.setNumeroPlazasDePie(valorBigDec.toBigInteger());
		} else {
			vehiculo.setNumeroPlazasDePie(new BigInteger(_0));
		}

		if(null != tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCo2() &&
				!"".equals(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCo2()) &&
				tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCarburanteBean() != null &&
				!"E".equals(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCarburanteBean().getIdCarburante())){

			valor = tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCo2();
			// MATW. Por si se recuperan valores anteriores a matw con más de tres decimales, setea el CO2 construyendo
			// el big decimal indicando como 3 el máximo número de decimales. No redondea.
			vehiculo.setCo2(new BigDecimal(valor).setScale(3, BigDecimal.ROUND_DOWN));
			/**
			 * Julio Molina
			 * Incidencia 2056. No enviamos la etiqueta del CO2 en el XML que se envia si el vehiculo es electrico.
			 */

		} else if(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCarburanteBean() != null &&
				"E".equals(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCarburanteBean().getIdCarburante())){
			//No se establece el parametro ya que no debe de ir en el XML
			valor = tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCo2();
			if (valor != null) {
				vehiculo.setCo2(new BigDecimal(valor).setScale(3, BigDecimal.ROUND_DOWN));
			}
			//vehiculo.setCo2(BigDecimal.ZERO);
		} else {
			vehiculo.setCo2(null);
		}

		// Masa en servicio (mom):
		if(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMom() != null &&
				!tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMom().equals("")){
			BigInteger intMasaServicio = new BigInteger(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMom().toString());
			vehiculo.setMasaEnServicio(intMasaServicio);
		}

		// MASA TÉCNICA ADMISIBLE
		if(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMtmaItv() != null &&
				!tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMtmaItv().equals("")){
			BigInteger intMasaTecnicaAdmisible = new BigInteger(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMtmaItv().toString());
			vehiculo.setMasaTecnicaAdmisible(intMasaTecnicaAdmisible);
		}
		
		if(null != tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getPotenciaNeta() && !"".equals(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getPotenciaNeta().toString().trim())){
			valor = tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getPotenciaNeta().toString();
			vehiculo.setPotenciaNetaMaxima(new BigDecimal(valor).setScale(3, BigDecimal.ROUND_DOWN));
		} else {
			vehiculo.setPotenciaNetaMaxima(new BigDecimal(0));
		}

		// MATE 2.5

		// Código eco:
		if(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCodigoEco() != null &&
				!tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCodigoEco().equals("")){
			vehiculo.setCodigoECO(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCodigoEco());
		}

		// Distancia entre ejes:
		if(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getDistanciaEntreEjes() != null &&
				!tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getDistanciaEntreEjes().equals("")){
			BigInteger intDistEjes = new BigInteger(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().
					getVehiculo().getDistanciaEntreEjes().toString());
			vehiculo.setDistanciaEjes(intDistEjes);
		}

		// Eco innovación:
		if(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getEcoInnovacion() != null &&
				!tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getEcoInnovacion().equals("")){
			if(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getEcoInnovacion().equals(TipoSINO.SI)){
				vehiculo.setEcoInnovacion(TipoSN.S.value());
			}else{
				vehiculo.setEcoInnovacion(TipoSN.N.value());
			}
		}

		if(null != tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getNivelEmisiones()
				&& !"".equals(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getNivelEmisiones())
				&& !"1".equals(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getNivelEmisiones())){
			valor = tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getNivelEmisiones().toUpperCase();
			vehiculo.setNivelEmisiones(valor);
		} else if(null != tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getNivelEmisiones()
				&& tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getNivelEmisiones().equals("1")
				&& tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getTipoTarjetaItvBean()!=null
				&& tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getTipoTarjetaItvBean().getIdTipoTarjetaItv().equals("D")) {
			valor = BasicText.changeSize("1", 6, '0', true);
			vehiculo.setNivelEmisiones(valor);
		} else {
			vehiculo.setNivelEmisiones("0");
		}

		// Reducción eco:
		if(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getReduccionEco() != null &&
				!tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getReduccionEco().equals("")){
			vehiculo.setReduccionEco(new BigInteger(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getReduccionEco().toString()));
		}

		// Vía anterior:
		if(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getViaAnterior() != null &&
				!tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getViaAnterior().equals("")){
			BigInteger intViaAnt = new BigInteger(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getViaAnterior().toString());
			vehiculo.setViaAnterior(intViaAnt);
		}

		// Vía posterior:
		if(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getViaPosterior() != null &&
				!tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getViaPosterior().equals("")){
			BigInteger intViaPos = new BigInteger(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getViaPosterior().toString());
			vehiculo.setViaPosterior(intViaPos);
		}

		// Consumo:
		if(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getConsumo() != null &&
				!tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getConsumo().equals("")){
			//String consumo = Utiles.rellenarCeros(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getConsumo().toString(),4);
			vehiculo.setConsumo(new BigInteger(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getConsumo().toString()));
		}

		// Carrocería:
		if(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCarroceriaBean() != null &&
				tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCarroceriaBean().getIdCarroceria() != null &&
				!tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCarroceriaBean().getIdCarroceria().equals("") &&
				!tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCarroceriaBean().getIdCarroceria().equals("-1")){

			vehiculo.setCarroceria(tramiteTraficoMatriculacionBean.getTramiteTraficoBean()
					.getVehiculo().getCarroceriaBean().getIdCarroceria());
		}

		// Categoría de homologación:
		if(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getHomologacionBean() != null &&
				tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getHomologacionBean().getIdHomologacion() != null &&
				!tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getHomologacionBean().getIdHomologacion().equals("") &&
				!tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getHomologacionBean().getIdHomologacion().equals("-1")){

			vehiculo.setCategoriaEu(tramiteTraficoMatriculacionBean.getTramiteTraficoBean()
					.getVehiculo().getHomologacionBean().getIdHomologacion());
		}

		// Tipo de alimentación
		if(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getAlimentacionBean() != null &&
				tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getAlimentacionBean().getIdAlimentacion() != null){
			vehiculo.setTipoAlimentacion(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getAlimentacionBean().getIdAlimentacion());
		}

		//VEHÍCULOS MULTIFÁSICOS
		// Masa en servicio base (mom base):
		if(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMomBase() != null){
			vehiculo.setMOMBase(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getMomBase().toBigInteger());
		}

		// Categoria electrica
		if (tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCategoriaElectricaBean() != null
				&& tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCategoriaElectricaBean().getIdCategoriaElectrica()!=null
				&& !tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCategoriaElectricaBean().getIdCategoriaElectrica().isEmpty()) {
			vehiculo.setCategoriaElectrica(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getCategoriaElectricaBean().getIdCategoriaElectrica());
		}

		// Autonomía eléctrica
		if (tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getAutonomiaElectrica()!=null) {
			vehiculo.setAutonomiaElectrica(tramiteTraficoMatriculacionBean.getTramiteTraficoBean().getVehiculo().getAutonomiaElectrica().toBigInteger());
		}

		return vehiculo;
	}

	private Tutor completaDatosTutor(Tutor tutor, TramiteTraficoMatriculacionBean traficoTramiteMatriculacionBean) {
		TipoDocumentoIdentificacion tipoDocumentoIdentificacionTutor = new TipoDocumentoIdentificacion();

		if (traficoTramiteMatriculacionBean.getRepresentanteTitularBean().getPersona().getNif()!=null
				&&!traficoTramiteMatriculacionBean.getRepresentanteTitularBean().getPersona().getNif().equals("")) {
			tipoDocumentoIdentificacionTutor.setNumero(traficoTramiteMatriculacionBean.getRepresentanteTitularBean().getPersona().getNif());
			tutor.setDocumentoIdentidad(tipoDocumentoIdentificacionTutor);

			TipoTutela tipoTutela = null;

			if(traficoTramiteMatriculacionBean.getRepresentanteTitularBean().getIdMotivoTutela() != null){
				tipoTutela = traficoTramiteMatriculacionBean.getRepresentanteTitularBean().getIdMotivoTutela();
				/**
				 * Inicio
				 * 20/12/13 JF, se incluye el valor de Menor Emancipado "E" como un nuevo
				 * valor posible del motivo de la tutela, se agrega el valor Menor Emancipado
				 */
					if(tipoTutela.equals(TipoTutela.MinoriaEdad)){
						tutor.setTipoTutela(TipoTutela.MinoriaEdad.toString());
					}else if (tipoTutela.equals(TipoTutela.MenorEmancipado)){
						tutor.setTipoTutela(TipoTutela.MenorEmancipado.toString());
					}else if (tipoTutela.equals(TipoTutela.Otros)){
						tutor.setTipoTutela(TipoTutela.Otros.toString());
					}
				/**
				 * Fin
				 */
			}

		}
		return tutor;
	}

	private TipoDatoTitular beanToXmlTipoDatoTitular(IntervinienteTrafico interviniente) {
		ObjectFactory objectFactory = new ObjectFactory();

		TipoDatoTitular tipoDatoTitular = new TipoDatoTitular();

		//DOCUMENTO DE IDENTIDAD
		TipoDocumentoIdentificacion tipoDocumentoIdentificacion = beanToXmlTipoDocumentoIdentificacion(interviniente, objectFactory);
		tipoDatoTitular.setDocumentoIdentidad(tipoDocumentoIdentificacion);

		//TIPODATONOMBRE
		TipoDatoNombre tipoDatoNombre = beanToXmlTipoDatoNombre(interviniente, objectFactory, tipoDatoTitular);
		tipoDatoTitular.setDatosNombre(tipoDatoNombre);

		//FECHA NACIMIENTO

		if (!interviniente.getPersona().getTipoPersona().equals(TipoPersona.Juridica)) {
			if(interviniente.getPersona().getFechaNacimientoBean()!=null && !interviniente.getPersona().getFechaNacimientoBean().getAnio().equals("") &&
					!interviniente.getPersona().getFechaNacimientoBean().getMes().equals("") && !interviniente.getPersona().getFechaNacimientoBean().getDia().equals(""))
				tipoDatoTitular.setFechaNacimiento(utilesFecha.invertirCadenaFechaSinSeparador(interviniente.getPersona().getFechaNacimientoBean().toString()));
		} else {
			tipoDatoTitular.setFechaNacimiento(null);
		}
		//SEXO
		tipoDatoTitular.setSexo(interviniente.getPersona().getSexo());

		// JMC NACIONALIDAD DESAPARECE DE LA VERSION INICIAL DE MATW
		//tipoDatoTitular.setNacionalidad(interviniente.getPersona().getNacionalidad());

		return tipoDatoTitular;
	}

	private TipoDatoNombre beanToXmlTipoDatoNombre(IntervinienteTrafico titular, ObjectFactory objectFactory, TipoDatoTitular tipoDatoTitular) {
		TipoDatoNombre tipoDatoNombre = new TipoDatoNombre();

		if(null == titular.getPersona().getNombre() || "".equals(titular.getPersona().getNombre())){
			if (titular.getPersona().getApellido1RazonSocial().length()>=70)
				tipoDatoNombre.setRazonSocial(titular.getPersona().getApellido1RazonSocial().substring(0, 70));
			else tipoDatoNombre.setRazonSocial(titular.getPersona().getApellido1RazonSocial());
			tipoDatoNombre.setNombre("");
			tipoDatoNombre.setPrimerApellido("");
			tipoDatoNombre.setSegundoApellido("");
		}else{
			tipoDatoNombre.setRazonSocial("");
			tipoDatoNombre.setNombre(titular.getPersona().getNombre());
			if (titular.getPersona().getApellido1RazonSocial().length()>=26)
				tipoDatoNombre.setPrimerApellido(titular.getPersona().getApellido1RazonSocial().substring(0, 26));
			else tipoDatoNombre.setPrimerApellido(titular.getPersona().getApellido1RazonSocial());
			tipoDatoNombre.setSegundoApellido(titular.getPersona().getApellido2());
		}

		tipoDatoTitular.setDatosNombre(tipoDatoNombre);

		return tipoDatoNombre;
	}

	private TipoDocumentoIdentificacion beanToXmlTipoDocumentoIdentificacion(IntervinienteTrafico interviniente, ObjectFactory objectFactory) {
		TipoDocumentoIdentificacion tipoDocumentoIdentificacion = new TipoDocumentoIdentificacion();

		if (interviniente.getPersona().getNif()!=null && !interviniente.getPersona().getNif().equals(""))
			tipoDocumentoIdentificacion.setNumero(interviniente.getPersona().getNif());
		else tipoDocumentoIdentificacion.setNumero(null);
		return tipoDocumentoIdentificacion;
	}

	private TipoDatoDireccion beanToXmldireccion(Direccion direccion) {
		//TIPO DATO DIRECCION
		TipoDatoDireccion tipoDatoDireccion = new TipoDatoDireccion();

		if (direccion.getBloque()!=null && !"".equals(direccion.getPortal())) {
			tipoDatoDireccion.setBloque(direccion.getBloque());
		}else {
			tipoDatoDireccion.setBloque("");
		}

		if(direccion.getPuerta()!= null && !"".equals(direccion.getPuerta())){
			tipoDatoDireccion.setPortal(direccion.getPuerta());
		} else {
			tipoDatoDireccion.setPortal("");
		}

		if(direccion.getCodPostalCorreos()!= null && !"".equals(direccion.getCodPostalCorreos())) {
			tipoDatoDireccion.setCodigoPostal(new BigInteger(direccion.getCodPostalCorreos()));
		} else tipoDatoDireccion.setCodigoPostal(new BigInteger("0"));

		if (direccion.getEscalera()!=null && !"".equals(direccion.getEscalera()))
			tipoDatoDireccion.setEscalera(direccion.getEscalera());
		else tipoDatoDireccion.setEscalera("");

		if (direccion.getHm()!=null && !"".equals(direccion.getHm()))
			tipoDatoDireccion.setHectometro(new BigInteger(direccion.getHm()));

		if (direccion.getPuntoKilometrico()!=null && !"".equals(direccion.getPuntoKilometrico()))
			tipoDatoDireccion.setKilometro(new BigInteger(direccion.getPuntoKilometrico()));

		if (direccion.getPuebloCorreos()!=null && !"".equals(direccion.getPuebloCorreos()))
			tipoDatoDireccion.setLocalidad(direccion.getPuebloCorreos());
		else tipoDatoDireccion.setLocalidad("");

		if (direccion.getMunicipio()!=null && !"".equals(direccion.getMunicipio()))
			if (direccion.getMunicipio().getIdMunicipio()!=null)
				tipoDatoDireccion.setMunicipio(direccion.getMunicipio().getProvincia().getIdProvincia()+direccion.getMunicipio().getIdMunicipio());
			else tipoDatoDireccion.setMunicipio("");

		if (direccion.getNombreVia()!=null && !"".equals(direccion.getNombreVia())) {
			String nombreVia = utilesConversiones.ajustarCamposIne(direccion.getNombreVia());
			tipoDatoDireccion.setNombreVia(UtilesCadenaCaracteres.quitarCaracteresExtranios(nombreVia));
			//tipoDatoDireccion.setNombreVia(nombreVia);

		} else tipoDatoDireccion.setNombreVia("");

		if (direccion.getNumero()!=null && !"".equals(direccion.getNumero()))
			tipoDatoDireccion.setNumero(direccion.getNumero());
		else tipoDatoDireccion.setNumero("");

		if (direccion.getPlanta()!=null && !"".equals(direccion.getPlanta()))
			tipoDatoDireccion.setPlanta(direccion.getPlanta());
		else tipoDatoDireccion.setPlanta("");

		if (direccion!=null && direccion.getLetra()!=null) {
			tipoDatoDireccion.setPuerta(direccion.getLetra());
		} else tipoDatoDireccion.setPuerta("");

		tipoDatoDireccion.setPais(PAIS);

		if (direccion!=null && direccion.getMunicipio()!=null &&
			direccion.getMunicipio().getProvincia()!=null &&
				direccion.getMunicipio().getProvincia().getIdProvincia()!=null) {

			if (direccion.getMunicipio().getProvincia().getIdProvincia().equals("-1"))
				tipoDatoDireccion.setProvincia("");
			else {
				//jbc cambio matw para enviar en la provincia la sigla
				tipoDatoDireccion.setProvincia(utilesConversiones.getSiglasFromIdProvincia(direccion.getMunicipio().getProvincia().getIdProvincia()));
			}
		} else tipoDatoDireccion.setProvincia("");

		if (direccion!=null&&direccion.getTipoVia()!=null&&direccion.getTipoVia().getIdTipoVia()!=null) {
			if (direccion.getTipoVia().getIdTipoVia().equals("-1")) {
				tipoDatoDireccion.setTipoVia("");
			} else {
				// Comprobamos si el tipo de vía tiene una traducción entre las aceptadas por DGT (que para MATW sólo los tiene en castellano)
				String idTipoVia = null;
				TipoViaVO tipoViaVO = servicioTipoVia.getTipoVia(direccion.getTipoVia().getIdTipoVia());
				idTipoVia = tipoViaVO.getIdTipoViaDgt();
				if(idTipoVia!=null){
					tipoDatoDireccion.setTipoVia(idTipoVia);
				} else {
					tipoDatoDireccion.setTipoVia(direccion.getTipoVia().getIdTipoVia());
				}
			}
		}
		else tipoDatoDireccion.setTipoVia("");

		return tipoDatoDireccion;
	}

	public ModeloSolicitud getModeloSolicitud() {
		if (modeloSolicitud == null) {
			modeloSolicitud = new ModeloSolicitud();
		}
		return modeloSolicitud;
	}

	public void setModeloSolicitud(ModeloSolicitud modeloSolicitud) {
		this.modeloSolicitud = modeloSolicitud;
	}

	public ModeloInterviniente getModeloInterviniente() {
		if (modeloInterviniente == null) {
			modeloInterviniente = new ModeloInterviniente();
		}
		return modeloInterviniente;
	}

	public void setModeloInterviniente(ModeloInterviniente modeloInterviniente) {
		this.modeloInterviniente = modeloInterviniente;
	}

	public ModeloColegiado getModeloColegiado() {
		if (modeloColegiado == null) {
			modeloColegiado = new ModeloColegiado();
		}
		return modeloColegiado;
	}

	public void setModeloColegiado(ModeloColegiado modeloColegiado) {
		this.modeloColegiado = modeloColegiado;
	}

	public ModeloCreditosTrafico getModeloCreditosTrafico() {
		if (modeloCreditosTrafico == null) {
			modeloCreditosTrafico = new ModeloCreditosTrafico();
		}
		return modeloCreditosTrafico;
	}

	public void setModeloCreditosTrafico(ModeloCreditosTrafico modeloCreditosTrafico) {
		this.modeloCreditosTrafico = modeloCreditosTrafico;
	}

	public ModeloTrafico getModeloTrafico() {
		if(modeloTrafico == null){
			modeloTrafico = new ModeloTrafico();
		}
		return modeloTrafico;
	}

	public void setModeloTrafico(ModeloTrafico modeloTrafico) {
		this.modeloTrafico = modeloTrafico;
	}

	public ModeloMatriculacion getModeloMatriculacion() {
		if(modeloMatriculacion == null){
			modeloMatriculacion = new ModeloMatriculacion();
		}
		return modeloMatriculacion;
	}

	public void setModeloMatriculacion(ModeloMatriculacion modeloMatriculacion) {
		this.modeloMatriculacion = modeloMatriculacion;
	}

}