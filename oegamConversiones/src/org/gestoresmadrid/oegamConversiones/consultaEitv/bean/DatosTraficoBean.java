package org.gestoresmadrid.oegamConversiones.consultaEitv.bean;

import java.io.Serializable;

public class DatosTraficoBean implements Serializable{

	private static final long serialVersionUID = 8985526859816782801L;
	
	private String matricula;
	private String servicio;
    private String relopciones;
    
	public DatosTraficoBean() {
		super();
	}
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public String getServicio() {
		return servicio;
	}
	public void setServicio(String servicio) {
		this.servicio = servicio;
	}
	public String getRelopciones() {
		return relopciones;
	}
	public void setRelopciones(String relopciones) {
		this.relopciones = relopciones;
	}
}
