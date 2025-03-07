package org.gestoresmadrid.core.general.model.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.general.model.dao.ContratoUsuarioDao;
import org.gestoresmadrid.core.general.model.vo.ContratoUsuarioVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ContratoUsuarioDaoImpl extends GenericDaoImplHibernate<ContratoUsuarioVO> implements ContratoUsuarioDao {

	private static final long serialVersionUID = 6198303284458837087L;
	private static final String USUARIO = "usuario";

	@Override
	public ContratoUsuarioVO getContratoUsuario(String idUsuario, Long idContrato) {
		Criteria criteria = getCurrentSession().createCriteria(ContratoUsuarioVO.class);
		criteria.add(Restrictions.eq("id.idUsuario", idUsuario));
		criteria.add(Restrictions.eq("id.idContrato", idContrato));
		return (ContratoUsuarioVO) criteria.uniqueResult();
	}

	@Override
	public List<ContratoUsuarioVO> getContratosPorUsuario(BigDecimal idUsuario) {
		Criteria criteria = getCurrentSession().createCriteria(ContratoUsuarioVO.class);
		criteria.add(Restrictions.eq("id.idUsuario", idUsuario.toString()));
		criteria.createAlias(USUARIO, USUARIO, CriteriaSpecification.LEFT_JOIN);
		criteria.add(Restrictions.eq("usuario.estadoUsuario", new BigDecimal(1)));
		criteria.add(Restrictions.eq("estadoUsuarioContrato", new BigDecimal(1)));
		return (List<ContratoUsuarioVO>) criteria.list();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<ContratoUsuarioVO> getContratosAnterioresPorUsuario(BigDecimal idUsuario) {
		Criteria criteria = getCurrentSession().createCriteria(ContratoUsuarioVO.class);
		criteria.add(Restrictions.eq("id.idUsuario", idUsuario.toString()));
		criteria.createAlias(USUARIO, USUARIO, CriteriaSpecification.LEFT_JOIN);
		criteria.add(Restrictions.eq("estadoUsuarioContrato", new BigDecimal(6)));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return (List<ContratoUsuarioVO>) criteria.list();
	}

	@Override
	public List<ContratoUsuarioVO> getUsuariosPorContrato(Long idContrato) {
		Criteria criteria = getCurrentSession().createCriteria(ContratoUsuarioVO.class);
		criteria.add(Restrictions.eq("id.idContrato", idContrato));
		criteria.createAlias(USUARIO, USUARIO, CriteriaSpecification.LEFT_JOIN);
		criteria.add(Restrictions.eq("usuario.estadoUsuario", new BigDecimal(1)));
		criteria.addOrder(Order.asc("usuario.apellidosNombre"));
		return (List<ContratoUsuarioVO>) criteria.list();
	}

	/*
	 * @Override public List<UsuarioVO> getUsuariosPorContrato(Long idContrato){ Criteria criteria = getCurrentSession().createCriteria(ContratoUsuarioVO.class); criteria.add(Restrictions.eq("id.idContrato", idContrato.toString())); return (UsuarioVO) criteria.list(); }
	 */

}