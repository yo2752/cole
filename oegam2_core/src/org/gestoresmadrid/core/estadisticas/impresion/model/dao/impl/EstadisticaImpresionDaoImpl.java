package org.gestoresmadrid.core.estadisticas.impresion.model.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.estadisticas.impresion.model.beans.ResultadoEstadisticaImpresion;
import org.gestoresmadrid.core.estadisticas.impresion.model.dao.EstadisticaImpresionDao;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;


@Repository
public class EstadisticaImpresionDaoImpl extends GenericDaoImplHibernate<TramiteTraficoVO> implements EstadisticaImpresionDao{


	private static final long serialVersionUID = -5759559567618118571L;

	@Override
	public List<ResultadoEstadisticaImpresion> getNumDistintivosPorJefatura(Date fechaIni, Date fechaFin,
			String jefatura) {
	
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String fechaInicial = dateFormat.format(fechaIni);
		String fechaFinal = dateFormat.format(fechaFin);
	
		String query = "SELECT count(*) as \"totalDistintivo\", ttm.tipo_distintivo as \"tipoDistintivo\",tt.jefatura_provincial as \"jefatura\""
			+ "  FROM tramite_trafico tt "
			+ "INNER JOIN tramite_traf_matr ttm ON tt.num_expediente = ttm.num_expediente "
			+ "WHERE tt.fecha_presentacion BETWEEN to_date('" + fechaInicial + " 00:00:00' , 'dd/mm/yyyy hh24:mi:ss')" + " AND to_date('" + fechaFinal + " 23:59:59', 'dd/mm/yyyy hh24:mi:ss')";
			if(jefatura != null && !"".equals(jefatura)){
				query = query + " AND tt.jefatura_provincial = '" + jefatura + "'";
			}else{
				query = query + " AND tt.jefatura_provincial IN ('M','M1','M2') ";
			}
			query = query + " GROUP BY ttt.tipo_distintivo, tt.jefatura_provincial; " ;
			
	
			
		
		
		return getCurrentSession().createSQLQuery(query).setResultTransformer(Transformers.aliasToBean(ResultadoEstadisticaImpresion.class)).list();
	}

	@Override
	public List<ResultadoEstadisticaImpresion> getStockDistintivosPorJefatura(Date fechaIni, Date fechaFin,
			String jefatura) {
	
		String query = "SELECT gs.stock as \"totalDistintivo\" , gsm.nombre as \"tipoDistintivo\", gs.jefatura_provincial as \"jefatura\""
			+ "  FROM gs_material_stock gs JOIN gs_material  gsm ON gs.material_id = gsm.material_id ";
			if(jefatura != null && !"".equals(jefatura)){
				query = query + " WHERE gs.jefatura_provincial = '" + jefatura + "'";
			}else{
				query = query + " WHERE gs.jefatura_provincial IN ('M','M1','M2') ";
			}
			query = query + " GROUP BY gs.jefatura_provincial,gs.stock,gsm.nombre; " ;
			
	
			
		
		
		return getCurrentSession().createSQLQuery(query).setResultTransformer(Transformers.aliasToBean(ResultadoEstadisticaImpresion.class)).list();
	}

}
