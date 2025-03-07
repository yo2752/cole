package org.gestoresmadrid.oegam2comun.EmpresaDIRe.view.beans;

import java.math.BigDecimal;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;

import utilidades.estructuras.FechaFraccionada;

public class ConsultaEmpresaDIReFilter {

	@FilterSimpleExpression(name = "fechaCreacion", restriction = FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaIni;
	@FilterSimpleExpression(name = "fechaActualizacion", restriction = FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaFin;
	@FilterSimpleExpression(name = "nombre")
	private String nombre;
	@FilterSimpleExpression(name = "codigoDire")
	private String codigoDire;
	@FilterSimpleExpression(name = "nif")
	private String nif;
	@FilterSimpleExpression(name = "paisNif")
	private String paisNif;
	@FilterSimpleExpression(name = "codigoDirePadre")
	private String codigoDirePadre;

	@FilterSimpleExpression(name = "direccion")
	private String direccion;
	@FilterSimpleExpression(name = "codigoPostal")
	private String codigoPostal;
	@FilterSimpleExpression(name = "ciudad")
	private String ciudad;
	@FilterSimpleExpression(name = "provincia")
	private String provincia;
	@FilterSimpleExpression(name = "pais")
	private String pais;
	@FilterSimpleExpression(name = "email")
	private String email;
	@FilterSimpleExpression(name = "telefono")
	private String telefono;
	@FilterSimpleExpression(name = "numExpediente")
	private BigDecimal numExpediente;
	@FilterSimpleExpression(name = "idContrato")
	private Long idContrato;
	@FilterSimpleExpression(name = "operacion")
	private String operacion;
	@FilterSimpleExpression(name = "numColegiado")
	private String numColegiado;

	public FechaFraccionada getFechaIni() {
		return fechaIni;
	}

	public void setFechaIni(FechaFraccionada fechaIni) {
		this.fechaIni = fechaIni;
	}

	public FechaFraccionada getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(FechaFraccionada fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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

	public String getCodigoDirePadre() {
		return codigoDirePadre;
	}

	public void setCodigoDirePadre(String codigoDirePadre) {
		this.codigoDirePadre = codigoDirePadre;
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

}