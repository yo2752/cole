package org.gestoresmadrid.core.trafico.interga.model.enumerados;

import java.util.ArrayList;
import java.util.List;

public enum EstadoTramitesInterga {

	Iniciado("1", "Iniciado") {
		public String toString() {
			return "1";
		}
	},
	Validado("2", "Validado") {
		public String toString() {
			return "2";
		}
	},
	Pendiente_Envio_DGT("3", "Pdt. Envío DGT") {
		public String toString() {
			return "3";
		}
	},
	Pendiente_Repuesta_DGT("4", "Pdt. Respuesta DGT") {
		public String toString() {
			return "4";
		}
	},
	Finalizado("5", "Finalizado") {
		public String toString() {
			return "5";
		}
	},
	Finalizado_Error("6", "Finalizado con Error") {
		public String toString() {
			return "6";
		}
	},
	Anulado("7", "Anulado") {
		public String toString() {
			return "7";
		}
	},
	Doc_Recibida("8", "Documentacion Recibida") {
		public String toString() {
			return "8";
		}
	},
	Imprimiendo("9", "Imprimiendo") {
		public String toString() {
			return "9";
		}
	},
	Impresion_OK("10", "Impresion OK") {
		public String toString() {
			return "10";
		}
	},
	Doc_Subida("11", "Documentación Subida") {
		public String toString() {
			return "11";
		}
	},
	Pendiente_Firma_Declaracion("12", "Pdt. Firma Declaración") {
		public String toString() {
			return "12";
		}
	},
	Declaracion_Firmada("13", "Declaración Firmada") {
		public String toString() {
			return "13";
		}
	},
	Finalizado_Pdt_Declaracion("14", "Finalizado Pdt. Declaración") {
		public String toString() {
			return "14";
		}
	},
	Error_Firma_Declaracion("15", "Error Firmar Declaración") {
		public String toString() {
			return "15";
		}
	},
	Finalizado_Pdt_PDF("16", "Finalizado Pdt. PDF") {
		public String toString() {
			return "16";
		}
	},
	Documentacion_Firmada("17", "Documentacion Firmada") {
		public String toString() {
			return "17";
		}
	},
	Pendiente_Envio_Documentos("18", "Pdt. Envío Documentos") {
		public String toString() {
			return "18";
		}
	},
	Doc_Parcialmente_Subida("19", "Documentación Parcialmente Subida") {
		public String toString() {
			return "19";
		}
	},
	Pendiente_Firma_Documentos("20", "Pendiente Firma Documentos") {
		public String toString() {
			return "20";
		}
	},
	Error_Firma_Documentos("21", "Error Firma Documentos") {
		public String toString() {
			return "21";
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

	private EstadoTramitesInterga(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public static EstadoTramitesInterga convertir(String valorEnum) {
		for (EstadoTramitesInterga element : EstadoTramitesInterga.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element;
			}
		}
		return null;
	}

	public static String convertirTexto(String valorEnum) {
		for (EstadoTramitesInterga element : EstadoTramitesInterga.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element.nombreEnum;
			}
		}
		return null;
	}

	public static List<EstadoTramitesInterga> listaEstadosTramiteDocumentacion() {
		List<EstadoTramitesInterga> listaEstados = new ArrayList<EstadoTramitesInterga>();
		listaEstados.add(EstadoTramitesInterga.Iniciado);
		listaEstados.add(EstadoTramitesInterga.Doc_Recibida);
		listaEstados.add(EstadoTramitesInterga.Impresion_OK);
		listaEstados.add(EstadoTramitesInterga.Doc_Subida);
		listaEstados.add(EstadoTramitesInterga.Doc_Parcialmente_Subida);
		return listaEstados;
	}

	public static List<EstadoTramitesInterga> listaEstadosTramite() {
		List<EstadoTramitesInterga> listaEstados = new ArrayList<EstadoTramitesInterga>();
		listaEstados.add(EstadoTramitesInterga.Iniciado);
		listaEstados.add(EstadoTramitesInterga.Validado);
		listaEstados.add(EstadoTramitesInterga.Pendiente_Firma_Declaracion);
		listaEstados.add(EstadoTramitesInterga.Declaracion_Firmada);
		listaEstados.add(EstadoTramitesInterga.Pendiente_Envio_DGT);
		listaEstados.add(EstadoTramitesInterga.Pendiente_Repuesta_DGT);
		listaEstados.add(EstadoTramitesInterga.Finalizado_Pdt_PDF);
		listaEstados.add(EstadoTramitesInterga.Finalizado_Pdt_Declaracion);
		listaEstados.add(EstadoTramitesInterga.Finalizado);
		listaEstados.add(EstadoTramitesInterga.Finalizado_Error);
		listaEstados.add(EstadoTramitesInterga.Error_Firma_Declaracion);
		listaEstados.add(EstadoTramitesInterga.Anulado);
		return listaEstados;
	}

	public static List<EstadoTramitesInterga> listaEstadosTramiteDuplicados() {
		List<EstadoTramitesInterga> listaEstados = new ArrayList<EstadoTramitesInterga>();
		listaEstados.add(EstadoTramitesInterga.Iniciado);
		listaEstados.add(EstadoTramitesInterga.Validado);
		listaEstados.add(EstadoTramitesInterga.Pendiente_Firma_Documentos);
		listaEstados.add(EstadoTramitesInterga.Documentacion_Firmada);
		listaEstados.add(EstadoTramitesInterga.Pendiente_Envio_DGT);
		listaEstados.add(EstadoTramitesInterga.Pendiente_Repuesta_DGT);
		listaEstados.add(EstadoTramitesInterga.Finalizado_Pdt_PDF);
		listaEstados.add(EstadoTramitesInterga.Pendiente_Envio_Documentos);
		listaEstados.add(EstadoTramitesInterga.Finalizado);
		listaEstados.add(EstadoTramitesInterga.Finalizado_Error);
		listaEstados.add(EstadoTramitesInterga.Error_Firma_Documentos);
		listaEstados.add(EstadoTramitesInterga.Anulado);
		return listaEstados;
	}
}
