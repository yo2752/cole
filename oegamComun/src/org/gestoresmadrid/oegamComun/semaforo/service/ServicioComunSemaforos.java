package org.gestoresmadrid.oegamComun.semaforo.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.evolucionSemaforo.model.enumerados.OperacionEvolSemaforo;
import org.gestoresmadrid.oegamComun.semaforo.view.bean.ResultadoSemaforosBean;
//import org.gestoresmadrid.core.evolucionSemaforo.model.enumerados.OperacionEvolSemaforo;
import org.gestoresmadrid.oegamComun.semaforo.view.dto.SemaforoDto;

public interface ServicioComunSemaforos extends Serializable {

	public SemaforoDto obtenerSemaforo(SemaforoDto semaforo);
	public SemaforoDto obtenerSemaforo(Long idSemaforo);
	public List<SemaforoDto> obtenerListaSemaforosSes(String nodo);
	public ResultadoSemaforosBean guardarSemaforo(SemaforoDto semaforo, SemaforoDto semaforoExist, OperacionEvolSemaforo operacion, Long idUsuario)
		throws Exception;
	public List<SemaforoDto> obtenerSemaforosSelec(String[] codigosSelec);
	public ResultadoSemaforosBean cambiarEstadoSemaforos(List<SemaforoDto> listaSemaforos, Integer estado, Long idUsuario) throws Exception;
	public ResultadoSemaforosBean deshabilitarSemaforos(List<SemaforoDto> listaSemaforos, Long idUsuario) throws Exception;
	
}
