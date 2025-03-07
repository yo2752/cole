package org.gestoresmadrid.core.trafico.testra.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.trafico.testra.model.dao.ConsultaTestraDao;
import org.gestoresmadrid.core.trafico.testra.model.vo.ConsultaTestraVO;
import org.gestoresmadrid.core.vehiculo.model.enumerados.EstadoVehiculo;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class ConsultaTestraDaoImpl extends GenericDaoImplHibernate<ConsultaTestraVO> implements ConsultaTestraDao {

	private static final long serialVersionUID = -4305582586138918807L;

	@SuppressWarnings("unchecked")
	@Override
	public List<ConsultaTestraVO> getConsultas(String dato, String numColegiado) {
		Criteria criteria = getCurrentSession().createCriteria(ConsultaTestraVO.class);
		if (dato != null && !dato.isEmpty()) {
			criteria.add(Restrictions.eq("dato", dato));
		}
		if (numColegiado != null && !numColegiado.isEmpty()) {
			criteria.add(Restrictions.eq("numColegiado", numColegiado));
			
		}
		return criteria.list();
	}
	
	@Override
	public ConsultaTestraVO getConsultaTestraPorTipoDatoNumColegiado(String tipo, String dato, String numColegiado) {
		Criteria criteria = getCurrentSession().createCriteria(ConsultaTestraVO.class);
		criteria.add(Restrictions.eq("dato", dato));
		criteria.add(Restrictions.eq("numColegiado", numColegiado));
		criteria.add(Restrictions.eq("tipo", tipo));
		return (ConsultaTestraVO) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getNumerosColegiados() {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(ConsultaTestraVO.class);
		criteria.add(Restrictions.eq("activo", EstadoVehiculo.Activo.getValorEnum()));
		criteria = criteria.setProjection(Projections.distinct(Projections.property("numColegiado")));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConsultaTestraVO> getConsultasByColegiado(String numColegiado) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(ConsultaTestraVO.class);
		criteria.add(Restrictions.eq("numColegiado", numColegiado));
		criteria.add(Restrictions.eq("activo", EstadoVehiculo.Activo.getValorEnum()));
		criteria.addOrder(Order.asc("tipo"));
		return criteria.list();
	}

	@Override
	public ConsultaTestraVO getConsultaById(Long id) {
		return (ConsultaTestraVO) getCurrentSession().get(ConsultaTestraVO.class, id);
	}

}