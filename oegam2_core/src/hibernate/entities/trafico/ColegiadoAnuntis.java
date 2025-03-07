package hibernate.entities.trafico;

import hibernate.entities.general.Colegiado;
import hibernate.entities.general.Contrato;

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

@Entity
@Table(name = "COLEGIADO_ANUNTIS")
public class ColegiadoAnuntis implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_COLEGIADO_ANUNTIS")
	@GeneratedValue(strategy = GenerationType.AUTO, generator="SEC_COLEGIADO_ANUNTIS") // Generador de secuencia de Oracle,
	// Si se migra la BBDD, se deberá utilizar otra estrategia
	@SequenceGenerator(name="SEC_COLEGIADO_ANUNTIS", sequenceName="CLAVE_COLEGIADO_ANUNTIS_SEQ")
	private Integer identificador;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NUM_COLEGIADO")
	private Colegiado colegiado;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CONTRATO")
	private Contrato contrato;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_INI", nullable = false)
	private Date fechaInicio;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_FIN")
	private Date fechaFin;

	public Integer getIdentificador() {
		return identificador;
	}

	public void setIdentificador(Integer identificador) {
		this.identificador = identificador;
	}

	public Colegiado getColegiado() {
		return colegiado;
	}

	public void setColegiado(Colegiado colegiado) {
		this.colegiado = colegiado;
	}

	public Contrato getContrato() {
		return contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

}