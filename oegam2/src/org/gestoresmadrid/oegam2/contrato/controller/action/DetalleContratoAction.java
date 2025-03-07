package org.gestoresmadrid.oegam2.contrato.controller.action;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.contrato.model.enumerados.EstadoContrato;
import org.gestoresmadrid.core.contrato.model.enumerados.EstadoUsuario;
import org.gestoresmadrid.core.model.enumerados.TipoActualizacion;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioCorreoContratoTramite;
import org.gestoresmadrid.oegam2comun.contrato.view.bean.ContratoUsuarioFilterBean;
import org.gestoresmadrid.oegam2comun.model.service.ServicioAplicacion;
import org.gestoresmadrid.oegam2comun.model.service.ServicioPermisos;
import org.gestoresmadrid.oegam2comun.model.service.ServicioUsuario;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.accesos.view.dto.AplicacionDto;
import org.gestoresmadrid.oegamComun.accesos.view.dto.FuncionDto;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.oegamComun.contrato.view.dto.CorreoContratoTramiteDto;
import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class DetalleContratoAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = -3441552816098767324L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(DetalleContratoAction.class);

	@Autowired
	private ServicioContrato servicioContrato;

	@Autowired
	private ServicioPermisos servicioPermisos;

	@Autowired
	private ServicioUsuario servicioUsuario;

	@Autowired
	private ServicioAplicacion servicioAplicacion;

	@Autowired
	private ServicioCorreoContratoTramite servicioCorreoContratoTramite;

	@Autowired
	Utiles utiles;

	@Autowired
	UtilesColegiado utilesColegiado;

	private BigDecimal idContrato;
	private Long idUsuario;
	private String nif;
	private ContratoDto contratoDto;

	private List<FuncionDto> listaPermisosAplicacion;
	private String codAplicacionSeleccionada;
	private String codAplicacionActiva;
	private Boolean aplicacionSel = false;
	private String[] idAplicaciones;
	private String[] permisosAplicaciones;

	private String[] idUsuarios;
	private String[] idUsuariosGestoria;
	private String[] idUsuariosContrato;
	private UsuarioDto usuarioDto;
	private UsuarioDto usuarioNuevoDto;
	private BigDecimal idUsuarioSeleccionado;
	private Boolean usuarioSel = false;
	private String[] permisosUsuarios;
	private String passCorpme;
	private String idCorreoContratoTramite;

	ContratoUsuarioFilterBean contratoUsuarioFilter;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	public ContratoUsuarioFilterBean getContratoUsuarioFilter() {
		return contratoUsuarioFilter;
	}

	public void setContratoUsuarioFilter(ContratoUsuarioFilterBean contratoUsuarioFilter) {
		this.contratoUsuarioFilter = contratoUsuarioFilter;
	}

	@Resource
	ModelPagination modeloEvolucionUsuarioPaginatedImpl;

	private static final String POP_UP_EVOLUCION_USUARIO = "irEvolucionUsuario";

	private static final String[] fetchList = { "colegiado", "provincia" };

	public String nuevo() throws Throwable {
		contratoDto = new ContratoDto();
		return SUCCESS;
	}

	public String detalle() throws Throwable {
		if (idContrato != null) {
			contratoDto = servicioContrato.detalleContrato(idContrato);
			if (contratoDto != null && StringUtils.isNotBlank(contratoDto.getPasswordCorpme())) {
				passCorpme = contratoDto.getPasswordCorpme();
			}
		}
		return SUCCESS;
	}

	public String detalleAplicacion() throws Throwable {
		listaPermisosAplicacion = servicioPermisos.obtenerPermisosContratoPorAplicacion(contratoDto.getIdContrato().longValue(), codAplicacionSeleccionada);
		contratoDto = servicioContrato.detalleContrato(contratoDto.getIdContrato());
		codAplicacionActiva = codAplicacionSeleccionada;
		aplicacionSel = true;
		return SUCCESS;
	}

	public String detalleUsuario() throws Throwable {
		usuarioDto = servicioUsuario.usuarionConPermisos(idUsuarioSeleccionado, contratoDto.getIdContrato().longValue());
		contratoDto = servicioContrato.detalleContrato(contratoDto.getIdContrato());
		usuarioSel = true;
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String guardar() throws Exception {
		if (EstadoContrato.Eliminado.getValorEnum().equals(contratoDto.getEstadoContrato())) {
			addActionMessage("El contrato se encuentra en estado eliminado, no se puede modificar");
		} else {
			if ((StringUtils.isBlank(passCorpme) || !passCorpme.equalsIgnoreCase(contratoDto.getPasswordCorpme())) && StringUtils.isNotBlank(contratoDto.getPasswordCorpme())) {
				String passwordMd5 = utiles.hashPassword(contratoDto.getPasswordCorpme());
				contratoDto.setPasswordCorpme(passwordMd5);
			}
			ResultBean result = servicioContrato.guardarContrato(contratoDto, utilesColegiado.getIdUsuarioSessionBigDecimal());
			if (result != null && !result.getError()) {
				contratoDto = servicioContrato.detalleContrato(contratoDto.getIdContrato());
				setPassCorpme(contratoDto.getPasswordCorpme());
				Boolean nuevo = (Boolean) result.getAttachment(ServicioContrato.NUEVO_CONTRATO);
				if (!nuevo) {
					List<AplicacionDto> aplicaciones = asignarDesasignarAplicaciones(contratoDto.getAplicacionesDto(), idAplicaciones, contratoDto.getIdContrato(), contratoDto.getColegiadoDto()
							.getNumColegiado());
					contratoDto.setAplicacionesDto(aplicaciones);
				}

				addActionMessage("Contrato '" + contratoDto.getRazonSocial() + "' guardado.");
			} else {
				if (result.getListaMensajes() != null && !result.getListaMensajes().isEmpty()) {
					addActionError("Error al guardar el contrato '" + contratoDto.getRazonSocial());
					for (String mensaje : result.getListaMensajes())
						addActionError(mensaje);
				}
				result = servicioCorreoContratoTramite.getCorreosPorContrato(contratoDto.getIdContrato().longValue());
				if (result != null && !result.getError()) {
					contratoDto.setCorreosTramites((List<CorreoContratoTramiteDto>) result.getAttachment("CORREOS"));
				}
			}
		}

		return SUCCESS;
	}

	public String guardarCorpme() throws Exception {
		if (EstadoContrato.Eliminado.getValorEnum().equals(contratoDto.getEstadoContrato())) {
			addActionMessage("El contrato se encuentra en estado eliminado, no se puede modificar");
		} else {
			if ((StringUtils.isBlank(passCorpme) || !passCorpme.equalsIgnoreCase(contratoDto.getPasswordCorpme())) && StringUtils.isNotBlank(contratoDto.getPasswordCorpme())) {
				String passwordMd5 = utiles.hashPassword(contratoDto.getPasswordCorpme());
				contratoDto.setPasswordCorpme(passwordMd5);
			}
			ResultBean result = servicioContrato.guardarCorpme(contratoDto.getColegiadoDto().getNumColegiado(), contratoDto.getColegiadoDto().getUsuario().getNif(), contratoDto.getColegiadoDto()
					.getUsuario().getApellido1(), contratoDto.getUsuarioCorpme(), contratoDto.getPasswordCorpme(), contratoDto.getNcorpme(), utilesColegiado.getIdUsuarioSessionBigDecimal());
			if (result != null && !result.getError()) {
				contratoDto = servicioContrato.detalleContrato(contratoDto.getIdContrato());
				passCorpme = contratoDto.getPasswordCorpme();
				addActionMessage("Contrato '" + contratoDto.getRazonSocial() + "' guardado.");
			} else {
				if (result.getListaMensajes() != null && !result.getListaMensajes().isEmpty()) {
					addActionError("Error al guardar el contrato '" + contratoDto.getRazonSocial());
					for (String mensaje : result.getListaMensajes())
						addActionError(mensaje);
				}
			}
		}
		return SUCCESS;
	}

	private List<AplicacionDto> asignarDesasignarAplicaciones(List<AplicacionDto> aplicaciones, String[] idAplicaciones,
			BigDecimal idContrato, String numColegiado) {
		List<AplicacionDto> aplicacionesActualizadas = new ArrayList<AplicacionDto>();
		for (AplicacionDto aplicacion : aplicaciones) {
			boolean asignar = false;
			boolean desasignar = true;

			if (!aplicacion.isAsignada()) {
				desasignar = false;
				if (idAplicaciones != null && idAplicaciones.length > 0) {
					for (String codigoAplicacion : idAplicaciones) {
						if (codigoAplicacion.equals(aplicacion.getCodigoAplicacion())) {
							asignar = true;
							break;
						}
					}
				}
			} else if (aplicacion.isAsignada()) {
				if (idAplicaciones != null && idAplicaciones.length > 0) {
					for (String codigoAplicacion : idAplicaciones) {
						if (codigoAplicacion.equals(aplicacion.getCodigoAplicacion())) {
							desasignar = false;
							break;
						}
					}
				}
			}

			if (asignar) {
				aplicacion.setAsignada(true);
				servicioAplicacion.asociarAplicacionContrato(aplicacion.getCodigoAplicacion(), idContrato, numColegiado, true);
			} else if (desasignar) {
				aplicacion.setAsignada(false);
				servicioAplicacion.asociarAplicacionContrato(aplicacion.getCodigoAplicacion(), idContrato, numColegiado, false);
			}
			aplicacionesActualizadas.add(aplicacion);
		}

		return aplicacionesActualizadas;
	}

	public String guardarPermisos() {
		ResultBean result = new ResultBean();
		boolean permisoActualizado = false;
		listaPermisosAplicacion = servicioPermisos.obtenerPermisosContratoPorAplicacion(contratoDto.getIdContrato().longValue(), codAplicacionActiva);

		for (FuncionDto funcion : listaPermisosAplicacion) {
			ResultBean resultPermiso = new ResultBean();
			boolean asignar = false;
			boolean desasignar = true;

			if (!funcion.isAsignada()) {
				desasignar = false;
				for (String codigoFuncion : permisosAplicaciones) {
					if (codigoFuncion.equals(funcion.getCodigoFuncion())) {
						asignar = true;
						break;
					}
				}
			} else if (funcion.isAsignada()) {
				for (String codigoFuncion : permisosAplicaciones) {
					if (codigoFuncion.equals(funcion.getCodigoFuncion())) {
						desasignar = false;
						break;
					}
				}
			}

			if (asignar) {
				permisoActualizado = true;
				resultPermiso = servicioPermisos.asignarPermiso(contratoDto.getIdContrato(), funcion.getCodigoFuncion(), codAplicacionActiva, contratoDto.getColegiadoDto().getNumColegiado(), 0L);
			} else if (desasignar) {
				permisoActualizado = true;
				resultPermiso = servicioPermisos.desasignarPermiso(contratoDto.getIdContrato(), funcion.getCodigoFuncion(), codAplicacionActiva, contratoDto.getColegiadoDto().getNumColegiado(), 0L);
			}

			if (resultPermiso != null && resultPermiso.getError()) {
				result.setError(true);
				result.setMensaje("Permiso " + funcion.getCodigoFuncion() + "no actualizado");
			}
		}

		if (result != null && result.getError()) {
			if (result.getListaMensajes() != null && !result.getListaMensajes().isEmpty()) {
				addActionError("Error en el mantenimiento de permisos." + contratoDto.getRazonSocial());
				for (String mensaje : result.getListaMensajes())
					addActionError(mensaje);
			}
		} else {
			addActionMessage("Permisos Actualizados correctamente.");
		}

		if (permisoActualizado) {
			listaPermisosAplicacion = servicioPermisos.obtenerPermisosContratoPorAplicacion(contratoDto.getIdContrato().longValue(), codAplicacionActiva);
		}
		contratoDto = servicioContrato.detalleContrato(contratoDto.getIdContrato());
		aplicacionSel = true;

		return SUCCESS;
	}

	public String guardarUsuario() {
		usuarioDto.setNumColegiado(contratoDto.getColegiadoDto().getNumColegiado());
		usuarioDto.setNumColegiadoNacional(contratoDto.getColegiadoDto().getNumColegiadoNacional());

		ResultBean result = servicioUsuario.guardarUsuario(usuarioDto);

		List<FuncionDto> permisos = guardarPermisosUsuario();
		usuarioDto.setPermisos(permisos);

		if (result.getError()) {
			addActionError("Error al guardar el usuario '" + usuarioDto.getApellidosNombre() + "': " + result.getMensaje() + ".");
		} else {
			addActionMessage("El usuario '" + usuarioDto.getApellidosNombre() + "' se ha guardado correctamente.");
		}

		contratoDto = servicioContrato.detalleContrato(contratoDto.getIdContrato());
		usuarioSel = true;

		return SUCCESS;
	}

	private List<FuncionDto> guardarPermisosUsuario() {
		ResultBean result = new ResultBean();
		boolean permisoActualizado = false;
		List<FuncionDto> permisos = servicioPermisos.obtenerPermisosUsuario(contratoDto.getIdContrato().longValue(), usuarioDto.getIdUsuario().longValue());
		Boolean esAdmin = utilesColegiado.tienePermisoAdmin();
		for (FuncionDto funcion : permisos) {
			ResultBean resultPermiso = new ResultBean();
			boolean asignar = false;
			boolean desasignar = true;

			if (!funcion.isAsignada()) {
				desasignar = false;
				for (String codigoFuncion : permisosUsuarios) {
					if (codigoFuncion.equals(funcion.getCodigoFuncion())) {
						asignar = true;
						break;
					}
				}
			} else if (funcion.isAsignada()) {
				if (!"OT02".equals(funcion.getCodigoFuncion()) && !"OT2M".equals(funcion.getCodigoFuncion()) && !"OT7M".equals(funcion.getCodigoFuncion())) {
					for (String codigoFuncion : permisosUsuarios) {
						if (codigoFuncion.equals(funcion.getCodigoFuncion())) {
							desasignar = false;
							break;
						}
					}
				} else {
					if (esAdmin) {
						for (String codigoFuncion : permisosUsuarios) {
							if (codigoFuncion.equals(funcion.getCodigoFuncion())) {
								desasignar = false;
								break;
							}
						}
					} else {
						desasignar = false;
					}
				}
			}

			if (asignar) {
				permisoActualizado = true;
				resultPermiso = servicioPermisos.asignarPermiso(contratoDto.getIdContrato(), funcion.getCodigoFuncion(), funcion.getCodigoAplicacion(), contratoDto.getColegiadoDto().getNumColegiado(),
						usuarioDto.getIdUsuario().longValue());
			} else if (desasignar) {
				permisoActualizado = true;
				resultPermiso = servicioPermisos.desasignarPermiso(contratoDto.getIdContrato(), funcion.getCodigoFuncion(), funcion.getCodigoAplicacion(), contratoDto.getColegiadoDto()
						.getNumColegiado(), usuarioDto.getIdUsuario().longValue());
			}

			if (resultPermiso != null && resultPermiso.getError()) {
				result.setError(true);
				result.setMensaje("Permiso " + funcion.getCodigoFuncion() + "no actualizado");
			}
		}

		if (permisoActualizado) {
			permisos = servicioPermisos.obtenerPermisosUsuario(contratoDto.getIdContrato().longValue(), usuarioDto.getIdUsuario().longValue());
		}

		return permisos;
	}

	// ********************************************************************************************************
	// ********************** ACTIONS DETALLE CONTRATO PESTAÑA -- USUARIOS ASOCIADOS AL COLEGIADO ****************************
	// ********************************************************************************************************

	private void desasignarPermisosUsuarioNuevo(BigDecimal idContrato, String numColegiado, Long idUsuario) {
		ResultBean result = new ResultBean();
		servicioPermisos.desasignarPermiso(idContrato, "OT02", "OEGAM_TRAF", numColegiado, idUsuario);
		if (result.getError()) {
			addActionError("Error estableciendo permisos al dar de alta el usuario: " + result.getMensaje() + ".");
		}
		servicioPermisos.desasignarPermiso(idContrato, "OT2T", "OEGAM_TRAF", numColegiado, idUsuario);
		if (result.getError()) {
			addActionError("Error estableciendo permisos al dar de alta el usuario: " + result.getMensaje() + ".");
		}
		servicioPermisos.desasignarPermiso(idContrato, "OT7M", "OEGAM_TRAF", numColegiado, idUsuario);
		if (result.getError()) {
			addActionError("Error estableciendo permisos al dar de alta el usuario: " + result.getMensaje() + ".");
		}
	}

	// ALTA NUEVO USUARIO
	public String nuevoUsuario() {
		usuarioNuevoDto.setNumColegiado(contratoDto.getColegiadoDto().getNumColegiado());
		usuarioNuevoDto.setNumColegiadoNacional(contratoDto.getColegiadoDto().getNumColegiadoNacional());
		ResultBean result = servicioUsuario.guardarUsuario(usuarioNuevoDto);

		if (result.getError()) {
			addActionError("Error al dar de alta el usuario '" + usuarioNuevoDto.getApellidosNombre() + "': " + result.getMensaje() + ".");
		} else {
			Long idUsuario = (Long) result.getAttachment(ServicioUsuario.ID_USUARIO);
			desasignarPermisosUsuarioNuevo(contratoDto.getIdContrato(), contratoDto.getColegiadoDto().getNumColegiado(), idUsuario);
			addActionMessage("El usuario '" + usuarioNuevoDto.getApellidosNombre() + "' se ha dado de alta correctamente. Ahora le puede asignar permisos modificándolo.");

			String GestionarUsuariosPorContrato = gestorPropiedades.valorPropertie("gestionar.usuarios.contrato");

			if (GestionarUsuariosPorContrato != null && "SI".equals(GestionarUsuariosPorContrato)) {

				servicioContrato.guardarEvolucionUsuario(new BigDecimal(idUsuario), new Timestamp(Calendar.getInstance().getTimeInMillis()), contratoDto.getIdContrato().longValue(),
						TipoActualizacion.CRE, new BigDecimal(EstadoUsuario.Habilitado.getValorEnum()), null, null, usuarioNuevoDto.getNif());
			}
		}

		contratoDto = servicioContrato.detalleContrato(contratoDto.getIdContrato());

		return SUCCESS;
	}

	// HABILITAR USUARIO

	public String habilitarUsuarios() {

		for (String idUsuario : idUsuarios) {
			UsuarioDto usuarioContrato = servicioUsuario.getUsuarioDto(new BigDecimal(idUsuario));
			if (usuarioContrato == null) {
				addActionError("El usuario con número '" + idUsuario + "' no se ha encontrado.");
			} else {
				if (EstadoUsuario.Habilitado.getValorEnum().equals(usuarioContrato.getEstadoUsuario())) {
					addActionMessage("El usuario '" + usuarioContrato.getApellidosNombre() + "' ya está habilitado.");
				} else if (EstadoUsuario.Eliminado.getValorEnum().equals(usuarioContrato.getEstadoUsuario())) {
					addActionError("El usuario '" + usuarioContrato.getApellidosNombre() + "' está eliminado, no se puede habilitar.");
				} else if (EstadoUsuario.Deshabilitado.getValorEnum().equals(usuarioContrato.getEstadoUsuario())) {
					try {
						ResultBean result = servicioUsuario.habilitarUsuario(new BigDecimal(idUsuario), false);
						if (result != null && !result.getError()) {
							addActionMessage("El usuario '" + usuarioContrato.getApellidosNombre() + "' se ha habilitado.");
						} else {
							addActionError(result.getMensaje());
						}
					} catch (Exception e) {
						log.error("Error al habilitar el usuario " + usuarioContrato.getApellidosNombre(), e);
						addActionError("Error al habilitar el usuario '" + usuarioContrato.getApellidosNombre() + "': " + e.getMessage());
					}
				}
			}
		}
		contratoDto = servicioContrato.detalleContrato(contratoDto.getIdContrato());
		return SUCCESS;
	}

	// DESHABILITAR USUARIOS

	public String deshabilitarUsuarios() {
		for (String idUsuario : idUsuarios) {
			UsuarioDto usuarioContrato = servicioUsuario.getUsuarioDto(new BigDecimal(idUsuario));

			if (usuarioContrato == null) {
				addActionError("El usuario con número '" + idUsuario + "' no se ha encontrado.");
			} else {
				if (EstadoUsuario.Deshabilitado.getValorEnum().equals(usuarioContrato.getEstadoUsuario())) {
					addActionMessage("El usuario '" + usuarioContrato.getApellidosNombre() + "' ya está deshabilitado.");
				} else if (EstadoUsuario.Eliminado.getValorEnum().equals(usuarioContrato.getEstadoUsuario())) {
					addActionError("El usuario '" + usuarioContrato.getApellidosNombre() + "' está eliminado, no se puede deshabilitar.");
				} else if (EstadoUsuario.Habilitado.getValorEnum().equals(usuarioContrato.getEstadoUsuario())) {
					try {
						ResultBean result = servicioUsuario.deshabilitarUsuario(new BigDecimal(idUsuario), new Date(), false);
						if (result != null && !result.getError()) {
							addActionMessage("El usuario '" + usuarioContrato.getApellidosNombre() + "' se ha deshabilitado.");
						} else {
							addActionError(result.getMensaje());
						}

					} catch (Exception e) {
						log.error("Error al deshabilitar el usuario " + usuarioContrato.getApellidosNombre(), e);
						addActionError("Error al deshabilitar el usuario '" + usuarioContrato.getApellidosNombre() + "': " + e.getMessage());
					}
				}
			}
		}

		contratoDto = servicioContrato.detalleContrato(contratoDto.getIdContrato());

		return SUCCESS;
	}

	// ELIMINAR USUARIOS

	public String eliminarUsuarios() {
		String[] idUsuariosTotales = null;
		// Se está seleccionando los checks de los usuarios asociados al colegiado
		if (idUsuarios != null) {
			idUsuariosTotales = idUsuarios;
		} else {
			if (idUsuariosContrato != null) {
				idUsuariosTotales = idUsuariosContrato;
			}
		}

		for (String idUsuario : idUsuariosTotales) {
			UsuarioDto usuarioContrato = servicioUsuario.getUsuarioDto(new BigDecimal(idUsuario));

			if (usuarioContrato == null) {
				addActionError("El usuario con número '" + idUsuario + "' no se ha encontrado.");
			} else {
				if (EstadoUsuario.Eliminado.getValorEnum().equals(usuarioContrato.getEstadoUsuario())) {
					addActionError("El usuario '" + usuarioContrato.getApellidosNombre() + "' ya está eliminado.");
				} else {
					try {
						servicioUsuario.eliminarUsuario(new BigDecimal(idUsuario), new Date());
						addActionMessage("El usuario '" + usuarioContrato.getApellidosNombre() + "' se ha eliminado.");

					} catch (Exception e) {
						log.error("Error al eliminar el usuario " + usuarioContrato.getApellidosNombre(), e);
						addActionError("Error al eliminar el usuario '" + usuarioContrato.getApellidosNombre() + "': " + e.getMessage());
					}
				}
			}
		}
		contratoDto = servicioContrato.detalleContrato(contratoDto.getIdContrato());
		return SUCCESS;
	}

	// *******************************************************************************************************
	// ********************** ACTIONS PESTAÑA -- USUARIOS ASOCIADOS AL CONTRATO ****************************
	// *******************************************************************************************************

	// EVOLUCION
	public String abrirEvolucionUsuario() {

		if (nif != null) {
			return POP_UP_EVOLUCION_USUARIO;
		}
		addActionError("No ha seleccionado ningún Usuario para obtener su evolucion." + nif);

		return actualizarPaginatedList();

	}

	public String cargarUsuariosGestoria() {
		// Buscar el usuario y contrato en la lista de usuariosContrato para comprobar si se quiere volver a
		// agregar el mismo

		ResultBean resultado = servicioContrato.cargarUsuarioGestoria(getIdUsuariosGestoria(), Long.parseLong(idContrato.toString()));
		ContratoDto contrato = servicioContrato.detalleContrato(idContrato);

		if (resultado.getError()) {
			addActionError("Error al cargar usuarios : " + resultado.getMensaje() + "para el contrato " + contrato.getRazonSocial() + " .");
		} else {
			addActionMessage("El usuario  se ha asociado al contrato: " + contrato.getRazonSocial() + ".");
		}

		contratoDto = servicioContrato.detalleContrato(contratoDto.getIdContrato());
		return SUCCESS;

	}

	// ASOCIAR USUARIOS

	public String agregarUsuariosGestoria() {
		// Buscar el usuario y contrato en la lista de usuariosContrato para comprobar si se quiere volver a
		// agregar el mismo

		ResultBean resultado = servicioContrato.asociarUsuarioContrato(getIdUsuariosGestoria(), idContrato.longValue());
		ContratoDto contrato = servicioContrato.detalleContrato(idContrato);

		if (resultado.getError()) {
			addActionError("Error al agregar usuarios : " + resultado.getMensaje() + contrato.getRazonSocial() + " .");
		} else {
			addActionMessage("El usuario  se ha asociado al contrato: " + contrato.getRazonSocial() + ".");
		}

		contratoDto = servicioContrato.detalleContrato(contratoDto.getIdContrato());
		return SUCCESS;

	}

	// DESASOCIAR USUARIOS

	public String eliminarUsuariosAsociados() {

		ResultBean resultado = servicioContrato.eliminarUsuarioContrato(getIdUsuariosContrato(), idContrato.longValue());
		ContratoDto contrato = servicioContrato.detalleContrato(idContrato);

		if (resultado.getError()) {
			addActionError("Error al eliminar usuarios : " + resultado.getMensaje() + "para el contrato " + contrato.getRazonSocial() + " .");
		} else {
			addActionMessage("El usuario  se ha eliminado del contrato: " + contrato.getRazonSocial() + ".");
		}

		contratoDto = servicioContrato.detalleContrato(contratoDto.getIdContrato());
		return SUCCESS;
	}

	public String eliminarCorreoContratoTramite() {
		log.info("Eliminar Correo Contrato Trámite");
		if (StringUtils.isNotBlank(idCorreoContratoTramite)) {
			servicioCorreoContratoTramite.eliminarCorreoContratoTramite(Long.parseLong(idCorreoContratoTramite));
			addActionMessage("El correo asociado al trámite del contrato ha sido eliminado correctamente.");
		} else {
			addActionError("Debe seleccionar algún correo.");
		}
		contratoDto = servicioContrato.detalleContrato(contratoDto.getIdContrato());
		return SUCCESS;
	}

	public String modificarCorreoContratoTramite() {
		log.info("Modificar Correo Contrato Trámite");
		CorreoContratoTramiteDto correoContratoTramite = null;
		if (StringUtils.isNotBlank(idCorreoContratoTramite)) {
			correoContratoTramite = servicioCorreoContratoTramite.getCorreoContratoTramiteDto(Long.parseLong(idCorreoContratoTramite));
			addActionMessage("Correo asociado al trámite ha sido cargado para su modificación");

			contratoDto = servicioContrato.detalleContrato(contratoDto.getIdContrato());
			contratoDto.setCodigoAplicacion(correoContratoTramite.getTipoTramite().getCodigoAplicacion());
			contratoDto.setCorreoContratoTramite(correoContratoTramite);
		} else {
			addActionError("Debe seleccionar algún correo.");
			contratoDto = servicioContrato.detalleContrato(contratoDto.getIdContrato());
		}
		return SUCCESS;
	}

	// *******************************************************************************************************

	// GETTERS AND SETTERS

	public BigDecimal getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(BigDecimal idContrato) {
		this.idContrato = idContrato;
	}

	public ContratoDto getContratoDto() {
		return contratoDto;
	}

	public void setContratoDto(ContratoDto contratoDto) {
		this.contratoDto = contratoDto;
	}

	public String getCodAplicacionSeleccionada() {
		return codAplicacionSeleccionada;
	}

	public void setCodAplicacionSeleccionada(String codAplicacionSeleccionada) {
		this.codAplicacionSeleccionada = codAplicacionSeleccionada;
	}

	public Boolean getAplicacionSel() {
		return aplicacionSel;
	}

	public void setAplicacionSel(Boolean aplicacionSel) {
		this.aplicacionSel = aplicacionSel;
	}

	public List<FuncionDto> getListaPermisosAplicacion() {
		return listaPermisosAplicacion;
	}

	public void setListaPermisosAplicacion(List<FuncionDto> listaPermisosAplicacion) {
		this.listaPermisosAplicacion = listaPermisosAplicacion;
	}

	public BigDecimal getIdUsuarioSeleccionado() {
		return idUsuarioSeleccionado;
	}

	public void setIdUsuarioSeleccionado(BigDecimal idUsuarioSeleccionado) {
		this.idUsuarioSeleccionado = idUsuarioSeleccionado;
	}

	public Boolean getUsuarioSel() {
		return usuarioSel;
	}

	public void setUsuarioSel(Boolean usuarioSel) {
		this.usuarioSel = usuarioSel;
	}

	public UsuarioDto getUsuarioDto() {
		return usuarioDto;
	}

	public void setUsuarioDto(UsuarioDto usuarioDto) {
		this.usuarioDto = usuarioDto;
	}

	public String[] getIdAplicaciones() {
		return idAplicaciones;
	}

	public void setIdAplicaciones(String[] idAplicaciones) {
		this.idAplicaciones = idAplicaciones;
	}

	public String[] getPermisosAplicaciones() {
		return permisosAplicaciones;
	}

	public void setPermisosAplicaciones(String[] permisosAplicaciones) {
		this.permisosAplicaciones = permisosAplicaciones;
	}

	public String[] getPermisosUsuarios() {
		return permisosUsuarios;
	}

	public void setPermisosUsuarios(String[] permisosUsuarios) {
		this.permisosUsuarios = permisosUsuarios;
	}

	public String getCodAplicacionActiva() {
		return codAplicacionActiva;
	}

	public void setCodAplicacionActiva(String codAplicacionActiva) {
		this.codAplicacionActiva = codAplicacionActiva;
	}

	public String[] getIdUsuarios() {
		return idUsuarios;
	}

	public void setIdUsuarios(String[] idUsuarios) {
		this.idUsuarios = idUsuarios;
	}

	public UsuarioDto getUsuarioNuevoDto() {
		return usuarioNuevoDto;
	}

	public void setUsuarioNuevoDto(UsuarioDto usuarioNuevoDto) {
		this.usuarioNuevoDto = usuarioNuevoDto;
	}

	public String[] getIdUsuariosGestoria() {
		return idUsuariosGestoria;
	}

	public void setIdUsuariosGestoria(String[] idUsuariosGestoria) {
		this.idUsuariosGestoria = idUsuariosGestoria;
	}

	public String[] getIdUsuariosContrato() {
		return idUsuariosContrato;
	}

	public void setIdUsuariosContrato(String[] idUsuariosContrato) {
		this.idUsuariosContrato = idUsuariosContrato;
	}

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloEvolucionUsuarioPaginatedImpl;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargaRestricciones() {
		if (contratoUsuarioFilter.getCif() != null && !contratoUsuarioFilter.getCif().isEmpty()) {
			contratoUsuarioFilter.setCif(contratoUsuarioFilter.getCif().toUpperCase());
		}
		if (contratoUsuarioFilter.getRazonSocial() != null && !contratoUsuarioFilter.getRazonSocial().isEmpty()) {
			contratoUsuarioFilter.setRazonSocial(contratoUsuarioFilter.getRazonSocial().toUpperCase());
		}
		if (!utilesColegiado.tienePermisoAdmin()) {
			contratoUsuarioFilter.setNumColegiado(utilesColegiado.getNumColegiadoSession());
			contratoUsuarioFilter.setIdContrato(utilesColegiado.getIdContratoSessionBigDecimal().toString());
		}
	}

	@Override
	protected void cargarFiltroInicial() {
		if (contratoUsuarioFilter == null) {
			contratoUsuarioFilter = new ContratoUsuarioFilterBean();
			contratoUsuarioFilter.setIdContrato(utilesColegiado.getIdContratoSessionBigDecimal().toString());
		}
		if (!utilesColegiado.tienePermisoAdmin()) {
			contratoUsuarioFilter.setNumColegiado(utilesColegiado.getNumColegiadoSession());
			// contratoUsuarioFilter.setEstadoUsuarioContrato(new String(1));
			contratoUsuarioFilter.setIdContrato(utilesColegiado.getIdContratoSessionBigDecimal().toString());
		}
	}

	@Override
	protected Object getBeanCriterios() {
		return contratoUsuarioFilter;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		contratoUsuarioFilter = (ContratoUsuarioFilterBean) object;
	}

	@Override
	protected String[] getListInitializedOnePath() {
		return fetchList;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	/**
	 * @return the passCorpme
	 */
	public String getPassCorpme() {
		return passCorpme;
	}

	/**
	 * @param passCorpme the passCorpme to set
	 */
	public void setPassCorpme(String passCorpme) {
		this.passCorpme = passCorpme;
	}

	public String getIdCorreoContratoTramite() {
		return idCorreoContratoTramite;
	}

	public void setIdCorreoContratoTramite(String idCorreoContratoTramite) {
		this.idCorreoContratoTramite = idCorreoContratoTramite;
	}

}
