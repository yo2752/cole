package org.gestoresmadrid.core.registradores.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;


/**
 * The persistent class for the AERONAVE_REGISTRO database table.
 * 
 */
@Entity
@Table(name="AERONAVE_REGISTRO")
public class AeronaveRegistroVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1220964990164994903L;

	@Id
	@SequenceGenerator(name = "aero_registro_secuencia", sequenceName = "AERO_REGISTRO_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "aero_registro_secuencia")
	@Column(name="ID_AERONAVE")
	private long idAeronave;

	private String marca;

	private String matricula;

	private String modelo;

	@Column(name="NUM_SERIE")
	private String numSerie;

	private String tipo;

	@Column(name = "ID_PROPIEDAD")
	private BigDecimal idPropiedad;
	
	@ManyToOne
	@JoinColumn(name="ID_PROPIEDAD", insertable=false, updatable=false)
	private PropiedadVO propiedad;

	public AeronaveRegistroVO() {
	}

	public long getIdAeronave() {
		return this.idAeronave;
	}

	public void setIdAeronave(long idAeronave) {
		this.idAeronave = idAeronave;
	}

	public String getMarca() {
		return this.marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getMatricula() {
		return this.matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getModelo() {
		return this.modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getNumSerie() {
		return this.numSerie;
	}

	public void setNumSerie(String numSerie) {
		this.numSerie = numSerie;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public PropiedadVO getPropiedad() {
		return this.propiedad;
	}

	public void setPropiedad(PropiedadVO propiedad) {
		this.propiedad = propiedad;
	}

	public BigDecimal getIdPropiedad() {
		return idPropiedad;
	}

	public void setIdPropiedad(BigDecimal idPropiedad) {
		this.idPropiedad = idPropiedad;
	}

}