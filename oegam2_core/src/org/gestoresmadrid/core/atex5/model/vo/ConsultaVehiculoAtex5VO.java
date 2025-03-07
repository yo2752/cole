package org.gestoresmadrid.core.atex5.model.vo;

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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.tasas.model.vo.TasaVO;

@Entity
@Table(name="CONSULTA_VEHICULO_ATEX5")
public class ConsultaVehiculoAtex5VO implements Serializable{

	private static final long serialVersionUID = 2805536434290179907L;

	@Id
	@Column(name = "ID_CONSULTA_VEHICULO")
	@SequenceGenerator(name = "consulta_vehiculo_atex5_secuencia", sequenceName = "ID_CONSULTA_VEHICULO_ATEX5_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "consulta_vehiculo_atex5_secuencia")
	private Long idConsultaVehiculoAtex5;
	
	@Column(name="NUM_COLEGIADO")
	private String numColegiado;
	
	@Column(name="NUM_EXPEDIENTE")
	private BigDecimal numExpediente;
	
	@Column(name="ID_CONTRATO")
	private Long idContrato;
	
	@Column(name="MATRICULA")
	private String matricula;
	
	@Column(name="BASTIDOR")
	private String bastidor;
	
	@Column(name="CODIGO_TASA")
	private String codigoTasa;
	
	@Column(name="ESTADO")
	private BigDecimal estado;
	
	@Column(name="FECHA_ALTA")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaAlta;
	
	@Column(name="RESPUESTA")
	private String respuesta;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_CONTRATO",insertable=false,updatable=false)
	private ContratoVO contrato;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CODIGO_TASA",insertable=false,updatable=false)
	private TasaVO tasa;

	public Long getIdConsultaVehiculoAtex5() {
		return idConsultaVehiculoAtex5;
	}

	public void setIdConsultaVehiculoAtex5(Long idConsultaVehiculoAtex5) {
		this.idConsultaVehiculoAtex5 = idConsultaVehiculoAtex5;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getBastidor() {
		return bastidor;
	}

	public void setBastidor(String bastidor) {
		this.bastidor = bastidor;
	}

	public String getCodigoTasa() {
		return codigoTasa;
	}

	public void setCodigoTasa(String codigoTasa) {
		this.codigoTasa = codigoTasa;
	}

	public BigDecimal getEstado() {
		return estado;
	}

	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public ContratoVO getContrato() {
		return contrato;
	}

	public void setContrato(ContratoVO contrato) {
		this.contrato = contrato;
	}
	
	public TasaVO getTasa() {
		return tasa;
	}

	public void setTasa(TasaVO tasa) {
		this.tasa = tasa;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

}
