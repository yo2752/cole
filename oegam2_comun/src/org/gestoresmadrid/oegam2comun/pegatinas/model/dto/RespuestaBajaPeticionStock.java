package org.gestoresmadrid.oegam2comun.pegatinas.model.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
 
@XmlRootElement (name = "anular_peticion_stock")
@XmlAccessorType(XmlAccessType.NONE)
public class RespuestaBajaPeticionStock implements Serializable 
{
    private static final long serialVersionUID = 1L;
 
    @XmlElement(name="resultado")
    private int resultado;
    
    @XmlElement(name="mensaje")
    private String mensaje;
    
    private Exception exception;
     
    public RespuestaBajaPeticionStock(int resultado, String mensaje) {
        super();
        this.resultado = resultado;
        this.mensaje = mensaje;
    }
     
    public RespuestaBajaPeticionStock(){
         
    }

	public int getResultado() {
		return resultado;
	}

	public void setResultado(int resultado) {
		this.resultado = resultado;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}
	
	
}