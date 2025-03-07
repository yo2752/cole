package org.gestoresmadrid.oegam2comun.datosBancariosFavoritos.view.dto;

import java.io.Serializable;

import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;

import utilidades.estructuras.Fecha;

public class DatosBancariosFavoritosDto implements Serializable{

	private static final long serialVersionUID = 6581430849396568331L;
	
	private Long idDatosBancarios;
	private String estado;
	private String formaPago;
	private ContratoDto contrato;
	private String nifPagador;
	private String descripcion;
	private Fecha fechaAlta;
	private Fecha fechaUltimaModificacion;
	private Fecha fechaBaja;
	private String entidad;
	private String oficina;
	private String dc;
	private String numeroCuentaPago;
	private String tipoDatoBancario;
	private String iban;
	private String tarjetaEntidad;
	private String tarjetaNum1;
	private String tarjetaNum2;
	private String tarjetaNum3;
	private String tarjetaNum4;
	private String tarjetaCcv;
	private String tarjetaMes;
	private String tarjetaAnio;
	private Boolean esFavorita;
	private String nombreTitular;
	private String idMunicipio;
	private String idProvincia;
	
	public Long getIdDatosBancarios() {
		return idDatosBancarios;
	}
	public void setIdDatosBancarios(Long idDatosBancarios) {
		this.idDatosBancarios = idDatosBancarios;
	}
	public String getFormaPago() {
		return formaPago;
	}
	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}
	public ContratoDto getContrato() {
		return contrato;
	}
	public void setContrato(ContratoDto contrato) {
		this.contrato = contrato;
	}
	public String getNifPagador() {
		return nifPagador;
	}
	public void setNifPagador(String nifPagador) {
		this.nifPagador = nifPagador;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Fecha getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(Fecha fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public Fecha getFechaUltimaModificacion() {
		return fechaUltimaModificacion;
	}
	public void setFechaUltimaModificacion(Fecha fechaUltimaModificacion) {
		this.fechaUltimaModificacion = fechaUltimaModificacion;
	}
	public String getEntidad() {
		return entidad;
	}
	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}
	public String getOficina() {
		return oficina;
	}
	public void setOficina(String oficina) {
		this.oficina = oficina;
	}
	public String getDc() {
		return dc;
	}
	public void setDc(String dc) {
		this.dc = dc;
	}
	public String getNumeroCuentaPago() {
		return numeroCuentaPago;
	}
	public void setNumeroCuentaPago(String numeroCuentaPago) {
		this.numeroCuentaPago = numeroCuentaPago;
	}
	public Boolean getEsFavorita() {
		return esFavorita;
	}
	public void setEsFavorita(Boolean esFavorita) {
		this.esFavorita = esFavorita;
	}
	public String getTipoDatoBancario() {
		return tipoDatoBancario;
	}
	public void setTipoDatoBancario(String tipoDatoBancario) {
		this.tipoDatoBancario = tipoDatoBancario;
	}
	public Fecha getFechaBaja() {
		return fechaBaja;
	}
	public void setFechaBaja(Fecha fechaBaja) {
		this.fechaBaja = fechaBaja;
	}
	public String getIban() {
		return iban;
	}
	public void setIban(String iban) {
		this.iban = iban;
	}
	public String getTarjetaEntidad() {
		return tarjetaEntidad;
	}
	public void setTarjetaEntidad(String tarjetaEntidad) {
		this.tarjetaEntidad = tarjetaEntidad;
	}
	public String getTarjetaNum1() {
		return tarjetaNum1;
	}
	public void setTarjetaNum1(String tarjetaNum1) {
		this.tarjetaNum1 = tarjetaNum1;
	}
	public String getTarjetaNum2() {
		return tarjetaNum2;
	}
	public void setTarjetaNum2(String tarjetaNum2) {
		this.tarjetaNum2 = tarjetaNum2;
	}
	public String getTarjetaNum3() {
		return tarjetaNum3;
	}
	public void setTarjetaNum3(String tarjetaNum3) {
		this.tarjetaNum3 = tarjetaNum3;
	}
	public String getTarjetaNum4() {
		return tarjetaNum4;
	}
	public void setTarjetaNum4(String tarjetaNum4) {
		this.tarjetaNum4 = tarjetaNum4;
	}
	public String getTarjetaCcv() {
		return tarjetaCcv;
	}
	public void setTarjetaCcv(String tarjetaCcv) {
		this.tarjetaCcv = tarjetaCcv;
	}
	public String getTarjetaMes() {
		return tarjetaMes;
	}
	public void setTarjetaMes(String tarjetaMes) {
		this.tarjetaMes = tarjetaMes;
	}
	public String getTarjetaAnio() {
		return tarjetaAnio;
	}
	public void setTarjetaAnio(String tarjetaAnio) {
		this.tarjetaAnio = tarjetaAnio;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getNombreTitular() {
		return nombreTitular;
	}
	public void setNombreTitular(String nombreTitular) {
		this.nombreTitular = nombreTitular;
	}
	public String getIdMunicipio() {
		return idMunicipio;
	}
	public void setIdMunicipio(String idMunicipio) {
		this.idMunicipio = idMunicipio;
	}
	public String getIdProvincia() {
		return idProvincia;
	}
	public void setIdProvincia(String idProvincia) {
		this.idProvincia = idProvincia;
	}
	
}
