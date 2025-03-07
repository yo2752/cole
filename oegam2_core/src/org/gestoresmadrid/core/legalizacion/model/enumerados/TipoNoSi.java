package org.gestoresmadrid.core.legalizacion.model.enumerados;

public enum TipoNoSi {
	SI(0, "Si") {
		public String toString() {
			return "true";
		}
	},
	NO(1, "No") {
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

	private TipoNoSi(Integer valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

}