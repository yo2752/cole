package org.gestoresmadrid.core.registradores.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.registradores.model.vo.InmuebleVO;

public interface InmuebleDao extends GenericDao<InmuebleVO>, Serializable {

	InmuebleVO getInmueble(Long idInmueble);

	List<InmuebleVO> getInmuebles(BigDecimal idTramiteRegistro);

	List<InmuebleVO> getInmuebles(Long idBien);
}
