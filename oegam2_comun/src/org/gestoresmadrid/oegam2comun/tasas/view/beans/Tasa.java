package org.gestoresmadrid.oegam2comun.tasas.view.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Tasa implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7756590913561354420L;

	private String codigoTasa;
	private String tipoTasa;
	private Integer idContrato;
	private BigDecimal precio;
	private Date fechaAlta;
	private Date fechaAsignacion;
	private Date fechaFinVigencia;
	private Integer idUsuario;
	private Integer importadoIcogam;

	public Tasa(boolean inicializar) {

	}

	public Tasa() {

	}

	public String getCodigoTasa() {
		return codigoTasa;
	}

	public void setCodigoTasa(String codigoTasa) {
		this.codigoTasa = codigoTasa;
	}

	public String getTipoTasa() {
		return tipoTasa;
	}

	public void setTipoTasa(String tipoTasa) {
		this.tipoTasa = tipoTasa;
	}

	public Integer getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Integer idContrato) {
		this.idContrato = idContrato;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaAsignacion() {
		return fechaAsignacion;
	}

	public void setFechaAsignacion(Date fechaAsignacion) {
		this.fechaAsignacion = fechaAsignacion;
	}

	public Date getFechaFinVigencia() {
		return fechaFinVigencia;
	}

	public void setFechaFinVigencia(Date fechaFinVigencia) {
		this.fechaFinVigencia = fechaFinVigencia;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Integer getImportadoIcogam() {
		return importadoIcogam;
	}

	public void setImportadoIcogam(Integer importadoIcogam) {
		this.importadoIcogam = importadoIcogam;
	}

}
