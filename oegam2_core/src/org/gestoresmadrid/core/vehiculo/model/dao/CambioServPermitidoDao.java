package org.gestoresmadrid.core.vehiculo.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.vehiculo.model.vo.CambioServPermitidoVO;

public interface CambioServPermitidoDao extends GenericDao<CambioServPermitidoVO>, Serializable{

	List<CambioServPermitidoVO> getCambioPermitido(String idServicioNuevo, String idServicioAnterior);

}
