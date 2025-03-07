package org.gestoresmadrid.core.registradores.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.registradores.model.vo.EstablecimientoRegistroVO;


public interface EstablecimientoDao extends GenericDao<EstablecimientoRegistroVO>, Serializable {

	public EstablecimientoRegistroVO getEstablecimientoRegistro(String id);
	public EstablecimientoRegistroVO getEstablecimientoPorPropiedad(BigDecimal id);

}
