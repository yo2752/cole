package org.gestoresmadrid.core.legalizacion.model.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.legalizacion.model.vo.LegalizacionCitaVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface LegalizacionDao extends GenericDao<LegalizacionCitaVO>, Serializable {

	public LegalizacionCitaVO getLegalizacionId(Integer idPeticion);

	public boolean esPosiblePeticion(String numColegiado);

	public List<LegalizacionCitaVO> listadoDiario(String numColegiado, Date fechaLegalizacion);

}
