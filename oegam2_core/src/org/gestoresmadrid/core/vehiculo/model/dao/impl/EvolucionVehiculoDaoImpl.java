package org.gestoresmadrid.core.vehiculo.model.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.vehiculo.model.dao.EvolucionVehiculoDao;
import org.gestoresmadrid.core.vehiculo.model.vo.EvolucionVehiculoVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class EvolucionVehiculoDaoImpl extends GenericDaoImplHibernate<EvolucionVehiculoVO> implements EvolucionVehiculoDao {

	private static final long serialVersionUID = 4939626183121160464L;

	@SuppressWarnings("unchecked")
	@Override
	public List<EvolucionVehiculoVO> getEvolucionVehiculo(Long idVehiculo, String numColegiado, List<String> tipoActualizacion) {
		Criteria criteria = getCurrentSession().createCriteria(EvolucionVehiculoVO.class);
		if (idVehiculo != null) {
			criteria.add(Restrictions.eq("id.idVehiculo", idVehiculo));
		}
		if (numColegiado != null && !numColegiado.isEmpty()) {
			criteria.add(Restrictions.eq("id.numColegiado", numColegiado));
		}
		if (tipoActualizacion != null && !tipoActualizacion.isEmpty()) {
			criteria.add(Restrictions.in("tipoActualizacion", tipoActualizacion));
		}

		criteria.createAlias("usuario", "usuario", CriteriaSpecification.LEFT_JOIN);
		criteria.addOrder(Order.desc("id.fechaHora"));

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public EvolucionVehiculoVO getEvolucionVehiculoSinCodig(Long idVehiculo, BigDecimal numExpediente) {
		Criteria criteria = getCurrentSession().createCriteria(EvolucionVehiculoVO.class);
		if (idVehiculo != null) {
			criteria.add(Restrictions.eq("id.idVehiculo", idVehiculo));
		}
		if (numExpediente != null) {
			criteria.add(Restrictions.eq("numExpediente", numExpediente));
		}
		criteria.add(Restrictions.eq("codigoItvNue", SINCODIG));
		criteria.add(Restrictions.sqlRestriction("nvl(CODIGO_ITV_ANT,'" +SINCODIG + "'" + ") <> '"+ SINCODIG + "'"));

		criteria.addOrder(Order.desc("id.fechaHora"));

		List<EvolucionVehiculoVO> list = criteria.list();
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}
}