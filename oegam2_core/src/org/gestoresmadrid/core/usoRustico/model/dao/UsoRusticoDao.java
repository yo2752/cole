package org.gestoresmadrid.core.usoRustico.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.usoRustico.model.vo.UsoRusticoVO;

public interface UsoRusticoDao extends Serializable, GenericDao<UsoRusticoVO>{

	List<UsoRusticoVO> getListaUsoRusticoPorTipo(String tipoUso);

}
