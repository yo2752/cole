package trafico.utiles.enumerados;

public enum AcreditacionTrafico {

	Herencia("HERENCIA", "HERENCIA"),
	Posesion("POSESION", "POSESIÓN"),
	Donacion("DONACION", "DONACIÓN");

	private String valorEnum;
	private String nombreEnum;

	private AcreditacionTrafico(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	@Override
	public String toString() {
		return valorEnum;
	}

	// valueOf
	public static AcreditacionTrafico convertir(String valorEnum) {
		for (AcreditacionTrafico at : AcreditacionTrafico.values()) {
			if (at.valorEnum.equals(valorEnum)) {
				return at;
			}
		}
		return null;
	}

}
