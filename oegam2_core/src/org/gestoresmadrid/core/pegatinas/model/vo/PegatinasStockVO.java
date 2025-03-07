package org.gestoresmadrid.core.pegatinas.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "PEGATINAS_STOCK")
public class PegatinasStockVO implements Serializable {

	private static final long serialVersionUID = -2958855025276048714L;

	public PegatinasStockVO() {
	}

	@Id
	@Column(name = "ID_STOCK")
	@SequenceGenerator(name = "secuencia_pegatinas_stock", sequenceName = "PEGATINAS_STOCK_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "secuencia_pegatinas_stock")
	private Integer idStock;

	@Column(name = "JEFATURA")
	private String jefatura;
	
	@Column(name = "TIPO")
	private String tipo;
	
	@Column(name = "STOCK")
	private Integer stock;
	
	public Integer getIdStock() {
		return idStock;
	}

	public void setIdStock(Integer idStock) {
		this.idStock = idStock;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public String getJefatura() {
		return jefatura;
	}

	public void setJefatura(String jefatura) {
		this.jefatura = jefatura;
	}
	
}