package facturacion.utiles.enumerados;

public enum ClienteFacturacion {

//	TipoTitular("TITULAR", "Titular"){
//		public String toString() {
//			return "Titular";
//		}
//	},
	TipoCompraVenta("COMPRAVENTA", "Compra Venta"){
		public String toString() {
			return "Compra Venta";
		}
	},
	TipoComprador("COMPRADOR", "Adquiriente") {
		public String toString() {
			return "Comprador";
		}
	},
	TipoTransmitente("TRANSMITENTE", "Transmitente") {
		public String toString() {
			return "Transmitente";
		}
	},
	TipoNinguno("NINGUNO", "Facturacion Manual") {
		public String toString() {
			return "Ninguno";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private ClienteFacturacion(String valorEnum, String nombreEnum) {
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