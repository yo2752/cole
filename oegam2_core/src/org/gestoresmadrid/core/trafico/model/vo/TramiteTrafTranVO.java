package org.gestoresmadrid.core.trafico.model.vo;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SecondaryTable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.gestoresmadrid.core.tasas.model.vo.TasaVO;
import org.hibernate.annotations.FetchMode;

/**
 * The persistent class for the TRAMITE_TRAF_TRAN database table.
 */
@Entity
@SecondaryTable(name = "TRAMITE_TRAF_TRAN")
@org.hibernate.annotations.Table(appliesTo = "TRAMITE_TRAF_TRAN", fetch = FetchMode.SELECT)
@DiscriminatorValue("T7")
public class TramiteTrafTranVO extends TramiteTraficoVO {

	private static final long serialVersionUID = 1698111446435602696L;

	@Column(table="TRAMITE_TRAF_TRAN", name = "ACEPTACION_RESPONS_ITV")
	private String aceptacionResponsItv;

	@Column(table="TRAMITE_TRAF_TRAN", name = "ACREDITA_HERENCIA_DONACION")
	private String acreditaHerenciaDonacion;

	@Column(table="TRAMITE_TRAF_TRAN", name = "ACTA_SUBASTA")
	private String actaSubasta;

	@Column(table="TRAMITE_TRAF_TRAN", name = "ALTA_IVTM")
	private String altaIvtm;

	@Column(table="TRAMITE_TRAF_TRAN", name = "BASE_IMPONIBLE")
	private BigDecimal baseImponible;

	@Column(table="TRAMITE_TRAF_TRAN", name = "CAMBIO_SERVICIO")
	private String cambioServicio;

	@Column(table="TRAMITE_TRAF_TRAN", name = "CET_ITP")
	private String cetItp;

	// bi-directional many-to-one association to Tasa
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(table="TRAMITE_TRAF_TRAN", name = "CODIGO_TASA_INF")
	private TasaVO tasa1;

	// bi-directional many-to-one association to Tasa
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(table="TRAMITE_TRAF_TRAN", name = "CODIGO_TASA_CAMSER")
	private TasaVO tasa2;

	@Column(table="TRAMITE_TRAF_TRAN", name="CONSENTIMIENTO")
	private String consentimiento;

	@Column(table="TRAMITE_TRAF_TRAN", name = "CONTRATO_FACTURA")
	private String contratoFactura;

	@Column(table="TRAMITE_TRAF_TRAN", name = "CUOTA_TRIBUTARIA")
	private BigDecimal cuotaTributaria;

	@Column(table="TRAMITE_TRAF_TRAN", name = "DUA")
	private String dua;

	@Column(table="TRAMITE_TRAF_TRAN", name = "ESTADO_620")
	private BigDecimal estado620;

	@Column(table="TRAMITE_TRAF_TRAN", name = "CHECK_VALOR_DECLARADO_620")
	private String checkValorDeclarado;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(table="TRAMITE_TRAF_TRAN", name = "FECHA_GENERACION_620")
	private Date fechaGeneracion620;

	@Column(table="TRAMITE_TRAF_TRAN", name = "EXENCION_ISD")
	private String exencionIsd;

	@Column(table="TRAMITE_TRAF_TRAN", name = "EXENTO_ITP")
	private String exentoItp;

	@Column(table="TRAMITE_TRAF_TRAN", name="FACTURA")
	private String factura;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(table="TRAMITE_TRAF_TRAN", name = "FECHA_CONTRATO")
	private Date fechaContrato;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(table="TRAMITE_TRAF_TRAN", name = "FECHA_DEVENGO_ITP")
	private Date fechaDevengoItp;

	@Column(table="TRAMITE_TRAF_TRAN", name = "FECHA_FACTURA")
	private Date fechaFactura;

	@Column(table="TRAMITE_TRAF_TRAN", name = "FUNDAMENTO_EXENCION")
	private String fundamentoExencion;

	@Column(table="TRAMITE_TRAF_TRAN", name = "FUNDAMENTO_NO_SUJETO_ITP")
	private String fundamentoNoSujetoItp;

	@Column(table="TRAMITE_TRAF_TRAN", name = "ID_NO_SUJECCION_06")
	private String idNoSujeccion06;

	@Column(table="TRAMITE_TRAF_TRAN", name = "ID_PROVINCIA_CET")
	private String idProvinciaCet;

	@Column(table="TRAMITE_TRAF_TRAN", name = "ID_REDUCCION_05")
	private String idReduccion05;

	@Column(table="TRAMITE_TRAF_TRAN", name = "IMPR_PERMISO_CIRCU")
	private String imprPermisoCircu;

	@Column(table="TRAMITE_TRAF_TRAN", name = "MODELO_ISD")
	private String modeloIsd;

	@Column(table="TRAMITE_TRAF_TRAN", name = "MODELO_ITP")
	private String modeloItp;

	@Column(table="TRAMITE_TRAF_TRAN", name = "MODO_ADJUDICACION")
	private String modoAdjudicacion;

