package org.gestoresmadrid.oegamCreditos.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;

import utilidades.estructuras.Fecha;

public class HistoricoCreditoDto implements Serializable {

	private static final long serialVersionUID = -3939892763930282129L;

	private Long idHistoricoCredito;

	private BigDecimal cantidadRestada;

	private BigDecimal cantidadSumada;

	private Fecha fecha;

	private Long idContrato;

	private ContratoDto contrato;

	private UsuarioDto usuario;

	private CreditoDto credito;

	public Long getIdHistoricoCredito() {
		return this.idHistoricoCredito;
	}

	public void setIdHistoricoCredito(Long idHistoricoCredito) {
		this.idHistoricoCredito = idHistoricoCredito;
	}

	public BigDecimal getCantidadRestada() {
		return this.cantidadRestada;
	}

	public void setCantidadRestada(BigDecimal cantidadRestada) {
		this.cantidadRestada = cantidadRestada;
	}

	public BigDecimal getCantidadSumada() {
		return this.cantidadSumada;
	}

	public void setCantidadSumada(BigDecimal cantidadSumada) {
		this.cantidadSumada = cantidadSumada;
	}

	public Fecha getFecha() {
		return this.fecha;
	}

	public void setFecha(Fecha fecha) {
		this.fecha = fecha;
	}

	public UsuarioDto getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDto usuario) {
		this.usuario = usuario;
	}

	public ContratoDto getContrato() {
		return contrato;
	}

	public void setContrato(ContratoDto contrato) {
		this.contrato = contrato;
	}

	public long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(long idContrato) {
		this.idContrato = idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public CreditoDto getCredito() {
		return credito;
	}

	public void setCredito(CreditoDto credito) {
		this.credito = credito;
	}
}