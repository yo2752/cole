package org.gestoresmadrid.core.model.enumerados;

public enum TextoNotificacion {

	Cambio_Estado("CAMBIO_ESTADO", "CAMBIO DE ESTADO") {};

	private String valorEnum;
	private String nombreEnum;

	private TextoNotificacion(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public static TextoNotificacion convertir(String valorEnum) {
		if (valorEnum != null) {
			for (TextoNotificacion element : TextoNotificacion.values()) {
				if (element.valorEnum.equals(valorEnum)) {
					return element;
				}
			}
		}
		return null;
	}

	public static String convertirTexto(String valorEnum) {
		TextoNotificacion conceptoCreditoFacturado = convertir(valorEnum);
		return conceptoCreditoFacturado != null ? conceptoCreditoFacturado.nombreEnum : null;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public String toString() {
		return nombreEnum;
	}
}
