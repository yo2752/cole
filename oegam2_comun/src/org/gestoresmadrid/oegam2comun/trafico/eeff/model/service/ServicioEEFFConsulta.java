package org.gestoresmadrid.oegam2comun.trafico.eeff.model.service;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.core.trafico.eeff.model.vo.EeffConsultaVO;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.beans.DetalleConsultaEEFFBean;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.dto.EeffConsultaDTO;

import escrituras.beans.ResultBean;

/**
 * 
 * @author Global
 *
 */
public interface ServicioEEFFConsulta {

	List<String> validarDatos(EeffConsultaDTO eeff);

	BigDecimal guardarDatos(EeffConsultaVO eeffConsultaVO);

	boolean actualizarDatos(EeffConsultaDTO eeffDTO);

	EeffConsultaDTO recuperarDatos(BigDecimal numExpediente);

	boolean solicitarConsulta(EeffConsultaDTO eeffConsultaDTO);

	ResultBean consultar(EeffConsultaDTO eeffConsulta);

	StringBuffer getFicheroXml(ColaBean solicitud);

	ResultBean comprobarCreditos(int numCreditos);

	ResultBean consultaProceso(ColaBean solicitud);

	EeffConsultaVO recuperarDatosVO(BigDecimal numExpediente);

	boolean actualizarDatosVO (EeffConsultaVO eeffConsultaVO);

	EeffConsultaVO obtenerRespuestaDesdeExpedienteLiberacion(BigDecimal numExpediente);

	ResultBean comprobarCreditosProceso(String idUsuario, int numCreditos);

	DetalleConsultaEEFFBean getDetalleConsulta(String numExpediente) throws FileNotFoundException, JAXBException, UnsupportedEncodingException;

}