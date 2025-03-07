package org.gestoresmadrid.oegam2.estadisticas.listados.controller.action;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.displaytag.pagination.PaginatedList;
import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.core.pagination.model.component.impl.PaginatedListImpl;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.estadisticas.listados.view.beans.ListadoUsuariosIPBean;
import org.gestoresmadrid.oegam2comun.estadisticas.listados.view.beans.ListadoUsuariosOnlineIPBean;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.usuarioLogin.ServicioUsuarioLogin;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import oegam.constantes.ConstantesAplicacion;
import trafico.utiles.constantes.ConstantesEstadisticas;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ListadoUsuariosAction extends AbstractPaginatedListAction {

	private static final long serialVersionUID = -7075987876360062905L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ListadoUsuariosAction.class);

	private static final String PAG_USUARIOS_ONLINE_AGRUPADOS = "pagUsuariosOnlineAgrupados";
	private static final String PAG_USUARIOS_ONLINE_REPETIDOS = "pagUsuariosOnlineRepetidos";

	@Resource
	private ModelPagination modeloListadoUsuariosIPPaginated;

	private ListadoUsuariosIPBean listadoUsuariosIPBean;
	private ListadoUsuariosOnlineIPBean listadoUsuariosOnlineIPBean;

	private PaginatedList listaUsuariosPorIP;
	private PaginatedList listaUsuariosOnline;

	private boolean usuariosPorIP;
	private boolean usuariosOnline;

	private List<Object[]> listaFrontalesUsuarios;
	private List<Object[]> listasUsuariosRepetidos;
	private List<Object[]> listaUsuariosTotalesFrontales;

	private String password; // Password para ver estadísticas
	private String passValidado; // Password Validado para ver estadísticas. Por defecto No hay permiso.
	private String idSesionCerrar; // Password para ver estadísticas

	@Autowired
	ServicioUsuarioLogin servicioUsuarioLogin;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	UtilesColegiado utilesColegiado;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	public String login() {
		setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_FALSE);
		return SUCCESS;
	}

	public String comprobarPassword() {
		String passwordPropiedades = gestorPropiedades.valorPropertie(ConstantesEstadisticas.PASSWORD_CAMPO);

		if (listadoUsuariosIPBean != null && StringUtils.isNotBlank(passwordPropiedades) && passwordPropiedades.equals(listadoUsuariosIPBean.getPassword()) && utilesColegiado.tienePermisoAdmin()) {
			setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_TRUE);
			cargarFiltroInicial();
		} else {
			setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_ERROR);
			addActionError(ConstantesEstadisticas.PASSWORD_INCORRECTO);
		}

		return SUCCESS;
	}

	public String listarUsuariosPorIP() {
		log.debug("inicio listado de usuarios por IP");
		setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_TRUE);

		setUsuariosPorIP(Boolean.TRUE);
		setUsuariosOnline(Boolean.FALSE);

		if (listadoUsuariosIPBean.getFechaLogin().isfechaNula()) {
			addActionError("Debe indicar un rango válido de Fecha Login.");
			return SUCCESS;
		}

		String ipMasRepetida = servicioUsuarioLogin.buscaIPMasRepetida(listadoUsuariosIPBean.getNumColegiado(), listadoUsuariosIPBean.getFechaLogin());
		if (StringUtils.isNotBlank(ipMasRepetida)) {
			addActionMessage(ipMasRepetida);
		}

		super.actualizarPaginatedList();
		setListaUsuariosPorIP(getModelo().buscarPag(getBeanCriterios(), Integer.parseInt(getResultadosPorPagina()), getPage(), getDir(), getSort(), getListInitializedOnePath(),
				getListInitializedAnyPath()));

		log.debug("fin listado de usuarios por IP");
		return SUCCESS;
	}

	public String listarUsuariosOnline() {
		log.debug("inicio listado de usuarios online");
		setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_TRUE);

		setUsuariosPorIP(Boolean.FALSE);
		setUsuariosOnline(Boolean.TRUE);

		super.actualizarPaginatedList();

		setListaUsuariosOnline(getModelo().buscarPag(getBeanCriterios(), Integer.parseInt(getResultadosPorPagina()), getPage(), getDir(), getSort(), getListInitializedOnePath(),
				getListInitializedAnyPath()));

		log.debug("fin listado de usuarios online");
		return SUCCESS;
	}

	public String cerrarSesionUsuariosOnline() {
		log.debug("inicio cerrar sesión de todos los usuarios online");
		setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_TRUE);

		String idSesionActual = ServletActionContext.getRequest().getSession().getId();
		try {
			int cantidadSesionesCerradas = servicioUsuarioLogin.cerrarSesionUsuariosOnline(idSesionActual, listadoUsuariosOnlineIPBean.getNumColegiado(), listadoUsuariosOnlineIPBean.getFrontal());
			addActionMessage("Se han cerrado un total de " + cantidadSesionesCerradas + " sesiones.");
		} catch (Exception e) {
			addActionError("Se ha producido un error al cerrar la sesión de los usuarios online");
			log.error("Se ha producido un error al cerrar la sesión de los usuarios online: ", e);
		}

		setUsuariosPorIP(Boolean.FALSE);
		setUsuariosOnline(Boolean.TRUE);

		super.actualizarPaginatedList();

		setListaUsuariosOnline(getModelo().buscarPag(getBeanCriterios(), Integer.parseInt(getResultadosPorPagina()), getPage(), getDir(), getSort(), getListInitializedOnePath(),
				getListInitializedAnyPath()));

		log.debug("Fin cerrar sesión de todos los usuarios online");
		return SUCCESS;
	}

	public String cerrarSesionUsuarioOnline() {
		log.debug("inicio cerrar sesión de usuarios online");
		setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_TRUE);

		String idSesionActual = ServletActionContext.getRequest().getSession().getId();
		if (StringUtils.isBlank(getIdSesionCerrar())) {
			addActionError("El id de la sesión que se desea cerrar no puede estar vacío.");
			// Se comprueba que no se está intentando cerrar la sesión del propio usuario que ejecuta la orden.
		} else if (StringUtils.isNotBlank(idSesionActual) && idSesionActual.equalsIgnoreCase(getIdSesionCerrar())) {
			addActionError("No puede cerrar la sesión actual.");
		} else {
			try {
				servicioUsuarioLogin.cerrarSesionUsuarioOnline(getIdSesionCerrar());
				addActionMessage("Se ha cerrado la sesión con id: " + getIdSesionCerrar() + ".");
			} catch (Exception e) {
				addActionError("Se ha producido un error al cerrar la sesión indicada.");
				log.error("Se ha producido un error al cerrar la sesión indicada: ", e);
			}
		}

		setUsuariosPorIP(Boolean.FALSE);
		setUsuariosOnline(Boolean.TRUE);

		super.actualizarPaginatedList();

		setListaUsuariosOnline(getModelo().buscarPag(getBeanCriterios(), Integer.parseInt(getResultadosPorPagina()), getPage(), getDir(), getSort(), getListInitializedOnePath(),
				getListInitializedAnyPath()));

		log.debug("Fin cierre sesión de usuarios online");
		return SUCCESS;
	}

	public String listarUsuariosOnlineAgrupados() {

		log.debug("inicio listado usuarios online agrupados");

		setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_TRUE);

		// Muestra el mensaje del ActionMessage con el frontal activo y sus usuarios.
		for (int x = 0; x < getListaFrontalesUsuarios().size(); x++) {
			String message = "En el frontal " + getListaFrontalesUsuarios().get(x)[0].toString() + " hay " + getListaFrontalesUsuarios().get(x)[1].toString() + " usuarios.";
			addActionMessage(message);
		}
		// Se muestra el total de usuarios que hay en todos los frontales
		for (int i = 0; i < getListaUsuariosTotalesFrontales().size(); i++) {
			String message = "En total, en todos los frontales hay " + getListaUsuariosTotalesFrontales().get(i) + " usuarios.";
			addActionMessage(message);
		}

		log.debug("fin listado usuarios online agrupados");

		return PAG_USUARIOS_ONLINE_AGRUPADOS;
	}

	public String listarUsuariosOnlineRepetidos() {

		log.debug("inicio listado usuarios online repetidos");

		setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_TRUE);

		if (getListasUsuariosRepetidos().size() == 1 && ((Integer) getListasUsuariosRepetidos().get(0)[3]).intValue() == 1) {
			addActionError("No hay usuarios online repetidos, solo existe una sesión activa y es su conexión actual.");
			return PAG_USUARIOS_ONLINE_REPETIDOS;
		}
		
		boolean hayUsuariosRepetidos = Boolean.FALSE;

		for (int x = 0; x < getListasUsuariosRepetidos().size(); x++) {

			if (((Integer) getListasUsuariosRepetidos().get(x)[3]).intValue() > 1) {
				String message = "El usuario con numColegiado " + getListasUsuariosRepetidos().get(x)[0].toString() + " e idUsuario " + getListasUsuariosRepetidos().get(x)[1].toString() + " ("
						+ getListasUsuariosRepetidos().get(x)[2].toString() + ")" + " está conectado " + getListasUsuariosRepetidos().get(x)[3].toString() + " veces.";
				addActionMessage(message);
				hayUsuariosRepetidos = Boolean.TRUE;
			}
		}

		if (!hayUsuariosRepetidos) {
			addActionError("No hay usuarios online repetidos.");
		}

		log.debug("fin listado usuarios online repetidos");

		return PAG_USUARIOS_ONLINE_REPETIDOS;
	}

	public String navegarUsuariosPorIp() {
		setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_TRUE);
		setUsuariosPorIP(Boolean.TRUE);
		setUsuariosOnline(Boolean.FALSE);

		super.navegar();
		setListaUsuariosPorIP(getModelo().buscarPag(getListadoUsuariosIPBean(), Integer.parseInt(getResultadosPorPagina()), getPage(), getDir(), getSort(), getListInitializedOnePath(),
				getListInitializedAnyPath()));
		return SUCCESS;
	}

	public String navegarUsuariosOnline() {
		setPassValidado(ConstantesEstadisticas.PASS_VALIDADO_TRUE);
		setUsuariosPorIP(Boolean.FALSE);
		setUsuariosOnline(Boolean.TRUE);

		super.navegar();
		setListaUsuariosOnline(getModelo().buscarPag(getListadoUsuariosOnlineIPBean(), Integer.parseInt(getResultadosPorPagina()), getPage(), getDir(), getSort(), getListInitializedOnePath(),
				getListInitializedAnyPath()));
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

	// Devuelve una lista con los usuarios que hay en cada frontal activo
	public List<Object[]> getListaFrontalesUsuarios() {
		if (listaFrontalesUsuarios == null) {
			listaFrontalesUsuarios = servicioUsuarioLogin.getUsuariosFrontal();
		}
		return listaFrontalesUsuarios;
	}

	public void setListaFrontalesUsuarios(List<Object[]> listaFrontalesUsuarios) {
		this.listaFrontalesUsuarios = listaFrontalesUsuarios;
	}

	public List<Object[]> getListasUsuariosRepetidos() {
		if (listasUsuariosRepetidos == null) {
			listasUsuariosRepetidos = servicioUsuarioLogin.getUsuariosRepetidos();
		}
		return listasUsuariosRepetidos;
	}

	public void setListasUsuariosRepetidos(List<Object[]> listasUsuariosRepetidos) {
		this.listasUsuariosRepetidos = listasUsuariosRepetidos;
	}

	// Devuelve una lista con los usuarios totales en los frontales
	public List<Object[]> getListaUsuariosTotalesFrontales() {
		if (listaUsuariosTotalesFrontales == null) {
			listaUsuariosTotalesFrontales = servicioUsuarioLogin.getUsuariosTotalesFrontales();
		}
		return listaUsuariosTotalesFrontales;
	}

	public void setListaUsuariosTotalesFrontales(List<Object[]> listaUsuariosTotalesFrontales) {
		this.listaUsuariosTotalesFrontales = listaUsuariosTotalesFrontales;
	}

	@Override
	protected String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	protected ModelPagination getModelo() {
		return modeloListadoUsuariosIPPaginated;
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargaRestricciones() {
		if (listadoUsuariosIPBean == null) {
			listadoUsuariosIPBean = new ListadoUsuariosIPBean();
		}
		if (listadoUsuariosOnlineIPBean == null) {
			listadoUsuariosOnlineIPBean = new ListadoUsuariosOnlineIPBean();
		}

	}

	@Override
	protected void cargarFiltroInicial() {
		if (listadoUsuariosIPBean == null) {
			listadoUsuariosIPBean = new ListadoUsuariosIPBean();
		}
		if (listadoUsuariosOnlineIPBean == null) {
			listadoUsuariosOnlineIPBean = new ListadoUsuariosOnlineIPBean();
		}

		listadoUsuariosIPBean.setFechaLogin(utilesFecha.getFechaFracionadaActual());

		setListaUsuariosPorIP(new PaginatedListImpl(Integer.parseInt(ConstantesAplicacion.RESULTADOS_POR_PAGINA_POR_DEFECTO), getPage(), getDir(), getSort(), 0, Collections.emptyList()));
		setListaUsuariosOnline(new PaginatedListImpl(Integer.parseInt(ConstantesAplicacion.RESULTADOS_POR_PAGINA_POR_DEFECTO), getPage(), getDir(), getSort(), 0, Collections.emptyList()));
	}

	@Override
	protected Object getBeanCriterios() {
		if (usuariosOnline) {
			return listadoUsuariosOnlineIPBean;
		} else {
			return listadoUsuariosIPBean;
		}
	}

	@Override
	protected void setBeanCriterios(Object object) {
		if (usuariosOnline) {
			this.listadoUsuariosOnlineIPBean = (ListadoUsuariosOnlineIPBean) object;
		} else {
			this.listadoUsuariosIPBean = (ListadoUsuariosIPBean) object;
		}

	}

	public ListadoUsuariosIPBean getListadoUsuariosIPBean() {
		return listadoUsuariosIPBean;
	}

	public void setListadoUsuariosIPBean(ListadoUsuariosIPBean listadoUsuariosIPBean) {
		this.listadoUsuariosIPBean = listadoUsuariosIPBean;
	}

	public ListadoUsuariosOnlineIPBean getListadoUsuariosOnlineIPBean() {
		return listadoUsuariosOnlineIPBean;
	}

	public void setListadoUsuariosOnlineIPBean(ListadoUsuariosOnlineIPBean listadoUsuariosOnlineIPBean) {
		this.listadoUsuariosOnlineIPBean = listadoUsuariosOnlineIPBean;
	}

	public boolean isUsuariosPorIP() {
		return usuariosPorIP;
	}

	public void setUsuariosPorIP(boolean usuariosPorIP) {
		this.usuariosPorIP = usuariosPorIP;
	}

	public boolean isUsuariosOnline() {
		return usuariosOnline;
	}

	public void setUsuariosOnline(boolean usuariosOnline) {
		this.usuariosOnline = usuariosOnline;
	}

	public PaginatedList getListaUsuariosPorIP() {
		return listaUsuariosPorIP;
	}

	public void setListaUsuariosPorIP(PaginatedList listaUsuariosPorIP) {
		this.listaUsuariosPorIP = listaUsuariosPorIP;
	}

	public PaginatedList getListaUsuariosOnline() {
		return listaUsuariosOnline;
	}

	public void setListaUsuariosOnline(PaginatedList listaUsuariosOnline) {
		this.listaUsuariosOnline = listaUsuariosOnline;
	}

	public String getIdSesionCerrar() {
		return idSesionCerrar;
	}

	public void setIdSesionCerrar(String idSesionCerrar) {
		this.idSesionCerrar = idSesionCerrar;
	}

}