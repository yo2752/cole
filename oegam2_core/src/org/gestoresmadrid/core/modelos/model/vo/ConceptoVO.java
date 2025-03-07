package org.gestoresmadrid.core.modelos.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="CONCEPTO")
public class ConceptoVO implements Serializable{
	
	private static final long serialVersionUID = -5436311713073938224L;

	@EmbeddedId
	private ConceptoPK id;
	
	@Column(name="DESC_CONCEPTO")
	private String descConcepto;
	
	@Column(name="NINTER")
	private String ninter;
	
	@Column(name="GRUPO_VALIDACION")
	private String grupoValidacion;
	
	@Column(name="ESTADO")
	private BigDecimal estado;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="MODELO",referencedColumnName="MODELO", insertable = false, updatable = false),
		@JoinColumn(name="VERSION",referencedColumnName="VERSION",  insertable = false, updatable = false)
	})
	private ModeloVO modelo;

	public ConceptoPK getId() {
		return id;
	}

	public void setId(ConceptoPK id) {
		this.id = id;
	}

	public String getDescConcepto() {
		return descConcepto;
	}

	public void setDescConcepto(String descConcepto) {
		this.descConcepto = descConcepto;
	}

	public String getNinter() {
		return ninter;
	}

	public void setNinter(String ninter) {
		this.ninter = ninter;
	}

	public String getGrupoValidacion() {
		return grupoValidacion;
	}

	public void setGrupoValidacion(String grupoValidacion) {
		this.grupoValidacion = grupoValidacion;
	}

	public ModeloVO getModelo() {
		return modelo;
	}

	public void setModelo(ModeloVO modelo) {
		this.modelo = modelo;
	}

	public BigDecimal getEstado() {
		return estado;
	}

	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}
	
}
