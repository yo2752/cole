package org.gestoresmadrid.oegam2comun.registradores.model.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.EntidadCancelacionDto;

public interface ServicioEntidadCancelacion extends Serializable{

	public ResultRegistro guardarOActualizarEntidadCancelacion(EntidadCancelacionDto entidadCancelacionDto);
	public ResultRegistro validarEntidadCancelacion(EntidadCancelacionDto entidadCancelacionDto);
	public EntidadCancelacionDto buscarPorContratoNif(BigDecimal idContrato, String cifEntidad);

}
