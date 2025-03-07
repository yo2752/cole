package org.gestoresmadrid.oegamDuplicadoPermisoConducir.view.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class FicheroInfoBean implements Serializable {

	private static final long serialVersionUID = -2991622141877577871L;

	private BigDecimal numExpediente;

	private String nombreFichero;

	private String puedeEliminarse;

	private String tipo;

	private String estado;

	public FicheroInfoBean(String nombreFichero) {
		super();
		this.nombreFichero = nombreFichero;
	}

	public FicheroInfoBean(BigDecimal numExpediente, String nombreFichero, String tipo, String puedeEliminarse, String estado) {
		super();
		this.numExpediente = numExpediente;
		this.nombreFichero = nombreFichero;
		this.puedeEliminarse = puedeEliminarse;
		this.tipo = tipo;
		this.estado = estado;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getNombreFichero() {
		return nombreFichero;
	}

	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}

	public String getPuedeEliminarse() {
		return puedeEliminarse;
	}

	public void setPuedeEliminarse(String puedeEliminarse) {
		this.puedeEliminarse = puedeEliminarse;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
}
