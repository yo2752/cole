package org.gestoresmadrid.core.importacionFichero.model.enumerados;

public enum TipoImportacion {

	XML_MATW("XML_MATW", "XML_MATW") {
		public String toString() {
			return "XML_MATW";
		}
	},
	XML_CTIT("XML_CTIT", "XML_CTIT") {
		public String toString() {
			return "XML_CTIT";
		}
	},
	XML_BAJA("XML_BAJA", "XML_BAJA") {
		public String toString() {
			return "XML_BAJA";
		}
	},
	XML_DUPLICADO("XML_DUPLICADO", "XML_DUPLICADO") {
		public String toString() {
			return "XML_DUPLICADO";
		}
	},
	XML_SOLICITUDES("SOLICITUDES", "SOLICITUDES") {
		public String toString() {
			return "SOLICITUDES";
		}
	},
	XML_CET("XML_CET", "XML_CET") {
		public String toString() {
			return "XML_CET";
		}
	},
	IMPORT_PEGATINAS("IMPORT_PEGATINAS", "IMPORT_PEGATINAS") {
		public String toString() {
			return "IMPORT_PEGATINAS";
		}
	},
	DUPLICADOS_DSTV("TXT_DUPLICADOS_DSTV", "TXT_DUPLICADOS_DSTV") {
		public String toString() {
			return "TXT_DUPLICADOS_DSTV";
		}
	},
	XLS_DUPLICADO("XLS_DUPLICADO", "XLS_DUPLICADO") {
		public String toString() {
			return "XLS_DUPLICADO";
		}
	},
	TXT_TASAS("TXT_TASAS", "TXT_TASAS") {
		public String toString() {
			return "TXT_TASAS";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private TipoImportacion(String valorEnum, String nombreEnum) {
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
