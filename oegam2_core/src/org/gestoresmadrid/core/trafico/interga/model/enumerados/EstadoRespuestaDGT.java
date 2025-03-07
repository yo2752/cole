package org.gestoresmadrid.core.trafico.interga.model.enumerados;

public enum EstadoRespuestaDGT {

	Pendiente("ESP", "Pendiente") {
		public String toString() {
			return "ESP";
		}
	},
	Enviado("OK", "Enviado") {
		public String toString() {
			return "OK";
		}
	},
	Tramitado("END", "Tramitado") {
		public String toString() {
			return "END";
		}
	},
	Con_Errores_Resolucion("END_ERR", "Con errores en la resolución") {
		public String toString() {
			return "END_ERR";
		}
	},
	Errores_DGT("ERR_DGT", "No fue procesado por DGT, debe enviarse un nuevo") {
		public String toString() {
			return "ERR_DGT";
		}
	},
	Finalizado("CLO", "Finalizado") {
		public String toString() {
			return "CLO";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	private EstadoRespuestaDGT(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public static EstadoRespuestaDGT convertir(String valorEnum) {
		for (EstadoRespuestaDGT element : EstadoRespuestaDGT.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element;
			}
		}
		return null;
	}

	public static String convertirTexto(String valorEnum) {
		for (EstadoRespuestaDGT element : EstadoRespuestaDGT.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element.nombreEnum;
			}
		}
		return null;
	}
}
