package org.gestoresmadrid.core.general.model.vo;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the ENVIO_DATA database table.
 */
@Embeddable
public class EnvioDataPK implements Serializable {

	private static final long serialVersionUID = 5913508554009961971L;

	private String correcta;

	private String proceso;
	
	private String cola;

	public EnvioDataPK() {}

	public String getCorrecta() {
		return this.correcta;
	}

	public void setCorrecta(String correcta) {
		this.correcta = correcta;
	}

	public String getProceso() {
		return this.proceso;
	}

	public void setProceso(String proceso) {
		this.proceso = proceso;
	}
	
	public String getCola() {
		return this.cola;
	}

	public void setCola(String cola) {
		this.cola = cola;
	}
	
}