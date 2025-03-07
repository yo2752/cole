package org.gestoresmadrid.oegam2comun.modelo600_601.view.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="", propOrder={"declaracion"})
@XmlRootElement(name="remesa")
public class SolicitudPresentacionModelos {
	
	 @XmlAttribute(name = "codigo", required = true)
	 protected String codigo;
	 @XmlAttribute(name = "fec_emision", required = true)
	 protected String fechaEmision;
	 @XmlAttribute(name = "version_xsd", required = false)
	 protected String versionXsd;
	 @XmlAttribute(name = "total_decl", required = false)
	 protected String totalDecl;
	 @XmlAttribute(name = "versioncodigo", required = false)
	 protected String versionCodigo;
	 @XmlElement(name = "declaracion", required = true)
	 protected Declaracion declaracion;
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getFechaEmision() {
		return fechaEmision;
	}
	public void setFechaEmision(String fechaEmision) {
		this.fechaEmision = fechaEmision;
	}
	public String getVersionXsd() {
		return versionXsd;
	}
	public void setVersionXsd(String versionXsd) {
		this.versionXsd = versionXsd;
	}
	public String getTotalDecl() {
		return totalDecl;
	}
	public void setTotalDecl(String totalDecl) {
		this.totalDecl = totalDecl;
	}
	public String getVersionCodigo() {
		return versionCodigo;
	}
	public void setVersionCodigo(String versionCodigo) {
		this.versionCodigo = versionCodigo;
	}
	public Declaracion getDeclaracion() {
		return declaracion;
	}
	public void setDeclaracion(Declaracion declaracion) {
		this.declaracion = declaracion;
	}
}