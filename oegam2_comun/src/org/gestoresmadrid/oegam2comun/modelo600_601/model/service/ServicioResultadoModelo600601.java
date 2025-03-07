package org.gestoresmadrid.oegam2comun.modelo600_601.model.service;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.gestoresmadrid.core.modelo600_601.model.vo.Modelo600_601VO;
import org.gestoresmadrid.core.modelo600_601.model.vo.ResultadoModelo600601VO;
import org.gestoresmadrid.oegam2comun.modelo600_601.view.dto.Modelo600_601Dto;
import org.gestoresmadrid.oegam2comun.modelo600_601.view.dto.ResultadoModelo600601Dto;
import org.gestoresmadrid.oegam2comun.modelo600_601.view.dto.ResultadoModelos;

import escrituras.beans.ResultBean;

public interface ServicioResultadoModelo600601 extends Serializable{
	
	public static String ID_MENSAJES_PROCESOS = "ESC";

	ResultBean guardarResultadoSubSanable(Modelo600_601VO modelo600601VO, Date fechaEjecucion, String respuesta);

	ResultBean guardarResultadoOk(Modelo600_601VO modelo600601VO,	ResultadoModelos resultLlamadaWS);

	String getTextoCodigoResultado(String codigo);

	ResultBean convertirListaResultadoVoToDto(Modelo600_601VO modeloVO,	Modelo600_601Dto modeloDto);

	ResultadoModelo600601Dto getResultadoModelo600601DtoPorId(Integer idResultado);

	ResultBean eliminarResuladosModelos(Set<ResultadoModelo600601VO> listaModelo, Long idModelo);

}
