package org.gestoresmadrid.oegam2comun.impresion.model.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.oegam2comun.impresion.ImpresionTramitesTransmision;
import org.gestoresmadrid.oegam2comun.impresion.model.service.ServicioImpresion;
import org.gestoresmadrid.oegam2comun.impresion.model.service.ServicioImpresionTransmision;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import escrituras.beans.ResultBean;
import trafico.modelo.impl.ModeloTramiteTrafImpl;
import trafico.servicio.implementacion.CriteriosImprimirTramiteTraficoBean;
import trafico.utiles.ConstantesPDF;
import trafico.utiles.enumerados.TipoImpreso;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioImpresionTransmisionImpl implements ServicioImpresionTransmision {

	private static final long serialVersionUID = -4996629646912009471L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioImpresionTransmision.class);

	@Autowired
	private ServicioImpresion servicioImpresion;

	@Autowired
	private ServicioTramiteTrafico servicioTramiteTrafico;
	
	@Autowired
	private ServicioTramiteTrafico servicioTramiteTraficoImpl;

	private ModeloTramiteTrafImpl modeloTramiteTrafImpl;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	Utiles utiles;

	@Override
	public ResultBean imprimirEnProcesoListaBastidores(String[] numsExpedientes) {
		ResultBean result = null;
		String mensajeProceso = null;
		String habilitado = gestorPropiedades.valorPropertie("habilitar.impresion.nuevas.transmisiones");
		if (("SI").equalsIgnoreCase(habilitado)) {
			mensajeProceso = servicioTramiteTraficoImpl.validarRelacionMatriculas(numsExpedientes);
		}
		if (mensajeProceso != null) {
			result = new ResultBean(true, mensajeProceso);
		}
		return result;
	}
	
	@Override
	public ResultBean imprimirTransmisionesPorExpedientes(String[] numsExpedientes, CriteriosImprimirTramiteTraficoBean criteriosImprimir, String numColegiado, BigDecimal idContrato,
			BigDecimal idUsuario, boolean esTelematica, Boolean tienePermisoImpresionPdf417) {
		ResultBean result = null;
		TipoTramiteTrafico tipoTramite = null;
		if (esTelematica) {
			tipoTramite = TipoTramiteTrafico.TransmisionElectronica;
		} else {
			tipoTramite = TipoTramiteTrafico.Transmision; 
		}
		ImpresionTramitesTransmision impresionTramitesTransmision = new ImpresionTramitesTransmision();
		if (criteriosImprimir.getTipoImpreso().equals(TipoImpreso.TransmisionBorradorPDF417.toString())) {
			result = imprimirBorradorPdf417(numsExpedientes, numColegiado, idContrato, idUsuario, tipoTramite, impresionTramitesTransmision);
		} else if (criteriosImprimir.getTipoImpreso().equals(TipoImpreso.TransmisionPDF417.toString())) {
			result = imprimir417(numsExpedientes, numColegiado, idContrato, idUsuario, tipoTramite, tienePermisoImpresionPdf417, impresionTramitesTransmision);
		} else if (criteriosImprimir.getTipoImpreso().equals(TipoImpreso.TransmisionModelo430.toString())) {
			result = imprimir430(numsExpedientes, numColegiado, idContrato, idUsuario, tipoTramite, impresionTramitesTransmision);
		} else if (criteriosImprimir.getTipoImpreso().equals(TipoImpreso.MatriculacionListadoBastidores_2.toString())) {
			result = imprimirListadoBastidores(numsExpedientes, numColegiado, idContrato, tipoTramite, impresionTramitesTransmision);
		} else if (criteriosImprimir.getTipoImpreso().equals(ConstantesPDF.MANDATO_GENERICO)) {
			result = imprimirMandatos(numsExpedientes, numColegiado, idContrato, idUsuario, criteriosImprimir, TipoImpreso.MatriculacionMandatoGenerico, impresionTramitesTransmision);
		} else if (criteriosImprimir.getTipoImpreso().equals(ConstantesPDF.MANDATO_ESPECIFICO)) {
			result = imprimirMandatos(numsExpedientes, numColegiado, idContrato, idUsuario, criteriosImprimir, TipoImpreso.MatriculacionMandatoEspecifico, impresionTramitesTransmision);
		} else if (esTelematica && criteriosImprimir.getTipoImpreso().equals(TipoImpreso.TransmisionPDFPresentacionTelematica.toString())) {
			result = imprimirPresentacionTelematica(numsExpedientes, numColegiado, idContrato, idUsuario, tipoTramite, impresionTramitesTransmision);
		} else if (esTelematica && criteriosImprimir.getTipoImpreso().equals(TipoImpreso.TransmisionDocumentosTelematicos.toString())) {
			result = servicioImpresion.recuperarDocumentosOficiales(numsExpedientes, ConstantesGestorFicheros.CTIT, criteriosImprimir.getTipoImpreso());
		}//Mantis 28966
		else if (criteriosImprimir.getTipoImpreso().equals(TipoImpreso.DeclaracionJuradaExportacionTransito.toString())){
			result = imprimirDeclaracionJurada(numsExpedientes, numColegiado, idContrato, idUsuario, criteriosImprimir, TipoImpreso.DeclaracionJuradaExportacionTransito, impresionTramitesTransmision);
		}else if (criteriosImprimir.getTipoImpreso().equals(TipoImpreso.DeclaracionJuradaExtravioFicha.toString())){
			result = imprimirDeclaracionJurada(numsExpedientes, numColegiado, idContrato, idUsuario, criteriosImprimir, TipoImpreso.DeclaracionJuradaExtravioFicha, impresionTramitesTransmision);
		}else if (criteriosImprimir.getTipoImpreso().equals(TipoImpreso.DeclaracionJuradaExtravioPermisoFicha.toString())){
			result = imprimirDeclaracionJurada(numsExpedientes, numColegiado, idContrato, idUsuario, criteriosImprimir, TipoImpreso.DeclaracionJuradaExtravioPermisoFicha, impresionTramitesTransmision);
		}else if (criteriosImprimir.getTipoImpreso().equals(TipoImpreso.DeclaracionJuradaExtravioPermisoLicencia.toString())){
			result = imprimirDeclaracionJurada(numsExpedientes, numColegiado, idContrato, idUsuario, criteriosImprimir, TipoImpreso.DeclaracionJuradaExtravioPermisoLicencia, impresionTramitesTransmision);
		}else if (criteriosImprimir.getTipoImpreso().equals(TipoImpreso.DeclaracionJuradaEntregaAnteriorPermiso.toString())){
			result = imprimirDeclaracionJurada(numsExpedientes, numColegiado, idContrato, idUsuario, criteriosImprimir, TipoImpreso.DeclaracionJuradaEntregaAnteriorPermiso, impresionTramitesTransmision);
		}else if(criteriosImprimir.getTipoImpreso().equals(TipoImpreso.SolicitudNRE06.toString())) {
			result = servicioImpresion.imprimirPdfSolicitudNRE06(numsExpedientes, ConstantesGestorFicheros.AEAT , criteriosImprimir.getTipoImpreso());
		}
		return servicioImpresion.tratarResultSiNulo(result);
	}

	private ResultBean imprimirDeclaracionJurada(String[] numsExpedientes, String numColegiado, BigDecimal idContrato, BigDecimal idUsuario, CriteriosImprimirTramiteTraficoBean criteriosImprimir,
			TipoImpreso tipoImpreso, ImpresionTramitesTransmision impresionTramitesTransmision) {
		BigDecimal[] numExp = utiles.convertirStringArrayToBigDecimal(numsExpedientes);
		ResultBean result = new ResultBean();
		ArrayList<byte[]> listaByte = new ArrayList<byte[]>();
		for (int i = 0; !result.getError() && i < numExp.length; i++) {
			ResultBean rs = null;
			try {
				rs = impresionTramitesTransmision.imprimirDeclaracionJurada(numExp[i], numColegiado, idContrato, idUsuario, criteriosImprimir.getTipoRepresentacion(), tipoImpreso.getNombreEnum());
			} catch (Exception e) {
				rs = new ResultBean();
				rs.setError(true);
				rs.setMensaje("Ha sucedido un error a la hora de imprimir el tramite");
				log.error("Ha sucedido un error a la hora de imprimir el tramite: " + e.getMessage(), numExp[i].toString());
			}
			if (rs == null || rs.getError()) {
				result.setError(true);
				result.addMensajeALista(numExp[i] + ": " + rs.getMensaje());
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
	
	private ResultBean imprimirPresentacionTelematica(String[] numsExpedientes, String numColegiado, BigDecimal idContrato, BigDecimal idUsuario, TipoTramiteTrafico tipoTramite,
			ImpresionTramitesTransmision impresionTramitesTransmision) {
		return imprimirTransmision(numsExpedientes, numColegiado, idContrato, idUsuario, tipoTramite, TipoImpreso.TransmisionPDFPresentacionTelematica, false, impresionTramitesTransmision);
	}

	private ResultBean imprimirMandatos(String[] numsExpedientes, String numColegiado, BigDecimal idContrato, BigDecimal idUsuario, CriteriosImprimirTramiteTraficoBean criteriosImprimir,
			TipoImpreso tipoImpreso, ImpresionTramitesTransmision impresionTramitesTransmision) {
		BigDecimal[] numExp = utiles.convertirStringArrayToBigDecimal(numsExpedientes);
		ResultBean result = new ResultBean();
		ArrayList<byte[]> listaByte = new ArrayList<byte[]>();
		for (int i = 0; !result.getError() && i < numExp.length; i++) {
			ResultBean rs = null;
			try {
				rs = impresionTramitesTransmision.imprimirTransmisionMandatos(numExp[i], numColegiado, idContrato, idUsuario, criteriosImprimir.getTipoRepresentacion(), tipoImpreso.getNombreEnum());
			} catch (OegamExcepcion e) {
				rs = new ResultBean();
				rs.setError(true);
				rs.setMensaje("Ha sucedido un error a la hora de imprimir el tramite");
				log.error("Ha sucedido un error a la hora de imprimir el tramite: " + e.getMessage(), numExp[i].toString());
			}
			if (rs == null || rs.getError()) {
				result.setError(true);
				result.addMensajeALista(numExp[i] + ": " + rs.getMensaje());
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

	@Override
	public ResultBean imprimirListadoBastidores(String[] numsExpedientes, String numColegiado, BigDecimal idContrato, TipoTramiteTrafico tipoTramite) {
		return imprimirListadoBastidores(numsExpedientes, numColegiado, idContrato, tipoTramite, new ImpresionTramitesTransmision());
	}

	private ResultBean imprimirListadoBastidores(String[] numsExpedientes, String numColegiado, BigDecimal idContrato, TipoTramiteTrafico tipoTramite,
			ImpresionTramitesTransmision impresionTramitesTransmision) {
		ResultBean result = new ResultBean();
		try {
			String validacion = ""; 
			String habilitado = gestorPropiedades.valorPropertie("habilitar.impresion.nuevas.transmisiones");
			if (("SI").equalsIgnoreCase(habilitado)) {
				validacion = getModeloTramiteTrafImpl().validarRelacionMatriculas(numsExpedientes);
			}
			if (validacion.equals("")) {
				List<String> listaExpedientes = new ArrayList<String>(Arrays.asList(numsExpedientes));
				result = impresionTramitesTransmision.impresionListadoBastidoresTransmision(listaExpedientes, numColegiado, idContrato, tipoTramite.getValorEnum());
				if (!result.getError()) {
					result.setListaMensajes(new ArrayList<String>());
				}
			} else {
				result.setError(true);
				result.addMensajeALista(validacion);
			}
		} catch (OegamExcepcion e) {
			result.setError(true);
			result.addMensajeALista("No se ha podido generar el documento");
			log.error("Hay errores al generar el listado de matriculas de Transmision");
			log.error(e);
		} catch (Throwable e) {
			log.error(e);
		}
		return result;
	}

	private ResultBean imprimir430(String[] numsExpedientes, String numColegiado, BigDecimal idContrato, BigDecimal idUsuario, TipoTramiteTrafico tipoTramite,
			ImpresionTramitesTransmision impresionTramitesTransmision) {
		ResultBean result = new ResultBean();
		if (numsExpedientes == null || numsExpedientes.length == 0) {
			result.setError(true);
			result.addMensajeALista("No hay trámites");
			return result;
		}

		ArrayList<byte[]> listaByte = new ArrayList<byte[]>();
		try {
			for (int i = 0; !result.getError() && i < numsExpedientes.length; i++) {
				ResultBean rs = impresionTramitesTransmision.impresionModelo430Transmision(numsExpedientes[i], numColegiado, idContrato, TipoImpreso.TransmisionModelo430.toString(), tipoTramite);
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
			log.error("Se ha producido un error a la hora de imprimir el tramite: " + e.getMessage());
		} catch (Throwable e) {
			result.setError(true);
			result.addMensajeALista("Se ha producido un error a la hora de imprimir el tramite.");
			log.error("Se ha producido un error a la hora de imprimir el tramite: " + e.getMessage());
		}

		if (result != null && !result.getError()) {
			result = servicioImpresion.generarResultBeanConArrayByteFinal(listaByte, TipoImpreso.TransmisionModelo430.toString() + ".pdf");
		}

		return result;
	}

	private ResultBean imprimir417(String[] numsExpedientes, String numColegiado, BigDecimal idContrato, BigDecimal idUsuario, TipoTramiteTrafico tipoTramite, Boolean tienePermisoImpresionPdf417,
			ImpresionTramitesTransmision impresionTramitesTransmision) {
		ResultBean result = new ResultBean();
		if (!tienePermisoImpresionPdf417) {
			result.setError(true);
			result.setMensaje(ConstantesPDF.NO_SE_PUEDE_IMPRIMIR_LA_MANCHA_PDF_PORQUE_NO_TIENE_PERMISO_PARA_ESTA_ACCION);
			return result;
		}

		for (String numExpediente : numsExpedientes) {
			if (!servicioTramiteTrafico.esImprimiblePdf(numExpediente)) {
				result.setError(true);
				result.setMensaje("El trámite " + numExpediente + " no se puede imprimir PDF, debe ser tramitado telemáticamente");
				return result;
			}
		}

		return imprimirTransmision(numsExpedientes, numColegiado, idContrato, idUsuario, tipoTramite, TipoImpreso.TransmisionPDF417, false, impresionTramitesTransmision);
	}

	private ResultBean imprimirBorradorPdf417(String[] numsExpedientes, String numColegiado, BigDecimal idContrato, BigDecimal idUsuario, TipoTramiteTrafico tipoTramite,
			ImpresionTramitesTransmision impresionTramitesTransmision) {
		return imprimirTransmision(numsExpedientes, numColegiado, idContrato, idUsuario, tipoTramite, TipoImpreso.TransmisionBorradorPDF417, false, impresionTramitesTransmision);
	}

	@Override
	public ResultBean imprimirTransmision(String[] numsExpedientes, String numColegiado, BigDecimal idContrato, BigDecimal idUsuario, TipoTramiteTrafico tipoTramite, TipoImpreso tipoImpreso,
			Boolean esProceso) {
		return imprimirTransmision(numsExpedientes, numColegiado, idContrato, idUsuario, tipoTramite, tipoImpreso, esProceso, new ImpresionTramitesTransmision());
	}

	private ResultBean imprimirTransmision(String[] numsExpedientes, String numColegiado, BigDecimal idContrato, BigDecimal idUsuario, TipoTramiteTrafico tipoTramite, TipoImpreso tipoImpreso,
			Boolean esProceso, ImpresionTramitesTransmision impresionTramitesTransmision) {
		ResultBean result = new ResultBean();
		if (numsExpedientes == null || numsExpedientes.length == 0) {
			result.setError(true);
			result.addMensajeALista("No hay trámites");
			return result;
		}

		ArrayList<byte[]> listaByte = new ArrayList<byte[]>();
		try {
			for (int i = 0; !result.getError() && i < numsExpedientes.length; i++) {
				ResultBean rs = impresionTramitesTransmision.impresionGeneralTransmision(numsExpedientes[i], numColegiado, tipoImpreso.toString(), idContrato, tipoTramite, idUsuario);
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
			if (esProceso) {
				result = servicioImpresion.generarResultBeanByteProceso(listaByte);
			} else {
				result = servicioImpresion.generarResultBeanConArrayByteFinal(listaByte, tipoImpreso.toString() + ".pdf");
			}
		}

		return result;
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
}
