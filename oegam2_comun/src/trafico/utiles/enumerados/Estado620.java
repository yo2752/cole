package trafico.utiles.enumerados;

public enum Estado620 {

	Iniciado("1", "Iniciado") {
		public String toString() {
			return "1";
		}
	},
	Validado("2", "Validado") {
		public String toString() {
			return "2";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private Estado620(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public static Estado620 convertir(String valorEnum) {
		if (valorEnum == null)
			return null;
		if (valorEnum.equals("1"))
			return Iniciado;
		else if (valorEnum.equals("2"))
			return Validado;
		else
			return null;
	}
}