	@Column(table="TRAMITE_TRAF_TRAN", name = "NO_SUJETO_ISD")
	private String noSujetoIsd;

	@Column(table="TRAMITE_TRAF_TRAN", name = "NO_SUJETO_ITP")
	private String noSujetoItp;

	@Column(table="TRAMITE_TRAF_TRAN", name = "NUM_AUTO_ISD")
	private String numAutoIsd;

	@Column(table="TRAMITE_TRAF_TRAN", name = "NUM_AUTO_ITP")
	private String numAutoItp;

	@Column(table="TRAMITE_TRAF_TRAN", name = "OFICINA_LIQUIDADORA")
	private String oficinaLiquidadora;

	@Column(table="TRAMITE_TRAF_TRAN", name = "P_ADQUISICION")
	private BigDecimal pAdquisicion;

	@Column(table="TRAMITE_TRAF_TRAN", name = "P_REDUCCION_ANUAL")
	private BigDecimal pReduccionAnual;

	@Column(table="TRAMITE_TRAF_TRAN", name = "SENTENCIA_JUDICIAL")
	private String sentenciaJudicial;

	@Column(table="TRAMITE_TRAF_TRAN", name = "TIPO_GRAVAMEN")
	private BigDecimal tipoGravamen;

	@Column(table="TRAMITE_TRAF_TRAN", name = "TIPO_TRANSFERENCIA")
	private String tipoTransferencia;

	@Column(table="TRAMITE_TRAF_TRAN", name = "VALOR_DECLARADO")
	private BigDecimal valorDeclarado;

	@Column(table="TRAMITE_TRAF_TRAN", name = "NUM_TITULARES")
	private BigDecimal numtitulares;

	@Column(table="TRAMITE_TRAF_TRAN", name = "CONSENTIMIENTO_CAMBIO")
	private String consentimientocambio;

	@Column(table="TRAMITE_TRAF_TRAN", name = "RES_CHECK_CTIT")
	private String resCheckCtit;

	//Mantis 25415
	@Column(table="TRAMITE_TRAF_TRAN", name = "VALOR_REAL")
	private BigDecimal valorReal;

	//Cambios Modelo 620
	@Column(table="TRAMITE_TRAF_TRAN", name = "SUBASTA")
	private String subasta;

	@Column(table="TRAMITE_TRAF_TRAN", name = "TIPO_REDUCCION")
	private String tipoReduccion;

	@Column(table="TRAMITE_TRAF_TRAN", name = "REDUCCION_PORCENTAJE")
	private BigDecimal reduccionPorcentaje;

	@Column(table="TRAMITE_TRAF_TRAN", name = "REDUCCION_IMPORTE")
	private BigDecimal reduccionImporte;

	@Column(table="TRAMITE_TRAF_TRAN", name = "PROCEDENCIA_620")
	private String procedencia;

	@Column(table="TRAMITE_TRAF_TRAN", name = "TIPO_MOTOR")
	private String tipoMotor;

	// DVV
//	@Column(table="TRAMITE_TRAF_TRAN", name = "CAMBIO_SOCIETARIO")
//	private Boolean cambioSocietario = Boolean.FALSE;

//	@Column(table="TRAMITE_TRAF_TRAN", name = "MODIFICACION_NO_CONSTANTE")
//	private Boolean modificacionNoConstante = Boolean.FALSE;

   @Column(table="TRAMITE_TRAF_TRAN", name = "ACREDITACION_PAGO")
	private String acreditacionPago;
   
	@Column(table="TRAMITE_TRAF_TRAN", name = "FICHERO_SUBIDO")
	private String ficheroSubido;
	
	@Column(table="TRAMITE_TRAF_TRAN", name = "AUTORIZADO_IVTM")
	private String autorizadoIvtm;
	
	@Column(table="TRAMITE_TRAF_TRAN", name = "AUTORIZADO_HER_DONA")
	private String autorizadoHerenciaDonacion;
	
	@Column(table="TRAMITE_TRAF_TRAN", name = "AUTORIZADO_EXENTO_CTR")
	private String autorizadoExentoCtr;

	@Column(table="TRAMITE_TRAF_TRAN", name = "EXENCION_CTR")
	private String exentoCtr;
	
	//@Column(table="TRAMITE_TRAF_TRAN", name = "AUTORIZADO_SUBASTA_JUD")
	//private String autorizadoSubastaJudicial;

	public BigDecimal getReduccionPorcentaje() {
		return reduccionPorcentaje;
	}

	public void setReduccionPorcentaje(BigDecimal reduccionPorcentaje) {
		this.reduccionPorcentaje = reduccionPorcentaje;
	}

	public BigDecimal getReduccionImporte() {
		return reduccionImporte;
	}

	public void setReduccionImporte(BigDecimal reduccionImporte) {
		this.reduccionImporte = reduccionImporte;
	}

	public TramiteTrafTranVO() {}

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

	public TasaVO getTasa1() {
		return this.tasa1;
	}

