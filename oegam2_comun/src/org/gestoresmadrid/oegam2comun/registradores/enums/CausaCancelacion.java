package org.gestoresmadrid.oegam2comun.registradores.enums;

/**
 * Posibles tipos de contrato
 */
public enum CausaCancelacion {

	CD("Condonaci�n deuda"),
	CA("Ejercicio anticipado de opci�n de compra por el arrendatario financiero"),
	CO("Ejercicio ordinario de opci�n de compra por el arrendatario financiero"),
	PA("Pago anticipado"),
	PO("Pago ordinario"),
	RE("Renuncia"),
	EV("Entrega voluntaria del bien"),
	RC("Recuperaci�n del bien");


	private String nombreEnum;

	private CausaCancelacion(String nombreEnum) {
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
			for(CausaCancelacion causaCancelacion : CausaCancelacion.values()){
				if(causaCancelacion.getKey().equals(valor)){
					return causaCancelacion.toString();
				}
			}
		}
		return null;
	}

}
