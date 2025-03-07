package org.gestoresmadrid.core.docbase.enumerados;

public enum OrdenDocBaseEnum {

	Referencia_propia("1", "Referencia propia") {
		public String toString() {
			return "1";
		}
	},
	Matricula("2", "Matrícula") {
		public String toString() {
			return "2";
		}
	},
	Numero_Expediente("3", "Número de Expediente") {
		public String toString() {
			return "3";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private OrdenDocBaseEnum(String valorEnum, String nombreEnum) {
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

	public static OrdenDocBaseEnum convertir(String valorEnum) {
		if (valorEnum == null)
			return null;
		if ("1".equalsIgnoreCase(valorEnum))
			return Referencia_propia;
		else if ("2".equalsIgnoreCase(valorEnum))
			return Matricula;
		else if ("3".equalsIgnoreCase(valorEnum))
			return Numero_Expediente;
		else
			return null;
	}

}