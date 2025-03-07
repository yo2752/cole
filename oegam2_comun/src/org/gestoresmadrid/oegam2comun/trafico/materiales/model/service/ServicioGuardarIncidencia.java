package org.gestoresmadrid.oegam2comun.trafico.materiales.model.service;

import java.io.Serializable;

import org.gestoresmadrid.core.trafico.materiales.model.vo.IncidenciaVO;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto.IncidenciaDto;

import escrituras.beans.ResultBean;

public interface ServicioGuardarIncidencia extends Serializable {
	static final String ACCION_CREATE    = "ALTA_INCIDENCIA";
	static final String ACCION_MODIFY    = "MODIFICA_INCIDENCIA";
	static final String ACCION_CONFIRMAR = "CONFIRMA_INCIDENCIA";
	
	ResultBean salvarIncidencia(IncidenciaDto incidenciaDto, String accion);
	ResultBean modificarIncidencia(IncidenciaDto incidenciaDto, String accion);
	
	ResultBean salvarIncidencia(IncidenciaDto incidenciaDto);
	ResultBean salvarIncidencia(IncidenciaVO incidenciaVO);
	ResultBean actualizarIncidenciaWithIdInventario(Long incidenciaId, Long inventarioId);
	
}
