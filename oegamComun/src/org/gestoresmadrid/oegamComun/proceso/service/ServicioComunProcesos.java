package org.gestoresmadrid.oegamComun.proceso.service;

import java.io.Serializable;

import org.gestoresmadrid.core.proceso.model.vo.ProcesoVO;

public interface ServicioComunProcesos extends Serializable {

	ProcesoVO getProcesoPorProcesoYNodo(String proceso, String nodo);

	ProcesoVO getProceso(String proceso);
}
