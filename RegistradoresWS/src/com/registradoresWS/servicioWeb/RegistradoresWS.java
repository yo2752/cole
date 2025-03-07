package com.registradoresWS.servicioWeb;

import com.registradoresWS.gestion.GestionComunicacion;


public class RegistradoresWS {	

	
	public byte[] wsOegam(byte[]datosEntrada){
		GestionComunicacion gestionComunicacion=new GestionComunicacion();
		return gestionComunicacion.wsOegam(datosEntrada); 
	}

	public boolean actualizarACK(byte[]datosEntrada){
		GestionComunicacion gestionComunicacion=new GestionComunicacion();
		return gestionComunicacion.actualizarACK(datosEntrada);
	}
}
