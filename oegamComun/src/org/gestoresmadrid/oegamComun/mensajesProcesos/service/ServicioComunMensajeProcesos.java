package org.gestoresmadrid.oegamComun.mensajesProcesos.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.oegamComun.mensajesProcesos.view.dto.MensajesProcesosDto;

public interface ServicioComunMensajeProcesos extends Serializable{

	List<MensajesProcesosDto> getListaMensajesProcesos();

	void guardarMensajeProceso(MensajesProcesosDto mensajesProcesosDto);

	MensajesProcesosDto getMensaje(String codigo);

	boolean tratarMensaje(String codigo, String mensaje);

}
