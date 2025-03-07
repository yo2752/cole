package trafico.servicio.implementacion;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import escrituras.beans.ResultBean;
import trafico.modelo.impl.ModeloTramiteTrafImpl;
import trafico.modelo.interfaz.ModeloTramiteTrafInterface;
import trafico.servicio.interfaz.ServicioImprimirTraficoInt;
import trafico.utiles.PdfMaker;
import trafico.utiles.enumerados.TipoImpreso;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioImprimirTraficoImpl implements ServicioImprimirTraficoInt {

	@Autowired
	private ImprimirNRE06ServicioI imprimirSolicitudNRE06Servicio;

	@Autowired
	private ImprimirSolicitu05Servicio imprimirSolicitud05;

	@Autowired
	ServicioContrato servicioContratoImpl;

	@Autowired
	ServicioTramiteTrafico servicioTramiteTrafico;

	@Autowired
	Utiles utiles;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioImprimirTraficoImpl.class);

	@Override
	public boolean estaEnEstadoValidoParaImprimirEsteTipo(String[] numExpedientes, String impreso) {
		EstadoTramiteTrafico[] estados = null;
		if (TipoImpreso.MatriculacionBorradorPDF417.toString().equals(impreso)) {
			estados = new EstadoTramiteTrafico[]{EstadoTramiteTrafico.Iniciado, EstadoTramiteTrafico.Validado_Telematicamente, EstadoTramiteTrafico.Validado_PDF, EstadoTramiteTrafico.Pendiente_Autorizacion_Colegio, EstadoTramiteTrafico.Finalizado_PDF, EstadoTramiteTrafico.LiberadoEEFF};
		} else if (TipoImpreso.MatriculacionPDFPresentacionTelematica.toString().equals(impreso)
				|| TipoImpreso.MatriculacionPermisoTemporalCirculacion.toString().equals(impreso)
				|| TipoImpreso.FichaTecnica.toString().equals(impreso)) {
			estados = new EstadoTramiteTrafico[]{EstadoTramiteTrafico.Finalizado_Telematicamente, EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso};
		} else if (TipoImpreso.TransmisionDocumentosTelematicos.toString().equals(impreso)) {
			estados = new EstadoTramiteTrafico[]{EstadoTramiteTrafico.Finalizado_Telematicamente, EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso, EstadoTramiteTrafico.Informe_Telematico};
		} else if (TipoImpreso.ConsultaEITV.toString().equals(impreso)) {
			estados = new EstadoTramiteTrafico[]{EstadoTramiteTrafico.Iniciado};
		} else if (TipoImpreso.InformeBajaTelematica.toString().equals(impreso)) {
			estados = new EstadoTramiteTrafico[]{EstadoTramiteTrafico.Finalizado_Telematicamente, EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso};
		} else if (TipoImpreso.MatriculacionListadoBastidores.toString().equals(impreso)) {
			estados = new EstadoTramiteTrafico[]{EstadoTramiteTrafico.Finalizado_Excel, EstadoTramiteTrafico.Finalizado_Excel_Impreso, EstadoTramiteTrafico.Finalizado_PDF, EstadoTramiteTrafico.Finalizado_Telematicamente, EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso};
		}

		if (estados != null) {
			ResultBean result = servicioTramiteTrafico.comprobarEstadosTramites(utiles.convertirStringArrayToBigDecimal(numExpedientes), estados);
			if (result != null) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean estaEnEstadoValidoParaImprimirMATE(String[] numExpedientes, String impreso) {
		EstadoTramiteTrafico[] estados = null;
		if (impreso.equals(TipoImpreso.MatriculacionPDF417.toString())) {
			estados = new EstadoTramiteTrafico[]{EstadoTramiteTrafico.Finalizado_PDF,EstadoTramiteTrafico.Pendiente_Autorizacion_Colegio};
		} else if (impreso.equals(TipoImpreso.MatriculacionPDFPresentacionTelematica.toString())
				|| impreso.equals(TipoImpreso.MatriculacionPermisoTemporalCirculacion.toString())
				|| impreso.equals(TipoImpreso.FichaTecnica.toString())) {
			estados = new EstadoTramiteTrafico[]{EstadoTramiteTrafico.Finalizado_Telematicamente,EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso};
		}

		if (estados != null) {
			ResultBean result = servicioTramiteTrafico.comprobarEstadosTramites(utiles.convertirStringArrayToBigDecimal(numExpedientes), estados);
			if (result != null) {
				return false;
			}
		}
		return true;
	}

	@Override
	public ResultBean cambiarEstadoTramiteImprimir(String tipoTramite, String[] numExpedientes, String tipoImpreso, BigDecimal idUsuario) {
		boolean resultado = false;
		ModeloTramiteTrafInterface modeloTramite = new ModeloTramiteTrafImpl();
		ResultBean resultBean = new ResultBean();
		if (tipoImpreso.equals(TipoImpreso.MatriculacionPDF417.toString())) {
			BigDecimal[] numExp = new BigDecimal[numExpedientes.length];
			for (int i = 0; i < numExpedientes.length; i++) {
				numExp[i] = new BigDecimal(numExpedientes[i]);
			}
			resultado = modeloTramite.cambiarEstado(numExp, EstadoTramiteTrafico.Finalizado_PDF, idUsuario);
		}
		if (tipoImpreso.equals(TipoImpreso.MatriculacionPDFPresentacionTelematica.toString())) {
			BigDecimal[] numExp = new BigDecimal[numExpedientes.length];
			for (int i = 0; i < numExpedientes.length; i++) {
				numExp[i] = new BigDecimal(numExpedientes[i]);
			}
			resultado = modeloTramite.cambiarEstado(numExp, EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso, idUsuario);
		}
		if (!resultado) {
			resultBean.setError(true);
			resultBean.setMensaje("Error no se ha podido cambiar de estado");
		} else {
			resultBean.setError(false);
		}
		return resultBean;
	}

	@Override
	public ResultBean imprimirNRE06(String[] numExpedientes){
		String nombreFichero = "NRE06";

		ResultBean result = new ResultBean();
		if (numExpedientes.length == 0) {
			result.setError(true);
			result.addMensajeALista("No hay trámites");
			return result;
		}
		ArrayList<byte[]> listaByte = new ArrayList<>();
		for (int i = 0; !result.getError() && i < numExpedientes.length; i++) {
			try {
				ResultBean r = null;
				log.info("Generamos la solicitud para el expediente: " + numExpedientes[i]);
				r = imprimirSolicitudNRE06Servicio.generarSolicitud(numExpedientes[i]);

				if (r == null || r.getError()) {
					result.setError(true);
					result.addMensajeALista(numExpedientes[i]+": "+ r.getMensaje());
				} else {
					byte[] byte1 = (byte[]) r.getAttachment(ResultBean.TIPO_PDF);
					listaByte.add(byte1);
				}
			} catch (Throwable e) {
				log.error("Se ha producido un error imprimiendo NRE06: ", e);
				result.setError(true);
				result.addMensajeALista("Se ha producido un error. Inténtelo más tarde.");
			}
		}
		if (result != null && !result.getError()) {
			byte[] byte1 = PdfMaker.concatenarPdf(listaByte);
			result.addAttachment(ResultBean.TIPO_PDF, byte1);
			result.addAttachment(ResultBean.NOMBRE_FICHERO, nombreFichero+".pdf");
		}
		return result;
	}

	@Override
	public ResultBean imprimirSolicitud05(String[] numExpedientes){
		String nombreFichero = "Solicitud05";

		ResultBean result = new ResultBean();
		if (numExpedientes.length == 0) {
			result.setError(true);
			result.addMensajeALista("No hay trámites");
			return result;
		}
		ArrayList<byte[]> listaByte = new ArrayList<>();
		for (int i = 0; !result.getError() && i < numExpedientes.length; i++) {
			try {
				ResultBean r = null;
				log.info("Generamos la solicitud para el expediente: " + numExpedientes[i]);
				r = imprimirSolicitud05.generarSolicitud(numExpedientes[i]);

				if (r == null || r.getError()) {
					result.setError(true);
					result.addMensajeALista(numExpedientes[i]+": "+ r.getMensaje());
				} else {
					byte[] byte1 = (byte[]) r.getAttachment(ResultBean.TIPO_PDF);
					listaByte.add(byte1);
				}
			} catch (Throwable e) {
				log.error("Se ha producido un error imprimiendo solicitud 05: ", e);
				result.setError(true);
				result.addMensajeALista("Se ha producido un error. Inténtelo más tarde.");
			}
		}
		if (result != null && !result.getError()) {
			byte[] byte1 = PdfMaker.concatenarPdf(listaByte);
			result.addAttachment(ResultBean.TIPO_PDF, byte1);
			result.addAttachment(ResultBean.NOMBRE_FICHERO, nombreFichero+".pdf");
		}
		return result;
	}

	public ImprimirNRE06ServicioI getImprimirSolicitudNRE06Servicio() {
		return imprimirSolicitudNRE06Servicio;
	}

	public void setImprimirSolicitudNRE06Servicio(ImprimirNRE06ServicioI imprimirSolicitudNRE06Servicio) {
		this.imprimirSolicitudNRE06Servicio = imprimirSolicitudNRE06Servicio;
	}

	public ServicioContrato getServicioContratoImpl() {
		return servicioContratoImpl;
	}

	public void setServicioContratoImpl(ServicioContrato servicioContratoImpl) {
		this.servicioContratoImpl = servicioContratoImpl;
	}
}