package org.gestoresmadrid.core.modelos.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.modelos.model.vo.ModeloVO;
import org.gestoresmadrid.core.modelos.model.vo.TipoDocumentoEscrVO;

public interface TipoDocumentoEscrDao extends GenericDao<TipoDocumentoEscrVO>, Serializable{

	List<TipoDocumentoEscrVO> getListaPorModelo(ModeloVO modelo);

}
