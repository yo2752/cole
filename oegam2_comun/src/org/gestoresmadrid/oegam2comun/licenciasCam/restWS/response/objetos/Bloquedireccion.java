
package org.gestoresmadrid.oegam2comun.licenciasCam.restWS.response.objetos;

import java.io.Serializable;

public class Bloquedireccion implements Serializable {

	private static final long serialVersionUID = 6526578607562563052L;
	
    private String infoIntercambioBDC;
    
    private DatosDireccion datosDireccion;

	public String getInfoIntercambioBDC() {
		return infoIntercambioBDC;
	}

	public void setInfoIntercambioBDC(String infoIntercambioBDC) {
		this.infoIntercambioBDC = infoIntercambioBDC;
	}

	public DatosDireccion getDatosDireccion() {
		return datosDireccion;
	}

	public void setDatosDireccion(DatosDireccion datosDireccion) {
		this.datosDireccion = datosDireccion;
	}
}
