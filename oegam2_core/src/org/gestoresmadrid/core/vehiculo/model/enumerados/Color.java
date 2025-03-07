package org.gestoresmadrid.core.vehiculo.model.enumerados;

import java.util.ArrayList;

public enum Color {

	Indefinido("-", "COLOR") {
		public String toString() {
			return "-";
		}
	},
	Amarillo("AM", "AMARILLO") {
		public String toString() {
			return "AM";
		}
	},
	Azul("AZ", "AZUL") {
		public String toString() {
			return "AZ";
		}
	},
	Beige("BE", "BEIGE") {
		public String toString() {
			return "BE";
		}
	},
	Blanco("BL", "BLANCO") {
		public String toString() {
			return "BL";
		}
	},
	Gris("GR", "GRIS") {
		public String toString() {
			return "GR";
		}
	},
	Marron("MA", "MARRON") {
		public String toString() {
			return "MA";
		}
	},
	Multicolor("MU", "MULTICOLOR") {
		public String toString() {
			return "MU";
		}
	},
	Naranja("NA", "NARANJA") {
		public String toString() {
			return "NA";
		}
	},
	Negro("NE", "NEGRO") {
		public String toString() {
			return "NE";
		}
	},
	Purpura("PU", "PÚRPURA") {
		public String toString() {
			return "PU";
		}
	},
	Rojo("RO", "ROJO") {
		public String toString() {
			return "RO";
		}
	},
	Rosa("RS", "ROSA") {
		public String toString() {
			return "RS";
		}
	},
	Verde("VE", "VERDE") {
		public String toString() {
			return "VE";
		}
	},
	Violeta("VI", "VIOLETA") {
		public String toString() {
			return "VI";
		}
	},
	No_disponible("ND", "NO DISPONIBLE") {
		public String toString() {
			return "ND";
		}
	};

	public static Color convertir(String valorEnum) {

		if (valorEnum == null)
			return null;
		if (valorEnum.equals(Indefinido.getValorEnum()))
			return Indefinido;
		if (valorEnum.equals(Amarillo.getValorEnum()))
			return Amarillo;
		if (valorEnum.equals(Azul.getValorEnum()))
			return Azul;
		if (valorEnum.equals(Beige.getValorEnum()))
			return Beige;
		if (valorEnum.equals(Blanco.getValorEnum()))
			return Blanco;
		if (valorEnum.equals(Gris.getValorEnum()))
			return Gris;
		if (valorEnum.equals(Marron.getValorEnum()))
			return Marron;
		if (valorEnum.equals(Multicolor.getValorEnum()))
			return Multicolor;
		if (valorEnum.equals(Naranja.getValorEnum()))
			return Naranja;
		if (valorEnum.equals(Negro.getValorEnum()))
			return Negro;
		if (valorEnum.equals(Purpura.getValorEnum()))
			return Purpura;
		if (valorEnum.equals(Rojo.getValorEnum()))
			return Rojo;
		if (valorEnum.equals(Rosa.getValorEnum()))
			return Rosa;
		if (valorEnum.equals(Verde.getValorEnum()))
			return Verde;
		if (valorEnum.equals(Violeta.getValorEnum()))
			return Violeta;
		if (valorEnum.equals(No_disponible.getValorEnum()))
			return No_disponible;
		return null;

	}

	public static String convertirTexto(String valorEnum) {

		if (valorEnum == null)
			return null;
		if (valorEnum.equals(Indefinido.getValorEnum()))
			return Indefinido.getNombreEnum();
		if (valorEnum.equals(Amarillo.getValorEnum()))
			return Amarillo.getNombreEnum();
		if (valorEnum.equals(Azul.getValorEnum()))
			return Azul.getNombreEnum();
		if (valorEnum.equals(Beige.getValorEnum()))
			return Beige.getNombreEnum();
		if (valorEnum.equals(Blanco.getValorEnum()))
			return Blanco.getNombreEnum();
		if (valorEnum.equals(Gris.getValorEnum()))
			return Gris.getNombreEnum();
		if (valorEnum.equals(Marron.getValorEnum()))
			return Marron.getNombreEnum();
		if (valorEnum.equals(Multicolor.getValorEnum()))
			return Multicolor.getNombreEnum();
		if (valorEnum.equals(Naranja.getValorEnum()))
			return Naranja.getNombreEnum();
		if (valorEnum.equals(Negro.getValorEnum()))
			return Negro.getNombreEnum();
		if (valorEnum.equals(Purpura.getValorEnum()))
			return Purpura.getNombreEnum();
		if (valorEnum.equals(Rojo.getValorEnum()))
			return Rojo.getNombreEnum();
		if (valorEnum.equals(Rosa.getValorEnum()))
			return Rosa.getNombreEnum();
		if (valorEnum.equals(Verde.getValorEnum()))
			return Verde.getNombreEnum();
		if (valorEnum.equals(Violeta.getValorEnum()))
			return Violeta.getNombreEnum();
		if (valorEnum.equals(No_disponible.getValorEnum()))
			return No_disponible.getNombreEnum();
		return null;
	}

	// Para añadir un color a los no permitidos en MATW añadirlo al array 'excluidos'
	public static Color[] values_MATW() {
		//
		ArrayList<Color> excluidos = new ArrayList<Color>();
		excluidos.add(Color.Beige);
		excluidos.add(Color.Rosa);
		//
		int numColores = Color.values().length;
		Color[] colores = Color.values();
		int contadorColorMatw = 0;
		Color[] coloresMatw = new Color[numColores - excluidos.size()];
		boolean colorExcluido = false;
		for (int i = 0; i < numColores; i++) {
			Color color = colores[i];
			for (Color excluido : excluidos) {
				if (color.getNombreEnum().equals(excluido.getNombreEnum())) {
					colorExcluido = true;
					break;
				}
			}
			if (!colorExcluido) {
				coloresMatw[contadorColorMatw] = color;
				contadorColorMatw++;
			}
			colorExcluido = false;
		}
		return coloresMatw;
	}

	private String valorEnum;
	private String nombreEnum;

	private Color(String valorEnum, String nombreEnum) {
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
