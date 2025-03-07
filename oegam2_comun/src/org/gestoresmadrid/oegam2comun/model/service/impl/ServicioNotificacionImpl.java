package org.gestoresmadrid.oegam2comun.model.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.general.model.dao.NotificacionDao;
import org.gestoresmadrid.core.general.model.vo.ColegiadoVO;
import org.gestoresmadrid.core.general.model.vo.NotificacionVO;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.oegam2comun.model.service.ServicioNotificacion;
import org.gestoresmadrid.oegam2comun.model.service.ServicioUsuario;
import org.gestoresmadrid.oegam2comun.view.dto.NotificacionDto;
import org.gestoresmadrid.oegamComun.colegiado.services.ServicioColegiado;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hibernate.entities.trafico.TramiteTrafico;

@Service
public class ServicioNotificacionImpl implements ServicioNotificacion {

	private static final long serialVersionUID = 4231422726344922626L;

	@Autowired
	private NotificacionDao notificacionDao;

	@Autowired
	private ServicioUsuario servicioUsuario;

	@Autowired
	private ServicioColegiado servicioColegiado;

	@Autowired
	private Conversor conversor;

	@Autowired
	Utiles utiles;

	@Transactional
	@Override
	public void notificarCambioEstado(List<TramiteTrafico> tramites, EstadoTramiteTrafico estadoNuevo, BigDecimal idUsuario) {
		NotificacionVO notificacion = new NotificacionVO();
		notificacion.setDescripcion("CAMBIO DE ESTADO");
		notificacion.setEstadoNue(new BigDecimal(estadoNuevo.getValorEnum()));
		notificacion.setFechaHora(new Date());
		UsuarioVO usuario = servicioUsuario.getUsuario(idUsuario);
		if (tramites != null) {
			for (TramiteTrafico tramite : tramites) {
				notificacion.setIdTramite(utiles.convertirLongABigDecimal(tramite.getNumExpediente()));
				notificacion.setEstadoAnt(tramite.getEstado());
				notificacionDao.createNotification(notificacion, usuario, tramite.getTipoTramite());
			}
		}
	}

	@Transactional
	@Override
	public void crearNotificacion(NotificacionDto dto) {
		NotificacionVO notificacion = conversor.transform(dto, NotificacionVO.class);
		notificacion.setFechaHora(new Date());
		if (notificacion.getDescripcion().length() > 45) {
			notificacion.setDescripcion(notificacion.getDescripcion().substring(0, 45));
		}
		notificacionDao.createNotification(notificacion, servicioUsuario.getUsuario(new BigDecimal(dto.getIdUsuario())), dto.getTipoTramite());
	}

	@Transactional
	@Override
	public void crearNotificacionConFechaNotificacion(NotificacionDto dto) {
		NotificacionVO notificacion = conversor.transform(dto, NotificacionVO.class);
		notificacion.setFechaHora(new Date());
		if (notificacion.getDescripcion().length() > 45) {
			notificacion.setDescripcion(notificacion.getDescripcion().substring(0, 45));
		}

		UsuarioVO usuario = servicioUsuario.getUsuario(new BigDecimal(dto.getIdUsuario()));
		notificacionDao.createNotification(notificacion, usuario, dto.getTipoTramite());

		ColegiadoVO colegiado = servicioColegiado.getColegiado(usuario.getNumColegiado());

		if (colegiado != null && !colegiado.getIdUsuario().equals(usuario.getIdUsuario())) {
			notificacion.setFechaHora(new Date());
			notificacionDao.createNotification(notificacion, servicioUsuario.getUsuario(new BigDecimal(colegiado.getIdUsuario())), dto.getTipoTramite());
		}
	}

	@Transactional
	@Override
	public List<NotificacionDto> getList(NotificacionDto filter, String... initialized) {
		NotificacionVO vo = conversor.transform(filter, NotificacionVO.class);
		List<NotificacionVO> lista = notificacionDao.getList(vo, initialized);
		return conversor.transform(lista, NotificacionDto.class);
	}

	@Override
	@Transactional
	public NotificacionVO getNotificacionById(long idNotificacion) {
		return notificacionDao.getNotificacionById(idNotificacion);
	}
	
}
