package org.gestoresmadrid.oegam2comun.tasas.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;

import utilidades.estructuras.Fecha;

public class TasaPegatinaDto implements Serializable {

	private static final long serialVersionUID = 8045524297805274722L;

	private Long idTasaPegatina;

	private String codigoTasa;

	private String tipoTasa;

	private ContratoDto idContrato;

	private BigDecimal precio;

	private Fecha fechaAlta;

	private Fecha fechaFinVigencia;

	private UsuarioDto usuario;

	private Fecha fechaFacturacion;

	public Long getIdTasaPegatina() {
		return idTasaPegatina;
	}

	public void setIdTasaPegatina(Long idTasaPegatina) {
		this.idTasaPegatina = idTasaPegatina;
	}

	public String getCodigoTasa() {
		return codigoTasa;
	}

	public void setCodigoTasa(String codigoTasa) {
		this.codigoTasa = codigoTasa;
	}

	public String getTipoTasa() {
		return tipoTasa;
	}

	public void setTipoTasa(String tipoTasa) {
		this.tipoTasa = tipoTasa;
	}

	public ContratoDto getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(ContratoDto idContrato) {
		this.idContrato = idContrato;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public Fecha getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Fecha fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Fecha getFechaFinVigencia() {
		return fechaFinVigencia;
	}

	public void setFechaFinVigencia(Fecha fechaFinVigencia) {
		this.fechaFinVigencia = fechaFinVigencia;
	}

	public UsuarioDto getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDto usuario) {
		this.usuario = usuario;
	}

	public Fecha getFechaFacturacion() {
		return fechaFacturacion;
	}

	public void setFechaFacturacion(Fecha fechaFacturacion) {
		this.fechaFacturacion = fechaFacturacion;
	}
}