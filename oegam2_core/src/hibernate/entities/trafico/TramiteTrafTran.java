package hibernate.entities.trafico;

import hibernate.entities.tasas.Tasa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the TRAMITE_TRAF_TRAN database table.
 * 
 */
@Entity
@Table(name="TRAMITE_TRAF_TRAN")
public class TramiteTrafTran implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="NUM_EXPEDIENTE")
	private Long numExpediente;

	@Column(name="ACEPTACION_RESPONS_ITV")
	private String aceptacionResponsItv;

	@Column(name="ACREDITA_HERENCIA_DONACION")
	private String acreditaHerenciaDonacion;

	@Column(name="ACTA_SUBASTA")
	private String actaSubasta;

	@Column(name="ALTA_IVTM")
	private String altaIvtm;

	@Column(name="BASE_IMPONIBLE")
	private BigDecimal baseImponible;

	@Column(name="CAMBIO_SERVICIO")
	private String cambioServicio;

	@Column(name="CET_ITP")
	private String cetItp;

	//bi-directional many-to-one association to Tasa
	@ManyToOne (optional=true, fetch=FetchType.LAZY)
	@JoinColumn(name="CODIGO_TASA_INF")
	private Tasa tasa1;

	//bi-directional many-to-one association to Tasa
	@ManyToOne (optional=true, fetch=FetchType.LAZY)
	@JoinColumn(name="CODIGO_TASA_CAMSER")
	private Tasa tasa2;

	private String consentimiento;

	@Column(name="CONTRATO_FACTURA")
	private String contratoFactura;

	@Column(name="CUOTA_TRIBUTARIA")
	private BigDecimal cuotaTributaria;

	private String dua;

	@Column(name="ESTADO_620")
	private BigDecimal estado620;

	@Column(name="EXENCION_ISD")
	private String exencionIsd;

	@Column(name="EXENTO_ITP")
	private String exentoItp;

	private String factura;

	@Column(name="FECHA_CONTRATO")
	private Date fechaContrato;

	@Temporal( TemporalType.DATE)
	@Column(name="FECHA_DEVENGO_ITP")
	private Date fechaDevengoItp;

	@Column(name="FECHA_FACTURA")
	private Date fechaFactura;

	@Column(name="FUNDAMENTO_EXENCION")
	private String fundamentoExencion;

	@Column(name="FUNDAMENTO_NO_SUJETO_ITP")
	private String fundamentoNoSujetoItp;

	@Column(name="ID_NO_SUJECCION_06")
	private String idNoSujeccion06;

	@Column(name="ID_PROVINCIA_CET")
	private String idProvinciaCet;

	@Column(name="ID_REDUCCION_05")
	private String idReduccion05;

	@Column(name="IMPR_PERMISO_CIRCU")
	private String imprPermisoCircu;

	@Column(name="MODELO_ISD")
	private String modeloIsd;

	@Column(name="MODELO_ITP")
	private String modeloItp;

	@Column(name="MODO_ADJUDICACION")
	private String modoAdjudicacion;

	@Column(name="NO_SUJETO_ISD")
	private String noSujetoIsd;

	@Column(name="NO_SUJETO_ITP")
	private String noSujetoItp;

	@Column(name="NUM_AUTO_ISD")
	private String numAutoIsd;

	@Column(name="NUM_AUTO_ITP")
	private String numAutoItp;

	@Column(name="OFICINA_LIQUIDADORA")
	private String oficinaLiquidadora;

	@Column(name="P_ADQUISICION")
	private BigDecimal pAdquisicion;

	@Column(name="P_REDUCCION_ANUAL")
	private BigDecimal pReduccionAnual;

	@Column(name="SENTENCIA_JUDICIAL")
	private String sentenciaJudicial;

	@Column(name="TIPO_GRAVAMEN")
	private BigDecimal tipoGravamen;

	@Column(name="TIPO_TRANSFERENCIA")
	private String tipoTransferencia;

	@Column(name="VALOR_DECLARADO")
	private BigDecimal valorDeclarado;

	@Column(name="NUM_TITULARES")
	private BigDecimal numtitulares;

	@Column(name="CONSENTIMIENTO_CAMBIO")
	private String consentimientocambio;

	//bi-directional one-to-one association to TramiteTrafico
	@OneToMany(fetch=FetchType.LAZY)
	@JoinColumn(name="NUM_EXPEDIENTE")
	private List<TramiteTrafico> tramiteTrafico;

	@Column(name = "RES_CHECK_CTIT")
	private String resCheckCtit;

	@Column(name = "CHECK_VALOR_DECLARADO_620")
	private String checkValorDeclarado;

	public TramiteTrafTran() {
	}

	public Long getNumExpediente() {
		return this.numExpediente;
	}

	public void setNumExpediente(Long numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getAceptacionResponsItv() {
		return this.aceptacionResponsItv;
	}

	public void setAceptacionResponsItv(String aceptacionResponsItv) {
		this.aceptacionResponsItv = aceptacionResponsItv;
	}

	public String getAcreditaHerenciaDonacion() {
		return this.acreditaHerenciaDonacion;
	}

	public void setAcreditaHerenciaDonacion(String acreditaHerenciaDonacion) {
		this.acreditaHerenciaDonacion = acreditaHerenciaDonacion;
	}

	public String getActaSubasta() {
		return this.actaSubasta;
	}

	public void setActaSubasta(String actaSubasta) {
		this.actaSubasta = actaSubasta;
	}

	public String getAltaIvtm() {
		return this.altaIvtm;
	}

	public void setAltaIvtm(String altaIvtm) {
		this.altaIvtm = altaIvtm;
	}

	public BigDecimal getBaseImponible() {
		return this.baseImponible;
	}

	public void setBaseImponible(BigDecimal baseImponible) {
		this.baseImponible = baseImponible;
	}

	public String getCambioServicio() {
		return this.cambioServicio;
	}

	public void setCambioServicio(String cambioServicio) {
		this.cambioServicio = cambioServicio;
	}

	public String getCetItp() {
		return this.cetItp;
	}

	public void setCetItp(String cetItp) {
		this.cetItp = cetItp;
	}

	public String getConsentimiento() {
		return this.consentimiento;
	}

	public void setConsentimiento(String consentimiento) {
		this.consentimiento = consentimiento;
	}

	public String getContratoFactura() {
		return this.contratoFactura;
	}

	public void setContratoFactura(String contratoFactura) {
		this.contratoFactura = contratoFactura;
	}

	public BigDecimal getCuotaTributaria() {
		return this.cuotaTributaria;
	}

	public void setCuotaTributaria(BigDecimal cuotaTributaria) {
		this.cuotaTributaria = cuotaTributaria;
	}

	public String getDua() {
		return this.dua;
	}

	public void setDua(String dua) {
		this.dua = dua;
	}

	public BigDecimal getEstado620() {
		return this.estado620;
	}

	public void setEstado620(BigDecimal estado620) {
		this.estado620 = estado620;
	}

	public String getExencionIsd() {
		return this.exencionIsd;
	}

	public void setExencionIsd(String exencionIsd) {
		this.exencionIsd = exencionIsd;
	}

	public String getExentoItp() {
		return this.exentoItp;
	}

	public void setExentoItp(String exentoItp) {
		this.exentoItp = exentoItp;
	}

	public String getFactura() {
		return this.factura;
	}

	public void setFactura(String factura) {
		this.factura = factura;
	}

	public Date getFechaContrato() {
		return this.fechaContrato;
	}

	public void setFechaContrato(Date fechaContrato) {
		this.fechaContrato = fechaContrato;
	}

	public Date getFechaDevengoItp() {
		return this.fechaDevengoItp;
	}

	public void setFechaDevengoItp(Date fechaDevengoItp) {
		this.fechaDevengoItp = fechaDevengoItp;
	}

	public Date getFechaFactura() {
		return this.fechaFactura;
	}

	public void setFechaFactura(Date fechaFactura) {
		this.fechaFactura = fechaFactura;
	}

	public String getFundamentoExencion() {
		return this.fundamentoExencion;
	}

	public void setFundamentoExencion(String fundamentoExencion) {
		this.fundamentoExencion = fundamentoExencion;
	}

	public String getFundamentoNoSujetoItp() {
		return this.fundamentoNoSujetoItp;
	}

	public void setFundamentoNoSujetoItp(String fundamentoNoSujetoItp) {
		this.fundamentoNoSujetoItp = fundamentoNoSujetoItp;
	}

	public String getIdNoSujeccion06() {
		return this.idNoSujeccion06;
	}

	public void setIdNoSujeccion06(String idNoSujeccion06) {
		this.idNoSujeccion06 = idNoSujeccion06;
	}

	public String getIdProvinciaCet() {
		return this.idProvinciaCet;
	}

	public void setIdProvinciaCet(String idProvinciaCet) {
		this.idProvinciaCet = idProvinciaCet;
	}

	public String getIdReduccion05() {
		return this.idReduccion05;
	}

	public void setIdReduccion05(String idReduccion05) {
		this.idReduccion05 = idReduccion05;
	}

	public String getImprPermisoCircu() {
		return this.imprPermisoCircu;
	}

	public void setImprPermisoCircu(String imprPermisoCircu) {
		this.imprPermisoCircu = imprPermisoCircu;
	}

	public String getModeloIsd() {
		return this.modeloIsd;
	}

	public void setModeloIsd(String modeloIsd) {
		this.modeloIsd = modeloIsd;
	}

	public String getModeloItp() {
		return this.modeloItp;
	}

	public void setModeloItp(String modeloItp) {
		this.modeloItp = modeloItp;
	}

	public String getModoAdjudicacion() {
		return this.modoAdjudicacion;
	}

	public void setModoAdjudicacion(String modoAdjudicacion) {
		this.modoAdjudicacion = modoAdjudicacion;
	}

	public String getNoSujetoIsd() {
		return this.noSujetoIsd;
	}

	public void setNoSujetoIsd(String noSujetoIsd) {
		this.noSujetoIsd = noSujetoIsd;
	}

	public String getNoSujetoItp() {
		return this.noSujetoItp;
	}

	public void setNoSujetoItp(String noSujetoItp) {
		this.noSujetoItp = noSujetoItp;
	}

	public String getNumAutoIsd() {
		return this.numAutoIsd;
	}

	public void setNumAutoIsd(String numAutoIsd) {
		this.numAutoIsd = numAutoIsd;
	}

	public String getNumAutoItp() {
		return this.numAutoItp;
	}

	public void setNumAutoItp(String numAutoItp) {
		this.numAutoItp = numAutoItp;
	}

	public String getOficinaLiquidadora() {
		return this.oficinaLiquidadora;
	}

	public void setOficinaLiquidadora(String oficinaLiquidadora) {
		this.oficinaLiquidadora = oficinaLiquidadora;
	}

	public BigDecimal getPAdquisicion() {
		return this.pAdquisicion;
	}

	public void setPAdquisicion(BigDecimal pAdquisicion) {
		this.pAdquisicion = pAdquisicion;
	}

	public BigDecimal getPReduccionAnual() {
		return this.pReduccionAnual;
	}

	public void setPReduccionAnual(BigDecimal pReduccionAnual) {
		this.pReduccionAnual = pReduccionAnual;
	}

	public String getSentenciaJudicial() {
		return this.sentenciaJudicial;
	}

	public void setSentenciaJudicial(String sentenciaJudicial) {
		this.sentenciaJudicial = sentenciaJudicial;
	}

	public BigDecimal getTipoGravamen() {
		return this.tipoGravamen;
	}

	public void setTipoGravamen(BigDecimal tipoGravamen) {
		this.tipoGravamen = tipoGravamen;
	}

	public String getTipoTransferencia() {
		return this.tipoTransferencia;
	}

	public void setTipoTransferencia(String tipoTransferencia) {
		this.tipoTransferencia = tipoTransferencia;
	}

	public BigDecimal getValorDeclarado() {
		return this.valorDeclarado;
	}

	public void setValorDeclarado(BigDecimal valorDeclarado) {
		this.valorDeclarado = valorDeclarado;
	}

	public Tasa getTasa1() {
		return this.tasa1;
	}

	public void setTasa1(Tasa tasa1) {
		this.tasa1 = tasa1;
	}

	public Tasa getTasa2() {
		return this.tasa2;
	}

	public void setTasa2(Tasa tasa2) {
		this.tasa2 = tasa2;
	}

	public BigDecimal getnumtitulares() {
		return this.numtitulares;
	}

	public void setNumTitulares(BigDecimal numtitulares) {
		this.numtitulares = numtitulares;
	}

	public String getconsentimientocambio() {
		return this.consentimientocambio;
	}

	public void setConsentimientoCambio(String consentimientocambio) {
		this.consentimientocambio = consentimientocambio;
	}

	public List<TramiteTrafico> getTramiteTrafico() {
		return this.tramiteTrafico;
	}

	public void setTramiteTrafico(List<TramiteTrafico> tramiteTrafico) {
		this.tramiteTrafico = tramiteTrafico;
	}

	public String getResCheckCtit() {
		return resCheckCtit;
	}

	public void setResCheckCtit(String resCheckCtit) {
		this.resCheckCtit = resCheckCtit;
	}

	public String getCheckValorDeclarado() {
		return checkValorDeclarado;
	}

	public void setCheckValorDeclarado(String checkValorDeclarado) {
		this.checkValorDeclarado = checkValorDeclarado;
	}

}