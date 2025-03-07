package org.gestoresmadrid.oegam2comun.wscn.utiles.enumerados;

public enum TipoRolNotificacion {
	
	TODOS(0, "Todos"),
	PROPIO(1,"Propio"), 
	AUTORIZADO_RED(2, "Autorizado Red"), 
	APODERADO(3, "Apoderado");
	
	private String nombreEnum;
	private Integer valorEnum;
	
	private TipoRolNotificacion(Integer valorEnum, String nombreEnum) {
		this.nombreEnum = nombreEnum;
		this.valorEnum = valorEnum;
	}
	
	public String getNombreEnum() {
		return nombreEnum;
	}
	
	public void setNombreEnum(String nombreEnum) {
		this.nombreEnum = nombreEnum;
	}
	
	public Integer getValorEnum() {
		return valorEnum;
	}
	
	public void setValorEnum(Integer valorEnum) {
		this.valorEnum = valorEnum;
	}
	
}