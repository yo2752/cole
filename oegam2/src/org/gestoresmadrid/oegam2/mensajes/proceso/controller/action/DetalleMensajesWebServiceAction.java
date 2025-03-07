package org.gestoresmadrid.oegam2.mensajes.proceso.controller.action;

import general.acciones.ActionBase;

import org.gestoresmadrid.oegam2comun.mensajes.procesos.model.service.ServicioMensajesProcesos;
import org.gestoresmadrid.oegamComun.mensajesProcesos.view.dto.MensajesProcesosDto;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class DetalleMensajesWebServiceAction extends ActionBase {

	private static final long serialVersionUID = 3927277385657750883L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(DetalleMensajesWebServiceAction.class);

	@Autowired
	private ServicioMensajesProcesos servicioMensajesProcesos;

	private String codigo;

	private MensajesProcesosDto mensajesProcesos;

	public String detalle() throws Throwable {
		if (codigo != null && !codigo.isEmpty()) {
			mensajesProcesos = servicioMensajesProcesos.getMensaje(codigo);
		}
		return SUCCESS;
	}

	public String guardar() throws Throwable {
		try {
			servicioMensajesProcesos.guardarMensajeProceso(mensajesProcesos);
			addActionMessage("Mensaje guardado correctamente");
		} catch (Exception e) {
			log.error("Error al guardar el mensaje", e);
			addActionError("Error al guardar el mensaje: " + e.getMessage());
		}
		return SUCCESS;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public MensajesProcesosDto getMensajesProcesos() {
		return mensajesProcesos;
	}

	public void setMensajesProcesos(MensajesProcesosDto mensajesProcesos) {
		this.mensajesProcesos = mensajesProcesos;
	}
}
