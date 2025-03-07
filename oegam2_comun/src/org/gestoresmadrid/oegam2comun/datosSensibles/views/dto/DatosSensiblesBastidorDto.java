package org.gestoresmadrid.oegam2comun.datosSensibles.views.dto;

import java.math.BigDecimal;
import java.util.Date;

import org.gestoresmadrid.oegam2comun.view.dto.GruposDto;

public class DatosSensiblesBastidorDto {
	
	private String bastidor;
	private String idGrupo;
	private String apellidosNombre;
	private Date fechaAlta;
	private String numColegiado;
	private GruposDto grupo;
	private BigDecimal idUsuario;
	private BigDecimal estado;
	private Date fechaBaja;
	private Long tiempoRestauracion;
	private Long usuarioOperacion;
	private BigDecimal tipoBastidor;
	
	public String getBastidor() {
		return bastidor;
	}
	public void setBastidor(String bastidor) {
		this.bastidor = bastidor;
	}
	public String getIdGrupo() {
		return idGrupo;
	}
	public void setIdGrupo(String idGrupo) {
		this.idGrupo = idGrupo;
	}
	public String getApellidosNombre() {
		return apellidosNombre;
	}
	public void setApellidosNombre(String apellidosNombre) {
		this.apellidosNombre = apellidosNombre;
	}
	public Date getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public String getNumColegiado() {
		return numColegiado;
	}
	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}
	public GruposDto getGrupo() {
		return grupo;
	}
	public void setGrupo(GruposDto grupo) {
		this.grupo = grupo;
	}
	public BigDecimal getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(BigDecimal idUsuario) {
		this.idUsuario = idUsuario;
	}
	public BigDecimal getEstado() {
		return estado;
	}
	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}
	public Date getFechaBaja() {
		return fechaBaja;
	}
	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}
	public Long getTiempoRestauracion() {
		return tiempoRestauracion;
	}
	public void setTiempoRestauracion(Long tiempoRestauracion) {
		this.tiempoRestauracion = tiempoRestauracion;
	}
	public Long getUsuarioOperacion() {
		return usuarioOperacion;
	}
	public void setUsuarioOperacion(Long usuarioOperacion) {
		this.usuarioOperacion = usuarioOperacion;
	}
	public BigDecimal getTipoBastidor() {
		return tipoBastidor;
	}
	public void setTipoBastidor(BigDecimal tipoBastidor) {
		this.tipoBastidor = tipoBastidor;
	}
	
}
