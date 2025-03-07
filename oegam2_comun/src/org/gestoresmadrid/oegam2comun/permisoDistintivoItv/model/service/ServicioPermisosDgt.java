package org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.docPermDistItv.model.enumerados.EstadoPermisoDistintivoItv;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.oegam2comun.distintivo.view.bean.ResultadoDistintivoDgtBean;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoPermisoDistintivoItvBean;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;

public interface ServicioPermisosDgt extends Serializable{

	ResultadoPermisoDistintivoItvBean solicitarPermiso(BigDecimal numExpediente, BigDecimal idUsuario, Date fecha, String ipConexion);
	
	public static final String NOMBRE_HOST = "nombreHostProceso";

	ResultadoPermisoDistintivoItvBean impresionPermisos(String numExpedientes, BigDecimal idUsuario, String ipConexion);

	ResultadoPermisoDistintivoItvBean revertirPermiso(BigDecimal numExpediente, BigDecimal idUsuario, Date fecha, String ipConexion);

	List<TramiteTraficoVO> getListaTramitesPorDocId(Long idDocPermisos);

	void actualizarEstadoPeticion(BigDecimal numExpediente, EstadoPermisoDistintivoItv estadoNuevo,	BigDecimal idUsuario, String ipConexion);

	void solicitarPermisoFinalizadoError(TramiteTraficoVO tramiteTrafico, BigDecimal idUsuario, String ipConexion);

	ResultadoPermisoDistintivoItvBean cambiarEstado(BigDecimal numExpediente, BigDecimal idUsuario, Date fecha,	BigDecimal estadoNuevo, String ipConexion);

	ResultadoPermisoDistintivoItvBean generarDocPermisosContrato(ContratoDto contratoDto, Date fecha, String tipoTramite, String ipConexion);

	ResultadoDistintivoDgtBean desasignarPermiso(BigDecimal numExpediente, BigDecimal idUsuario, String ipConexion);

	ResultadoPermisoDistintivoItvBean generarKoTramite(BigDecimal numExpediente, BigDecimal idUsuario, String ipConexion);


}
