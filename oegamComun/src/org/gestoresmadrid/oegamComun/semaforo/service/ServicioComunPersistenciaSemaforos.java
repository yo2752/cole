package org.gestoresmadrid.oegamComun.semaforo.service;

import org.gestoresmadrid.core.evolucionSemaforo.model.enumerados.OperacionEvolSemaforo;
import org.gestoresmadrid.oegamComun.semaforo.view.bean.ResultadoSemaforosBean;
import org.gestoresmadrid.oegamComun.semaforo.view.dto.SemaforoDto;

public interface ServicioComunPersistenciaSemaforos {
	public ResultadoSemaforosBean guardarSemaforo(SemaforoDto semaforo, OperacionEvolSemaforo operacion, Long idUsuario) throws Exception;
}
