package trafico.utiles.enumerados;

public enum CategoriaElectrica {

	Bateria("BEV", "Veh�culo el�ctrico de bater�a") {
		public String toString() {
			return "BEV";
		}
	},
	HibridoEnchufable("PHEV", "Veh�culo el�ctrico h�brido enchufable") {
		public String toString() {
			return "PHEV";
		}
	},
	AutonomiaExtendida("REEV", "Veh�culo el�ctrico de autonom�a extendida") {
		public String toString() {
			return "REEV";
		}
	},
	Hibrido("HEV", "Veh�culo h�brido") {
		public String toString() {
			return "HEV";
		}
	},
	PilaCombustible("FCEV", "Veh�culo el�ctrico de pila de combustible") {
		public String toString() {
			return "FCEV";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private CategoriaElectrica(String valorEnum, String nombreEnum) {
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

	public static CategoriaElectrica convertir(String valorEnum) {
		if (valorEnum == null) {
			return null;
		} else {
			for (CategoriaElectrica categoriaElectrica : CategoriaElectrica.values()) {
				if (categoriaElectrica.getValorEnum().equals(valorEnum)) {
					return categoriaElectrica;
				}
			}
			return null;
		}
	}
}
