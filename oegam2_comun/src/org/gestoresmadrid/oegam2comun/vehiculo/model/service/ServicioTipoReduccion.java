package org.gestoresmadrid.oegam2comun.vehiculo.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.vehiculo.model.vo.TipoReduccionVO;

public interface ServicioTipoReduccion extends Serializable {
	TipoReduccionVO getTipoReduccion(String tipoReduccion);
	List<TipoReduccionVO> getListaTiposReducciones();
}