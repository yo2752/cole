package org.gestoresmadrid.core.administracion.model.dao;

import java.util.List;

import org.gestoresmadrid.core.administracion.model.enumerados.EstadoRecargaCacheEnum;
import org.gestoresmadrid.core.administracion.model.vo.RecargaCacheVO;

public interface RecargaCacheDao {
	
	public void guardarPeticion(RecargaCacheVO peticion);
	
	public List<RecargaCacheVO> recuperarSolicitudesPendientes();
	
	public void marcarPeticionTratada(RecargaCacheVO peticion, EstadoRecargaCacheEnum estado);

}
