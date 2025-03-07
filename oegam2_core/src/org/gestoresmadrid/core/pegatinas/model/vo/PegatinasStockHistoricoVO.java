package org.gestoresmadrid.core.pegatinas.model.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "PEGATINAS_STOCK_HISTORICO")
public class PegatinasStockHistoricoVO implements Serializable {

	private static final long serialVersionUID = -2958855025276048714L;

	public PegatinasStockHistoricoVO() {
	}

	@Id
	@Column(name = "ID_HISTORICO")
	@SequenceGenerator(name = "secuencia_pegatinahist_stock", sequenceName = "PEGATINAS_STOCKHIST_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "secuencia_pegatinahist_stock")
	private Integer idHistorico;

	@Column(name = "ID_STOCK")
	private Integer idStock;
	
	@Column(name = "ACCION")
	private String accion;
	
	@Column(name = "STOCK")
	private Integer stock;
	
	@Column(name = "FECHA")
	private Date fecha;
	
	@Column(name = "TIPO")
	private String tipo;
	
	@Column(name = "MATRICULA")
	private String matricula;

	public Integer getIdHistorico() {
		return idHistorico;
	}

	public void setIdHistorico(Integer idHistorico) {
		this.idHistorico = idHistorico;
	}

	public Integer getIdStock() {
		return idStock;
	}

	public void setIdStock(Integer idStock) {
		this.idStock = idStock;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

}