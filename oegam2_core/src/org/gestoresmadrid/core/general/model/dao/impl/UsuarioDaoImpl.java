package org.gestoresmadrid.core.general.model.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.gestoresmadrid.core.general.model.dao.UsuarioDao;
import org.gestoresmadrid.core.general.model.vo.ContratoUsuarioVO;
import org.gestoresmadrid.core.general.model.vo.GrupoVO;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.model.beans.AliasQueryBean;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioDaoImpl extends GenericDaoImplHibernate<UsuarioVO> implements UsuarioDao {

	private static final long serialVersionUID = 6657573491766111197L;

	@SuppressWarnings("unchecked")
	@Override
	public List<UsuarioVO> getUsuarioPorNifYEstado(String nifUsuario, BigDecimal estadoUsuario) {
		Criteria criteria = getCurrentSession().createCriteria(UsuarioVO.class);
		criteria.add(Restrictions.eq("nif", nifUsuario));
		criteria.add(Restrictions.eq("estadoUsuario", estadoUsuario));
		return criteria.list();
	}

	@Override
	public UsuarioVO getUsuarioPorNifYColegiado(String nif, String numColegiado) {
		Criteria criteria = getCurrentSession().createCriteria(UsuarioVO.class);
		criteria.add(Restrictions.eq("nif", nif));
		criteria.add(Restrictions.eq("numColegiado", numColegiado));
		return (UsuarioVO) criteria.uniqueResult();
	}

	@Override
	public UsuarioVO getUsuarioPorNifYEstadoVO(String nifUsuario, BigDecimal estadoUsuario) {
		Criteria criteria = getCurrentSession().createCriteria(UsuarioVO.class);
		criteria.add(Restrictions.eq("nif", nifUsuario));
		criteria.add(Restrictions.eq("estadoUsuario", estadoUsuario));
		return (UsuarioVO) criteria.uniqueResult();
	}

	@Override
	public UsuarioVO getUsuario(Long idUsuario) {
		Criteria criteria = getCurrentSession().createCriteria(UsuarioVO.class);
		if (idUsuario != null) {
			criteria.add(Restrictions.eq("idUsuario", idUsuario));
		}
		return (UsuarioVO) criteria.uniqueResult();
	}

	@Override
	public String getJefaturaProvincialPorUsuario(Long idUsuario) {
		Criteria criteria = getCurrentSession().createCriteria(UsuarioVO.class);
		if (idUsuario != null) {
			criteria.add(Restrictions.eq("idUsuario", idUsuario));
		}
		@SuppressWarnings("unchecked")
		List<UsuarioVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			UsuarioVO usuarioVO = lista.get(0);
			return usuarioVO.getJefaturaJPT();
		}
		return null;
	}

	@Override
	public List<UsuarioVO> obtenerGrupoUsuarioPorNif(UsuarioVO usuarioVO) {
		List<AliasQueryBean> listaAlias = new ArrayList<>();
		listaAlias.add(new AliasQueryBean(GrupoVO.class, "grupo", "grupo", CriteriaSpecification.LEFT_JOIN));
		List<Criterion> listCriterion = new ArrayList<>();
		listCriterion.add(Restrictions.eq("nif", usuarioVO.getNif()));
		listCriterion.add(Restrictions.eq("estadoUsuario", usuarioVO.getEstadoUsuario()));
		return buscarPorCriteria(listCriterion, listaAlias, null);
	}

	public Integer numUsuariosPorNifEstado(String nif, BigDecimal estado) {
		Criteria criteria = getCurrentSession().createCriteria(UsuarioVO.class);
		criteria.add(Restrictions.eq("nif", nif));
		criteria.add(Restrictions.eq("estadoUsuario", estado));
		criteria.setProjection(Projections.rowCount());
		return (Integer) criteria.uniqueResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<UsuarioVO> getUsuariosNumColegiado(String numColegiado) {
		Criteria criteria = getCurrentSession().createCriteria(UsuarioVO.class);
		criteria.add(Restrictions.eq("numColegiado", numColegiado));
		return (List<UsuarioVO>) criteria.list();
	}

	@Override
	public List<UsuarioVO> getUsuarioPorNumColegiado(String numColegiado) {
		Criteria criteria = getCurrentSession().createCriteria(UsuarioVO.class);
		if (numColegiado != null && !numColegiado.isEmpty()) {
			criteria.add(Restrictions.eq("numColegiado", numColegiado));
			criteria.addOrder(Order.asc("apellidosNombre"));
		}

		@SuppressWarnings("unchecked")
		List<UsuarioVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return Collections.emptyList();
	}

	@Override
	public List<UsuarioVO> getUsuariosHabilitadosPorNumColegiado(String numColegiado) {
		Criteria criteria = getCurrentSession().createCriteria(UsuarioVO.class);
		if (numColegiado != null && !numColegiado.isEmpty()) {
			criteria.add(Restrictions.eq("numColegiado", numColegiado));
			criteria.add(Restrictions.eq("estadoUsuario", BigDecimal.ONE));
		}
		@SuppressWarnings("unchecked")
		List<UsuarioVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}
		return Collections.emptyList();
	}

	// Para obtener todos los usuarios de un contrato
	@Override
	public List<UsuarioVO> getUsuariosPorContrato(Long idContrato) {
		List<UsuarioVO> listaUsuarios = new ArrayList<>();
		List<ContratoUsuarioVO> listaContratosUsuario = new ArrayList<>();

		Criteria criteria = getCurrentSession().createCriteria(ContratoUsuarioVO.class);
		if (idContrato != null ) {
			criteria.add(Restrictions.eq("id.idContrato", idContrato.longValue()));
			criteria.add(Restrictions.eq("estadoUsuarioContrato", BigDecimal.ONE));

			listaContratosUsuario = criteria.list();

			for(ContratoUsuarioVO elem:listaContratosUsuario) {
				listaUsuarios.add(elem.getUsuario());
			}
			return listaUsuarios;
		}
		return null;
	}
}