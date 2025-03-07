 package org.gestoresmadrid.presentacion.jpt.controller.action;

import org.gestoresmadrid.oegam2comun.model.service.ServicioUsuario;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import general.acciones.ActionBase;

public class JefaturaJPTAction extends ActionBase{

	private static final long serialVersionUID = 1L;

	private UsuarioDto usuario;
	private String jefaturaSeleccionada;

	@Autowired
	private ServicioUsuario servicioUsuario;

	@Autowired
	private UtilesColegiado utilesColegiado;

	public String execute() {
		usuario = servicioUsuario.getUsuarioDto(utilesColegiado.getIdUsuarioSessionBigDecimal());
		return SUCCESS;
	}

	public String cambiar() {
		if (jefaturaValida()) {
			ResultBean resultado = servicioUsuario.actualizarJefaturaJPTUsuario(utilesColegiado.getIdUsuarioSessionBigDecimal(), jefaturaSeleccionada);
			if (!resultado.getError()) {
				addActionMessage(resultado.getMensaje());
			} else {
				addActionError(resultado.getMensaje());
			}
		} else {
			addActionError("Debe elegir una jefatura a la que cambiar.");
		}
		usuario = servicioUsuario.getUsuarioDto(utilesColegiado.getIdUsuarioSessionBigDecimal());
		return SUCCESS;
	}

	private boolean jefaturaValida() {
		return (!("-1".equals(jefaturaSeleccionada)));
	}

	public UsuarioDto getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDto usuario) {
		this.usuario = usuario;
	}

	public String getJefaturaSeleccionada() {
		return jefaturaSeleccionada;
	}

	public void setJefaturaSeleccionada(String jefaturaSeleccionada) {
		this.jefaturaSeleccionada = jefaturaSeleccionada;
	}

}