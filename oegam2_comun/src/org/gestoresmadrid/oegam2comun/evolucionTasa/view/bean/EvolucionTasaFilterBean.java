package org.gestoresmadrid.oegam2comun.evolucionTasa.view.bean;

import java.io.Serializable;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;

public class EvolucionTasaFilterBean implements Serializable {


	private static final long serialVersionUID = -9132608988627513430L;
			
	
	@FilterSimpleExpression(name="id.codigoTasa")
	private String codigoTasa;


	public String getCodigoTasa() {
		return codigoTasa;
	}


	public void setCodigoTasa(String codigoTasa) {
		this.codigoTasa = codigoTasa;
	}
}
