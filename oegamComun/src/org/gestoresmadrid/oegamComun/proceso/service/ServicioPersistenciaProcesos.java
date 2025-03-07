package org.gestoresmadrid.oegamComun.proceso.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.proceso.model.vo.ProcesoVO;

public interface ServicioPersistenciaProcesos extends Serializable {

	ProcesoVO getProcesoPorProcesoYNodo(String proceso, String nodo);

	ProcesoVO getProceso(String proceso);

	List<ProcesoVO> getListaProcesos();

	List<ProcesoVO> getListaProcesosOrdenados(String nodo);

	BigDecimal getIntentosMaximos(String nombreProceso, String nodo);

	void actualizarProceso(ProcesoVO procesoVO);
}
