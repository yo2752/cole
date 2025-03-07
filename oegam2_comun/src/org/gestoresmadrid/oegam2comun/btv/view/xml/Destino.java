package org.gestoresmadrid.oegam2comun.btv.view.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Destino", propOrder = {
    "codigoDestino",
    "descripcionDestino"
})
public class Destino {

    @XmlElement(name = "Codigo", required = true)
    protected String codigoDestino;
    @XmlElement(name = "Descripcion", required = true)
    protected String descripcionDestino;
    
	public String getCodigoDestino() {
		return codigoDestino;
	}
	public void setCodigoDestino(String codigoDestino) {
		this.codigoDestino = codigoDestino;
	}
	public String getDescripcionDestino() {
		return descripcionDestino;
	}
	public void setDescripcionDestino(String descripcionDestino) {
		this.descripcionDestino = descripcionDestino;
	}
    
	
}
