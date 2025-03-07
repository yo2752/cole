package trafico.ivtm.acciones;

//TODO MPC. Cambio IVTM. Esta clase es la que está bien.
import general.acciones.ActionBase;

import java.math.BigDecimal;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.gestoresmadrid.oegam2comun.trafico.dto.ivtm.IvtmMatriculacionDto;

import trafico.ivtm.servicio.ServicioIVTMMatriculacionImpl;
import trafico.ivtm.servicio.ServicioIVTMMatriculacionIntf;
import trafico.modelo.ivtm.IVTMModeloMatriculacionInterface;
import trafico.modelo.ivtm.impl.IVTMModeloMatriculacionImpl;
import trafico.utiles.enumerados.EstadoIVTM;
import trafico.utiles.enumerados.RespuestaPagoIVTM;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class RespuestaPagoIVTMAction extends ActionBase implements SessionAware {

	private static final long serialVersionUID = 4950269075248870773L;

	protected static final ILoggerOegam log = LoggerOegam.getLogger(RespuestaPagoIVTMAction.class);

	private static final String CONSULTA_TRAMITE_MATRICULACION_DETALLE = "consultaTramiteMatriculacionDetalle";
	private static final String CONSULTA_TRAMITE_MATRICULACION_INICIO = "consultaTramiteMatriculacionInicio";

	private String numExpediente;

	private boolean errorPagoIVTM;
	private String respuestaPagoIVTM;

	private IVTMModeloMatriculacionInterface ivtmModelo;

	private ServicioIVTMMatriculacionIntf servicioIVTM;

	public String vueltaPagoConsultaIVTM() throws Throwable {
		vueltaPagoIVTM();
		return CONSULTA_TRAMITE_MATRICULACION_INICIO;
	}

	public String vueltaPagoTramiteIVTM() throws Throwable {
		vueltaPagoIVTM();
		return CONSULTA_TRAMITE_MATRICULACION_DETALLE;
	}

	private void vueltaPagoIVTM() throws Throwable {
		try {
			String codigoPago = ServletActionContext.getRequest().getParameter("codigo");
			errorPagoIVTM = true;
			if (codigoPago != null) {
				respuestaPagoIVTM = RespuestaPagoIVTM.convertirTexto(codigoPago);
				IvtmMatriculacionDto ivtmMatriculacionDto = getIvtmModelo().buscarIvtmNumExp(new BigDecimal(numExpediente));
				ivtmMatriculacionDto.setCodigoRespuestaPago(RespuestaPagoIVTM.convertir(codigoPago));

				if (codigoPago.equals(RespuestaPagoIVTM.Correcto.getValorEnum())) {
					ivtmMatriculacionDto.setEstadoIvtm(new BigDecimal(EstadoIVTM.Pagado.getValorEnum()));
					errorPagoIVTM = false;
				}
				getServicioIVTM().guardarIVTM(ivtmMatriculacionDto);
			}
		} catch (Exception e) {
			errorPagoIVTM = true;
			respuestaPagoIVTM = "Error al procesar la respuesta del pago";
			log.error(e.getMessage());
		}
	}

	public String getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(String numExpediente) {
		this.numExpediente = numExpediente;
	}

	public IVTMModeloMatriculacionInterface getIvtmModelo() {
		if (ivtmModelo == null) {
			ivtmModelo = new IVTMModeloMatriculacionImpl();
		}
		return ivtmModelo;
	}

	public void setIvtmModelo(IVTMModeloMatriculacionInterface ivtmModelo) {
		this.ivtmModelo = ivtmModelo;
	}

	public ServicioIVTMMatriculacionIntf getServicioIVTM() {
		if (servicioIVTM == null) {
			servicioIVTM = new ServicioIVTMMatriculacionImpl();
		}
		return servicioIVTM;
	}

	public void setServicioIVTM(ServicioIVTMMatriculacionIntf servicioIVTM) {
		this.servicioIVTM = servicioIVTM;
	}

	public boolean isErrorPagoIVTM() {
		return errorPagoIVTM;
	}

	public void setErrorPagoIVTM(boolean errorPagoIVTM) {
		this.errorPagoIVTM = errorPagoIVTM;
	}

	public String getRespuestaPagoIVTM() {
		return respuestaPagoIVTM;
	}

	public void setRespuestaPagoIVTM(String respuestaPagoIVTM) {
		this.respuestaPagoIVTM = respuestaPagoIVTM;
	}
}
