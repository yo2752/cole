package org.gestoresmadrid.core.licencias.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.licencias.model.vo.LcDireccionVO;
import org.gestoresmadrid.core.licencias.model.vo.LcPersonaDireccionVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface LcPersonaDireccionDao extends GenericDao<LcPersonaDireccionVO>, Serializable {

	List<LcPersonaDireccionVO> getPersonaDireccionPorNif(String numColegiado, String nif);

	LcPersonaDireccionVO buscarDireccionExistente(LcDireccionVO direccion, String numColegiado, String nif);
}
