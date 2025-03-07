package org.gestoresmadrid.core.proceso.model.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.proceso.model.vo.ProcesoPK;
import org.gestoresmadrid.core.proceso.model.vo.ProcesoVO;

public interface ProcesoDao extends GenericDao<ProcesoVO> {

	/**
	 * Busca los procesos cuyo nodo corresponden al pasado por parámetro
	 * @param nodoProceso
	 * @return
	 */
	List<ProcesoVO> tomarProcesos(String nodo);

	/**
	 * Obtiene la lista de entidad Proceso para un nodo en concreto, si se pasa una lista de nombres como parámetro. Si no se pasa lista, devuelve todos
	 * @param nodo
	 * @param procesosPatron
	 * @return listaProcesos
	 */
	ArrayList<ProcesoVO> getProcesosPatron(String nodo, ArrayList<String> procesosPatron);

	/**
	 * Devuelve el numero máximos de intentos configurado para el proceso
	 * @param nombreProceso Nombre del proceso
	 * @param nodo Nombre del nodo
	 * @return Número máximo de intentos
	 */
	BigDecimal getIntentosMaximos(String nombreProceso, String nodo);

	ProcesoVO get(ProcesoPK procesoPk);

	ProcesoVO get(String nodo, String proceso);

	List<ProcesoVO> list(ProcesoVO filter, String... initialized);

	List<ProcesoVO> getProceso(String nodo,Long orden);

	List<ProcesoVO> getListaProcesosOrdenados(String nodo);

	ProcesoVO getProceso(String proceso, String nodo);

	ProcesoVO getProcesoVO(String proceso);

	ProcesoVO getProcesoPorProcesoYNodo(String proceso, String nodo);

	ProcesoVO getProcesoN(String proceso);

	List<ProcesoVO> getListaProcesos();
}
