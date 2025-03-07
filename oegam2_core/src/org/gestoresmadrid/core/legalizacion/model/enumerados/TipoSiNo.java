package org.gestoresmadrid.core.legalizacion.model.enumerados;

public enum TipoSiNo {
	SI(1, "Si") {
		public String toString() {
			return "true";
		}
	},
	NO(0, "No") {
		public String toString() {
			return "false";
		}
	};

	private Integer valorEnum;
	private String nombreEnum;

	public Integer getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	private TipoSiNo(Integer valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

}