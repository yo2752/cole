package trafico.beans.utiles;

import java.math.BigDecimal;

public class ParametrosPegatinaMatriculacion {

	private Integer etiquetasTramite;
	private Integer etiquetasFila;
	private Integer etiquetasColumna;

	private BigDecimal margenSup;
	private BigDecimal margenInf;
	private BigDecimal margenDcho;
	private BigDecimal margenIzqd;

	private BigDecimal horizontal;
	private BigDecimal vertical;

	private Integer filaPrimer;
	private Integer columnaPrimer;

	private String matryBast;

	public ParametrosPegatinaMatriculacion() {
		this.etiquetasTramite = 3;
		this.etiquetasFila = 5;
		this.etiquetasColumna = 21;
		this.margenSup = BigDecimal.valueOf(12.0F);
		this.margenInf = BigDecimal.valueOf(12.0F);
		this.margenDcho = BigDecimal.valueOf(17.5F);
		this.margenIzqd = BigDecimal.valueOf(17.5F);
		this.horizontal = BigDecimal.ZERO;
		this.vertical = BigDecimal.ZERO;
		this.filaPrimer = 1;
		this.columnaPrimer = 1;
		this.matryBast = "";
	}

	public String getMatryBast() {
		return matryBast;
	}

	public void setMatryBast(String matryBast) {
		this.matryBast = matryBast;
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

	public BigDecimal getMargenSup() {
		return margenSup;
	}

	public void setMargenSup(BigDecimal margenSup) {
		this.margenSup = margenSup;
	}

	public BigDecimal getMargenInf() {
		return margenInf;
	}

	public void setMargenInf(BigDecimal margenInf) {
		this.margenInf = margenInf;
	}

	public BigDecimal getMargenDcho() {
		return margenDcho;
	}

	public void setMargenDcho(BigDecimal margenDcho) {
		this.margenDcho = margenDcho;
	}

	public BigDecimal getMargenIzqd() {
		return margenIzqd;
	}

	public void setMargenIzqd(BigDecimal margenIzqd) {
		this.margenIzqd = margenIzqd;
	}

	public BigDecimal getHorizontal() {
		return horizontal;
	}

	public void setHorizontal(BigDecimal horizontal) {
		this.horizontal = horizontal;
	}

	public BigDecimal getVertical() {
		return vertical;
	}

	public void setVertical(BigDecimal vertical) {
		this.vertical = vertical;
	}

	public Integer getFilaPrimer() {
		return filaPrimer;
	}

	public void setFilaPrimer(Integer filaPrimer) {
		this.filaPrimer = filaPrimer;
	}

	public Integer getColumnaPrimer() {
		return columnaPrimer;
	}

	public void setColumnaPrimer(Integer columnaPrimer) {
		this.columnaPrimer = columnaPrimer;
	}

}