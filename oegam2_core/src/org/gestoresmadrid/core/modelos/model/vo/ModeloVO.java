package org.gestoresmadrid.core.modelos.model.vo;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="MODELO")
public class ModeloVO implements Serializable{

	private static final long serialVersionUID = -5307222198106569794L;

	@EmbeddedId
	private ModeloPK id;
	
	@Column(name="DESC_MODELO")
	private String descModelo;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(name="MODELO_FUNDAMENTO",joinColumns={@JoinColumn(name="MODELO"),@JoinColumn(name="VERSION")},inverseJoinColumns= {@JoinColumn(name="FUNDAMENTO_EXENCION")})
	private Set<FundamentoExencionVO> fundamentosExencion; 
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(name="MODELO_BONIFICACION",joinColumns={@JoinColumn(name="MODELO"),@JoinColumn(name="VERSION")},inverseJoinColumns= {@JoinColumn(name="BONIFICACION")})
	private Set<BonificacionVO> bonificaciones; 

	public ModeloPK getId() {
		return id;
	}

	public void setId(ModeloPK id) {
		this.id = id;
	}

	public String getDescModelo() {
		return descModelo;
	}

	public void setDescModelo(String descModelo) {
		this.descModelo = descModelo;
	}

	public Set<FundamentoExencionVO> getFundamentosExencion() {
		return fundamentosExencion;
	}

	public void setFundamentosExencion(Set<FundamentoExencionVO> fundamentosExencion) {
		this.fundamentosExencion = fundamentosExencion;
	}

	public Set<BonificacionVO> getBonificaciones() {
		return bonificaciones;
	}

	public void setBonificaciones(Set<BonificacionVO> bonificaciones) {
		this.bonificaciones = bonificaciones;
	}

}
