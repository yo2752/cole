package org.gestoresmadrid.oegam2comun.registradores.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import utilidades.estructuras.Fecha;

public class CuadroAmortizacionDto implements Serializable {

	private static final long serialVersionUID = -565078650653453827L;

	private long idCuadroAmortizacion;

	private Timestamp fecCreacion;

	private Timestamp fecModificacion;

	private Date fechaVencimiento;
	
	private Fecha fechaVencimientoCuadroAmort;

	private BigDecimal impAmortizacion;

	private BigDecimal impCapitalAmortizado;

	private BigDecimal impCapitalPendiente;

	private BigDecimal impCargaFinan;

	private BigDecimal impCuotaNeta;

	private BigDecimal impDistiEntreCuenta;

	private BigDecimal impImpuesto;

	private BigDecimal impInteresDevengado;

	private BigDecimal impInteresesPlazo;

	private BigDecimal impPlazo;

	private BigDecimal impRecupCoste;

	private BigDecimal impRecupCostePte;

	private BigDecimal impTotalCuota;

	private String tipoPlazo;

	private BigDecimal idDatosFinancieros;

	public long getIdCuadroAmortizacion() {
		return idCuadroAmortizacion;
	}

	public void setIdCuadroAmortizacion(long idCuadroAmortizacion) {
		this.idCuadroAmortizacion = idCuadroAmortizacion;
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

	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public Fecha getFechaVencimientoCuadroAmort() {
		return fechaVencimientoCuadroAmort;
	}

	public void setFechaVencimientoCuadroAmort(Fecha fechaVencimientoCuadroAmort) {
		this.fechaVencimientoCuadroAmort = fechaVencimientoCuadroAmort;
	}

	public BigDecimal getImpAmortizacion() {
		return impAmortizacion;
	}

	public void setImpAmortizacion(BigDecimal impAmortizacion) {
		this.impAmortizacion = impAmortizacion;
	}

	public BigDecimal getImpCapitalAmortizado() {
		return impCapitalAmortizado;
	}

	public void setImpCapitalAmortizado(BigDecimal impCapitalAmortizado) {
		this.impCapitalAmortizado = impCapitalAmortizado;
	}

	public BigDecimal getImpCapitalPendiente() {
		return impCapitalPendiente;
	}

	public void setImpCapitalPendiente(BigDecimal impCapitalPendiente) {
		this.impCapitalPendiente = impCapitalPendiente;
	}

	public BigDecimal getImpCargaFinan() {
		return impCargaFinan;
	}

	public void setImpCargaFinan(BigDecimal impCargaFinan) {
		this.impCargaFinan = impCargaFinan;
	}

	public BigDecimal getImpCuotaNeta() {
		return impCuotaNeta;
	}

	public void setImpCuotaNeta(BigDecimal impCuotaNeta) {
		this.impCuotaNeta = impCuotaNeta;
	}

	public BigDecimal getImpDistiEntreCuenta() {
		return impDistiEntreCuenta;
	}

	public void setImpDistiEntreCuenta(BigDecimal impDistiEntreCuenta) {
		this.impDistiEntreCuenta = impDistiEntreCuenta;
	}

	public BigDecimal getImpImpuesto() {
		return impImpuesto;
	}

	public void setImpImpuesto(BigDecimal impImpuesto) {
		this.impImpuesto = impImpuesto;
	}

	public BigDecimal getImpInteresDevengado() {
		return impInteresDevengado;
	}

	public void setImpInteresDevengado(BigDecimal impInteresDevengado) {
		this.impInteresDevengado = impInteresDevengado;
	}

	public BigDecimal getImpInteresesPlazo() {
		return impInteresesPlazo;
	}

	public void setImpInteresesPlazo(BigDecimal impInteresesPlazo) {
		this.impInteresesPlazo = impInteresesPlazo;
	}

	public BigDecimal getImpPlazo() {
		return impPlazo;
	}

	public void setImpPlazo(BigDecimal impPlazo) {
		this.impPlazo = impPlazo;
	}

	public BigDecimal getImpRecupCoste() {
		return impRecupCoste;
	}

	public void setImpRecupCoste(BigDecimal impRecupCoste) {
		this.impRecupCoste = impRecupCoste;
	}

	public BigDecimal getImpRecupCostePte() {
		return impRecupCostePte;
	}

	public void setImpRecupCostePte(BigDecimal impRecupCostePte) {
		this.impRecupCostePte = impRecupCostePte;
	}

	public BigDecimal getImpTotalCuota() {
		return impTotalCuota;
	}

	public void setImpTotalCuota(BigDecimal impTotalCuota) {
		this.impTotalCuota = impTotalCuota;
	}

	public String getTipoPlazo() {
		return tipoPlazo;
	}

	public void setTipoPlazo(String tipoPlazo) {
		this.tipoPlazo = tipoPlazo;
	}

	public BigDecimal getIdDatosFinancieros() {
		return idDatosFinancieros;
	}

	public void setIdDatosFinancieros(BigDecimal idDatosFinancieros) {
		this.idDatosFinancieros = idDatosFinancieros;
	}

}
