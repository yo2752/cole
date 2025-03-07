package org.gestoresmadrid.core.vehiculo.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.vehiculo.model.vo.DirectivaCeeVO;

public interface DirectivaCeeDao extends GenericDao<DirectivaCeeVO>, Serializable {

	List<DirectivaCeeVO> getListaDirectivasCee(String criterioConstruccion);

	DirectivaCeeVO getDirectivaPorId(String idDirectivaCEE);
}
