package org.gestoresmadrid.oegam2comun.pegatinas.model.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
 
@XmlRootElement (name = "notificacion_impresas")
@XmlAccessorType(XmlAccessType.NONE)
public class RespuestaNotificacionImpresas implements Serializable 
{
    private static final long serialVersionUID = 1L;
 
    @XmlElement(name="resultado")
    private int resultado;
    
    @XmlElement(name="mensaje")
    private String mensaje;
    
    private Exception exception;
    
    public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public void setResultado(int resultado) {
		this.resultado = resultado;
	}

	public int getResultado() {
		return resultado;
	}

	public RespuestaNotificacionImpresas(int resultado, String mensaje) {
        super();
        this.resultado = resultado;
        this.mensaje = mensaje;
    }
     
    public RespuestaNotificacionImpresas(){
         
    }

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

}