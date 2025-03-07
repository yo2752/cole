package org.gestoresmadrid.oegamConversiones.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class NotificacionDto implements Serializable {

	private static final long serialVersionUID = -5915952364721843784L;

	private String descripcion;

	private BigDecimal estadoAnt;

	private BigDecimal estadoNue;

	private BigDecimal idTramite;

	private String tipoTramite;

	private Long idUsuario;

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public BigDecimal getEstadoAnt() {
		return estadoAnt;
	}

	public void setEstadoAnt(BigDecimal estadoAnt) {
		this.estadoAnt = estadoAnt;
	}

	public BigDecimal getEstadoNue() {
		return estadoNue;
	}

	public void setEstadoNue(BigDecimal estadoNue) {
		this.estadoNue = estadoNue;
	}

	public BigDecimal getIdTramite() {
		return idTramite;
	}

	public void setIdTramite(BigDecimal idTramite) {
		this.idTramite = idTramite;
	}

	public String getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
}
