package org.gestoresmadrid.oegam2comun.atex5.view.dto;

import java.io.Serializable;

public class DatosElementoSeguridadAtex5Dto implements Serializable{

	private static final long serialVersionUID = -1631530076436617622L;
	
	private String nombre;
	private String tipo;
	private String valor;
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	
}
