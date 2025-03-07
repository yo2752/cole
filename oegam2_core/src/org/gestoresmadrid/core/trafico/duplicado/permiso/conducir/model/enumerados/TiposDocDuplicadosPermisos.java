package org.gestoresmadrid.core.trafico.duplicado.permiso.conducir.model.enumerados;

import java.util.ArrayList;
import java.util.List;

public enum TiposDocDuplicadosPermisos {

	MANDATO("M", "Mandato Específico") {
		public String toString() {
			return "M";
		}
	},
	DECLARACION("D", "Declaración Responsabilidad") {
		public String toString() {
			return "D";
		}
	},
	DECLARACION_TITULAR("DT", "Declaración Responsabilidad Titular") {
		public String toString() {
			return "DT";
		}
	},
	SOLICITUD("S", "Solicitud") {
		public String toString() {
			return "S";
		}
	},
	OTRO("O", "Otro") {
		public String toString() {
			return "O";
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

	private TiposDocDuplicadosPermisos(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public static TiposDocDuplicadosPermisos convertir(String valorEnum) {
		for (TiposDocDuplicadosPermisos element : TiposDocDuplicadosPermisos.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element;
			}
		}
		return null;
	}

	public static String convertirNombre(String valorEnum) {
		for (TiposDocDuplicadosPermisos element : TiposDocDuplicadosPermisos.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element.nombreEnum;
			}
		}
		return null;
	}

	public static List<TiposDocDuplicadosPermisos> listaTipoDocumentos() {
		List<TiposDocDuplicadosPermisos> listaTipoDocumentos = new ArrayList<TiposDocDuplicadosPermisos>();
		listaTipoDocumentos.add(TiposDocDuplicadosPermisos.DECLARACION_TITULAR);
		listaTipoDocumentos.add(TiposDocDuplicadosPermisos.MANDATO);
		listaTipoDocumentos.add(TiposDocDuplicadosPermisos.OTRO);
		return listaTipoDocumentos;
	}
}
