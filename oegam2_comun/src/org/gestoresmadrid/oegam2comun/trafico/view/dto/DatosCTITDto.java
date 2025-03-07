package org.gestoresmadrid.oegam2comun.trafico.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import trafico.beans.schemas.generated.transTelematica.TipoConsentimiento;

public class DatosCTITDto implements Serializable {

	private static final long serialVersionUID = 6095980928557727328L;

	// -- Datos del trámite de transmisión
	private String customDossierNumber;

	// -- Datos del colegiado y del colegio
	private String agencyFiscalId;

	private String externalSystemFiscalID;

	private String pasos;

	private String numColegiado;

	private TipoConsentimiento consentimiento;

	private BigDecimal plazas;

	private String idServicio;

	private String pesoMma;

	private String tara;

	private String sellerINECode;

	private String firstMatriculationINECode;

	public BigDecimal getPlazas() {
		return plazas;
	}

	public void setPlazas(BigDecimal plazas) {
		this.plazas = plazas;
	}

	public String getIdServicio() {
		return idServicio;
	}

	public void setIdServicio(String idServicio) {
		this.idServicio = idServicio;
	}

	public String getPesoMma() {
		return pesoMma;
	}

	public void setPesoMma(String pesoMma) {
		this.pesoMma = pesoMma;
	}

	public String getTara() {
		return tara;
	}

	public void setTara(String tara) {
		this.tara = tara;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public TipoConsentimiento getConsentimiento() {
		return consentimiento;
	}

	public void setConsentimiento(String consentimiento) {
		this.consentimiento = TipoConsentimiento.fromValue(consentimiento);
	}

	public String getPasos() {
		return pasos;
	}

	public void setPasos(String pasos) {
		this.pasos = pasos;
	}

	public String getCustomDossierNumber() {
		return customDossierNumber;
	}

	public void setCustomDossierNumber(String customDossierNumber) {
		this.customDossierNumber = customDossierNumber;
	}

	public String getAgencyFiscalId() {
		return agencyFiscalId;
	}

	public void setAgencyFiscalId(String agencyFiscalId) {
		this.agencyFiscalId = agencyFiscalId;
	}

	public String getExternalSystemFiscalID() {
		return externalSystemFiscalID;
	}

	public void setExternalSystemFiscalID(String externalSystemFiscalID) {
		this.externalSystemFiscalID = externalSystemFiscalID;
	}

	public String getSellerINECode() {
		return sellerINECode;
	}

	public void setSellerINECode(String sellerINECode) {
		this.sellerINECode = sellerINECode;
	}

	public String getFirstMatriculationINECode() {
		return firstMatriculationINECode;
	}

	public void setFirstMatriculationINECode(String firstMatriculationINECode) {
		this.firstMatriculationINECode = firstMatriculationINECode;
	}
}