package org.gestoresmadrid.oegam2comun.tasas.view.beans;

import java.math.BigDecimal;

/**
 * Bean empleado para comprar una cantidad N de tasas de un grupo y tipo determinado
 */
public class TasasSolicitadasBean {

	private Long idDesglose;
	private String codigoTasa;
	private Integer grupo;
	private String tipo;
	private String descripcion;
	private BigDecimal cantidad;
	private BigDecimal importe;

	public String getCodigoTasa() {
		return codigoTasa;
	}

	public void setCodigoTasa(String codigoTasa) {
		this.codigoTasa = codigoTasa;
	}

	public Integer getGrupo() {
		return grupo;
	}

	public void setGrupo(Integer grupo) {
		this.grupo = grupo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public BigDecimal getCantidad() {
		return cantidad;
	}

	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}

	public BigDecimal getImporte() {
		return importe;
	}

	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Long getIdDesglose() {
		return idDesglose;
	}

	public void setIdDesglose(Long idDesglose) {
		this.idDesglose = idDesglose;
	}

	public BigDecimal getSubTotal() {
		return (importe != null && cantidad != null) ? importe.multiply(cantidad) : BigDecimal.ZERO;
	}
}
