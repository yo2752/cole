package org.gestoresmadrid.core.trafico.model.vo;

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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.gestoresmadrid.core.tasas.model.vo.TasaVO;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoVO;

@Entity
@Table(name = "TRAMITE_TRAF_SOLINFO")
public class TramiteTrafSolInfoVehiculoVO implements Serializable {

	private static final long serialVersionUID = -2102970289737741790L;

	@EmbeddedId
	private TramiteTrafSolInfoVehiculoPK id;

	@Column(name = "ID_TRAMITE_SOLINFO")
	@SequenceGenerator(name = "tramite_solInfo_secuencia", sequenceName = "ID_TRAMITE_SOL_INFO_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "tramite_solInfo_secuencia")
	private Long idTramiteSolInfo;

	@Column(name = "CODIGO_TASA")
	private String codigoTasa;

	@OneToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "CODIGO_TASA", insertable=false, updatable=false)
	private TasaVO tasa;

	@Column(name = "ESTADO")
	private BigDecimal estado;

	@Column(name = "RESULTADO")
	private String resultado;

	@Column(name = "REFERENCIA_ATEM")
	private String referenciaAtem;

	@Column(name = "MOTIVO_INTEVE")
	private String motivoInteve;

	@Column(name="TIPO_INFORME")
	private String tipoInforme;

	@Column(name = "MATRICULA")
	private String matricula;

	@Column(name = "BASTIDOR")
	private String bastidor;

	// uni-directional many-to-one association to Vehiculo
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "NUM_COLEGIADO", referencedColumnName = "NUM_COLEGIADO", insertable = false, updatable = false),
			@JoinColumn(name = "ID_VEHICULO", referencedColumnName = "ID_VEHICULO", insertable = false, updatable = false) })
	private VehiculoVO vehiculo;

	// bi-directional many-to-one association to TramiteTrafico
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "NUM_EXPEDIENTE", insertable = false, updatable = false)
	private TramiteTrafSolInfoVO tramiteTrafico;

	public TramiteTrafSolInfoVehiculoVO() {}

	public TasaVO getTasa() {
		return tasa;
	}

	public void setTasa(TasaVO tasa) {
		this.tasa = tasa;
	}

	public BigDecimal getEstado() {
		return estado;
	}

	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public String getReferenciaAtem() {
		return referenciaAtem;
	}

	public void setReferenciaAtem(String referenciaAtem) {
		this.referenciaAtem = referenciaAtem;
	}

	public VehiculoVO getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(VehiculoVO vehiculo) {
		this.vehiculo = vehiculo;
	}

	public TramiteTrafSolInfoVO getTramiteTrafico() {
		return tramiteTrafico;
	}

	public void setTramiteTrafico(TramiteTrafSolInfoVO tramiteTrafico) {
		this.tramiteTrafico = tramiteTrafico;
	}

	public String getMotivoInteve() {
		return motivoInteve;
	}

	public void setMotivoInteve(String motivoInteve) {
		this.motivoInteve = motivoInteve;
	}

	public String getTipoInforme() {
		return tipoInforme;
	}

	public void setTipoInforme(String tipoInforme) {
		this.tipoInforme = tipoInforme;
	}

	public Long getIdTramiteSolInfo() {
		return idTramiteSolInfo;
	}

	public void setIdTramiteSolInfo(Long idTramiteSolInfo) {
		this.idTramiteSolInfo = idTramiteSolInfo;
	}

	public String getCodigoTasa() {
		return codigoTasa;
	}

	public void setCodigoTasa(String codigoTasa) {
		this.codigoTasa = codigoTasa;
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

	public TramiteTrafSolInfoVehiculoPK getId() {
		return id;
	}

	public void setId(TramiteTrafSolInfoVehiculoPK id) {
		this.id = id;
	}
}