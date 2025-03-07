package org.gestoresmadrid.oegam2comun.notificacionpreferencias.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.general.model.vo.ContratoPreferenciaVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.oegam2comun.notificacionpreferencias.dao.ContratoPreferenciasSegSocDAO;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class ContratoPreferenciasSegSocDAOImpl extends GenericDaoImplHibernate<ContratoPreferenciaVO> implements ContratoPreferenciasSegSocDAO {
		
	private static final long serialVersionUID = -4963070528820851174L;

	@Override
	public ContratoPreferenciaVO obtenerContratoPreferenciaByIdContrato(Long idContrato){
		List<Criterion> listCriterion = new ArrayList<Criterion>();
		if (idContrato != -1) {
			listCriterion.add(Restrictions.eq("idContrato", idContrato));
			List<ContratoPreferenciaVO> lista = buscarPorCriteria(listCriterion, null, null);
			if (lista != null && !lista.isEmpty()) {
				return lista.get(0);
			}
		}

		return null;
	}
	
	@Override
	public BigDecimal obtenerOrdenDocBase(Long idContrato) {
		Criteria criteria = getCurrentSession().createCriteria(ContratoPreferenciaVO.class);
		criteria.add(Restrictions.eq("idContrato", idContrato));
		ProjectionList listaProyection = Projections.projectionList();
		listaProyection.add(Projections.property("ordenDocbaseYb"));
		criteria.setProjection(listaProyection);
		return (BigDecimal) criteria.uniqueResult();
	}


	@Override
	public Object insertOrUpdate(ContratoPreferenciaVO contratoPreferencias) {
		return guardarOActualizar(contratoPreferencias);
		
	}

}