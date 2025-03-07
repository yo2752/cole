package org.gestoresmadrid.core.registradores.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.registradores.model.vo.MinutaRegistroVO;



public interface MinutaRegistroDao extends GenericDao<MinutaRegistroVO>, Serializable {

	public MinutaRegistroVO getMinutaRegistro(String id);

	public List<MinutaRegistroVO> getMinutasRegistroPorTramite(BigDecimal idTramiteRegistro);

}
