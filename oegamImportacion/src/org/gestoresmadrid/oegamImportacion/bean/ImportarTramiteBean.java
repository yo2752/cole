package org.gestoresmadrid.oegamImportacion.bean;

import java.io.Serializable;

import org.gestoresmadrid.oegamConversiones.jaxb.pegatina.ParametrosPegatinaMatriculacion;

public class ImportarTramiteBean implements Serializable {

	private static final long serialVersionUID = 5788594948487360412L;
	
	private String tipoImportacion;
	private Long idContrato;
	private Integer etiquetasTramite;
	private Integer etiquetasFila;
	private Integer etiquetasColumna;
	private String formatoTasa;

	private ParametrosPegatinaMatriculacion etiquetaParametros;

	public String getTipoImportacion() {
		return tipoImportacion;
	}

	public void setTipoImportacion(String tipoImportacion) {
		this.tipoImportacion = tipoImportacion;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public Integer getEtiquetasTramite() {
		return etiquetasTramite;
	}

	public void setEtiquetasTramite(Integer etiquetasTramite) {
		this.etiquetasTramite = etiquetasTramite;
	}

	public Integer getEtiquetasFila() {
		return etiquetasFila;
	}

	public void setEtiquetasFila(Integer etiquetasFila) {
		this.etiquetasFila = etiquetasFila;
	}

	public Integer getEtiquetasColumna() {
		return etiquetasColumna;
	}

	public void setEtiquetasColumna(Integer etiquetasColumna) {
		this.etiquetasColumna = etiquetasColumna;
	}

	public ParametrosPegatinaMatriculacion getEtiquetaParametros() {
		return etiquetaParametros;
	}

	public void setEtiquetaParametros(ParametrosPegatinaMatriculacion etiquetaParametros) {
		this.etiquetaParametros = etiquetaParametros;
	}

	public String getFormatoTasa() {
		return formatoTasa;
	}

	public void setFormatoTasa(String formatoTasa) {
		this.formatoTasa = formatoTasa;
	}
}
