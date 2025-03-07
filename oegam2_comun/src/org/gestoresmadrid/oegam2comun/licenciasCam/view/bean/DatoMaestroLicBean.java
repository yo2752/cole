package org.gestoresmadrid.oegam2comun.licenciasCam.view.bean;

import java.io.Serializable;

public class DatoMaestroLicBean implements Serializable {

	private static final long serialVersionUID = 4388816735359106266L;

	private String codigo;

	private String descripcion;

	private boolean obligatorio;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public boolean isObligatorio() {
		return obligatorio;
	}

	public void setObligatorio(boolean obligatorio) {
		this.obligatorio = obligatorio;
	}
}
