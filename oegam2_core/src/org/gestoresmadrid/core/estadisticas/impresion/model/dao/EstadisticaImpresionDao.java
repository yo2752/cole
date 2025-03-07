package org.gestoresmadrid.core.estadisticas.impresion.model.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.estadisticas.impresion.model.beans.ResultadoEstadisticaImpresion;
import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;

public interface EstadisticaImpresionDao extends Serializable,GenericDao<TramiteTraficoVO> {
	
	List<ResultadoEstadisticaImpresion> getNumDistintivosPorJefatura(Date fechaIni, Date fechaFin, String jefatura);
	
	
	List<ResultadoEstadisticaImpresion> getStockDistintivosPorJefatura(Date fechaIni, Date fechaFin, String jefatura);

}
