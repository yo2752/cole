package escrituras.utiles.enumerados;

public enum EstadoTramiteCredito {

	Incremental("I", "Incremental [Contador]") {
		public String toString() {
			return "I";
		}
	},
	Decremental("D", "Decremental [Crédito]") {
		public String toString() {
			return "D";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private EstadoTramiteCredito(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public static String convertirTexto(String valorEnum) {
		if (valorEnum.equals("I")) {
			return "Incremental [Contador]";
		}
		if (valorEnum.equals("D")) {
			return "Decremental [Credito]";
		}
		return null;
	}
}