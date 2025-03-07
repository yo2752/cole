package org.gestoresmadrid.oegamDocBase.enumerados;

public enum DocumentoBaseEstadoTramite {

	NO_ENVIADO("0", "No enviado") {
		public String toString() {
			return "0";
		}
	},
	ENVIADO_NO_PROCESADO("1", "Enviado, pero no procesado") {
		public String toString() {
			return "1";
		}
	},
	MARCADO_PARA_ENVIAR("2", "Marcado por un colegiado para enviar a demanda a Gestión Documental") {
		public String toString() {
			return "2";
		}
	},
	MARCADO_PARA_ANADIR("3", "No telemático, marcado por un colegiado para enviar a Yerbabuena") {
		public String toString() {
			return "3";
		}
	},
	MARCADO_PARA_ELIMINAR("4", "Marcado por un colegiado para eliminar de Gestión Documental") {
		public String toString() {
			return "4";
		}
	},
	RECIBIDO_PROCESADO("8", "Pendiente documentación") {
		public String toString() {
			return "8";
		}
	},
	DIGITALIZADO_OK("9", "Digitalizado automático por Yerbabuena") {
		public String toString() {
			return "9";
		}
	},
	DIGITALIZADO_KO("10", "Digitalizado manual por Yerbabuena") {
		public String toString() {
			return "10";
		}
	},
	INCIDENCIA_ACREDITACION("20", "Digitalizado manual con Incidencia Acreditación") {
		public String toString() {
			return "20";
		}
	},
	INCIDENCIA_DOMICILIO("21", "Digitalizado manual con Incidencia Domicilio") {
		public String toString() {
			return "21";
		}
	},
	INCIDENCIA_PERMISO("22", "Digitalizado manual con incidencia Permiso o Declaración") {
		public String toString() {
			return "22";
		}
	},
	INCIDENCIA_FICHA_TECNICA("23", "Digitalizado manual con incidencia Ficha técnica o declaración") {
		public String toString() {
			return "23";
		}
	},
	INCIDENCIA_CONTRATO("24", "Digitalizado manual con incidencia Contrato o factura") {
		public String toString() {
			return "24";
		}
	},
	INCIDENCIA_MANDATOS("25", "Digitalizado manual con incidencia Mandatos") {
		public String toString() {
			return "25";
		}
	},
	INCIDENCIA_OTROS("26", "Incidencia Otros") {
		public String toString() {
			return "26";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private DocumentoBaseEstadoTramite(String valorEnum, String nombreEnum) {
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

	public static DocumentoBaseEstadoTramite convertir(String valorEnum) {

		for (DocumentoBaseEstadoTramite element : DocumentoBaseEstadoTramite.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element;
			}
		}
		return null;

	}

}