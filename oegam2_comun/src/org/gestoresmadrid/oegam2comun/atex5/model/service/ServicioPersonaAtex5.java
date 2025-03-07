package org.gestoresmadrid.oegam2comun.atex5.model.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.atex5.model.vo.ConsultaPersonaAtex5VO;
import org.gestoresmadrid.oegam2comun.atex5.view.bean.ResultadoAtex5Bean;
import org.gestoresmadrid.oegam2comun.atex5.view.dto.ConsultaPersonaAtex5Dto;

import es.trafico.servicios.vehiculos.consulta.atex.webservices.RespuestaAtex;


public interface ServicioPersonaAtex5 extends Serializable{
	
	public final String NOMBRE_XML = "CONSULTA_PERSONA_ATEX5_";
	public final String NOMBRE_ZIP = "CONSULTAS_PERSONAS_ATEX5";
	public final String NOMBRE_HOST_SOLICITUD_NODO_2 = "nombreHostSolicitudProcesos2";
	public final String descConsultaPersAtex5Dto = "consultaPersAtex5Dto";
	public final String numExpedientePersAtex5 = "numExpediente";
	public final String NOMBRE_FICHERO = "nombreFichero";
	public final String FICHERO = "fichero";
	public final String RUTA_FICH_TEMP = "RUTA_ARCHIVOS_TEMP";
	public static String cobrarCreditos = "cobrar.properties.atex5";

	ResultadoAtex5Bean getConsultaPersonaAtex5(BigDecimal numExpediente);

	ResultadoAtex5Bean guardarConsultaPersonaAtex5(ConsultaPersonaAtex5Dto consultaPersonaAtex5Dto, BigDecimal idUsuario);

	ConsultaPersonaAtex5VO getConsultaPersonaAtex5VO(Long idConsultaPersonaAtex5, Boolean tramiteCompleto);

	ResultadoAtex5Bean consultarPersonaAtex5(ConsultaPersonaAtex5Dto consultaPersonaAtex5Dto, BigDecimal idUsuario);

	ConsultaPersonaAtex5VO getConsultaPersonaAtex5PorExpVO(BigDecimal numExpediente, Boolean tramiteCompleto);

	ResultadoAtex5Bean guardarXmlConsultaPersonaAtex5(BigDecimal numExpediente, RespuestaAtex respuestaAtex);

	ResultadoAtex5Bean cambiarEstado(BigDecimal numExpediente, BigDecimal idUsuario, BigDecimal estadoNuevo, Boolean accionesAsociadas);

	ResultadoAtex5Bean devolverCreditosWS(BigDecimal numExpediente,	BigDecimal idUsuario, BigDecimal idContrato);

	ResultadoAtex5Bean imprimirPdf(BigDecimal numExpediente);



}
