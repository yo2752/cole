package org.gestoresmadrid.oegam2comun.registradores.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class ComisionDto implements Serializable {

	private static final long serialVersionUID = 1553818848804053043L;
	
	private long idComision;

	private String condicionAplicacion;

	private Timestamp fecCreacion;

	private Timestamp fecModificacion;

	private String financiado;

	private BigDecimal importeMaximo;

	private BigDecimal importeMinimo;

	private BigDecimal porcentaje;

	private String tipoComision;

	private BigDecimal idDatosFinancieros;

	public long getIdComision() {
		return idComision;
	}

	public void setIdComision(long idComision) {
		this.idComision = idComision;
	}

	public String getCondicionAplicacion() {
		return condicionAplicacion;
	}

	public void setCondicionAplicacion(String condicionAplicacion) {
		this.condicionAplicacion = condicionAplicacion;
	}

	public Timestamp getFecCreacion() {
		return fecCreacion;
	}

	public void setFecCreacion(Timestamp fecCreacion) {
		this.fecCreacion = fecCreacion;
	}

	public Timestamp getFecModificacion() {
		return fecModificacion;
	}

	public void setFecModificacion(Timestamp fecModificacion) {
		this.fecModificacion = fecModificacion;
	}

	public String getFinanciado() {
		return financiado;
	}

	public void setFinanciado(String financiado) {
		this.financiado = financiado;
	}

	public BigDecimal getImporteMaximo() {
		return importeMaximo;
	}

	public void setImporteMaximo(BigDecimal importeMaximo) {
		this.importeMaximo = importeMaximo;
	}

	public BigDecimal getImporteMinimo() {
		return importeMinimo;
	}

	public void setImporteMinimo(BigDecimal importeMinimo) {
		this.importeMinimo = importeMinimo;
	}

	public BigDecimal getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(BigDecimal porcentaje) {
		this.porcentaje = porcentaje;
	}

	public String getTipoComision() {
		return tipoComision;
	}

	public void setTipoComision(String tipoComision) {
		this.tipoComision = tipoComision;
	}

	public BigDecimal getIdDatosFinancieros() {
		return idDatosFinancieros;
	}

	public void setIdDatosFinancieros(BigDecimal idDatosFinancieros) {
		this.idDatosFinancieros = idDatosFinancieros;
	}

}
