package org.gestoresmadrid.core.modelos.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="FUNDAMENTO_EXENCION")
public class FundamentoExencionVO implements Serializable{
	
	private static final long serialVersionUID = -7320203161185023476L;

	@Id
	@Column(name="FUNDAMENTO_EXENCION")
	private String fundamentoExcencion;
	
	@Column(name="DESC_FUNDAMENTO_EXE")
	private String descripcion;
	
	public String getFundamentoExcencion() {
		return fundamentoExcencion;
	}

	public void setFundamentoExcencion(String fundamentoExcencion) {
		this.fundamentoExcencion = fundamentoExcencion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
}
