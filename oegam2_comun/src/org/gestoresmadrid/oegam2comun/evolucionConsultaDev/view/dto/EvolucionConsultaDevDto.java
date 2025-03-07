package org.gestoresmadrid.oegam2comun.evolucionConsultaDev.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.gestoresmadrid.oegam2comun.consultaDev.model.dto.ConsultaDevDto;
import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;

public class EvolucionConsultaDevDto implements Serializable{

	private static final long serialVersionUID = 368766528627591599L;

	private Long idConsultaDev;
	private Date fechaCambio;
	private Long idUsuario;
	private String tipoActuacion;
	private BigDecimal estadoAnt;
	private BigDecimal estadoNuevo;
	private UsuarioDto usuario;
	private ConsultaDevDto consultaDev;

	public Long getIdConsultaDev() {
		return idConsultaDev;
	}

	public void setIdConsultaDev(Long idConsultaDev) {
		this.idConsultaDev = idConsultaDev;
	}

	public Date getFechaCambio() {
		return fechaCambio;
	}

	public void setFechaCambio(Date fechaCambio) {
		this.fechaCambio = fechaCambio;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getTipoActuacion() {
		return tipoActuacion;
	}

	public void setTipoActuacion(String tipoActuacion) {
		this.tipoActuacion = tipoActuacion;
	}

	public BigDecimal getEstadoAnt() {
		return estadoAnt;
	}

	public void setEstadoAnt(BigDecimal estadoAnt) {
		this.estadoAnt = estadoAnt;
	}

	public BigDecimal getEstadoNuevo() {
		return estadoNuevo;
	}

	public void setEstadoNuevo(BigDecimal estadoNuevo) {
		this.estadoNuevo = estadoNuevo;
	}

	public UsuarioDto getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDto usuario) {
		this.usuario = usuario;
	}

	public ConsultaDevDto getConsultaDev() {
		return consultaDev;
	}

	public void setConsultaDev(ConsultaDevDto consultaDev) {
		this.consultaDev = consultaDev;
	}
	
}
