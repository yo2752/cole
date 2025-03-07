package org.gestoresmadrid.core.vehiculo.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.vehiculo.model.dao.MarcaFabricanteDao;
import org.gestoresmadrid.core.vehiculo.model.vo.MarcaFabricanteVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class MarcaFabricanteDaoImpl extends GenericDaoImplHibernate<MarcaFabricanteVO> implements MarcaFabricanteDao {

	private static final long serialVersionUID = 6476096753135988665L;

	@Override
	public MarcaFabricanteVO getMarcaFabricante(String codigoMarca, Long codFabricante) {
		Criteria criteria = getCurrentSession().createCriteria(MarcaFabricanteVO.class);
		if (codigoMarca != null && !"".equals(codigoMarca)) {
			criteria.add(Restrictions.eq("id.codigoMarca", new Long(codigoMarca)));
		}
		if (codFabricante != null) {
			criteria.add(Restrictions.eq("id.codFabricante", codFabricante));
		}

		@SuppressWarnings("unchecked")
		List<MarcaFabricanteVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista.get(0);
		}
		return null;
	}
}