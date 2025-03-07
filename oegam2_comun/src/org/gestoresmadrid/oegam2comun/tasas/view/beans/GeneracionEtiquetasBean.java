package org.gestoresmadrid.oegam2comun.tasas.view.beans;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Date;

public class GeneracionEtiquetasBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8776979954164638843L;

	private String numeroTasa;
	private String formaPago;
	private Date fechaOperacion;
	private String idMedioPago;
	private String jefatura;
	private String importeTasa;
	private String tipoTasa;
	private transient BufferedImage codigoBarras;

	public GeneracionEtiquetasBean() {

	}

	public GeneracionEtiquetasBean(String numeroTasa, String formaPago,
			Date fechaOperacion, String idMedioPago, String jefatura,
			String importeTasa, String tipoTasa) {

		this.numeroTasa = numeroTasa;
		this.formaPago = formaPago;
		this.fechaOperacion = fechaOperacion;
		this.idMedioPago = idMedioPago;
		this.jefatura = jefatura;
		this.importeTasa = importeTasa;
		this.tipoTasa = tipoTasa;
	}

	public String getNumeroTasa() {
		return numeroTasa;
	}

	public void setNumeroTasa(String numeroTasa) {
		this.numeroTasa = numeroTasa;
	}

	public String getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}

	public Date getFechaOperacion() {
		return fechaOperacion;
	}

	public void setFechaOperacion(Date fechaOperacion) {
		this.fechaOperacion = fechaOperacion;
	}

	public String getidMedioPago() {
		return idMedioPago;
	}

	public void setidMedioPago(String idMedioPago) {
		this.idMedioPago = idMedioPago;
	}

	public String getJefatura() {
		return jefatura;
	}

	public void setJefatura(String jefatura) {
		this.jefatura = jefatura;
	}

	public String getImporteTasa() {
		return importeTasa;
	}

	public void setImporteTasa(String importeTasa) {
		this.importeTasa = importeTasa;
	}

	public String getTipoTasa() {
		return tipoTasa;
	}

	public void setTipoTasa(String tipoTasa) {
		this.tipoTasa = tipoTasa;
	}

	public BufferedImage getCodigoBarras() {
		return codigoBarras;
	}

	public void setCodigoBarras(BufferedImage codigoBarras) {
		this.codigoBarras = codigoBarras;
	}

}
