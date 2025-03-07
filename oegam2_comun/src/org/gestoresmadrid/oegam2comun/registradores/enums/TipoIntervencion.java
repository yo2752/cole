package org.gestoresmadrid.oegam2comun.registradores.enums;

/**
 * Posibles tipos de intervención
 */
public enum TipoIntervencion {

	C("Comprador/Prestatario"),
	F("Financiador"),
	A("Avalista/Fiador"),
	V("Vendedor"),
	AR("Arrendador"),
	AO("Arrendatario");

	private String nombreEnum;

	private TipoIntervencion(String nombreEnum) {
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
			for(TipoIntervencion tipoIntervencion : TipoIntervencion.values()){
				if(tipoIntervencion.getKey().equals(valor)){
					return tipoIntervencion.toString();
				}
			}
		}
		return null;
	}

}
