package org.gestoresmadrid.core.trafico.materiales.model.dao.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.TipoDistintivo;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.trafico.materiales.model.dao.MaterialStockDao;
import org.gestoresmadrid.core.trafico.materiales.model.enumerados.TipoDocumentoEnum;
import org.gestoresmadrid.core.trafico.materiales.model.vo.MaterialStockVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class MaterialStockDaoImpl extends GenericDaoImplHibernate<MaterialStockVO> implements MaterialStockDao {

	private static final long serialVersionUID = 763366064635818356L;

	@Override
	public MaterialStockVO findStockPorJefaturaMaterial(String jefatura, Long material) {
		Criteria criteria = getCurrentSession().createCriteria(MaterialStockVO.class, "materialStock");
		criteria.createAlias("materialStock.materialVO", "materialVO");
		criteria.createAlias("materialStock.jefaturaProvincial", "jefaturaProvincial");

		criteria.add(Restrictions.eq("jefaturaProvincial.jefaturaProvincial", jefatura));
		criteria.add(Restrictions.eq("materialVO.materialId", material));

		return (MaterialStockVO) criteria.uniqueResult();
	}

	@Override
	public MaterialStockVO findStockPorJefaturaTipo(String jefatura, String tipo) {
		Criteria criteria = getCurrentSession().createCriteria(MaterialStockVO.class, "materialStock");
		criteria.createAlias("materialStock.jefaturaProvincial", "jefaturaProvincial");

		criteria.add(Restrictions.eq("jefaturaProvincial.jefaturaProvincial", jefatura));
		criteria.add(Restrictions.eq("tipo", tipo));

		return (MaterialStockVO) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MaterialStockVO> findStockByJefaturaMaterial(String jefatura, Long material) {
		Criteria criteria = getCurrentSession().createCriteria(MaterialStockVO.class, "materialStock");
		criteria.createAlias("materialStock.materialVO", "materialVO");
		criteria.createAlias("materialStock.jefaturaProvincial", "jefaturaProvincial");

		criteria.add(Restrictions.eq("jefaturaProvincial.jefaturaProvincial", jefatura));
		criteria.add(Restrictions.eq("materialVO.materialId", material));

		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MaterialStockVO> findStock() {
		Criteria criteria = getCurrentSession().createCriteria(MaterialStockVO.class, "materialStock");
		criteria.createAlias("materialStock.jefaturaProvincial", "jefaturaProvincial");
		criteria.addOrder(Order.asc("jefaturaProvincial.jefaturaProvincial"));
		return criteria.list();
	}

	@Override
	public MaterialStockVO findStockByPrimaryKey(Long stockId) {
		Criteria criteria = getCurrentSession().createCriteria(MaterialStockVO.class, "materialStock");
		criteria.add(Restrictions.eq("materialStock.materialStockId", stockId));
		return (MaterialStockVO) criteria.uniqueResult();
	}

	@Override
	public MaterialStockVO findStockBystockInvId(Long stockInvId) {
		Criteria criteria = getCurrentSession().createCriteria(MaterialStockVO.class, "materialStock");
		criteria.add(Restrictions.eq("stockInvId", stockInvId));

		MaterialStockVO materialStockVO = (MaterialStockVO) criteria.uniqueResult();

		return materialStockVO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MaterialStockVO> findStockByFecha(Date fecha) {
		Date fechaInicio = fechaInicioDia(fecha);
		Date fechaFin = fechaFinDia(fecha);

		Criteria criteria = getCurrentSession().createCriteria(MaterialStockVO.class, "materialStock");
		criteria.add(Restrictions.isNotNull("stockInvId"));
		criteria.add(Restrictions.gtProperty("fecUltConsumo", "fecUltRecarga"));
		Conjunction and = Restrictions.conjunction();
		and.add(Restrictions.ge("fecUltConsumo", fechaInicio));
		and.add(Restrictions.lt("fecUltConsumo", fechaFin));
		criteria.add(and);

		return criteria.list();
	}

	@Override
	public HashMap<String, HashMap<String, Long>> obtenerFacturacionSemanalStock(String jefaturaProvincial, Long tipoDocumento) {

		// TODO Auto-generated method stub

		Criteria criteria = getCurrentSession().createCriteria(MaterialStockVO.class, "materialStock");
		if (StringUtils.isNotEmpty(jefaturaProvincial)) {
			criteria.createAlias("materialStock.jefaturaProvincial", "jefatura");
			criteria.add(Restrictions.eq("jefatura.jefaturaProvincial", jefaturaProvincial));
		}

		if (tipoDocumento != null) {
			if (tipoDocumento.equals(TipoDocumentoEnum.Distintivo.getValorEnum())) {
				criteria.createAlias("materialStock.materialVO", "material");
				criteria.add(Restrictions.ne("material.tipo", "PC"));
			} else if (tipoDocumento.equals(TipoDocumentoEnum.PermisoConducir.getValorEnum())) {
				criteria.createAlias("materialStock.materialVO", "material");
				criteria.add(Restrictions.eq("material.tipo", "PC"));
			}
		}

		@SuppressWarnings("unchecked")
		List<MaterialStockVO> materialStockList = criteria.list();

		HashMap<String, HashMap<String, Long>> resultado = new HashMap<String, HashMap<String, Long>>();
		for (MaterialStockVO item : materialStockList) {
			String keyJT = item.getJefaturaProvincial().getDescripcion();
			String keyTipo = "";
			if ("PC".equals(item.getMaterialVO().getTipo())) {
				keyTipo = item.getMaterialVO().getTipo();
			} else {
				TipoDistintivo tipoDistintivo = TipoDistintivo.convertir(item.getMaterialVO().getTipo());
				keyTipo = tipoDistintivo.getNombreEnum();
			}

			if (resultado.containsKey(keyJT)) {
				HashMap<String, Long> value = resultado.get(keyJT);
				if (!value.containsKey(keyTipo)) {
					value.put(keyTipo, item.getUnidades());
				}
			} else {
				HashMap<String, Long> materialStock = new HashMap<String, Long>();
				materialStock.put(keyTipo, item.getUnidades());
				resultado.put(keyJT, materialStock);
			}
		}

		return resultado;
	}

	private Date fechaInicioDia(Date fecha) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date time = cal.getTime();

		return time;
	}

	private Date fechaFinDia(Date fecha) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		Date time = cal.getTime();

		return time;
	}

}
