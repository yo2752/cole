package org.gestoresmadrid.oegam2comun.trafico.eeff.model.service;

import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.core.trafico.eeff.model.vo.EeffLiberacionVO;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.beans.DetalleConsultaEEFFBean;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.dto.EeffConsultaDTO;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.dto.EeffLiberacionDTO;

import trafico.beans.jaxb.matw.FORMATOGA.MATRICULACION;
import escrituras.beans.ResultBean;

/**
 * 
 * @author Globaltms
 *
 */
public interface ServicioEEFF {

	/* Implica varios servicios */

	List<ResultBean> consultarMasivoDesdeLiberacion(String[] numExpedientes);

	/* Liberacion */

	EeffLiberacionDTO recuperarDatosLiberacion(BigDecimal numExpediente);

	boolean guardarDatosLiberacion(EeffLiberacionDTO eeffDTO, BigDecimal numExpediente, String numColegiado);

	ResultBean guardarDatosImportadosLiberacion(MATRICULACION faMa, BigDecimal numExp);

	EeffLiberacionDTO actualizarDatosLiberacion(EeffLiberacionDTO eeffDTO);

	ResultBean liberar(BigDecimal numExpediente, BigDecimal idUsuario);

	ResultBean validarEeffLibMatwFORMATOGA(MATRICULACION faMa);

	boolean duplicarLiberacion(BigDecimal numExpedientePrevio, BigDecimal numExpedienteNuevo);

	boolean esRealizadoLiberacion(BigDecimal numExpediente);

	List<ResultBean> liberarMasivo(String[] numsExpedientes, BigDecimal idUsuario);

	/* Consulta */

	EeffConsultaDTO recuperarDatosConsulta(BigDecimal numExpediente);

	List<String> validarDatosConsulta(EeffConsultaDTO eeffDTO);

	boolean actualizarDatosConsulta(EeffConsultaDTO eeffDTO);

	boolean solicitarConsulta(EeffConsultaDTO eeffDTO);

	ResultBean consultarDatos(EeffConsultaDTO eeffConsulta);

	StringBuffer getFicheroXmlLiberar(ColaBean solicitud);

	EeffLiberacionVO liberacionProcesoRealizada(EeffLiberacionVO eeffLiberacion, String estadoBastidor, String numRegistroEntrada, String numRegistroSalida);

	StringBuffer getFicheroXmlConsultar(ColaBean solicitud);

	ResultBean liberarProceso(ColaBean solicitud);

	ResultBean procesoConsultar(ColaBean solicitud);

	boolean actualizarSolicitudSinXml(BigDecimal idTramite);

	DetalleConsultaEEFFBean getDetalleConsultaEEFF(String numExpediente);
}