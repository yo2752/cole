package org.gestoresmadrid.oegam2comun.registradores.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import utilidades.estructuras.Fecha;

public class ReconocimientoDeudaDto implements Serializable {

	private static final long serialVersionUID = 8297668857279679777L;

	private long idReconocimientoDeuda;

	private BigDecimal diaVencimiento;

	private Timestamp fecCreacion;

	private Timestamp fecModificacion;

	private Timestamp fecPrimerVencimiento;

	private Fecha fecPrimerVencimientoReconDeuda;

	private BigDecimal impPlazos;

	private BigDecimal impReconocido;

	private BigDecimal numPlazos;

	private BigDecimal tiempoEntrePagos;

	private BigDecimal idDatosFinancieros;

	public long getIdReconocimientoDeuda() {
		return idReconocimientoDeuda;
	}

	public void setIdReconocimientoDeuda(long idReconocimientoDeuda) {
		this.idReconocimientoDeuda = idReconocimientoDeuda;
	}

	public BigDecimal getDiaVencimiento() {
		return diaVencimiento;
	}

	public void setDiaVencimiento(BigDecimal diaVencimiento) {
		this.diaVencimiento = diaVencimiento;
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

	public Timestamp getFecPrimerVencimiento() {
		return fecPrimerVencimiento;
	}

	public void setFecPrimerVencimiento(Timestamp fecPrimerVencimiento) {
		this.fecPrimerVencimiento = fecPrimerVencimiento;
	}

	public Fecha getFecPrimerVencimientoReconDeuda() {
		return fecPrimerVencimientoReconDeuda;
	}

	public void setFecPrimerVencimientoReconDeuda(Fecha fecPrimerVencimientoReconDeuda) {
		this.fecPrimerVencimientoReconDeuda = fecPrimerVencimientoReconDeuda;
	}

	public BigDecimal getImpPlazos() {
		return impPlazos;
	}

	public void setImpPlazos(BigDecimal impPlazos) {
		this.impPlazos = impPlazos;
	}

	public BigDecimal getImpReconocido() {
		return impReconocido;
	}

	public void setImpReconocido(BigDecimal impReconocido) {
		this.impReconocido = impReconocido;
	}

	public BigDecimal getNumPlazos() {
		return numPlazos;
	}

	public void setNumPlazos(BigDecimal numPlazos) {
		this.numPlazos = numPlazos;
	}

	public BigDecimal getTiempoEntrePagos() {
		return tiempoEntrePagos;
	}

	public void setTiempoEntrePagos(BigDecimal tiempoEntrePagos) {
		this.tiempoEntrePagos = tiempoEntrePagos;
	}

	public BigDecimal getIdDatosFinancieros() {
		return idDatosFinancieros;
	}

	public void setIdDatosFinancieros(BigDecimal idDatosFinancieros) {
		this.idDatosFinancieros = idDatosFinancieros;
	}

}
