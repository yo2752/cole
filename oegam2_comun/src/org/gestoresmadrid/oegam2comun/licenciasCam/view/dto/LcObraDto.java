package org.gestoresmadrid.oegam2comun.licenciasCam.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class LcObraDto implements Serializable {

	private static final long serialVersionUID = 2302845225768714773L;

	private Long idDatosObra;

	private Boolean andamios;

	private Boolean contenedorViaPublica;

	private String descripcionObra;

	private BigDecimal duracionAndamioMeses;

	private BigDecimal duracionGruaMeses;

	private BigDecimal duracionVallaMeses;

	private BigDecimal duracionContenedorMeses;

	private Boolean instalacionGrua;

	private BigDecimal plazoEjecucion;

	private BigDecimal plazoInicio;

	private BigDecimal presupuestoTotal;

	private BigDecimal superficieAfectada;

	private String tipoObra;

	private Boolean vallas;

	private Boolean ocupacionViaPublica;

	private List<LcParteAutonomaDto> partesAutonomas;

	// Objetos de pantalla
	private LcParteAutonomaDto parteAutonoma;
	private List<String> tiposObra;
	private String tipoObraPantalla;

	public LcObraDto() {}

	public Long getIdDatosObra() {
		return this.idDatosObra;
	}

	public void setIdDatosObra(Long idDatosObra) {
		this.idDatosObra = idDatosObra;
	}

	public Boolean getAndamios() {
		return this.andamios;
	}

	public void setAndamios(Boolean andamios) {
		this.andamios = andamios;
	}

	public Boolean getContenedorViaPublica() {
		return this.contenedorViaPublica;
	}

	public void setContenedorViaPublica(Boolean contenedorViaPublica) {
		this.contenedorViaPublica = contenedorViaPublica;
	}

	public String getDescripcionObra() {
		return this.descripcionObra;
	}

	public void setDescripcionObra(String descripcionObra) {
		this.descripcionObra = descripcionObra;
	}

	public Boolean getInstalacionGrua() {
		return this.instalacionGrua;
	}

	public void setInstalacionGrua(Boolean instalacionGrua) {
		this.instalacionGrua = instalacionGrua;
	}

	public BigDecimal getPresupuestoTotal() {
		return this.presupuestoTotal;
	}

	public void setPresupuestoTotal(BigDecimal presupuestoTotal) {
		this.presupuestoTotal = presupuestoTotal;
	}

	public String getTipoObra() {
		return this.tipoObra;
	}

	public void setTipoObra(String tipoObra) {
		this.tipoObra = tipoObra;
	}

	public Boolean getVallas() {
		return this.vallas;
	}

	public void setVallas(Boolean vallas) {
		this.vallas = vallas;
	}

	public Boolean getOcupacionViaPublica() {
		return ocupacionViaPublica;
	}

	public void setOcupacionViaPublica(Boolean ocupacionViaPublica) {
		this.ocupacionViaPublica = ocupacionViaPublica;
	}

	public List<LcParteAutonomaDto> getPartesAutonomas() {
		return partesAutonomas;
	}

	public void setPartesAutonomas(List<LcParteAutonomaDto> partesAutonomas) {
		this.partesAutonomas = partesAutonomas;
	}

	public LcParteAutonomaDto getParteAutonoma() {
		return parteAutonoma;
	}

	public void setParteAutonoma(LcParteAutonomaDto parteAutonoma) {
		this.parteAutonoma = parteAutonoma;
	}

	public BigDecimal getDuracionAndamioMeses() {
		return duracionAndamioMeses;
	}

	public void setDuracionAndamioMeses(BigDecimal duracionAndamioMeses) {
		this.duracionAndamioMeses = duracionAndamioMeses;
	}

	public BigDecimal getDuracionGruaMeses() {
		return duracionGruaMeses;
	}

	public void setDuracionGruaMeses(BigDecimal duracionGruaMeses) {
		this.duracionGruaMeses = duracionGruaMeses;
	}

	public BigDecimal getDuracionVallaMeses() {
		return duracionVallaMeses;
	}

	public void setDuracionVallaMeses(BigDecimal duracionVallaMeses) {
		this.duracionVallaMeses = duracionVallaMeses;
	}

	public BigDecimal getDuracionContenedorMeses() {
		return duracionContenedorMeses;
	}

	public void setDuracionContenedorMeses(BigDecimal duracionContenedorMeses) {
		this.duracionContenedorMeses = duracionContenedorMeses;
	}

	public BigDecimal getPlazoEjecucion() {
		return plazoEjecucion;
	}

	public void setPlazoEjecucion(BigDecimal plazoEjecucion) {
		this.plazoEjecucion = plazoEjecucion;
	}

	public BigDecimal getPlazoInicio() {
		return plazoInicio;
	}

	public void setPlazoInicio(BigDecimal plazoInicio) {
		this.plazoInicio = plazoInicio;
	}

	public BigDecimal getSuperficieAfectada() {
		return superficieAfectada;
	}

	public void setSuperficieAfectada(BigDecimal superficieAfectada) {
		this.superficieAfectada = superficieAfectada;
	}

	public List<String> getTiposObra() {
		return tiposObra;
	}

	public void setTiposObra(List<String> tiposObra) {
		this.tiposObra = tiposObra;
	}

	public String getTipoObraPantalla() {
		return tipoObraPantalla;
	}

	public void setTipoObraPantalla(String tipoObraPantalla) {
		this.tipoObraPantalla = tipoObraPantalla;
	}
}