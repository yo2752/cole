package org.gestoresmadrid.core.general.model.dao.impl;

import java.util.Collections;
import java.util.List;

import org.gestoresmadrid.core.general.model.dao.AplicacionDao;
import org.gestoresmadrid.core.general.model.vo.AplicacionVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class AplicacionDaoImpl extends GenericDaoImplHibernate<AplicacionVO> implements AplicacionDao {

	private static final long serialVersionUID = 6943083126126252201L;
	private static final String CODIGO_APLICACION = "codigoAplicacion";

	@Override
	public boolean tieneAplicacion(Long idContrato, String codigoAplicacion) {
		Criteria criteria = getCurrentSession().createCriteria(AplicacionVO.class);
		criteria.add(Restrictions.eq("contratos.idContrato", idContrato));
		criteria.add(Restrictions.eq(CODIGO_APLICACION, codigoAplicacion));
		criteria.createAlias("contratos", "contratos");
		@SuppressWarnings("unchecked")
		List<AplicacionVO> lista = criteria.list();
		return lista != null && lista.size() == 1;
	}

	@Override
	public List<AplicacionVO> getAplicacionByCodigo(String codigoAplicacion) {
		Criteria criteria = getCurrentSession().createCriteria(AplicacionVO.class);
		criteria.add(Restrictions.eq(CODIGO_APLICACION, codigoAplicacion));
		@SuppressWarnings("unchecked")
		List<AplicacionVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return Collections.emptyList();
	}
	
	@Override
	public AplicacionVO getAplicacionPorCodigo(String codAplicacion) {
		Criteria criteria = getCurrentSession().createCriteria(AplicacionVO.class);
		criteria.add(Restrictions.eq(CODIGO_APLICACION, codAplicacion));
		return (AplicacionVO) criteria.uniqueResult();
	}

	@Override
	public List<AplicacionVO> getAplicaciones() {
		Criteria criteria = getCurrentSession().createCriteria(AplicacionVO.class);
		@SuppressWarnings("unchecked")
		List<AplicacionVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return Collections.emptyList();
	}
}