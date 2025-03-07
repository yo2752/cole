package org.gestoresmadrid.oegam2comun.tasas.model.service.impl;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.core.model.enumerados.SubTipoFicheros;
import org.gestoresmadrid.core.model.enumerados.TipoFicheros;
import org.gestoresmadrid.core.model.security.utils.Cryptographer;
import org.gestoresmadrid.oegam2comun.cola.model.service.ServicioCola;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.tasas.model.dto.RespuestaTasas;
import org.gestoresmadrid.oegam2comun.tasas.model.service.ServicioGeneracionEtiquetasTasas;
import org.gestoresmadrid.oegam2comun.tasas.model.service.ServicioTasa;
import org.gestoresmadrid.oegam2comun.tasas.view.beans.GeneracionEtiquetasBean;
import org.gestoresmadrid.oegam2comun.tasas.view.beans.Tasa;
import org.gestoresmadrid.oegam2comun.trafico.ficheros.temporales.model.service.ServicioFicherosTemporales;
import org.gestoresmadrid.oegamComun.tasa.view.dto.TasaDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import escrituras.beans.ResultBean;
import net.sf.jasperreports.engine.JRException;
import utilidades.estructuras.Fecha;
import utilidades.informes.ReportExporter;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.propiedades.PropertiesConstantes;
import utilidades.web.OegamExcepcion;
@Service
public class ServicioGeneracionEtiquetasTasasImpl implements ServicioGeneracionEtiquetasTasas, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1799453917295545976L;
	private static final String NOMBRE_HOST_SOLICITUD = "nombreHostSolicitud";
	private static final ILoggerOegam LOG = LoggerOegam.getLogger(ServicioGeneracionEtiquetasTasasImpl.class);

	@Autowired
	private GestorDocumentos gestorDocumentos;
	
	@Autowired
	private ServicioTasa servicioTasa;
	
	@Autowired
	private ServicioCola servicioCola; 
	
	@Autowired
	private ServicioFicherosTemporales servicioFicherosTemporales;
	
	@Autowired
	private ServicioContrato servicioContrato;
	
	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilesFecha utilesFecha;
	
	@Autowired
	private Cryptographer cryptographer;

	@Autowired
	Utiles utiles;

	/**
	 * Genera un informe en pdf con una plantilla .jasper y los datos provenientes de una coleccion de beans
	 * @param listaEtiquetas 
	 * @param idContrato 
	 * @param idUsuario 
	 * 
	 * @param 
	 * @return byteArray
	 */
	@Override
	public RespuestaTasas generarInformePdf(List<GeneracionEtiquetasBean> listaEtiquetas, String numColegiado, ColaBean cola) {
		RespuestaTasas result = null;
		BufferedImage barcodeImage;
		Collection<GeneracionEtiquetasBean> listaEtiquetasAux = new HashSet<GeneracionEtiquetasBean>();
		for(GeneracionEtiquetasBean etiquetaBean : listaEtiquetas){
			barcodeImage = null;
			try {
				barcodeImage = com.google.zxing.client.j2se.MatrixToImageWriter.toBufferedImage(
												new com.google.zxing.MultiFormatWriter().encode(String.valueOf(etiquetaBean.getNumeroTasa()), com.google.zxing.BarcodeFormat.CODE_39, 1200, 400));
			} catch (Exception e) {
				LOG.error("Error generando pdf ", e);
			}
			etiquetaBean.setCodigoBarras(barcodeImage);
			listaEtiquetasAux.add(etiquetaBean);
		}
		try {
			ReportExporter reportExporter = new ReportExporter();
			String ruta =  gestorPropiedades.valorPropertie(PropertiesConstantes.RUTA_DIRECTORIO_DATOS) + gestorPropiedades.valorPropertie(ServicioGeneracionEtiquetasTasas.RUTA_CARPETA);
			byte[] byteArray = reportExporter.generarInforme(ruta, gestorPropiedades.valorPropertie(ServicioGeneracionEtiquetasTasas.NOMBRE_INFORME), 
					ServicioGeneracionEtiquetasTasas.FORMATO, null, ServicioGeneracionEtiquetasTasas.TAG_CABECERA, null, listaEtiquetasAux);
			FicheroBean ficheroBean = guardarDocumento(byteArray, numColegiado, cola.getIdContrato());
			
			if(ficheroBean != null && ficheroBean.getFichero() != null){
				ResultBean resultFichTemp = servicioFicherosTemporales.altaFicheroTemporal(ficheroBean.getNombreDocumento(), ficheroBean.getExtension(),
						TipoFicheros.convertirNombre(ficheroBean.getTipoDocumento()),SubTipoFicheros.convertirNombre(ficheroBean.getSubTipo()),
						ficheroBean.getFecha(), numColegiado,cola.getIdUsuario(), cola.getIdContrato());
				if(resultFichTemp != null && resultFichTemp.getError()){
					result = new RespuestaTasas();
					result.setError(true);
					result.setMensajeError(resultFichTemp.getListaMensajes().get(0));
				}else{
					Long idFicheroTemporal = (Long) resultFichTemp.getAttachment("idFicheroTemporal");
					cola.setIdTramite(new BigDecimal(idFicheroTemporal));
				}
			}
			
		} catch (JRException e) {
			LOG.error("Error generando PDF", e);
			result = new RespuestaTasas();
			result.setException(e);
		} catch (ParserConfigurationException e) {
			LOG.error("Error generando PDF", e);
			result = new RespuestaTasas();
			result.setException(e);
		} catch (OegamExcepcion e) {
			LOG.error("Error generando PDF", e);
			result = new RespuestaTasas();
			result.setException(new Exception(e));
		}
		return result;
	}

	/**
	 * Guarda un array de bytes en un fichero pdf
	 * @param byteFinal
	 * @param idContrato 
	 * @return
	 * @throws OegamExcepcion 
	 */
	@Override
	public FicheroBean guardarDocumento(byte[] byteFinal, String numColegiado, BigDecimal idContrato) throws OegamExcepcion {
		// Se crea el fichero con los datos asociados. Recupera su contenido de un array de bytes
		FicheroBean fichero = new FicheroBean();
		try {
			fichero.setTipoDocumento(TipoFicheros.Tasas.toString());
			fichero.setSubTipo(SubTipoFicheros.TASAS_ETIQUETAS.toString());
			fichero.setFecha(utilesFecha.getFechaTimeStampConDate(Calendar.getInstance().getTime()));
			fichero.setNombreDocumento(generarNombreFichero(ServicioGeneracionEtiquetasTasas.NOMBRE_DOCUMENTO, numColegiado, idContrato));
			fichero.setSobreescribir(false);
			fichero.setFicheroByte(byteFinal);
			fichero.setExtension(ConstantesGestorFicheros.EXTENSION_PDF);
			
			fichero.setFichero(gestorDocumentos.guardarByte(fichero));
		} catch (OegamExcepcion e) {
			LOG.error("Error guardando el fichero", e);
			throw new OegamExcepcion(ConstantesGestorFicheros.ERROR_GUARDAR_ARCHIVO);
		} catch (Exception e) {
			LOG.error("Error guardando el fichero", e);
			throw new OegamExcepcion(ConstantesGestorFicheros.ERROR_GUARDAR_ARCHIVO);
		}
		return fichero;
	}
	
	/**
	 * Genera el nombre de los archivos a guardar
	 * @param raiz
	 * @param numColegiado
	 * @param idContrato 
	 * @return
	 * @throws Exception
	 */
	public String generarNombreFichero(String raiz, String numColegiado, BigDecimal idContrato) throws Exception{
		Fecha fecha = utilesFecha.getFechaTimeStampConDate(Calendar.getInstance().getTime());
		Date fin = new Date();
		utilesFecha.setHoraEnDate(fin, ServicioGeneracionEtiquetasTasas.horaFInDia);
		utilesFecha.setSegundosEnDate(fin, ServicioGeneracionEtiquetasTasas.N_SEGUNDOS);
		
		String nombreFich = raiz + numColegiado + "_" + idContrato + "_" + fecha.getDia() + fecha.getMes() + fecha.getAnio() + 
								fecha.getHora() + fecha.getMinutos() + fecha.getSegundos();
		return nombreFich;
	}
	
	/**
	 * Guardar el fichero con el bean de las etiquetas en la carpeta temporal
	 * @param listaEtiquetasBean
	 * @param numColegiado
	 * @param idcontrato 
	 * @return
	 */
	public String guardarFicheroEtiquetasBean(List<GeneracionEtiquetasBean> listaEtiquetasBean, ContratoVO contratoVO) {
		FicheroBean fichero = new FicheroBean();
		String nombreFichGuardado = null;
		try {
			fichero.setTipoDocumento(TipoFicheros.Tasas.toString());
			fichero.setSubTipo(SubTipoFicheros.TASAS_ETIQUETAS_TEMP.toString());
			fichero.setFecha(utilesFecha.getFechaTimeStampConDate(Calendar.getInstance().getTime()));
			fichero.setNombreDocumento(generarNombreFichero(ServicioGeneracionEtiquetasTasas.NOMBRE_DOCUMENTO_TEMP, contratoVO.getColegiado().getNumColegiado(), new BigDecimal(contratoVO
					.getIdContrato())));
			fichero.setSobreescribir(false);
			fichero.setExtension(ConstantesGestorFicheros.EXTENSION_DATA);

			String json = new Gson().toJson(listaEtiquetasBean, List.class);

			String listaEtiquetasBeanEncriptada = cryptographer.encrypt(json);

			nombreFichGuardado = gestorDocumentos.guardarFicheroSerializable(fichero, listaEtiquetasBeanEncriptada);
		} catch (Exception e) {
			LOG.error("Error creando el fichero temporal para la creación de las tasas, e: ", e);
		} catch (OegamExcepcion e) {
			LOG.error("Error creando el fichero temporal para la creación de las tasas, e: ", e);
		}
		return nombreFichGuardado;
	}
	
	/**
	 * Obtiene una lista con en los Bean de las etiquetas guardados en un xml en la carpeta temporal
	 * @param nombreFich
	 * @return
	 */
	@Override
	public List<GeneracionEtiquetasBean> obtenerListaTempEtiquetasBean(String nombreFich) {
		String[] descNombreFichero = nombreFich.split("_");
		Fecha fecha = utilesFecha.getFechaConDate(utilesFecha.getFechaHoraDate( descNombreFichero[4]));
		List<GeneracionEtiquetasBean> listaEtiquetas = null;
		try {
			FileResultBean fileResult = gestorDocumentos.buscarFicheroPorNombreTipo(TipoFicheros.Tasas.toString(),SubTipoFicheros.TASAS_ETIQUETAS_TEMP.toString(),
					fecha,nombreFich, ConstantesGestorFicheros.EXTENSION_DATA);
			
			if(fileResult != null && fileResult.getFile() != null){
				String listaEtiquetasBeanEncriptada = (String) gestorDocumentos.obtenerCollectionSerializable(fileResult.getFile());
				String listaEtiquetasBean = cryptographer.decrypt(listaEtiquetasBeanEncriptada);
				
				Gson gson = new Gson();
				Type type = new TypeToken<List<GeneracionEtiquetasBean>>(){}.getType();
				listaEtiquetas = gson.fromJson(listaEtiquetasBean, type);
				
				//listaEtiquetas = (List<GeneracionEtiquetasBean>) gestorDocumentos.obtenerCollectionSerializable(fileResult.getFile());
			}
		} catch (OegamExcepcion e) {
			LOG.error("Error creando la coleccion Serializable de las etiquetas, e: " ,e);
		}
		return listaEtiquetas;
	}
	
	@Override
	public RespuestaTasas generarTasasPegatinasProceso(ColaBean colaBean) {
		RespuestaTasas result = null;
		List<GeneracionEtiquetasBean> listaGenEtiquetasBean = obtenerListaTempEtiquetasBean(colaBean.getXmlEnviar());
		if(listaGenEtiquetasBean != null){
			String[] nomXml = colaBean.getXmlEnviar().split("_");
			String numColegiado = nomXml[2].substring(0, 4);
			result = generarInformePdf(listaGenEtiquetasBean, numColegiado, colaBean);
		}else{
			result = new RespuestaTasas(true, "Ha surgido un error a la hora de obtener las tasas de pegatinas.");
		}
		return result;
	}
	
	@Override
	public ResultBean generarListadoEtiquetas(String[] numTasas) {
		ResultBean resultado = new ResultBean();
		BigDecimal contratoAux = null;
		List<GeneracionEtiquetasBean> listaGenEtiquetasBean = null;
		List<String> listaErrores = new ArrayList<String>();;
		GeneracionEtiquetasBean genEtiquetasBean = null;
		boolean errorContrato = false;
		if(numTasas != null){
			List<TasaDto> listaTasasDto = servicioTasa.getListaTasasPegatinasPorCodigos(Arrays.asList(numTasas));
			if(listaTasasDto != null){
				listaGenEtiquetasBean = new ArrayList<GeneracionEtiquetasBean>();
				for(TasaDto tasaDto : listaTasasDto){
					if(contratoAux == null){
						contratoAux = tasaDto.getContrato().getIdContrato();
					}
					if(tasaDto.getFechaAsignacion() != null){
						listaErrores.add("La tasa: " + tasaDto.getCodigoTasa() + ", tiene fecha de asignación, debera desasignarla primero para poder generarla.");
					}else if(!tasaDto.getContrato().getIdContrato().equals(contratoAux)){
						errorContrato = true;
						break;
					}else{
						genEtiquetasBean = new GeneracionEtiquetasBean();
						genEtiquetasBean.setNumeroTasa(tasaDto.getCodigoTasa());
						genEtiquetasBean.setTipoTasa(tasaDto.getTipoTasa());
						if(tasaDto.getPrecio().toString().contentEquals(",")){
							genEtiquetasBean.setImporteTasa(utiles.formatDoubleToString("0.00",tasaDto.getPrecio().doubleValue()));
						}else{
							genEtiquetasBean.setImporteTasa(tasaDto.getPrecio().toString());
						}
						genEtiquetasBean.setFechaOperacion(utilesFecha.convertirFecha(tasaDto.getFechaAlta()));
						genEtiquetasBean.setFormaPago(ServicioGeneracionEtiquetasTasas.FORMA_PAGO_TASAS);
						genEtiquetasBean.setJefatura(ServicioGeneracionEtiquetasTasas.JEFATURA_TASAS);
						listaGenEtiquetasBean.add(genEtiquetasBean);
					}
				}
				if(!errorContrato){
					resultado.addAttachment("listaGeneracionEtiquetas", listaGenEtiquetasBean);
					resultado.addAttachment("idContrato", contratoAux);
				}else{
					resultado.setError(true);
					resultado.setMensaje("No se pueden generar las tasas porque no todas corresponden al mismo contrato.");
				}
			}else{
				resultado.setError(true);
				resultado.setMensaje("No existen datos para las tasas seleccionadas.");
			}
		}else{
			resultado.setError(true);
			resultado.setMensaje("Debe seleccionar alguna tasa para poder generar.");
		}
		resultado.addAttachment("listaErrores", listaErrores);
		return resultado;
	}
	
	@Override
	public ResultBean crearDocumentoSolicitudColaPegatinas(List<GeneracionEtiquetasBean> listaGeneEtiquetasBean, BigDecimal contrato, BigDecimal idUsuario) {
		ResultBean result = null;
		ContratoVO contratoVO = servicioContrato.getContrato(contrato);
		String nombreFich = guardarFicheroEtiquetasBean(listaGeneEtiquetasBean, contratoVO);
		if(nombreFich != null){
			try {
				result = servicioCola.crearSolicitud(ProcesosEnum.EMISIONTASAETIQUETAS.getNombreEnum(), nombreFich, gestorPropiedades.valorPropertie(NOMBRE_HOST_SOLICITUD), 
						"N/A", null, idUsuario, null, contrato);
			} catch (OegamExcepcion e) {
				LOG.error("Error a la hora de encolar la solicitud para encolar las tasas de pegatinas, erorr: ", e);
				result = new ResultBean(true, "Ha surgido un error a la hora de generar las tasas de pegatinas");
			}
		}else{
			result = new ResultBean(true, "Ha surgido un error a la hora de generar las tasas de pegatinas");
		}
		return result;
	}

	@Override
	public GeneracionEtiquetasBean generarEtiquetasBean(Tasa t) {
		GeneracionEtiquetasBean generacionEtiquetasBean = new GeneracionEtiquetasBean();
		generacionEtiquetasBean.setNumeroTasa(t.getCodigoTasa());
		generacionEtiquetasBean.setTipoTasa(t.getTipoTasa());
		generacionEtiquetasBean.setFechaOperacion(t.getFechaAlta());
		if(t.getPrecio().toString().contentEquals(",")){
			generacionEtiquetasBean.setImporteTasa(utiles.formatDoubleToString("0.00",t.getPrecio().doubleValue()));
		}else{
			generacionEtiquetasBean.setImporteTasa(t.getPrecio().toString());
		}
		generacionEtiquetasBean.setFormaPago(ServicioGeneracionEtiquetasTasas.FORMA_PAGO_TASAS);
		generacionEtiquetasBean.setJefatura(ServicioGeneracionEtiquetasTasas.JEFATURA_TASAS);
		return generacionEtiquetasBean;
	}
	
	@Override
	public ResultBean comprobarDatosObligatoriosGeneracionTasasEtiquetas(String idCodigoTasaElectronica, String idCodigoTasaPegatina) {
		ResultBean resultado = null;
		if(idCodigoTasaElectronica != null){
			resultado = new ResultBean(true, "Actualmente, las tasas electrï¿½nicas no se pueden generar en pdf, ï¿½nicamente las tasas de pegatinas.");
		}else if(idCodigoTasaPegatina == null || idCodigoTasaPegatina.isEmpty()){
			resultado = new ResultBean(true,"Debe seleccionar alguna tasa.");
		}
		return resultado;
	}
	
	@Override
	public void borrarDocTasasPegatinas(String nombreFich, String tipo, String subTipo) {
		String[] descNombreFichero = nombreFich.split("_");
		String fechaHora = descNombreFichero[4];
		Fecha fecha = utilesFecha.getFechaConDate(utilesFecha.getFechaHoraDate(fechaHora));
		try {
			gestorDocumentos.borraFicheroSiExiste(tipo, subTipo, fecha, nombreFich);
		} catch (OegamExcepcion e) {
			LOG.error("Error a la hora de borrar el fichero de tasas generado", e);
		}
	}
	
	public ServicioTasa getServicioTasa() {
		return servicioTasa;
	}

	public void setServicioTasa(ServicioTasa servicioTasa) {
		this.servicioTasa = servicioTasa;
	}

	public ServicioCola getServicioCola() {
		return servicioCola;
	}

	public void setServicioCola(ServicioCola servicioCola) {
		this.servicioCola = servicioCola;
	}

	public ServicioFicherosTemporales getServicioFicherosTemporales() {
		return servicioFicherosTemporales;
	}

	public void setServicioFicherosTemporales(
			ServicioFicherosTemporales servicioFicherosTemporales) {
		this.servicioFicherosTemporales = servicioFicherosTemporales;
	}

	public ServicioContrato getServicioContrato() {
		return servicioContrato;
	}

	public void setServicioContrato(ServicioContrato servicioContrato) {
		this.servicioContrato = servicioContrato;
	}
	
}
