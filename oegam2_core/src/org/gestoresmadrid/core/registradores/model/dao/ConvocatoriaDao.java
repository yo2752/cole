package org.gestoresmadrid.core.registradores.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.registradores.model.vo.ConvocatoriaVO;

public interface ConvocatoriaDao extends GenericDao<ConvocatoriaVO>, Serializable {

	ConvocatoriaVO getConvocatoria(BigDecimal idTramiteRegistro, Long idReunion);
}
