package org.gestoresmadrid.oegam2comun.trafico.eeff.view.dto;

import java.io.Serializable;
import java.util.List;

public class ResultadoConsultaEEFFDto implements Serializable {

	private static final long serialVersionUID = -463662594410640860L;

	private String tarjetaBastidor;
	private String tarjetaNive;
	private String firCif;
	private String firMarca;
	private String concesionarioComercial;
	private String custodiaActual;
	private String custodioSiguiente;
	private String estadoFinanciero;
	private String nifCliente;
	private String estadoBastidor;
	private String fechaFacturaFinal;
	private String importeFacturaFinal;
	private String numeroFacturaFinal;
	private String custodioAnterior;
	private String custodioFinal;
	private String entidadCredito;
	private Boolean datosHistoricosITV;
	private String custodioActualAnterior;
	private String custodioAnteriorAnterior;
	private String custodioFinalAnterior;
	private String custodioSiguienteAnterior;
	private String denominacioNEstadoFinancieroAnterior;
	private String nombreApellidosCliente;
	private List<String> direccionCliente;
	private String codigoError;
	private String descripcionError;

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

	public String getConcesionarioComercial() {
		return concesionarioComercial;
	}

	public void setConcesionarioComercial(String concesionarioComercial) {
		this.concesionarioComercial = concesionarioComercial;
	}

	public String getCustodiaActual() {
		return custodiaActual;
	}

	public void setCustodiaActual(String custodiaActual) {
		this.custodiaActual = custodiaActual;
	}

	public String getCustodioSiguiente() {
		return custodioSiguiente;
	}

	public void setCustodioSiguiente(String custodioSiguiente) {
		this.custodioSiguiente = custodioSiguiente;
	}

	public String getEstadoFinanciero() {
		return estadoFinanciero;
	}

	public void setEstadoFinanciero(String estadoFinanciero) {
		this.estadoFinanciero = estadoFinanciero;
	}

	public String getNifCliente() {
		return nifCliente;
	}

	public void setNifCliente(String nifCliente) {
		this.nifCliente = nifCliente;
	}

	public String getEstadoBastidor() {
		return estadoBastidor;
	}

	public void setEstadoBastidor(String estadoBastidor) {
		this.estadoBastidor = estadoBastidor;
	}

	public String getFechaFacturaFinal() {
		return fechaFacturaFinal;
	}

	public void setFechaFacturaFinal(String fechaFacturaFinal) {
		this.fechaFacturaFinal = fechaFacturaFinal;
	}

	public String getImporteFacturaFinal() {
		return importeFacturaFinal;
	}

	public void setImporteFacturaFinal(String importeFacturaFinal) {
		this.importeFacturaFinal = importeFacturaFinal;
	}

	public String getNumeroFacturaFinal() {
		return numeroFacturaFinal;
	}

	public void setNumeroFacturaFinal(String numeroFacturaFinal) {
		this.numeroFacturaFinal = numeroFacturaFinal;
	}

	public String getCustodioAnterior() {
		return custodioAnterior;
	}

	public void setCustodioAnterior(String custodioAnterior) {
		this.custodioAnterior = custodioAnterior;
	}

	public String getCustodioFinal() {
		return custodioFinal;
	}

	public void setCustodioFinal(String custodioFinal) {
		this.custodioFinal = custodioFinal;
	}

	public String getEntidadCredito() {
		return entidadCredito;
	}

	public void setEntidadCredito(String entidadCredito) {
		this.entidadCredito = entidadCredito;
	}

	public Boolean getDatosHistoricosITV() {
		return datosHistoricosITV;
	}

	public void setDatosHistoricosITV(Boolean datosHistoricosITV) {
		this.datosHistoricosITV = datosHistoricosITV;
	}

	public String getCustodioActualAnterior() {
		return custodioActualAnterior;
	}

	public void setCustodioActualAnterior(String custodioActualAnterior) {
		this.custodioActualAnterior = custodioActualAnterior;
	}

	public String getCustodioAnteriorAnterior() {
		return custodioAnteriorAnterior;
	}

	public void setCustodioAnteriorAnterior(String custodioAnteriorAnterior) {
		this.custodioAnteriorAnterior = custodioAnteriorAnterior;
	}

	public String getCustodioFinalAnterior() {
		return custodioFinalAnterior;
	}

	public void setCustodioFinalAnterior(String custodioFinalAnterior) {
		this.custodioFinalAnterior = custodioFinalAnterior;
	}

	public String getCustodioSiguienteAnterior() {
		return custodioSiguienteAnterior;
	}

	public void setCustodioSiguienteAnterior(String custodioSiguienteAnterior) {
		this.custodioSiguienteAnterior = custodioSiguienteAnterior;
	}

	public String getDenominacioNEstadoFinancieroAnterior() {
		return denominacioNEstadoFinancieroAnterior;
	}

	public void setDenominacioNEstadoFinancieroAnterior(String denominacioNEstadoFinancieroAnterior) {
		this.denominacioNEstadoFinancieroAnterior = denominacioNEstadoFinancieroAnterior;
	}

	public String getNombreApellidosCliente() {
		return nombreApellidosCliente;
	}

	public void setNombreApellidosCliente(String nombreApellidosCliente) {
		this.nombreApellidosCliente = nombreApellidosCliente;
	}

	public List<String> getDireccionCliente() {
		return direccionCliente;
	}

	public void setDireccionCliente(List<String> direccionCliente) {
		this.direccionCliente = direccionCliente;
	}

	public String getCodigoError() {
		return codigoError;
	}

	public void setCodigoError(String codigoError) {
		this.codigoError = codigoError;
	}

	public String getDescripcionError() {
		return descripcionError;
	}

	public void setDescripcionError(String descripcionError) {
		this.descripcionError = descripcionError;
	}

}