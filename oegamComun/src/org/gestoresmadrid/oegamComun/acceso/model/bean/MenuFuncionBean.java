package org.gestoresmadrid.oegamComun.acceso.model.bean;

import java.io.Serializable;

public class MenuFuncionBean implements Serializable{

	private static final long serialVersionUID = -3078616690384355598L;
	
	String codigoAplicacion; 
	String descAplicacion;
    String codigoFuncion; 
    String descFuncion; 
    String codigoFuncionPadre; 
    String url; 
    Long nivel; 
    Long orden; 
    String tipo;
	public String getCodigoAplicacion() {
		return codigoAplicacion;
	}
	public void setCodigoAplicacion(String codigoAplicacion) {
		this.codigoAplicacion = codigoAplicacion;
	}
	public String getDescAplicacion() {
		return descAplicacion;
	}
	public void setDescAplicacion(String descAplicacion) {
		this.descAplicacion = descAplicacion;
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
	public String getCodigoFuncionPadre() {
		return codigoFuncionPadre;
	}
	public void setCodigoFuncionPadre(String codigoFuncionPadre) {
		this.codigoFuncionPadre = codigoFuncionPadre;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Long getNivel() {
		return nivel;
	}
	public void setNivel(Long nivel) {
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