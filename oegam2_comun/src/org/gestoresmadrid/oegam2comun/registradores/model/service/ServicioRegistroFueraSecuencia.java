package org.gestoresmadrid.oegam2comun.registradores.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.oegam2comun.registradores.view.dto.RegistroFueraSecuenciaDto;

import escrituras.beans.ResultBean;

public interface ServicioRegistroFueraSecuencia extends Serializable {

	List<RegistroFueraSecuenciaDto> getRegistrosFueraSecuencia(BigDecimal idTramiteRegistro);

	RegistroFueraSecuenciaDto getRegistroFueraSecuencia(Long idRegistroFueraSecuencia);

	ResultBean guardarRegistroFueraSecuencia(RegistroFueraSecuenciaDto registroFueraSecuencia, BigDecimal idUsuario);
}
