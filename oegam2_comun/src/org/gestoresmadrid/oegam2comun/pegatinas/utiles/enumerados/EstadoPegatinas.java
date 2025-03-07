package org.gestoresmadrid.oegam2comun.pegatinas.utiles.enumerados;

public enum EstadoPegatinas {
	
	TODOS(-1,"Todos"),
	PENDIENTE_IMPRESION(0,"Pendiente Impresión"),
	IMPRESA_OK(1,"Impresa");

	private Integer valorEnum;
	private String nombreEnum;
	
	private EstadoPegatinas(Integer valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public Integer getValorEnum() {
		return valorEnum;
	}

	public void setValorEnum(Integer valorEnum) {
		this.valorEnum = valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public void setNombreEnum(String nombreEnum) {
		this.nombreEnum = nombreEnum;
	}

}