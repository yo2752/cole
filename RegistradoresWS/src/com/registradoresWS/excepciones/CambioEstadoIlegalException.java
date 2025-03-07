package com.registradoresWS.excepciones;

public class CambioEstadoIlegalException extends Exception{

	public CambioEstadoIlegalException(){}
	
	public CambioEstadoIlegalException(String mensaje){
		super(mensaje);
	}
	
	private static final long serialVersionUID = 1L;
	
	// Esta excepci�n ser� lanzada cuando el servicio web intente
	// establecer en un tr�mite un estado ilegal, es decir salt�ndose
	// de alguna forma los estados por los que tiene que pasar el tr�mite
	// durante el intercambio normal de mensajes con el corpme
}
