package org.gestoresmadrid.core.modelos.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="TIPO_IMPUESTO")
public class TipoImpuestoVO implements Serializable{
	
	private static final long serialVersionUID = -7032769405099540891L;

	@Id
	@Column(name="TIPO_IMPUESTO")
	private String tipoImpuesto;
	
	@Column(name="MONTO")
	private BigDecimal monto;
	
	@Column(name="FUNDAMENTO_EXENCION")
	private String fundamentoExencion;
	
	@Column(name="DESCRIPCION")
	private String descripcion;
	
	@Column(name="SUJETO_EXENTO")
	private String sujetoExento;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="MODELO", insertable = false, updatable = false),
		@JoinColumn(name="VERSION", insertable = false, updatable = false)
	})
	private ModeloVO modelo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="MODELO",referencedColumnName="MODELO",insertable=false,updatable=false),
		@JoinColumn(name="VERSION",referencedColumnName="VERSION",insertable=false,updatable=false),
		@JoinColumn(name="CONCEPTO",referencedColumnName="CONCEPTO",insertable=false,updatable=false)
	})
	private ConceptoVO concepto;

	public String getTipoImpuesto() {
		return tipoImpuesto;
	}

	public void setTipoImpuesto(String tipoImpuesto) {
		this.tipoImpuesto = tipoImpuesto;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public String getFundamentoExencion() {
		return fundamentoExencion;
	}

	public void setFundamentoExencion(String fundamentoExencion) {
		this.fundamentoExencion = fundamentoExencion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getSujetoExento() {
		return sujetoExento;
	}

	public void setSujetoExento(String sujetoExento) {
		this.sujetoExento = sujetoExento;
	}

	public ModeloVO getModelo() {
		return modelo;
	}

	public void setModelo(ModeloVO modelo) {
		this.modelo = modelo;
	}

	public ConceptoVO getConcepto() {
		return concepto;
	}

	public void setConcepto(ConceptoVO concepto) {
		this.concepto = concepto;
	}
	
}
