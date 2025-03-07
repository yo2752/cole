package org.gestoresmadrid.oegam2comun.conductor.view.beans;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.oegam2comun.conductor.view.dto.ConductorDto;
import org.gestoresmadrid.oegam2comun.conductor.view.xml.SolicitudRegistroEntrada;

public class ResultConsultaConductorBean {
    
    private BigDecimal numExpediente;   
    public List<String> listaOK;
    public List<String> listaErrores;
    public String mensaje;
    public Boolean error;
    public Integer numOk;
    public Integer numError;
    public ConductorDto conductorDto;       
    private File ficheroXml;
    private String nombreXml;
    private byte[]  Xml;
    private SolicitudRegistroEntrada solicitudRegistroEntrada;
   
    public void setConsultaConductor(ConductorDto conductorDto) {
        this.conductorDto = conductorDto;
    }
    
    public ResultConsultaConductorBean(Boolean false1) {
       error=false1;
    }

    public void addListaErroresList(List<String> listaMensajes){
        if(listaErrores == null || listaErrores.isEmpty()){
            listaErrores = new ArrayList<String>();
        }
        for(String mensaje : listaMensajes){
            listaErrores.add(mensaje);
        }
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

    public ConductorDto getConductorDto() {
        return conductorDto;
    }

    public void setConductorDto(ConductorDto conductorDto) {
        this.conductorDto = conductorDto;
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

  public SolicitudRegistroEntrada getSolicitudRegistroEntrada() {
    return solicitudRegistroEntrada;
  }

  public void setSolicitudRegistroEntrada(SolicitudRegistroEntrada solicitudRegistroEntrada) {
    this.solicitudRegistroEntrada = solicitudRegistroEntrada;
  }

  public byte[]  getXml() {
    return Xml;
  }

  public void setXml(byte[] bXmlFirmado) {
    Xml = bXmlFirmado;
  }

  


  
    
    
}