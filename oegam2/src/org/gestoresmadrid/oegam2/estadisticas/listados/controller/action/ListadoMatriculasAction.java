package org.gestoresmadrid.oegam2.estadisticas.listados.controller.action;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioVehiculo;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.vehiculo.view.dto.VehiculoDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import general.acciones.ActionBase;
import trafico.utiles.constantes.ConstantesEstadisticas;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ListadoMatriculasAction extends ActionBase {

	private static final long serialVersionUID = -3043967506788455546L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ListadoMatriculasAction.class);

	private int numElementosSinAgrupar;

	private String password; // Password para ver estadísticas
	private String passValidado; // Password Validado para ver estadísticas. Por defecto No hay permiso.

	private String numMatricula;
	private String letraMatricula;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	UtilesColegiado utilesColegiado;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	private ServicioVehiculo servicioVehiculo;

	public String login() {
		setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_FALSE);
		return SUCCESS;
	}

	public String comprobarPassword() {
		String passwordPropiedades = gestorPropiedades.valorPropertie(ConstantesEstadisticas.PASSWORD_CAMPO);

		if (StringUtils.isNotBlank(passwordPropiedades) && passwordPropiedades.equals(getPassword()) && utilesColegiado.tienePermisoAdmin()) {
			setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_TRUE);
		} else {
			setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_ERROR);
			addActionError(ConstantesEstadisticas.PASSWORD_INCORRECTO);
		}

		return SUCCESS;
	}

	public String calcularFechaPrimeraMatriculacion() {
		log.debug("inicio calcular Fecha Primera Matriculación");
		setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_TRUE);

		if (StringUtils.isNotBlank(getNumMatricula()) && getNumMatricula().length() != 4) {
			addActionError(ConstantesEstadisticas.MENSAJE_ERROR_NUM_MATRICULA);
			return ConstantesEstadisticas.LISTADO_MATRICULAS;
		} else if (StringUtils.isNotBlank(getLetraMatricula()) && getLetraMatricula().length() != 3) {
			addActionError(ConstantesEstadisticas.MENSAJE_ERROR_LETRA_MATRICULA);
			return ConstantesEstadisticas.LISTADO_MATRICULAS;
		}

		VehiculoDto vehiculo = servicioVehiculo.getVehiculoDto(null, null, getNumMatricula() + getLetraMatricula(), null, null, null);

		if (vehiculo == null) {
			addActionError("No se ha encontrado la matrícula " + getNumMatricula() + "-" + getLetraMatricula().toUpperCase() + ".");
		} else if (vehiculo.getFechaPrimMatri() != null && !vehiculo.getFechaPrimMatri().isfechaNula()) {
			addActionMessage("La matrícula " + getNumMatricula() + " - " + getLetraMatricula().toUpperCase() + " corresponde a " + utilesFecha.formatoFecha(vehiculo.getFechaPrimMatri())
					+ " en la plataforma OEGAM.");
		} else {
			addActionError("La matrícula " + getNumMatricula() + "-" + getLetraMatricula().toUpperCase() + " no tiene registrada la fecha de primera matriculación.");
		}

		log.debug("fin calcular Fecha Primera Matriculación");
		return SUCCESS;
	}

	// ----------------- GET & SET -------------------------

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassValidado() {
		return passValidado;
	}

	public void setPassValidado(String passValidado) {
		this.passValidado = passValidado;
	}

	protected ILoggerOegam getLog() {
		return log;
	}

	public int getNumElementosSinAgrupar() {
		return numElementosSinAgrupar;
	}

	public void setNumElementosSinAgrupar(int numElementosSinAgrupar) {
		this.numElementosSinAgrupar = numElementosSinAgrupar;
	}

	public String getNumMatricula() {
		return numMatricula;
	}

	public void setNumMatricula(String numMatricula) {
		this.numMatricula = numMatricula;
	}

	public String getLetraMatricula() {
		return letraMatricula;
	}

	public void setLetraMatricula(String letraMatricula) {
		this.letraMatricula = letraMatricula;
	}

}