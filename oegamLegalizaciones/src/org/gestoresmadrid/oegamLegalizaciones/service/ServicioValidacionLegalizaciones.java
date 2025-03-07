package org.gestoresmadrid.oegamLegalizaciones.service;

import java.io.Serializable;

import org.gestoresmadrid.core.legalizacion.model.vo.LegalizacionCitaVO;
import org.gestoresmadrid.oegamLegalizaciones.view.beans.ResultadoLegalizacionesBean;
import org.gestoresmadrid.oegamLegalizaciones.view.dto.LegalizacionCitaDto;

import utilidades.estructuras.Fecha;

public interface ServicioValidacionLegalizaciones extends Serializable {
	public ResultadoLegalizacionesBean validarGuardar(LegalizacionCitaDto legDto, boolean esAdmin);

	public ResultadoLegalizacionesBean permiteBorrar(LegalizacionCitaVO legalizacionCita, boolean esAdmin);

	public ResultadoLegalizacionesBean esPosibleSolicitud(LegalizacionCitaVO legalizacionCita);

	public ResultadoLegalizacionesBean fechaLegalizacionValida(Fecha fechaLegalizacion, Fecha fechaLimitePresentacionEnColegio, boolean isAdmin);

	public ResultadoLegalizacionesBean permiteSolicitarLegalizacion(Fecha fechaLegalizacion, boolean esAdmin);

}
