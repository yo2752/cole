package org.gestoresmadrid.core.general.model.dao.impl;

import java.util.Collections;
import java.util.List;

import org.gestoresmadrid.core.general.model.dao.UsuarioFuncionSinAccesoDao;
import org.gestoresmadrid.core.general.model.vo.UsuarioFuncionSinAccesoVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioFuncionSinAccesoDaoImpl extends GenericDaoImplHibernate<UsuarioFuncionSinAccesoVO> implements UsuarioFuncionSinAccesoDao {

	private static final long serialVersionUID = 3066507329016730267L;

	@Override
	public List<UsuarioFuncionSinAccesoVO> compruebaAcceso(String codigoFuncion) {
		Criteria criteria = getCurrentSession().createCriteria(UsuarioFuncionSinAccesoVO.class);
		criteria.add(Restrictions.eq("id.codigoFuncion", codigoFuncion));
		@SuppressWarnings("unchecked")
		List<UsuarioFuncionSinAccesoVO> lista = criteria.list();
		if (lista != null && !lista.isEmpty()) {
			return lista;
		}

		return Collections.emptyList();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UsuarioFuncionSinAccesoVO> getUsuarioFuncionSinAcceso(String codigoAplicacion, String codigoFuncion, Long idContrato, Long idUsuario) {
		Criteria criteria = getCurrentSession().createCriteria(UsuarioFuncionSinAccesoVO.class);
		if (codigoAplicacion != null) {
			criteria.add(Restrictions.eq("id.codigoAplicacion", codigoAplicacion));
		}
		if (codigoFuncion != null) {
			criteria.add(Restrictions.eq("id.codigoFuncion", codigoFuncion));
		}
		if (idContrato != null) {
			criteria.add(Restrictions.eq("id.idContrato", idContrato));
		}
		if (idUsuario != null) {
			criteria.add(Restrictions.eq("id.idUsuario", idUsuario));
		}
		return (List<UsuarioFuncionSinAccesoVO>) criteria.list();
	}

	@Override
	public void borrar(String codigoAplicacion, String codigoFuncion, Long idContrato, Long idUsuario) {
		Criteria criteria = getCurrentSession().createCriteria(UsuarioFuncionSinAccesoVO.class);
		criteria.add(Restrictions.eq("id.codigoAplicacion", codigoAplicacion));
		criteria.add(Restrictions.eq("id.codigoFuncion", codigoFuncion));
		criteria.add(Restrictions.eq("id.idContrato", idContrato));
		criteria.add(Restrictions.eq("id.idUsuario", idUsuario));

		@SuppressWarnings("unchecked")
		List<UsuarioFuncionSinAccesoVO> list = (List<UsuarioFuncionSinAccesoVO>) criteria.list();
		for (UsuarioFuncionSinAccesoVO u : list) {
			getCurrentSession().delete(u);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UsuarioFuncionSinAccesoVO> getListaFuncionSinAccesoContrato(Long idContrato) {
		Criteria criteria = getCurrentSession().createCriteria(UsuarioFuncionSinAccesoVO.class);
		criteria.add(Restrictions.eq("id.idContrato", idContrato));
		criteria.add(Restrictions.eq("id.idUsuario", 0L));
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UsuarioFuncionSinAccesoVO> getListaFuncionSinAccesoPorContratoYUsuario(Long idContrato, Long idUsuario) {
		Criteria criteria = getCurrentSession().createCriteria(UsuarioFuncionSinAccesoVO.class);
		criteria.add(Restrictions.eq("id.idContrato", idContrato));
		criteria.add(Restrictions.eq("id.idUsuario", idUsuario));
		return criteria.list();
	}
}
