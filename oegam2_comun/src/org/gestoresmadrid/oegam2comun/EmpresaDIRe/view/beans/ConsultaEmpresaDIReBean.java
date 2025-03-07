package org.gestoresmadrid.oegam2comun.EmpresaDIRe.view.beans;

import java.math.BigDecimal;
import java.util.Date;

public class ConsultaEmpresaDIReBean {

	private String codigoDire;
	private String nif; 
	private String paisNif;
	private Date fechaCreacion;
	private Date fechaActualizacion;
	private String codigoDirePadre;
	private BigDecimal estado;
	private String direccion;
	private String codigoPostal;
	private String ciudad;
	private String provincia;
	private String pais;
	private String email;
	private String telefono;
	private BigDecimal numExpediente;
	private Long idContrato;
	private String operacion;
	private String numColegiado;
	private String nombre;
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
	public BigDecimal getNumExpediente() {
		return numExpediente;
	}
	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}
	public Long getIdContrato() {
		return idContrato;
	}
	public void setIdContrato(Long idContrato) {
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
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}