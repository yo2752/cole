package org.gestoresmadrid.core.registradores.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.registradores.model.vo.EntidadCancelacionVO;




public interface EntidadCancelacionDao extends GenericDao<EntidadCancelacionVO>, Serializable {

	EntidadCancelacionVO buscarPorContratoNif(BigDecimal idContrato, String cifEntidad);
	
}
