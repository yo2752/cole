package org.gestoresmadrid.core.codigoIae.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.codigoIae.model.vo.CodigoIAEVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface CodigoIAEDao extends GenericDao<CodigoIAEVO>, Serializable {

	List<CodigoIAEVO> listaCodigosIAE();
}
