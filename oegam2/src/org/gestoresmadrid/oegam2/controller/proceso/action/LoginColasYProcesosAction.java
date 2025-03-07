package org.gestoresmadrid.oegam2.controller.proceso.action;

import java.net.InetAddress;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.gestoresmadrid.oegam2comun.proceso.model.service.ServicioProcesos;
import org.gestoresmadrid.oegam2comun.proceso.model.service.ServicioUsuarioConexionGestProcesos;
import org.gestoresmadrid.oegam2comun.proceso.model.service.ServicioUsuarioGestProcesos;
import org.gestoresmadrid.oegam2comun.proceso.view.dto.UsuarioGestProcesosDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import general.acciones.ActionBase;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class LoginColasYProcesosAction extends ActionBase {

	private static final long serialVersionUID = -36941813547264622L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(LoginColasYProcesosAction.class);

	private static final String LOGIN = "loginCYP";
	private static final String CAMBIO_PASSWORD = "cambioPassword";
	private static final String GESTION_COLAS = "inicioGestionCola";
	private static final String GESTION_PROCESOS = "inicioGestionProceso";

	private static final String PASSWORD_DEFECTO = "gestion.procesos.password.defecto";

	private static final String PANTALLA_COLA = "COLAS";
	private static final String PANTALLA_PROCESO = "PROCESOS";

	private String username;
	private String password;
	private String passwordNueva;
	private boolean logado;
	private boolean cambio;

	private String pantalla;
	private String rol;

	@Autowired
	ServicioProcesos servicioProceso;

	@Autowired
	ServicioUsuarioGestProcesos servicioUsuarioGestProcesos;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	Utiles utiles;

	@Autowired
	UtilesColegiado utilesColegiado;

	public String inicioCola() {
		pantalla = PANTALLA_COLA;
		return LOGIN;
	}

	public String inicioProceso() {
		pantalla = PANTALLA_PROCESO;
		return LOGIN;
	}

	public String login() {
		logado = false;
		try {
			if (StringUtils.isNotBlank(username)) {
				UsuarioGestProcesosDto usuarioGest = servicioUsuarioGestProcesos
						.getUserProcesosByIdUsuarioAndUser(utilesColegiado.getIdUsuarioSession(), username);
				if (usuarioGest != null) {
					if (usuarioGest.getEstado() == null || usuarioGest.getEstado().equals(1L)) {
						if (username.equals(usuarioGest.getUserName())) {
							String passwordDefecto = gestorPropiedades.valorPropertie(PASSWORD_DEFECTO);
							if (passwordDefecto != null && passwordDefecto.equals(usuarioGest.getPassword())) {
								if (password.equals(usuarioGest.getPassword())) {
									addActionMessage("Debe modificar la contraseña asignada por defecto.");
									return CAMBIO_PASSWORD;
								} else {
									addActionError("Contraseña incorrecta.");
								}
							} else {
								String passwordMd5 = utiles.hashPassword(password);
								if (passwordMd5 != null && passwordMd5.equals(usuarioGest.getPassword())) {
									logado = true;
									guardarUltimaConexion(usuarioGest.getIdGestProcesos());
									if (PANTALLA_COLA.equals(pantalla)) {
										rol = usuarioGest.getRol();
										return GESTION_COLAS;
									}
									if (PANTALLA_PROCESO.equals(pantalla)) {
										return GESTION_PROCESOS;
									}
								} else {
									password = "";
									addActionError("Contraseña incorrecta.");
								}
							}
						} else {
							addActionError("Nombre de usuario incorrecto.");
						}
					} else {
						addActionError("Usuario deshabilitado.");
					}
				} else {
					addActionError("Este usuario no está dado de alta para la Gestión de Procesos.");
				}
			} else {
				addActionError("Debe rellenar el nombre de usuario.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de logarse en Gestión Procesos, error: ", e);
			addActionError("Ha sucedido un error a la hora de logarse en Gestión Procesos.");
		}
		return LOGIN;
	}

	public String irCambioPassword() {
		logado = false;
		try {
			UsuarioGestProcesosDto usuarioGest = servicioUsuarioGestProcesos
					.getUsuarioGestProcesosByIdUsuario(utilesColegiado.getIdUsuarioSession());
			if (usuarioGest != null) {
				if (usuarioGest.getEstado() == null || usuarioGest.getEstado().equals(1L)) {
					if (username.equals(usuarioGest.getUserName())) {
						return CAMBIO_PASSWORD;
					} else {
						addActionError("Nombre de usuario incorrecto.");
					}
				} else {
					addActionError("Usuario deshabilitado.");
				}
			} else {
				addActionError("Este usuario no está dado de alta para la Gestión de Procesos.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de logarse en Gestión Procesos, error: ", e);
			addActionError("Ha sucedido un error a la hora de logarse en Gestión Procesos.");
		}
		return LOGIN;
	}

	public String cambioPasswordInicio() {
		String passwordAntiguaMd5 = "";
		try {
			UsuarioGestProcesosDto usuarioGest = servicioUsuarioGestProcesos
					.getUserProcesosByIdUsuarioAndUser(utilesColegiado.getIdUsuarioSession(), username);
			if (usuarioGest != null) {
				if (cambio) {
					passwordAntiguaMd5 = utiles.hashPassword(password);
				} else {
					passwordAntiguaMd5 = password;
				}
				if (passwordAntiguaMd5.equals(usuarioGest.getPassword())) {
					String passwordMd5 = utiles.hashPassword(passwordNueva);
					ResultBean result = servicioUsuarioGestProcesos.cambiarPassword(username, passwordMd5);
					if (result != null && result.getError()) {
						addActionError(result.getMensaje());
					} else {
						if (cambio) {
							addActionMessage("Contrasela modificada correctamente.");
							return LOGIN;
						} else {
							logado = true;
							guardarUltimaConexion(usuarioGest.getIdGestProcesos());
							return GESTION_COLAS;
						}
					}
				} else {
					addActionError("Contraseña antigua incorrecta.");
				}
			} else {
				addActionError(
						"Ha sucedido un error a la hora de recuperar el usuario al cambiar la contraseña para Gestión Procesos.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de cambiar la contraseña para Gestión Procesos, error: ", e);
			addActionError("Ha sucedido un error a la hora de cambiar la contraseña para Gestión Procesos.");
		}
		return CAMBIO_PASSWORD;
	}

	private void guardarUltimaConexion(Long idGestProcesos) {
		String ipFrontal = "";
		String ipAccesso = "";
		String tipoAcceso = "";
		try {
			InetAddress address = InetAddress.getLocalHost();
			ipFrontal = address.getHostAddress();
			ipAccesso = ServletActionContext.getRequest().getRemoteAddr();
			if (ServletActionContext.getRequest().getHeader("client-ip") != null) {
				ipAccesso = ServletActionContext.getRequest().getHeader("client-ip"); 
			}
		} catch (Throwable e1) {
			log.error("Error UnknownHostException: ", e1);
		}
		if (PANTALLA_COLA.equals(pantalla)) {
			tipoAcceso = ServicioUsuarioConexionGestProcesos.ACCESO_COLA;
		} else if (PANTALLA_PROCESO.equals(pantalla)) {
			tipoAcceso = ServicioUsuarioConexionGestProcesos.ACCESO_PROCESO;
		}
		servicioUsuarioGestProcesos.guardarUltimaConexion(idGestProcesos, ipAccesso, ipFrontal, tipoAcceso);
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

	public boolean isLogado() {
		return logado;
	}

	public void setLogado(boolean logado) {
		this.logado = logado;
	}

	public boolean isCambio() {
		return cambio;
	}

	public void setCambio(boolean cambio) {
		this.cambio = cambio;
	}

	public String getPantalla() {
		return pantalla;
	}

	public void setPantalla(String pantalla) {
		this.pantalla = pantalla;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}
}