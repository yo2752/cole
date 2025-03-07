package org.gestoresmadrid.core.modelos.model.dao;

import java.io.Serializable;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.modelos.model.vo.TipoImpuestoVO;

public interface TipoImpuestoDao extends GenericDao<TipoImpuestoVO>, Serializable{

	TipoImpuestoVO getTipoimpuestoPorConceptoYModelo(String concepto, String modelo, String version);
	
	TipoImpuestoVO getTipoimpuestoPorConcepto(String concepto);

}
