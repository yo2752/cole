package org.gestoresmadrid.core.circular.model.vo;

import java.io.Serializable;
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

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;

@Entity
@Table(name="CIRCULAR_CONTRATO")
public class CircularContratoVO implements Serializable{

	private static final long serialVersionUID = -3295674334959129366L;
	
	@Id
	@SequenceGenerator(name = "circular_contrato_secuencia", sequenceName = "ID_CIRCULAR_CONT_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "circular_contrato_secuencia")
	@Column(name = "ID_CIR_CON")
	private Long idCircularContrato;
	
	@Column(name="ID_CONTRATO")
	private Long idContrato;
	
	@Column(name="ID_CIRCULAR")
	private Long idCircular;
	
	@Column(name="ID_USUARIO")
	private Long idUsuario;
	
	@Column(name="FECHA")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_CIRCULAR",insertable=false,updatable=false)
	private CircularVO circular;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_USUARIO",insertable=false,updatable=false)
	private UsuarioVO usuario;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_CONTRATO",insertable=false,updatable=false)
	private ContratoVO contrato;

	public Long getIdCircularContrato() {
		return idCircularContrato;
	}

	public void setIdCircularContrato(Long idCircularContrato) {
		this.idCircularContrato = idCircularContrato;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public Long getIdCircular() {
		return idCircular;
	}

	public void setIdCircular(Long idCircular) {
		this.idCircular = idCircular;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public CircularVO getCircular() {
		return circular;
	}

	public void setCircular(CircularVO circular) {
		this.circular = circular;
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
