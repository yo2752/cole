package trafico.utiles.enumerados;

public enum TipoTransferencia {

	/*
	 * MOD CFS 24-04-2012 - Desglose de Trámites CTI
	 * 
	 * Se cambian los tipos de trámites de CTI.
	 * 
	 * 	tipo 1: se deslgosa en los nuevos tipos 1, 2 y 3.
	 * 	tipo 4: es el antiguo tipo 2.
	 * 	tipo 5: es el antiguo tipo 3.
	 * 
	 */

	tipo1("1", "Cambio Titularidad Completo"),
	tipo2("2", "Finalización tras una notificación"),
	tipo3("3", "Interviene Compraventa"),
	tipo4("4", "Notificación de cambio de titularidad"),
	tipo5("5", "Entrega Compraventa");

	private String valorEnum;
	private String nombreEnum;

	private TipoTransferencia(String valorEnum, String nombreEnum) {
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
	public static TipoTransferencia convertir(Integer valorEnum) {
		return valorEnum != null ? convertir(Integer.toString(valorEnum)) : null;
	}

	// valueOf
	public static TipoTransferencia convertir(String valorEnum) {
		for (TipoTransferencia tt : TipoTransferencia.values()) {
			if (tt.valorEnum.equals(valorEnum)) {
				return tt;
			}
		}
		return null;
	}

	public static String convertirTexto(String valorEnum) {
		TipoTransferencia tt = convertir(valorEnum);
		return tt != null ? tt.nombreEnum : null;
	}

}