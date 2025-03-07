package org.gestoresmadrid.oegam2comun.impresion.model.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.gestoresmadrid.core.accionTramite.model.vo.AccionTramiteVO;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.TipoDistintivo;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.tipoPermDistItv.model.enumerado.TipoDocumentoImprimirEnum;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafMatrVO;
import org.gestoresmadrid.oegam2comun.accionTramite.model.service.ServicioAccionTramite;
import org.gestoresmadrid.oegam2comun.docPermDistItv.view.bean.DocumentoPermDistItvBean;
import org.gestoresmadrid.oegam2comun.docPermDistItv.view.bean.TramitesPermDistItvBean;
import org.gestoresmadrid.oegam2comun.impresion.ImpresionTramitesMatw;
import org.gestoresmadrid.oegam2comun.impresion.model.service.ServicioImpresion;
import org.gestoresmadrid.oegam2comun.impresion.model.service.ServicioImpresionMatriculacion;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoPermisoDistintivoItvBean;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thoughtworks.xstream.XStream;

import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import escrituras.beans.ResultBean;
import net.sf.jasperreports.engine.JRException;
import trafico.beans.utiles.ParametrosPegatinaMatriculacion;
import trafico.modelo.impl.ModeloTramiteTrafImpl;
import trafico.servicio.implementacion.CriteriosImprimirTramiteTraficoBean;
import trafico.utiles.ConstantesPDF;
import trafico.utiles.enumerados.TipoAccion;
import trafico.utiles.enumerados.TipoImpreso;
import trafico.utiles.imprimir.ImprimirTramitesMatriculacion;
import trafico.utiles.imprimir.PreferenciasEtiquetas;
import utilidades.informes.ReportExporter;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioImpresionMatriculacionImpl implements ServicioImpresionMatriculacion {

	private static final long serialVersionUID = -3969604060950475438L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioImpresionMatriculacion.class);
	
	public static String RUTA_DIRECTORIO_DATOS = "rutaDirectorioDatos";

	@Autowired
	private ServicioTramiteTrafico servicioTramiteTraficoImpl;

	@Autowired
	private ServicioImpresion servicioImpresion;

	@Autowired
	private ServicioTramiteTrafico servicioTramiteTrafico;

	@Autowired
	private ServicioAccionTramite servicioAccionTramite;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	Utiles utiles;

	@Autowired
	private UtilesColegiado utilesColegiado;
	
	private ModeloTramiteTrafImpl modeloTramiteTrafImpl;

	@Override
	public ResultBean imprimirEnProcesoListaBastidores(String[] numsExpedientes) {
		ResultBean result = null;
		String mensajeProceso = servicioTramiteTraficoImpl.validarRelacionMatriculas(numsExpedientes);

		if (mensajeProceso != null) {
			result = new ResultBean(true, mensajeProceso);
		}
		return result;
	}

	@Override
	public ResultBean imprimirMatriculacionPorExpedientes(String[] numsExpedientes, CriteriosImprimirTramiteTraficoBean criteriosImprimir, BigDecimal idUsuario, Boolean tienePermisoImpresionPdf417) {
		ResultBean result = null;
		ImprimirTramitesMatriculacion imprimirTramitesMatriculacion = new ImprimirTramitesMatriculacion();
		if (criteriosImprimir.getTipoTramiteMatriculacion() != null) {
			// Tramite de Matw
			result = imprimirMatriculacionMatw(numsExpedientes, criteriosImprimir, idUsuario, tienePermisoImpresionPdf417, imprimirTramitesMatriculacion);
		} else {
			result = imprimirMatriculacionMate(numsExpedientes, criteriosImprimir, idUsuario, tienePermisoImpresionPdf417, imprimirTramitesMatriculacion);
		}
		return servicioImpresion.tratarResultSiNulo(result);
	}

	private ResultBean imprimirMatriculacionMate(String[] numsExpedientes, CriteriosImprimirTramiteTraficoBean criteriosImprimir, BigDecimal idUsuario, Boolean tienePermisoImpresionPdf417,
			ImprimirTramitesMatriculacion imprimirTramitesMatriculacion) {
		ResultBean result = null;
		if (criteriosImprimir.getTipoImpreso().equals(TipoImpreso.MatriculacionBorradorPDF417.toString())) {
			result = imprimirBorrador417Mate(numsExpedientes, imprimirTramitesMatriculacion);
		} else if (criteriosImprimir.getTipoImpreso().equals(TipoImpreso.MatriculacionPDF417.toString())) {
			result = imprimir417Mate(numsExpedientes, tienePermisoImpresionPdf417, imprimirTramitesMatriculacion);
		} else if (criteriosImprimir.getTipoImpreso().equals(TipoImpreso.MatriculacionPDFPresentacionTelematica.toString())) {
			result = imprimirPresentacionTelematicaMate(numsExpedientes, imprimirTramitesMatriculacion);
		} else if (criteriosImprimir.getTipoImpreso().equals(TipoImpreso.MatriculacionListadoBastidores_2.toString())) {
			result = imprimirListadoBastidores(numsExpedientes);
		} else if (criteriosImprimir.getTipoImpreso().equals(TipoImpreso.MatriculacionPermisoTemporalCirculacion.toString())
				|| criteriosImprimir.getTipoImpreso().equals(TipoImpreso.FichaTecnica.toString())) {
			result = servicioImpresion.recuperarDocumentosOficiales(numsExpedientes, ConstantesGestorFicheros.MATE, criteriosImprimir.getTipoImpreso());
		} else if (criteriosImprimir.getTipoImpreso().equals(TipoImpreso.PDFJustificantePresentacion576.toString())) {
			result = servicioImpresion.recuperarDocumentosOficiales(numsExpedientes, ConstantesGestorFicheros.TIPO576, criteriosImprimir.getTipoImpreso());
		} else if (criteriosImprimir.getTipoImpreso().equals(TipoImpreso.MatriculacionPegatinasEtiquetaMatricula.toString())) {
			result = imprimirEtiquetas(numsExpedientes, criteriosImprimir.getParametrosEtiquetasMatriculacion(), imprimirTramitesMatriculacion);
		} else if (criteriosImprimir.getTipoImpreso().equals(ConstantesPDF.MANDATO_GENERICO)) {
			result = imprimirMandatosPorExpedienteMate(numsExpedientes, idUsuario, TipoImpreso.MatriculacionMandatoGenerico, imprimirTramitesMatriculacion);
		} else if (criteriosImprimir.getTipoImpreso().equals(ConstantesPDF.MANDATO_ESPECIFICO)) {
			result = imprimirMandatosPorExpedienteMate(numsExpedientes, idUsuario, TipoImpreso.MatriculacionMandatoEspecifico, imprimirTramitesMatriculacion);
		}else if(criteriosImprimir.getTipoImpreso().equals(TipoImpreso.SolicitudNRE06.toString())) {
			result = servicioImpresion.imprimirPdfSolicitudNRE06(numsExpedientes, ConstantesGestorFicheros.AEAT , criteriosImprimir.getTipoImpreso());
		}else if (criteriosImprimir.getTipoImpreso().equals(TipoImpreso.CertificadoRevisionColegial.toString())) {
			result = servicioImpresion.recuperarDocumentosOficiales(numsExpedientes, ConstantesGestorFicheros.MATE, criteriosImprimir.getTipoImpreso());
		} 
		return result;
	}

	private ResultBean imprimirMandatosPorExpedienteMate(String[] numsExpedientes, BigDecimal idUsuario, TipoImpreso tipoImpreso, ImprimirTramitesMatriculacion imprimirTramitesMatriculacion) {
		ResultBean result = new ResultBean();
		ArrayList<byte[]> listaByte = new ArrayList<byte[]>();
		for (int i = 0; !result.getError() && i < numsExpedientes.length; i++) {
			ResultBean rs = null;
			try {
				if (TipoImpreso.MatriculacionMandatoGenerico.equals(tipoImpreso)) {
					rs = imprimirTramitesMatriculacion.matriculacionMandatoGenerico(numsExpedientes[i]);
				} else if (TipoImpreso.MatriculacionMandatoEspecifico.equals(tipoImpreso)) {
					rs = imprimirTramitesMatriculacion.matriculacionMandatoEspecifico(numsExpedientes[i]);
				}
			} catch (OegamExcepcion e) {
				rs = new ResultBean();
				rs.setError(true);
				rs.setMensaje("Ha sucedido un error a la hora de imprimir el tramite");
				log.error("Ha sucedido un error a la hora de imprimir el tramite: " + e.getMessage(), e);
			} catch (Throwable e) {
				rs = new ResultBean();
				rs.setError(true);
				log.error("Error a la hora de imprimir: " + e.getMessage(), e);
				result = new ResultBean(true, "El tipo indicado no es un tipo válido para imprimir");
			}
			if (rs == null || rs.getError()) {
				result.setError(true);
				result.addMensajeALista(numsExpedientes[i] + ": " + rs.getMensaje());
			} else {
				byte[] byte1 = (byte[]) rs.getAttachment(ResultBean.TIPO_PDF);
				listaByte.add(byte1);
			}
		}

		if (result != null && !result.getError()) {
			result = servicioImpresion.generarResultBeanConArrayByteFinal(listaByte, tipoImpreso.toString() + ".pdf");
		}
		return result;
	}

	private ResultBean imprimirMandatosPorExpedienteMatw(String[] numsExpedientes, TipoImpreso tipoImpreso) {
		ResultBean result = new ResultBean();
		if (numsExpedientes == null || numsExpedientes.length == 0) {
			result = new ResultBean(true, "No hay trámites para imprimir");
			return result;
		}
		ArrayList<byte[]> listaByte = new ArrayList<byte[]>();
		ImpresionTramitesMatw impresionTramitesMatw = new ImpresionTramitesMatw();
		try {
			for (int i = 0; !result.getError() && i < numsExpedientes.length; i++) {
				ResultBean rs = impresionTramitesMatw.impresionMandatosMatriculacion(numsExpedientes[i], tipoImpreso.toString());
				if (rs == null || rs.getError()) {
					result.setError(true);
					result.addMensajeALista(numsExpedientes[i] + ": " + rs.getMensaje());
				} else {
					byte[] byte1 = (byte[]) rs.getAttachment(ResultBean.TIPO_PDF);
					listaByte.add(byte1);
				}
			}
		} catch (OegamExcepcion e) {
			log.error("Error a la hora de imprimir: " + e.getMessage(), e);
			result = new ResultBean(true, "El tipo indicado no es un tipo válido para imprimir");
		} catch (Throwable e) {
			log.error("Error a la hora de imprimir: " + e.getMessage(), e);
			result = new ResultBean(true, "El tipo indicado no es un tipo válido para imprimir");
		}
		if (result != null && !result.getError()) {
			result = servicioImpresion.generarResultBeanConArrayByteFinal(listaByte, tipoImpreso.toString() + ".pdf");
		}
		return result;
	}

	private ResultBean imprimirEtiquetas(String[] numsExpedientes, ParametrosPegatinaMatriculacion parametrosEtiquetasMatriculacion, ImprimirTramitesMatriculacion imprimirTramitesMatriculacion) {
		ResultBean result = new ResultBean();
		if (parametrosEtiquetasMatriculacion == null) {
			result.setError(true);
			result.addMensajeALista("No se han encontrado los parámetros necesario para generar las etiquetas");
			return result;
		}
		try {
			PreferenciasEtiquetas.guardarPreferencias(parametrosEtiquetasMatriculacion, utilesColegiado.getNumColegiadoSession());
		} catch (OegamExcepcion e) {
			result.setError(true);
			result.addMensajeALista("Existen problemas al guardar la configuración. Inténtelo más tarde");
			return result;
		}
		String matriYBasti = "";
		for (int i = 0; i < numsExpedientes.length; i++) {
			TramiteTrafDto tramiteTrafDto = servicioTramiteTrafico.getTramiteDto(new BigDecimal(numsExpedientes[i]), true);
			if (tramiteTrafDto != null && tramiteTrafDto.getVehiculoDto() != null) {
				matriYBasti += (null != tramiteTrafDto.getVehiculoDto().getBastidor() ? tramiteTrafDto.getVehiculoDto().getBastidor() : " ") + ";"
						+ (null != tramiteTrafDto.getVehiculoDto().getMatricula() ? tramiteTrafDto.getVehiculoDto().getMatricula() : " ") + "_";
			}
		}
		parametrosEtiquetasMatriculacion.setMatryBast(matriYBasti);
		result = imprimirTramitesMatriculacion.matriculacionPegatinasEtiquetaMatricula(parametrosEtiquetasMatriculacion);

		return result;
	}

	@Override
	public ResultBean imprimirListadoBastidores(String[] numsExpedientes) {
		ResultBean result = new ResultBean();
		try {
			String validacion = getModeloTramiteTrafImpl().validarRelacionMatriculas(numsExpedientes);
			if (validacion.equals("")) {
				List<String> listaExpedientes = new ArrayList<String>(Arrays.asList(numsExpedientes));
				result = new ImpresionTramitesMatw().matriculacionListadoBastidoresMatriculacion(listaExpedientes);
			} else {
				result.setError(true);
				result.addMensajeALista(validacion);
			}
		} catch (OegamExcepcion e) {
			result.setError(true);
			result.addMensajeALista("No se ha podido generar el documento");
			log.error("Hay errores al generar el listado de matriculas de MATW", e);
		} catch (Throwable e) {
			log.error("Hay errores al generar el listado de matriculas de MATW", e);
		}
		return result;
	}

	private ResultBean imprimirPresentacionTelematicaMate(String[] numsExpedientes, ImprimirTramitesMatriculacion imprimirTramitesMatriculacion) {
		ResultBean result = new ResultBean();
		if (numsExpedientes == null || numsExpedientes.length == 0) {
			result.setError(true);
			result.addMensajeALista("No hay trámites");
			return result;
		}

		ArrayList<byte[]> listaByte = new ArrayList<byte[]>();
		try {
			for (int i = 0; !result.getError() && i < numsExpedientes.length; i++) {
				ResultBean rs = imprimirTramitesMatriculacion.matriculacionPDFPresentacionTelematica(numsExpedientes[i]);
				if (rs == null || rs.getError()) {
					result.setError(true);
					result.addMensajeALista(numsExpedientes[i] + ": " + rs.getMensaje());
				} else {
					byte[] byte1 = (byte[]) rs.getAttachment(ResultBean.TIPO_PDF);
					listaByte.add(byte1);
				}
			}
		} catch (OegamExcepcion e) {
			result.setError(true);
			result.addMensajeALista("Se ha producido un error a la hora de imprimir el tramite.");
			log.error("Se ha producido un error a la hora de imprimir el tramite: " + e.getMessage(), e);
		} catch (Throwable e) {
			result.setError(true);
			result.addMensajeALista("Se ha producido un error a la hora de imprimir el tramite.");
			log.error("Se ha producido un error a la hora de imprimir el tramite: " + e.getMessage(), e);
		}

		if (result != null && !result.getError()) {
			result = servicioImpresion.generarResultBeanConArrayByteFinal(listaByte, "presentacionTelematica.pdf");
		}

		return result;
	}

	private ResultBean imprimir417Mate(String[] numsExpedientes, Boolean tienePermisoImpresionPdf417, ImprimirTramitesMatriculacion imprimirTramitesMatriculacion) {
		ResultBean result = new ResultBean();
		if (numsExpedientes == null || numsExpedientes.length == 0) {
			result.setError(true);
			result.addMensajeALista("No hay trámites");
			return result;
		}

		if (!tienePermisoImpresionPdf417) {
			result.setError(true);
			result.setMensaje(ConstantesPDF.NO_SE_PUEDE_IMPRIMIR_LA_MANCHA_PDF_PORQUE_NO_TIENE_PERMISO_PARA_ESTA_ACCION);
			return result;
		}

		ArrayList<byte[]> listaByte = new ArrayList<byte[]>();
		try {
			for (int i = 0; !result.getError() && i < numsExpedientes.length; i++) {
				ResultBean rs = imprimirTramitesMatriculacion.matriculacionPDF417(numsExpedientes[i]);
				if (rs == null || rs.getError()) {
					result.setError(true);
					result.addMensajeALista(numsExpedientes[i] + ": " + rs.getMensaje());
				} else {
					byte[] byte1 = (byte[]) rs.getAttachment(ResultBean.TIPO_PDF);
					listaByte.add(byte1);
				}
			}
		} catch (OegamExcepcion e) {
			result.setError(true);
			result.addMensajeALista("Se ha producido un error a la hora de imprimir el tramite.");
			log.error("Se ha producido un error a la hora de imprimir el tramite: " + e.getMessage(), e);
		} catch (Throwable e) {
			result.setError(true);
			result.addMensajeALista("Se ha producido un error a la hora de imprimir el tramite.");
			log.error("Se ha producido un error a la hora de imprimir el tramite: " + e.getMessage(), e);
		}

		if (result != null && !result.getError()) {
			result = servicioImpresion.generarResultBeanConArrayByteFinal(listaByte, "417.pdf");
		}

		return result;
	}

	private ResultBean imprimirBorrador417Mate(String[] numsExpedientes, ImprimirTramitesMatriculacion imprimirTramitesMatriculacion) {
		ResultBean result = new ResultBean();
		if (numsExpedientes == null || numsExpedientes.length == 0) {
			result.setError(true);
			result.addMensajeALista("No hay trámites");
			return result;
		}

		ArrayList<byte[]> listaByte = new ArrayList<byte[]>();
		try {
			for (int i = 0; !result.getError() && i < numsExpedientes.length; i++) {
				ResultBean rs = imprimirTramitesMatriculacion.matriculacionBorradorPDF417(numsExpedientes[i]);
				if (rs == null || rs.getError()) {
					result.setError(true);
					result.addMensajeALista(numsExpedientes[i] + ": " + rs.getMensaje());
				} else {
					byte[] byte1 = (byte[]) rs.getAttachment(ResultBean.TIPO_PDF);
					listaByte.add(byte1);
				}
			}
		} catch (OegamExcepcion e) {
			result.setError(true);
			result.addMensajeALista("Se ha producido un error a la hora de imprimir el tramite.");
			log.error("Se ha producido un error a la hora de imprimir el tramite: " + e.getMessage(), e);
		} catch (Throwable e) {
			result.setError(true);
			result.addMensajeALista("Se ha producido un error a la hora de imprimir el tramite.");
			log.error("Se ha producido un error a la hora de imprimir el tramite: " + e.getMessage(), e);
		}

		if (result != null && !result.getError()) {
			result = servicioImpresion.generarResultBeanConArrayByteFinal(listaByte, "borrador417.pdf");
		}
		return result;
	}

	private ResultBean imprimirMatriculacionMatw(String[] numsExpedientes, CriteriosImprimirTramiteTraficoBean criteriosImprimir, BigDecimal idUsuario, Boolean tienePermisoImpresionPdf417,
			ImprimirTramitesMatriculacion imprimirTramitesMatriculacion) {
		ResultBean result = null;
		if (criteriosImprimir.getTipoImpreso().equals(TipoImpreso.MatriculacionBorradorPDF417.toString())) {
			result = imprimirBorrador417Matw(numsExpedientes, idUsuario);
		} else if (criteriosImprimir.getTipoImpreso().equals(TipoImpreso.MatriculacionPDF417.toString())) {
			result = imprimir417Matw(numsExpedientes, idUsuario, tienePermisoImpresionPdf417);
		} else if (criteriosImprimir.getTipoImpreso().equals(TipoImpreso.MatriculacionPDFPresentacionTelematica.toString())) {
			result = imprimirPdfPresentacionTelematicaMatw(numsExpedientes, idUsuario);
		} else if (criteriosImprimir.getTipoImpreso().equals(TipoImpreso.MatriculacionListadoBastidores_2.toString())) {
			result = imprimirListadoBastidores(numsExpedientes);
		} else if (criteriosImprimir.getTipoImpreso().equals(TipoImpreso.MatriculacionPermisoTemporalCirculacion.toString())
				|| criteriosImprimir.getTipoImpreso().equals(TipoImpreso.FichaTecnica.toString())) {
			result = servicioImpresion.recuperarDocumentosOficiales(numsExpedientes, ConstantesGestorFicheros.MATE, criteriosImprimir.getTipoImpreso());
		} else if (criteriosImprimir.getTipoImpreso().equals(TipoImpreso.PDFJustificantePresentacion576.toString())) {
			result = servicioImpresion.recuperarDocumentosOficiales(numsExpedientes, ConstantesGestorFicheros.TIPO576, criteriosImprimir.getTipoImpreso());
		} else if (criteriosImprimir.getTipoImpreso().equals(TipoImpreso.MatriculacionPegatinasEtiquetaMatricula.toString())) {
			result = imprimirEtiquetas(numsExpedientes, criteriosImprimir.getParametrosEtiquetasMatriculacion(), imprimirTramitesMatriculacion);
		} else if (criteriosImprimir.getTipoImpreso().equals(ConstantesPDF.MANDATO_GENERICO)) {
			result = imprimirMandatosPorExpedienteMatw(numsExpedientes, TipoImpreso.MatriculacionMandatoGenerico);
		} else if (criteriosImprimir.getTipoImpreso().equals(ConstantesPDF.MANDATO_ESPECIFICO)) {
			result = imprimirMandatosPorExpedienteMatw(numsExpedientes, TipoImpreso.MatriculacionMandatoEspecifico);
		} else if (criteriosImprimir.getTipoImpreso().equals(TipoImpreso.ConsultaEITV.toString())) {
			result = imprimirConsultaEitv(numsExpedientes, TipoImpreso.ConsultaEITV.toString());
		} else if (criteriosImprimir.getTipoImpreso().equals(TipoImpreso.XMLConsultaEitv.toString())) {
			result = imprimirXMLConsultaEitv(numsExpedientes, TipoImpreso.XMLConsultaEitv.toString());
		}else if(criteriosImprimir.getTipoImpreso().equals(TipoImpreso.SolicitudNRE06.toString())) {
			result = servicioImpresion.imprimirPdfSolicitudNRE06(numsExpedientes, ConstantesGestorFicheros.AEAT , criteriosImprimir.getTipoImpreso());
		}else if (criteriosImprimir.getTipoImpreso().equals(TipoImpreso.CertificadoRevisionColegial.toString())) {
			result = servicioImpresion.recuperarDocumentosOficiales(numsExpedientes, ConstantesGestorFicheros.MATE, criteriosImprimir.getTipoImpreso());
		} 

		return result;
	}
	

	private ResultBean imprimirConsultaEitv(String[] numsExpedientes, String tipo) {
		ResultBean result = null;
		ImpresionTramitesMatw impresionTramitesMatw = new ImpresionTramitesMatw();
		if (numsExpedientes.length > 1) {
			result = impresionTramitesMatw.imprimirConsultaTarjetaEitvPorExpedientes(numsExpedientes, tipo);
		} else {
			result = impresionTramitesMatw.imprimirConsultaTarjetaEitvPorExpediente(numsExpedientes[0], tipo);
		}
		return result;
	}

	private ResultBean imprimirXMLConsultaEitv(String[] numsExpedientes, String tipo) {
		ResultBean result = null;
		ImpresionTramitesMatw impresionTramitesMatw = new ImpresionTramitesMatw();
		if (numsExpedientes.length > 1) {
			result = impresionTramitesMatw.imprimirXMLConsultaTarjetaEitvPorExpedientes(numsExpedientes, tipo);
		} else {
			result = impresionTramitesMatw.imprimirXMLConsultaTarjetaEitvPorExpediente(numsExpedientes[0], tipo);
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = OegamExcepcion.class)
	public ResultBean imprimirMatw(String[] numsExpedientes, BigDecimal idUsuario, TipoImpreso tipoImpreso) {
		ResultBean result = new ResultBean();
		if (numsExpedientes == null || numsExpedientes.length == 0) {
			result.setError(true);
			result.addMensajeALista("No hay trámites");
			return result;
		}

		ArrayList<byte[]> listaByte = new ArrayList<byte[]>();
		try {
			ImpresionTramitesMatw impresionTramitesMatw = new ImpresionTramitesMatw();
			for (int i = 0; !result.getError() && i < numsExpedientes.length; i++) {
				ResultBean rs = impresionTramitesMatw.matriculacionGeneralMatw(numsExpedientes[i], tipoImpreso.toString(), TipoTramiteTrafico.Matriculacion);

				if (rs == null || rs.getError()) {
					result.setError(true);
					result.addMensajeALista(numsExpedientes[i] + ": " + rs.getMensaje());
				} else {
					byte[] byte1 = (byte[]) rs.getAttachment(ResultBean.TIPO_PDF);
					listaByte.add(byte1);
				}
			}
		} catch (OegamExcepcion e) {
			result.setError(true);
			result.addMensajeALista("Se ha producido un error a la hora de imprimir el tramite.");
			log.error("Se ha producido un error a la hora de imprimir el tramite: " + e.getMessage(), e);
		} catch (Throwable e) {
			result.setError(true);
			result.addMensajeALista("Se ha producido un error a la hora de imprimir el tramite.");
			log.error("Se ha producido un error a la hora de imprimir el tramite: " + e.getMessage(), e);
		}

		if (result != null && !result.getError()) {
			result = servicioImpresion.generarResultBeanConArrayByteFinal(listaByte, tipoImpreso.toString() + ".pdf");
		}

		return result;
	}

	@Override
	@Transactional
	public ResultBean imprimirMatwProceso(String[] numsExpedientes, BigDecimal idUsuario, TipoImpreso tipoImpreso) {
		ResultBean result = new ResultBean();
		if (numsExpedientes == null || numsExpedientes.length == 0) {
			result.setError(true);
			result.addMensajeALista("No hay trámites");
			return result;
		}
		AccionTramiteVO accionTramiteVO = null;
		if (TipoImpreso.MatriculacionPDF417Matw.getValorEnum().equals(tipoImpreso)) {
			accionTramiteVO = servicioAccionTramite.setAccionTramite(idUsuario, null, TipoAccion.Impresion417.getValorEnum(), null, null);
		}

		ArrayList<byte[]> listaByte = new ArrayList<byte[]>();
		try {
			ImpresionTramitesMatw impresionTramitesMatw = new ImpresionTramitesMatw();
			for (int i = 0; !result.getError() && i < numsExpedientes.length; i++) {
				ResultBean rs = impresionTramitesMatw.matriculacionGeneralMatw(numsExpedientes[i], tipoImpreso.toString(), TipoTramiteTrafico.Matriculacion);

				if (TipoImpreso.MatriculacionPDF417Matw.getValorEnum().equals(tipoImpreso)) {
					accionTramiteVO.getId().setNumExpediente(utiles.stringToLong(numsExpedientes[i]));
					accionTramiteVO.setFechaFin(utilesFecha.formatoFecha("dd/MM/yyyy", utilesFecha.getFechaHoy()));
					if (rs != null) {
						accionTramiteVO.setRespuesta(rs.getMensaje());
					}
					servicioAccionTramite.guardarAccion(accionTramiteVO);
				}

				if (rs == null || rs.getError()) {
					result.setError(true);
					result.addMensajeALista(numsExpedientes[i] + ": " + rs.getMensaje());
				} else {
					byte[] byte1 = (byte[]) rs.getAttachment(ResultBean.TIPO_PDF);
					listaByte.add(byte1);
				}
			}
		} catch (OegamExcepcion e) {
			result.setError(true);
			result.addMensajeALista("Se ha producido un error a la hora de imprimir el tramite.");
			log.error("Se ha producido un error a la hora de imprimir el tramite: " + e.getMessage(), e);
		} catch (Throwable e) {
			result.setError(true);
			result.addMensajeALista("Se ha producido un error a la hora de imprimir el tramite.");
			log.error("Se ha producido un error a la hora de imprimir el tramite: " + e.getMessage(), e);
		}

		if (result != null && !result.getError()) {
			result = servicioImpresion.generarResultBeanByteProceso(listaByte);
		}

		return result;
	}

	private ResultBean imprimirPdfPresentacionTelematicaMatw(String[] numsExpedientes, BigDecimal idUsuario) {
		return imprimirMatw(numsExpedientes, idUsuario, TipoImpreso.MatriculacionPDFPresentacionTelematica);
	}

	private ResultBean imprimir417Matw(String[] numsExpedientes, BigDecimal idUsuario, Boolean tienePermisoImpresionPdf417) {
		if (!tienePermisoImpresionPdf417) {
			ResultBean result = new ResultBean();
			result.setError(true);
			result.setMensaje(ConstantesPDF.NO_SE_PUEDE_IMPRIMIR_LA_MANCHA_PDF_PORQUE_NO_TIENE_PERMISO_PARA_ESTA_ACCION);
			return result;
		}
		return imprimirMatw(numsExpedientes, idUsuario, TipoImpreso.MatriculacionPDF417);
	}

	private ResultBean imprimirBorrador417Matw(String[] numsExpedientes, BigDecimal idUsuario) {
		return imprimirMatw(numsExpedientes, idUsuario, TipoImpreso.MatriculacionBorradorPDF417);
	}

	public ModeloTramiteTrafImpl getModeloTramiteTrafImpl() {
		if (modeloTramiteTrafImpl == null) {
			modeloTramiteTrafImpl = new ModeloTramiteTrafImpl();
		}
		return modeloTramiteTrafImpl;
	}

	public void setModeloTramiteTrafImpl(ModeloTramiteTrafImpl modeloTramiteTrafImpl) {
		this.modeloTramiteTrafImpl = modeloTramiteTrafImpl;
	}

	@Override
	public ResultadoPermisoDistintivoItvBean generarInformeDocImpresoGestoria(List<TramiteTrafMatrVO> listaTramitesMatrBBDD, TipoDocumentoImprimirEnum tipoDocumento, TipoDistintivo tipoDistintivo, String docId, Boolean esMoto) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("numeroTramites", listaTramitesMatrBBDD.size());
			params.put("numTramitesPagina", 30);
			String xml = generarXmlJustfPermDstvEitv(listaTramitesMatrBBDD, tipoDocumento, tipoDistintivo, docId, esMoto);
			ReportExporter re = new ReportExporter();
			resultado.setByteFichero(re.generarInforme(gestorPropiedades.valorPropertie(RUTA_DIRECTORIO_DATOS) + gestorPropiedades.valorPropertie("documentoPermDistItv.plantillas.rutaPlantillas"),"justificantePermEitvDstv" , "pdf", xml, tipoDocumento.getNombreEnum(), params, null));
		} catch (JRException e) {
			log.error(e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Se ha producido un error a la hora de generar el documento impreso.");
		} catch (ParserConfigurationException e) {
			log.error(e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Se ha producido un error a la hora de generar el documento impreso.");
		}
		return resultado;
	}
	@Override
	public ResultadoPermisoDistintivoItvBean generarInformeDocImpresoPermEitvGestoria(List<TramiteTrafMatrVO> listaTramitesMatrBBDD, TipoDocumentoImprimirEnum tipoDocumento, TipoDistintivo tipoDistintivo, String docId, Boolean esMoto) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("numeroTramites", listaTramitesMatrBBDD.size());
			params.put("numTramitesPagina", 30);
			String xml = generarXmlJustfPermDstvEitv(listaTramitesMatrBBDD, tipoDocumento, tipoDistintivo, docId, esMoto);
			ReportExporter re = new ReportExporter();
			resultado.setByteFichero(re.generarInforme(gestorPropiedades.valorPropertie(RUTA_DIRECTORIO_DATOS) + gestorPropiedades.valorPropertie("documentoPermDistItv.plantillas.rutaPlantillas"),"justificantePermEitv" , "pdf", xml, tipoDocumento.getNombreEnum(), params, null));
		} catch (JRException e) {
			log.error(e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Se ha producido un error a la hora de generar el documento impreso.");
		} catch (ParserConfigurationException e) {
			log.error(e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Se ha producido un error a la hora de generar el documento impreso.");
		}
		return resultado;
	}

	@Override
	public ResultadoPermisoDistintivoItvBean generarInformeDocImpresoPermEitvColegio(List<TramiteTrafMatrVO> listaTramitesMatrBBDD, TipoDocumentoImprimirEnum tipoDocumento, TipoDistintivo tipoDistintivo, String docId, Boolean esMotos) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("numeroTramites", listaTramitesMatrBBDD.size());
			params.put("numTramitesPagina", 30);
			String xml = generarXmlJustfPermDstvEitv(listaTramitesMatrBBDD, tipoDocumento, tipoDistintivo, docId, esMotos);
			ReportExporter re = new ReportExporter();
			resultado.setByteFichero(re.generarInforme(gestorPropiedades.valorPropertie(RUTA_DIRECTORIO_DATOS) + gestorPropiedades.valorPropertie("documentoPermDistItv.plantillas.rutaPlantillas"),"justificantePermEitvColegio" , "pdf", xml, tipoDocumento.getNombreEnum(), params, null));
		} catch (JRException e) {
			log.error(e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Se ha producido un error a la hora de generar el documento impreso.");
		} catch (ParserConfigurationException e) {
			log.error(e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Se ha producido un error a la hora de generar el documento impreso.");
		}
		return resultado;
	}
	
	private String generarXmlJustfPermDstvEitv(List<TramiteTrafMatrVO> listaTramitesMatrBBDD, TipoDocumentoImprimirEnum tipoDocumento, TipoDistintivo tipoDistintivo, String docId, Boolean esMotos) {
		DocumentoPermDistItvBean documentoPermDistItvBean = new DocumentoPermDistItvBean();
		if(TipoDocumentoImprimirEnum.DISTINTIVO.getValorEnum().equals(tipoDocumento.getValorEnum()) && esMotos){
			documentoPermDistItvBean.setDocumento(tipoDocumento.getNombreEnum() + " " + tipoDistintivo.getNombreEnum() + "- Motocicletas");
		}else{
			documentoPermDistItvBean.setDocumento(tipoDocumento.getNombreEnum() + " " + tipoDistintivo.getNombreEnum());
		}
		documentoPermDistItvBean.setJefatura(listaTramitesMatrBBDD.get(0).getJefaturaTrafico().getDescripcion());
		documentoPermDistItvBean.setNumColegiado(listaTramitesMatrBBDD.get(0).getNumColegiado());
		documentoPermDistItvBean.setGestoria(listaTramitesMatrBBDD.get(0).getContrato().getColegiado().getUsuario().getApellidosNombre());
		documentoPermDistItvBean.setNifGestor(listaTramitesMatrBBDD.get(0).getContrato().getColegiado().getUsuario().getNif());
		documentoPermDistItvBean.setDocId(docId);
		if(TipoTramiteTrafico.Matriculacion.getValorEnum().equals(listaTramitesMatrBBDD.get(0).getTipoTramite())){
			documentoPermDistItvBean.setTipoTramite(TipoTramiteTrafico.Matriculacion.getNombreEnum());
		}else if(TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(listaTramitesMatrBBDD.get(0).getTipoTramite())){
			documentoPermDistItvBean.setTipoTramite(TipoTramiteTrafico.TransmisionElectronica.getNombreEnum());
		}
		List<TramitesPermDistItvBean> listaTramitesBean = new ArrayList<TramitesPermDistItvBean>();
		int cont = 1;
		int i = 0;
		for(TramiteTrafMatrVO tramiteBBDD : listaTramitesMatrBBDD){
			TramitesPermDistItvBean tramitesPermDistItvBean = new TramitesPermDistItvBean();
			tramitesPermDistItvBean.setMatricula(tramiteBBDD.getVehiculo().getMatricula());
			tramitesPermDistItvBean.setNumero(cont++);
			tramitesPermDistItvBean.setNumExpediente(tramiteBBDD.getNumExpediente().toString());

			tramitesPermDistItvBean.setPc("OK");
			tramitesPermDistItvBean.setEitv("OK");
			tramitesPermDistItvBean.setFechaPresentacion(new SimpleDateFormat("dd-MM-yyyy").format(tramiteBBDD.getFechaPresentacion()));
			listaTramitesBean.add(i++,tramitesPermDistItvBean);
		}
		documentoPermDistItvBean.setTramites(listaTramitesBean);
		
		String xml="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>";
		
		XStream xStream = new XStream();
		xStream.processAnnotations(DocumentoPermDistItvBean.class);
		xml += xStream.toXML(documentoPermDistItvBean);
		xml = xml.replaceAll("__", "_");
		xml = xml.replaceAll("\n *<", "<");	
		return xml;
	}

}
