package hibernate.entities.trafico;

import hibernate.entities.general.Contrato;
import hibernate.entities.general.JefaturaTrafico;
import hibernate.entities.general.Usuario;
import hibernate.entities.tasas.Tasa;
import hibernate.entities.yerbabuena.Ybpdf;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the TRAMITE_TRAFICO database table.
 * 
 */
@Entity
@Table(name = "TRAMITE_TRAFICO")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class TramiteTraficoAbstract implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "NUM_EXPEDIENTE", insertable = false, updatable = false)
	private Long numExpediente;

	private String anotaciones;

	@Column(name = "CAMBIO_DOMICILIO")
	private String cambioDomicilio;

	private String cem;

	private String cema;

	private BigDecimal estado;

	@Column(name = "EXENTO_CEM")
	private String exentoCem;

	@Column(name = "EXENTO_IEDTM")
	private String exentoIedtm;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_ALTA")
	private Date fechaAlta;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_IEDTM")
	private Date fechaIedtm;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_IMPRESION")
	private Date fechaImpresion;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_PRESENTACION")
	private Date fechaPresentacion;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_ULT_MODIF")
	private Date fechaUltModif;

	@Column(name = "FINANCIERA_IEDTM")
	private String financieraIedtm;

	@ManyToOne
	@JoinColumn(name = "ID_USUARIO")
	private Usuario usuario;

	private String iedtm;

	// bi-directional many-to-one association to JefaturaTrafico
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "JEFATURA_PROVINCIAL")
	private JefaturaTrafico jefaturaTrafico;

//  //bi-directional one-to-one association to TramiteTrafTran
//    @ManyToOne (fetch=FetchType.LAZY, optional = true)
//    @JoinColumn(name="NUM_EXPEDIENTE",insertable=false, updatable=false)
//	private TramiteTrafTran tramiteTrafTran;

	// bi-directional many-to-one association to IntervinienteTrafico
	@OneToMany(mappedBy = "tramiteTrafico", fetch = FetchType.LAZY)
	private List<IntervinienteTrafico> intervinienteTraficos;

	@Column(name = "MODELO_IEDTM")
	private String modeloIedtm;

	@Column(name = "N_REG_IEDTM")
	private String nRegIedtm;

	@Column(name = "NO_SUJECION_IEDTM")
	private String noSujecionIedtm;

	private String npasos;

	@Column(name = "REF_PROPIA")
	private String refPropia;

	private String renting;

