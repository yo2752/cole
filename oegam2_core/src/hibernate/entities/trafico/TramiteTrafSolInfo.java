package hibernate.entities.trafico;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import hibernate.entities.tasas.Tasa;

/**
 * The persistent class for the TRAMITE_TRAF_SOLINFO database table.
 * 
 */
@Entity
@Table(name="TRAMITE_TRAF_SOLINFO")
public class TramiteTrafSolInfo implements Serializable {

	private static final long serialVersionUID = -7813679249783236244L;

	@Column(name = "ID_TRAMITE_SOLINFO")
	@SequenceGenerator(name = "tramite_solInfo_secuencia", sequenceName = "ID_TRAMITE_SOL_INFO_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "tramite_solInfo_secuencia")
	private Long idTramiteSolInfo;

	@EmbeddedId
	private TramiteTrafSolInfoPK id;

	//bi-directional many-to-one association to Tasa
	@ManyToOne (optional=true, fetch=FetchType.LAZY)
	@JoinColumn(name="CODIGO_TASA")
	private Tasa tasa;

	private BigDecimal estado;

	private String resultado;

	@Column(name="REFERENCIA_ATEM")
	private String referenciaAtem;

	@Column(name = "MATRICULA")
	private String matricula;

	@Column(name = "BASTIDOR")
	private String bastidor;

	//uni-directional many-to-one association to Vehiculo
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "NUM_COLEGIADO", referencedColumnName = "NUM_COLEGIADO", insertable = false, updatable = false),
			@JoinColumn(name = "ID_VEHICULO", referencedColumnName = "ID_VEHICULO", insertable = false, updatable = false) })
	private Vehiculo vehiculo;

	//bi-directional many-to-one association to TramiteTrafico
	@ManyToOne(fetch=FetchType.LAZY, optional=true)
	@JoinColumn(name="NUM_EXPEDIENTE", insertable=false, updatable=false)
	private TramiteTrafico tramiteTrafico;

	public TramiteTrafSolInfo() {
	}

	public Tasa getTasa() {
		return tasa;
	}

	public void setTasa(Tasa tasa) {
		this.tasa = tasa;
	}

	public BigDecimal getEstado() {
		return this.estado;
	}

	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}

	public String getResultado() {
		return this.resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public TramiteTrafico getTramiteTrafico() {
		return this.tramiteTrafico;
	}

	public void setTramiteTrafico(TramiteTrafico tramiteTrafico) {
		this.tramiteTrafico = tramiteTrafico;
	}

	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}

	public String getReferenciaAtem() {
		return referenciaAtem;
	}

	public void setReferenciaAtem(String referenciaAtem) {
		this.referenciaAtem = referenciaAtem;
	}

	public Long getIdTramiteSolInfo() {
		return idTramiteSolInfo;
	}

	public void setIdTramiteSolInfo(Long idTramiteSolInfo) {
		this.idTramiteSolInfo = idTramiteSolInfo;
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

	public TramiteTrafSolInfoPK getId() {
		return id;
	}

	public void setId(TramiteTrafSolInfoPK id) {
		this.id = id;
	}

}