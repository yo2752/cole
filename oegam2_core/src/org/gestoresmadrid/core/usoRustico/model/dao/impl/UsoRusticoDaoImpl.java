package org.gestoresmadrid.core.usoRustico.model.dao.impl;

import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.usoRustico.model.dao.UsoRusticoDao;
import org.gestoresmadrid.core.usoRustico.model.vo.UsoRusticoVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class UsoRusticoDaoImpl extends GenericDaoImplHibernate<UsoRusticoVO> implements UsoRusticoDao{

	private static final long serialVersionUID = 8227162969198175005L;

	@SuppressWarnings("unchecked")
	@Override
	public List<UsoRusticoVO> getListaUsoRusticoPorTipo(String tipoUso) {
		Criteria criteria = getCurrentSession().createCriteria(UsoRusticoVO.class);
		criteria.add(Restrictions.eq("tipoUso", tipoUso));
		return criteria.list();
	}
}
