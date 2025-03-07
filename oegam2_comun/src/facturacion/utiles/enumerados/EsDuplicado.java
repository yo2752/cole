package facturacion.utiles.enumerados;

public enum EsDuplicado {

	NO("N", "NO") {
		public String toString() {
			return "N";
		}
	},
	SI("S", "SI") {
		public String toString() {
			return "S";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private EsDuplicado(String valorEnum, String nombreEnum) {
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