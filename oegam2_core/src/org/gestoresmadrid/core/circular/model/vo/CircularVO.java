package org.gestoresmadrid.core.circular.model.vo;

import java.io.Serializable;
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
@Table(name="CIRCULAR")
public class CircularVO implements Serializable{

	private static final long serialVersionUID = 8614683361874882928L;

	@Id
	@SequenceGenerator(name = "circular_secuencia", sequenceName = "ID_CIRCULAR_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "circular_secuencia")
	@Column(name = "ID_CIRCULAR")
	private Long idCircular;
	
	@Column(name="NUM_CIRCULAR")
	private String numCircular;
	
	@Column(name="ESTADO")
	private String estado;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="FECHA_INICIO")
	private Date fechaInicio;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="FECHA_FIN")
	private Date fechaFin;
	
	@Column(name="TEXTO")
	private String texto;
	
	@Column(name="DIAS")
	private String dias;
	
	@Column(name="REPETICIONES")
	private BigDecimal repeticiones;
	
	@Column(name="FECHA")
	private BigDecimal fecha;
	
	public Long getIdCircular() {
		return idCircular;
	}

	public void setIdCircular(Long idCircular) {
		this.idCircular = idCircular;
	}

	public String getNumCircular() {
		return numCircular;
	}

	public void setNumCircular(String numCircular) {
		this.numCircular = numCircular;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
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

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getDias() {
		return dias;
	}

	public void setDias(String dias) {
		this.dias = dias;
	}

	public BigDecimal getRepeticiones() {
		return repeticiones;
	}

	public void setRepeticiones(BigDecimal repeticiones) {
		this.repeticiones = repeticiones;
	}

	public BigDecimal getFecha() {
		return fecha;
	}

	public void setFecha(BigDecimal fecha) {
		this.fecha = fecha;
	}
}
