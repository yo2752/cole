package org.gestoresmadrid.oegam2comun.EmpresaDIRe.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;

import utilidades.estructuras.Fecha;

public class EmpresaDIReDto implements Serializable {

	private static final long serialVersionUID = 1830178671813328107L;

	// Datos básicos sacados de la documentacion

	private String codigoDire;
	private String nif;
	private String paisNif;
	private Fecha fechaCreacion;
	private Fecha fechaActualizacion;
	private String codigoDirePadre;
	private String estado;
	private String direccion;
	private String codigoPostal;
	private String ciudad;
	private String provincia;
	private String pais;
	private String email;
	private String telefono;
	private String nombre;

	// Datos para la gestion del contrato

	private BigDecimal numExpediente;
	private ContratoDto contrato;
	private String numColegiado;
	private String operacion;
	private BigDecimal idContrato;

	public BigDecimal getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(BigDecimal idContrato) {
		this.idContrato = idContrato;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public ContratoDto getContrato() {
		return contrato;
	}

	public void setContrato(ContratoDto contrato) {
		this.contrato = contrato;
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

	public Fecha getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Fecha fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Fecha getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Fecha fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public String getCodigoDirePadre() {
		return codigoDirePadre;
	}

	public void setCodigoDirePadre(String codigoDirePadre) {
		this.codigoDirePadre = codigoDirePadre;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
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