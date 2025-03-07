package org.gestoresmadrid.core.evolucionConsultaDev.model.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class EvolucionConsultaDevPK implements Serializable{

	private static final long serialVersionUID = -6687236599673152501L;
	
	@Column(name="ID_CONSULTA_DEV")
	private Long idConsultaDev;
	
	@Column(name="FECHA_CAMBIO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaCambio;
	
	@Column(name="ID_USUARIO")
	private Long idUsuario;

	public Long getIdConsultaDev() {
		return idConsultaDev;
	}

	public void setIdConsultaDev(Long idConsultaDev) {
		this.idConsultaDev = idConsultaDev;
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
