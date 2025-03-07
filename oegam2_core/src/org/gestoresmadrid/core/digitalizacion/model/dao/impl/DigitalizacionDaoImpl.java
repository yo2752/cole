package org.gestoresmadrid.core.digitalizacion.model.dao.impl;

import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.digitalizacion.model.dao.DigitalizacionDao;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.trafico.liquidacion620.beans.ResultadoLiquidacion620;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

@Repository
public class DigitalizacionDaoImpl extends GenericDaoImplHibernate<TramiteTraficoVO> implements DigitalizacionDao{

	private static final long serialVersionUID = 4461493973096148580L;

	@Override
	public List<TramiteTraficoVO> getListaDigitalizacion(String idMacroExpediente, Date fechaPresentacion,
			String tipoDocumento, String tipoTramite) {
		String query = "select distinct (db.doc_id),tt.fecha_presentacion,tt.tipo_tramite,db.carpeta"
				+ "inner join tramite_trafico tt on tt.doc_base = db.id"
				+ "where tt.doc_base = db.id";

		List<TramiteTraficoVO> resultado = (List<TramiteTraficoVO>)getCurrentSession().createSQLQuery(query).setResultTransformer(Transformers.aliasToBean(ResultadoLiquidacion620.class)).list();
		return resultado;
	}

}