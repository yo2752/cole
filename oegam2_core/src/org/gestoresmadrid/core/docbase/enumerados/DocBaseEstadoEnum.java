package org.gestoresmadrid.core.docbase.enumerados;

public enum DocBaseEstadoEnum {

	CREADO_DOC_LOGICO("CR", "Creado documento lógico"){
		public String toString() {
			return "CR";
		}
	},
	IMPRESO_DOC("IMP", "Impreso documento"){
		public String toString() {
			return "IMP";
		}
	},
	MODIFICAR("MOD", "Documento marcado para modificar"){
		public String toString() {
			return "MOD";
		}
	},	
	REVERTIR("REV", "Documento marcado para revertir"){
		public String toString() {
			return "REV";
		}
	},
	ERROR_IMPRESION("ERR_IMP", "Error de impresión del documento"){
		public String toString() {
			return "ERR_IMP";
		}
	},
	ERROR_SIN_TRAMITES("ERR_NO_TRAM", "Error documento sin trámites"){
		public String toString() {
			return "ERR_NO_TRAM";
		}
	}, DOC_REVERTIDO("DOC_RVT", "Documento Revertido"){
		public String toString() {
			return "DOC_RVT";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private DocBaseEstadoEnum(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}
	public String getValorEnum() {
		return valorEnum;
	}
	public void setValor(String valorEnum) {
		this.valorEnum = valorEnum;
	}
	public String getNombreEnum() {
		return nombreEnum;
	}
	public void setNombre(String nombreEnum) {
		this.nombreEnum = nombreEnum;
	}

	public static DocBaseEstadoEnum convertir(String valorEnum) {
		for (DocBaseEstadoEnum element : DocBaseEstadoEnum.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element;
			}
		}
		return null;
	}
	
	public static String convertirTexto(String valorEnum) {
		for (DocBaseEstadoEnum element : DocBaseEstadoEnum.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element.nombreEnum;
			}
		}
		return null;
	}
	
	public static String convertirNombreAValor(String nombreEnum) {
		for (DocBaseEstadoEnum element : DocBaseEstadoEnum.values()) {
			if (element.nombreEnum.equals(nombreEnum)) {
				return element.valorEnum;
			}
		}
		return null;
	}
	
	public static DocBaseEstadoEnum[] getEstadosGestionDBAmin() {
		DocBaseEstadoEnum[] estados = new DocBaseEstadoEnum[4];
		estados[0] = DocBaseEstadoEnum.CREADO_DOC_LOGICO;
		estados[1] = DocBaseEstadoEnum.DOC_REVERTIDO;
		estados[2] = DocBaseEstadoEnum.IMPRESO_DOC;
		estados[3] = DocBaseEstadoEnum.ERROR_IMPRESION;
		return estados;
	}
	public static DocBaseEstadoEnum[] getEstadosGestionDB() {
		DocBaseEstadoEnum[] estados = new DocBaseEstadoEnum[2];
		estados[0] = DocBaseEstadoEnum.CREADO_DOC_LOGICO;
		estados[1] = DocBaseEstadoEnum.IMPRESO_DOC;
		return estados;
	}

}