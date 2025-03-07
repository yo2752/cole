package org.gestoresmadrid.oegamBajas.btv.view.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Asunto", propOrder = {
    "codigoAsunto",
    "descripcionAsunto"
})
public class Asunto {

    @XmlElement(name = "Codigo",required = true)
    protected String codigoAsunto;
    @XmlElement(name = "Descripcion", required = true)
    protected String descripcionAsunto;
    
	public String getCodigoAsunto() {
		return codigoAsunto;
	}
	public void setCodigoAsunto(String codigoAsunto) {
		this.codigoAsunto = codigoAsunto;
	}
	public String getDescripcionAsunto() {
		return descripcionAsunto;
	}
	public void setDescripcionAsunto(String descripcionAsunto) {
		this.descripcionAsunto = descripcionAsunto;
	}
    

}
