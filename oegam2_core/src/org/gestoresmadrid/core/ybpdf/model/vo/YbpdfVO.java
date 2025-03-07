package org.gestoresmadrid.core.ybpdf.model.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the YBPDF database table.
 */
@Entity
@Table(name = "Ybpdf")
public class YbpdfVO implements Serializable {

	private static final long serialVersionUID = 5460965354288497518L;

	@Id
	@Column(name = "ID_YBPDF")
	private String idYbpdf;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_ENVIO")
	private Date fecha;

	@Column(name = "NUM_COLEGIADO", updatable = false)
	private String numColegiado;

	@Column(name = "CARPETA", updatable = false)
	private String carpeta;

	@Column(name = "ENVIARAYB")
	private int enviarayb;

	public YbpdfVO() {}

	public String getIdYbpdf() {
		return this.idYbpdf;
	}

	public void setIdYbpdf(String idYbpdf) {
		this.idYbpdf = idYbpdf;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getCarpeta() {
		return carpeta;
	}

	public void setCarpeta(String carpeta) {
		this.carpeta = carpeta;
	}

	public int getEnviarayb() {
		return enviarayb;
	}

	public void setEnviarayb(int enviarayb) {
		this.enviarayb = enviarayb;
	}
}