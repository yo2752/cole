package org.gestoresmadrid.oegam2comun.trafico.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class DatosCardMatwDto implements Serializable {

	private static final long serialVersionUID = -7768610698424097337L;

	// -- Datos del trámite de matriculación
	private boolean hasForm05;

	private boolean hasForm06;

	private boolean hasForm576;

	private BigDecimal itvCardNew;

	private String form05Key;

	private String form06ExcemptionType;

	private String form06ExcemptionValue;

	// -- Datos del vehículo
	private String serialNumber;

	// JRG Añadido nuevo campo para MateCarton: 0003630
	private String serialCardITV;

	// -- Datos del colegiado y del colegio
	private String agentFiscalId;

	private String externalSystemFiscalId;

	public boolean isHasForm05() {
		return hasForm05;
	}

	public void setHasForm05(boolean hasForm05) {
		this.hasForm05 = hasForm05;
	}

	public boolean isHasForm06() {
		return hasForm06;
	}

	public void setHasForm06(boolean hasForm06) {
		this.hasForm06 = hasForm06;
	}

	public boolean isHasForm576() {
		return hasForm576;
	}

	public void setHasForm576(boolean hasForm576) {
		this.hasForm576 = hasForm576;
	}

	public BigDecimal getItvCardNew() {
		return itvCardNew;
	}

	public void setItvCardNew(BigDecimal itvCardNew) {
		this.itvCardNew = itvCardNew;
	}

	public String getForm05Key() {
		return form05Key;
	}

	public void setForm05Key(String form05Key) {
		this.form05Key = form05Key;
	}

	public String getForm06ExcemptionType() {
		return form06ExcemptionType;
	}

	public void setForm06ExcemptionType(String form06ExcemptionType) {
		this.form06ExcemptionType = form06ExcemptionType;
	}

	public String getForm06ExcemptionValue() {
		return form06ExcemptionValue;
	}

	public void setForm06ExcemptionValue(String form06ExcemptionValue) {
		this.form06ExcemptionValue = form06ExcemptionValue;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getSerialCardITV() {
		return serialCardITV;
	}

	public void setSerialCardITV(String serialCardITV) {
		this.serialCardITV = serialCardITV;
	}

	public String getAgentFiscalId() {
		return agentFiscalId;
	}

	public void setAgentFiscalId(String agentFiscalId) {
		this.agentFiscalId = agentFiscalId;
	}

	public String getExternalSystemFiscalId() {
		return externalSystemFiscalId;
	}

	public void setExternalSystemFiscalId(String externalSystemFiscalId) {
		this.externalSystemFiscalId = externalSystemFiscalId;
	}
}