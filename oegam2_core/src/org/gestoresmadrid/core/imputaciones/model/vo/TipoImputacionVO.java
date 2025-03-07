package org.gestoresmadrid.core.imputaciones.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name="TIPO_IMPUTACION")
public class TipoImputacionVO implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "tipo_imputacion_secuencia", sequenceName = "ID_TIPO_IMPUTACION_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "tipo_imputacion_secuencia")
	@Column(name="ID_TIPO_IMPUTACION")
	private Long idTipoImputacion;
	
	@Column(name="DESC_CORTA")
	private String descCorta;
	
	@Column(name="DESCRIPCION")
	private String descripcion;

	public Long getIdTipoImputacion() {
		return idTipoImputacion;
	}

	public void setIdTipoImputacion(Long idTipoImputacion) {
		this.idTipoImputacion = idTipoImputacion;
	}

	public String getDescCorta() {
		return descCorta;
	}

	public void setDescCorta(String descCorta) {
		this.descCorta = descCorta;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	

}
