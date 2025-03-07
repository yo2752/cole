package trafico.utiles.enumerados;

public enum TipoProcedencia {

	NACIONAL("Nacional", "NACIONAL") {
		public String toString() {
			return "Nacional";
		}
	},
	EXTRANJERO("Extranjero", "EXTRANJERO") {
		public String toString() {
			return "Extranjero";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private TipoProcedencia(String valorEnum, String nombreEnum) {
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

	public static TipoProcedencia convertir(String valorEnum) {
		if (valorEnum == null)
			return null;

		if ("Nacional".equalsIgnoreCase(valorEnum))
			return NACIONAL;
		else if ("Extranjero".equalsIgnoreCase(valorEnum))
			return EXTRANJERO;
		else
			return null;
	}

}