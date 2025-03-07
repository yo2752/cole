package trafico.utiles.enumerados;

public enum TiposGruposUsuarios {

	EntidadFinanciera("EF", "ENTIDADFINANCIERA") {
		public String toString() {
			return "EF";
		}
	},
	CuerposSeguridad("CS", "CUERPOSEGURIDAD") {
		public String toString() {
			return "CS";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private TiposGruposUsuarios(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public static TiposGruposUsuarios convertir(String valorEnum) {
		if (valorEnum == null)
			return null;

		if (EntidadFinanciera.valorEnum.equals(valorEnum))
			return EntidadFinanciera;
		else if (CuerposSeguridad.valorEnum.equals(valorEnum))
			return CuerposSeguridad;
		else
			return null;
	}

	public static String convertirTexto(String valorEnum) {
		if (valorEnum == null)
			return null;

		if (EntidadFinanciera.valorEnum.equals(valorEnum))
			return EntidadFinanciera.nombreEnum;
		else if (CuerposSeguridad.valorEnum.equals(valorEnum))
			return CuerposSeguridad.nombreEnum;
		else
			return null;
	}

}