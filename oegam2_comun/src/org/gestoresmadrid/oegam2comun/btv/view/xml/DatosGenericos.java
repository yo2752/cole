package org.gestoresmadrid.oegam2comun.btv.view.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "organismo",
    "remitente",
    "interesados",
    "asunto",
    "destino"
})
public class DatosGenericos {
	
	@XmlElement(name = "Organismo", required = true)
    protected TipoOrganismo organismo;
    @XmlElement(name = "Remitente", required = true)
    protected DatosRemitente remitente;
    @XmlElement(name = "Interesados", required = true)
    protected DatosInteresado interesados;
    @XmlElement(name = "Asunto", required = true)
    protected Asunto asunto;
    @XmlElement(name = "Destino", required = true)
    protected Destino destino;

    
    public TipoOrganismo getOrganismo() {
		return organismo;
	}

	public void setOrganismo(TipoOrganismo organismo) {
		this.organismo = organismo;
	}

	public DatosRemitente getRemitente() {
		return remitente;
	}

	public void setRemitente(DatosRemitente remitente) {
		this.remitente = remitente;
	}

	public DatosInteresado getInteresados() {
		return interesados;
	}

	public void setInteresados(DatosInteresado interesados) {
		this.interesados = interesados;
	}
	
	public Asunto getAsunto() {
		return asunto;
	}

	public void setAsunto(Asunto asunto) {
		this.asunto = asunto;
	}

	public Destino getDestino() {
		return destino;
	}

	public void setDestino(Destino destino) {
		this.destino = destino;
	}

}
