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
@Table(name = "PEGATINAS_STOCK_PETICIONES")
public class PegatinasStockPeticionesVO implements Serializable {

	private static final long serialVersionUID = -2958855025276048714L;

	public PegatinasStockPeticionesVO() {
	}

	@Id
	@Column(name = "ID_PETICION")
	@SequenceGenerator(name = "secuencia_pegatinapeti_stock", sequenceName = "PEGATINAS_STOCKPETI_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "secuencia_pegatinapeti_stock")
	private Integer idPeticion;

	@Column(name = "ID_STOCK")
	private Integer idStock;
	
	@Column(name = "ESTADO")
	private Integer estado;
	
	@Column(name = "DESCR_ESTADO")
	private String descrEstado;
	
	@Column(name = "TIPO")
	private String tipo;
	
	@Column(name = "IDENTIFICADOR")
	private String identificador;
	
	@Column(name = "NUM_PEGATINAS")
	private Integer numPegatinas;
	
	@Column(name = "FECHA")
	private Date fecha;

	public Integer getIdPeticion() {
		return idPeticion;
	}

	public void setIdPeticion(Integer idPeticion) {
		this.idPeticion = idPeticion;
	}

	public Integer getIdStock() {
		return idStock;
	}

	public void setIdStock(Integer idStock) {
		this.idStock = idStock;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public String getDescrEstado() {
		return descrEstado;
	}

	public void setDescrEstado(String descrEstado) {
		this.descrEstado = descrEstado;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public Integer getNumPegatinas() {
		return numPegatinas;
	}

	public void setNumPegatinas(Integer numPegatinas) {
		this.numPegatinas = numPegatinas;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
}