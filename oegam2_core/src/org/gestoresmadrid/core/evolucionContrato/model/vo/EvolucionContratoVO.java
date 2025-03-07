package org.gestoresmadrid.core.evolucionContrato.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;

@Entity
@Table(name="EVOLUCION_CONTRATO")
public class EvolucionContratoVO implements Serializable{

	private static final long serialVersionUID = 6706397657309216472L;
	
	@EmbeddedId
	private EvolucionContratoPK id;
	
	@Column(name="TIPO_ACTUACION")
	private String tipoActualizacion;
	
	@Column(name="MOTIVO")
	private String motivo;
	
	@Column(name="SOLICITANTE")
	private String solicitante;
	
	@Column(name="ESTADO_ANT")
	private BigDecimal estadoAnt;
	
	@Column(name="ESTADO_NUEVO")
	private BigDecimal estadoNuevo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_USUARIO",insertable=false,updatable=false)
	private UsuarioVO usuario;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CONTRATO",insertable=false,updatable=false)
	private ContratoVO contrato;
	
	public EvolucionContratoPK getId() {
		return id;
	}

	public void setId(EvolucionContratoPK id) {
		this.id = id;
	}

	public String getTipoActualizacion() {
		return tipoActualizacion;
	}

	public void setTipoActualizacion(String tipoActualizacion) {
		this.tipoActualizacion = tipoActualizacion;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
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

	public UsuarioVO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioVO usuario) {
		this.usuario = usuario;
	}

	public ContratoVO getContrato() {
		return contrato;
	}

	public void setContrato(ContratoVO contrato) {
		this.contrato = contrato;
	}
	
}
