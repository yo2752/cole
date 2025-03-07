package org.gestoresmadrid.core.trafico.materiales.model.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.TipoDistintivo;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.trafico.materiales.model.dao.StockMaterialDao;
import org.gestoresmadrid.core.trafico.materiales.model.vo.StockMaterialVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class StockMaterialDaoImpl extends GenericDaoImplHibernate<StockMaterialVO> implements StockMaterialDao{

	private static final long serialVersionUID = -1977621542955943532L;
	
	public StockMaterialVO getElementById(Long id) {
		Criteria criteria = getCurrentSession().createCriteria(StockMaterialVO.class);
		criteria.add(Restrictions.eq("id", id));
		return (StockMaterialVO) criteria.uniqueResult();
	}
	
	public StockMaterialVO comprobarExistente(StockMaterialVO vo) {
		Criteria criteria = getCurrentSession().createCriteria(StockMaterialVO.class);
		criteria.add(Restrictions.eq("jefatura", vo.getJefatura()));
		criteria.add(Restrictions.eq("tipoMaterial", vo.getTipoMaterial()));
		return (StockMaterialVO) criteria.uniqueResult();
	}

	@Override
	public StockMaterialVO findByJefaturaTipo(String jefatura, String tipo) {
		Criteria criteria = getCurrentSession().createCriteria(StockMaterialVO.class);
		criteria.add(Restrictions.eq("jefatura", jefatura));
		criteria.add(Restrictions.eq("tipoMaterial", tipo));
		return (StockMaterialVO) criteria.uniqueResult();
	}



	@Override
	public HashMap<String, HashMap<String, Long>> obtenerFacturacionSemanalStock(String jefatura,
			Long tipoDocumento) {

				Criteria criteria = getCurrentSession().createCriteria(StockMaterialVO.class, "stockMaterial");
				if (StringUtils.isNotEmpty(jefatura)) {
					criteria.createAlias("jefatura", "jefatura");
					criteria.add(Restrictions.eq("jefatura.jefaturaProvincial", jefatura));
				}
				@SuppressWarnings("unchecked")
				List<StockMaterialVO> materialStockList = criteria.list();

				HashMap<String, HashMap<String, Long>> resultado = new HashMap<String, HashMap<String, Long>>();
				for (StockMaterialVO item : materialStockList) {
					String keyJT = item.getJefaturaProvincial().getDescripcion();
					String keyTipo = "";
					if ("PC".equals(item.getTipoMaterial())) {
						keyTipo = item.getTipoMaterial();
					} else {
						TipoDistintivo tipoDistintivo = TipoDistintivo.convertir(item.getTipoMaterial());
						keyTipo = tipoDistintivo.getNombreEnum();
					}

					if (resultado.containsKey(keyJT)) {
						HashMap<String, Long> value = resultado.get(keyJT);
						if (!value.containsKey(keyTipo)) {
							value.put(keyTipo, Long.parseLong(item.getUnidades().toString()));
						}
					} else {
						HashMap<String, Long> materialStock = new HashMap<String, Long>();
						materialStock.put(keyTipo, Long.parseLong(item.getUnidades().toString()));
						resultado.put(keyJT, materialStock);
					}
				}

				return resultado;
	}


}
