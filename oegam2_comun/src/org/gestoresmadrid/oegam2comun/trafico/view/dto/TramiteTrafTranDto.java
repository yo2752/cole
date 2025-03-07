package org.gestoresmadrid.oegam2comun.trafico.view.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import org.gestoresmadrid.oegam2comun.registradores.view.dto.FicheroInfo;
import org.gestoresmadrid.oegam2comun.vehiculo.view.dto.TipoMotorDto;
import org.gestoresmadrid.oegam2comun.vehiculo.view.dto.TipoReduccionDto;
import org.gestoresmadrid.oegamComun.interviniente.view.dto.IntervinienteTraficoDto;

public class TramiteTrafTranDto extends TramiteTrafDto {

	private static final long serialVersionUID = 2367124678948045530L;

	private String aceptacionResponsItv;

	private String acreditaHerenciaDonacion;

	private String actaSubasta;

	private String altaIvtm;

	private BigDecimal baseImponible;

	private String cambioServicio;

	private String cetItp;

	private String codigoTasaInf;
	private String codigoTasaCamser;

	private String consentimiento;

	private String contratoFactura;

	private BigDecimal cuotaTributaria;

	private String dua;

	private String electronica;

	private BigDecimal estado620;

	private String exencionIsd;
	private String exentoItp;

	private String factura;

	private Date fechaContrato;
	private Date fechaDevengoItp;
	private Date fechaFactura;

	private String fundamentoExencion;

	private String fundamentoNoSujetoItp;

	private String idNoSujeccion06;

	private String checkValorDeclarado;

	private Date fechaGeneracion620;

	private String idProvinciaCet;

	private String idReduccion05;

	private String imprPermisoCircu;

	private String modeloIsd;
	private String modeloItp;
	private String modoAdjudicacion;

	private String noSujetoIsd;
	private String noSujetoItp;

	private String numAutoIsd;
	private String numAutoItp;

	private String oficinaLiquidadora;

	private BigDecimal pAdquisicion;
	private BigDecimal pReduccionAnual;

	private String sentenciaJudicial;

	private BigDecimal tipoGravamen;

	private String tipoTransferencia;

	private BigDecimal valorDeclarado;

	private BigDecimal numtitulares;

	private String consentimientocambio;

	private String resCheckCtit;

	private IntervinienteTraficoDto adquiriente;
	private IntervinienteTraficoDto transmitente;
	private IntervinienteTraficoDto presentador;
	private IntervinienteTraficoDto representanteAdquiriente;
	private IntervinienteTraficoDto representanteTransmitente;
	private IntervinienteTraficoDto conductorHabitual;
	private IntervinienteTraficoDto representanteCompraventa;
	private IntervinienteTraficoDto arrendatario;
	private IntervinienteTraficoDto representanteArrendatario;
	private IntervinienteTraficoDto primerCotitularTransmision;
	private IntervinienteTraficoDto segundoCotitularTransmision;
	private IntervinienteTraficoDto compraVenta;
	private IntervinienteTraficoDto representantePrimerCotitularTransmision;
	private IntervinienteTraficoDto representanteSegundoCotitularTransmision;

	//Cambios Modelo 620
	private String prescrita;

	private TipoMotorDto tipoMotor;
	private String subasta;
	private TipoReduccionDto tipoReduccion;

	private BigDecimal reduccionPorcentaje;
	private BigDecimal reduccionImporte;
	private String indTransmitente;
	private String procedencia;
	
	private String acreditacionPago;
	
	private ArrayList<FicheroInfo> ficherosSubidos;
	private String extensionFicheroEnviado;
	private String ficheroSubido;
	
	private String autorizadoIvtm;
	private String autorizadoHerenciaDonacion;
	

	//DVV
//	private Boolean cambioSocietario;
//	private Boolean modificacionNoConstante;
//	private Boolean acreditacionPago;

	public TramiteTrafTranDto() {}

