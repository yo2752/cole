package org.gestoresmadrid.oegam2comun.registradores.enums;

/**
 * Posibles tipos de claúsula
 */
public enum TipoClausula {

	GENERAL("Cláusula general"),
	PARTICULAR("Cláusula particular");

	private String nombreEnum;

	private TipoClausula(String nombreEnum) {
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
			for(TipoClausula tipoClausula : TipoClausula.values()){
				if(tipoClausula.getKey().equals(valor)){
					return tipoClausula.toString();
				}
			}
		}
		return null;
	}

}
