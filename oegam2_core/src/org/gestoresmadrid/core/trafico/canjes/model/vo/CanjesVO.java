package org.gestoresmadrid.core.trafico.canjes.model.vo;

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

@Entity
@Table(name = "CANJES")
public class CanjesVO implements Serializable{

	private static final long serialVersionUID = -4171159920016297548L;
	
	@Id
	@SequenceGenerator(name = "canje_secuencia", sequenceName = "ID_CANJE_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "canje_secuencia")
	@Column(name = "ID_CANJE")
	private Long idCanje;

	@Column(name="DOC_CANJE_ID")
	private String docIdCanje;
	
	@Column(name="NUM_COLEGIADO")
	private String numColegiado;
	
	@Column(name="NOMBRE_APELL")
	private String nombreApell;
	
	@Column(name="CATEGORIAS")
	private String categorias;
	
	@Column(name="DNI_NIE")
	private String dniNie;
	
	@Column(name="NUM_CARNET")
	private String numCarnet;
	
	@Column(name="PAIS")
	private String pais;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA")
	private Date fechaAlta;
	
	
	@Column(name = "FECHA_EXPEDICION")
	private String fechaExpedicion;

	@Column(name="LUGAR_EXPEDICION")
	private String lugarExpedicion;
	

	@Column(name = "FECHA_NACIMIENTO")
	private String fechaNacimiento;
	
	@Column(name="IMPRESO")
	private String impreso;

	public Long getIdCanje() {
		return idCanje;
	}

	public void setIdCanje(Long idCanje) {
		this.idCanje = idCanje;
	}

	public String getDocIdCanje() {
		return docIdCanje;
	}

	public void setDocIdCanje(String docIdCanje) {
		this.docIdCanje = docIdCanje;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public String getNombreApell() {
		return nombreApell;
	}

	public void setNombreApell(String nombreApell) {
		this.nombreApell = nombreApell;
	}

	public String getCategorias() {
		return categorias;
	}

	public void setCategorias(String categorias) {
		this.categorias = categorias;
	}

	public String getDniNie() {
		return dniNie;
	}

	public void setDniNie(String dniNie) {
		this.dniNie = dniNie;
	}

	public String getNumCarnet() {
		return numCarnet;
	}

	public void setNumCarnet(String numCarnet) {
		this.numCarnet = numCarnet;
	}

	public String getLugarExpedicion() {
		return lugarExpedicion;
	}

	public void setLugarExpedicion(String lugarExpedicion) {
		this.lugarExpedicion = lugarExpedicion;
	}

	public String getImpreso() {
		return impreso;
	}

	public void setImpreso(String impreso) {
		this.impreso = impreso;
	}

	public String getFechaExpedicion() {
		return fechaExpedicion;
	}

	public void setFechaExpedicion(String fechaExpedicion) {
		this.fechaExpedicion = fechaExpedicion;
	}

	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

}
