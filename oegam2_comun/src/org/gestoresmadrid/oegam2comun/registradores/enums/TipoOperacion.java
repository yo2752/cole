package org.gestoresmadrid.oegam2comun.registradores.enums;

/**
 * Posibles tipos de operación
 */
public enum TipoOperacion {

	C("Contrato"),
	CA("Cancelación por pago total"),
	CCI("Cancelación convencional de la inscripción"),
	CET("Cancelación embargo"),
	CJI("Cancelación judicial de la inscripción"),
	D("Desistimiento"),
	CCD("Cancelación o comunicación DGT"),
	CEM("Cancelación de embargo"),
	CAH("Cancelación de hipoteca"),
	CAP("Cancelación de prenda sin desplazamiento"),
	CAF("Cancelación afección");

	private String nombreEnum;

	private TipoOperacion(String nombreEnum) {
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
			for(TipoOperacion tipoOperacion : TipoOperacion.values()){
				if(tipoOperacion.getKey().equals(valor)){
					return tipoOperacion.toString();
				}
			}
		}
		return null;
	}

}
