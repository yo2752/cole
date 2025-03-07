package org.gestoresmadrid.oegam2comun.registradores.enums;

/**
 * Tipos de comisión
 */
public enum DocumentType {

	DPR("DPR"),
	APR("APR"),
	DAPR("DAPR"),
	ARA("ARA"),
	ARDA("ARDA"),
	RITDR("RITDR"),
	RDEFDR("RDEFDR"),
	ARIT("ARIT"),
	ARIP("ARIP"),
	ARID("ARID"),
	FDR("FDR"),
	ACK_RECEIVED("ACK_RECEIVED"),
	INVOICE("INVOICE"),
	OTHER("OTHER");

	private String nombreEnum;

	private DocumentType(String nombreEnum) {
		this.nombreEnum = nombreEnum;
	}

	@Override
	public String toString() {
		return nombreEnum;
	}
	
	public String getKey(){
		return name();
	}

}
