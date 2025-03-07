package hibernate.entities.trafico;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * The persistent class for the VEHICULO_TRAMITE_TRAFICO database table.
 * 
 */
@Entity
@Table(name="VEHICULO_TRAMITE_TRAFICO")
public class VehiculoTramiteTrafico implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private VehiculoTramiteTraficoPK id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="FECHA_HORA")
	private Date fechaHora;

	private BigDecimal kilometros;

	//bi-directional many-to-one association to TramiteTrafico
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="NUM_EXPEDIENTE", insertable=false, updatable=false)
	private TramiteTrafico tramiteTrafico;

	//bi-directional many-to-one association to Vehiculo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="ID_VEHICULO", referencedColumnName="ID_VEHICULO", insertable=false, updatable=false),
		@JoinColumn(name="NUM_COLEGIADO", referencedColumnName="NUM_COLEGIADO", insertable=false, updatable=false)
	})
	private Vehiculo vehiculo;

	public VehiculoTramiteTrafico() {
	}

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

	public BigDecimal getKilometros() {
		return this.kilometros;
	}

	public void setKilometros(BigDecimal kilometros) {
		this.kilometros = kilometros;
	}

	public TramiteTrafico getTramiteTrafico() {
		return this.tramiteTrafico;
	}

	public void setTramiteTrafico(TramiteTrafico tramiteTrafico) {
		this.tramiteTrafico = tramiteTrafico;
	}

	public Vehiculo getVehiculo() {
		return this.vehiculo;
	}

	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}

}