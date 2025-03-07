package org.gestoresmadrid.core.registradores.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.registradores.model.vo.RegistroFueraSecuenciaVO;

public interface RegistroFueraSecuenciaDao extends GenericDao<RegistroFueraSecuenciaVO>, Serializable {

	List<RegistroFueraSecuenciaVO> getRegistrosFueraSecuencia(BigDecimal idTramiteRegistro);

	RegistroFueraSecuenciaVO getRegistroFueraSecuencia(Long idRegistroFueraSecuencia);
}
