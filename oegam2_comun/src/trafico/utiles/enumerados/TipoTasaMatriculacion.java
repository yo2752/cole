package trafico.utiles.enumerados;

public enum TipoTasaMatriculacion {

	UnoUno("1.1", "1.1") {
		public String toString() {
			return "1.1";
		}
	},
	UnoDos("1.2", "1.2") {
		public String toString() {
			return "1.2";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private TipoTasaMatriculacion(String valorEnum, String nombreEnum) {
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