package org.gestoresmadrid.oegam2comun.mensajeErrorServicio.views.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * The persistent class for the MENSAJE_ERROR_SERVICIO database table.
 * @author ext_fjcl
 *
 */
public class MensajeErrorServicioDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String proceso;
	private String descripcion;
	private Date fecha;
	private Long idEnvio;
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