package org.gestoresmadrid.oegam2comun.tasas.view.beans;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ResumenCompraBean {

	private List<TasasSolicitadasBean> listaCompraTasasCirculacion;
	private List<TasasSolicitadasBean> listaCompraTasasConduccion;
	private List<TasasSolicitadasBean> listaCompraTasasFormacionReconocimiento;
	private List<TasasSolicitadasBean> listaCompraTasasOtrasTarifas;

	private List<TasasSolicitadasBean> listaResumenCompraBean;

	private String entidad;
	private String oficina;
	private String dc;
	private String formaPago;
	private String numeroCuentaPago;
	private BigDecimal importeTotalTasas;
	private Long idCompra;
	private BigDecimal estado;
	private Date fechaAlta;
	private Date fechaCompra;
	private String nrc;
	private String nAutoliquidacion;
	private String numJustificante791;
	private Long idContrato;
	private String cifContrato;
	private String aliasColegiado;
	private String apellidosColegiado;
	private String datosBancarios;
	private String csv;
	private String refPropia;

	private String tarjetaEntidad;
	private String tarjetaNum1;
	private String tarjetaNum2;
	private String tarjetaNum3;
	private String tarjetaNum4;
	private String tarjetaCcv;
	private String tarjetaMes;
	private String tarjetaAnio;

	private String iban;

	private String respuesta;

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

	public String getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}

	public String getNumeroCuentaPago() {
		return numeroCuentaPago;
	}

	public void setNumeroCuentaPago(String numeroCuentaPago) {
		this.numeroCuentaPago = numeroCuentaPago;
	}

	public BigDecimal getImporteTotalTasas() {
		return importeTotalTasas;
	}

	public void setImporteTotalTasas(BigDecimal importeTotalTasas) {
		this.importeTotalTasas = importeTotalTasas;
	}

	public Long getIdCompra() {
		return idCompra;
	}

	public void setIdCompra(Long idCompra) {
		this.idCompra = idCompra;
	}

	public BigDecimal getEstado() {
		return estado;
	}

	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaCompra() {
		return fechaCompra;
	}

	public void setFechaCompra(Date fechaCompra) {
		this.fechaCompra = fechaCompra;
	}

	public List<TasasSolicitadasBean> getListaCompraTasasCirculacion() {
		return listaCompraTasasCirculacion;
	}

	public void setListaCompraTasasCirculacion(List<TasasSolicitadasBean> listaCompraTasasCirculacion) {
		this.listaCompraTasasCirculacion = listaCompraTasasCirculacion;
	}

	public List<TasasSolicitadasBean> getListaCompraTasasConduccion() {
		return listaCompraTasasConduccion;
	}

	public void setListaCompraTasasConduccion(List<TasasSolicitadasBean> listaCompraTasasConduccion) {
		this.listaCompraTasasConduccion = listaCompraTasasConduccion;
	}

	public List<TasasSolicitadasBean> getListaCompraTasasFormacionReconocimiento() {
		return listaCompraTasasFormacionReconocimiento;
	}

	public void setListaCompraTasasFormacionReconocimiento(List<TasasSolicitadasBean> listaCompraTasasFormacionReconocimiento) {
		this.listaCompraTasasFormacionReconocimiento = listaCompraTasasFormacionReconocimiento;
	}

	public List<TasasSolicitadasBean> getListaCompraTasasOtrasTarifas() {
		return listaCompraTasasOtrasTarifas;
	}

	public void setListaCompraTasasOtrasTarifas(List<TasasSolicitadasBean> listaCompraTasasOtrasTarifas) {
		this.listaCompraTasasOtrasTarifas = listaCompraTasasOtrasTarifas;
	}

	public List<TasasSolicitadasBean> getListaResumenCompraBean() {
		return listaResumenCompraBean;
	}

	public void setListaResumenCompraBean(List<TasasSolicitadasBean> listaResumenCompraBean) {
		this.listaResumenCompraBean = listaResumenCompraBean;
	}

	public String getNrc() {
		return nrc;
	}

	public void setNrc(String nrc) {
		this.nrc = nrc;
	}

	public String getNAutoliquidacion() {
		return nAutoliquidacion;
	}

	public void setNAutoliquidacion(String nAutoliquidacion) {
		this.nAutoliquidacion = nAutoliquidacion;
	}

	public String getNumJustificante791() {
		return numJustificante791;
	}

	public void setNumJustificante791(String numJustificante791) {
		this.numJustificante791 = numJustificante791;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public String getCifContrato() {
		return cifContrato;
	}

	public void setCifContrato(String cifContrato) {
		this.cifContrato = cifContrato;
	}

	public String getAliasColegiado() {
		return aliasColegiado;
	}

	public void setAliasColegiado(String aliasColegiado) {
		this.aliasColegiado = aliasColegiado;
	}

	public String getApellidosColegiado() {
		return apellidosColegiado;
	}

	public void setApellidosColegiado(String apellidosColegiado) {
		this.apellidosColegiado = apellidosColegiado;
	}

	public String getDatosBancarios() {
		return datosBancarios;
	}

	public void setDatosBancarios(String datosBancarios) {
		this.datosBancarios = datosBancarios;
	}

	public String getCsv() {
		return csv;
	}

	public void setCsv(String csv) {
		this.csv = csv;
	}

	public String getRefPropia() {
		return refPropia;
	}

	public void setRefPropia(String refPropia) {
		this.refPropia = refPropia;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
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

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

}
