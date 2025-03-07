package org.gestoresmadrid.oegam2comun.cola.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.cola.model.vo.ColaVO;

import escrituras.beans.ResultBean;
import utilidades.estructuras.FechaFraccionada;
import utilidades.web.OegamExcepcion;

public interface ServicioCola extends Serializable {

	ColaVO getCola(Long idEnvio);

	ColaVO getColaIdTramite(BigDecimal idTramite, String proceso);

	void solicitudErronea(Long idEnvio);

	void solicitudCorrecta(Long idEnvio, String resultado, String proceso, String nodo);

	void solicitudErroneaImprimirTramites(BigDecimal idEnvio);

	void errorServicio(Long idEnvio, String respuesta);

	void eliminar(BigDecimal idTramite, String proceso);

	ColaVO nuevaCola(String proceso, String nodo, String tipoTramite, String idTramite, BigDecimal idUsuario, String xmlEnviar, String respuesta, BigDecimal idContrato) throws OegamExcepcion;

	ColaVO generarCola(ColaVO colaVO);

	String getHiloCola(ColaVO colaVO) throws OegamExcepcion;

	ResultBean crearSolicitud(String proceso, String xmlEnviar, String nodo, String tipoTramite, String idTramite, BigDecimal idUsuario, String respuesta, BigDecimal idContrato) throws OegamExcepcion;

	ColaVO tomarSolicitud(String proceso, String cola, String nodo);

	boolean comprobarEstados(ColaVO colaVO, String proceso);

	boolean establecerMaxPrioridad(Long idEnvio);

	List<Object[]> solicitudesAgrupadasPorHilos(String proceso);

	List<Object[]> solicitudesAgrupadasPorHilos();

	Boolean comprobarColasProcesoActivas(String proceso, BigDecimal idContrato, String xmlEnviar);

	Integer obtenerMaxCola();

	List<ColaVO> getSolicitudesActivasProceso(String proceso, String nodo);

	void actualizar(ColaVO cola);

	ColaVO getColaPrincipal(String proceso, String cola, String nodo);

	void borrar(ColaVO cola);

	List<ColaVO> getSolicitudesColaMonitorizacion(String proceso, String estado, FechaFraccionada fecha);
}
