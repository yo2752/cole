package org.gestoresmadrid.oegam2comun.impresion;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Set;

import org.gestoresmadrid.core.model.enumerados.TipoImpreso;
import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.oegam2comun.impresion.util.ImpresionGeneral;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoSolInfo;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.SolicitudInformeVehiculoDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafSolInfoDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import trafico.beans.utiles.CampoPdfBean;
import trafico.utiles.ConstantesPDF;
import trafico.utiles.PdfMaker;
import trafico.utiles.UtilResources;
import utilidades.web.OegamExcepcion;

public class ImpresionTramitesSolicitud extends ImpresionGeneral {

	private TramiteTrafSolInfoDto detalleSolicitud;

	private ArrayList<CampoPdfBean> camposFormateados;
	private Set<String> camposPlantilla;

	private PdfMaker pdf;
	private byte[] bytePdf;

	private String mensaje;

	private String ruta;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	public ResultBean imprimirMandatoPorExpediente(BigDecimal numExpediente, String tipoImpreso) throws OegamExcepcion, Throwable {
		ResultBean resultadoMetodo = new ResultBean();

		camposFormateados = new ArrayList<CampoPdfBean>();
		pdf = new PdfMaker();

		mensaje = "";
		boolean error = false;
		// --------------------------------------------------------------------------------

		obtenerDetalleTramiteSolicitud(numExpediente);

		if (detalleSolicitud != null) {
			if (detalleSolicitud.getSolicitante() == null || detalleSolicitud.getSolicitante().getPersona() == null || detalleSolicitud.getSolicitante().getPersona().getNif() == null
					|| detalleSolicitud.getSolicitante().getPersona().getNif().isEmpty()) {
				if (TipoImpreso.MatriculacionMandatoEspecifico.toString().equals(tipoImpreso)) {
					resultadoMetodo = new ResultBean(true, "No hay Solicitante. No se puede obtener Mandato Específico.");
				} else if (TipoImpreso.MatriculacionMandatoGenerico.toString().equals(tipoImpreso)) {
					resultadoMetodo = new ResultBean(true, "No hay Solicitante. No se puede obtener Mandato Genérico.");
				}
				return resultadoMetodo;
			}

			calcularRuta(tipoImpreso);
			bytePdf = pdf.abrirPdf(ruta);
			camposPlantilla = pdf.getAllFields(bytePdf);
			camposFormateados.addAll(obtenerValoresMandatoMatwDuplicadoYSol(ConstantesPDF._11, detalleSolicitud.getSolicitante(), null, camposPlantilla, detalleSolicitud.getIdContrato(),
					detalleSolicitud.getNumColegiado()));
			procesarAsuntos();
			bytePdf = pdf.setCampos(bytePdf, camposFormateados);

		} else {
			error = true;
			mensaje = "Los datos del trámite están vacíos";
		}

		if (error == true || bytePdf == null) {
			error = true;
			mensaje += ". Puede que no esté completo el trámite impreso.";
		}

		resultadoMetodo.setError(error);
		resultadoMetodo.setMensaje(mensaje);
		resultadoMetodo.addAttachment(ResultBean.TIPO_PDF, bytePdf);
		return resultadoMetodo;
	}

	private void procesarAsuntos() {
		if (camposPlantilla.contains(ConstantesPDF.ID_ASUNTO1_MOTIVO)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_ASUNTO1_MOTIVO, "Solicitud de Informes: ", false, false, ConstantesPDF._11));
		}

		String asuntoExplicacion = "";
		int cont = 1;
		if (detalleSolicitud.getSolicitudes() != null) {
			for (SolicitudInformeVehiculoDto solicitudVehiculo : detalleSolicitud.getSolicitudes()) {
				if (solicitudVehiculo.getVehiculo().getMatricula() != null && !solicitudVehiculo.getVehiculo().getMatricula().isEmpty()) {
					asuntoExplicacion += solicitudVehiculo.getVehiculo().getMatricula();
				} else if (solicitudVehiculo.getVehiculo().getBastidor() != null && !solicitudVehiculo.getVehiculo().getBastidor().isEmpty()) {
					asuntoExplicacion += solicitudVehiculo.getVehiculo().getBastidor();
				} else if (solicitudVehiculo.getVehiculo().getNive() != null && !solicitudVehiculo.getVehiculo().getBastidor().isEmpty()) {
					asuntoExplicacion += solicitudVehiculo.getVehiculo().getNive();
				}
				if (cont < detalleSolicitud.getSolicitudes().size()) {
					asuntoExplicacion += ", ";
				}
				cont++;
			}
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_ASUNTO1_EXPLICACION)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_ASUNTO1_EXPLICACION, asuntoExplicacion, false, false, ConstantesPDF._11));
		}
	}

	private void calcularRuta(String tipoImpreso) throws OegamExcepcion {
		UtilResources util = new UtilResources();
		ruta = gestorPropiedades.valorPropertie(ConstantesPDF.RUTA_PLANTILLAS);
		if (tipoImpreso.equals(TipoImpreso.MatriculacionMandatoGenerico.toString())) {
			ruta = util.getFilePath(ruta + TipoImpreso.MatriculacionMandatoGenerico.getNombreEnum());
		} else if (tipoImpreso.equals(TipoImpreso.MatriculacionMandatoEspecifico.toString())) {
			ruta = util.getFilePath(ruta + TipoImpreso.MatriculacionMandatoEspecifico.getNombreEnum());
		}
	}

	private void obtenerDetalleTramiteSolicitud(BigDecimal numExpediente) throws Throwable {
		detalleSolicitud = new TramiteTrafSolInfoDto();
		ServicioTramiteTraficoSolInfo servicioTramiteTraficoSolInfo = ContextoSpring.getInstance().getBean(ServicioTramiteTraficoSolInfo.class);
		ResultBean result = servicioTramiteTraficoSolInfo.getTramiteTraficoSolInfo(numExpediente);
		detalleSolicitud = (TramiteTrafSolInfoDto) result.getAttachment(ServicioTramiteTraficoSolInfo.TRAMITE_DETALLE);
	}
}
