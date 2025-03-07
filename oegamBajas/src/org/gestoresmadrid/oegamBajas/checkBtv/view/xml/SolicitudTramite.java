package org.gestoresmadrid.oegamBajas.checkBtv.view.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder={"datosFirmados","firmaGestor","version"})
@XmlRootElement(name="Solicitud_Tramite")
public class SolicitudTramite {
	
    @XmlElement(name = "Datos_Firmados", required = true)
    protected DatosFirmados datosFirmados;
    @XmlElement(name = "Firma_Gestor", required = true)
    protected String firmaGestor;
    @XmlAttribute(name = "Version", required = true)
    protected String version;
	/**
	 * @return the datosFirmados
	 */
	public DatosFirmados getDatosFirmados() {
		return datosFirmados;
	}
	/**
	 * @param datosFirmados the datosFirmados to set
	 */
	public void setDatosFirmados(DatosFirmados datosFirmados) {
		this.datosFirmados = datosFirmados;
	}
	/**
	 * @return the firmaGestor
	 */
	public String getFirmaGestor() {
		return firmaGestor;
	}
	/**
	 * @param firmaGestor the firmaGestor to set
	 */
	public void setFirmaGestor(String firmaGestor) {
		this.firmaGestor = firmaGestor;
	}
	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}
	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

}
