package org.gestoresmadrid.core.consultasTGate.model.vo;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "CONSULTAS_TGATE")
public class ConsultasTGateVO {

	@Id
	@Column(name = "ID_CONSULTAS_TGATE")
	@SequenceGenerator(name = "consultas_tgate_secuencia", sequenceName = "CONSULTAS_TGATE_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "consultas_tgate_secuencia")
	private Long idConsultasTGate;

	@Column(name = "NUM_EXPEDIENTE")
	private BigDecimal numExpediente;

	@Column(name = "TIPO_SERVICIO")
	private String tipoServicio;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_HORA")
	private Date fechaHora;

	@Column(name = "ID_USUARIO")
	private BigDecimal idUsuario;

	@Column(name = "NUM_COLEGIADO")
	private String numColegiado;

	@Column(name = "ID_VEHICULO")
	private Long idVehiculo;

	private String origen;

	private String respuesta;

	public Long getIdConsultasTGate() {
		return idConsultasTGate;
	}

	public void setIdConsultasTGate(Long idConsultasTGate) {
		this.idConsultasTGate = idConsultasTGate;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getTipoServicio() {
		return tipoServicio;
	}

	public void setTipoServicio(String tipoServicio) {
		this.tipoServicio = tipoServicio;
	}

	public BigDecimal getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(BigDecimal idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Date getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public Long getIdVehiculo() {
		return idVehiculo;
	}

	public void setIdVehiculo(Long idVehiculo) {
		this.idVehiculo = idVehiculo;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
}
