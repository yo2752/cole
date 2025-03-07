package org.gestoresmadrid.oegamSanciones.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.sancion.model.enumerados.EstadoSancion;
import org.gestoresmadrid.core.sancion.model.enumerados.Motivo;
import org.gestoresmadrid.oegamSanciones.service.ServicioSanciones;
import org.gestoresmadrid.oegamSanciones.service.ServicioValidacionSanciones;
import org.gestoresmadrid.oegamSanciones.view.beans.ResultadoSancionesBean;
import org.gestoresmadrid.oegamSanciones.view.dto.SancionDto;
import org.gestoresmadrid.oegamSanciones.view.dto.SancionListadosMotivosDto;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.validaciones.NIFValidator;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioValidacionSancionesImpl implements ServicioValidacionSanciones {

	private static final long serialVersionUID = -7613990972612467408L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioValidacionSancionesImpl.class);

	@Autowired
	ServicioSanciones servicioSanciones;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	public ResultadoSancionesBean validacionImprimirListado(String[] codSeleccionados) {
		ResultadoSancionesBean resultado = new ResultadoSancionesBean(false);
		Fecha compararFecha = null;
		String compararNumColegiado = null;
		SancionDto sancionDto;
		SancionListadosMotivosDto sancionListadosMotivosDto = new SancionListadosMotivosDto();

		// Busqueda por ids
		try {
			for (String codigo : codSeleccionados) {
				sancionDto = servicioSanciones.getSancionPorId(Integer.parseInt(codigo));
				if (sancionDto == null) {
					resultado.setError(true);
					resultado.setMensaje("No existe la sanción.");
					break;
				}

				if (sancionDto.getFechaPresentacion().getDate().before(utilesFecha.getFechaActual().getDate())) {
					resultado.setError(true);
					resultado.setMensaje("No puede imprimir un listado de días anteriores al de hoy.");
				}

				if (compararFecha == null) {
					compararFecha = sancionDto.getFechaPresentacion();
					resultado.setFechaPresentacion(compararFecha);
				} else if (!sancionDto.getFechaPresentacion().getFecha().equals(compararFecha.getFecha())) {
					resultado.setError(true);
					resultado.setMensaje("Todos los trámites seleccionados deben tener la misma fecha de presentación.");
				}

				if (compararNumColegiado == null) {
					compararNumColegiado = sancionDto.getNumColegiado();
				} else if (!sancionDto.getNumColegiado().equals(compararNumColegiado)) {
					resultado.setError(true);
					resultado.setMensaje("Todos los trámites seleccionados deben ser del mismo colegiado.");
				}

				if (resultado.getError()) {
					break;
				}

				if (StringUtils.isBlank(sancionDto.getNombre())) {
					sancionDto.setNombre(" ");
				}

				if (Motivo.ALEGACION.getValorEnum().equals(sancionDto.getMotivo())) {
					sancionListadosMotivosDto.getListaSancionMotivoAle().add(sancionDto);
				} else if (Motivo.RECURSO.getValorEnum().equals(sancionDto.getMotivo())) {
					sancionListadosMotivosDto.getListaSancionMotivoRec().add(sancionDto);
				}
			}

		} catch (Exception e) {
			resultado.setMensaje("Se ha producido un error no esperado al intentar validar las sanciones seleccionadas.");
			log.error("Se ha producido un error no esperado al intentar validar las sanciones seleccionadas", e);
		}

		if (!resultado.getError()) {
			resultado.setSancionListadosMotivosDto(sancionListadosMotivosDto);
		}

		return resultado;
	}

	@Override
	public ResultadoSancionesBean validacionGuardarSancion(SancionDto sancionDto) {
		ResultadoSancionesBean resultado = new ResultadoSancionesBean(false);

		if (sancionDto.getEstadoSancion() != null && EstadoSancion.FINALIZADO.getValorEnum().equals(sancionDto.getEstadoSancion().toString())) {
			resultado.setError(true);
			resultado.setMensaje("No se puede modificar una sanción en estado FINALIZADA.");
			return resultado;
		}

		if (sancionDto.getFechaPresentacion().isfechaNula()) {
			resultado.setError(true);
			resultado.setMensaje("Se debe introducir una fecha completa.");
			return resultado;
		}

		Fecha hoy = utilesFecha.getFechaActual();
		try {
			if (!utilesFecha.esFechaLaborableConsiderandoFestivos(sancionDto.getFechaPresentacion()) || utilesFecha.compararFechaMayor(hoy, sancionDto.getFechaPresentacion()) == 1) {
				resultado.setError(true);
				resultado.setMensaje("Debe seleccionar una Fecha de Presentación laborable igual o posterior a la fecha actual.");
				return resultado;
			}
		} catch (ParseException | OegamExcepcion e) {
			log.error("Error al parsear la fecha", e);
			resultado.setError(true);
			resultado.setMensaje("Debe seleccionar una Fecha de Presentación laborable igual o posterior a la fecha actual.");
			return resultado;
		}

		if (StringUtils.isBlank(sancionDto.getApellidos())) {
			resultado.setError(true);
			resultado.setMensaje("No se ha rellenado los apellidos o razón social.");
			return resultado;
		}

		if (StringUtils.isBlank(sancionDto.getDni())) {
			resultado.setError(true);
			resultado.setMensaje("No se ha rellenado el DNI.");
			return resultado;
		} else {
			BigDecimal resultValida = NIFValidator.validarNif(sancionDto.getDni());
			if (resultValida == null || resultValida.intValue() <= 0) {
				resultado.setError(true);
				resultado.setMensaje("DNI no valido.");
				return resultado;
			}
		}

		if (sancionDto.getMotivo() == null) {
			resultado.setError(true);
			resultado.setMensaje("Se debe de elegir un motivo.");
		}

		return resultado;
	}

}
