package org.gestoresmadrid.oegam2comun.trafico.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoPermisoDistintivoItvBean;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.FicheroInfo;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafDuplicadoDto;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;

import escrituras.beans.ResultBean;

public interface ServicioTramiteTraficoDuplicado extends Serializable {

	public static String NUMEXPEDIENTE = "NUMEXPEDIENTE";
	public static String ESTADO_VALIDAR = "estadoValidar";

	TramiteTrafDuplicadoDto getTramiteDuplicado(BigDecimal numExpediente, boolean tramiteCompleto);

	ResultBean guardarTramite(TramiteTrafDuplicadoDto tramiteDto, BigDecimal idUsuario, boolean desdeValidar, boolean desdeJustificante, boolean admin);

	ResultBean validarTramite(TramiteTrafDuplicadoDto tramiteDto);

	List<TramiteTrafDuplicadoDto> duplicadosExcel(String jefatura);

	ResultBean pendientesEnvioExcel(TramiteTrafDuplicadoDto tramiteDto, BigDecimal idUsuario);

	ArrayList<FicheroInfo> recuperarDocumentos(BigDecimal numExpediente);

	ResultBean comprobarDuplicado(BigDecimal numExpediente, BigDecimal idUsuario);

	ResultBean tramitarDuplicado(BigDecimal numExpediente, BigDecimal idUsuario);

	ResultadoPermisoDistintivoItvBean getTramitesFinalizadosTelematicamentePorContrato(Long idContrato, Date fecha);

	ResultadoPermisoDistintivoItvBean listaTramitesFinalizadosTelmPorContrato(ContratoDto contratoDto, Date fecha,String tipoSolicitud, Boolean esMotos);
}