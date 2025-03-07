package trafico.beans.daos.pq_facturacion;

import org.gestoresmadrid.oegam2comun.tasas.view.beans.Tasa;

import escrituras.beans.Persona;

public class TablaFacturacionBean {

	private Persona titularVehiculo;
	private Persona titularFacturacion;
	private Tasa tasa;
	private String matricula;

	public TablaFacturacionBean() {
	}

	public TablaFacturacionBean(Persona titularVehiculo, Tasa tasa, Persona titularFacturacion, String matricula) {
		this.titularVehiculo = titularVehiculo;
		this.titularFacturacion = titularFacturacion;
		this.tasa = tasa;
		this.matricula = matricula;
	}

	public Persona getTitularVehiculo() {
		return titularVehiculo;
	}

	public void setTitularVehiculo(Persona titularVehiculo) {
		this.titularVehiculo = titularVehiculo;
	}

	public Persona getTitularFacturacion() {
		return titularFacturacion;
	}

	public void setTitularFacturacion(Persona titularFacturacion) {
		this.titularFacturacion = titularFacturacion;
	}

	public Tasa getTasa() {
		return tasa;
	}

	public void setTasa(Tasa tasa) {
		this.tasa = tasa;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

}