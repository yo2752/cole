package trafico.utiles.enumerados;

public enum LugarAdquisicion {

	España(1, "ESPAÑA") {
		public String toString() {
			return "1";
		}
	},
	OtrosEstadosMiembrosDeLaUE(2, "OTROS ESTADOS MIEMBROS DE LA U.E.") {
		public String toString() {
			return "2";
		}
	},
	EstadoNoMiembroDeLaUE(3, "ESTADO NO MIEMBRO DE LA U.E.") {
		public String toString() {
			return "3";
		}
	};

	private Integer valorEnum;
	private String nombreEnum;

	private LugarAdquisicion(Integer valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public Integer getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public static LugarAdquisicion convertir(Integer valorEnum) {
		if (valorEnum == null) {
			return null;
		} else {
			for (LugarAdquisicion lugarAdquisicion : LugarAdquisicion.values()) {
				if (lugarAdquisicion.getValorEnum().equals(valorEnum)) {
					return lugarAdquisicion;
				}
			}
			return null;
		}
	}
}