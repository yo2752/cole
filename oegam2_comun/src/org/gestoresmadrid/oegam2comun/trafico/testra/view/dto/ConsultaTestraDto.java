package org.gestoresmadrid.oegam2comun.trafico.testra.view.dto;

import java.io.Serializable;
import java.util.Date;

public class ConsultaTestraDto implements Serializable{

	private static final long serialVersionUID = -8743670046353854003L;

	private Long id;

	private String dato;

	private String tipo;

	private Date fechaAlta;

	private Date fechaUltimaSancion;

	private String numColegiado;

	private Short activo;
	
	private String correoElectronico;
	
	private Date fechaModif;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDato() {
		return dato;
	}

	public void setDato(String dato) {
		this.dato = dato;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaUltimaSancion() {
		return fechaUltimaSancion;
	}

	public void setFechaUltimaSancion(Date fechaUltimaSancion) {
		this.fechaUltimaSancion = fechaUltimaSancion;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public Short getActivo() {
		return activo;
	}

	public void setActivo(Short activo) {
		this.activo = activo;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public Date getFechaModif() {
		return fechaModif;
	}

	public void setFechaModif(Date fechaModif) {
		this.fechaModif = fechaModif;
	}

}
