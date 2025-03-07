package org.gestoresmadrid.core.modelo.bien.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.gestoresmadrid.core.bien.model.vo.BienVO;
import org.gestoresmadrid.core.modelo600_601.model.vo.Modelo600_601VO;

@Entity
@Table(name="MODELO_600_601_BIEN")
public class ModeloBienVO implements Serializable{
	
	private static final long serialVersionUID = -6819995882188730384L;

	@EmbeddedId
	private ModeloBienPK id;
	
	@Column(name="TRANSMISION")
	private BigDecimal transmision;
	
	@Column(name="VALOR_DECLARADO")
	private BigDecimal valorDeclarado;
	
	@Column(name="VALOR_TASACION")
	private BigDecimal valorTasacion;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ID_MODELO",insertable=false,updatable=false)
	private Modelo600_601VO modelo600_601;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ID_BIEN",insertable=false,updatable=false)
	private BienVO bien;

	public ModeloBienPK getId() {
		return id;
	}

	public void setId(ModeloBienPK id) {
		this.id = id;
	}

	public BigDecimal getTransmision() {
		return transmision;
	}

	public void setTransmision(BigDecimal transmision) {
		this.transmision = transmision;
	}

	public BigDecimal getValorDeclarado() {
		return valorDeclarado;
	}

	public void setValorDeclarado(BigDecimal valorDeclarado) {
		this.valorDeclarado = valorDeclarado;
	}

	public Modelo600_601VO getModelo600_601() {
		return modelo600_601;
	}

	public void setModelo600_601(Modelo600_601VO modelo600_601) {
		this.modelo600_601 = modelo600_601;
	}

	public BienVO getBien() {
		return bien;
	}

	public void setBien(BienVO bien) {
		this.bien = bien;
	}

	public BigDecimal getValorTasacion() {
		return valorTasacion;
	}

	public void setValorTasacion(BigDecimal valorTasacion) {
		this.valorTasacion = valorTasacion;
	}
	
}
