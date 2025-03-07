package org.gestoresmadrid.core.registradores.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.registradores.model.dao.SociedadCargoDao;
import org.gestoresmadrid.core.registradores.model.vo.SociedadCargoVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class SociedadCargoDaoImpl extends GenericDaoImplHibernate<SociedadCargoVO> implements SociedadCargoDao {

	private static final long serialVersionUID = -2162276219894640201L;

	@Override
	public SociedadCargoVO getSociedadCargo(String cif, String numColegiado, String nifCargo, String codigoCargo) {
		Criteria criteria = getCurrentSession().createCriteria(SociedadCargoVO.class);
		if (cif != null && !cif.isEmpty()) {
			criteria.add(Restrictions.eq("id.cifSociedad", cif));
		}
		if (numColegiado != null && !numColegiado.isEmpty()) {
			criteria.add(Restrictions.eq("id.numColegiado", numColegiado));
		}
		if (nifCargo != null && !nifCargo.isEmpty()) {
			criteria.add(Restrictions.eq("id.nifCargo", nifCargo));
		}
		if (codigoCargo != null && !codigoCargo.isEmpty()) {
			criteria.add(Restrictions.eq("id.codigoCargo", codigoCargo));
		}
		criteria.createAlias("personaCargo", "personaCargo");
		@SuppressWarnings("unchecked")
		List<SociedadCargoVO> lista = criteria.list();
		if (null != lista && !lista.isEmpty()) {
			return lista.get(0);
		}
		return null;
	}

}
