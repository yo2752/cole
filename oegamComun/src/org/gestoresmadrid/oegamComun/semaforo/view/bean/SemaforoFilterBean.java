package org.gestoresmadrid.oegamComun.semaforo.view.bean;

import java.io.Serializable;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;

public class SemaforoFilterBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 206047494292636618L;

	@FilterSimpleExpression(name = "nodo")
	private String nodo;

	@FilterSimpleExpression(name = "proceso")
	private String proceso;

	@FilterSimpleExpression(name = "estado")
	private Integer estado;

	public String getNodo() {
		return nodo;
	}

	public void setNodo(String nodo) {
		this.nodo = nodo;
	}

	public String getProceso() {
		return proceso;
	}

	public void setProceso(String proceso) {
		this.proceso = proceso;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}
	
}
