package org.gestoresmadrid.core.impr.model.vo;

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

import org.gestoresmadrid.core.general.model.vo.UsuarioVO;

@Entity
@Table(name="EVOLUCION_DOC_PRM_DSTV_FICHA")
public class EvoDocPrmDstvFichaVO implements Serializable{

	private static final long serialVersionUID = -178595313195765909L;

	@Id
	@SequenceGenerator(name = "EvoDocPermDisItv_secuencia", sequenceName = "ID_EVOL_DOC_PRM_DSTV_FCH_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "EvoDocPermDisItv_secuencia")
	@Column(name="ID_EVO_DOC_PRM_DSTV_FCH")
	private Long idEvolucionDocPrmDstvFicha;

	@Column(name="TIPO_DOCUMENTO")
	private String tipoDocumento;
	
	@Column(name="OPERACION")
	private String operacion;
	
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
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_USUARIO",insertable=false,updatable=false)
	private UsuarioVO usuario;

	public Long getIdEvolucionDocPrmDstvFicha() {
		return idEvolucionDocPrmDstvFicha;
	}

	public void setIdEvolucionDocPrmDstvFicha(Long idEvolucionDocPrmDstvFicha) {
		this.idEvolucionDocPrmDstvFicha = idEvolucionDocPrmDstvFicha;
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

	public Date getFechaCambio() {
		return fechaCambio;
	}

	public void setFechaCambio(Date fechaCambio) {
		this.fechaCambio = fechaCambio;
	}

	public BigDecimal getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(BigDecimal idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getDocID() {
		return docID;
	}

	public void setDocID(String docID) {
		this.docID = docID;
	}

	public UsuarioVO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioVO usuario) {
		this.usuario = usuario;
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
	
}
