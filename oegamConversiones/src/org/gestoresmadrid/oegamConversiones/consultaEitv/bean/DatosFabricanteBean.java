package org.gestoresmadrid.oegamConversiones.consultaEitv.bean;

import java.io.Serializable;

public class DatosFabricanteBean implements Serializable{

	private static final long serialVersionUID = 7025463518672541681L;

	private String numcertificado;
    private String numerovin;
    private String controlvin;
    private String codigoitv;
    private String clasificacion;
    private String criterioutilizacion;
	
    public DatosFabricanteBean() {
		super();
	}

	public String getNumcertificado() {
		return numcertificado;
	}

	public void setNumcertificado(String numcertificado) {
		this.numcertificado = numcertificado;
	}

	public String getNumerovin() {
		return numerovin;
	}

	public void setNumerovin(String numerovin) {
		this.numerovin = numerovin;
	}

	public String getControlvin() {
		return controlvin;
	}

	public void setControlvin(String controlvin) {
		this.controlvin = controlvin;
	}

	public String getCodigoitv() {
		return codigoitv;
	}

	public void setCodigoitv(String codigoitv) {
		this.codigoitv = codigoitv;
	}

	public String getClasificacion() {
		return clasificacion;
	}

	public void setClasificacion(String clasificacion) {
		this.clasificacion = clasificacion;
	}

	public String getCriterioutilizacion() {
		return criterioutilizacion;
	}

	public void setCriterioutilizacion(String criterioutilizacion) {
		this.criterioutilizacion = criterioutilizacion;
	}
    
}
