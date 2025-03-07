package com.registradoresWS.enumeraciones;

public enum ErroresWS {

	FILE_NOT_FOUND(1, "FILE_NOT_FOUND") {
		public String toString() {
			return "Error interno";
		}
	},
	IO(2, "IO") {
		public String toString() {
			return "Error interno";
		}
	},
	BASE64_DECODING(3, "BASE64_DECODING") {
		public String toString() {
			return "Error interno";
		}
	},
	JAXB(4, "JAXB") {
		public String toString() {
			return "El xml recibido no es valido segun el esquema: CORPME_eDoc.xsd";
		}
	},
	OEGAM(5, "OEGAM") {
		public String toString() {
			return "Error interno";
		}
	},
	GENERIC_EXCEPTION(6, "GENERIC_EXCEPTION") {
		public String toString() {
			return "Error interno";
		}
	},
	TRAMITE_DESCONOCIDO_EXCEPTION(7, "TRAMITE_DESCONOCIDO_EXCEPTION") {
		public String toString() {
			return "No existe un trámite con ese identificador";
		}
	},
	CAMBIO_ESTADO_ILEGAL_EXCEPTION(8, "CAMBIO_ESTADO_ILEGAL_EXCEPTION") {
		public String toString() {
			return "Mensaje fuera de secuencia";
		}
	};

	private ErroresWS(Integer valor, String nombre) {
		this.valor = valor;
		this.nombre = nombre;
	}

	private Integer valor;
	private String nombre;

	public String getNombre() {
		return nombre;
	}

	public Integer getValor() {
		return valor;
	}

	// valueOf
	public static ErroresWS convertir(Integer valor) {

		if (valor == null)
			return null;

		switch (valor) {
			case 1:
				return FILE_NOT_FOUND;

			case 2:
				return IO;

			case 3:
				return BASE64_DECODING;

			case 4:
				return JAXB;

			case 5:
				return OEGAM;

			case 6:
				return GENERIC_EXCEPTION;

			case 7:
				return TRAMITE_DESCONOCIDO_EXCEPTION;

			case 8:
				return CAMBIO_ESTADO_ILEGAL_EXCEPTION;

			default:
				return null;
		}
	}
}
