package org.gestoresmadrid.core.registradores.model.dao;

import java.io.Serializable;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.registradores.model.vo.ClausulaVO;




public interface ClausulaDao extends GenericDao<ClausulaVO>, Serializable {

	public ClausulaVO getClausula(String id);

}
