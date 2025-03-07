package org.gestoresmadrid.core.registradores.model.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.registradores.model.dao.InmuebleDao;
import org.gestoresmadrid.core.registradores.model.vo.InmuebleVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class InmuebleDaoImpl extends GenericDaoImplHibernate<InmuebleVO> implements InmuebleDao {

	private static final long serialVersionUID = 1145618002490405249L;

	@Override
	public InmuebleVO getInmueble(Long idInmueble) {
		Criteria criteria = getCurrentSession().createCriteria(InmuebleVO.class);
		if (idInmueble != null) {
			criteria.add(Restrictions.eq("idInmueble", idInmueble));
		}
		aniadirCriteria(criteria);
		return (InmuebleVO) criteria.uniqueResult();
	}
	
	@Override
	public List<InmuebleVO> getInmuebles(Long idBien) {
		Criteria criteria = getCurrentSession().createCriteria(InmuebleVO.class);
		if (idBien != null) {
			criteria.add(Restrictions.eq("idBien", idBien));
		}
		aniadirCriteria(criteria);
		@SuppressWarnings("unchecked")
		List<InmuebleVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return null;
	}

	@Override
	public List<InmuebleVO> getInmuebles(BigDecimal idTramiteRegistro) {
		Criteria criteria = getCurrentSession().createCriteria(InmuebleVO.class);
		if (idTramiteRegistro != null) {
			criteria.add(Restrictions.eq("idTramiteRegistro", idTramiteRegistro));
		}
		aniadirCriteria(criteria);
		@SuppressWarnings("unchecked")
		List<InmuebleVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return null;
	}
	
	private void aniadirCriteria(Criteria criteria) {
		criteria.createAlias("bien", "bien", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bien.direccion", "bienDireccion", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bien.tipoInmueble", "bienTipoInmueble", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bien.tipoInmueble.id", "bienTipoInmuebleId", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bien.unidadMetrica", "bienUnidadMetrica", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bien.usoRustico", "bienUsoRustico", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bien.sistemaExplotacion", "bienSistemaExplotacion", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bien.situacion", "bienSituacion", CriteriaSpecification.LEFT_JOIN);
	}
	
}
