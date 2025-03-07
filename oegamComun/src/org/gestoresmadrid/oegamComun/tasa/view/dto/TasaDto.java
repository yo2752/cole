package org.gestoresmadrid.oegamComun.tasa.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.oegamComun.trafico.view.dto.TramiteTraficoDto;
import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;

import utilidades.estructuras.Fecha;

public class TasaDto implements Serializable {

	private static final long serialVersionUID = -3065999183549314322L;

	private String codigoTasa;

	private Fecha fechaAlta;

	private Fecha fechaAsignacion;

	private Fecha fechaFinVigencia;

	private ContratoDto contrato;

	private UsuarioDto usuario;

	private String tipoTasa;

	private BigDecimal numExpediente;

	private String refPropia;

	private String comentarios;

	private BigDecimal precio;

	private Integer formato;

	private TramiteTraficoDto tramiteTrafico;

	private BigDecimal importadoIcogam;

	private String estado;

	private List<TramiteTraficoDto> listaExpedientesAsignados;

	private String asignada;

	private String bloqueada;
	
	private String tipoAlmacen;
	
	private String tipoTramiteAlmacen;

	public TasaDto() {}

	public String getCodigoTasa() {
		return codigoTasa;
	}

	public void setCodigoTasa(String codigoTasa) {
		this.codigoTasa = codigoTasa;
	}

	public Fecha getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Fecha fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Fecha getFechaAsignacion() {
		return fechaAsignacion;
	}

	public void setFechaAsignacion(Fecha fechaAsignacion) {
		this.fechaAsignacion = fechaAsignacion;
	}

	public Fecha getFechaFinVigencia() {
		return fechaFinVigencia;
	}

	public void setFechaFinVigencia(Fecha fechaFinVigencia) {
		this.fechaFinVigencia = fechaFinVigencia;
	}

	public ContratoDto getContrato() {
		return contrato;
	}

	public void setContrato(ContratoDto contrato) {
		this.contrato = contrato;
	}

	public UsuarioDto getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDto usuario) {
		this.usuario = usuario;
	}

	public String getTipoTasa() {
		return tipoTasa;
	}

	public void setTipoTasa(String tipoTasa) {
		this.tipoTasa = tipoTasa;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	public String getRefPropia() {
		return refPropia;
	}

	public void setRefPropia(String refPropia) {
		this.refPropia = refPropia;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public Integer getFormato() {
		return formato;
	}

	public void setFormato(Integer formato) {
		this.formato = formato;
	}

	public TramiteTraficoDto getTramiteTrafico() {
		return tramiteTrafico;
	}

	public void setTramiteTrafico(TramiteTraficoDto tramiteTrafico) {
		this.tramiteTrafico = tramiteTrafico;
	}

	public BigDecimal getImportadoIcogam() {
		return importadoIcogam;
	}

	public void setImportadoIcogam(BigDecimal importadoIcogam) {
		this.importadoIcogam = importadoIcogam;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getAsignada() {
		return asignada;
	}

	public void setAsignada(String asignada) {
		this.asignada = asignada;
	}

	public List<TramiteTraficoDto> getListaExpedientesAsignados() {
		return listaExpedientesAsignados;
	}

	public void setListaExpedientesAsignados(List<TramiteTraficoDto> listaExpedientesAsignados) {
		this.listaExpedientesAsignados = listaExpedientesAsignados;
	}

	public String getBloqueada() {
		return bloqueada;
	}

	public void setBloqueada(String bloqueada) {
		this.bloqueada = bloqueada;
	}

	public String getTipoAlmacen() {
		return tipoAlmacen;
	}

	public void setTipoAlmacen(String tipoAlmacen) {
		this.tipoAlmacen = tipoAlmacen;
	}

	public String getTipoTramiteAlmacen() {
		return tipoTramiteAlmacen;
	}

	public void setTipoTramiteAlmacen(String tipoTramiteAlmacen) {
		this.tipoTramiteAlmacen = tipoTramiteAlmacen;
	}
}
