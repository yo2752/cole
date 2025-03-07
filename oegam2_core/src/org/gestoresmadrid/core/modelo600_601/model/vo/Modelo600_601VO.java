package org.gestoresmadrid.core.modelo600_601.model.vo;

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

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.datosBancariosFavoritos.model.vo.DatosBancariosFavoritosVO;
import org.gestoresmadrid.core.intervinienteModelos.model.vo.IntervinienteModelosVO;
import org.gestoresmadrid.core.modelo.bien.model.vo.ModeloBienVO;
import org.gestoresmadrid.core.modelos.model.vo.BonificacionVO;
import org.gestoresmadrid.core.modelos.model.vo.ConceptoVO;
import org.gestoresmadrid.core.modelos.model.vo.FundamentoExencionVO;
import org.gestoresmadrid.core.modelos.model.vo.ModeloVO;
import org.gestoresmadrid.core.modelos.model.vo.OficinaLiquidadoraVO;
import org.gestoresmadrid.core.modelos.model.vo.TipoImpuestoVO;
import org.gestoresmadrid.core.notario.model.vo.NotarioVO;
import org.gestoresmadrid.core.remesa.model.vo.RemesaVO;

@Entity
@Table(name="MODELO_600_601")
public class Modelo600_601VO implements Serializable{

	private static final long serialVersionUID = 3217617853939536366L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="SEC_ID_MODELO_600_601")
	@SequenceGenerator(name="SEC_ID_MODELO_600_601", sequenceName="ID_MODELO_600_601_SEQ")
	@Column(name="ID_MODELO")
	private Long idModelo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="MODELO", referencedColumnName="MODELO"),
		@JoinColumn(name="VERSION", referencedColumnName="VERSION")
	})
	private ModeloVO modelo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="MODELO",referencedColumnName="MODELO",insertable=false,updatable=false),
		@JoinColumn(name="VERSION",referencedColumnName="VERSION",insertable=false,updatable=false),
		@JoinColumn(name="CONCEPTO",referencedColumnName="CONCEPTO",insertable=false,updatable=false)
	})
	private ConceptoVO concepto;
	
	@Column(name="CONCEPTO")
	private String idConcepto;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_ALTA")
	private Date fechaAlta;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="TIPO_IMPUESTO",referencedColumnName="TIPO_IMPUESTO")
	private TipoImpuestoVO tipoImpuesto;
	
	@Column(name="ESTADO_MODE")
	private BigDecimal estado;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ID_CONTRATO", insertable=false,updatable=false)
	private ContratoVO contrato;
	
	@Column(name="ID_CONTRATO")
	private Long idContrato;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CODIGO_NOTARIO")
	private NotarioVO notario;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BONIFICACION",insertable=false,updatable=false)
	private BonificacionVO bonificacion;
	
	@Column(name="BONIFICACION")
	private String idBonificacion;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FUNDAMENTO_EXENCION",insertable=false,updatable=false)
	private FundamentoExencionVO fundamentoExencion;
	
	@Column(name="FUNDAMENTO_EXENCION")
	private String idFundamentoExencion;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_DEVENGO")
	private Date fechaDevengo;
	
	@Column(name="VALOR_DECLARADO")
	private BigDecimal valorDeclarado;
	
	@Column(name="CUOTA")
	private BigDecimal cuota;
	
	@Column(name="BASE_IMPONIBLE")
	private BigDecimal baseImponible;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_PRESENTACION")
	private Date fechaPresentacion;
	
	@Column(name="BASE_LIQUIDABLE")
	private BigDecimal baseLiquidable;
	
	@Column(name="INGRESAR")
	private BigDecimal ingresar;
	
	@Column(name="TOTAL_INGRESAR")
	private BigDecimal totalIngresar;
	
	@Column(name="EXENTO")
	private BigDecimal exento;
	
	@Column(name="NO_SUJETO")
	private BigDecimal noSujeto;
	
	@Column(name="NUMERO_PROTOCOLO")
	private BigDecimal numProtocolo;
	
	@Column(name="PROTOCOLO_BIS")
	private BigDecimal protocoloBis;
	
	@Column(name="FUNDAMENTO_NO_SUJECION")
	private String fundamentoNoSujeccion;
	
	@Column(name="BONIFICACION_CUOTA")
	private BigDecimal bonificacionCuota;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ID_REMESA",insertable=false,updatable=false)
	private RemesaVO remesa;
	
	@Column(name="ID_REMESA")
	private Long idRemesa;
	
	@Column(name="NUM_COLEGIADO")
	private String numColegiado;
	
	@Column(name = "NUM_EXPEDIENTE")
	private BigDecimal numExpediente;
	
	@Column(name = "TIPO_DOCUMENTO")
	private String tipoDocumento;
	
	@Column(name="DURACION_DERECHO_REAL")
	private String duracionDerechoReal;
	
	@Column(name="NUM_ANIO")
	private BigDecimal numAnio;	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="NACIMIENTO_USUFRUCTUARIO")
	private Date nacimientoUsufructuario;
	
	@Column(name="IMPORTE_RENTA")
	private BigDecimal importeRenta;
	
	@Column(name="DURACION_RENTA")
	private String duracionRenta;
	
	@Column(name="TIPO_PERIODO_RENTA")
	private String tipoPeriodoRenta;
	
	@Column(name="PRESCRITO")
	private BigDecimal prescrito;
	
	@Column(name="LIQUIDACION_COMPLEMENTARIA")
	private BigDecimal liquidacionComplementaria;
	
