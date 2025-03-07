package org.gestoresmadrid.core.enumerados;

import java.util.ArrayList;
import java.util.List;

public enum TipoImpr {

	Permiso_Circulacion("PC", "Permiso de circulacion"){
		public String toString() {
			return "PC";
		}
	},
	Ficha_Tecnica("FCT", "Ficha tecnica"){
		public String toString() {
			return "FCT";
		}
	},
	IMPR_Automovil("T", "Impr Automovil"){
		public String toString() {
			return "T";
		}
	}, 
	IMPR_Ciclomotor("MT", "Impr Ciclomotor"){
		public String toString() {
			return "MT";
		}
	}, Distintivo("DSTV", "Distintivo"){
		public String toString() {
			return "DSTV";
		}
	}, Relacion_Impr("REL", "Relacion Impr"){
		public String toString() {
			return "REL";
		}
	}, Justificante_Impr("JSF", "Justificante Impr"){
		public String toString() {
			return "JSF";
		}
	}, Permiso_Y_Fichas("PC/FCT", "Permiso/Ficha Tecnica"){
		public String toString() {
			return "PC/FCT";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private TipoImpr(String valorEnum, String nombreEnum) {
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

	public static TipoImpr convertir(String valorEnum) {
		for (TipoImpr element : TipoImpr.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element;
			}
		}
		return null;
	}
	
	public static String convertirTexto(String valorEnum) {
		for (TipoImpr element : TipoImpr.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element.nombreEnum;
			}
		}
		return null;
	}
	
	public static String convertirNombreAValor(String nombreEnum) {
		for (TipoImpr element : TipoImpr.values()) {
			if (element.nombreEnum.equals(nombreEnum)) {
				return element.valorEnum;
			}
		}
		return null;
	}
	public static boolean esTipoImprValido(String tipoImpr) {
		if(TipoImpr.Permiso_Circulacion.getValorEnum().equals(tipoImpr)) {
			return Boolean.TRUE;
		} else if(TipoImpr.Ficha_Tecnica.getValorEnum().equals(tipoImpr)) {
			return Boolean.TRUE;
		} else if(TipoImpr.Permiso_Y_Fichas.getValorEnum().equals(tipoImpr)) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public static List<TipoImpr> getListaTipoImprConsulta() {
		List<TipoImpr> listaBuscador = new ArrayList<>();
		listaBuscador.add(TipoImpr.Permiso_Circulacion);
		listaBuscador.add(TipoImpr.Ficha_Tecnica);
		return listaBuscador;
	}
}