//	@Column(name="RES_CHECK_CTIT")
//	private String resCheckCtit;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NUM_EXPEDIENTE")
	private TramiteTrafTran tramiteTrafTran;

	private String respuesta;

	@Column(name = "TIPO_TRAMITE")
	private String tipoTramite;

	private BigDecimal ybestado;

	// bi-directional many-to-one association to JustificanteProf
	@OneToMany(mappedBy = "tramiteTrafico")
	private List<JustificanteProf> justificanteProfs;

	// bi-directional many-to-one association to Tasa
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "CODIGO_TASA")
	private Tasa tasa;

	// bi-directional many-to-one association to Ybpdf
	@ManyToOne
	@JoinColumn(name = "ID_YBPDF")
	private Ybpdf ybpdf;

	@Column(name = "NUM_COLEGIADO", updatable = false)
	private String numColegiado;

	// uni-directional many-to-one association to Vehiculo
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "NUM_COLEGIADO", referencedColumnName = "NUM_COLEGIADO", insertable = false, updatable = false),
			@JoinColumn(name = "ID_VEHICULO", referencedColumnName = "ID_VEHICULO", insertable = false, updatable = false) })
	private Vehiculo vehiculo;

	// bi-directional many-to-one association to Contrato
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CONTRATO")
	private Contrato contrato;

	public TramiteTraficoAbstract() {
	}

	public Long getNumExpediente() {
		return this.numExpediente;
	}

	public void setNumExpediente(Long numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getAnotaciones() {
		return this.anotaciones;
	}

	public void setAnotaciones(String anotaciones) {
		this.anotaciones = anotaciones;
	}

	public String getCambioDomicilio() {
		return this.cambioDomicilio;
	}

	public void setCambioDomicilio(String cambioDomicilio) {
		this.cambioDomicilio = cambioDomicilio;
	}

	public String getCem() {
		return this.cem;
	}

	public void setCem(String cem) {
		this.cem = cem;
	}

	public String getCema() {
		return this.cema;
	}

	public void setCema(String cema) {
		this.cema = cema;
	}

	public Tasa getTasa() {
		return this.tasa;
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

	public String getExentoCem() {
		return this.exentoCem;
	}

	public void setExentoCem(String exentoCem) {
		this.exentoCem = exentoCem;
	}

	public String getExentoIedtm() {
		return this.exentoIedtm;
	}

	public void setExentoIedtm(String exentoIedtm) {
		this.exentoIedtm = exentoIedtm;
	}

	public Date getFechaAlta() {
		return this.fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaIedtm() {
		return this.fechaIedtm;
	}

	public void setFechaIedtm(Date fechaIedtm) {
		this.fechaIedtm = fechaIedtm;
	}

	public Date getFechaImpresion() {
		return this.fechaImpresion;
	}

	public void setFechaImpresion(Date fechaImpresion) {
		this.fechaImpresion = fechaImpresion;
	}

	public Date getFechaPresentacion() {
		return this.fechaPresentacion;
	}

	public void setFechaPresentacion(Date fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}

	public Date getFechaUltModif() {
		return this.fechaUltModif;
	}

	public void setFechaUltModif(Date fechaUltModif) {
		this.fechaUltModif = fechaUltModif;
	}

	public String getFinancieraIedtm() {
		return this.financieraIedtm;
	}

	public void setFinancieraIedtm(String financieraIedtm) {
		this.financieraIedtm = financieraIedtm;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getIedtm() {
		return this.iedtm;
	}

	public void setIedtm(String iedtm) {
		this.iedtm = iedtm;
	}

	public JefaturaTrafico getJefaturaTrafico() {
		return this.jefaturaTrafico;
	}

	public void setJefaturaTrafico(JefaturaTrafico jefaturaTrafico) {
		this.jefaturaTrafico = jefaturaTrafico;
	}

	public String getModeloIedtm() {
		return this.modeloIedtm;
	}

	public void setModeloIedtm(String modeloIedtm) {
		this.modeloIedtm = modeloIedtm;
	}

	public String getNRegIedtm() {
		return this.nRegIedtm;
	}

	public void setNRegIedtm(String nRegIedtm) {
		this.nRegIedtm = nRegIedtm;
	}

	public String getNoSujecionIedtm() {
		return this.noSujecionIedtm;
	}

	public void setNoSujecionIedtm(String noSujecionIedtm) {
		this.noSujecionIedtm = noSujecionIedtm;
	}

	public String getNpasos() {
		return this.npasos;
	}

	public void setNpasos(String npasos) {
		this.npasos = npasos;
	}

	public String getRefPropia() {
		return this.refPropia;
	}

	public void setRefPropia(String refPropia) {
		this.refPropia = refPropia;
	}

	public String getRenting() {
		return this.renting;
	}

	public void setRenting(String renting) {
		this.renting = renting;
	}

//	public String getResCheckCtit() {
//		return this.resCheckCtit;
//	}

//	public void setResCheckCtit(String resCheckCtit) {
//		this.resCheckCtit = resCheckCtit;
//	}

	public String getRespuesta() {
		return this.respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public String getTipoTramite() {
		return this.tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public Vehiculo getVehiculo() {
		return this.vehiculo;
	}

	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}

	public Contrato getContrato() {
		return this.contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public TramiteTrafTran getTramiteTrafTran() {
		return this.tramiteTrafTran;
	}

	public void setTramiteTrafTran(TramiteTrafTran tramiteTrafTran) {
		this.tramiteTrafTran = tramiteTrafTran;
	}

	public List<IntervinienteTrafico> getIntervinienteTraficos() {
		return this.intervinienteTraficos;
	}

	public void setIntervinienteTraficos(List<IntervinienteTrafico> intervinienteTraficos) {
		this.intervinienteTraficos = intervinienteTraficos;
	}

	public Ybpdf getYbpdf() {
		return ybpdf;
	}

	public void setYbpdf(Ybpdf ybpdf) {
		this.ybpdf = ybpdf;
	}

//	public Cola getCola() {
//		return cola;
//	}

//	public void setCola(Cola cola) {
//		this.cola = cola;
//	}

	public List<JustificanteProf> getJustificanteProfs() {
		return this.justificanteProfs;
	}

	public void setJustificanteProfs(List<JustificanteProf> justificanteProfs) {
		this.justificanteProfs = justificanteProfs;
	}

}