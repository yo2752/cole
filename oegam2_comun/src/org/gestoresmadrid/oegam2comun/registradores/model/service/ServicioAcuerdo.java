package org.gestoresmadrid.oegam2comun.registradores.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.registradores.model.vo.AcuerdoVO;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.AcuerdoDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.TramiteRegistroDto;

import escrituras.beans.ResultBean;

public interface ServicioAcuerdo extends Serializable {
	
	public static String ACUERDO = "acuerdo"; 

	AcuerdoVO getAcuerdo(Long idAcuerdo);

	AcuerdoDto getAcuerdoDto(Long idAcuerdo);

	List<AcuerdoDto> getAcuerdos(BigDecimal idTramiteRegistro);

	ResultBean guardarAcuerdo(AcuerdoDto acuerdoDto, TramiteRegistroDto tramiteRegistro, BigDecimal idUsuario);

	void eliminarAcuerdo(Long idAcuerdo);
	
}
