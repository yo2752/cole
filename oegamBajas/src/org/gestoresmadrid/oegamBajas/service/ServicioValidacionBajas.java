package org.gestoresmadrid.oegamBajas.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafBajaVO;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafBajaDto;
import org.gestoresmadrid.oegamBajas.view.bean.ResultadoBajasBean;

import utilidades.estructuras.Fecha;

public interface ServicioValidacionBajas extends Serializable{
	
	public static final String VALIDAR_TRAMITE_BTV = "{call PQ_TRAMITE_TRAFICO.VALIDAR_TRAMITE_BTV(?,?,?,?,?,?,?,?,?)}";

	ResultadoBajasBean validacionesBloqueantesGuardado(TramiteTrafBajaDto tramiteTraficoBaja);

	ResultadoBajasBean validarFechaMatriculacion(TramiteTrafBajaVO tramiteTrafBajaVO);

	ResultadoBajasBean validarTramiteBtvPQ(TramiteTrafBajaVO tramiteTrafBajaVO, BigDecimal idUsuario);

	ResultadoBajasBean validarTramitePQ(TramiteTrafBajaVO tramiteTrafBajaVO);

	ResultadoBajasBean comprobarLocalmenteTramitabilidad(TramiteTrafBajaVO tramiteTrafBajaVO, BigDecimal numExpediente);

	Boolean vehiculoMayorDe15Anios(Fecha fechaMatriculacion);

	ResultadoBajasBean validarTramitabilidadTramite(TramiteTrafBajaVO tramiteTrafBajaVO, BigDecimal numExpediente);

	ResultadoBajasBean validarPendienteEnvioExcel(TramiteTrafBajaVO tramiteTrafBajaVO, BigDecimal numExpediente);

}
