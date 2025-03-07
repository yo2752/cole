package org.gestoresmadrid.oegam2.intervinienteTrafico.controller.action;

import escrituras.beans.ResultBean;
import general.acciones.ActionBase;

import java.math.BigDecimal;

import org.gestoresmadrid.oegam2comun.intervinienteTrafico.model.service.ServicioIntervinienteTrafico;
import org.gestoresmadrid.oegamComun.interviniente.view.dto.IntervinienteTraficoDto;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class DetalleIntervinienteAction extends ActionBase {

	private static final long serialVersionUID = -1534915784493555347L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(DetalleIntervinienteAction.class);

	@Autowired
	private ServicioIntervinienteTrafico servicioIntervinienteTrafico;

	private String nif;
	private String numColegiado;
	private BigDecimal numExpediente;
	private String tipoInterviniente;

	private IntervinienteTraficoDto intervinienteTrafico;

	public String detalle() throws Throwable {
		intervinienteTrafico = servicioIntervinienteTrafico.getIntervinienteTraficoDto(numExpediente, tipoInterviniente, nif, numColegiado);
		return SUCCESS;
	}

	public String modificacion() throws Throwable {
		ResultBean result = servicioIntervinienteTrafico.guardarMantenimientoInterviniente(intervinienteTrafico);

		if (result.getError()) {
			String mensajeError = "Se ha producido un error al guardar el interviniente: ";
			if (result.getListaMensajes() != null && !result.getListaMensajes().isEmpty()) {
				for (String mensaje : result.getListaMensajes()) {
					mensajeError += " - " + mensaje;
				}
			} else {
				mensajeError += result.getMensaje();
			}
			addActionError(mensajeError);
		} else {
			addActionMessage("El interviniente se ha guardado correctamente. " + (null != result.getMensaje() ? result.getMensaje() : ""));
		}
		return SUCCESS;
	}

	public ServicioIntervinienteTrafico getServicioIntervinienteTrafico() {
		return servicioIntervinienteTrafico;
	}

	public void setServicioIntervinienteTrafico(ServicioIntervinienteTrafico servicioIntervinienteTrafico) {
		this.servicioIntervinienteTrafico = servicioIntervinienteTrafico;
	}
	
	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public IntervinienteTraficoDto getIntervinienteTrafico() {
		return intervinienteTrafico;
	}

	public void setIntervinienteTrafico(IntervinienteTraficoDto intervinienteTrafico) {
		this.intervinienteTrafico = intervinienteTrafico;
	}

	public String getTipoInterviniente() {
		return tipoInterviniente;
	}

	public void setTipoInterviniente(String tipoInterviniente) {
		this.tipoInterviniente = tipoInterviniente;
	}
}