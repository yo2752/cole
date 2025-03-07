package org.gestoresmadrid.oegam2comun.trafico.eeff.model.service;

import java.math.BigDecimal;

import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.core.trafico.eeff.model.vo.EeffLiberacionVO;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.dto.EeffLiberacionDTO;
import org.hibernate.HibernateException;

import utilidades.web.OegamExcepcion;
import escrituras.beans.ResultBean;

public interface ServicioEEFFLiberacion {
	public static final String NOMBRE_HOST = "nombreHostProceso";
	EeffLiberacionDTO recuperarDatos(BigDecimal numExpediente);

	boolean actualizarDatos(EeffLiberacionVO eeffVO);

	/**
	 * Método para importar datos de liberación del trámite para MatW
	 * @param ga
	 * @param numExp
	 * @return
	 * @throws HibernateException
	 */
	ResultBean guardarDatosImportados(trafico.beans.jaxb.matw.FORMATOGA.MATRICULACION faMa, BigDecimal numExp);

	/**
	 * 
	 * @param eeffLiberacionDTO
	 * @param numExpediente
	 * @param numColegiado
	 * @throws Exception
	 */
	boolean guardarDatos(EeffLiberacionDTO eeffLiberacionDTO, BigDecimal numExpediente, String numColegiado);

	/**
	 * 
	 * @param numExpediente
	 * @param idUsuario 
	 * @param eeffLiberacionDTO
	 * @return
	 * @throws Exception
	 */
	ResultBean liberar(BigDecimal numExpediente, BigDecimal idUsuario) throws OegamExcepcion;

	/**
	 * Metodo para validación de impotar y exportar.
	 * @param ga
	 * @return
	 */
	ResultBean validarEeffLibMatwFORMATOGA(trafico.beans.jaxb.matw.FORMATOGA.MATRICULACION faMa);

	boolean duplicar(BigDecimal numExpedientePrevio, BigDecimal numExpedienteNuevo);

	/**
	 * Comprueba si un tramite esta liberado.
	 * @param numExpediente
	 * @return
	 */
	boolean esRealizado(BigDecimal numExpediente);

	StringBuffer getFicheroXml(ColaBean solicitud);

	EeffLiberacionVO liberacionProceso(EeffLiberacionVO eeffLiberacionVO, String estadoBastidor, String numRegistroEntrada, String numRegistroSalida);

	ResultBean comprobarCreditos(int numCreditos);

	ResultBean liberaProceso(ColaBean solicitud);

	EeffLiberacionDTO actualizarDatosDTO(EeffLiberacionDTO eeffDTO);

	EeffLiberacionVO recuperarDatosVO (BigDecimal numExpediente);

	ResultBean comprobarCreditosProceso(String idUsuario, int numCreditos);

}