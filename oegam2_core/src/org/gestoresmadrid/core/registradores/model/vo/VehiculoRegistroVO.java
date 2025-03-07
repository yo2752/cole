package org.gestoresmadrid.core.registradores.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the VEHICULO database table.
 */
@Entity
@Table(name = "VEHICULO_REGISTRO")
public class VehiculoRegistroVO implements Serializable {

	private static final long serialVersionUID = -8980188726160122100L;

	@Id
	@SequenceGenerator(name = "vehiculo_registro_secuencia", sequenceName = "VEHICULO_REGISTRO_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "vehiculo_registro_secuencia")
	@Column(name = "ID_VEHICULO")
	private long idVehiculo;

	@Column(name = "BASTIDOR")
	private String bastidor;
	
	@Column(name = "GRUPO")
	private BigDecimal grupo;
	
	@Column(name = "MARCA")
	private String marca;

	@Column(name = "MATRICULA")
	private String matricula;
	
	@Column(name = "MODELO")
	private String modelo;
	
	@Column(name = "NIVE")
	private String nive;

	@Column(name = "TIPO")
	private String tipo;
	
	@Column(name = "ID_PROPIEDAD")
	private BigDecimal idPropiedad;
	
	@OneToOne
	@JoinColumn(name="ID_PROPIEDAD", insertable=false, updatable=false)
	private PropiedadVO propiedad;

	public long getIdVehiculo() {
		return idVehiculo;
	}

	public void setIdVehiculo(long idVehiculo) {
		this.idVehiculo = idVehiculo;
	}

	public String getBastidor() {
		return bastidor;
	}

	public void setBastidor(String bastidor) {
		this.bastidor = bastidor;
	}

	public BigDecimal getGrupo() {
		return grupo;
	}

	public void setGrupo(BigDecimal grupo) {
		this.grupo = grupo;
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

	public String getNive() {
		return nive;
	}

	public void setNive(String nive) {
		this.nive = nive;
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

	public PropiedadVO getPropiedad() {
		return propiedad;
	}

	public void setPropiedad(PropiedadVO propiedad) {
		this.propiedad = propiedad;
	}

}