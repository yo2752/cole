package org.gestoresmadrid.core.mensajeErrorServicio.model.dao.impl;

import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.mensajeErrorServicio.model.dao.MensajeErrorServicioDao;
import org.gestoresmadrid.core.mensajeErrorServicio.model.vo.MensajeErrorServicioVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import utilidades.estructuras.FechaFraccionada;

/**
 * @author ext_fjcl
 */
@Repository
public class MensajeErrorServicioDaoImpl extends GenericDaoImplHibernate<MensajeErrorServicioVO> implements MensajeErrorServicioDao {

	private static final long serialVersionUID = -5149017930368462037L;

	@SuppressWarnings("unchecked")
	public List<MensajeErrorServicioVO> getListaMensajeErrorServicio(Date fecha) {
		Criteria criteria = getCurrentSession().createCriteria(MensajeErrorServicioVO.class);
		if (fecha == null) {
			fecha = new Date();
		}
		criteria.add(Restrictions.le("fecha", fecha));
		criteria.addOrder(Order.asc("fecha"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MensajeErrorServicioVO> getListaMensajeErrorServicio(FechaFraccionada fecha) {
		Criteria criteria = getCurrentSession().createCriteria(MensajeErrorServicioVO.class);

		if (fecha != null && !fecha.isfechaNula()) {
			if (!fecha.isfechaInicioNula()) {
				criteria.add(Restrictions.ge("fecha", fecha.getFechaInicio()));
			}
			if (!fecha.isfechaFinNula()) {
				criteria.add(Restrictions.le("fecha", fecha.getFechaMaxFin()));
			}
		}
		
		criteria.addOrder(Order.asc("fecha"));
		
		return criteria.list();
	}

}
