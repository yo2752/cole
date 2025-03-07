package org.gestoresmadrid.core.trafico.materiales.model.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.trafico.materiales.model.vo.IncidenciaVO;

public interface IncidenciaDao extends GenericDao<IncidenciaVO>, Serializable {
	IncidenciaVO findIncidenciaByPk(Long incidencia);
	IncidenciaVO findIncidenciaBySerial(String numSerie);
	IncidenciaVO findIncidenciaByJefaturaMaterialNumSerie(String jefatura, Long material, String numSerie);
	IncidenciaVO findIncidenciaByPk(Long incidencia, boolean complete);
	List<IncidenciaVO> findIncidenciaByJefaturaAndEstadoAndFecha(String jefatura, Date fechaDesde, Date fechaHasta);
}
