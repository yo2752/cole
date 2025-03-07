package org.gestoresmadrid.core.enumerados;

import java.util.ArrayList;
import java.util.List;

public enum TipoDocumentoDuplicado {

	PERMISO_CIRCULACION("1", "Permiso Circulación") {
		public String toString() {
			return "1";
		}
	},
	TARJETA_ITV("2", "Tarjeta ITV") {
		public String toString() {
			return "2";
		}
	},
	PERMISO_CIRCULACION_ITV("3", "Permiso Circulación + Tarjeta ITV") {
		public String toString() {
			return "3";
		}
	}
//	,
//	DISTINTIVO("4", "distintivomedioambiental") {
//		public String toString() {
//			return "4";
//		}
//	}
	;

	private String valorEnum;
	private String nombreEnum;

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	private TipoDocumentoDuplicado(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public static TipoDocumentoDuplicado convertir(String valorEnum) {
		for (TipoDocumentoDuplicado element : TipoDocumentoDuplicado.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element;
			}
		}
		return null;
	}

	public static String convertirNombre(String valorEnum) {
		for (TipoDocumentoDuplicado element : TipoDocumentoDuplicado.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element.nombreEnum;
			}
		}
		return null;
	}

	public static List<TipoDocumentoDuplicado> listaTipoDocumentos() {
		List<TipoDocumentoDuplicado> listaTipoDocumentos = new ArrayList<TipoDocumentoDuplicado>();
		listaTipoDocumentos.add(TipoDocumentoDuplicado.PERMISO_CIRCULACION);
		listaTipoDocumentos.add(TipoDocumentoDuplicado.TARJETA_ITV);
		listaTipoDocumentos.add(TipoDocumentoDuplicado.PERMISO_CIRCULACION_ITV);
		//listaTipoDocumentos.add(TipoDocumentoDuplicado.DISTINTIVO);
		return listaTipoDocumentos;
	}
}
