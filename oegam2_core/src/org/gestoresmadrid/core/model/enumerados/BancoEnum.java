package org.gestoresmadrid.core.model.enumerados;

public enum BancoEnum {

	BANKIA("2038", "Bankia"), BBVA("0182", "BBVA"), CAIXBANK("2100", "Caixbank"),
	BANCO_POPULAR("0075", "Banco Popular"), BANCO_SABADELL("0081", "Banco Sabadell"),
	BANCO_SANTANDER("0049", "Banco Santander"), CAJAMAR("3058", "Cajamar");

	private String valorEnum;
	private String nombreEnum;

	private BancoEnum(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public static BancoEnum convertir(String valorEnum) {
		if (valorEnum != null && !valorEnum.isEmpty()) {
			for (BancoEnum elemento : BancoEnum.values()) {
				if (elemento.getValorEnum().equals(valorEnum)) {
					return elemento;
				}
			}
		}
		return null;
	}

	public static String convertirNombre(String nombreEnum) {
		if (nombreEnum != null && !nombreEnum.isEmpty()) {
			for (BancoEnum elemento : BancoEnum.values()) {
				if (elemento.getNombreEnum().equals(nombreEnum)) {
					return elemento.getValorEnum();
				}
			}
		}
		return null;
	}

	public static String convertirTexto(String valorEnum) {
		if (valorEnum != null && !valorEnum.isEmpty()) {
			for (BancoEnum elemento : BancoEnum.values()) {
				if (elemento.getValorEnum().equals(valorEnum)) {
					return elemento.getNombreEnum();
				}
			}
		}
		return null;
	}

	/**
	 * @return the valorEnum
	 */
	public String getValorEnum() {
		return valorEnum;
	}

	/**
	 * @return the nombreEnum
	 */
	public String getNombreEnum() {
		return nombreEnum;
	}

}