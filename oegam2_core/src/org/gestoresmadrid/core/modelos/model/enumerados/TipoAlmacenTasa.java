package org.gestoresmadrid.core.modelos.model.enumerados;

public enum TipoAlmacenTasa {
	SIN_ALMACEN("SIN_ALMACEN", "Sin almac�n"){
		public String toString() {
			return "SIN_ALMACEN";
		}
	},
	ALMACEN_MATW("ALMACEN_MATW", "Almac�n tasa Matw"){
		public String toString() {
			return "ALMACEN_MATW";
		}
	},
	ALMACEN_CTIT("ALMACEN_CTIT", "Almac�n tasa Ctit"){
		public String toString() {
			return "ALMACEN_CTIT";
		}
	},
	ALMACEN_BAJA("ALMACEN_BAJA", "Almac�n tasa Baja"){
		public String toString() {
			return "ALMACEN_BAJA";
		}
	},
	ALMACEN_DUPLICADO("ALMACEN_DUPLICADO", "Almac�n tasa Duplicado"){
		public String toString() {
			return "ALMACEN_DUPLICADO";
		}
	},
	ALMACEN_INTEVE("ALMACEN_INTEVE", "Almac�n tasa Inteve"){
		public String toString() {
			return "ALMACEN_INTEVE";
		}
	},
	ALMACEN_PERMISO_INT("ALMACEN_PERMISO_INT", "Almac�n tasa Permiso Internacional"){
		public String toString() {
			return "ALMACEN_PERMISO_INT";
		}
	};

	private String valorEnum;
	private String nombreEnum;
	
	private TipoAlmacenTasa(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public void setValorEnum(String valorEnum) {
		this.valorEnum = valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public void setNombreEnum(String nombreEnum) {
		this.nombreEnum = nombreEnum;
	}
	
	public static TipoAlmacenTasa convertir(String valorEnum) {
		for (TipoAlmacenTasa element : TipoAlmacenTasa.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element;
			}
		}
		return null;
	}
	
	public static String convertirTexto(String valorEnum) {
		for (TipoAlmacenTasa element : TipoAlmacenTasa.values()) {
			if (element.valorEnum.equals(valorEnum)) {
				return element.nombreEnum;
			}
		}
		return null;
	}
	
	public static String convertirNombreAValor(String nombreEnum) {
		for (TipoAlmacenTasa element : TipoAlmacenTasa.values()) {
			if (element.nombreEnum.equals(nombreEnum)) {
				return element.valorEnum;
			}
		}
		return null;
	}
	
   
}
