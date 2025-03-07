package org.gestoresmadrid.oegam2comun.trafico.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.oegam2comun.trafico.view.beans.ResultadoCambioServicioBean;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafCambioServicioDto;

import escrituras.beans.ResultBean;

public interface ServicioTramiteTraficoCambioServicio extends Serializable {

	public static String NUMEXPEDIENTE = "NUMEXPEDIENTE";

	TramiteTrafCambioServicioDto getTramiteCambServ(BigDecimal numExpediente, boolean tramiteCompleto);

	ResultadoCambioServicioBean guardarTramite(TramiteTrafCambioServicioDto tramiteTraficoCambServ, BigDecimal idUsuarioDeSesion, boolean desdeValidar, boolean admin);

	ResultadoCambioServicioBean consultarPersona(TramiteTrafCambioServicioDto tramiteTraficoCambServ, String tipoIntervinienteBuscar);

	ResultadoCambioServicioBean consultaVehiculo(TramiteTrafCambioServicioDto tramiteTraficoCambServ, String matriculaBusqueda);

	ResultadoCambioServicioBean validarTramite(TramiteTrafCambioServicioDto tramiteTraficoCambServ, BigDecimal idUsuario);

	ResultBean pendientesEnvioExcel(TramiteTrafCambioServicioDto tramiteTraficoCambServ, BigDecimal idUsuario);

	List<TramiteTrafCambioServicioDto> cambioServicioExcel(String jefatura);
}