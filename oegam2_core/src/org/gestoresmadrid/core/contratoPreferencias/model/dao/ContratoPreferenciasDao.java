package org.gestoresmadrid.core.contratoPreferencias.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.general.model.vo.ContratoPreferenciaVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface ContratoPreferenciasDao extends GenericDao<ContratoPreferenciaVO>, Serializable {

	BigDecimal obtenerOrdenDocBase(Long idContrato);

}