	public TramiteTrafTranDto(TramiteTrafDto tramite) {
		setRefPropia(tramite.getRefPropia());
		setNumExpediente(tramite.getNumExpediente());
		setVehiculoDto(tramite.getVehiculoDto());
		setJefaturaTraficoDto(tramite.getJefaturaTraficoDto());
		setNumColegiado(tramite.getNumColegiado());
	}

	public String getPrescrita() {
		return prescrita;
	}

	public void setPrescrita(String prescrita) {
		this.prescrita = prescrita;
	}

	public TipoMotorDto getTipoMotor() {
		return tipoMotor;
	}

	public void setTipoMotor(TipoMotorDto tipoMotor) {
		this.tipoMotor = tipoMotor;
	}

	public String getSubasta() {
		return subasta;
	}

	public void setSubasta(String subasta) {
		this.subasta = subasta;
	}

	public TipoReduccionDto getTipoReduccion() {
		return tipoReduccion;
	}

	public void setTipoReduccion(TipoReduccionDto reduccionCodigo) {
		this.tipoReduccion = reduccionCodigo;
	}

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

	public String getElectronica() {
		return electronica;
	}

	public void setElectronica(String electronica) {
		this.electronica = electronica;
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

	public String getCodigoTasaInf() {
		return codigoTasaInf;
	}

	public void setCodigoTasaInf(String codigoTasaInf) {
		this.codigoTasaInf = codigoTasaInf;
	}

	public String getCodigoTasaCamser() {
		return codigoTasaCamser;
	}

	public void setCodigoTasaCamser(String codigoTasaCamser) {
		this.codigoTasaCamser = codigoTasaCamser;
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

	public BigDecimal getnumtitulares() {
		return this.numtitulares;
	}

	public void setNumTitulares(BigDecimal numtitulares) {
		this.numtitulares = numtitulares;
	}

	public IntervinienteTraficoDto getAdquiriente() {
		return adquiriente;
	}

	public void setAdquiriente(IntervinienteTraficoDto adquiriente) {
		this.adquiriente = adquiriente;
	}

	public IntervinienteTraficoDto getPresentador() {
		return presentador;
	}

	public void setPresentador(IntervinienteTraficoDto presentador) {
		this.presentador = presentador;
	}

	public IntervinienteTraficoDto getTransmitente() {
		return transmitente;
	}

	public void setTransmitente(IntervinienteTraficoDto transmitente) {
		this.transmitente = transmitente;
	}

	public IntervinienteTraficoDto getRepresentanteAdquiriente() {
		return representanteAdquiriente;
	}

	public void setRepresentanteAdquiriente(IntervinienteTraficoDto representanteAdquiriente) {
		this.representanteAdquiriente = representanteAdquiriente;
	}

	public IntervinienteTraficoDto getRepresentanteTransmitente() {
		return representanteTransmitente;
	}

	public void setRepresentanteTransmitente(IntervinienteTraficoDto representanteTransmitente) {
		this.representanteTransmitente = representanteTransmitente;
	}

	public IntervinienteTraficoDto getConductorHabitual() {
		return conductorHabitual;
	}

	public void setConductorHabitual(IntervinienteTraficoDto conductorHabitual) {
		this.conductorHabitual = conductorHabitual;
	}

	public IntervinienteTraficoDto getRepresentanteCompraventa() {
		return representanteCompraventa;
	}

	public void setRepresentanteCompraventa(IntervinienteTraficoDto representanteCompraventa) {
		this.representanteCompraventa = representanteCompraventa;
	}

	public IntervinienteTraficoDto getArrendatario() {
		return arrendatario;
	}

	public void setArrendatario(IntervinienteTraficoDto arrendatario) {
		this.arrendatario = arrendatario;
	}

	public IntervinienteTraficoDto getRepresentanteArrendatario() {
		return representanteArrendatario;
	}

	public void setRepresentanteArrendatario(IntervinienteTraficoDto representanteArrendatario) {
		this.representanteArrendatario = representanteArrendatario;
	}

	public IntervinienteTraficoDto getCompraVenta() {
		return compraVenta;
	}

	public void setCompraVenta(IntervinienteTraficoDto compraVenta) {
		this.compraVenta = compraVenta;
	}

	public IntervinienteTraficoDto getPrimerCotitularTransmision() {
		return primerCotitularTransmision;
	}

	public void setPrimerCotitularTransmision(IntervinienteTraficoDto primerCotitularTransmision) {
		this.primerCotitularTransmision = primerCotitularTransmision;
	}

	public IntervinienteTraficoDto getSegundoCotitularTransmision() {
		return segundoCotitularTransmision;
	}

	public void setSegundoCotitularTransmision(IntervinienteTraficoDto segundoCotitularTransmision) {
		this.segundoCotitularTransmision = segundoCotitularTransmision;
	}

	public IntervinienteTraficoDto getRepresentantePrimerCotitularTransmision() {
		return representantePrimerCotitularTransmision;
	}

	public void setRepresentantePrimerCotitularTransmision(IntervinienteTraficoDto representantePrimerCotitularTransmision) {
		this.representantePrimerCotitularTransmision = representantePrimerCotitularTransmision;
	}

	public IntervinienteTraficoDto getRepresentanteSegundoCotitularTransmision() {
		return representanteSegundoCotitularTransmision;
	}

	public void setRepresentanteSegundoCotitularTransmision(IntervinienteTraficoDto representanteSegundoCotitularTransmision) {
		this.representanteSegundoCotitularTransmision = representanteSegundoCotitularTransmision;
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

	public Date getFechaGeneracion620() {
		return fechaGeneracion620;
	}

	public void setFechaGeneracion620(Date fechaGeneracion620) {
		this.fechaGeneracion620 = fechaGeneracion620;
	}

	public String getIndTransmitente() {
		return indTransmitente;
	}

	public void setIndTransmitente(String indTransmitente) {
		this.indTransmitente = indTransmitente;
	}

	public String getProcedencia() {
		return procedencia;
	}

	public void setProcedencia(String prodecencia) {
		this.procedencia = prodecencia;
	}

	public String getAcreditacionPago() {
		return acreditacionPago;
	}

	public void setAcreditacionPago(String acreditacionPago) {
		this.acreditacionPago = acreditacionPago;
	}

	public ArrayList<FicheroInfo> getFicherosSubidos() {
		return ficherosSubidos;
	}

	public void setFicherosSubidos(ArrayList<FicheroInfo> ficherosSubidos) {
		this.ficherosSubidos = ficherosSubidos;
	}

	public String getExtensionFicheroEnviado() {
		return extensionFicheroEnviado;
	}

	public void setExtensionFicheroEnviado(String extensionFicheroEnviado) {
		this.extensionFicheroEnviado = extensionFicheroEnviado;
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

	public String getAutorizadoHerenciaDonacion() {
		return autorizadoHerenciaDonacion;
	}

	public void setAutorizadoHerenciaDonacion(String autorizadoHerenciaDonacion) {
		this.autorizadoHerenciaDonacion = autorizadoHerenciaDonacion;
	}

//	public Boolean getCambioSocietario() {
//		return cambioSocietario;
//	}

//	public void setCambioSocietario(Boolean cambioSocietario) {
//		this.cambioSocietario = cambioSocietario;
//	}

//	public Boolean getModificacionNoConstante() {
//		return modificacionNoConstante;
//	}

//	public void setModificacionNoConstante(Boolean modificacionNoConstante) {
//		this.modificacionNoConstante = modificacionNoConstante;
//	}

//	public Boolean getAcreditacionPago() {
//		return acreditacionPago;
//	}

//	public void setAcreditacionPago(Boolean acreditacionPago) {
//		this.acreditacionPago = acreditacionPago;
//	}

}