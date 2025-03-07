package org.gestoresmadrid.core.general.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.general.model.vo.GrupoVO;
import org.gestoresmadrid.core.model.beans.DatoMaestroBean;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface GrupoDao extends GenericDao<GrupoVO>, Serializable {

	List<GrupoVO> getGrupos();

	GrupoVO getGrupo(String idGrupo);

	List<DatoMaestroBean> getComboGrupos();

	List<String> getDescripcionGrupo(String idGrupo);
}
