package org.gestoresmadrid.core.tipoPermDistItv.model.enumerado;

import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;

public enum TipoDocumentoImprimirEnum {


	PERMISO_CIRCULACION("PC", "Permiso de circulación", "Permiso"){
		public String toString() {
			return "PC";
		}
	},
	DISTINTIVO("DSTV", "Distintivo", "Distintivo"){
		public String toString() {
			return "DSTV";
		}
	},
	FICHA_TECNICA("FCT", "Ficha técnica", "Ficha técnica"){
		public String toString() {
			return "FCT";
		}
	},FICHA_PERMISO("FC/PC", "Ficha técnica/Permiso", "Ficha/Permiso"){
		public String toString() {
			return "FC/PC";
		}
	},JUSTIFICANTE("JSF", "Justificante", "Justificante"){
		public String toString() {
			return "JSF";
		}
	},PERMISO_CIRCULACION_KO("PC_KO", "Permiso de circulación KO", "Permiso KO"){
		public String toString() {
			return "PC_KO";
		}
	},FICHA_TECNICA_KO("FCT_KO", "Ficha Técnica KO", "Ficha Técnica KO"){
		public String toString() {
			return "FCT_KO";
		}
	},PERMISO_INTERNACIONAL("PRI", "Permiso Internacional", "Permiso Internacional"){
		public String toString() {
			return "PRI";
		}
	};

	private String valorEnum;
	private String nombreEnum;
	private String nombreEnumError;

	private TipoDocumentoImprimirEnum(String valorEnum, String nombreEnum, String nombreEnumError) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
		this.nombreEnumError = nombreEnumError;
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

	public String getNombreEnumError() {
		return nombreEnumError;
	}
	public void setNombreEnumError(String nombreEnumError) {
		this.nombreEnumError = nombreEnumError;
	}

	public static TipoDocumentoImprimirEnum convertir(String valorEnum) {
		for (TipoDocumentoImprimirEnum element : TipoDocumentoImprimirEnum.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element;
			}
		}
		return null;
	}
	
	public static String convertirTexto(String valorEnum) {
		for (TipoDocumentoImprimirEnum element : TipoDocumentoImprimirEnum.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element.nombreEnum;
			}
		}
		return null;
	}
	
	public static String convertirNombreAValor(String nombreEnum) {
		for (TipoDocumentoImprimirEnum element : TipoDocumentoImprimirEnum.values()) {
			if (element.nombreEnum.equals(nombreEnum)) {
				return element.valorEnum;
			}
		}
		return null;
	}
	
	public static String convertirValorATipoTramite(String valorEnum) {

		if (PERMISO_CIRCULACION.getValorEnum().equals(valorEnum)) {
			return TipoTramiteTrafico.Solicitud_Permiso.getValorEnum();
		} else if (DISTINTIVO.getValorEnum().equals(valorEnum)) {
			return TipoTramiteTrafico.Solicitud_Distintivo.getValorEnum();
		} else if (FICHA_TECNICA.getValorEnum().equals(valorEnum)) {
			return TipoTramiteTrafico.Solicitud_Ficha_Tecnica.getValorEnum();
		} else if (FICHA_PERMISO.getValorEnum().equals(valorEnum)) {
			return TipoTramiteTrafico.Solicitud_Fichas_Permisos.getValorEnum();
		} else if (PERMISO_CIRCULACION_KO.getValorEnum().equals(valorEnum)) {
			return TipoTramiteTrafico.IMPR_KO.getValorEnum();
		} else if (PERMISO_INTERNACIONAL.getValorEnum().equals(valorEnum)) {
			return TipoTramiteTrafico.PermisonInternacional.getValorEnum();
		}

		return null;
	}
}