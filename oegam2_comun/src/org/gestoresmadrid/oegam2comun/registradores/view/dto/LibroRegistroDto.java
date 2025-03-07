package org.gestoresmadrid.oegam2comun.registradores.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import utilidades.estructuras.Fecha;

public class LibroRegistroDto implements Serializable{

	private static final long serialVersionUID = 4076012979614849109L;

	private long idLibro;

	private String nombreLibro;

	private String nombreFichero;

	private BigDecimal numero;

	private Date fecApertura;

	private Fecha fecAperturaLibro;

	private Date fecCierre;

	private Fecha fecCierreLibro;

	private Date fecCierreAnterior;

	private Fecha fecCierreAnteriorLibro;

	private BigDecimal idTramiteRegistro;

	public long getIdLibro() {
		return idLibro;
	}

	public void setIdLibro(long idLibro) {
		this.idLibro = idLibro;
	}

	public String getNombreLibro() {
		return nombreLibro;
	}

	public void setNombreLibro(String nombreLibro) {
		this.nombreLibro = nombreLibro;
	}

	public String getNombreFichero() {
		return nombreFichero;
	}

	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}

	public BigDecimal getNumero() {
		return numero;
	}

	public void setNumero(BigDecimal numero) {
		this.numero = numero;
	}

	public Date getFecApertura() {
		return fecApertura;
	}

	public void setFecApertura(Date fecApertura) {
		this.fecApertura = fecApertura;
	}

	public Fecha getFecAperturaLibro() {
		return fecAperturaLibro;
	}

	public void setFecAperturaLibro(Fecha fecAperturaLibro) {
		this.fecAperturaLibro = fecAperturaLibro;
	}

	public Date getFecCierre() {
		return fecCierre;
	}

	public void setFecCierre(Date fecCierre) {
		this.fecCierre = fecCierre;
	}

	public Fecha getFecCierreLibro() {
		return fecCierreLibro;
	}

	public void setFecCierreLibro(Fecha fecCierreLibro) {
		this.fecCierreLibro = fecCierreLibro;
	}

	public Date getFecCierreAnterior() {
		return fecCierreAnterior;
	}

	public void setFecCierreAnterior(Date fecCierreAnterior) {
		this.fecCierreAnterior = fecCierreAnterior;
	}

	public Fecha getFecCierreAnteriorLibro() {
		return fecCierreAnteriorLibro;
	}

	public void setFecCierreAnteriorLibro(Fecha fecCierreAnteriorLibro) {
		this.fecCierreAnteriorLibro = fecCierreAnteriorLibro;
	}

	public BigDecimal getIdTramiteRegistro() {
		return idTramiteRegistro;
	}

	public void setIdTramiteRegistro(BigDecimal idTramiteRegistro) {
		this.idTramiteRegistro = idTramiteRegistro;
	}

}
