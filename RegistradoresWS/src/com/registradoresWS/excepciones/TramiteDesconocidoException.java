package com.registradoresWS.excepciones;

public class TramiteDesconocidoException extends Exception {

	public TramiteDesconocidoException(){}
	
	public TramiteDesconocidoException(String mensaje){
		super(mensaje);
	}
	private static final long serialVersionUID = 1L;

	// Esta excepción será lanzada cuando el servicio web reciba un idTramite
	// desconocido en la base de datos
}
