package org.gestoresmadrid.core.sancion.model.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.sancion.model.dao.SancionDao;
import org.gestoresmadrid.core.sancion.model.vo.SancionVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class SancionDaoImpl extends GenericDaoImplHibernate<SancionVO> implements SancionDao {

	private static final long serialVersionUID = -127059569103695158L;

	public SancionDaoImpl() {
		super();
	}

	@Override
	public SancionVO getSancionId(Integer idSancion) {
		SancionVO sancion = null;

		Criteria criteria = getCurrentSession().createCriteria(SancionVO.class);
		criteria.add(Restrictions.eq("idSancion", idSancion));

		sancion = (SancionVO) criteria.uniqueResult();

		return sancion;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SancionVO> listado(SancionVO sancion) {
		List<SancionVO> listaSan = null;

		Criteria criteria = getCurrentSession().createCriteria(SancionVO.class);
		if (StringUtils.isNotBlank(sancion.getNumColegiado())) {
			criteria.add(Restrictions.eq("numColegiado", sancion.getNumColegiado()));
		}
		if (sancion.getMotivo() != null) {
			criteria.add(Restrictions.eq("motivo", sancion.getMotivo()));
		}
		criteria.add(Restrictions.eq("fechaPresentacion", sancion.getFechaPresentacion()));
		criteria.add(Restrictions.eq("estado", 1));
		criteria.addOrder(Order.asc("numColegiado"));
		criteria.addOrder(Order.asc("apellidos"));
		criteria.addOrder(Order.asc("nombre"));

		listaSan = criteria.list();

		return listaSan;
	}

}
