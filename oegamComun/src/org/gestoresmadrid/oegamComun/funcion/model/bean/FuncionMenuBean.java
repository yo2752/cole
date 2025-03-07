package org.gestoresmadrid.oegamComun.funcion.model.bean;

import java.io.Serializable;

public class FuncionMenuBean implements Serializable{

	private static final long serialVersionUID = 6013914419838447597L;
	
	String codigoAplicacion;
	String codigoFuncion;
	String descFuncion;
	String codFuncionPadre;
	String url;
	String nivel;
	Long orden;
	String tipo;
	
	public String getCodigoAplicacion() {
		return codigoAplicacion;
	}
	public void setCodigoAplicacion(String codigoAplicacion) {
		this.codigoAplicacion = codigoAplicacion;
	}
	public String getCodigoFuncion() {
		return codigoFuncion;
	}
	public void setCodigoFuncion(String codigoFuncion) {
		this.codigoFuncion = codigoFuncion;
	}
	public String getDescFuncion() {
		return descFuncion;
	}
	public void setDescFuncion(String descFuncion) {
		this.descFuncion = descFuncion;
	}
	public String getCodFuncionPadre() {
		return codFuncionPadre;
	}
	public void setCodFuncionPadre(String codFuncionPadre) {
		this.codFuncionPadre = codFuncionPadre;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getNivel() {
		return nivel;
	}
	public void setNivel(String nivel) {
		this.nivel = nivel;
	}
	public Long getOrden() {
		return orden;
	}
	public void setOrden(Long orden) {
		this.orden = orden;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	
}
