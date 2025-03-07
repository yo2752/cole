package org.gestoresmadrid.oegamComun.semaforo.service;

import java.io.Serializable;

import org.gestoresmadrid.oegamComun.semaforo.view.bean.ResultadoSemaforosBean;
import org.gestoresmadrid.oegamComun.semaforo.view.dto.SemaforoDto;

public interface ServicioComunValidacionSemaforos extends Serializable {
	public ResultadoSemaforosBean validacionGuardarSemaforo(SemaforoDto semaforo, SemaforoDto semaforoExist);
}
