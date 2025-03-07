package org.gestoresmadrid.oegam2comun.pegatinas.utiles.enumerados;

public enum EstadoPeticiones {
	
	TODOS(-1,"Todos"),
	SOLICITUD_CREADA(0, "Solicitud Creada"),
	PENDIENTE_RECEPCION(1,"Pendiente Recepción"),
	ANULADO_PENDIENTE(2, "Anulado Pendiente"),
	ANULADO(3,"Anulado"),
	FINALIZADO_PENDIENTE(4, "Finalizado Pendiente"),
	FINALIZADO(5,"Finalizado"),
	FINALIZADO_ERROR(6,"Finalizado con error");

	private Integer valorEnum;
	private String nombreEnum;
	
	private EstadoPeticiones(Integer valorEnum, String nombreEnum) {
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