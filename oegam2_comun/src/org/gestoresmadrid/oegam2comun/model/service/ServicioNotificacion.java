package org.gestoresmadrid.oegam2comun.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.general.model.vo.NotificacionVO;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.oegam2comun.view.dto.NotificacionDto;

import hibernate.entities.trafico.TramiteTrafico;

public interface ServicioNotificacion extends Serializable {

	public void notificarCambioEstado(List<TramiteTrafico> tramites, EstadoTramiteTrafico estadoNuevo, BigDecimal idUsuario);

	void crearNotificacion(NotificacionDto dto);

	void crearNotificacionConFechaNotificacion(NotificacionDto dto);

	List<NotificacionDto> getList(NotificacionDto filter, String... initialized);

	public NotificacionVO getNotificacionById(long idNotificacion);
}
