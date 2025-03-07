package org.gestoresmadrid.oegam2comun.registradores.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.oegam2comun.registradores.view.dto.RegistroDto;

public interface ServicioRegistro extends Serializable {

	RegistroDto getRegistroPorId(long idRegistro);

	List<RegistroDto> getRegistro(String idProvincia, String tipo);

	Long getIdRegistro(String idRegistro, String idProvincia, String tipo);

	RegistroDto getRegistroPorNombre(String nombre, String tipo);
}
