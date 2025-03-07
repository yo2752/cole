package org.gestoresmadrid.oegam2comun.trafico.materiales.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.trafico.materiales.model.vo.IncidenciaVO;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.IncidenciasResultadosBean;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.rest.IncidenciaInfoRest;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto.IncidenciaDto;

public interface ServicioConsultaIncidencias extends Serializable {
	
	
	List<IncidenciasResultadosBean> convertirListaEnBeanPantalla(List<IncidenciaVO> lista);
	IncidenciaDto getIncidencia(Long incidenciaId);
	IncidenciaVO  obtenerIncidenciaVO(Long incidenciaId);
	IncidenciaVO obtenerIncidenciaForJefaturaMaterialNumSerie(String jefatura, Long materia, String numSerie);
	
	IncidenciaVO getVOFromDto(IncidenciaDto incidenciaDto);
	IncidenciaDto getDtoFromVO(IncidenciaVO incidenciaVO);
	IncidenciaDto getDtoFromInfoRest(IncidenciaInfoRest incidenciaInfoRest);
	
	IncidenciaVO getVOFromInfoRest(IncidenciaInfoRest incidenciaInfoRest);
	IncidenciaDto obtenerIncidencia(Long incidenciaId);
}
