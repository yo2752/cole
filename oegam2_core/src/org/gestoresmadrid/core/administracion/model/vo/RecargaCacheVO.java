package org.gestoresmadrid.core.administracion.model.vo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.gestoresmadrid.core.administracion.model.enumerados.EstadoRecargaCacheEnum;
import org.gestoresmadrid.core.administracion.model.enumerados.TipoRecargaCacheEnum;


@Entity
@Table(name="RECARGA_CACHE")
public class RecargaCacheVO {

	@Id
	@SequenceGenerator(name = "recarga_cache_secuencia", sequenceName = "ID_PETICION_RECARGA_CACHE_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "recarga_cache_secuencia")
	@Column(name="ID_PETICION")
	private Integer idPeticion;
	
	@Column(name="IP")
	private String IP;
	
	@Column(name="FECHA_PETICION")
	private Date fechaPeticion;
	
	@Enumerated(EnumType.STRING)
	@Column(name="TIPO")
	private TipoRecargaCacheEnum tipo;
	
	@Column(name="FECHA_RECARGA")
	private Date fechaRecarga;
	
	@Enumerated(EnumType.STRING)
	@Column(name="ESTADO")
	private EstadoRecargaCacheEnum estado;

	@Column(name="ERROR")
	private String error;
	
	
	
	
	public Integer getIdPeticion() {
		return idPeticion;
	}

	public void setIdPeticion(Integer idPeticion) {
		this.idPeticion = idPeticion;
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}

	public Date getFechaPeticion() {
		return fechaPeticion;
	}

	public void setFechaPeticion(Date fechaPeticion) {
		this.fechaPeticion = fechaPeticion;
	}

	public TipoRecargaCacheEnum getTipo() {
		return tipo;
	}

	public void setTipo(TipoRecargaCacheEnum tipo) {
		this.tipo = tipo;
	}

	public Date getFechaRecarga() {
		return fechaRecarga;
	}

	public void setFechaRecarga(Date fechaRecarga) {
		this.fechaRecarga = fechaRecarga;
	}

	public EstadoRecargaCacheEnum getEstado() {
		return estado;
	}

	public void setEstado(EstadoRecargaCacheEnum estado) {
		this.estado = estado;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	
	
	
	
}
