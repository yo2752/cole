package org.gestoresmadrid.oegam2comun.registradores.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.MotorBuqueDto;

public interface ServicioMotorBuque extends Serializable{

	public ResultRegistro getMotorBuque(String id);
	public ResultRegistro guardarOActualizarMotorBuque(MotorBuqueDto motorBuqueDto, long idBuque);
	public ResultRegistro borrarMotorBuque(String id);
	public List<MotorBuqueDto> getMotoresPorBuque(String idBuque);
	public ResultRegistro validarMotorBuque(MotorBuqueDto motorBuqueDto);

}
