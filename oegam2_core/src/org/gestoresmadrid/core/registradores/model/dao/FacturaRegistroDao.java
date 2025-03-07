package org.gestoresmadrid.core.registradores.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.registradores.model.vo.FacturaRegistroVO;



public interface FacturaRegistroDao extends GenericDao<FacturaRegistroVO>, Serializable {

	public List<FacturaRegistroVO> getFacturasRegistroPorTramite(BigDecimal idTramiteRegistro);

	public FacturaRegistroVO getFacturaRegistro(Long id);

}
