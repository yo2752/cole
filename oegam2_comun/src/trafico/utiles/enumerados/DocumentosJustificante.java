package trafico.utiles.enumerados;

public enum DocumentosJustificante {

	NotificacionVenta("NV", "NOTIFICACIÓN VENTA") {
		public String toString() {
			return "NV";
		}
	},
	CambioTitular("CT", "CAMBIO TITULAR") {
		public String toString() {
			return "CT";
		}
	};
//	PermisoCirculacion("PC","Permiso de circulación."){
//		public String toString(){
//			return "PC"; 
//		}
//	}, 
//	FichaTecnica("FT", "Ficha técnica."){
//		public String toString() {
//	        return "FT";
//	    }
//	},
//	PermisoCirculacionYFichaTecnica("PCYFT", "Permiso de circulación y ficha técnica.") {
//		public String toString() {
//	        return "PCYFT";
//	    }
//	};

	private String valorEnum;
	private String nombreEnum;

	private DocumentosJustificante(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public static DocumentosJustificante convertir(String valorEnum) {

		if (valorEnum == null)
			return null;

		if (valorEnum.equals("NV"))
			return NotificacionVenta;

		if (valorEnum.equals("CT"))
			return CambioTitular;

		return null;
	}

	// valueOf
	public static DocumentosJustificante convertir(Integer valorEnum) {

		if (valorEnum == null)
			return null;

		switch (valorEnum) {
			case 1:
				return NotificacionVenta;
			case 2:
				return CambioTitular;
			default:
				return null;
		}
	}

	public static String convertirTexto(String documento) {
		if (documento != null) {
			for (DocumentosJustificante doc : DocumentosJustificante.values()) {
				if (doc.getValorEnum().equals(documento)) {
					return doc.getNombreEnum();
				}
			}
		}
		return null;
	}
}