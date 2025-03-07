package org.gestoresmadrid.core.evolucionPrmDstvFicha.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;

@Entity
@Table(name="EVOLUCION_PRM_DSTV_FICHA")
public class EvolucionPrmDstvFichaVO implements Serializable{

	private static final long serialVersionUID = 2915980519364907547L;

	@Id
	@SequenceGenerator(name = "EvoPermDisItv_secuencia", sequenceName = "ID_EVOL_PRM_DSTV_FCH_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "EvoPermDisItv_secuencia")
	@Column(name="ID_EVO_PRM_DSTV_FCH")
	private Long idEvolucionPrmDstvFicha;

	@Column(name="TIPO_DOCUMENTO")
	private String tipoDocumento;
	
	@Column(name="OPERACION")
	private String operacion;
	
	@Column(name="NUM_EXPEDIENTE")
	private BigDecimal numExpediente;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="FECHA_CAMBIO")
	private Date fechaCambio;
	
	@Column(name="ESTADO_ANT")
	private BigDecimal estadoAnterior;
	
	@Column(name="ESTADO_NUEVO")
	private BigDecimal estadoNuevo;
	
	@Column(name="ID_USUARIO")
	private BigDecimal idUsuario;
	
	@Column(name="DOC_ID")
	private String docID;
	
	@Column(name="IP")
	private String ip;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_USUARIO",insertable=false,updatable=false)
	private UsuarioVO usuario;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="NUM_EXPEDIENTE",insertable=false,updatable=false)
	private TramiteTraficoVO tramiteTrafico;

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

	public UsuarioVO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioVO usuario) {
		this.usuario = usuario;
	}

	public TramiteTraficoVO getTramiteTrafico() {
		return tramiteTrafico;
	}

	public void setTramiteTrafico(TramiteTraficoVO tramiteTrafico) {
		this.tramiteTrafico = tramiteTrafico;
	}

	public String getDocID() {
		return docID;
	}

	public void setDocID(String docID) {
		this.docID = docID;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
}
