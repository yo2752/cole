package org.gestoresmadrid.core.trafico.liquidacion620.model.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.trafico.liquidacion620.beans.ResultadoLiquidacion620;
import org.gestoresmadrid.core.trafico.liquidacion620.model.dao.Liquidacion620Dao;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoVO;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

@Repository
public class Liquidacion620DaoImpl extends GenericDaoImplHibernate<VehiculoVO> implements Liquidacion620Dao {

	private static final long serialVersionUID = 4859724987435460293L;

	@Override
	public List<ResultadoLiquidacion620> getListaLiquidaciones620(Date fechaIni, Date fechaFin, String numColegiado) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String fechaInicial = dateFormat.format(fechaIni);
		String fechaFinal = dateFormat.format(fechaFin);

		String query = "SELECT distinct (ve.matricula) as \"matricula\", ma.dsmarca as \"marca\",mo.dsmodveh as \"modelo\", to_char(tt.estado) as \"estado\", ttt.valor_declarado as \"valorDeclarado\" ,ttt.base_imponible as \"baseImponible\",ttt.cuota_tributaria as \"cuotaTributaria\",mo.vprevehi as \"valorCam\","
			+ " 0 as \"baseImponibleCam\" ,0 as \"cuotaCam\" , tt.num_expediente as \"numExpediente\", ve.fecha_matriculacion as \"fechaPrimMat\", ttt.fecha_devengo_itp as\"fechaDevengo\"  FROM vehiculo ve "
			+ "INNER JOIN tramite_trafico tt ON tt.id_vehiculo = ve.id_vehiculo "
			+ "INNER JOIN tramite_traf_tran ttt ON tt.num_expediente = ttt.num_expediente "
			+ "INNER JOIN modelo_cam mo ON ve.cdmodveh = mo.cdmodveh "
			+ "INNER JOIN marca_cam ma ON ve.cdmarca = ma.cdmarca "
			+ "where ttt.check_valor_declarado_620 = 'true' and "
			+ "ma.cdmarca = mo.cdmarca and "
			+ "ttt.exento_itp = 'NO' and "
			+ "ttt.fecha_devengo_itp between mo.fecdesde and mo.fechasta and "
			+ "ttt.fecha_devengo_itp between ma.fecdesde and ma.fechasta and "
			+ "ttt.fecha_generacion_620 BETWEEN to_date('" + fechaInicial + " 00:00:00' , 'dd/mm/yyyy hh24:mi:ss')" + " AND to_date('" + fechaFinal + " 23:59:59', 'dd/mm/yyyy hh24:mi:ss')";
			if(numColegiado != null && !"".equals(numColegiado)){
				query = query + " and tt.num_colegiado = "+ numColegiado;
			}
		query = query + " order by tt.num_expediente " ;

		List<ResultadoLiquidacion620> resultado = (List<ResultadoLiquidacion620>)getCurrentSession().createSQLQuery(query).setResultTransformer(Transformers.aliasToBean(ResultadoLiquidacion620.class)).list();

		return resultado;
	}

}