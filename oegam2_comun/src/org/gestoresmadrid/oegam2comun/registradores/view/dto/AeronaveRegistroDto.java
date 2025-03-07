package org.gestoresmadrid.oegam2comun.registradores.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class AeronaveRegistroDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6619175921008276031L;

	private long idAeronave;
	private String marca;
	private String matricula;
	private String modelo;
	private String numSerie;
	private String tipo;
	private BigDecimal idPropiedad;

	public long getIdAeronave() {
		return idAeronave;
	}

	public void setIdAeronave(long idAeronave) {
		this.idAeronave = idAeronave;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getNumSerie() {
		return numSerie;
	}

	public void setNumSerie(String numSerie) {
		this.numSerie = numSerie;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public BigDecimal getIdPropiedad() {
		return idPropiedad;
	}

	public void setIdPropiedad(BigDecimal idPropiedad) {
		this.idPropiedad = idPropiedad;
	}


}
