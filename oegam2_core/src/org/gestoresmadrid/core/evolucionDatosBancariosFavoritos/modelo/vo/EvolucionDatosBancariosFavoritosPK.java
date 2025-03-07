package org.gestoresmadrid.core.evolucionDatosBancariosFavoritos.modelo.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class EvolucionDatosBancariosFavoritosPK implements Serializable{

	private static final long serialVersionUID = 3808090895419621750L;

	@Column(name = "ID_DATOS_BANCARIOS")
	private Long idDatosBancarios;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="FECHA_CAMBIO")
	private Date fechaCambio;
	
	@Column(name = "ID_USUARIO")
	private Long idUsuario;

	public Long getIdDatosBancarios() {
		return idDatosBancarios;
	}

	public void setIdDatosBancarios(Long idDatosBancarios) {
		this.idDatosBancarios = idDatosBancarios;
	}

	public Date getFechaCambio() {
		return fechaCambio;
	}

	public void setFechaCambio(Date fechaCambio) {
		this.fechaCambio = fechaCambio;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	
}
