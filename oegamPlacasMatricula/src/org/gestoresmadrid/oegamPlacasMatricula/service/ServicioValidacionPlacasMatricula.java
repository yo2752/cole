package org.gestoresmadrid.oegamPlacasMatricula.service;

import java.io.Serializable;
import java.util.ArrayList;

import org.gestoresmadrid.oegam2comun.trafico.solicitudesplacas.enumerados.EstadoSolicitudPlacasEnum;
import org.gestoresmadrid.oegamPlacasMatricula.view.bean.ValidacionPlacasBean;
import org.gestoresmadrid.oegamPlacasMatricula.view.dto.SolicitudPlacaDto;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public interface ServicioValidacionPlacasMatricula extends Serializable {
	ILoggerOegam log = LoggerOegam.getLogger(ServicioValidacionPlacasMatricula.class);
	
	public ArrayList<ValidacionPlacasBean> validarSolicitudes(ArrayList<SolicitudPlacaDto> listaSolicitudes, EstadoSolicitudPlacasEnum estadoPrevio);
	public ValidacionPlacasBean validarSolicitudesModificar(String[] estados);
	public ValidacionPlacasBean validarSolicitudesBorrar(String[] estados);
	public ValidacionPlacasBean validarRealizacion(ArrayList<SolicitudPlacaDto> listaSolicitudes);
	public ValidacionPlacasBean validarConfirmacion(ArrayList<SolicitudPlacaDto> listaSolicitudes);
	public ValidacionPlacasBean validarFinalizacion(ArrayList<SolicitudPlacaDto> listaSolicitudes);
}
