package org.gestoresmadrid.oegam2comun.registradores.enums;

/**
 * Tipos de comisión
 */
public enum TipoComision {

	APE("Comisión Apertura"),
	MOD("Comisión Modificación"),
	DEV("Comisión Devolución"),
	CAN("Comisión Cancelación Anticipada"),
	EST("Comisión Estudio"),
	GRD("Comisión por Gastos de Reclamación de Posiciones Deudoras"),
	SUB("Comisión por Subrogación"),
	ECA("Comisión Expedición copia adicional del contrato y/o certificado"),
	LRD("Comisión Levantamiento de Reserva Dominio");

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
