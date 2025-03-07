package org.gestoresmadrid.oegam2comun.registradores.enums;

/**
 * Posibles tipos de contrato
 */
public enum TipoContrato {

	B1("Financiación a comprador con reserva de dominio"),
	B2("Financiación a comprador con prohibición de disponer"),
	L1("Leasing con opción de compra"),
	L2("Leasing"),
	R1("Renting con opción de compra"),
	R2("Renting");

	private String nombreEnum;

	private TipoContrato(String nombreEnum) {
		this.nombreEnum = nombreEnum;
	}

	@Override
	public String toString() {
		return nombreEnum;
	}
	
	public String getKey(){
		return name();
	}
	
	public static String convertirTexto(String valor) {    
		if(valor != null && !valor.isEmpty()){
			for(TipoContrato tipoContrato : TipoContrato.values()){
				if(tipoContrato.getKey().equals(valor)){
					return tipoContrato.toString();
				}
			}
		}
		return null;
	}

}
