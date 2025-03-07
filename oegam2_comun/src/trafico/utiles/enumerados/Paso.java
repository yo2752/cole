package trafico.utiles.enumerados;

public enum Paso {

	FullTramitable("0", "0") {
		public String toString() {
			return "0";
		}
	},

	UnoFullIncidencias("1", "1") {
		public String toString() {
			return "1";
		}
	},
	DosFullIncidencias("2", "2") {
		public String toString() {
			return "2";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private Paso(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public static Paso convertir(String valorEnum) {

		if (valorEnum == null)
			return null;

		if (valorEnum.equals("1"))
			return UnoFullIncidencias;
		else if (valorEnum.equals("2"))
			return DosFullIncidencias;
		else if (valorEnum.equals("0"))
			return FullTramitable;

		else
			return null;
	}

	public static Paso convertirNpaso(String nPasos) {
		if(nPasos != null && !nPasos.equals(nPasos)){
			if(nPasos.equals("1")){
				return Paso.FullTramitable;
			} else if( nPasos.equals("2")){
				return Paso.UnoFullIncidencias;
			}
		}
		return null;
	}
}