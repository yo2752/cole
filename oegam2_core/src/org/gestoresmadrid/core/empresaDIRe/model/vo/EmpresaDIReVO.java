package org.gestoresmadrid.core.empresaDIRe.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="EMPRESA_DIRE")
public class EmpresaDIReVO implements Serializable {

	private static final long serialVersionUID = 5199440606227746846L;

	@Column(name = "CODIGO_DIRE")
	private String codigoDire;

	@Column(name="NIF")
	private String nif; 
	
	@Column(name="PAIS_NIF")
	private String paisNif; 
	
	@Column(name="FECHA_CREACION")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaCreacion;
	
	@Column(name="FECHA_ACTUALIZACION")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaActualizacion;
	
	@Column(name="CODIGO_DIRE_PADRE")
	private String codigoDirePadre; 
	
	@Column(name="ESTADO")
	private BigDecimal estado; 
	
	@Column(name="DIRECCION")
	private String direccion; 
	
	@Column(name="CODIGO_POSTAL")
	private String codigoPostal; 
	
	@Column(name="CIUDAD")
	private String ciudad; 
	
	@Column(name="PROVINCIA")
	private String provincia; 
	
	@Column(name="PAIS")
	private String pais; 
	
	@Column(name="EMAIL")
	private String email; 
	
	@Column(name="TELEFONO")
	private String telefono;

	@Id
	@Column(name="NUM_EXPEDIENTE")
	private BigDecimal numExpediente;	
	
	@Column(name="ID_CONTRATO")
	private BigDecimal idContrato;
	

	@Column(name="OPERACION")
	private String operacion;
	
	
	@Column(name="NUM_COLEGIADO")
	private String numColegiado;
	
	
	@Column(name="NOMBRE")
	private String nombre;

	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public BigDecimal getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(BigDecimal idContrato) {
		this.idContrato = idContrato;
	}

	public String getOperacion() {
		return operacion;
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getCodigoDire() {
		return codigoDire;
	}

	public void setCodigoDire(String codigoDire) {
		this.codigoDire = codigoDire;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getPaisNif() {
		return paisNif;
	}

	public void setPaisNif(String paisNif) {
		this.paisNif = paisNif;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public String getCodigoDirePadre() {
		return codigoDirePadre;
	}

	public void setCodigoDirePadre(String codigoDirePadre) {
		this.codigoDirePadre = codigoDirePadre;
	}

	public BigDecimal getEstado() {
		return estado;
	}

	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	} 
	

}
