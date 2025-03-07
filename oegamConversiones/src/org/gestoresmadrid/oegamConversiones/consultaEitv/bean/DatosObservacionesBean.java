package org.gestoresmadrid.oegamConversiones.consultaEitv.bean;

import java.io.Serializable;

public class DatosObservacionesBean implements Serializable{

	private static final long serialVersionUID = 2787172465925522312L;

    private String codprocedencia;
    private String textobservaciones;
    
	public DatosObservacionesBean() {
		super();
	}

	public String getCodprocedencia() {
		return codprocedencia;
	}

	public void setCodprocedencia(String codprocedencia) {
		this.codprocedencia = codprocedencia;
	}

	public String getTextobservaciones() {
		return textobservaciones;
	}

	public void setTextobservaciones(String textobservaciones) {
		this.textobservaciones = textobservaciones;
	}
	
}
