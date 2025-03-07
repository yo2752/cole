package org.gestoresmadrid.oegam2.controller.proceso.action;

import java.util.Date;
import java.util.List;

import org.gestoresmadrid.oegam2comun.proceso.model.service.ServicioProcesos;
import org.gestoresmadrid.oegam2comun.proceso.view.bean.ResultadoProcesosBean;
import org.gestoresmadrid.oegam2comun.proceso.view.dto.ProcesoDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.springframework.beans.factory.annotation.Autowired;

import general.acciones.ActionBase;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ProcesosAction extends ActionBase {

	private static final long serialVersionUID = -36941813547264622L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ProcesosAction.class);

	private static final String LISTA_PROCESOS = "pagListaProcesos";
	private static final String LOGIN = "login";

	private String patron;
	private String listaProcesosPatron;
	private Date fechaActualizacion;
	private List<ProcesoDto> listaProcesos;
	private String ordenProceso;
	private String intentosMax;
	private String hilosColas;
	private String horaInicio;
	private String horaFin;
	private String tiempoCorrecto;
	private String tiempoRecuperable;
	private String tiempoNoRecuperable;
	private String tiempoSinDatos;

	private String username;
	private String password;
	private String passwordNueva;
	private boolean logado;
	private boolean cambio;

	@Autowired
	ServicioProcesos servicioProceso;

	@Autowired
	UtilesColegiado utilesColegiado;

	public String lista() {
		try {
			if (!logado) {
				return LOGIN;
			}
			ResultadoProcesosBean resultado = servicioProceso.getListadoProcesosPantalla();
			if (resultado.getError()) {
				aniadirListaErrores(resultado.getListaMensajes());
			}
			rellenarDatos(resultado);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de listar los procesos, error: ", e);
			addActionError("Ha sucedido un error a la hora de listar los procesos.");
		}
		return LISTA_PROCESOS;
	}

	public String listaPorPatron() {
		try {
			ResultadoProcesosBean resultado = servicioProceso.getListadoProcesosPorPatron(patron);
			if (resultado.getError()) {
				aniadirListaErrores(resultado.getListaMensajes());
			}
			rellenarDatos(resultado);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de listar los procesos por patron, error: ", e);
			addActionError("Ha sucedido un error a la hora de listar los procesos por patron.");
		}
		return LISTA_PROCESOS;
	}

	public String pararProceso() {
		try {
			ResultadoProcesosBean resultado = servicioProceso.pararProceso(ordenProceso, patron,
					utilesColegiado.getIdUsuarioSession(), username);
			if (resultado.getError()) {
				aniadirListaErrores(resultado.getListaMensajes());
			}
			rellenarDatos(resultado);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de parar el proceso, error: ", e);
			addActionError("Ha sucedido un error a la hora de parar el proceso.");
		}
		return LISTA_PROCESOS;
	}

	private void rellenarDatos(ResultadoProcesosBean resultado) {
		listaProcesos = resultado.getListaProcesos();
		if (resultado.getListaProcesosPatron() != null && !resultado.getListaProcesosPatron().isEmpty()) {
			listaProcesosPatron = resultado.getListaProcesosPatron();
		}
		fechaActualizacion = new Date();
	}

	public String activarProceso() {
		try {
			ResultadoProcesosBean resultado = servicioProceso.activarProceso(ordenProceso, patron,
					utilesColegiado.getIdUsuarioSession(), username);
			if (resultado.getError()) {
				aniadirListaErrores(resultado.getListaMensajes());
			}
			rellenarDatos(resultado);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de activar el proceso, error: ", e);
			addActionError("Ha sucedido un error a la hora de activar el proceso.");
		}
		return LISTA_PROCESOS;
	}

	public String actualizar() {
		try {
			ResultadoProcesosBean resultado = servicioProceso.actualizarGestorColas(patron);
			if (resultado.getError()) {
				aniadirListaErrores(resultado.getListaMensajes());
			}
			rellenarDatos(resultado);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de actualizar el proceso, error: ", e);
			addActionError("Ha sucedido un error a la hora de actualizar el proceso.");
		}
		return LISTA_PROCESOS;
	}

	public String modificar() {
		try {
			ResultadoProcesosBean resultado = servicioProceso.modificar(ordenProceso, intentosMax, hilosColas,
					horaInicio, horaFin, tiempoCorrecto, tiempoRecuperable, tiempoNoRecuperable, tiempoSinDatos,
					patron);
			if (resultado.getError()) {
				aniadirListaErrores(resultado.getListaMensajes());
			} else {
				addActionMessage(resultado.getNombreProceso() + " modificado correctamente.");
			}
			rellenarDatos(resultado);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de modificar el proceso, error: ", e);
			addActionError("Ha sucedido un error a la hora de modificar el proceso.");
		}
		return LISTA_PROCESOS;
	}

	public String arrancarPatron() {
		try {
			ResultadoProcesosBean resultado = servicioProceso.activarProcesoPorPatron(patron,
					utilesColegiado.getIdUsuarioSession());
			if (resultado.getError()) {
				aniadirListaErrores(resultado.getListaMensajes());
			}
			rellenarDatos(resultado);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de arrancar los procesos por patron, error: ", e);
			addActionError("Ha sucedido un error a la hora de arrancar los procesos por patron.");
		}
		return LISTA_PROCESOS;
	}

	public String pararPatron() {
		try {
			ResultadoProcesosBean resultado = servicioProceso.pararProcesoPorPatron(patron, utilesColegiado.getIdUsuarioSession());
			if (resultado.getError()) {
				aniadirListaErrores(resultado.getListaMensajes());
			}
			rellenarDatos(resultado);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de listar parar los procesos por patron, error: ", e);
			addActionError("Ha sucedido un error a la hora de parar los procesos por patron.");
		}
		return LISTA_PROCESOS;
	}

	public List<ProcesoDto> getListaProcesos() {
		return listaProcesos;
	}

	public void setListaProcesos(List<ProcesoDto> listaProcesos) {
		this.listaProcesos = listaProcesos;
	}

	public String getPatron() {
		return patron;
	}

	public void setPatron(String patron) {
		this.patron = patron;
	}

	public String getListaProcesosPatron() {
		return listaProcesosPatron;
	}

	public void setListaProcesosPatron(String listaProcesosPatron) {
		this.listaProcesosPatron = listaProcesosPatron;
	}

	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public String getOrdenProceso() {
		return ordenProceso;
	}

	public void setOrdenProceso(String ordenProceso) {
		this.ordenProceso = ordenProceso;
	}

	public String getIntentosMax() {
		return intentosMax;
	}

	public void setIntentosMax(String intentosMax) {
		this.intentosMax = intentosMax;
	}

	public String getHilosColas() {
		return hilosColas;
	}

	public void setHilosColas(String hilosColas) {
		this.hilosColas = hilosColas;
	}

	public String getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	public String getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(String horaFin) {
		this.horaFin = horaFin;
	}

	public String getTiempoCorrecto() {
		return tiempoCorrecto;
	}

	public void setTiempoCorrecto(String tiempoCorrecto) {
		this.tiempoCorrecto = tiempoCorrecto;
	}

	public String getTiempoRecuperable() {
		return tiempoRecuperable;
	}

	public void setTiempoRecuperable(String tiempoRecuperable) {
		this.tiempoRecuperable = tiempoRecuperable;
	}

	public String getTiempoNoRecuperable() {
		return tiempoNoRecuperable;
	}

	public void setTiempoNoRecuperable(String tiempoNoRecuperable) {
		this.tiempoNoRecuperable = tiempoNoRecuperable;
	}

	public String getTiempoSinDatos() {
		return tiempoSinDatos;
	}

	public void setTiempoSinDatos(String tiempoSinDatos) {
		this.tiempoSinDatos = tiempoSinDatos;
	}

	public boolean isLogado() {
		return logado;
	}

	public void setLogado(boolean logado) {
		this.logado = logado;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordNueva() {
		return passwordNueva;
	}

	public void setPasswordNueva(String passwordNueva) {
		this.passwordNueva = passwordNueva;
	}

	public boolean isCambio() {
		return cambio;
	}

	public void setCambio(boolean cambio) {
		this.cambio = cambio;
	}

}