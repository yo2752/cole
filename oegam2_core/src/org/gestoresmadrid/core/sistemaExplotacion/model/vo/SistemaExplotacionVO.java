package org.gestoresmadrid.core.sistemaExplotacion.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="SISTEMA_EXPLOTACION")
public class SistemaExplotacionVO implements Serializable{
	
	private static final long serialVersionUID = -1799228566266201179L;

	@Id
	@Column(name="SISTEMA_EXPLOTACION")
	private String idSistemaExplotacion;
	
	@Column(name="DESC_SISTEMA")
	private String descSistema;

	public String getIdSistemaExplotacion() {
		return idSistemaExplotacion;
	}

	public void setIdSistemaExplotacion(String idSistemaExplotacion) {
		this.idSistemaExplotacion = idSistemaExplotacion;
	}

	public String getDescSistema() {
		return descSistema;
	}

	public void setDescSistema(String descSistema) {
		this.descSistema = descSistema;
	}
	
	

}
