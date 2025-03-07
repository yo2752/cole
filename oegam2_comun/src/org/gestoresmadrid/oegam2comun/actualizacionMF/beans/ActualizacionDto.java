package org.gestoresmadrid.oegam2comun.actualizacionMF.beans;

import java.io.Serializable;
import java.util.Date;

public class ActualizacionDto implements Serializable {

	private static final long serialVersionUID = 3362488648862082561L;

	private Long idActualizacion;

	private Date fechaAlta;

	private String estado;

	private String resultado;

	private String ficheroSubido;

	private String ficheroResultado;

	public Long getIdActualizacion() {
		return idActualizacion;
	}

	public void setIdActualizacion(Long idActualizacion) {
		this.idActualizacion = idActualizacion;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public String getFicheroSubido() {
		return ficheroSubido;
	}

	public void setFicheroSubido(String ficheroSubido) {
		this.ficheroSubido = ficheroSubido;
	}

	public String getFicheroResultado() {
		return ficheroResultado;
	}

	public void setFicheroResultado(String ficheroResultado) {
		this.ficheroResultado = ficheroResultado;
	}

}