package org.gestoresmadrid.core.cola.model.dao;

import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.cola.model.vo.ColaVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

import utilidades.estructuras.FechaFraccionada;

public interface ColaDao extends GenericDao<ColaVO> {

	ColaVO getCola(Long idEnvio);

	ColaVO getColaPorIdTramite(BigDecimal idTramite, String proceso);

	List<ColaVO> getHiloNuevo(ColaVO colaVO);

	String getHilo(ColaVO colaVO);

	List<ColaVO> getListaColaTramites(ColaVO colaVO);

	List<ColaVO> getListaColaTramitesHoy();

	List<Object[]> solicitudesAgrupadasPorHilos(String proceso);

	List<Object[]> solicitudesAgrupadasPorHilos();

	ColaVO getColaSolicitudProceso(String proceso, String cola, String nodo);

	List<ColaVO> getSolicitudesCola(String numExpediente, String proceso, String tipoTramite, String estado, String cola, String matricula, String bastidor, FechaFraccionada fecha, String nodo);

	Integer recuperarNumPendientesPorProceso(String proceso, String[] numHost, FechaFraccionada fechaFracionadaActual);

	List<ColaVO> getColasActivasProceso(String proceso, BigDecimal idContrato, String xmlEnviar);

	List<ColaVO> getColasActivasProceso(String proceso, String nodo);

	String obtenerMaxCola();

	int getNumColasProceso(String proceso, String nodo, Long idContrato);

	ColaVO getColaPrincipal(String proceso, String cola, String nodo);

	List<ColaVO> getColasCreadasPorProceso(String proceso, String nodo);

	List<ColaVO> getColasCreadasPorProcesoIdTramite(String proceso, String nodo, BigDecimal idTramite);

	List<ColaVO> existeColaTramiteProceso(BigDecimal idTramite, String proceso);

	String getHilo(String nodo, String proceso);

	List<ColaVO> getSolicitudesColaMonitorizacion(String proceso, String estado, FechaFraccionada fecha,
			String hostPeticiones1, String hostPeticiones2);

	List<Object[]> consultaCreditosBloqueadosPorContrato(BigDecimal idContrato);
}
