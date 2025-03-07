package trafico.beans;

import utilidades.estructuras.Fecha;

public class DatosCardMateBean {

	// -- Datos del tramite de matriculación
	private String customDossierNumber;
	private String hasForm05;
	private String hasForm06;
	private String hasForm576;
	private String form05Key;
	private String form06ExcemptionKey;
	private String form06ExcemptionValue;

	// -- Datos del vehículo
	private String vehichleSerialNumber;
	private String vehicleKind;
	private String firstMatriculationDate;
	private String itvCardType;
	private String kmUsed;
	private String codeITV;

	// JRG. Añadido nuevo campo para MateCarton: 0003630
	private String serialCardITV;
	//*******
	// -- Datos de Intervinientes
	private Fecha fiscalRepresentantBirthDate;

	// -- Datos del colegiado y del colegio
	private String agencyFiscalId;
	private String externalSystemFiscalID;

	public String getCustomDossierNumber() {
		return customDossierNumber;
	}

	public void setCustomDossierNumber(String customDossierNumber) {
		this.customDossierNumber = customDossierNumber;
	}

	public String getHasForm05() {
		return hasForm05;
	}

	public void setHasForm05(String hasForm05) {
		this.hasForm05 = hasForm05;
	}

	public String getHasForm06() {
		return hasForm06;
	}

	public void setHasForm06(String hasForm06) {
		this.hasForm06 = hasForm06;
	}

	public String getHasForm576() {
		return hasForm576;
	}

	public void setHasForm576(String hasForm576) {
		this.hasForm576 = hasForm576;
	}

	public String getForm05Key() {
		return form05Key;
	}

	public void setForm05Key(String form05Key) {
		this.form05Key = form05Key;
	}

	public String getForm06ExcemptionKey() {
		return form06ExcemptionKey;
	}

	public void setForm06ExcemptionKey(String form06ExcemptionKey) {
		this.form06ExcemptionKey = form06ExcemptionKey;
	}

	public String getForm06ExcemptionValue() {
		return form06ExcemptionValue;
	}

	public void setForm06ExcemptionValue(String form06ExcemptionValue) {
		this.form06ExcemptionValue = form06ExcemptionValue;
	}

	public String getVehichleSerialNumber() {
		return vehichleSerialNumber;
	}

	public void setVehichleSerialNumber(String vehichleSerialNumber) {
		this.vehichleSerialNumber = vehichleSerialNumber;
	}

	public String getVehicleKind() {
		return vehicleKind;
	}

	public void setVehicleKind(String vehicleKind) {
		this.vehicleKind = vehicleKind;
	}

	public String getFirstMatriculationDate() {
		return firstMatriculationDate;
	}

	public void setFirstMatriculationDate(String firstMatriculationDate) {
		this.firstMatriculationDate = firstMatriculationDate;
	}

	public String getItvCardType() {
		return itvCardType;
	}

	public void setItvCardType(String itvCardType) {
		this.itvCardType = itvCardType;
	}

	public String getKmUsed() {
		return kmUsed;
	}

	public void setKmUsed(String kmUsed) {
		this.kmUsed = kmUsed;
	}

	public Fecha getFiscalRepresentantBirthDate() {
		return fiscalRepresentantBirthDate;
	}

	public void setFiscalRepresentantBirthDate(Fecha fiscalRepresentantBirthDate) {
		this.fiscalRepresentantBirthDate = fiscalRepresentantBirthDate;
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
	
	public String getCodeITV() {
		return codeITV;
	}

	public void setCodeITV(String codeITV) {
		this.codeITV = codeITV;
	}

	public String getSerialCardITV() {
		return serialCardITV;
	}

	public void setSerialCardITV(String serialCardITV) {
		this.serialCardITV = serialCardITV;
	}

}