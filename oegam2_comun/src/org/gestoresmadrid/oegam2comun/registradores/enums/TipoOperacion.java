package org.gestoresmadrid.oegam2comun.registradores.enums;

/**
 * Posibles tipos de operaci�n
 */
public enum TipoOperacion {

	C("Contrato"),
	CA("Cancelaci�n por pago total"),
	CCI("Cancelaci�n convencional de la inscripci�n"),
	CET("Cancelaci�n embargo"),
	CJI("Cancelaci�n judicial de la inscripci�n"),
	D("Desistimiento"),
	CCD("Cancelaci�n o comunicaci�n DGT"),
	CEM("Cancelaci�n de embargo"),
	CAH("Cancelaci�n de hipoteca"),
	CAP("Cancelaci�n de prenda sin desplazamiento"),
	CAF("Cancelaci�n afecci�n");

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
