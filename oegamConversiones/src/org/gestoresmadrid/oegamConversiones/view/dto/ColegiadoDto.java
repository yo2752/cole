package org.gestoresmadrid.oegamConversiones.view.dto;

import java.io.Serializable;
import java.util.Date;

public class ColegiadoDto implements Serializable {

	private static final long serialVersionUID = -5809820872830712741L;

	private String numColegiado;

	private String alias;
	
	private Date fechaCaducidad;

	private UsuarioDto usuario;

	private String claveFacturacion;

	private String numColegiadoNacional;

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public UsuarioDto getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDto usuario) {
		this.usuario = usuario;
	}

	public String getClaveFacturacion() {
		return claveFacturacion;
	}

	public void setClaveFacturacion(String claveFacturacion) {
		this.claveFacturacion = claveFacturacion;
	}

	public String getNumColegiadoNacional() {
		return numColegiadoNacional;
	}

	public void setNumColegiadoNacional(String numColegiadoNacional) {
		this.numColegiadoNacional = numColegiadoNacional;
	}

	public Date getFechaCaducidad() {
		return fechaCaducidad;
	}

	public void setFechaCaducidad(Date fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}
}