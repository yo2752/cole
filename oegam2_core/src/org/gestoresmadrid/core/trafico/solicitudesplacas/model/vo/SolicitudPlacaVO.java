package org.gestoresmadrid.core.trafico.solicitudesplacas.model.vo;

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
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.personas.model.vo.PersonaVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoVO;

/**
 * The persistent class for the SOLICITUD_PLACA database table.
 * 
 */
@Entity
@Table(name="SOLICITUD_PLACA")
//@NamedQuery(name="SolicitudPlaca.findAll1", query="SELECT s FROM SolicitudPlaca s")
public class SolicitudPlacaVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="solicitud_placa_seq_gen")
	@SequenceGenerator(name="solicitud_placa_seq_gen", sequenceName="SOLICITUD_PLACA_SEQ")
	@Column(name="ID_SOLICITUD")
	private Integer idSolicitud;

	@Column(name = "NUM_EXPEDIENTE")
	private BigDecimal numExpediente;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_SOLICITUD")
	private Date fechaSolicitud;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_USUARIO", insertable=false, updatable=false)
	private UsuarioVO usuario;

	@Column(name="TIPO_DELANTERA")
	private Integer tipoDelantera;

	@Column(name="TIPO_TRASERA")
	private Integer tipoTrasera;

	@Column(name="TIPO_ADICIONAL")
	private Integer tipoAdicional;

	//bi-directional many-to-one association to TramiteTrafico
	@ManyToOne
	@JoinColumn(name="NUM_EXPEDIENTE", insertable=false, updatable=false)
	private TramiteTraficoVO tramiteTrafico;

	@Column(name="ESTADO")
	private Integer estado;

	@Column(name="ID_VEHICULO")
	private Long idVehiculo;

	@Column(name="NUM_COLEGIADO")
	private String numColegiado;

	@OneToOne
	@JoinColumns({
		@JoinColumn(name="ID_VEHICULO", referencedColumnName="ID_VEHICULO", insertable=false, updatable=false),
		@JoinColumn(name="NUM_COLEGIADO", referencedColumnName="NUM_COLEGIADO", insertable=false, updatable=false)
		})
	private VehiculoVO vehiculo;

	@ManyToOne
	@JoinColumn(name="ID_CONTRATO", referencedColumnName="ID_CONTRATO", insertable=false, updatable=false)
	private ContratoVO contrato;

	@Column(name="ID_CONTRATO")
	private Integer idContrato;

	@OneToOne
	@JoinColumns({
		@JoinColumn(name="NIF_TITULAR", referencedColumnName="NIF", insertable=false, updatable=false),
		@JoinColumn(name="NUM_COLEGIADO", referencedColumnName="NUM_COLEGIADO", insertable=false, updatable=false)
	})
	private PersonaVO titular;

	@Column(name="NIF_TITULAR")
	private String nifTitular;

	@Column(name="ID_USUARIO")
	private Long idUsuario;

	@Column(name="DUPLICADA")
	private Integer duplicada;

	@Column(name="TIPO_VEHICULO")
	private String tipoVehiculo;

	@Column(name="MATRICULA")
	private String matricula;

	@Column(name="BASTIDOR")
	private String bastidor;

	@Column(name="REF_PROPIA")
	private String refPropia;

	public SolicitudPlacaVO() {
	}

	public VehiculoVO getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(VehiculoVO vehiculo) {
		this.vehiculo = vehiculo;
	}

	public Integer getIdSolicitud() {
		return this.idSolicitud;
	}

	public void setIdSolicitud(Integer idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public Date getFechaSolicitud() {
		return this.fechaSolicitud;
	}

	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public Integer getTipoDelantera() {
		return this.tipoDelantera;
	}

	public void setTipoDelantera(Integer tipoDelantera) {
		this.tipoDelantera = tipoDelantera;
	}

	public Integer getTipoTrasera() {
		return this.tipoTrasera;
	}

	public void setTipoTrasera(Integer tipoTrasera) {
		this.tipoTrasera = tipoTrasera;
	}

	public Integer getTipoAdicional() {
		return this.tipoAdicional;
	}

	public void setTipoAdicional(Integer tipoAdicional) {
		this.tipoAdicional = tipoAdicional;
	}

	public TramiteTraficoVO getTramiteTrafico() {
		return this.tramiteTrafico;
	}

	public void setTramiteTrafico(TramiteTraficoVO tramiteTrafico) {
		this.tramiteTrafico = tramiteTrafico;
	}

	public Integer getEstado() {
		return this.estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public String getNifTitular() {
		return nifTitular;
	}

	public void setNifTitular(String nifTitular) {
		this.nifTitular = nifTitular;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Integer getDuplicada() {
		return duplicada;
	}

	public void setDuplicada(Integer duplicada) {
		this.duplicada = duplicada;
	}

	public String getTipoVehiculo() {
		return tipoVehiculo;
	}

	public void setTipoVehiculo(String tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
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

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
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

	public Integer getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Integer idContrato) {
		this.idContrato = idContrato;
	}

	public PersonaVO getTitular() {
		return titular;
	}

	public void setTitular(PersonaVO titular) {
		this.titular = titular;
	}

	public String getRefPropia() {
		return refPropia;
	}

	public void setRefPropia(String refPropia) {
		this.refPropia = refPropia;
	}

	public Long getIdVehiculo() {
		return idVehiculo;
	}

	public void setIdVehiculo(Long idVehiculo) {
		this.idVehiculo = idVehiculo;
	}

}