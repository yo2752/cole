package org.gestoresmadrid.utilidades.listas;

/**
 * Enumerado de tipo de ordenación de una lista.
 * 
 * @author ext_ihgl.
 *
 */

public enum OrderType {

	/* VALORES ENUMERADO */
	asc("asc", "asc") {
		public String toString() {
			return "asc";
		}
	},

	desc("desc", "desc") {
		public String toString() {
			return "desc";
		}
	};

	/* ATRIBUTOS */
	private String valorEnum;
	private String nombreEnum;

	private OrderType(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	/* GETTERS/SETTERS */
	public String getValorEnum() {
		return valorEnum;
	}

	public void setValorEnum(String valorEnum) {
		this.valorEnum = valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public void setNombreEnum(String nombreEnum) {
		this.nombreEnum = nombreEnum;
	}

}