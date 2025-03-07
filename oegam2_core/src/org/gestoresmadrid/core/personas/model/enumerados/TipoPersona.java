package org.gestoresmadrid.core.personas.model.enumerados;

public enum TipoPersona {

	Fisica("FISICA", "Física", "1", 0) {
		public String toString() {
			return "FISICA";
		}
	},
	Juridica("JURIDICA", "Jurídica", "2", 2) {
		public String toString() {
			return "JURIDICA";
		}
	},
	Juridica_Publica("JURIDICA_PUBLICA", "Jurídica pública", "3", 1) {
		public String toString() {
			return "JURIDICA";
		}
	};

	private String valorEnum;
	private String nombreEnum;
	private String valorXML;
	private int valorPersona;

	private TipoPersona(String valorEnum, String nombreEnum, String valorXML, int valorPersona) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
		this.valorXML = valorXML;
		this.valorPersona = valorPersona;
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

	public static TipoPersona convertir(String valorEnum) {
		for (TipoPersona tipoPersona : TipoPersona.values()) {
			if (tipoPersona.getValorEnum().equals(valorEnum)) {
				return tipoPersona;
			}
		}
		return null;
	}

	public int getValorPersona() {
		return valorPersona;
	}

	public void setValorPersona(int valorPersona) {
		this.valorPersona = valorPersona;
	}

	public void setValorEnum(String valorEnum) {
		this.valorEnum = valorEnum;
	}

	public void setNombreEnum(String nombreEnum) {
		this.nombreEnum = nombreEnum;
	}
}