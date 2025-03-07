package org.gestoresmadrid.oegam2comun.registradores.enums;

/**
 * Posibles estados de facturación del expediente
 */
public enum InvoicingStatus {

	NONE("NONE"),
	INVOICE_RECEIVED("INVOICE_RECEIVED"),
	CLOSED("CLOSED");

	private String nombreEnum;

	private InvoicingStatus(String nombreEnum) {
		this.nombreEnum = nombreEnum;
	}

	@Override
	public String toString() {
		return nombreEnum;
	}

}