//	@Column(name="NUM_JUSTI_PRIMERA_AUTOLIQ")
//	private String numJustiPrimeraAutolq;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="FECHA_PRIMERA_LIQUIDACION")
	private Date fechaPrimLiquidacion;
	
	@Column(name="NUMERO_PRIMERA_PRESENTACION")
	private BigDecimal numeroPrimPresentacion;
	
	@Column(name="DESC_DERECHO_REAL")
	private String descDerechoReal;

	@Column(name="NUM_PERIODO_RENTA")
	private BigDecimal numPeriodoRenta;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="FECHA_GENERACION")
	private Date fechaGeneracion;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name = "OFICINA_LIQUIDADORA",referencedColumnName="OFICINA_LIQUIDADORA"),
		@JoinColumn(name = "ID_PROVINCIA", referencedColumnName="ID_PROVINCIA")
	})
	private OficinaLiquidadoraVO oficinaLiquidadora; 
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_DATOS_BANCARIOS",insertable=false,updatable=false)
	private DatosBancariosFavoritosVO datosBancarios;
	
	@Column(name="ID_DATOS_BANCARIOS")
	private Long idDatosBancarios;
	
	@Column(name="DATOS_BANCARIOS")
	private String numCuentaPago;
	
	@Column(name="NIF_PAGADOR")
	private String nifPagador;
	
	@OneToMany(mappedBy = "modelo600601")
	private Set<ResultadoModelo600601VO> listaResultadoModelo;
	
	@OneToMany(mappedBy="modelo600_601")
	private Set<IntervinienteModelosVO> listaIntervinientes;
	
	@OneToMany(mappedBy = "modelo600_601")
	private Set<ModeloBienVO> listaBienes;
	
	@Column(name="NOMBRE_ESCRITURA")
	private String nombreEscritura;
	
	@Column(name="LIQUIDACION_MANUAL")
	private boolean liquidacionManual;
	
	@Column(name="REFERENCIA_PROPIA")
	private String referenciaPropia;
	

	public Long getIdModelo() {
		return idModelo;
	}

	public void setIdModelo(Long idModelo) {
		this.idModelo = idModelo;
	}

	public ModeloVO getModelo() {
		return modelo;
	}

	public void setModelo(ModeloVO modelo) {
		this.modelo = modelo;
	}

	public ConceptoVO getConcepto() {
		return concepto;
	}

	public void setConcepto(ConceptoVO concepto) {
		this.concepto = concepto;
	}

	public String getIdConcepto() {
		return idConcepto;
	}

	public void setIdConcepto(String idConcepto) {
		this.idConcepto = idConcepto;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
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

	public BigDecimal getValorDeclarado() {
		return valorDeclarado;
	}

	public void setValorDeclarado(BigDecimal valorDeclarado) {
		this.valorDeclarado = valorDeclarado;
	}

	public BigDecimal getCuota() {
		return cuota;
	}

	public void setCuota(BigDecimal cuota) {
		this.cuota = cuota;
	}

	public BigDecimal getBaseImponible() {
		return baseImponible;
	}

	public void setBaseImponible(BigDecimal baseImponible) {
		this.baseImponible = baseImponible;
	}

	public Date getFechaPresentacion() {
		return fechaPresentacion;
	}

	public void setFechaPresentacion(Date fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}

	public BigDecimal getBaseLiquidable() {
		return baseLiquidable;
	}

	public void setBaseLiquidable(BigDecimal baseLiquidable) {
		this.baseLiquidable = baseLiquidable;
	}

	public BigDecimal getIngresar() {
		return ingresar;
	}

	public void setIngresar(BigDecimal ingresar) {
		this.ingresar = ingresar;
	}

	public BigDecimal getTotalIngresar() {
		return totalIngresar;
	}

	public void setTotalIngresar(BigDecimal totalIngresar) {
		this.totalIngresar = totalIngresar;
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

	public BigDecimal getBonificacionCuota() {
		return bonificacionCuota;
	}

	public void setBonificacionCuota(BigDecimal bonificacionCuota) {
		this.bonificacionCuota = bonificacionCuota;
	}

	public RemesaVO getRemesa() {
		return remesa;
	}

	public void setRemesa(RemesaVO remesa) {
		this.remesa = remesa;
	}

	public Long getIdRemesa() {
		return idRemesa;
	}

	public void setIdRemesa(Long idRemesa) {
		this.idRemesa = idRemesa;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
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

	public Date getNacimientoUsufructuario() {
		return nacimientoUsufructuario;
	}

	public void setNacimientoUsufructuario(Date nacimientoUsufructuario) {
		this.nacimientoUsufructuario = nacimientoUsufructuario;
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

//	public String getNumJustiPrimeraAutolq() {
//		return numJustiPrimeraAutolq;
//	}
//
//	public void setNumJustiPrimeraAutolq(String numJustiPrimeraAutolq) {
//		this.numJustiPrimeraAutolq = numJustiPrimeraAutolq;
//	}

	public Date getFechaPrimLiquidacion() {
		return fechaPrimLiquidacion;
	}

	public void setFechaPrimLiquidacion(Date fechaPrimLiquidacion) {
		this.fechaPrimLiquidacion = fechaPrimLiquidacion;
	}

	public BigDecimal getNumeroPrimPresentacion() {
		return numeroPrimPresentacion;
	}

	public void setNumeroPrimPresentacion(BigDecimal numeroPrimPresentacion) {
		this.numeroPrimPresentacion = numeroPrimPresentacion;
	}

	public String getDescDerechoReal() {
		return descDerechoReal;
	}

	public void setDescDerechoReal(String descDerechoReal) {
		this.descDerechoReal = descDerechoReal;
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

	public OficinaLiquidadoraVO getOficinaLiquidadora() {
		return oficinaLiquidadora;
	}

	public void setOficinaLiquidadora(OficinaLiquidadoraVO oficinaLiquidadora) {
		this.oficinaLiquidadora = oficinaLiquidadora;
	}

	public Set<ResultadoModelo600601VO> getListaResultadoModelo() {
		return listaResultadoModelo;
	}

	public void setListaResultadoModelo(
			Set<ResultadoModelo600601VO> listaResultadoModelo) {
		this.listaResultadoModelo = listaResultadoModelo;
	}

	public Set<IntervinienteModelosVO> getListaIntervinientes() {
		return listaIntervinientes;
	}

	public void setListaIntervinientes(
			Set<IntervinienteModelosVO> listaIntervinientes) {
		this.listaIntervinientes = listaIntervinientes;
	}

	public Set<ModeloBienVO> getListaBienes() {
		return listaBienes;
	}

	public void setListaBienes(Set<ModeloBienVO> listaBienes) {
		this.listaBienes = listaBienes;
	}

	public DatosBancariosFavoritosVO getDatosBancarios() {
		return datosBancarios;
	}

	public void setDatosBancarios(DatosBancariosFavoritosVO datosBancarios) {
		this.datosBancarios = datosBancarios;
	}

	public String getNumCuentaPago() {
		return numCuentaPago;
	}

	public void setNumCuentaPago(String numCuentaPago) {
		this.numCuentaPago = numCuentaPago;
	}

	public String getNifPagador() {
		return nifPagador;
	}

	public void setNifPagador(String nifPagador) {
		this.nifPagador = nifPagador;
	}

	public Long getIdDatosBancarios() {
		return idDatosBancarios;
	}

	public void setIdDatosBancarios(Long idDatosBancarios) {
		this.idDatosBancarios = idDatosBancarios;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	/**
	 * @return the nombreEscritura
	 */
	public String getNombreEscritura() {
		return nombreEscritura;
	}

	/**
	 * @param nombreEscritura the nombreEscritura to set
	 */
	public void setNombreEscritura(String nombreEscritura) {
		this.nombreEscritura = nombreEscritura;
	}

	/**
	 * @return the liquidacionManual
	 */
	public boolean isLiquidacionManual() {
		return liquidacionManual;
	}

	/**
	 * @param liquidacionManual the liquidacionManual to set
	 */
	public void setLiquidacionManual(boolean liquidacionManual) {
		this.liquidacionManual = liquidacionManual;
	}

	public String getIdBonificacion() {
		return idBonificacion;
	}

	public void setIdBonificacion(String idBonificacion) {
		this.idBonificacion = idBonificacion;
	}

	public String getIdFundamentoExencion() {
		return idFundamentoExencion;
	}

	public void setIdFundamentoExencion(String idFundamentoExencion) {
		this.idFundamentoExencion = idFundamentoExencion;
	}

	public String getReferenciaPropia() {
		return referenciaPropia;
	}

	public void setReferenciaPropia(String referenciaPropia) {
		this.referenciaPropia = referenciaPropia;
	}
	
}