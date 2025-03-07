package org.gestoresmadrid.core.bien.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class BienRemesasPK implements Serializable{

	private static final long serialVersionUID = 4957132322349365796L;

	@Column(name="ID_REMESA")
	private Long idRemesa;
	
	@Column(name="ID_BIEN")
	private Long idBien;

	public Long getIdRemesa() {
		return idRemesa;
	}

	public void setIdRemesa(Long idRemesa) {
		this.idRemesa = idRemesa;
	}

	public Long getIdBien() {
		return idBien;
	}

	public void setIdBien(Long idBien) {
		this.idBien = idBien;
	}
	
	
}
