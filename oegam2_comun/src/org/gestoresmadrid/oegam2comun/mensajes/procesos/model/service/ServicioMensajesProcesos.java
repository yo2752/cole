package org.gestoresmadrid.oegam2comun.mensajes.procesos.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.oegamComun.mensajesProcesos.view.dto.MensajesProcesosDto;

public interface ServicioMensajesProcesos extends Serializable {

	List<MensajesProcesosDto> getListaMensajesProcesos();

	void guardarMensajeProceso(MensajesProcesosDto meProcesosDto);

	MensajesProcesosDto getMensaje(String codigo);

	boolean tratarMensaje(String codigo, String mensaje);
}
