package org.gestoresmadrid.core.direccion.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.direccion.model.dao.MunicipioDao;
import org.gestoresmadrid.core.direccion.model.vo.MunicipioVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class MunicipioDaoImpl extends GenericDaoImplHibernate<MunicipioVO> implements MunicipioDao {

	private static final long serialVersionUID = -5031478620891925105L;

	@Override
	public MunicipioVO getMunicipio(String idMunicipio, String idProvincia) {
		Criteria criteria = getCurrentSession().createCriteria(MunicipioVO.class);
		criteria.add(Restrictions.eq("id.idMunicipio", idMunicipio));
		criteria.add(Restrictions.eq("id.idProvincia", idProvincia));
		return (MunicipioVO) criteria.uniqueResult();
	}

	@Override
	public List<MunicipioVO> listaMunicipios(String idProvincia) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(MunicipioVO.class);
		if (idProvincia != null && !idProvincia.isEmpty()) {
			criteria.add(Restrictions.eq("id.idProvincia", idProvincia));
		}

		criteria.addOrder(Order.asc("nombre"));

		List<MunicipioVO> lista = (List<MunicipioVO>) criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return null;
	}

	@Override
	public List<MunicipioVO> listaOficinasLiquidadoras(String idMunicipio) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(MunicipioVO.class);
		if (idMunicipio != null && !idMunicipio.isEmpty()) {
			criteria.add(Restrictions.eq("id.idMunicipio", idMunicipio));
		}

		criteria.add(Restrictions.isNotNull("oficinaLiquidadora"));

		List<MunicipioVO> lista = (List<MunicipioVO>) criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return null;
	}

	@Override
	public MunicipioVO getMunicipioPorNombre(String nombreMunicipio, String idProvincia) {
		Criteria criteria = getCurrentSession().createCriteria(MunicipioVO.class);
		criteria.add(Restrictions.eq("nombre", nombreMunicipio));
		if(idProvincia != null && !idProvincia.isEmpty()){
			criteria.add(Restrictions.eq("id.idProvincia", idProvincia));
		}
		return (MunicipioVO) criteria.uniqueResult();
	}
}