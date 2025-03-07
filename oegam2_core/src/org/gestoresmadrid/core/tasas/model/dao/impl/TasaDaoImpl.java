package org.gestoresmadrid.core.tasas.model.dao.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.gestoresmadrid.core.model.enumerados.EstadoTasaBloqueo;
import org.gestoresmadrid.core.model.enumerados.TipoTasa;
import org.gestoresmadrid.core.tasas.model.dao.TasaDao;
import org.gestoresmadrid.core.tasas.model.vo.TasaVO;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TasaDaoImpl extends GenericDaoImplHibernate<TasaVO> implements TasaDao {

	private static final long serialVersionUID = -6478485409685384669L;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	public TasaVO getTasa(String codigoTasa, BigDecimal numExpediente, String tipoTasa) {
		Criteria criteria = getCurrentSession().createCriteria(TasaVO.class);
		if (codigoTasa != null && !codigoTasa.isEmpty()) {
			criteria.add(Restrictions.eq("codigoTasa", codigoTasa));
		}
		if (numExpediente != null) {
			criteria.add(Restrictions.eq("numExpediente", numExpediente));
		}

		if (tipoTasa != null && !tipoTasa.isEmpty()) {
			criteria.add(Restrictions.eq("tipoTasa", tipoTasa));
		}

		criteria.createCriteria("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.createCriteria("usuario", "usuario", CriteriaSpecification.LEFT_JOIN);
		criteria.setFetchMode("contrato.provincia", FetchMode.JOIN);

		return (TasaVO) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TasaVO> obtenerTasasContrato(Long idContrato, String tipoTasa, int maxResult) {
		Criteria criteria = getCurrentSession().createCriteria(TasaVO.class);
		if (tipoTasa != null && !tipoTasa.isEmpty()) {
			criteria.add(Restrictions.eq("tipoTasa", tipoTasa));
		}
		if (idContrato != null) {
			Criteria criteriaContrato = criteria.createCriteria("contrato", "contrato",
					CriteriaSpecification.LEFT_JOIN);
			criteriaContrato.add(Restrictions.eq("idContrato", idContrato));
		}
		criteria.add(Restrictions.isNull("fechaAsignacion"));
		criteria.add(Restrictions.isNull("numExpediente"));
		criteria.add(Restrictions.or(Restrictions.isNull("bloqueada"),
				Restrictions.eq("bloqueada", EstadoTasaBloqueo.DESBLOQUEADA.getValorEnum())));

		criteria.add(Restrictions.ge("fechaAlta", utilesFecha.getFechaDate("20180701")));

		if (maxResult != 0) {
			criteria.setMaxResults(maxResult);
		}

		criteria.addOrder(Order.asc("fechaAlta"));
		criteria.addOrder(Order.asc("codigoTasa"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public TasaVO getTasaLibre(Long idContrato, String tipoTasa) {
		Criteria criteria = getCurrentSession().createCriteria(TasaVO.class);

		criteria.add(Restrictions.eq("tipoTasa", tipoTasa));
		criteria.add(Restrictions.eq("contrato.idContrato", idContrato));
		criteria.add(Restrictions.isNull("fechaAsignacion"));
		criteria.add(Restrictions.isNull("numExpediente"));
		criteria.add(Restrictions.or(Restrictions.isNull("bloqueada"),
				Restrictions.eq("bloqueada", EstadoTasaBloqueo.DESBLOQUEADA.getValorEnum())));

		criteria.createCriteria("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);

		criteria.addOrder(Order.asc("fechaAlta"));
		criteria.addOrder(Order.asc("codigoTasa"));

		List<TasaVO> list = criteria.list();
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TasaVO> getTasasLibres(Long idContrato, String tipoTasa) {
		Criteria criteria = getCurrentSession().createCriteria(TasaVO.class);

		if (StringUtils.isNotBlank(tipoTasa)) {
			criteria.add(Restrictions.eq("tipoTasa", tipoTasa));
		}
		if (idContrato != null) {
			criteria.add(Restrictions.eq("contrato.idContrato", idContrato));
		}
		criteria.add(Restrictions.isNull("fechaAsignacion"));
		criteria.add(Restrictions.isNull("numExpediente"));
		criteria.add(Restrictions.or(Restrictions.isNull("bloqueada"),
				Restrictions.eq("bloqueada", EstadoTasaBloqueo.DESBLOQUEADA.getValorEnum())));

		criteria.createCriteria("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);

		criteria.addOrder(Order.asc("fechaAlta"));
		criteria.addOrder(Order.asc("codigoTasa"));

		List<TasaVO> list = criteria.list();
		if (list != null && !list.isEmpty()) {
			return criteria.list();
		}

		return Collections.emptyList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TasaVO> getTasasLibresMatriculacion(Long idContrato) {
		Criteria criteria = getCurrentSession().createCriteria(TasaVO.class);

		if (idContrato != null) {
			criteria.add(Restrictions.eq("contrato.idContrato", idContrato));
		}
		criteria.add(Restrictions.or(Restrictions.eq("tipoTasa", TipoTasa.UnoUno.getValorEnum()),
				Restrictions.eq("tipoTasa", TipoTasa.UnoDos.getValorEnum())));
		criteria.add(Restrictions.isNull("fechaAsignacion"));
		criteria.add(Restrictions.isNull("numExpediente"));
		criteria.add(Restrictions.or(Restrictions.isNull("bloqueada"),
				Restrictions.eq("bloqueada", EstadoTasaBloqueo.DESBLOQUEADA.getValorEnum())));

		criteria.createCriteria("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);

		criteria.addOrder(Order.asc("fechaAlta"));
		criteria.addOrder(Order.asc("codigoTasa"));

		List<TasaVO> list = criteria.list();
		if (list != null && !list.isEmpty()) {
			return criteria.list();
		}

		return Collections.emptyList();
	}

	@Override
	public TasaVO getTasaInteve(String codigoTasa, BigDecimal numExpediente, String tipoTasa) {
		Criteria criteria = getCurrentSession().createCriteria(TasaVO.class);
		if (codigoTasa != null && !codigoTasa.isEmpty()) {
			criteria.add(Restrictions.eq("codigoTasa", codigoTasa));
		}
		if (numExpediente != null) {
			criteria.add(Restrictions.eq("numExpediente", numExpediente));
		}

		if (tipoTasa != null && !tipoTasa.isEmpty()) {
			criteria.add(Restrictions.eq("tipoTasa", tipoTasa));
		}

		criteria.createCriteria("contrato", "contrato", CriteriaSpecification.LEFT_JOIN);
		criteria.setFetchMode("contrato.provincia", FetchMode.JOIN);

		@SuppressWarnings("unchecked")
		List<TasaVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista.get(0);
		}
		return null;
	}

	@Override
	public List<TasaVO> getTasasPorNumExpediente(BigDecimal numExpediente) {
		Criteria criteria = getCurrentSession().createCriteria(TasaVO.class);

		if (numExpediente != null) {
			criteria.add(Restrictions.eq("numExpediente", numExpediente));
		}
		List<TasaVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}

		return Collections.emptyList();
	}

}
