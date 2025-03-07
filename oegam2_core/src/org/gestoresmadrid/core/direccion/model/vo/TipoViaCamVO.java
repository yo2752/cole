package org.gestoresmadrid.core.direccion.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TIPO_VIA_CAM")
public class TipoViaCamVO implements Serializable{

	private static final long serialVersionUID = 6802914072001683000L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_TIPO_VIA_CAM")
	private String idTipoViaCam;
	
	@Column(name="NOMBRE")
	private String nombre;
	
	@Column(name="OBSOLETO")
	private String obsoleto;

	public String getIdTipoViaCam() {
		return idTipoViaCam;
	}

	public void setIdTipoViaCam(String idTipoViaCam) {
		this.idTipoViaCam = idTipoViaCam;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getObsoleto() {
		return obsoleto;
	}

	public void setObsoleto(String obsoleto) {
		this.obsoleto = obsoleto;
	}
	
	
}
