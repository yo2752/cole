package org.gestoresmadrid.core.vehiculo.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;

/**
 * The persistent class for the VEHICULO_TRAMITE_TRAFICO database table.
 */
@Entity
@Table(name = "VEHICULO_TRAMITE_TRAFICO")
public class VehiculoTramiteTraficoVO implements Serializable {

	private static final long serialVersionUID = 121514393675537199L;

	@EmbeddedId
	private VehiculoTramiteTraficoPK id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_HORA")
	private Date fechaHora;

	@Column(name = "NUM_COLEGIADO")
	private String numColegiado;

	private BigDecimal kilometros;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NUM_EXPEDIENTE", insertable = false, updatable = false)
	private TramiteTraficoVO tramiteTrafico;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "ID_VEHICULO", referencedColumnName = "ID_VEHICULO", insertable = false, updatable = false),
			@JoinColumn(name = "NUM_COLEGIADO", referencedColumnName = "NUM_COLEGIADO", insertable = false, updatable = false) })
	private VehiculoVO vehiculo;

	public VehiculoTramiteTraficoVO() {}

	public VehiculoTramiteTraficoPK getId() {
		return this.id;
	}

	public void setId(VehiculoTramiteTraficoPK id) {
		this.id = id;
	}

	public Date getFechaHora() {
		return this.fechaHora;
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

	public BigDecimal getKilometros() {
		return this.kilometros;
	}

	public void setKilometros(BigDecimal kilometros) {
		this.kilometros = kilometros;
	}

	public TramiteTraficoVO getTramiteTrafico() {
		return tramiteTrafico;
	}

	public void setTramiteTrafico(TramiteTraficoVO tramiteTrafico) {
		this.tramiteTrafico = tramiteTrafico;
	}

	public VehiculoVO getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(VehiculoVO vehiculo) {
		this.vehiculo = vehiculo;
	}
}