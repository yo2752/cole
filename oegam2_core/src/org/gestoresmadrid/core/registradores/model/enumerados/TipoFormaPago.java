package org.gestoresmadrid.core.registradores.model.enumerados;

public enum TipoFormaPago {

	CUENTA("1", "Cargo en cuenta") {
		public String toString() {
			return "1";
		}
	},
	TRANSFERENCIA("2", "Transferencia") {
		public String toString() {
			return "2";
		}
	},
	USUARIO_ABONADO("3", "Usuario Abonado Servicios Interactivos CORPME") {
		public String toString() {
			return "3";
		}
	};

	private String valorEnum;
	private String nombreEnum;

	private TipoFormaPago(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	// valueOf
	public static TipoFormaPago convertir(String valorEnum) {
		if ("1".equals(valorEnum))
			return CUENTA;
		if ("2".equals(valorEnum))
			return TRANSFERENCIA;
		if ("3".equals(valorEnum))
			return USUARIO_ABONADO;
		return null;
	}
}