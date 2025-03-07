package org.gestoresmadrid.oegamConversiones.view.dto;

import java.io.Serializable;

public class MenuDto implements Serializable {

	private static final long serialVersionUID = 4123290650723156330L;

	private String codigoAplicacion;

	private String codigoFuncion;

	private String descFuncion;

	private String codFuncionPadre;
	
	private Long nivel;
	
	private Long orden;
	
	private String tipo;
	
	private String url;

	private AplicacionDto aplicacionDto;

	private boolean asignada;
	
	private boolean esPadre;

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

	public AplicacionDto getAplicacionDto() {
		return aplicacionDto;
	}

	public void setAplicacionDto(AplicacionDto aplicacionDto) {
		this.aplicacionDto = aplicacionDto;
	}

	public boolean isAsignada() {
		return asignada;
	}

	public void setAsignada(boolean asignada) {
		this.asignada = asignada;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isEsPadre() {
		return esPadre;
	}

	public void setEsPadre(boolean esPadre) {
		this.esPadre = esPadre;
	}
}