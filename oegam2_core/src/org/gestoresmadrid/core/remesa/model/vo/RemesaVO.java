package org.gestoresmadrid.core.remesa.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.gestoresmadrid.core.bien.model.vo.BienRemesaVO;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.intervinienteModelos.model.vo.IntervinienteModelosVO;
import org.gestoresmadrid.core.modelo600_601.model.vo.Modelo600_601VO;
import org.gestoresmadrid.core.modelos.model.vo.BonificacionVO;
import org.gestoresmadrid.core.modelos.model.vo.ConceptoVO;
import org.gestoresmadrid.core.modelos.model.vo.FundamentoExencionVO;
import org.gestoresmadrid.core.modelos.model.vo.ModeloVO;
import org.gestoresmadrid.core.modelos.model.vo.OficinaLiquidadoraVO;
import org.gestoresmadrid.core.modelos.model.vo.TipoImpuestoVO;
import org.gestoresmadrid.core.notario.model.vo.NotarioVO;
import org.gestoresmadrid.core.participacion.model.vo.ParticipacionVO;

@Entity
@Table(name="REMESA")
public class RemesaVO implements Serializable{

	private static final long serialVersionUID = -221361355262434340L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="SEC_ID_REMESA")
	@SequenceGenerator(name="SEC_ID_REMESA", sequenceName="ID_REMESA_SEQ")
	@Column(name="ID_REMESA")
	private Long idRemesa;
	
	@Column(name = "NUM_EXPEDIENTE")
	private BigDecimal numExpediente;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_ALTA")
	private Date fechaAlta;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="MODELO", referencedColumnName="MODELO"),
		@JoinColumn(name="VERSION", referencedColumnName="VERSION")
	})
	private ModeloVO modelo;
	
	@Column(name = "ESTADO_REMESA")
	private BigDecimal estado;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BONIFICACION",referencedColumnName="BONIFICACION")
	private BonificacionVO bonificacion;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FUNDAMENTO_EXENCION",referencedColumnName="FUNDAMENTO_EXENCION")
	private FundamentoExencionVO fundamentoExencion;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_DEVENGO")
	private Date fechaDevengo;
	
	@Column(name = "IMPORTE_TOTAL")
	private BigDecimal importeTotal;
	
	@Column(name = "N_BIENES")
	private BigDecimal numBienes;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_PRESENTACION")
	private Date fechaPresentacion;
	
	@Column(name = "EXENTO")
	private BigDecimal exento;
	
	@Column(name = "NO_SUJETO")
	private BigDecimal noSujeto;
	
	@Column(name = "NUMERO_PROTOCOLO")
	private BigDecimal numProtocolo;
	
	@Column(name = "PROTOCOLO_BIS")
	private BigDecimal protocoloBis;
	
	@Column(name = "FUNDAMENTO_NO_SUJECION")
	private String fundamentoNoSujeccion;
	
	@Column(name = "TIPO_DOCUMENTO")
	private String tipoDocumento;
	
	@Column(name = "PRESCRITO")
	private BigDecimal prescrito;
	
	@Column(name = "LIQUIDACION_COMPLEMENTARIA")
	private BigDecimal liquidacionComplementaria;
	
	@Column(name = "NUM_JUSTI_PRIMERA_AUTOLIQ")
	private String numJustiPrimeraAutoliq;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_PRIMERA_LIQUIDACION")
	private Date fechaPrimLiquidacion;
	
	@Column(name = "NUMERO_PRIMERA_PRESENTACION")
	private BigDecimal numPrimPresentacion;
	
	@Column(name = "DESC_DERECHO_REAL")
	private String descDerechoReal;
	
	@Column(name = "DURACION_DERECHO_REAL")
	private String duracionDerechoReal;
	
	@Column(name = "NUM_ANIO")
	private BigDecimal numAnio;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "NACIMIENTO_USUFRUCTUARIO")
	private Date nacimientoUsuFructuario;
	
	@Column(name = "IMPORTE_RENTA")
	private BigDecimal importeRenta;
	
	@Column(name = "DURACION_RENTA")
	private String duracionRenta;
	
	@Column(name = "TIPO_PERIODO_RENTA")
	private String tipoPeriodoRenta;
	
	@Column(name = "NUM_PERIODO_RENTA")
	private BigDecimal numPeriodoRenta;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_GENERACION")
	private Date fechaGeneracion;
	
	@Column(name="NUM_COLEGIADO")
	private String numColegiado;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CONTRATO",referencedColumnName="ID_CONTRATO")
	private ContratoVO contrato;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CODIGO_NOTARIO")
	private NotarioVO notario;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="MODELO",referencedColumnName="MODELO",insertable=false,updatable=false),
		@JoinColumn(name="VERSION",referencedColumnName="VERSION",insertable=false,updatable=false),
		@JoinColumn(name="CONCEPTO",referencedColumnName="CONCEPTO",insertable=false,updatable=false)
	})
	private ConceptoVO concepto;
	
	@Column(name="CONCEPTO")
	private String idConcepto;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="TIPO_IMPUESTO",referencedColumnName="TIPO_IMPUESTO")
	private TipoImpuestoVO tipoImpuesto;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name = "OFICINA_LIQUIDADORA",referencedColumnName="OFICINA_LIQUIDADORA"),
		@JoinColumn(name = "ID_PROVINCIA", referencedColumnName="ID_PROVINCIA")
	})
	private OficinaLiquidadoraVO oficinaLiquidadora; 

	@OneToMany(mappedBy = "remesa")
	private Set<BienRemesaVO> listaBienes;
	
	@OneToMany(mappedBy="remesa")
	private Set<IntervinienteModelosVO> listaIntervinientes;

	@OneToMany(mappedBy = "remesa")
	private Set<ParticipacionVO> listaCoefParticipacion;
	
	@OneToMany(mappedBy = "remesa")
	private Set<Modelo600_601VO> listaModelos;
	
	@Column(name="REFERENCIA_PROPIA")
	private String referenciaPropia;

	public Long getIdRemesa() {
		return idRemesa;
	}

	public void setIdRemesa(Long idRemesa) {
		this.idRemesa = idRemesa;
	}

	public Set<BienRemesaVO> getListaBienes() {
		return listaBienes;
	}

	public void setListaBienes(Set<BienRemesaVO> listaBienes) {
		this.listaBienes = listaBienes;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}


	public ConceptoVO getConcepto() {
		return concepto;
	}

	public void setConcepto(ConceptoVO concepto) {
		this.concepto = concepto;
	}

	public TipoImpuestoVO getTipoImpuesto() {
		return tipoImpuesto;
	}

	public void setTipoImpuesto(TipoImpuestoVO tipoImpuesto) {
		this.tipoImpuesto = tipoImpuesto;
	}

	public BigDecimal getEstado() {
		return estado;
	}

	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}

	public BonificacionVO getBonificacion() {
		return bonificacion;
	}

	public void setBonificacion(BonificacionVO bonificacion) {
		this.bonificacion = bonificacion;
	}


	public FundamentoExencionVO getFundamentoExencion() {
		return fundamentoExencion;
	}

	public void setFundamentoExencion(FundamentoExencionVO fundamentoExencion) {
		this.fundamentoExencion = fundamentoExencion;
	}

	public Date getFechaDevengo() {
		return fechaDevengo;
	}

	public void setFechaDevengo(Date fechaDevengo) {
		this.fechaDevengo = fechaDevengo;
	}

	public BigDecimal getImporteTotal() {
		return importeTotal;
	}

	public void setImporteTotal(BigDecimal importeTotal) {
		this.importeTotal = importeTotal;
	}

	public Date getFechaPresentacion() {
		return fechaPresentacion;
	}

	public void setFechaPresentacion(Date fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}

	public BigDecimal getExento() {
		return exento;
	}

	public void setExento(BigDecimal exento) {
		this.exento = exento;
	}

	public BigDecimal getNoSujeto() {
		return noSujeto;
	}

	public void setNoSujeto(BigDecimal noSujeto) {
		this.noSujeto = noSujeto;
	}

	public BigDecimal getNumProtocolo() {
		return numProtocolo;
	}

	public void setNumProtocolo(BigDecimal numProtocolo) {
		this.numProtocolo = numProtocolo;
	}

	public BigDecimal getProtocoloBis() {
		return protocoloBis;
	}

	public void setProtocoloBis(BigDecimal protocoloBis) {
		this.protocoloBis = protocoloBis;
	}

	public String getFundamentoNoSujeccion() {
		return fundamentoNoSujeccion;
	}

	public void setFundamentoNoSujeccion(String fundamentoNoSujeccion) {
		this.fundamentoNoSujeccion = fundamentoNoSujeccion;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public BigDecimal getPrescrito() {
		return prescrito;
	}

	public void setPrescrito(BigDecimal prescrito) {
		this.prescrito = prescrito;
	}

	public BigDecimal getLiquidacionComplementaria() {
		return liquidacionComplementaria;
	}

	public void setLiquidacionComplementaria(BigDecimal liquidacionComplementaria) {
		this.liquidacionComplementaria = liquidacionComplementaria;
	}

	public String getNumJustiPrimeraAutoliq() {
		return numJustiPrimeraAutoliq;
	}

	public void setNumJustiPrimeraAutoliq(String numJustiPrimeraAutoliq) {
		this.numJustiPrimeraAutoliq = numJustiPrimeraAutoliq;
	}

	public Date getFechaPrimLiquidacion() {
		return fechaPrimLiquidacion;
	}

	public void setFechaPrimLiquidacion(Date fechaPrimLiquidacion) {
		this.fechaPrimLiquidacion = fechaPrimLiquidacion;
	}

	public BigDecimal getNumPrimPresentacion() {
		return numPrimPresentacion;
	}

	public void setNumPrimPresentacion(BigDecimal numPrimPresentacion) {
		this.numPrimPresentacion = numPrimPresentacion;
	}

	public String getDescDerechoReal() {
		return descDerechoReal;
	}

	public void setDescDerechoReal(String descDerechoReal) {
		this.descDerechoReal = descDerechoReal;
	}

	public String getDuracionDerechoReal() {
		return duracionDerechoReal;
	}

	public void setDuracionDerechoReal(String duracionDerechoReal) {
		this.duracionDerechoReal = duracionDerechoReal;
	}

	public BigDecimal getNumAnio() {
		return numAnio;
	}

	public void setNumAnio(BigDecimal numAnio) {
		this.numAnio = numAnio;
	}

	public Date getNacimientoUsuFructuario() {
		return nacimientoUsuFructuario;
	}

	public void setNacimientoUsuFructuario(Date nacimientoUsuFructuario) {
		this.nacimientoUsuFructuario = nacimientoUsuFructuario;
	}

	public BigDecimal getImporteRenta() {
		return importeRenta;
	}

	public void setImporteRenta(BigDecimal importeRenta) {
		this.importeRenta = importeRenta;
	}

	public String getDuracionRenta() {
		return duracionRenta;
	}

	public void setDuracionRenta(String duracionRenta) {
		this.duracionRenta = duracionRenta;
	}

	public String getTipoPeriodoRenta() {
		return tipoPeriodoRenta;
	}

	public void setTipoPeriodoRenta(String tipoPeriodoRenta) {
		this.tipoPeriodoRenta = tipoPeriodoRenta;
	}

	public BigDecimal getNumPeriodoRenta() {
		return numPeriodoRenta;
	}

	public void setNumPeriodoRenta(BigDecimal numPeriodoRenta) {
		this.numPeriodoRenta = numPeriodoRenta;
	}

	public Date getFechaGeneracion() {
		return fechaGeneracion;
	}

	public void setFechaGeneracion(Date fechaGeneracion) {
		this.fechaGeneracion = fechaGeneracion;
	}

	public ContratoVO getContrato() {
		return contrato;
	}

	public void setContrato(ContratoVO contrato) {
		this.contrato = contrato;
	}

	public NotarioVO getNotario() {
		return notario;
	}

	public void setNotario(NotarioVO notario) {
		this.notario = notario;
	}

	public OficinaLiquidadoraVO getOficinaLiquidadora() {
		return oficinaLiquidadora;
	}

	public void setOficinaLiquidadora(OficinaLiquidadoraVO oficinaLiquidadora) {
		this.oficinaLiquidadora = oficinaLiquidadora;
	}

	public Set<IntervinienteModelosVO> getListaIntervinientes() {
		return listaIntervinientes;
	}

	public void setListaIntervinientes(
			Set<IntervinienteModelosVO> listaIntervinientes) {
		this.listaIntervinientes = listaIntervinientes;
	}

	public Set<ParticipacionVO> getListaCoefParticipacion() {
		return listaCoefParticipacion;
	}

	public void setListaCoefParticipacion(
			Set<ParticipacionVO> listaCoefParticipacion) {
		this.listaCoefParticipacion = listaCoefParticipacion;
	}

	public BigDecimal getNumBienes() {
		return numBienes;
	}

	public void setNumBienes(BigDecimal numBienes) {
		this.numBienes = numBienes;
	}

	public ModeloVO getModelo() {
		return modelo;
	}

	public void setModelo(ModeloVO modelo) {
		this.modelo = modelo;
	}

	public String getIdConcepto() {
		return idConcepto;
	}

	public void setIdConcepto(String idConcepto) {
		this.idConcepto = idConcepto;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public Set<Modelo600_601VO> getListaModelos() {
		return listaModelos;
	}

	public void setListaModelos(Set<Modelo600_601VO> listaModelos) {
		this.listaModelos = listaModelos;
	}

	public String getReferenciaPropia() {
		return referenciaPropia;
	}

	public void setReferenciaPropia(String referenciaPropia) {
		this.referenciaPropia = referenciaPropia;
	}
	
}
