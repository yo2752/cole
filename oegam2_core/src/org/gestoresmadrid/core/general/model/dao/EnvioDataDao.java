package org.gestoresmadrid.core.general.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.general.model.vo.EnvioDataVO;
import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface EnvioDataDao extends GenericDao<EnvioDataVO>, Serializable {

	/**
	 * Inserta un registro del proceso de la última ejecución del tipo especificado en el parámetro
	 * @param colaBean
	 * @param resultadoEjecucion
	 * @param excepcion
	 */
	void actualizarUltimaEjecucion(ColaBean colaBean, String resultadoEjecucion, String excepcion);

	void actualizarUltimaEjecucionNuevo(String proceso, String cola, BigDecimal idTramite, String respuesta, String resultadoEjecucion, String excepcion);

	/**
	 * Inserta un registro del proceso de la última ejecución del tipo especificado en el parámetro
	 * @param proceso
	 * @param respuesta
	 * @param resultadoEjecucion
	 */
	void actualizarEjecucion(String proceso, String respuesta, String resultadoEjecucion, String numCola);

	/**
	 * busca si se ha realizado una peticion correcta en el dia solicitado
	 * @param proceso
	 * @param tipoEjecucion
	 * @param fechaInicial
	 * @param fecha
	 * @return
	 */
	List<EnvioDataVO> getExisteEnvioData(String proceso, String tipoEjecucion, Date fechaInicial, Date fechaFinal);

	List<EnvioDataVO> getExisteEnvioDataEnvioStock(String proceso, String tipoEjecucion, Date fechaInicial, Date fechaFinal);

	/**
	 * busca el ultimo envio data para ese proceso y ejecucion
	 * @param proceso
	 * @param tipoEjecucion
	 * @return
	 */
	List<EnvioDataVO> getUltimoEnvioData(String proceso, String tipoEjecucion);

	EnvioDataVO getEnvioDataProcesoResultadoEjecucion(String proceso, String resultadoEjecucion, String numCola);

	Date getUltimoEnvioDataProcesoEjecucion(String proceso, String tipoEjecucion, String hiloCola);

	EnvioDataVO getEnvioDataPorHoras(String proceso, String tipoEjecucion, String hiloCola, Date fechaInicial, Date fechaFinal);

	EnvioDataVO getListaEnvioDataPorProcesoYTipoEjecucion(String proceso, String ejecucionExcepcion);

	List<EnvioDataVO> getListaEnvioDataPorProcesoYTipoEjecucionPorCola(String proceso, String ejecucionExcepcion);

	List<EnvioDataVO> getListaUltimoEnvioPorEjecucion(String proceso, String cola, String ejecucionCorrecta, String orden, String dir);

	List<EnvioDataVO> recuperarUltimasEjecucionesProceso(String proceso);

	EnvioDataVO getEnvioDataPorProcesoYColaYTipoEjecucion(String proceso, String cola, String ejecucionCorrecta);

	List<EnvioDataVO> listar(String tipoEjecucion);

	List<EnvioDataVO> recuperarUltimasOk(String proceso, String cola);
}
