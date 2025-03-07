package org.gestoresmadrid.presentacion.jpt.controller.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.gestoresmadrid.core.presentacion.jpt.model.beans.ResumenJPTBean;
import org.gestoresmadrid.oegam2comun.evolucionJPT.EvolucionPresentacionJptDTO;
import org.gestoresmadrid.oegam2comun.model.service.ServicioUsuario;
import org.gestoresmadrid.oegam2comun.presentacion.jpt.model.service.ServicioEvolucionPresentacionJpt;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;
import org.gestoresmadrid.presentacion.jpt.model.service.ServicioPresentacionJefaturaTrafico;
import org.springframework.beans.factory.annotation.Autowired;

import general.acciones.ActionBase;

public class PresentacionJPTAction extends ActionBase {
	private static final long serialVersionUID = 1L;

	private static final String CONFIRMAR = "confirmar";
	private static final String PRESENTACION = "presentacion";

	@Autowired
	ServicioPresentacionJefaturaTrafico servicio;

	@Autowired
	ServicioUsuario servicioUsuario;

	@Autowired
	ServicioEvolucionPresentacionJpt servicioEvolucion;

	@Autowired
	private UtilesColegiado utilesColegiado;

	private List<String> listadoIds;
	private List<ResumenJPTBean> resumenExpedientes;

	private UsuarioDto usuario;

	private String numExpedienteEvolucion;
	private List<EvolucionPresentacionJptDTO> listaEvolucion;

	public String execute() {
		return SUCCESS;
	}

	public String resumen() {
		if (validarID()) {
			resumenExpedientes = servicio.getResumenMultiple(listadoIds);
			if (listadoIds == null || listadoIds.isEmpty()) {
				addActionError("No se han encontrado expedientes.");
				return SUCCESS;
			}
		} else {
			return SUCCESS;
		}

		return CONFIRMAR;
	}

	public String presentacion() {
		resumenExpedientes = servicio.presentarTramitesMultiple(listadoIds, getUsuario());
		return PRESENTACION;
	}

	private Boolean validarID() {
		Boolean valido = true;
		if (listadoIds == null || listadoIds.isEmpty()) {
			addActionError("No se ha introduccido ningun documento.");
			valido = false;
		}
		if (getUsuario().getJefaturaJPT() == null || "".equals(getUsuario().getJefaturaJPT())) {
			addActionError("Debe especificar una Jefatura para continuar. Puede realizarlo desde el"
					+ "menu Presentacion JPT -> Cambio Jefatura JPT");
			valido = false;
		}
		return valido;
	}

	public String evolucion() {
		HttpServletRequest request = ServletActionContext.getRequest();
		numExpedienteEvolucion = request.getParameter("numExpediente");
		listaEvolucion = servicioEvolucion.mostrarEvolucionExpediente(numExpedienteEvolucion);
		return "evolucion";
	}

	public List<ResumenJPTBean> getResumenExpedientes() {
		return resumenExpedientes;
	}

	public void setResumenExpedientes(List<ResumenJPTBean> resumenExpedientes) {
		this.resumenExpedientes = resumenExpedientes;
	}

	public List<String> getListadoIds() {
		return listadoIds;
	}

	public void setListadoIds(List<String> listadoIds) {
		this.listadoIds = listadoIds;
	}

	public UsuarioDto getUsuario() {
		if (usuario == null)
			usuario = servicioUsuario.getUsuarioDto(utilesColegiado.getIdUsuarioSessionBigDecimal());
		return usuario;
	}

	public String getNumExpedienteEvolucion() {
		return numExpedienteEvolucion;
	}

	public void setNumExpedienteEvolucion(String numExpedienteEvolucion) {
		this.numExpedienteEvolucion = numExpedienteEvolucion;
	}

	public List<EvolucionPresentacionJptDTO> getListaEvolucion() {
		return listaEvolucion;
	}

	public void setListaEvolucion(List<EvolucionPresentacionJptDTO> listaEvolucion) {
		this.listaEvolucion = listaEvolucion;
	}

}