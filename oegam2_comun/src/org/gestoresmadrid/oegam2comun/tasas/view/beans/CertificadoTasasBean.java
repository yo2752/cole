package org.gestoresmadrid.oegam2comun.tasas.view.beans;

import java.io.Serializable;

import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;
import org.gestoresmadrid.oegamComun.tasa.view.dto.TasaDto;

public class CertificadoTasasBean implements Serializable{

	private static final long serialVersionUID = -1102594481435314378L;

	private PersonaDto titularVehiculo;
	private PersonaDto titularFacturacion;
	private TasaDto tasa;
	private String matricula;
	
	public CertificadoTasasBean(PersonaDto titularVehiculo,	PersonaDto titularFacturacion, TasaDto tasa, String matricula) {
		this.titularVehiculo = titularVehiculo;
		this.titularFacturacion = titularFacturacion;
		this.tasa = tasa;
		this.matricula = matricula;
	}
	public PersonaDto getTitularVehiculo() {
		return titularVehiculo;
	}
	public void setTitularVehiculo(PersonaDto titularVehiculo) {
		this.titularVehiculo = titularVehiculo;
	}
	public PersonaDto getTitularFacturacion() {
		return titularFacturacion;
	}
	public void setTitularFacturacion(PersonaDto titularFacturacion) {
		this.titularFacturacion = titularFacturacion;
	}
	public TasaDto getTasa() {
		return tasa;
	}
	public void setTasa(TasaDto tasa) {
		this.tasa = tasa;
	}
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	
}