package org.gestoresmadrid.core.registradores.model.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.registradores.model.dao.LibroRegistroDao;
import org.gestoresmadrid.core.registradores.model.vo.LibroRegistroVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class LibroRegistroDaoImpl extends GenericDaoImplHibernate<LibroRegistroVO> implements LibroRegistroDao {

	private static final long serialVersionUID = -3331933745380122617L;

	@Override
	public LibroRegistroVO getLibroRegistro(String id) {
		Criteria criteria = getCurrentSession().createCriteria(LibroRegistroVO.class);
		criteria.add(Restrictions.eq("idLibro", Long.parseLong(id)));
		return (LibroRegistroVO) criteria.uniqueResult();
	}


	public List<LibroRegistroVO> getLibrosRegistro(BigDecimal idTramiteRegistro){
		Criteria criteria = getCurrentSession().createCriteria(LibroRegistroVO.class);
		if (null != idTramiteRegistro) {
			criteria.add(Restrictions.eq("idTramiteRegistro", idTramiteRegistro));
		}
		@SuppressWarnings("unchecked")
		List<LibroRegistroVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return null;

	}
}
