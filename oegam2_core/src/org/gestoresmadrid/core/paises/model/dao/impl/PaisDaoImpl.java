package org.gestoresmadrid.core.paises.model.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.paises.model.dao.PaisDao;
import org.gestoresmadrid.core.paises.model.vo.PaisVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class PaisDaoImpl extends GenericDaoImplHibernate<PaisVO> implements PaisDao {

	private static final long serialVersionUID = 3911518105967037303L;

	@SuppressWarnings("unchecked")
	@Override
	public PaisVO getPais(String idPais) {
		Criteria criteria = getCurrentSession().createCriteria(PaisVO.class);
		if (idPais != null && idPais.isEmpty()) {
			criteria.add(Restrictions.eq("idPais", idPais));
		}
		List<PaisVO> listaPaises = (List<PaisVO>) criteria.list();
		if (listaPaises != null && listaPaises.size() == 1) {
			return listaPaises.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PaisVO getPaisPorSigla(String sigla) {
		Criteria criteria = getCurrentSession().createCriteria(PaisVO.class);
		if (sigla != null && !sigla.isEmpty()) {
			if (sigla.length() == 2) {
				criteria.add(Restrictions.eq("sigla2", sigla));
			}
			if (sigla.length() == 3) {
				if (sigla.matches("[0-9]*")) {
					criteria.add(Restrictions.eq("sigla1", sigla));
				} else {
					criteria.add(Restrictions.eq("sigla3", sigla));
				}
			}
		}
		List<PaisVO> listaPaises = (List<PaisVO>) criteria.list();
		if (listaPaises != null && listaPaises.size() == 1) {
			return listaPaises.get(0);
		}
		return null;
	}

	// TipoPais: 0-PAISES EXPORTACION 1-PAISES TRANSITO COMUNITARIO CEE
	@SuppressWarnings("unchecked")
	@Override
	public List<PaisVO> listaPaises(BigDecimal tipoPais) {
		Criteria criteria = getCurrentSession().createCriteria(PaisVO.class);
		// Dependiendo del tipo de pais se devolvera una lista con los paises determinados.
		if (tipoPais != null) {
			criteria.add(Restrictions.eq("tipoPais", tipoPais));
		}
		criteria.addOrder(Order.asc("nombre"));
		return (List<PaisVO>) criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PaisVO> paises() {
		Criteria criteria = getCurrentSession().createCriteria(PaisVO.class);
		criteria.addOrder(Order.asc("nombre"));
		return (List<PaisVO>) criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PaisVO> paisesPorNombre(String pais) {
		Criteria criteria = getCurrentSession().createCriteria(PaisVO.class);
		if (StringUtils.isNotBlank(pais)) {
			criteria.add(Restrictions.like("nombre", pais + "%"));
		}
		return (List<PaisVO>) criteria.list();
	}
}
