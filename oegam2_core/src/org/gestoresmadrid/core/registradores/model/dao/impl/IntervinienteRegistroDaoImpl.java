package org.gestoresmadrid.core.registradores.model.dao.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.registradores.model.dao.IntervinienteRegistroDao;
import org.gestoresmadrid.core.registradores.model.vo.IntervinienteRegistroVO;
import org.gestoresmadrid.core.registradores.model.vo.TramiteRegRbmVO;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class IntervinienteRegistroDaoImpl extends GenericDaoImplHibernate<IntervinienteRegistroVO> implements IntervinienteRegistroDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3146091379425681775L;

	public List<IntervinienteRegistroVO> getRepresentantes(long id) {
		Criteria criteria = getCurrentSession().createCriteria(IntervinienteRegistroVO.class);
		if (id != 0) {
			criteria.add(Restrictions.eq("idRepresentado", id));
		}
		// criteria.createAlias("datRegMercantil", "datRegMercantil", CriteriaSpecification.LEFT_JOIN);
		@SuppressWarnings("unchecked")
		List<IntervinienteRegistroVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return null;

	}

	public long getIdIntervinientePorNifColegiadoTipo(IntervinienteRegistroVO interviniente) {
		Criteria criteria = getCurrentSession().createCriteria(IntervinienteRegistroVO.class);
		if (interviniente.getNif() != null && interviniente.getTipoInterviniente() != null && interviniente.getNumColegiado() != null) {
			criteria.add(Restrictions.eq("nif", interviniente.getNif()));
			criteria.add(Restrictions.eq("tipoInterviniente", interviniente.getTipoInterviniente()));
			criteria.add(Restrictions.eq("numColegiado", interviniente.getNumColegiado()));
			if (interviniente.getIdRepresentado() != 0)
				criteria.add(Restrictions.eq("nifRepresentado", interviniente.getIdRepresentado()));
			else
				criteria.add(Restrictions.isNull("nifRepresentado"));
			criteria.setProjection(Projections.property("idInterviniente"));
			criteria.createAlias("datRegMercantil", "datRegMercantil", CriteriaSpecification.LEFT_JOIN);
		}
		long lista = (long) (criteria.uniqueResult() != null ? criteria.uniqueResult() : 0L);
		if (lista != 0) {
			return lista;
		}
		return 0;
	}

	public IntervinienteRegistroVO getIntervinientePorNifColegiadoTipo(IntervinienteRegistroVO interviniente) {
		Criteria criteria = getCurrentSession().createCriteria(IntervinienteRegistroVO.class);
		if (interviniente.getNif() != null && interviniente.getTipoInterviniente() != null && interviniente.getNumColegiado() != null) {
			criteria.add(Restrictions.eq("nif", interviniente.getNif()));
			criteria.add(Restrictions.eq("tipoInterviniente", interviniente.getTipoInterviniente()));
			criteria.add(Restrictions.eq("numColegiado", interviniente.getNumColegiado()));
			if (interviniente.getIdRepresentado() != 0)
				criteria.add(Restrictions.eq("idRepresentado", interviniente.getIdRepresentado()));
			else
				criteria.add(Restrictions.eq("idRepresentado", 0L));
			criteria.createAlias("datRegMercantil", "datRegMercantil", CriteriaSpecification.LEFT_JOIN);
		}
		return (IntervinienteRegistroVO) criteria.uniqueResult();
	}

	public IntervinienteRegistroVO getInterviniente(String id) {
		Criteria criteria = getCurrentSession().createCriteria(IntervinienteRegistroVO.class);
		criteria.add(Restrictions.eq("idInterviniente", Long.parseLong(id)));
		criteria.createAlias("datRegMercantil", "datRegMercantil", CriteriaSpecification.LEFT_JOIN);
		return (IntervinienteRegistroVO) criteria.uniqueResult();
	}

	public List<IntervinienteRegistroVO> getFinanciadores() {
		Criteria criteria = getCurrentSession().createCriteria(IntervinienteRegistroVO.class);
		criteria.add(Restrictions.eq("tipoInterviniente", "021"));
		criteria.createAlias("datRegMercantil", "datRegMercantil", CriteriaSpecification.LEFT_JOIN);
		@SuppressWarnings("unchecked")
		List<IntervinienteRegistroVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return null;
	}

	public boolean hasTramites(String id) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteRegRbmVO.class);
		criteria.createAlias("intervinienteRegistro", "interviniente");
		criteria.add(Restrictions.eq("interviniente.idInterviniente", Long.parseLong(id)));
		criteria.setProjection(Projections.count("interviniente.idInterviniente"));

		Integer count = (Integer) criteria.uniqueResult();
		if (count > 0) {
			return true;
		}
		return false;
	}

	@Override
	public IntervinienteRegistroVO getIntervinienteTramiteRegRbmNif(TramiteRegRbmVO tramiteRegRbmVO, String nif) {
		Criteria criteria = getCurrentSession().createCriteria(IntervinienteRegistroVO.class);
		criteria.add(Restrictions.eq("nif", nif));
		criteria.createAlias("tramites", "tramites");
		criteria.add(Restrictions.eq("tramites.idTramiteRegistro", tramiteRegRbmVO.getIdTramiteRegistro()));
		criteria.createAlias("datRegMercantil", "datRegMercantil", CriteriaSpecification.LEFT_JOIN);
		return (IntervinienteRegistroVO) criteria.uniqueResult();
	}

	@Override
	public List<IntervinienteRegistroVO> getIntervinientesTramiteRegRbmTipo(BigDecimal idtramiteRegRbm, String tipo) {
		Criteria criteria = getCurrentSession().createCriteria(IntervinienteRegistroVO.class);
		criteria.createAlias("tramites", "tramites");
		criteria.add(Restrictions.eq("tramites.idTramiteRegistro", idtramiteRegRbm));
		criteria.add(Restrictions.eq("tipoInterviniente", tipo));
		criteria.createAlias("datRegMercantil", "datRegMercantil", CriteriaSpecification.LEFT_JOIN);
		@SuppressWarnings("unchecked")
		List<IntervinienteRegistroVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return null;
	}

	@Override
	public List<IntervinienteRegistroVO> getFinanciadoresColegiado(String numColegiado) {
		Criteria criteria = getCurrentSession().createCriteria(IntervinienteRegistroVO.class);
		criteria.add(Restrictions.eq("tipoInterviniente", "021"));
		criteria.add(Restrictions.eq("numColegiado", numColegiado));
		criteria.createAlias("datRegMercantil", "datRegMercantil", CriteriaSpecification.LEFT_JOIN);
		@SuppressWarnings("unchecked")
		List<IntervinienteRegistroVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return null;
	}

	@Override
	public List<TramiteRegRbmVO> getTramitesInterviniente(long idInterviniente) {
		Criteria criteria = getCurrentSession().createCriteria(TramiteRegRbmVO.class);
		criteria.createAlias("intervinienteRegistro", "intervinienteRegistro");
		criteria.add(Restrictions.eq("intervinienteRegistro.idInterviniente", idInterviniente));
		@SuppressWarnings("unchecked")
		List<TramiteRegRbmVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return null;
	}

	@Override
	public List<IntervinienteRegistroVO> getIntervinientesPorDireccion(String nif, String numColegiado, Long idDireccion) {
		Criteria criteria = getCurrentSession().createCriteria(IntervinienteRegistroVO.class);
		if (nif != null && !nif.isEmpty()) {
			criteria.add(Restrictions.eq("nif", nif));
		}
		if (numColegiado != null && !numColegiado.isEmpty()) {
			criteria.add(Restrictions.eq("numColegiado", numColegiado));
		}
		if (idDireccion != null) {
			criteria.add(Restrictions.eq("idDireccion", idDireccion));
		}

		criteria.createAlias("direccion", "direccion", CriteriaSpecification.LEFT_JOIN);

		@SuppressWarnings("unchecked")
		List<IntervinienteRegistroVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return Collections.emptyList();
	}

}