package trafico.utiles.enumerados;

public enum TipoDatoActualizar {

	Id_Vehiculo("Id Vehiculo", "Id Vehiculo") {
		public String toString() {
			return "Id Vehiculo";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private TipoDatoActualizar(String valorEnum, String nombreEnum) {
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