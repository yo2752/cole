package org.gestoresmadrid.core.tasas.model.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.tasas.model.dao.CompraTasasDao;
import org.gestoresmadrid.core.tasas.model.vo.CompraTasaVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class CompraTasasDaoImpl extends GenericDaoImplHibernate<CompraTasaVO> implements CompraTasasDao {

	private static final long serialVersionUID = 3616195798555875646L;

	@Override
	public CompraTasaVO get(Long idCompra, Long idContrato, boolean withDesglose) {
		CompraTasaVO compra = null;
		if (idCompra != null) {
			Criteria criteria = getCurrentSession().createCriteria(CompraTasaVO.class);
			criteria.add(Restrictions.eq("idCompra", idCompra));
			if (idContrato != null) {
				criteria.createCriteria("contrato").add(Restrictions.eq("idContrato", idContrato));
			}
			if (withDesglose) {
				criteria.createCriteria("desgloseCompraTasas", CriteriaSpecification.LEFT_JOIN).createCriteria("tasaDgt", CriteriaSpecification.LEFT_JOIN);
			}
			compra = (CompraTasaVO) criteria.uniqueResult();
		}
		return compra;
	}

	@Override
	public List<CompraTasaVO> getListaCompraTasa(Integer estado, Date fechaCompraDesde, Date fechaCompraHasta, Date fechaAltaDesde, Date fechaAltaHasta) {
		List<Criterion> listCriterion = new ArrayList<>();
		if (fechaCompraDesde != null) {
			listCriterion.add(Restrictions.ge("fechaCompra", fechaCompraDesde));
		}
		if (fechaCompraHasta != null) {
			listCriterion.add(Restrictions.le("fechaCompra", fechaCompraHasta));
		}
		if (fechaAltaDesde != null) {
			listCriterion.add(Restrictions.ge("fechaAlta", fechaAltaDesde));
		}
		if (fechaAltaHasta != null) {
			listCriterion.add(Restrictions.le("fechaAlta", fechaAltaHasta));
		}
		if (estado != null) {
			listCriterion.add(Restrictions.eq("estado", estado));
		}

		return buscarPorCriteria(listCriterion, null, null);
	}

	@Override
	public CompraTasaVO guardarCompraTasa(CompraTasaVO compraTasa) {
		compraTasa.setIdCompra((Long) guardar(compraTasa));
		return compraTasa;
	}

	@Override
	public void eliminarCompraTasa(CompraTasaVO compraTasa) {
		borrar(compraTasa);
	}
}