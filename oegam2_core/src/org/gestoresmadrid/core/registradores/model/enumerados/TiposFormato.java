package org.gestoresmadrid.core.registradores.model.enumerados;

// PCKS7, PDF , RTF, DOC, TIF, ASC, XLS, XML, JPG, JPEG y ZIP
public enum TiposFormato{

	PKCS7(1, "PKCS7"){
		public String toString() {
	        return "PKCS7";
	    }
	},
	PDF(2, "PDF"){
		public String toString() {
	        return "PDF";
	    }
	},
	RTF(3, "RTF"){
		public String toString() {
	        return "RTF";
	    }
	},
	DOC(4, "DOC"){
		public String toString() {
	        return "DOC";
	    }
	},
	TIF(5, "TIF"){
		public String toString() {
	        return "TIF";
	    }
	},
	XML(6, "XML"){
		public String toString() {
	        return "XML";
	    }
	},
	TXT(7, "TXT"){
		public String toString() {
	        return "TXT";
	    }
	},
	ASC(8, "ASC"){
		public String toString() {
	        return "ASC";
	    }
	},
	XLS(9, "XLS"){
		public String toString() {
	        return "XLS";
	    }
	},
	JPG(9, "JPG"){
		public String toString() {
	        return "JPG";
	    }
	},
	JPEG(9, "JPEG"){
		public String toString() {
	        return "JPEG";
	    }
	},
	ZIP(9, "ZIP"){
		public String toString() {
	        return "ZIP";
	    }
	};
	
	private TiposFormato(int valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}
	
	private int valorEnum;
	private String nombreEnum;
	
	public static TiposFormato convertirExtension(String param) {    
		// PCKS7, PDF , RTF, DOC, TIF, ASC, XLS, XML, JPG, JPEG y ZIP
		if(param==null)
			return null;

		if ("pkcs7".equals(param)){
			return PKCS7;
		}else if ("pdf".equals(param)){
			return PDF;
		}else if ("rtf".equals(param)){
			return RTF;
		}else if ("doc".equals(param)){
			return DOC;
		}else if ("tif".equals(param)){
			return TIF;
		}else if ("asc".equals(param)){
			return ASC;
		}else if ("xls".equals(param)){
			return XLS;
		}else if ("xml".equals(param)){
			return XML;
		}else if ("jpg".equals(param)){
			return JPG;
		}else if ("jpeg".equals(param)){
			return JPEG;
		}else if ("zip".equals(param)){
			return ZIP;
		}else {
			return null;
		}

	}
	
	public String getNombreEnum() {
		return nombreEnum;
	}

	public int getValorEnum() {
		return valorEnum;
	}
}

