package org.gestoresmadrid.core.situacion.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="SITUACION")
public class SituacionVO implements Serializable{
	
	private static final long serialVersionUID = 183385869422236686L;

	@Id
	@Column(name="SITUACION")
	private String idSituacion;
	
	@Column(name="DESC_SITUACION")
	private String descSituacion;

	public String getIdSituacion() {
		return idSituacion;
	}

	public void setIdSituacion(String idSituacion) {
		this.idSituacion = idSituacion;
	}

	public String getDescSituacion() {
		return descSituacion;
	}

	public void setDescSituacion(String descSituacion) {
		this.descSituacion = descSituacion;
	}
	
	

}
