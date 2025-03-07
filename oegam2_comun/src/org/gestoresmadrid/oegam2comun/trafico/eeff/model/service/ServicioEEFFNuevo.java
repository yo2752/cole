package org.gestoresmadrid.oegam2comun.trafico.eeff.model.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.trafico.eeff.model.enumerados.EstadoConsultaEEFF;
import org.gestoresmadrid.core.trafico.eeff.model.vo.ConsultaEEFFVO;
import org.gestoresmadrid.core.trafico.eeff.model.vo.LiberacionEEFFVO;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.dto.ConsultaEEFFDto;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.dto.LiberacionEEFFDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafMatrDto;

import trafico.beans.jaxb.matw.FORMATOGA.MATRICULACION;
import escrituras.beans.ResultBean;

public interface ServicioEEFFNuevo extends Serializable{

	public static final String COBRAR_CREDITOS_EEFF = "eeff.creditos.cobrarCreditos.liberar";
	public static final String NOMBRE_XML = "nombreXml";
	public static final String NOMBRE_HOST_SOLICITUD = "nombreHostSolicitud";
	public static final String CONSULTA_EEFF_DTO = "consultaEEFFDto";
	public static final String NUM_EXPEDIENTE_CONSULTA_EEFF_DTO = "numExpedienteDto";
	public static final int    EEFF_LONGITUD_NIVE = 32;
	public static final String EEFF_COBRAR_CREDITOS_CONSULTA = "eeff.creditos.cobrarCreditos.consulta";

	ResultBean liberar(LiberacionEEFFDto liberacionEEFFDTO, BigDecimal idContrato, BigDecimal idUsuario);

	ResultBean guardarDatosLiberacion(TramiteTrafMatrDto tramiteTrafMatrDto, BigDecimal numExpediente);

	LiberacionEEFFVO getLiberacionEEFFVO(BigDecimal numExpediente);

	ResultBean comprobarDatosModificadosLiberacion(TramiteTrafMatrDto tramiteTrafMatrDto);

	LiberacionEEFFDto getLiberacionEEFFDto(BigDecimal numExpediente);

	ResultBean guardarLiberacionProceso(LiberacionEEFFDto liberacionEEFFDto);

	ResultBean obtenerDetalleConsultaEEFF(BigDecimal numExpediente);

	ResultBean guardarConsultaEEFF(ConsultaEEFFDto consultaEEFF);

	ResultBean consultarEEFF(BigDecimal numExpediente, BigDecimal idUsuario, Boolean esConsulta);

	ConsultaEEFFVO getConsultaEEFFVO(BigDecimal numExpediente, Boolean consultaCompleta);

	ConsultaEEFFDto getConsultaEEFFDto(BigDecimal numExpediente, Boolean consultaCompleta);

	ResultBean guardarConsultaEEFFProceso(ConsultaEEFFDto consultaEEFFDto);

	ResultBean cambiarEstadoConsultaEEFF(BigDecimal numExpediente, EstadoConsultaEEFF estadoNuevo, BigDecimal idUsuario, Boolean accionesAsociadas);

	ResultBean validarEeffLibMatwFORMATOGA(MATRICULACION formatoMatriculacion);

	ResultBean guardarDatosImportacion(MATRICULACION formatoMatriculacion, BigDecimal numExpediente);
	/**
	 * 
	 * @param numExpedienteTramite numero de expediente de tramite trafico
	 * @param idUsuario usuario que realiza 
	 * @return
	 */
	ResultBean realizarConsultaEEFFTramiteTrafico(BigDecimal numExpedienteTramite, BigDecimal idUsuario);

}