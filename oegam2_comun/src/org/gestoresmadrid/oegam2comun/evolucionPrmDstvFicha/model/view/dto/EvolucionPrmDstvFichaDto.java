package org.gestoresmadrid.oegam2comun.evolucionPrmDstvFicha.model.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;

import trafico.dto.TramiteTraficoDto;

public class EvolucionPrmDstvFichaDto implements Serializable{

	private static final long serialVersionUID = 4227699023689775542L;

	private Long idEvolucionPrmDstvFicha;

	private String tipoDocumento;
	
	private String operacion;
	
	private BigDecimal numExpediente;
	
	private Date fechaCambio;
	
	private BigDecimal estadoAnterior;
	
	private BigDecimal estadoNuevo;
	
	private BigDecimal idUsuario;
	
	private String docId;
	
	private UsuarioDto usuario;
	
	private TramiteTraficoDto tramiteTrafico;

	public Long getIdEvolucionPrmDstvFicha() {
		return idEvolucionPrmDstvFicha;
	}

	public void setIdEvolucionPrmDstvFicha(Long idEvolucionPrmDstvFicha) {
		this.idEvolucionPrmDstvFicha = idEvolucionPrmDstvFicha;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getOperacion() {
		return operacion;
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public Date getFechaCambio() {
		return fechaCambio;
	}

	public void setFechaCambio(Date fechaCambio) {
		this.fechaCambio = fechaCambio;
	}

	public BigDecimal getEstadoAnterior() {
		return estadoAnterior;
	}

	public void setEstadoAnterior(BigDecimal estadoAnterior) {
		this.estadoAnterior = estadoAnterior;
	}

	public BigDecimal getEstadoNuevo() {
		return estadoNuevo;
	}

	public void setEstadoNuevo(BigDecimal estadoNuevo) {
		this.estadoNuevo = estadoNuevo;
	}

	public BigDecimal getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(BigDecimal idUsuario) {
		this.idUsuario = idUsuario;
	}

	public UsuarioDto getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDto usuario) {
		this.usuario = usuario;
	}

	public TramiteTraficoDto getTramiteTrafico() {
		return tramiteTrafico;
	}

	public void setTramiteTrafico(TramiteTraficoDto tramiteTrafico) {
		this.tramiteTrafico = tramiteTrafico;
	}

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}
}
