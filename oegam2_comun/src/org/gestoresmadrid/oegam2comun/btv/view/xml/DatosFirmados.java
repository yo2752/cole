package org.gestoresmadrid.oegam2comun.btv.view.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "datosGenericos",
    "datosEspecificos"
})
@XmlRootElement(name = "Datos_Firmados")
public class DatosFirmados {

    @XmlElement(name = "Datos_Genericos", required = true)
    protected DatosGenericos datosGenericos;
    @XmlElement(name = "Datos_Especificos", required = true)
    protected DatosEspecificos datosEspecificos;
    
    
	public DatosGenericos getDatosGenericos() {
		return datosGenericos;
	}
	public void setDatosGenericos(DatosGenericos datosGenericos) {
		this.datosGenericos = datosGenericos;
	}
	public DatosEspecificos getDatosEspecificos() {
		return datosEspecificos;
	}
	public void setDatosEspecificos(DatosEspecificos datosEspecificos) {
		this.datosEspecificos = datosEspecificos;
	}
    
    

}
