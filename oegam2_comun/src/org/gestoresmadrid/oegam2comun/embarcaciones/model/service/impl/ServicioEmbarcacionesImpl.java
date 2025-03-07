package org.gestoresmadrid.oegam2comun.embarcaciones.model.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.embarcaciones.model.service.ServicioEmbarcaciones;
import org.gestoresmadrid.oegam2comun.embarcaciones.view.dto.EmbarcacionDto;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersona;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import escrituras.beans.ResultBean;
import trafico.beans.utiles.CampoPdfBean;
import trafico.servicio.interfaz.ServicioImprimirTraficoInt;
import trafico.utiles.ConstantesPDF;
import trafico.utiles.PdfMaker;
import trafico.utiles.UtilResources;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioEmbarcacionesImpl implements ServicioEmbarcaciones {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6056865270038437203L;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioEmbarcacionesImpl.class);

	@Autowired
	private ServicioImprimirTraficoInt servicioImprimir;

	@Autowired
	private ServicioPersona servicioPersona;
	
	@Autowired
	private ServicioContrato servicioContrato;

	@Autowired
	private GestorDocumentos gestorDocumentos;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	private UtilesColegiado utilesColegiado;
	
	private static final String RUTA = "/trafico/plantillasPDF/PlantillaNRE06.pdf";
	
	//CONSTANTES CAMPOS PDF
	private static final String TIPO_EXENCION = "Cuadro combinado2";
	private static final String NIF_TITULAR = "nif titular";
	private static final String APELLIDOS_TITULAR = "apellidos titular";
	private static final String NOMBRE_TITULAR = "nombre titular";
	private static final String NIF_COLEGIADO = "nif colegiado";
	private static final String APELLIDOS_COLEGIADO = "apellidos Colegiado";
	private static final String NOMBRE_COLEGIADO = "Nombre colegiado";
	private static final String FECHA_PRESENTACION_DIA = "dia";
	private static final String FECHA_PRESENTACION_MES = "mes";
	private static final String FECHA_PRESENTACION_ANIO = "año";
	private static final String NUMERO_EXPEDIENTE = "número exp OEgAM";
	private static final String NUMERO_BASTIDOR = "Bastidor";

	public ResultBean buscarPersona(String nif, String numColegiado) {
		return servicioPersona.buscarPersona(nif, utilesColegiado.getNumColegiadoSession());
	}

	public ResultBean generarSolicitudNRE06(EmbarcacionDto embarcacionDto, BigDecimal idContrato, String _numExpediente) {
		log.info("Generando solicitud NRE08 embarcaciones");
		ResultBean result = new ResultBean();
		Fecha fechaHoraActual = utilesFecha.getFechaHoraActual();
		ContratoDto contrato = servicioContrato.getContratoDto(idContrato);
		StringBuilder numExpediente = new StringBuilder();
		numExpediente
			.append(contrato.getColegiadoDto().getNumColegiado())
			.append(fechaHoraActual.getDia())
			.append(fechaHoraActual.getMes())
			.append(fechaHoraActual.getAnio())
			.append(fechaHoraActual.getHora())
			.append(fechaHoraActual.getMinutos())
			.append(fechaHoraActual.getSegundos());
		if(_numExpediente != null && !_numExpediente.isEmpty()){
			numExpediente = new StringBuilder(_numExpediente);
		}
		/* Genero el documento */
		result = generarSolicitud(numExpediente.toString(),embarcacionDto,contrato);

		// Con el documento generado, realizamos el resto de acciones asociadas.
		byte[] archivo = null;
		if (result != null && !result.getError()) {
			archivo = (byte[]) result.getAttachment(ResultBean.TIPO_PDF);
			String nombreArchivo = "NRE06_" + numExpediente;
			boolean guardado = false;
			if (archivo != null) {
				try {
					File fichero =  gestorDocumentos.guardarFichero(ConstantesGestorFicheros.EMBARCACIONES, 
							ConstantesGestorFicheros.PDF_EMBARCACIONES, fechaHoraActual, nombreArchivo, ConstantesGestorFicheros.EXTENSION_PDF, archivo);
					if(fichero != null){
						guardado = true;
					}
				} catch (OegamExcepcion e) {
					log.error("Ha sucedido un error a la hora de guardar el pdf de embarcaciones, error:",e);
					result.addError("Ha sucedido un error a la hora de guardar el pdf de embarcaciones.");
					result.setError(true);
				}
			}
			if (archivo == null || !guardado) {
				result.addError("Existen problemas al imprimir. Inténtelo más tarde");
				result.setError(true);
				log.error("Problemas al imprimir solicitud NRE06");
			} else {
				result.addMensajeALista("La impresión se realizó correctamente");
				result.addAttachment("numExpediente", numExpediente.toString());
				log.info("Se ha generado correctamente los impreso de tipo " + nombreArchivo + " para los trámites " + numExpediente + " solicitados por " + utilesColegiado.getApellidosNombreUsuario());
			}
		}
		return result;
	}

	public ResultBean generarSolicitud(String numExpediente, EmbarcacionDto embarcacionDto, ContratoDto contrato){
		int[] vectPags = new int[ConstantesPDF._1];
		ArrayList<CampoPdfBean> camposFormateados = new ArrayList<CampoPdfBean>();
		ResultBean resultadoMetodo = new ResultBean();
		PdfMaker pdf = new PdfMaker();
		vectPags[0] = ConstantesPDF._1;
		
		//Abro la plantilla del PDF que corresponda
		UtilResources util = new UtilResources();
		String file = util.getFilePath(RUTA);
		byte[] byte1 = pdf.abrirPdf(file);
		Set<String> camposPlantilla = pdf.getAllFields(byte1);

		//Proceso el PDF - Estas validaciones solo se pueden hacer cuando ya se tiene el detalle del tramite
		String tipoExencion = "NS8";
		if(camposPlantilla.contains(TIPO_EXENCION)) {
			String campo = tipoExencion;
			CampoPdfBean campoAux = new CampoPdfBean(TIPO_EXENCION, campo, false, false, ConstantesPDF._12);
			camposFormateados.add(campoAux);
		}
		if(camposPlantilla.contains(NIF_TITULAR)) {
			String campo = embarcacionDto.getTitular().getNif();
			CampoPdfBean campoAux = new CampoPdfBean(NIF_TITULAR, campo, false, false, ConstantesPDF._12);
			camposFormateados.add(campoAux);
		}

		if(camposPlantilla.contains(APELLIDOS_TITULAR)) {
			String campo = embarcacionDto.getTitular().getApellido1RazonSocial();
			CampoPdfBean campoAux = new CampoPdfBean(APELLIDOS_TITULAR, campo, false, false, ConstantesPDF._12);
			camposFormateados.add(campoAux);
		}

		if(camposPlantilla.contains(NOMBRE_TITULAR)) {
			String campo = embarcacionDto.getTitular().getNombre();
			CampoPdfBean campoAux = new CampoPdfBean(NOMBRE_TITULAR, campo, false, false, ConstantesPDF._12);
			camposFormateados.add(campoAux);
		}
		if(camposPlantilla.contains(NIF_COLEGIADO)) {
			String campo = contrato.getColegiadoDto().getUsuario().getNif();
			CampoPdfBean campoAux = new CampoPdfBean(NIF_COLEGIADO, campo, false, false, ConstantesPDF._12);
			camposFormateados.add(campoAux);
		}

		if(camposPlantilla.contains(APELLIDOS_COLEGIADO)) {
			String[] nombre = contrato.getColegiadoDto().getUsuario().getApellidosNombre().split(",");
			String campo = "";
			if (nombre.length > 0)
				campo = nombre[0];
			CampoPdfBean campoAux = new CampoPdfBean(APELLIDOS_COLEGIADO, campo, false, false, ConstantesPDF._12);
			camposFormateados.add(campoAux);
		}

		if(camposPlantilla.contains(NOMBRE_COLEGIADO)) {
			String[] nombre = contrato.getColegiadoDto().getUsuario().getApellidosNombre().split(",");
			String campo = "";
			if (nombre.length > 1)
				campo = nombre[1];
			CampoPdfBean campoAux = new CampoPdfBean(NOMBRE_COLEGIADO, campo, false, false, ConstantesPDF._12);
			camposFormateados.add(campoAux);
		}
		//Resto del PDF
		Fecha fecha = utilesFecha.getFechaFracionada(new Date());
		
		if(camposPlantilla.contains(FECHA_PRESENTACION_DIA)) {
			String campo = fecha.getDia();
			CampoPdfBean campoAux = new CampoPdfBean(FECHA_PRESENTACION_DIA, campo, false, false, ConstantesPDF._12);
			camposFormateados.add(campoAux);
		}
		
		if(camposPlantilla.contains(FECHA_PRESENTACION_MES)) {
			String campo = fecha.getMes();
			CampoPdfBean campoAux = new CampoPdfBean(FECHA_PRESENTACION_MES, campo, false, false, ConstantesPDF._12);
			camposFormateados.add(campoAux);
		}
		
		if(camposPlantilla.contains(FECHA_PRESENTACION_ANIO)) {
			String campo = fecha.getAnio();
			CampoPdfBean campoAux = new CampoPdfBean(FECHA_PRESENTACION_ANIO, campo, false, false, ConstantesPDF._12);
			camposFormateados.add(campoAux);
		}
		if(camposPlantilla.contains(NUMERO_EXPEDIENTE)) {
			String campo = numExpediente;
			CampoPdfBean campoAux = new CampoPdfBean(NUMERO_EXPEDIENTE, campo, false, false, ConstantesPDF._12);
			camposFormateados.add(campoAux);
		}
		
		if(camposPlantilla.contains(NUMERO_BASTIDOR)) {
			String campo = embarcacionDto.getHin();
			CampoPdfBean campoAux = new CampoPdfBean(NUMERO_BASTIDOR, campo, false, false, ConstantesPDF._12);
			camposFormateados.add(campoAux);
		}
		
		byte1 = pdf.setCampos(byte1, camposFormateados);
		resultadoMetodo.setError(false);
		resultadoMetodo.addAttachment("pdf", byte1);  
			
		return resultadoMetodo;
	}

	public ResultBean generarTxt(EmbarcacionDto embarcacion, BigDecimal idContrato, String _numExpediente) {
		log.info("Generando txt solicitud NRE08 ");
		ResultBean resultado = new ResultBean();
		Fecha fechaHoraActual = utilesFecha.getFechaHoraActual();
		ContratoDto contrato = servicioContrato.getContratoDto(idContrato);
		StringBuilder numExpediente = new StringBuilder();
		numExpediente
			.append(contrato.getColegiadoDto().getNumColegiado())
			.append(fechaHoraActual.getDia())
			.append(fechaHoraActual.getMes())
			.append(fechaHoraActual.getAnio())
			.append(fechaHoraActual.getHora())
			.append(fechaHoraActual.getMinutos())
			.append(fechaHoraActual.getSegundos());
		if(_numExpediente != null && !_numExpediente.isEmpty()){
			numExpediente = new StringBuilder(_numExpediente);
		}
		/* Genero el documento */
		resultado = generarTxt(numExpediente.toString(),embarcacion,contrato);

		// Con el documento generado, realizamos el resto de acciones asociadas.
		byte[] archivo = null;
		if (resultado != null && !resultado.getError()) {
			archivo = (byte[]) resultado.getAttachment("txt");
			String nombreArchivo = "NRE06_" + numExpediente;
			boolean guardado = false;
			if (archivo != null) {
				try {
					File fichero =  gestorDocumentos.guardarFichero(ConstantesGestorFicheros.EMBARCACIONES, 
							ConstantesGestorFicheros.TXT_EMBARCACIONES, fechaHoraActual, nombreArchivo, ConstantesGestorFicheros.EXTENSION_TXT, archivo);
					if(fichero != null){
						guardado = true;
					}
				} catch (OegamExcepcion e) {
					log.error("Ha sucedido un error a la hora de guardar el txt de embarcaciones, error:",e);
					resultado.addError("Ha sucedido un error a la hora de guardar el txt de embarcaciones.");
					resultado.setError(true);
				}
			}
			if (archivo == null || !guardado) {
				resultado.addError("Existen problemas al imprimir. Inténtelo más tarde");
				resultado.setError(true);
				log.error("Problemas al imprimir el txt de la solicitud NRE06");
			} else {
				resultado.addMensajeALista("La impresión se realizó correctamente");
				resultado.addAttachment("numExpediente", numExpediente.toString());
				log.info("Se ha generado correctamente los impreso de tipo " + nombreArchivo + " para los trámites " + numExpediente + " solicitados por " + utilesColegiado.getApellidosNombreUsuario());
			}
		}
		return resultado;
	}

	public ResultBean generarTxt(String numExpediente, EmbarcacionDto embarcacion, ContratoDto contrato){
		ResultBean resultadoMetodo = new ResultBean();
		StringBuilder linea = new StringBuilder();
		String[] nombre = contrato.getColegiadoDto().getUsuario().getApellidosNombre().split(",");
		String nombreColegiado = "";
		String apellidosColegiado = "";
		if (nombre.length > 0)
			apellidosColegiado = nombre[0];
		if (nombre.length > 1)
			nombreColegiado = nombre[1];
		
		linea.append("<TVEG01>")
			.append("006")
			.append(embarcacion.getTitular().getNif())
			.append(StringUtils.rightPad(embarcacion.getTitular().getApellido1RazonSocial().trim() + embarcacion.getTitular().getApellido2().trim(), 30, ""))
			.append(StringUtils.rightPad(embarcacion.getTitular().getNombre().trim(), 15, ""))
			.append(contrato.getColegiadoDto().getUsuario().getNif())
			.append(StringUtils.rightPad(apellidosColegiado,30,""))
			.append(StringUtils.rightPad(nombreColegiado, 15, ""))
			.append(utilesFecha.getFechaHoraActual().getAnio())
			.append(utilesFecha.getFechaHoraActual().getMes())
			.append(utilesFecha.getFechaHoraActual().getDia())
			.append(StringUtils.rightPad("", 25, ""))
			.append(StringUtils.rightPad(embarcacion.getHin().trim(), 50, ""))
			.append(StringUtils.rightPad("", 39, ""))
			.append("</TVEG01>");
		
		resultadoMetodo.setError(false);
		resultadoMetodo.addAttachment("txt", linea.toString().getBytes());  
			
		return resultadoMetodo;
	}

	/**
	 * @return the servicioImprimir
	 */
	public ServicioImprimirTraficoInt getServicioImprimir() {
		return servicioImprimir;
	}

	/**
	 * @param servicioImprimir the servicioImprimir to set
	 */
	public void setServicioImprimir(ServicioImprimirTraficoInt servicioImprimir) {
		this.servicioImprimir = servicioImprimir;
	}

	/**
	 * @return the servicioPersona
	 */
	public ServicioPersona getServicioPersona() {
		return servicioPersona;
	}

	/**
	 * @param servicioPersona the servicioPersona to set
	 */
	public void setServicioPersona(ServicioPersona servicioPersona) {
		this.servicioPersona = servicioPersona;
	}

	/**
	 * @return the servicioContrato
	 */
	public ServicioContrato getServicioContrato() {
		return servicioContrato;
	}

	/**
	 * @param servicioContrato the servicioContrato to set
	 */
	public void setServicioContrato(ServicioContrato servicioContrato) {
		this.servicioContrato = servicioContrato;
	}

}
