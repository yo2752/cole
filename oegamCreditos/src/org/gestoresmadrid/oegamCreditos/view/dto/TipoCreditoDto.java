package org.gestoresmadrid.oegamCreditos.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class TipoCreditoDto implements Serializable {

	private static final long serialVersionUID = 6518018193034669165L;

	private String tipoCredito;

	private String descripcion;

	private BigDecimal estado;

	private BigDecimal importe;

	private String increDecre;

	private BigDecimal ordenListado;

	public String getTipoCredito() {
		return this.tipoCredito;
	}

	public void setTipoCredito(String tipoCredito) {
		this.tipoCredito = tipoCredito;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public BigDecimal getEstado() {
		return this.estado;
	}

	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}

	public BigDecimal getImporte() {
		return this.importe;
	}

	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	public String getIncreDecre() {
		return this.increDecre;
	}

	public void setIncreDecre(String increDecre) {
		this.increDecre = increDecre;
	}

	public BigDecimal getOrdenListado() {
		return this.ordenListado;
	}

	public void setOrdenListado(BigDecimal ordenListado) {
		this.ordenListado = ordenListado;
	}

}