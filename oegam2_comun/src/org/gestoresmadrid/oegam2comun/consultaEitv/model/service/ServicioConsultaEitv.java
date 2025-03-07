package org.gestoresmadrid.oegam2comun.consultaEitv.model.service;

import java.math.BigDecimal;

import org.gestoresmadrid.oegam2comun.cola.view.dto.ColaDto;
import org.gestoresmadrid.oegam2comun.consultaEitv.model.dto.RespuestaConsultaEitv;
import org.gestoresmadrid.oegam2comun.trafico.prematriculados.view.dto.VehiculoPrematriculadoDto;

import escrituras.beans.ResultBean;

public interface ServicioConsultaEitv {

	public final String NOMBRE_HOST_SOLICITUD_NODO_2 = "nombreHostSolicitudProcesos2";
	public final String NOMBRE_HOST_SOLICITUD_NODO_3 = "nombreHostSolicitudProcesos3";
	public final String CONSULTA_TARJETA_XML_ENVIAR = "EITV";
	public final String CONSULTA_TARJETA_PRE_MATRICULADOS_XML_ENVIAR = "PRE_EITV";
	public final String TEXTO_LEGAL = "texto legal";

	RespuestaConsultaEitv generarConsultaEitv(ColaDto colaDto);

	ResultBean consultaEitv(BigDecimal numExpediente, BigDecimal idUsuarioDeSesion, BigDecimal idContrato);

	ResultBean consultaEitvPreMatriculados(VehiculoPrematriculadoDto vehiculo, BigDecimal idUsuarioDeSesion, BigDecimal idContrato);

	void cambiarEstadoTramiteProceso(BigDecimal numExpediente, BigDecimal estadoNuevo, BigDecimal idUsuario);

}
