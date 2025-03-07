package facturacion.utiles.enumerados;

public enum EstadoFacturacion {	

	EstadoNueva("0", "NUEVA")								 { public String toString() { return "Nueva"; 					} },
	EstadoFacturaGenerada("1", "FACTURA GENERADA")			 { public String toString() { return "Factura Generada";		} },
	EstadoFacturaRectificativa("2", "FACTURA RECTIFICATIVA") { public String toString() { return "Factura Rectificativa";	} },
	EstadoFacturaAnulada("3", "FACTURA ANULADA")			 { public String toString() { return "Factura Anulada";			} },
	EstadoFacturaAbono("4", "FACTURA ABONO")				 { public String toString() { return "Factura Abono";			} };

	private String valorEnum;
	private String nombreEnum;

	private EstadoFacturacion(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}
}