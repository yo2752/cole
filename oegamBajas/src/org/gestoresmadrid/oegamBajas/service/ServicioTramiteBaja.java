package org.gestoresmadrid.oegamBajas.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafBajaDto;
import org.gestoresmadrid.oegamBajas.view.bean.ResultadoBajasBean;
import org.gestoresmadrid.oegamComun.interviniente.view.dto.IntervinienteTraficoDto;
import org.gestoresmadrid.oegamComun.vehiculo.view.dto.VehiculoDto;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;

public interface ServicioTramiteBaja extends Serializable {

	ResultadoBajasBean guardarTramite(TramiteTrafBajaDto tramiteTraficoBaja, BigDecimal idUsuario);

	ResultadoBajasBean obtenerTramiteBajaDto(BigDecimal numExpediente);

	ResultadoBajasBean validarTramite(BigDecimal numExpediente, BigDecimal idUsuario);

	ResultadoBajasBean comprobarBtv(BigDecimal numExpediente, BigDecimal idUsuario);

	ResultadoBajasBean tramitarBtv(BigDecimal numExpediente, BigDecimal idUsuarioDeSesion);

	ResultadoBajasBean pendienteEnvioExcel(BigDecimal numExpediente, BigDecimal idUsuario);

	ResultadoBean crearTramiteBajaJustificante(Long idContrato, Long idUsuario, String numColegiado, IntervinienteTraficoDto titular, VehiculoDto vehiculo, String refPropia, String jefaturaProvincial);
}
