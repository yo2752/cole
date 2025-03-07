package org.gestoresmadrid.core.vehiculo.model.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.vehiculo.model.dao.CaracteristicasElectricasDao;
import org.gestoresmadrid.core.vehiculo.model.vo.CaracteristicasElectricasVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class CaracteristicasElectricasDaoImpl extends GenericDaoImplHibernate<CaracteristicasElectricasVO> implements CaracteristicasElectricasDao {

	private static final long serialVersionUID = 6670635234689451938L;

	@Override
	public List<CaracteristicasElectricasVO> getCaracteristicasElectricas(String codigoMarca, String modelo, String tipoItv, String version, String variante, String bastidor,
			String categoriaElectrica, BigDecimal consumo, BigDecimal autonomiaElectrica) {

		Criteria criteria = getCurrentSession().createCriteria(CaracteristicasElectricasVO.class);

		if (codigoMarca != null && !codigoMarca.isEmpty()) {
			criteria.add(Restrictions.eq("codigoMarca", codigoMarca));
		}

		if (modelo != null && !modelo.isEmpty()) {
			criteria.add(Restrictions.eq("modelo", modelo));
		}

		if (tipoItv != null && !tipoItv.isEmpty()) {
			criteria.add(Restrictions.eq("tipoItv", tipoItv));
		}

		if (version != null && !version.isEmpty()) {
			criteria.add(Restrictions.eq("version", version));
		}

		if (variante != null && !variante.isEmpty()) {
			criteria.add(Restrictions.eq("variante", variante));
		}

		if (bastidor != null && !bastidor.isEmpty()) {
			criteria.add(Restrictions.like("bastidor", bastidor + "%"));
		}

		if (categoriaElectrica != null && !categoriaElectrica.isEmpty()) {
			criteria.add(Restrictions.like("categoriaElectrica", categoriaElectrica + "%"));
		}

		criteria.add(Restrictions.sqlRestriction("nvl(" + consumo + ",0) = nvl(CONSUMO,0)"));
		criteria.add(Restrictions.sqlRestriction("nvl(" + autonomiaElectrica + ",0) = nvl(AUTONOMIA_ELECTRICA,0)"));

		@SuppressWarnings("unchecked")
		List<CaracteristicasElectricasVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return null;
	}
}
