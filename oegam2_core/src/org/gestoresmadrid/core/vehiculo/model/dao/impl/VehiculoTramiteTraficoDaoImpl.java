package org.gestoresmadrid.core.vehiculo.model.dao.impl;

import java.math.BigDecimal;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.vehiculo.model.dao.VehiculoTramiteTraficoDao;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoTramiteTraficoVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class VehiculoTramiteTraficoDaoImpl extends GenericDaoImplHibernate<VehiculoTramiteTraficoVO> implements VehiculoTramiteTraficoDao {

	private static final long serialVersionUID = -3797812944980475754L;

	@Override
	public VehiculoTramiteTraficoVO getVehiculoTramite(BigDecimal numExpediente, Long idVehiculo) {
		Criteria criteria = getCurrentSession().createCriteria(VehiculoTramiteTraficoVO.class);
		criteria.add(Restrictions.eq("id.numExpediente", numExpediente));
		criteria.add(Restrictions.eq("id.idVehiculo", idVehiculo));
		criteria.createAlias("vehiculo", "vehiculo", CriteriaSpecification.LEFT_JOIN);
		return (VehiculoTramiteTraficoVO) criteria.uniqueResult();
	}
}
