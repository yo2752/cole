package org.gestoresmadrid.oegam2comun.tasas.model.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.tasas.model.vo.EvolucionTasaVO;
import org.gestoresmadrid.core.tasas.model.vo.TasaVO;
import org.gestoresmadrid.oegam2comun.evolucionTasa.view.dto.EvolucionTasaDto;

import escrituras.beans.ResultBean;

public interface ServicioEvolucionTasaNueva extends Serializable {

	String DESASIGNAR = "DESASIGNACION";
	String ASIGNAR = "ASIGNACION";
	String CREAR = "CREACION";

	public List<EvolucionTasaDto> convertirListaVoToDto(List<EvolucionTasaVO> list);

	ResultBean insertarEvolucionTasa(TasaVO tasa, String accion);

	void guardarEvolucion(Date fecha, Long idUsuario, String tipoActuacion);

	public ResultBean eliminarEvolucionTasa(String idstasaSeleccion);

}