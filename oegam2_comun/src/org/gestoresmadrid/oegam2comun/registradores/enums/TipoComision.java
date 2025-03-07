package org.gestoresmadrid.oegam2comun.registradores.enums;

/**
 * Tipos de comisi�n
 */
public enum TipoComision {

	APE("Comisi�n Apertura"),
	MOD("Comisi�n Modificaci�n"),
	DEV("Comisi�n Devoluci�n"),
	CAN("Comisi�n Cancelaci�n Anticipada"),
	EST("Comisi�n Estudio"),
	GRD("Comisi�n por Gastos de Reclamaci�n de Posiciones Deudoras"),
	SUB("Comisi�n por Subrogaci�n"),
	ECA("Comisi�n Expedici�n copia adicional del contrato y/o certificado"),
	LRD("Comisi�n Levantamiento de Reserva Dominio");

	private String nombreEnum;

	private TipoComision(String nombreEnum) {
		this.nombreEnum = nombreEnum;
	}

	@Override
	public String toString() {
		return nombreEnum;
	}

	public String getKey() {
		return name();
	}
	
	public static String convertirTexto(String valor) {    
		if(valor != null && !valor.isEmpty()){
			for(TipoComision tipoComision : TipoComision.values()){
				if(tipoComision.getKey().equals(valor)){
					return tipoComision.toString();
				}
			}
		}
		return null;
	}

}
