package org.gestoresmadrid.oegam2comun.pegatinas.model.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
 
@XmlRootElement (name = "peticion_stock")
@XmlAccessorType(XmlAccessType.NONE)
public class RespuestaPeticionStock implements Serializable 
{
    private static final long serialVersionUID = 1L;
 
    @XmlElement(name="resultado")
    private int resultado;
     
    @XmlElement(name="identificador_peticion")
    private String identificadorPeticion;     
    
    @XmlElement(name="mensaje")
    private String mensaje;
    
    private Exception exception;
     
    public RespuestaPeticionStock(int resultado, String identificadorPeticion, String mensaje) {
        super();
        this.resultado = resultado;
        this.identificadorPeticion = identificadorPeticion;
        this.mensaje = mensaje;
    }
     
    public RespuestaPeticionStock(){
         
    }

	public int getResultado() {
		return resultado;
	}

	public void setResultado(int resultado) {
		this.resultado = resultado;
	}

	public String getIdentificadorPeticion() {
		return identificadorPeticion;
	}

	public void setIdentificadorPeticion(String identificadorPeticion) {
		this.identificadorPeticion = identificadorPeticion;
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