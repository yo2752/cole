package org.gestoresmadrid.oegam2comun.modelo600_601.view.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "tipoRenta", "periodicidad", "duracion", "rentaMensual" })
@XmlRootElement(name = "arrendamiento_rustico")
public class ArrendamientoRustico {

	@XmlElement(name = "TipoRenta", required = true)
	protected boolean tipoRenta;
	@XmlElement(name = "Periodicidad", required = true)
	protected int periodicidad;
	@XmlElement(name = "Duracion", required = true)
	protected int duracion;
	@XmlElement(name = "RentaMensual", required = true)
	protected int rentaMensual;

	public boolean isTipoRenta() {
		return tipoRenta;
	}
	public void setTipoRenta(boolean tipoRenta) {
		this.tipoRenta = tipoRenta;
	}
	public int getPeriodicidad() {
		return periodicidad;
	}
	public void setPeriodicidad(int periodicidad) {
		this.periodicidad = periodicidad;
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