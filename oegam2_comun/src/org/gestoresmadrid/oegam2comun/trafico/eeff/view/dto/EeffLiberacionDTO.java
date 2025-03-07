package org.gestoresmadrid.oegam2comun.trafico.eeff.view.dto;

import java.math.BigDecimal;

import utilidades.estructuras.Fecha;

public class EeffLiberacionDTO extends EeffDTO {

	private static final long serialVersionUID = -2515912376290251654L;

	private BigDecimal numExpediente;

	private String nif;

	private String idDireccion;

	private String numFactura;

	private Fecha fechaFactura;

	private String importe;

	private String nombre;

	private String primerApellido;

	private String segundoApellido;

	private String tipoVia;

	private String nombreVia;

	private String bloque;

	private String numero;

	private String escalera;

	private String piso;

	private String puerta;

	private String provincia;

	private String municipio;

	private String codPostal;

	private boolean exento;

	private String respuestaConsulta;

	private String numExpedienteConsulta;

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getIdDireccion() {
		return idDireccion;
	}

	public void setIdDireccion(String idDireccion) {
		this.idDireccion = idDireccion;
	}

	public String getNumFactura() {
		return numFactura;
	}

	public void setNumFactura(String numFactura) {
		this.numFactura = numFactura;
	}

	public Fecha getFechaFactura() {
		return fechaFactura;
	}

	public void setFechaFactura(Fecha fechaFactura) {
		this.fechaFactura = fechaFactura;
	}

	public String getImporte() {
		return importe;
	}

	public void setImporte(String importe) {
		this.importe = importe;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPrimerApellido() {
		return primerApellido;
	}

	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	public String getSegundoApellido() {
		return segundoApellido;
	}

	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	public String getTipoVia() {
		return tipoVia;
	}

	public void setTipoVia(String tipoVia) {
		this.tipoVia = tipoVia;
	}

	public String getNombreVia() {
		return nombreVia;
	}

	public void setNombreVia(String nombreVia) {
		this.nombreVia = nombreVia;
	}

	public String getBloque() {
		return bloque;
	}

	public void setBloque(String bloque) {
		this.bloque = bloque;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getEscalera() {
		return escalera;
	}

	public void setEscalera(String escalera) {
		this.escalera = escalera;
	}

	public String getPiso() {
		return piso;
	}

	public void setPiso(String piso) {
		this.piso = piso;
	}

	public String getPuerta() {
		return puerta;
	}

	public void setPuerta(String puerta) {
		this.puerta = puerta;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getCodPostal() {
		return codPostal;
	}

	public void setCodPostal(String codPostal) {
		this.codPostal = codPostal;
	}

	public boolean isExento() {
		return exento;
	}

	public void setExento(boolean exento) {
		this.exento = exento;
	}

	public String getRespuestaConsulta() {
		return respuestaConsulta;
	}

	public void setRespuestaConsulta(String respuestaConsulta) {
		this.respuestaConsulta = respuestaConsulta;
	}

	public String getNumExpedienteConsulta() {
		return numExpedienteConsulta;
	}

	public void setNumExpedienteConsulta(String numExpedienteConsulta) {
		this.numExpedienteConsulta = numExpedienteConsulta;
	}

}
