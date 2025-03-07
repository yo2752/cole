package trafico.utiles.enumerados;

public enum TipoLimitacionDisposicionIEDTM {

	ImpuestoEspecial("E", "IMPUESTO ESPECIAL") {
		public String toString() {
			return "E";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private TipoLimitacionDisposicionIEDTM(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public static TipoLimitacionDisposicionIEDTM convertir(String valorEnum) {
		if (valorEnum == null)
			return null;
		if (valorEnum.equals("E"))
			return ImpuestoEspecial;
		else
			return null;
	}

}