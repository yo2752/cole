package org.gestoresmadrid.oegam2comun.arrendatarios.view.beans;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.oegam2comun.arrendatarios.view.dto.ArrendatarioDto;
import org.gestoresmadrid.oegam2comun.arrendatarios.view.xml.SolicitudRegistroEntrada;

public class ResultConsultaArrendatarioBean implements Serializable{
	
	private static final long serialVersionUID = 6723112354670617837L;
	
	private BigDecimal numExpediente;   
    public List<String> listaOK;
    public List<String> listaErrores;
    public String mensaje;
    public Boolean error;
    public Integer numOk;
    public Integer numError;
    public ArrendatarioDto arrendatarioDto;  
    private File ficheroXml;
    private String nombreXml;
    private SolicitudRegistroEntrada solicitudRegistroEntrada;
    private byte[] xml;
    
	public ResultConsultaArrendatarioBean(Boolean error) {
        this.error=error;
    }

    public void addListaError(String mensaje){
        if(listaErrores == null){
            listaErrores =  new ArrayList<String>();
        }
        listaErrores.add(mensaje);
    }
    public void addListaOk(String mensaje){
        if(listaOK == null){
            listaOK =  new ArrayList<String>();
        }
        listaOK.add(mensaje);
    }
    
    
    public List<String> getListaOK() {
        return listaOK;
    }

    public void setListaOK(List<String> listaOK) {
        this.listaOK = listaOK;
    }

    public List<String> getListaErrores() {
        return listaErrores;
    }

    public void setListaErrores(List<String> listaErrores) {
        this.listaErrores = listaErrores;
    }   
    
    public Integer getNumOk() {
        return numOk;
    }

    public void setNumOk(Integer numOk) {
        this.numOk = numOk;
    }

    public Integer getNumError() {
        return numError;
    }

    public void setNumError(Integer numError) {
        this.numError = numError;
    }

    public void addOk(){
        if(numOk== null){
            numOk = 0;
        }
        numOk++;
    }
    
    public void addError(){
        if(numError== null){
            numError = 0;
        }
        numError++;
    }
      
    public BigDecimal getNumExpediente() {
        return numExpediente;
    }

    public void setNumExpediente(BigDecimal numExpediente) {
        this.numExpediente = numExpediente;
    }

    public String getMensaje() {
        return mensaje;
    }
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    public Boolean getError() {
        return error;
    }
    public void setError(Boolean error) {
        this.error = error;
    }

	public File getFicheroXml() {
		return ficheroXml;
	}

	public void setFicheroXml(File ficheroXml) {
		this.ficheroXml = ficheroXml;
	}

	public String getNombreXml() {
		return nombreXml;
	}

	public void setNombreXml(String nombreXml) {
		this.nombreXml = nombreXml;
	}

	public ArrendatarioDto getArrendatarioDto() {
		return arrendatarioDto;
	}

	public void setArrendatarioDto(ArrendatarioDto arrendatarioDto) {
		this.arrendatarioDto = arrendatarioDto;
	}

	public SolicitudRegistroEntrada getSolicitudRegistroEntrada() {
		return solicitudRegistroEntrada;
	}

	public void setSolicitudRegistroEntrada(SolicitudRegistroEntrada solicitudRegistroEntrada) {
		this.solicitudRegistroEntrada = solicitudRegistroEntrada;
	}

	public byte[] getXml() {
		return xml;
	}

	public void setXml(byte[] xml) {
		this.xml = xml;
	}

	  
    
    
    
	
}
