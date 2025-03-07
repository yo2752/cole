package org.gestoresmadrid.oegam2comun.impresion.masiva.view.dto;

import java.io.Serializable;

import utilidades.estructuras.Fecha;
import utilidades.estructuras.FechaFraccionada;

public class ImpresionMasivaDto implements Serializable {

	private static final long serialVersionUID = 2057751211220248809L;

	private Integer idImpresionMasiva;

	private String rutaFichero;

	private String nombreFichero;

	private String tipoDocumento;

	private Integer estadoImpresion;

	private String numColegiado;

	private FechaFraccionada fechaFiltradoEnviadoProceso;

	private Fecha fechaEnviadoProceso;

	private FechaFraccionada fechaFiltradoAltaBBDD;

	private Fecha fechaAltaBBDD;

	public Integer getIdImpresionMasiva() {
		return idImpresionMasiva;
	}

	public void setIdImpresionMasiva(Integer idImpresionMasiva) {
		this.idImpresionMasiva = idImpresionMasiva;
	}

	public String getRutaFichero() {
		return rutaFichero;
	}

	public void setRutaFichero(String rutaFichero) {
		this.rutaFichero = rutaFichero;
	}

	public String getNombreFichero() {
		return nombreFichero;
	}

	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public Integer getEstadoImpresion() {
		return estadoImpresion;
	}

	public void setEstadoImpresion(Integer estadoImpresion) {
		this.estadoImpresion = estadoImpresion;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public FechaFraccionada getFechaFiltradoEnviadoProceso() {
		return fechaFiltradoEnviadoProceso;
	}

	public void setFechaFiltradoEnviadoProceso(FechaFraccionada fechaFiltradoEnviadoProceso) {
		this.fechaFiltradoEnviadoProceso = fechaFiltradoEnviadoProceso;
	}

	public Fecha getFechaEnviadoProceso() {
		return fechaEnviadoProceso;
	}

	public void setFechaEnviadoProceso(Fecha fechaEnviadoProceso) {
		this.fechaEnviadoProceso = fechaEnviadoProceso;
	}

	public FechaFraccionada getFechaFiltradoAltaBBDD() {
		return fechaFiltradoAltaBBDD;
	}

	public void setFechaFiltradoAltaBBDD(FechaFraccionada fechaFiltradoAltaBBDD) {
		this.fechaFiltradoAltaBBDD = fechaFiltradoAltaBBDD;
	}

	public Fecha getFechaAltaBBDD() {
		return fechaAltaBBDD;
	}

	public void setFechaAltaBBDD(Fecha fechaAltaBBDD) {
		this.fechaAltaBBDD = fechaAltaBBDD;
	}
}
