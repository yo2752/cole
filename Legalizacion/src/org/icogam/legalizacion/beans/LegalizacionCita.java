package org.icogam.legalizacion.beans;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the LEGALIZACION_CITAS database table.
 * 
 */
@Entity
@Table(name="LEGALIZACION_CITAS")
public class LegalizacionCita implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_PETICION")
	@GeneratedValue(strategy = GenerationType.AUTO, generator="SEC_LEGALIZACION")
	@SequenceGenerator(name="SEC_LEGALIZACION", sequenceName="LEGALIZACION_CITAS_SEQ")
	private Integer idPeticion;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;
	
	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_LEGALIZACION")
	private Date fechaLegalizacion;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_DOCUMENTO")
	private Date fechaDocumento;
	
	@Column(name="FICHERO_ADJUNTO")
	private int ficheroAdjunto;
	
	private String nombre;

	@Column(name="NUM_COLEGIADO")
	private String numColegiado;

	private String referencia;

	private int solicitado;

	@Column(name="TIPO_DOCUMENTO")
	private String tipoDocumento;

	private Integer estado;
	
	@Column(name="ESTADO_PETICION")
	private Integer estadoPeticion;
	
	private Integer orden;
	
	@Column(name="CLASE_DOCUMENTO")
	private String claseDocumento;
	
	@Column(name="PAIS")
	private String pais;
	
	public LegalizacionCita() {
	}

	public Integer getIdPeticion() {
		return this.idPeticion;
	}

	public void setIdPeticion(Integer idPeticion) {
		this.idPeticion = idPeticion;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Date getFechaDocumento() {
		return fechaDocumento;
	}

	public void setFechaDocumento(Date fechaDocumento) {
		this.fechaDocumento = fechaDocumento;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNumColegiado() {
		return this.numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getReferencia() {
		return this.referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public int getFicheroAdjunto() {
		return ficheroAdjunto;
	}

	public void setFicheroAdjunto(int ficheroAdjunto) {
		this.ficheroAdjunto = ficheroAdjunto;
	}

	public int getSolicitado() {
		return solicitado;
	}

	public void setSolicitado(int solicitado) {
		this.solicitado = solicitado;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public Date getFechaLegalizacion() {
		return fechaLegalizacion;
	}

	public void setFechaLegalizacion(Date fechaLegalizacion) {
		this.fechaLegalizacion = fechaLegalizacion;
	}

	public Integer getEstadoPeticion() {
		return estadoPeticion;
	}

	public void setEstadoPeticion(Integer estadoPeticion) {
		this.estadoPeticion = estadoPeticion;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public String getClaseDocumento() {
		return claseDocumento;
	}

	public void setClaseDocumento(String claseDocumento) {
		this.claseDocumento = claseDocumento;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

}