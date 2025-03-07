package org.gestoresmadrid.oegam2comun.wscn.utiles.enumerados;

public enum TipoEstadoNotificacion {
	
	TODOS(-1,"Todos"),
	SIN_ACUSE(0,"Sin Acuse"),
	ACUSE_SOLICITADO(1,"Acuse Solicitado"),
	NOTIFICADA_ACEPTACION(2,"Notificada por aceptacion"),
	NOTIFICADA_RECHAZO(3,"Notificada por rechazo"),
	NOTIFICADA_TRANSCURSO_PLAZO(4,"Notificada por transcurso de plazo"),
	FINALIZADO_CON_ERROR(5, "Finalizado con error");

	private Integer valorEnum;
	private String nombreEnum;
	
	private TipoEstadoNotificacion(Integer valorEnum, String nombreEnum) {
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