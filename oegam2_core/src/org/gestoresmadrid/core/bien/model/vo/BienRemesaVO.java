package org.gestoresmadrid.core.bien.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.gestoresmadrid.core.remesa.model.vo.RemesaVO;

@Entity
@Table(name="REMESA_BIEN")
public class BienRemesaVO implements Serializable{

	private static final long serialVersionUID = 4853250313459126950L;

	@EmbeddedId
	private BienRemesasPK id;
	
	@Column(name="VALOR_DECLARADO")
	private BigDecimal valorDeclarado;
	
	@Column(name="TRANSMISION")
	private BigDecimal transmision;
	
	@Column(name="VALOR_TASACION")
	private BigDecimal valorTasacion;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ID_REMESA",insertable=false,updatable=false)
	private RemesaVO remesa;
	
	@ManyToOne
	@JoinColumn(name="ID_BIEN",insertable=false,updatable=false)
	private BienVO bien;

	public BienRemesasPK getId() {
		return id;
	}

	public void setId(BienRemesasPK id) {
		this.id = id;
	}

	public BigDecimal getValorDeclarado() {
		return valorDeclarado;
	}

	public void setValorDeclarado(BigDecimal valorDeclarado) {
		this.valorDeclarado = valorDeclarado;
	}

	public BigDecimal getTransmision() {
		return transmision;
	}

	public void setTransmision(BigDecimal transmision) {
		this.transmision = transmision;
	}

	public RemesaVO getRemesa() {
		return remesa;
	}

	public void setRemesa(RemesaVO remesa) {
		this.remesa = remesa;
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
