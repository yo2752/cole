package escrituras.utiles.enumerados;

import trafico.beans.jaxb.matriculacion.TipoSexo;

public enum SexoPersona2 {

	Varon("V", "V") {
		public String toString() {
			return "V";
		}
	},
	Hembra("H", "H") {
		public String toString() {
			return "H";
		}
	},
	Juridica("X", "X") {
		public String toString() {
			return "X";
		}
	};

	public static TipoSexo convertirAformatoGA(String param) {
		if (param.equals(SexoPersona2.Hembra.getValorEnum())) {
			return TipoSexo.H;
		}
		if (param.equals(SexoPersona2.Juridica.getValorEnum())) {
			return TipoSexo.X;
		}
		if (param.equals(SexoPersona2.Varon.getValorEnum())) {
			return TipoSexo.V;
		} else {
			return null;
		}
	}

	private String valorEnum;
	private String nombreEnum;

	private SexoPersona2(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

}