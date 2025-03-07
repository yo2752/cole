package org.gestoresmadrid.oegam2comun.modelo600_601.view.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "viviendaHabitual", "duracion", "rentaMensual" })
@XmlRootElement(name = "arrendamiento_urbano")
public class ArrendamientoUrbano {

	@XmlElement(name = "ViviendaHabitual", required = true)
	protected boolean viviendaHabitual;
	@XmlElement(name = "Duracion", required = true)
	protected int duracion;
	@XmlElement(name = "RentaMensual", required = true)
	protected int rentaMensual;

	public boolean isViviendaHabitual() {
		return viviendaHabitual;
	}
	public void setViviendaHabitual(boolean viviendaHabitual) {
		this.viviendaHabitual = viviendaHabitual;
	}
	public int getDuracion() {
		return duracion;
	}
	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}
	public int getRentaMensual() {
		return rentaMensual;
	}
	public void setRentaMensual(int rentaMensual) {
		this.rentaMensual = rentaMensual;
	}

}