package org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.docPermDistItv.model.enumerados.EstadoPermisoDistintivoItv;
import org.gestoresmadrid.oegam2comun.cola.view.dto.ColaDto;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoPermisoDistintivoItvBean;

public interface ServicioImpresionPermisosDgt extends Serializable{

	public static final String NOMBRE_PERM_DSTV_EITV_IMPRESION = "DocumentosImpresos_";
	
	void actualizarEstado(BigDecimal idDoc, EstadoPermisoDistintivoItv estado, String resultadoEjecucion, BigDecimal idUsuario);

	ResultadoPermisoDistintivoItvBean impresionPermisoDgt(ColaDto solicitud);
	
	
}
