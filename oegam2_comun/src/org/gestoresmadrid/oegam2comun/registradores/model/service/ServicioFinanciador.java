package org.gestoresmadrid.oegam2comun.registradores.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.oegam2comun.registradores.view.dto.TramiteRegRbmDto;
import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.IntervinienteRegistroDto;

import escrituras.beans.ResultBean;

public interface ServicioFinanciador extends Serializable{

	IntervinienteRegistroDto getRepresentante(String identificador);

	List<IntervinienteRegistroDto> getFinanciadores();

	IntervinienteRegistroDto getFinanciador(String identificador);

	ResultBean borrarFinanciador(String id);

	ResultBean borrarRepresentante(String id);

	IntervinienteRegistroDto getFinanciadorPorNifColegiado(IntervinienteRegistroDto financiadorNif);

	List<IntervinienteRegistroDto> getRepresentantes(long id);
	
	ResultBean validarInterviniente(IntervinienteRegistroDto intervinienteRegistro);

	List<IntervinienteRegistroDto> getFinanciadoresColegiado(String numColegiado);

	List<TramiteRegRbmDto> getTramites(long idInterviniente);

	ResultRegistro guardarFinanciador(IntervinienteRegistroDto financiador, BigDecimal idUsuario);
	

}
