package org.gestoresmadrid.oegam2comun.registradores.enums;

/**
 * Posibles tipos de pacto
 */
public enum TipoPacto {

	SAM("Seguro de Amortizaci�n de Cr�dito"),
	EDD("Exclusi�n del Derecho de Desestimiento"),
	RDO("Reserva de Dominio"),
	DCE("Derecho de Cesi�n"),
	EOC("Compromiso ejercicio de opci�n compra (Leasing)"),
	GCC("Gastos operaci�n por cuenta del cliente"),
	ERF("Exenci�n responsabilidad al financiador de desperfectos en los bienes suministrados por proveedor (Leasing)"),
	DOC("Derecho de opci�n de compra por parte del arrendatario (Renting)"),
	SSV("Seguro Sobre Veh�culo");

	private String nombreEnum;

	private TipoPacto(String nombreEnum) {
		this.nombreEnum = nombreEnum;
	}

	public String getKey() {
		return name();
	}

	@Override
	public String toString() {
		return nombreEnum;
	}
	
	public static String convertirTexto(String valor) {    
		if(valor != null && !valor.isEmpty()){
			for(TipoPacto tipoPacto : TipoPacto.values()){
				if(tipoPacto.getKey().equals(valor)){
					return tipoPacto.toString();
				}
			}
		}
		return null;
	}

}
