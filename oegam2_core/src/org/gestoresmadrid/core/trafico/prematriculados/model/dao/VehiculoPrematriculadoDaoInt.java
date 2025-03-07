package org.gestoresmadrid.core.trafico.prematriculados.model.dao;

import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.trafico.prematriculados.model.vo.VehiculoPrematriculadoVO;

public interface VehiculoPrematriculadoDaoInt extends GenericDao<VehiculoPrematriculadoVO> {

	List<VehiculoPrematriculadoVO> buscar (Long[] ids, String numColegiado);
	
	VehiculoPrematriculadoVO buscar(Long id, String numColegiado);
}
