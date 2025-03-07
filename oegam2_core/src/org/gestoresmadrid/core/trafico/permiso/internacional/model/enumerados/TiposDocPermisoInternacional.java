package org.gestoresmadrid.core.trafico.permiso.internacional.model.enumerados;

import java.util.ArrayList;
import java.util.List;

public enum TiposDocPermisoInternacional {

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
	CARNET_IDENTIDAD("DNI", "Carnet Identidad") {
		public String toString() {
			return "DNI";
		}
	},
	SOLICITUD("S", "Solicitud") {
		public String toString() {
			return "S";
		}
	},
	CARNET_CONDUCIR("CC", "Carnet Conducir") {
		public String toString() {
			return "CC";
		}
	},
	OTRO("O", "Otro") {
		public String toString() {
			return "O";
		}
	},
	PERMISO_INTERNACIONAL("PI", "Permiso Internacional") {
		public String toString() {
			return "PI";
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

	private TiposDocPermisoInternacional(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public static TiposDocPermisoInternacional convertir(String valorEnum) {
		for (TiposDocPermisoInternacional element : TiposDocPermisoInternacional.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element;
			}
		}
		return null;
	}

	public static String convertirNombre(String valorEnum) {
		for (TiposDocPermisoInternacional element : TiposDocPermisoInternacional.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element.nombreEnum;
			}
		}
		return null;
	}

	public static List<TiposDocPermisoInternacional> listaTipoDocumentos() {
		List<TiposDocPermisoInternacional> listaTipoDocumentos = new ArrayList<TiposDocPermisoInternacional>();
		listaTipoDocumentos.add(TiposDocPermisoInternacional.CARNET_CONDUCIR);
		listaTipoDocumentos.add(TiposDocPermisoInternacional.CARNET_IDENTIDAD);
		listaTipoDocumentos.add(TiposDocPermisoInternacional.MANDATO);
		listaTipoDocumentos.add(TiposDocPermisoInternacional.SOLICITUD);
		listaTipoDocumentos.add(TiposDocPermisoInternacional.OTRO);
		return listaTipoDocumentos;
	}
}
