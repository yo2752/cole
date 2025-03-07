package org.gestoresmadrid.oegamLegalizaciones.service.impl;

import java.sql.Timestamp;
import java.text.ParseException;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.legalizacion.constantes.ConstantesLegalizacion;
import org.gestoresmadrid.core.legalizacion.model.vo.LegalizacionCitaVO;
import org.gestoresmadrid.oegamLegalizaciones.service.ServicioPersistenciaLegalizaciones;
import org.gestoresmadrid.oegamLegalizaciones.service.ServicioValidacionLegalizaciones;
import org.gestoresmadrid.oegamLegalizaciones.view.beans.ResultadoLegalizacionesBean;
import org.gestoresmadrid.oegamLegalizaciones.view.dto.LegalizacionCitaDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioValidacionLegalizacionesImpl implements ServicioValidacionLegalizaciones {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7243182283480112711L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioValidacionLegalizacionesImpl.class);

	@Autowired
	private UtilesFecha utilesFecha;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	private ServicioPersistenciaLegalizaciones servicioPersistenciaLegalizaciones;

	@Override
	public ResultadoLegalizacionesBean validarGuardar(LegalizacionCitaDto legDto, boolean esAdmin) {
		ResultadoLegalizacionesBean resultado = new ResultadoLegalizacionesBean();
		resultado.setError(false);

		if (legDto.getFechaDocumentacion().isfechaNula()) {
			resultado.setError(true);
			resultado.addMensajeAListaError("Se debe introducir una fecha de documentación completa.");
		}
		if (StringUtils.isBlank(legDto.getNombre())) {
			resultado.setError(true);
			resultado.addMensajeAListaError("No se ha rellenado el nombre.");
		}
		if (StringUtils.isBlank(legDto.getTipoDocumento())) {
			resultado.setError(true);
			resultado.addMensajeAListaError("Se debe elegir un tipo de documento.");
		}
		if (StringUtils.isBlank(legDto.getClaseDocumento())) {
			resultado.setError(true);
			resultado.addMensajeAListaError("Se debe elegir la clase documento.");
		}
		if (esAdmin) {
			if (StringUtils.isBlank(legDto.getPais())) {
				resultado.setError(true);
				resultado.addMensajeAListaError("No se ha rellenado el país.");
			}
		} else {
			ResultadoLegalizacionesBean result;
			if (legDto.getIdPeticion() == null) {
				result = permiteGuardarPeticion(legDto, esAdmin);
			} else {
				result = permiteModificarPeticion(legDto.getIdPeticion(), legDto.getFechaLegalizacion(), esAdmin);
			}
			if (result.getError()) {
				resultado.setError(Boolean.TRUE);
				resultado.addMensajeAListaError(result.getMensaje());

			}
		}

		return resultado;
	}

	private Integer horaFin() {
		return Integer.parseInt(gestorPropiedades.valorPropertie(ConstantesLegalizacion.HORA_FIN));
	}

	private Integer horaInicio() {
		return Integer.parseInt(gestorPropiedades.valorPropertie(ConstantesLegalizacion.HORA_INICIO));
	}

	private boolean fueradeHorario(boolean esAdmin) throws NumberFormatException, OegamExcepcion {
		boolean resultado = false;
		Fecha fecha = utilesFecha.getFechaHoraActualLEG();

		if (!esAdmin) {
			if (Integer.parseInt(fecha.getHora()) < horaInicio() || Integer.parseInt(fecha.getHora()) >= horaFin()) {
				resultado = true;
			}
		}
		return resultado;
	}

	@Override
	public ResultadoLegalizacionesBean permiteBorrar(LegalizacionCitaVO legalizacionCita, boolean esAdmin) {
		ResultadoLegalizacionesBean result = new ResultadoLegalizacionesBean();
		result.setError(false);

		// Si no tiene fecha de legalización se puede borrar siempre.
		try {
			int fechasComparadas = utilesFecha.compararFechaMayor(utilesFecha.getFechaHoraActual(), utilesFecha.getFechaConDate(legalizacionCita.getFechaLegalizacion()));

			if (fechasComparadas == 0) {
				result.setError(true);
				result.setMensaje("No se puede eliminar la petición con referencia " + legalizacionCita.getReferencia() + ". Hoy será entregada al MAEC.");
			}

			if (fechasComparadas == 1) {
				result.setError(true);
				result.setMensaje("No se puede eliminar la petición con referencia " + legalizacionCita.getReferencia() + ". La fecha de legalización ya ha pasado.");
			}

			if (fechasComparadas == 2) {
				// La fecha de legalización es mayor que la actual, pero si es el día siguiente al actual y son más de la hora limite y no se puede borrar.
				// La hora limite normalmente son las 16h pero en agosto muchas veces son las 14h
				int fechasCompadasAux = utilesFecha.compararFechaMayor(utilesFecha.getPrimerLaborablePosterior(utilesFecha.getFechaHoraActual()), utilesFecha.getFechaConDate(legalizacionCita
						.getFechaLegalizacion()));
				if (fechasCompadasAux == 0) {
					if (fueradeHorario(esAdmin)) {
						result.setError(true);
						result.setMensaje("No se puede eliminar la petición con referencia " + legalizacionCita.getReferencia() + ". La fecha es el próximo día laborable y " + "son más de las"
								+ horaFin() + "h.");
					}
				}
			}

		} catch (Throwable e) {
			log.error("Se ha producido un error al intentar validar para borrar la legalización", e);
			result.setError(true);
			result.setMensaje("Se ha producido un error al intentar validar para borrar la legalización");
		}

		return result;
	}

	@Override
	public ResultadoLegalizacionesBean esPosibleSolicitud(LegalizacionCitaVO legalizacionCita) {
		ResultadoLegalizacionesBean resultado = new ResultadoLegalizacionesBean();
		resultado.setError(false);

		try {
			// Se tiene que comprobar que no han pasado 7 días desde que se entregó la documentación.
			if (legalizacionCita.getFechaLegalizacion() != null) {
				Timestamp fecha7Dias = utilesFecha.sumaDiasAFecha(utilesFecha.getFechaConDate(legalizacionCita.getFechaLegalizacion()), "7");
				int fechasComparadas = utilesFecha.compararFechaMayor(utilesFecha.getFechaHoraActual(), utilesFecha.getFechaFracionada(fecha7Dias));

				if (fechasComparadas == 1) {
					resultado.setError(true);
				}
			}

		} catch (ParseException e) {
			log.error("Se ha producido un error al obtener los datos de la petición: " + legalizacionCita.getIdPeticion() + " al comprobar si es posible hacer"
					+ "una solicitud y no ha pasado más de 1 semana", e);
			resultado.setError(true);
			resultado.setMensaje("Se ha producido un error al obtener los datos de la petición: " + legalizacionCita.getIdPeticion() + " al comprobar si es posible hacer"
					+ "una solicitud y no ha pasado más de 1 semana");
		}
		return resultado;
	}

	@Override
	public ResultadoLegalizacionesBean fechaLegalizacionValida(Fecha fechaLegalizacion, Fecha fechaLimitePresentacionEnColegio, boolean esAdmin) {
		ResultadoLegalizacionesBean result = new ResultadoLegalizacionesBean();
		result.setError(false);

		try {
			int resultadoComparacion = utilesFecha.compararFechaMayor(utilesFecha.getFechaActual(), fechaLimitePresentacionEnColegio);

			if (resultadoComparacion == 0) {
				if (fueradeHorario(esAdmin)) {
					result.setError(true);
					result.setMensaje("No se puede modificar o guardar la petición porque ya han pasado las" + horaFin() + "h del día anterior a legalización.");
					return result;
				}
			}

			if (resultadoComparacion == 1) {
				result.setError(true);
				result.setMensaje("No se puede modificar o guardar la petición porque ya han pasado las " + horaFin() + "h del día laborable anterior a la fecha de legalización introducida.");
				return result;
			}

			if (!utilesFecha.esFechaLaborableConsiderandoFestivos(fechaLegalizacion)) {
				result.setError(true);
				result.setMensaje("La fecha de Legalización tiene que ser un día laborable.");
				return result;
			}
		} catch (OegamExcepcion | Exception e) {
			log.error("Se ha producido un error al intentar validar la Fecha de Legalización.", e);
			result.setError(true);
			result.setMensaje("Se ha producido un error al intentar validar la Fecha de Legalización.");
		}

		return result;
	}

	/**
	 * Método que comprueba la fecha de legalización para ver si es válida. 1- No puede ser menor o igual a la fecha actual 2- No puede ser en fin de semana o festivo nacional 3- Si es después de las hora límite / fin no puede ser mañana Esta hora normalmente son las 16h aunque en agosto pueden ser
	 * las 14h
	 */
	@Override
	public ResultadoLegalizacionesBean permiteSolicitarLegalizacion(Fecha fechaLegalizacion, boolean esAdmin) {
		ResultadoLegalizacionesBean result = new ResultadoLegalizacionesBean();
		result.setError(false);

		try {
			if (!utilesFecha.esFechaLaborableConsiderandoFestivos(fechaLegalizacion)) {
				result.setError(true);
				result.setMensaje("La fecha de legalización tiene que ser un día laborable.");
				return result;
			}

			int resultadoComparacion = utilesFecha.compararFechaMayor(utilesFecha.getFechaActual(), fechaLegalizacion);

			if (resultadoComparacion == 1 || resultadoComparacion == 0) {
				result.setError(true);
				result.setMensaje("La fecha de legalización tiene que ser mayor al día actual.");
				return result;
			}

			if (resultadoComparacion == 2) {
				int fechasCompadasAux = utilesFecha.compararFechaMayor(utilesFecha.getPrimerLaborablePosterior(utilesFecha.getFechaHoraActual()), fechaLegalizacion);

				if (fechasCompadasAux == 0 && fueradeHorario(esAdmin)) {
					result.setError(true);
					result.setMensaje("Han pasado las " + horaFin() + "h. " + "La fecha de legalización tiene que ser mayor que el siguiente dia laborable.");
					return result;
				}
			}

		} catch (OegamExcepcion | Exception e) {
			log.error("Se ha producido un error al intentar comprobar si se puede solicitar legalización ", e);
			result.setError(true);
			result.setMensaje("Se ha producido un error al intentar comprobar si se puede solicitar legalización.");
		}

		return result;
	}

	private ResultadoLegalizacionesBean peticionModificable(LegalizacionCitaVO legalizacion) throws Exception {
		ResultadoLegalizacionesBean result = new ResultadoLegalizacionesBean();
		result.setError(false);

		try {
			if (legalizacion.getFechaLegalizacion() != null) {
				int resultadoComparacion = utilesFecha.compararFechaMayor(utilesFecha.getFechaActual(), utilesFecha.getFechaConDate(legalizacion.getFechaLegalizacion()));

				if (resultadoComparacion == 0 || resultadoComparacion == 1) {
					result.setError(true);
					result.setMensaje("La petición con nombre: " + legalizacion.getNombre() + " ha sido entregada en el Ministerio y no puede modificarse.");
				}
			}
		} catch (ParseException e) {
			throw new Exception(e);
		}

		return result;
	}

	/**
	 * Método que comprueba la fecha de legalización que se quiere asignar a una petición 1- Después de la hora fin /limite del día anterior a la fecha de legalización no se puede modificar Dicha hora suelen ser las 16h, aunque en agosto puede que sean las 14h 2- Si la fecha de legalización es menor
	 * al día actual no se permite modificar 3- Si la fecha de legalización a insertar no es laborable no es válida
	 */

	private ResultadoLegalizacionesBean permiteModificarPeticion(int idPeticion, Fecha fechaLegalizacion, boolean isAdmin) {
		ResultadoLegalizacionesBean result = new ResultadoLegalizacionesBean();
		result.setError(false);

		try {
			LegalizacionCitaVO legalizacion = servicioPersistenciaLegalizaciones.obtenerLegalizacionPorId(idPeticion);
			// Comprueba si la petición es modificable
			result = peticionModificable(legalizacion);
			if (result.getError()) {
				return result;
			}

			Fecha fechaLimitePresentacionEnColegio = utilesFecha.getPrimerLaborableAnterior(fechaLegalizacion);
			// Comprueba si la fecha de legalización a introducir es válida
			result = fechaLegalizacionValida(fechaLegalizacion, fechaLimitePresentacionEnColegio, isAdmin);
			if (result.getError()) {
				return result;
			}
		} catch (Throwable e) {
			log.error("Se ha producido un error al intentar comprobar si se puede modificar la legalización ", e);
			result.setError(true);
			result.setMensaje("Se ha producido un error al intentar comprobar si se puede modificar la legalización.");
		}

		return result;
	}

	/**
	 * Método que comprueba si la fecha de legalización es válida para dar de alta una petición.
	 */
	private ResultadoLegalizacionesBean permiteGuardarPeticion(LegalizacionCitaDto legalizacionCitaDto, boolean isAdmin) {
		ResultadoLegalizacionesBean result = new ResultadoLegalizacionesBean();

		if (!legalizacionCitaDto.getFechaLegalizacion().isfechaNula()) {

			Fecha fechaLimitePresentacionEnColegio = new Fecha();
			try {
				fechaLimitePresentacionEnColegio = utilesFecha.getPrimerLaborableAnterior(legalizacionCitaDto.getFechaLegalizacion());
			} catch (ParseException | OegamExcepcion e) {
				log.error("Se ha producido un error al intentar comprobar si se puede guardar la legalización ", e);
				result.setError(true);
				result.setMensaje("Se ha producido un error al intentar comprobar si se puede guardar la legalización.");
			}
			// Comprueba si la fecha de legalización a introducir es válida
			result = fechaLegalizacionValida(legalizacionCitaDto.getFechaLegalizacion(), fechaLimitePresentacionEnColegio, isAdmin);

		}

		return result;
	}

}
