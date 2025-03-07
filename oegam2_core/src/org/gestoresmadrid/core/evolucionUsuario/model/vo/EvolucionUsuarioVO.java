package org.gestoresmadrid.core.evolucionUsuario.model.vo;

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
@Table(name="EVOLUCION_USUARIO")
public class EvolucionUsuarioVO implements Serializable{

	private static final long serialVersionUID = 6706397657309216472L;
	
	@EmbeddedId
	private EvolucionUsuarioPK id;
	
	@Column(name="ID_CONTRATO")
	private Long idContrato;
	
	
	@Column(name="TIPO_ACTUACION")
	private String tipoActuacion;
	
	
	@Column(name="ESTADO_ANT")
	private BigDecimal estadoAnt;
	
	@Column(name="ESTADO_NUEVO")
	private BigDecimal estadoNuevo;
	
	@Column(name="CONTRATO_ANT")
	private Long contratoAnt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_USUARIO",insertable=false,updatable=false)
	private UsuarioVO usuario;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CONTRATO_ANT",insertable=false,updatable=false)
	private ContratoVO contrato;
	
	@Column(name="NIF")
	private String nif;
	
	public EvolucionUsuarioPK getId() {
		return id;
	}

	public void setId(EvolucionUsuarioPK id) {
		this.id = id;
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

	public Long getContratoAnt() {
		return contratoAnt;
	}

	public void setContratoAnt(Long contratoAnt) {
		this.contratoAnt = contratoAnt;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	


	
}
