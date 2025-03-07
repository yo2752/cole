package org.gestoresmadrid.core.trafico.eeff.model.vo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * Clase padre para entidades financieras.
 */
@MappedSuperclass
public class EeffVO{
	
	@Column(name="NUM_COLEGIADO")
	private Integer numColegiado;
//	REALIZADO	NUMBER(1,0)
	@Column(name="REALIZADO")
	private boolean realizado;
//	FECHA_REALIZACION	DATE
	@Column(name="FECHA_REALIZACION")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaRealizacion  ;
//	TARJETA_BASTIDOR	VARCHAR2(32 BYTE)
	@Column(name="TARJETA_BASTIDOR")
	private String tarjetaBastidor;
//	TARJETA_NIVE	VARCHAR2(21 BYTE)
	@Column(name="TARJETA_NIVE")
	private String tarjetaNive;
//	FIR_CIF	VARCHAR2(10 BYTE)
	@Column(name="FIR_CIF")
	private String firCif;
//	FIR_MARCA	VARCHAR2(32 BYTE)
	@Column(name="FIR_MARCA")
	private String firMarca;
// Respuesta del webServices
	@Column(name="RESPUESTA")
	private String respuesta;
	@Column(name="NIF_REPRESENTADO")
	private String nifRepresentado;
	@Column(name="NOMBRE_REPRESENTADO")
	private String nombreRepresentado;
	
	public Integer getNumColegiado() {
		return numColegiado;
	}
	public void setNumColegiado(Integer numColegiado) {
		this.numColegiado = numColegiado;
	}
	public boolean isRealizado() {
		return realizado;
	}
	public void setRealizado(boolean realizado) {
		this.realizado = realizado;
	}
//	public Date getFechaRealizacion() {
//		return fechaRealizacion;
//	}
	public Date getFechaRealizacion() {
		return this.fechaRealizacion;
	}
	public void setFechaRealizacion(Date fechaRealizacion) {
		this.fechaRealizacion = fechaRealizacion;
	}
	public String getTarjetaBastidor() {
		return tarjetaBastidor;
	}
	public void setTarjetaBastidor(String tarjetaBastidor) {
		this.tarjetaBastidor = tarjetaBastidor;
	}
	public String getTarjetaNive() {
		return tarjetaNive;
	}
	public void setTarjetaNive(String tarjetaNive) {
		this.tarjetaNive = tarjetaNive;
	}
	public String getFirCif() {
		return firCif;
	}
	public void setFirCif(String firCif) {
		this.firCif = firCif;
	}
	public String getFirMarca() {
		return firMarca;
	}
	public void setFirMarca(String firMarca) {
		this.firMarca = firMarca;
	}
	public String getRespuesta() {
		return respuesta;
	}
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
	public String getNifRepresentado() {
		return nifRepresentado;
	}
	public void setNifRepresentado(String nifRepresentado) {
		this.nifRepresentado = nifRepresentado;
	}
	public String getNombreRepresentado() {
		return nombreRepresentado;
	}
	public void setNombreRepresentado(String nombreRepresentado) {
		this.nombreRepresentado = nombreRepresentado;
	}
	
	
}
