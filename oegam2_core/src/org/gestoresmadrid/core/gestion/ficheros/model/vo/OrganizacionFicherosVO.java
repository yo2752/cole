package org.gestoresmadrid.core.gestion.ficheros.model.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the ORGANIZACION_FICHEROS database table.
 * 
 * @author ext_ihgl
 * 
 */
@Entity
@Table(name="ORGANIZACION_FICHEROS")
@NamedQuery(name="OrganizacionFicherosVO.findAll", query="SELECT g FROM OrganizacionFicherosVO g")
public class OrganizacionFicherosVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="organizacion_ficheros_seq_gen")
	@SequenceGenerator(name="organizacion_ficheros_seq_gen", sequenceName="ORGANIZACION_FICHEROS_SEQ")
	private Long id;
	
	@Column(name="TIPO_DOCUMENTO")
	private String tipoDocumento;
	
	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_FIN")
	private Date fechaFin;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_INICIO")
	private Date fechaInicio;

	@Column(name="NODO")
	private String nodo;
	
	@Column(name="CARPETA")
	private String carpeta;

	public OrganizacionFicherosVO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getNodo() {
		return nodo;
	}

	public void setNodo(String nodo) {
		this.nodo = nodo;
	}

	public String getCarpeta() {
		return carpeta;
	}

	public void setCarpeta(String carpeta) {
		this.carpeta = carpeta;
	}

}