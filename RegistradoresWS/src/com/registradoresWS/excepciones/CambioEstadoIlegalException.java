package com.registradoresWS.excepciones;

public class CambioEstadoIlegalException extends Exception{

	public CambioEstadoIlegalException(){}
	
	public CambioEstadoIlegalException(String mensaje){
		super(mensaje);
	}
	
	private static final long serialVersionUID = 1L;
	
	// Esta excepción será lanzada cuando el servicio web intente
	// establecer en un trámite un estado ilegal, es decir saltándose
	// de alguna forma los estados por los que tiene que pasar el trámite
	// durante el intercambio normal de mensajes con el corpme
}