	public void setTasa1(TasaVO tasa1) {
		this.tasa1 = tasa1;
	}

	public TasaVO getTasa2() {
		return this.tasa2;
	}

	public void setTasa2(TasaVO tasa2) {
		this.tasa2 = tasa2;
	}

	public BigDecimal getNumtitulares() {
		return numtitulares;
	}

	public void setNumtitulares(BigDecimal numtitulares) {
		this.numtitulares = numtitulares;
	}

	public String getConsentimientocambio() {
		return consentimientocambio;
	}

	public void setConsentimientocambio(String consentimientocambio) {
		this.consentimientocambio = consentimientocambio;
	}

	public String getResCheckCtit() {
		return resCheckCtit;
	}

	public void setResCheckCtit(String resCheckCtit) {
		this.resCheckCtit = resCheckCtit;
	}

	//Mantis 25415
	public BigDecimal getValorReal() {
		return valorReal;
	}
	//Mantis 25415
	public void setValorReal(BigDecimal valorReal) {
		this.valorReal = valorReal;
	}

	public String getSubasta() {
		return subasta;
	}

	public void setSubasta(String subasta) {
		this.subasta = subasta;
	}

	public String getCheckValorDeclarado() {
		return checkValorDeclarado;
	}

	public void setCheckValorDeclarado(String checkValorDeclarado) {
		this.checkValorDeclarado = checkValorDeclarado;
	}

	public Date getFechaGeneracion620() {
		return fechaGeneracion620;
	}

	public void setFechaGeneracion620(Date fechaGeneracion620) {
		this.fechaGeneracion620 = fechaGeneracion620;
	}

	public BigDecimal getpAdquisicion() {
		return pAdquisicion;
	}

	public void setpAdquisicion(BigDecimal pAdquisicion) {
		this.pAdquisicion = pAdquisicion;
	}

	public BigDecimal getpReduccionAnual() {
		return pReduccionAnual;
	}

	public void setpReduccionAnual(BigDecimal pReduccionAnual) {
		this.pReduccionAnual = pReduccionAnual;
	}

	public String getProcedencia() {
		return procedencia;
	}

	public void setProcedencia(String procedencia) {
		this.procedencia = procedencia;
	}

	public String getTipoReduccion() {
		return tipoReduccion;
	}

	public void setTipoReduccion(String tipoReduccion) {
		this.tipoReduccion = tipoReduccion;
	}

	public String getTipoMotor() {
		return tipoMotor;
	}

	public void setTipoMotor(String tipoMotor) {
		this.tipoMotor = tipoMotor;
	}

	public String getAcreditacionPago() {
		return acreditacionPago;
	}

	public void setAcreditacionPago(String acreditacionPago) {
		this.acreditacionPago = acreditacionPago;
	}

	public String getFicheroSubido() {
		return ficheroSubido;
	}

	public void setFicheroSubido(String ficheroSubido) {
		this.ficheroSubido = ficheroSubido;
	}

	public String getAutorizadoIvtm() {
		return autorizadoIvtm;
	}

	public void setAutorizadoIvtm(String autorizadoIvtm) {
		this.autorizadoIvtm = autorizadoIvtm;
	}

	public String getExentoCtr() {
		return exentoCtr;
	}

	public void setExentoCtr(String exentoCtr) {
		this.exentoCtr = exentoCtr;
	}

	public String getAutorizadoHerenciaDonacion() {
		return autorizadoHerenciaDonacion;
	}

	public void setAutorizadoHerenciaDonacion(String autorizadoHerenciaDonacion) {
		this.autorizadoHerenciaDonacion = autorizadoHerenciaDonacion;
	}

	public String getAutorizadoExentoCtr() {
		return autorizadoExentoCtr;
	}

	public void setAutorizadoExentoCtr(String autorizadoExentoCtr) {
		this.autorizadoExentoCtr = autorizadoExentoCtr;
	}

	/*public String getAutorizadoSubastaJudicial() {
		return autorizadoSubastaJudicial;
	}

	public void setAutorizadoSubastaJudicial(String autorizadoSubastaJudicial) {
		this.autorizadoSubastaJudicial = autorizadoSubastaJudicial;
	}*/


//	public Boolean getCambioSocietario() {
//		return cambioSocietario;
//	}

//	public void setCambioSocietario(Boolean cambioSocietario) {
//		this.cambioSocietario = cambioSocietario == null ? Boolean.FALSE : cambioSocietario;
//	}

//	public Boolean getModificacionNoConstante() {
//		return modificacionNoConstante;
//	}

//	public void setModificacionNoConstante(Boolean modificacionNoConstante) {
//		this.modificacionNoConstante = modificacionNoConstante == null ? Boolean.FALSE : modificacionNoConstante;
//	}

//	public Boolean getAcreditacionPago() {
//		return acreditacionPago;
//	}

//	public void setAcreditacionPago(Boolean acreditacionPago) {
//		this.acreditacionPago = acreditacionPago;
//	}

}