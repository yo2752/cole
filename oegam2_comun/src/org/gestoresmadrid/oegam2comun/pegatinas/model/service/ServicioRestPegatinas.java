package org.gestoresmadrid.oegam2comun.pegatinas.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.pegatinas.model.vo.PegatinasNotificaVO;
import org.gestoresmadrid.oegam2comun.pegatinas.model.dto.RespuestaBajaPeticionStock;
import org.gestoresmadrid.oegam2comun.pegatinas.model.dto.RespuestaNotificacionImpresas;
import org.gestoresmadrid.oegam2comun.pegatinas.model.dto.RespuestaNotificacionInvalidos;
import org.gestoresmadrid.oegam2comun.pegatinas.model.dto.RespuestaPeticionStock;
import org.gestoresmadrid.oegam2comun.pegatinas.model.dto.RespuestaRecepcionStock;

public interface ServicioRestPegatinas extends Serializable{
	
	public final String PEGATINAS_ERROR_LOG = "PEGATINAS EXCEPCION: ";
	
	RespuestaPeticionStock pedirStockPegatinas(String tipoSolicitud, String datosSolicitud);

	RespuestaBajaPeticionStock solicitarBajaStock(String tipoSolicitud, String datosSolicitud);

	RespuestaRecepcionStock confirmarRecepcionStock(String tipoSolicitud, String datosSolicitud);

	RespuestaNotificacionImpresas notificarImpresion(String tipoSolicitud, List<PegatinasNotificaVO> lista, String jefatura);

	RespuestaNotificacionInvalidos notificarInvalidos(String tipoSolicitud, List<PegatinasNotificaVO> lista, String jefatura);
}
