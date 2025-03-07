package org.gestoresmadrid.core.trafico.materiales.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "GSM_COLEGIO")
public class ColegioConsejoVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4094734832468044744L;

	@Id
	@Column(name = "COLEGIO_ID")
	private Long   colegioId;
	
	@Column(name = "COLEGIO_DES")
	private String colegioDes;

	public Long getColegioId() {
		return colegioId;
	}

	public void setColegioId(Long colegioId) {
		this.colegioId = colegioId;
	}

	public String getColegioDes() {
		return colegioDes;
	}

	public void setColegioDes(String colegioDes) {
		this.colegioDes = colegioDes;
	}
	
}
