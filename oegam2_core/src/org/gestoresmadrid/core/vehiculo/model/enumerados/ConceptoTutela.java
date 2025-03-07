package org.gestoresmadrid.core.vehiculo.model.enumerados;

public enum ConceptoTutela {

	Tutela("TUTELA O PATRIA POTESTAD", "TUTELA O PATRIA POTESTAD") {
		public String toString() {
			return "TUTELA O PATRIA POTESTAD";
		}
	},
	Administrador("ADMINISTRADOR", "ADMINISTRADOR") {
		public String toString() {
			return "ADMINISTRADOR";
		}
	},
	Apoderado("APODERADO", "APODERADO") {
		public String toString() {
			return "APODERADO";
		}
	},
	Representante("REPRESENTANTE", "REPRESENTANTE") {
		public String toString() {
			return "REPRESENTANTE";
		}

	};

	private String valorEnum;
	private String nombreEnum;

	private ConceptoTutela(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public static ConceptoTutela convertir(String valorEnum) {
		if (valorEnum == null)
			return null;

		if ("TUTELA O PATRIA POTESTAD".equals(valorEnum)) {
			return Tutela;
		}
		if ("ADMINISTRADOR".equals(valorEnum)) {
			return Administrador;
		}
		if ("APODERADO".equals(valorEnum)) {
			return Apoderado;
		}
		if ("REPRESENTANTE".equals(valorEnum)) {
			return Representante;
		}
		return null;
	}
	
	public static String convertirTexto(String valorEnum) {
		if (valorEnum == null)
			return null;

		if ("TUTELA O PATRIA POTESTAD".equals(valorEnum)) {
			return Tutela.getNombreEnum();
		}
		if ("ADMINISTRADOR".equals(valorEnum)) {
			return Administrador.getNombreEnum();
		}
		if ("APODERADO".equals(valorEnum)) {
			return Apoderado.getNombreEnum();
		}
		if ("REPRESENTANTE".equals(valorEnum)) {
			return Representante.getNombreEnum();
		}
		return null;
	}
}
