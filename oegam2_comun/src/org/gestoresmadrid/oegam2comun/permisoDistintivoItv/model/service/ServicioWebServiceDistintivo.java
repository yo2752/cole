package org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.docPermDistItv.model.enumerados.EstadoPermisoDistintivoItv;
import org.gestoresmadrid.oegam2comun.cola.view.dto.ColaDto;
import org.gestoresmadrid.oegam2comun.distintivo.view.bean.ResultadoDistintivoDgtBean;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoPermisoDistintivoItvBean;

public interface ServicioWebServiceDistintivo extends Serializable{

	public final String TIMEOUT_PROP_PERMISO_DISTINTIVO_ITV = "webservice.permisodistintivoitv.timeOut";
	public final String WEBSERVICE_PERMISO_DISTINTIVO_ITV = "webservice.permisodistintivoitv.url";
	public final String UTF_8 = "UTF-8";
	public static final String TEXTO_WS_DISTINTIVO = "DISTINTIVO";
	
	ResultadoPermisoDistintivoItvBean impresionDistintivo(ColaDto solicitud, String tipoSolicitud);
	
	ResultadoDistintivoDgtBean procesarSolicitudDstv(ColaDto solicitud);

	void actualizarEstado(BigDecimal idDoc, EstadoPermisoDistintivoItv estado, String respuesta, BigDecimal idUsuario);

	void actualizarEstadoDstv(BigDecimal numExpediente, EstadoPermisoDistintivoItv estado, String respuestaError);
	
	ResultadoDistintivoDgtBean procesarSolicitudDuplicadoDstv(ColaDto solicitud);

	void actualizarEstadoDstvVNMO(Long idVehNotMatOegam, EstadoPermisoDistintivoItv estadoNuevo, String respuesta, BigDecimal idUsuario);

	ResultadoPermisoDistintivoItvBean impresionDstvDuplicado(ColaDto solicitud);

	ResultadoDistintivoDgtBean solicitarDstvAntiguos();

	void actualizarEstadosDistintivos(Long idDoc, String estadoAnt, String estadoNuevo, BigDecimal idUsuario, String ipConexion);

}