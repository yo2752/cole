package org.gestoresmadrid.core.direccion.model.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.direccion.model.dao.DgtMunicipioDao;
import org.gestoresmadrid.core.direccion.model.vo.DgtMunicipioVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class DgtMunicipioDaoImpl extends GenericDaoImplHibernate<DgtMunicipioVO> implements DgtMunicipioDao {

	private static final long serialVersionUID = -5812228273640235367L;

	@Override
	public DgtMunicipioVO getDgtMunicipio(String idProvincia, String municipio) {
		Criteria criteria = getCurrentSession().createCriteria(DgtMunicipioVO.class);
		if (idProvincia != null && !idProvincia.isEmpty()) {
			criteria.add(Restrictions.eq("dgtProvincia.idDgtProvincia", idProvincia));
		}
		if (municipio != null && !municipio.isEmpty()) {
			criteria.add(Restrictions.eq("municipio", municipio.toUpperCase().trim()));
		}
		@SuppressWarnings("unchecked")
		List<DgtMunicipioVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista.get(0);
		}
		return null;
	}
	
	@Override
	public DgtMunicipioVO getDgtMunicipioPorIdDgt(String idProvincia, BigDecimal idDgtMunicipio) {
		Criteria criteria = getCurrentSession().createCriteria(DgtMunicipioVO.class);
		if (idProvincia != null && !idProvincia.isEmpty()) {
			criteria.add(Restrictions.eq("dgtProvincia.idDgtProvincia", idProvincia));
		}
		if (idDgtMunicipio != null) {
			criteria.add(Restrictions.eq("idDgtMunicipio", idDgtMunicipio));
		}
		@SuppressWarnings("unchecked")
		List<DgtMunicipioVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista.get(0);
		}
		return null;
	}

	@Override
	public DgtMunicipioVO getDgtMunicipioPorCodigoIne(String idProvincia, String codigoIne) {
		Criteria criteria = getCurrentSession().createCriteria(DgtMunicipioVO.class);
		if (idProvincia != null && !idProvincia.isEmpty()) {
			criteria.add(Restrictions.eq("dgtProvincia.idDgtProvincia", idProvincia));
		}
		if (codigoIne != null && !codigoIne.isEmpty()) {
			criteria.add(Restrictions.eq("codigoIne", codigoIne));
		}
		@SuppressWarnings("unchecked")
		List<DgtMunicipioVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista.get(0);
		}
		return null;
	}
}
