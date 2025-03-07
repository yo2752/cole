package org.gestoresmadrid.core.mensajeErrorServicio.model.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the MENSAJE_ERROR_SERVICIO database table.
 * @author ext_fjcl
 *
 */
@Entity
@Table(name = "MENSAJE_ERROR_SERVICIO")
public class MensajeErrorServicioVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="SEC_ID_MENSAJE_ERR_SERVICIO")
	@SequenceGenerator(name="SEC_ID_MENSAJE_ERR_SERVICIO", sequenceName="ID_MENSAJE_ERR_SERVICIO_SEQ")
	@Column(name = "ID")
	private Long id;

	@Column(name = "PROCESO")
	private String proceso;

	@Column(name = "DESCRIPCION")
	private String descripcion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA")
	private Date fecha;

	@Column(name = "ID_ENVIO")
	private Long idEnvio;

	@Column(name = "COLA")
	private String cola;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the proceso
	 */
	public String getProceso() {
		return proceso;
	}

	/**
	 * @param proceso the proceso to set
	 */
	public void setProceso(String proceso) {
		this.proceso = proceso;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the idEnvio
	 */
	public Long getIdEnvio() {
		return idEnvio;
	}

	/**
	 * @param idEnvio the idEnvio to set
	 */
	public void setIdEnvio(Long idEnvio) {
		this.idEnvio = idEnvio;
	}

	/**
	 * @return the cola
	 */
	public String getCola() {
		return cola;
	}

	/**
	 * @param cola the cola to set
	 */
	public void setCola(String cola) {
		this.cola = cola;
	}


}