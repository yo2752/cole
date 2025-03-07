package org.gestoresmadrid.oegam2comun.registradores.enums;

/**
 * Posibles tipos de pacto
 */
public enum TipoPacto {

	SAM("Seguro de Amortización de Crédito"),
	EDD("Exclusión del Derecho de Desestimiento"),
	RDO("Reserva de Dominio"),
	DCE("Derecho de Cesión"),
	EOC("Compromiso ejercicio de opción compra (Leasing)"),
	GCC("Gastos operación por cuenta del cliente"),
	ERF("Exención responsabilidad al financiador de desperfectos en los bienes suministrados por proveedor (Leasing)"),
	DOC("Derecho de opción de compra por parte del arrendatario (Renting)"),
	SSV("Seguro Sobre Vehículo");

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
