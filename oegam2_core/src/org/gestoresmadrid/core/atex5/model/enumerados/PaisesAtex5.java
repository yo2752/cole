package org.gestoresmadrid.core.atex5.model.enumerados;

public enum PaisesAtex5 {

	Alemania("D", "Alemania"){
		public String toString() {
	        return "D";
	    }
	},
	Antillas_Neerlandesas("NA", "Antillas Neerlandesas"){
		public String toString() {
	        return "NA";
	    }
	},
	Austria("A", "Austria"){
		public String toString() {
	        return "A";
	    }
	},
	Bulgaria("BG", "Bulgaria"){
		public String toString() {
	        return "BG";
	    }
	},
	Belgica("B", "Bélgica"){
		public String toString() {
	        return "B";
	    }
	},
	Chipre("CY", "Chipre"){
		public String toString() {
	        return "CY";
	    }
	},
	Dinamarca("DK", "Dinamarca"){
		public String toString() {
	        return "DK";
	    }
	},
	Eslovaquia("SK", "Eslovaquia"){
		public String toString() {
	        return "SK";
	    }
	},
	Eslovenia("SLO", "Eslovenia"){
		public String toString() {
	        return "SLO";
	    }
	},
	Estonia("EST", "Estonia"){
		public String toString() {
	        return "EST";
	    }
	},
	Finlandia("FIN", "Finlandia"){
		public String toString() {
	        return "FIN";
	    }
	},
	Francia("F2", "Francia (DPICA)"){
		public String toString() {
	        return "F2";
	    }
	},
	Gibraltar("GBZ", "Gibraltar"){
		public String toString() {
	        return "GBZ";
	    }
	},
	Grecia("GR", "Grecia"){
		public String toString() {
	        return "GR";
	    }
	},
	Guernsey("GBG", "Guernsey"){
		public String toString() {
	        return "GBG";
	    }
	},
	Holanda("NL", "Holanda"){
		public String toString() {
	        return "NL";
	    }
	},
	Hungria("H", "Hungría"){
		public String toString() {
	        return "H";
	    }
	},
	Irlanda("IRL", "Irlanda"){
		public String toString() {
	        return "IRL";
	    }
	},
	Isla_de_Man("GBM", "Isla de Man"){
		public String toString() {
	        return "GBM";
	    }
	},
	Islandia("IS", "Islandia"){
		public String toString() {
	        return "IS";
	    }
	},
	Italia("I", "Italia"){
		public String toString() {
	        return "I";
	    }
	},
	Jersey("GBJ", "Jersey"){
		public String toString() {
	        return "GBJ";
	    }
	},
	Letonia("LV", "Letonia"){
		public String toString() {
	        return "LV";
	    }
	},
	Liechtenstein("FL", "Liechtenstein"){
		public String toString() {
	        return "FL";
	    }
	},
	Lituania("LT", "Lituania"){
		public String toString() {
	        return "LT";
	    }
	},
	Luxemburgo("L", "Luxemburgo"){
		public String toString() {
	        return "L";
	    }
	},
	Malta("M", "Malta"){
		public String toString() {
	        return "M";
	    }
	},
	Noruega("N", "Noruega"){
		public String toString() {
	        return "N";
	    }
	},
	Polonia("PL", "Polonia"){
		public String toString() {
	        return "PL";
	    }
	},
	Portugal("P", "Portugal"){
		public String toString() {
	        return "P";
	    }
	},
	Reino_Unido("GB", "Reino Unido"){
		public String toString() {
	        return "GB";
	    }
	},
	Republica_Checa("CZ", "República Checa"){
		public String toString() {
	        return "CZ";
	    }
	},
	Rumania("RO", "Rumanía"){
		public String toString() {
	        return "RO";
	    }
	},
	Suecia("S", "Suecia"){
		public String toString() {
	        return "S";
	    }
	},
	Suiza("CH", "Suiza"){
		public String toString() {
	        return "CH";
	    }
	};
	
	private String valorEnum;
	private String nombreEnum;
	
	private PaisesAtex5(String valorEnum, String nombreEnum) {
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
	
	public static PaisesAtex5 convertir(String valorEnum) {
		if(valorEnum != null && !valorEnum.isEmpty()){
			for(PaisesAtex5 pais : PaisesAtex5.values()){
				if(pais.getValorEnum().equals(valorEnum)){
					return pais;
				}
			}
		}
		return null;
	}
	
	public static String convertirTexto(String valor){
		if(valor != null){
			for(PaisesAtex5 pais : PaisesAtex5.values()){
				if(pais.getValorEnum().equals(valor)){
					return pais.getNombreEnum();
				}
			}
		}
		return null;
	}
	
	public static String convertirNombre(String nombre){
		if(nombre != null){
			for(PaisesAtex5 pais : PaisesAtex5.values()){
				if(pais.getNombreEnum().equals(nombre)){
					return pais.getValorEnum();
				}
			}
		}
		return null;
	}
	
	public static String convertirTexto(PaisesAtex5 paisAtex5){
		if(paisAtex5 != null){
			for(PaisesAtex5 pais : PaisesAtex5.values()){
				if(pais.getValorEnum() == paisAtex5.getValorEnum()){
					return pais.getNombreEnum();
				}
			}
		}
		return null;
	}
}
