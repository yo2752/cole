package org.gestoresmadrid.core.modelo.bien.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ModeloBienPK implements Serializable{
	
	private static final long serialVersionUID = 7466712468143463488L;

	@Column(name="ID_MODELO")
	private Long idModelo;
	
	@Column(name="ID_BIEN")
	private Long idBien;

	public Long getIdModelo() {
		return idModelo;
	}

	public void setIdModelo(Long idModelo) {
		this.idModelo = idModelo;
	}

	public Long getIdBien() {
		return idBien;
	}

	public void setIdBien(Long idBien) {
		this.idBien = idBien;
	}
	
}
