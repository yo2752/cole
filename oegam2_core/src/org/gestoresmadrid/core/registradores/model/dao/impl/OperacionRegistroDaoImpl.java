package org.gestoresmadrid.core.registradores.model.dao.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.registradores.model.dao.OperacionRegistroDao;
import org.gestoresmadrid.core.registradores.model.vo.OperacionRegistroVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class OperacionRegistroDaoImpl extends GenericDaoImplHibernate<OperacionRegistroVO> implements OperacionRegistroDao, Serializable {

	private static final long serialVersionUID = 7905941039914342560L;

	@Override
	public OperacionRegistroVO getOperacionRegistro(String codigo, String tipoTramite) {
		Criteria criteria = getCurrentSession().createCriteria(OperacionRegistroVO.class);
		if (codigo != null && !codigo.isEmpty()) {
			criteria.add(Restrictions.eq("id.codigo", codigo));
		}
		if (tipoTramite != null && !tipoTramite.isEmpty()) {
			criteria.add(Restrictions.eq("id.tipoTramite", tipoTramite));
		}
		criteria.add(Restrictions.eq("estado", BigDecimal.ZERO));

		@SuppressWarnings("unchecked")
		List<OperacionRegistroVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista.get(0);
		}
		return null;
	}

	@Override
	public List<OperacionRegistroVO> getOperacionesRegistro() {
		Criteria criteria = getCurrentSession().createCriteria(OperacionRegistroVO.class);
		criteria.add(Restrictions.eq("estado", BigDecimal.ZERO));

		@SuppressWarnings("unchecked")
		List<OperacionRegistroVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		} else {
			return Collections.emptyList();
		}
	}
}
