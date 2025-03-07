package org.gestoresmadrid.oegam2comun.tasas.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.gestoresmadrid.oegamComun.tasa.view.dto.TasaDto;
import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;

import utilidades.estructuras.Fecha;

public class EvolucionTasaDto implements Serializable {

	private static final long serialVersionUID = 233541712630003343L;

	private String codigoTasa;

	private Fecha fechaHora;

	private String accion;

	private Date fechaVigenciaAnt;

	private Date fechaVigenciaNue;

	private UsuarioDto usuario;

	private BigDecimal numExpediente;

	private BigDecimal precioAnt;

	private String precioNue;
	
	private String motivoBloqueo;

	private TasaDto tasa;

	public String getCodigoTasa() {
		return codigoTasa;
	}

	public void setCodigoTasa(String codigoTasa) {
		this.codigoTasa = codigoTasa;
	}

	public Fecha getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(Fecha fechaHora) {
		this.fechaHora = fechaHora;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public Date getFechaVigenciaAnt() {
		return fechaVigenciaAnt;
	}

	public void setFechaVigenciaAnt(Date fechaVigenciaAnt) {
		this.fechaVigenciaAnt = fechaVigenciaAnt;
	}

	public Date getFechaVigenciaNue() {
		return fechaVigenciaNue;
	}

	public void setFechaVigenciaNue(Date fechaVigenciaNue) {
		this.fechaVigenciaNue = fechaVigenciaNue;
	}

	public UsuarioDto getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDto usuario) {
		this.usuario = usuario;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public BigDecimal getPrecioAnt() {
		return precioAnt;
	}

	public void setPrecioAnt(BigDecimal precioAnt) {
		this.precioAnt = precioAnt;
	}

	public String getPrecioNue() {
		return precioNue;
	}

	public void setPrecioNue(String precioNue) {
		this.precioNue = precioNue;
	}

	public TasaDto getTasa() {
		return tasa;
	}

	public void setTasa(TasaDto tasa) {
		this.tasa = tasa;
	}

	public String getMotivoBloqueo() {
		return motivoBloqueo;
	}

	public void setMotivoBloqueo(String motivoBloqueo) {
		this.motivoBloqueo = motivoBloqueo;
	}
}
