package org.gestoresmadrid.oegam2comun.btv.view.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder={"datosFirmados","firma","version"})
@XmlRootElement(name="Solicitud_Registro_Entrada")
public class SolicitudRegistroEntrada {
	
	@XmlElement(name = "Datos_Firmados", required = true)
    protected DatosFirmados datosFirmados;
    @XmlElement(name = "Firma", required = true)
    protected String firma;
    @XmlAttribute(name = "Version", required = true)
    protected String version;
    
    
	public DatosFirmados getDatosFirmados() {
		return datosFirmados;
	}
	public void setDatosFirmados(DatosFirmados datosFirmados) {
		this.datosFirmados = datosFirmados;
	}
	public String getFirma() {
		return firma;
	}
	public void setFirma(String firma) {
		this.firma = firma;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
    
    
}
