package org.gestoresmadrid.core.modelos.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.modelos.model.vo.ConceptoVO;
import org.gestoresmadrid.core.modelos.model.vo.ModeloVO;

public interface ConceptoDao extends GenericDao<ConceptoVO>, Serializable{

	List<ConceptoVO> getListaConceptosPorModelo(ModeloVO modeloVO);

	ConceptoVO getConceptoPorAbreviatura(String abreviatura);

	List<ConceptoVO> getListaTodosConceptos(String modelo, String version);

}
