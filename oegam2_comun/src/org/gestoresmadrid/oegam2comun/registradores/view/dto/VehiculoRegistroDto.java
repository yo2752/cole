package org.gestoresmadrid.oegam2comun.registradores.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class VehiculoRegistroDto implements Serializable{

	private static final long serialVersionUID = -6574241996946778772L;

	private long idVehiculo;

	private String bastidor;

	private Timestamp fecCreacion;
	
	private BigDecimal grupo;
	
	private String marca;
	
	private String matricula;
	
	private String modelo;

	private Timestamp fecModificacion;

	private String nive;

	private String tipo;

	private BigDecimal idPropiedad;

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

	public Timestamp getFecCreacion() {
		return fecCreacion;
	}

	public void setFecCreacion(Timestamp fecCreacion) {
		this.fecCreacion = fecCreacion;
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

	public Timestamp getFecModificacion() {
		return fecModificacion;
	}

	public void setFecModificacion(Timestamp fecModificacion) {
		this.fecModificacion = fecModificacion;
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


}
