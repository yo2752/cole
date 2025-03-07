package org.gestoresmadrid.core.tasas.model.enumeration;

public enum TipoTasaDGT {

	UnoUno("1.1", "1.1"){
		public String toString() {
	        return "1.1";
	    }
	},
	UnoDos("1.2", "1.2"){
		public String toString() {
	        return "1.2";
	    }
	},
	UnoTres("1.3", "1.3"){
		public String toString() {
	        return "1.3";
	    }
	},
	UnoCuatro("1.4", "1.4"){
		public String toString() {
	        return "1.4";
	    }
	},
	UnoCinco("1.5", "1.5"){
		public String toString() {
	        return "1.5";
	    }
	},
	UnoSeis("1.6", "1.6"){
		public String toString() {
	        return "1.6";
	    }
	},
	DosTres("2.3", "2.3"){
		public String toString() {
	        return "2.3";
	    }
	},
	
	CuatroUno("4.1", "4.1"){
		public String toString() {
	        return "4.1";
	    }
	},
	
	CuatroDos("4.2", "4.2"){
		public String toString() {
	        return "4.2";
	    }
	},
	
	CuatroTres("4.3", "4.3"){
		public String toString() {
	        return "4.3";
	    }
	},
	CuatroTresUno("4.31", "4.31"){
		public String toString() {
	        return "4.31";
	    }
	},
	CuatroTresDos("4.32", "4.32"){
		public String toString() {
	        return "4.32";
	    }
	},
	CuatroTresTres("4.33", "4.33"){
		public String toString() {
	        return "4.33";
	    }
	},
	CuatroTresCuatro("4.34", "4.34"){
		public String toString() {
	        return "4.34";
	    }
	},
	CuatroCuatro("4.4", "4.4"){
		public String toString() {
	        return "4.4";
	    }
	},
	CuatroCinco("4.5", "4.5"){
		public String toString() {
	        return "4.5";
	    }
	};

	private String valorEnum;
	private String nombreEnum;


	
	private TipoTasaDGT(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public static TipoTasaDGT convertir(String valor) {
		for (TipoTasaDGT element : TipoTasaDGT.values()) {
			if (element.valorEnum.equals(valor)) {
				return element;
			}
		}
		return null;
	}	
    
}
