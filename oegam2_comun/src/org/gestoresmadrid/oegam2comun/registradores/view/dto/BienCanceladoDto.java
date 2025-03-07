package org.gestoresmadrid.oegam2comun.registradores.view.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class BienCanceladoDto implements Serializable{

	private static final long serialVersionUID = 8953056892822879573L;
	private long idBienCancelado;

	private String bastidor;

	private String clase;

	private Timestamp fecCreacion;

	private Timestamp fecModificacion;

	private String marca;

	private String matricula;

	private String modelo;

	private String numeroFabricacion;

	public long getIdBienCancelado() {
		return idBienCancelado;
	}

	public void setIdBienCancelado(long idBienCancelado) {
		this.idBienCancelado = idBienCancelado;
	}

	public String getBastidor() {
		return bastidor;
	}

	public void setBastidor(String bastidor) {
		this.bastidor = bastidor;
	}

	public String getClase() {
		return clase;
	}

	public void setClase(String clase) {
		this.clase = clase;
	}

	public Timestamp getFecCreacion() {
		return fecCreacion;
	}

	public void setFecCreacion(Timestamp fecCreacion) {
		this.fecCreacion = fecCreacion;
	}

	public Timestamp getFecModificacion() {
		return fecModificacion;
	}

	public void setFecModificacion(Timestamp fecModificacion) {
		this.fecModificacion = fecModificacion;
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

	public String getNumeroFabricacion() {
		return numeroFabricacion;
	}

	public void setNumeroFabricacion(String numeroFabricacion) {
		this.numeroFabricacion = numeroFabricacion;
	}

}
