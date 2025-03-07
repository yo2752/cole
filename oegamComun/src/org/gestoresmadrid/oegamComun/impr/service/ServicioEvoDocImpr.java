package org.gestoresmadrid.oegamComun.impr.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.impr.model.vo.EvolucionDocImprVO;


public interface ServicioEvoDocImpr extends Serializable{

	void guardarEvolucion(Long docId, String tipo, String operacion, String estadoAnt, String estadoNuevo, Long idUsuario);

	void borrarEvolucionImpr(Long docId);

	List<EvolucionDocImprVO> obtenerEvolucionDocImpr(Long docId, Boolean completo);

}
