package org.gestoresmadrid.oegam2comun.consultaDev.model.service;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.core.consultaDev.model.enumerados.EstadoConsultaDev;
import org.gestoresmadrid.oegam2comun.cola.view.dto.ColaDto;
import org.gestoresmadrid.oegam2comun.consultaDev.model.bean.ResultadoWSConsultaDev;

public interface ServicioWebServiceConsultaDev extends Serializable{

	public final String estadoSuscripcion = "estadoSuscripcion";

	ResultadoWSConsultaDev generarConsultaDev(ColaDto colaDto);

	void cambiarEstadoConsulta(BigDecimal idConsultaDev, BigDecimal idUsuario, EstadoConsultaDev estado);

	void devolverCreditos(BigDecimal idConsultaDev, BigDecimal idContrato, BigDecimal idUsuario);

}
