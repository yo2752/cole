package org.gestoresmadrid.core.registradores.model.enumerados;

/**
 * Posibles tipos de persona CORPME
 */
public enum TipoPersonaRegistro {

	Fisica("FISICA", "Física", "F") {
		public String toString() {
			return "FISICA";
		}
	},
	Juridica("JURIDICA", "Jurídica", "J") {
		public String toString() {
			return "JURIDICA";
		}
	},
	Extranjero("EXTRANJERO", "Extranjero", "E") {
		public String toString() {
			return "EXTRANJERO";
		}
	},
	Compania_Publica("COMPANIA_PUBLICA", "Compañía Pública", "P") {
		public String toString() {
			return "COMPAÑIA PUBLICA";
		}
	};


	private String valorEnum;
	private String nombreEnum;
	private String valorXML;

	private TipoPersonaRegistro(String valorEnum, String nombreEnum, String valorXML) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
		this.valorXML = valorXML;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public String getValorXML() {
		return valorXML;
	}

	public void setValorXML(String valorXML) {
		this.valorXML = valorXML;
	}

	// valueOf
	public static String convertirTexto(String valor) {    
		if(valor != null && !valor.isEmpty()){
			for(TipoPersonaRegistro tipoPersonaRegistro : TipoPersonaRegistro.values()){
				if(tipoPersonaRegistro.getValorEnum().equals(valor)){
					return tipoPersonaRegistro.getNombreEnum();
				}
			}
		}
		return null;
	}

	// valueOf
	public static String convertirTextoDesdeXML(String valor) {    
		if(valor != null && !valor.isEmpty()){
			for(TipoPersonaRegistro tipoPersonaRegistro : TipoPersonaRegistro.values()){
				if(tipoPersonaRegistro.getValorXML().equals(valor)){
					return tipoPersonaRegistro.getNombreEnum();
				}
			}
		}
		return null;
	}

	public static String convertirValorXml(String valor) {    
		if(valor != null && !valor.isEmpty()){
			for(TipoPersonaRegistro tipoPersonaRegistro : TipoPersonaRegistro.values()){
				if(tipoPersonaRegistro.getValorEnum().equals(valor)){
					return tipoPersonaRegistro.getValorXML();
				}
			}
		}
		return null;
	}

}
