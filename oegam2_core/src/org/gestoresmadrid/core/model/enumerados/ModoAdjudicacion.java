package org.gestoresmadrid.core.model.enumerados;

public enum ModoAdjudicacion {

	tipo1("1", "Transmisión"),
	tipo2("2", "Adjudicación Judicial o subasta"),
	tipo3("3", "Fallecimiento o donación del titular"),
	tipo4("4", "Otros");

	private String valorEnum;
	private String nombreEnum;

	private ModoAdjudicacion(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public void setValor(String valorEnum) {
		this.valorEnum = valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public void setNombre(String nombreEnum) {
		this.nombreEnum = nombreEnum;
	}

	@Override
	public String toString() {
		return valorEnum;
	}

	// valueOf
	public static ModoAdjudicacion convertir(Integer valorEnum) {
		return valorEnum != null ? convertir(Integer.toString(valorEnum)) : null;
	}

	// valueOf
	public static ModoAdjudicacion convertir(String valorEnum) {
		for (ModoAdjudicacion m : ModoAdjudicacion.values()) {
			if (m.valorEnum.equals(valorEnum)) {
				return m;
			}
		}
		return null;
	}

	public static String convertirTexto(String valorEnum) {
		ModoAdjudicacion m = convertir(valorEnum);
		return m != null ? m.nombreEnum : null;
	}

}
