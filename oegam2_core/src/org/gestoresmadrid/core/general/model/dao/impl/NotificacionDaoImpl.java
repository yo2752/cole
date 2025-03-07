package org.gestoresmadrid.core.general.model.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.general.model.dao.NotificacionDao;
import org.gestoresmadrid.core.general.model.vo.NotificacionVO;
import org.gestoresmadrid.core.general.model.vo.TipoTramiteVO;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.model.dao.impl.GenericDaoImplHibernate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class NotificacionDaoImpl extends GenericDaoImplHibernate<NotificacionVO> implements NotificacionDao {

	private static final long serialVersionUID = -7068451118627074253L;

	public void createNotification(NotificacionVO notificacion, UsuarioVO usuario, String tipoTramite) {
		usuario.setFechaNotificacion(notificacion.getFechaHora());
		TipoTramiteVO tipoTramiteVO = new TipoTramiteVO();
		tipoTramiteVO.setTipoTramite(tipoTramite);
		notificacion.setTipoTramite(tipoTramiteVO);
		notificacion.setUsuario(usuario);
		guardar(notificacion);
	}

	public List<NotificacionVO> getList(NotificacionVO filter, String... initialized) {
		// Recuperamos entidades Lazzy
		List<String> fetchModeJoinList = new ArrayList<>();
		for (String param : initialized) {
			if (!param.isEmpty()) {
				fetchModeJoinList.add(param);
			}
		}
		String[] fetchModeJoinArray = null;
		if (fetchModeJoinList != null) {
			fetchModeJoinArray = new String[fetchModeJoinList.size()];
			for (int i = 0; i < fetchModeJoinList.size(); i++) {
				fetchModeJoinArray[i] = fetchModeJoinList.get(i);
			}
		}
		List<Criterion> criterion = new ArrayList<>();
		if (filter.getDescripcion() != null && !filter.getDescripcion().trim().isEmpty()) {
			criterion.add(Restrictions.eq("descripcion", filter.getDescripcion()));
		}

		if (filter.getEstadoAnt() != null) {
			criterion.add(Restrictions.eq("estadoAnt", filter.getEstadoAnt()));
		}

		if (filter.getEstadoNue() != null) {
			criterion.add(Restrictions.eq("estadoNue", filter.getEstadoNue()));
		}

		if (filter.getFechaHora() != null) {
			criterion.add(Restrictions.eq("fechaHora", filter.getFechaHora()));
		}

		if (filter.getIdNotificacion() > 0) {
			criterion.add(Restrictions.eq("idNotificacion", filter.getIdNotificacion()));
		}

		if (filter.getIdTramite() != null) {
			criterion.add(Restrictions.eq("idTramite", filter.getIdTramite()));
		}

		if (filter.getTipoTramite() != null && filter.getTipoTramite().getTipoTramite() != null) {
			criterion.add(Restrictions.eq("tipoTramite.tipoTramite", filter.getTipoTramite().getTipoTramite()));
		}
		return buscarPorCriteria(-1, -1, criterion, null, null, null, null, null, fetchModeJoinArray);
	}

	@Override
	public NotificacionVO getNotificacionById(long idNotificacion) {
		Criteria criteria = getCurrentSession().createCriteria(NotificacionVO.class);
		criteria.add(Restrictions.eq("idNotificacion", idNotificacion));
		return (NotificacionVO)criteria.uniqueResult();
	}
}