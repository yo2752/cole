package org.gestoresmadrid.oegam2comun.modelo600_601.view.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "tipoUsufructo", "transmisionCalculado", "transmision", "fechaNacimientoUsu", "compartido", "plazoUsufructo", "numAdquirientes" })
@XmlRootElement(name = "derecho_real")
public class DerechoReal {

	@XmlElement(name = "TipoUsufructo", required = true)
	protected boolean tipoUsufructo;
	@XmlElement(name = "TransmisionCalculado", required = true)
	protected int transmisionCalculado;
	@XmlElement(name = "Transmision", required = true)
	protected int transmision;
	@XmlElement(name = "FechaNacimientoUsu", required = true)
	protected int fechaNacimientoUsu;
	@XmlElement(name = "Compartido", required = true)
	protected int compartido;
	@XmlElement(name = "PlazoUsufructo", required = true)
	protected int plazoUsufructo;
	@XmlElement(name = "NumAdquirientes", required = true)
	protected int numAdquirientes;

	public boolean isTipoUsufructo() {
		return tipoUsufructo;
	}
	public void setTipoUsufructo(boolean tipoUsufructo) {
		this.tipoUsufructo = tipoUsufructo;
	}
	public int getTransmisionCalculado() {
		return transmisionCalculado;
	}
	public void setTransmisionCalculado(int transmisionCalculado) {
		this.transmisionCalculado = transmisionCalculado;
	}
	public int getTransmision() {
		return transmision;
	}
	public void setTransmision(int transmision) {
		this.transmision = transmision;
	}
	public int getFechaNacimientoUsu() {
		return fechaNacimientoUsu;
	}
	public void setFechaNacimientoUsu(int fechaNacimientoUsu) {
		this.fechaNacimientoUsu = fechaNacimientoUsu;
	}
	public int getCompartido() {
		return compartido;
	}
	public void setCompartido(int compartido) {
		this.compartido = compartido;
	}
	public int getPlazoUsufructo() {
		return plazoUsufructo;
	}
	public void setPlazoUsufructo(int plazoUsufructo) {
		this.plazoUsufructo = plazoUsufructo;
	}
	public int getNumAdquirientes() {
		return numAdquirientes;
	}
	public void setNumAdquirientes(int numAdquirientes) {
		this.numAdquirientes = numAdquirientes;
	}

}