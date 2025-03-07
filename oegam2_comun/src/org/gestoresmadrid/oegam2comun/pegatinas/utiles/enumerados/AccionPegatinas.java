package org.gestoresmadrid.oegam2comun.pegatinas.utiles.enumerados;

public enum AccionPegatinas {
	
	TODOS("", "Todos"),
	IMPRESION("Impresión","Impresión"),
	IMPRESION_ERRONEA("Impresión errónea","Impresión errónea"),
	STOCK_RECIBIDO("Stock Recibido","Stock Recibido"),
	STOCK_ANULADO("Stock Anulado","Stock Anulado");
	
	
	private String valorEnum;
	private String nombreEnum;
	
	private AccionPegatinas(String valorEnum, String nombreEnum) {
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

}