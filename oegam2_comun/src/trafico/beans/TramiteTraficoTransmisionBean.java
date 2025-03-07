package trafico.beans;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.gestoresmadrid.oegam2comun.registradores.view.dto.FicheroInfo;

import escrituras.beans.Direccion;
import escrituras.beans.OficinaLiquidadora;
import escrituras.beans.Provincia;
import hibernate.entities.trafico.TipoReduccionBean;
import trafico.utiles.enumerados.AcreditacionTrafico;
import trafico.utiles.enumerados.Estado620;
import trafico.utiles.enumerados.ModoAdjudicacion;
import trafico.utiles.enumerados.NoSujeccionOExencion;
import trafico.utiles.enumerados.ReduccionNoSujeccionOExencion05;
import trafico.utiles.enumerados.TipoTransferencia;
import trafico.utiles.enumerados.TipoTransferenciaActual;
import utilidades.estructuras.Fecha;

// Atributos
public class TramiteTraficoTransmisionBean {
	private TramiteTraficoBean tramiteTraficoBean;
	private IntervinienteTrafico adquirienteBean;
	private IntervinienteTrafico conductorHabitualBean;
	private IntervinienteTrafico representanteAdquirienteBean;
	private IntervinienteTrafico transmitenteBean;
	private IntervinienteTrafico representanteTransmitenteBean;
	private IntervinienteTrafico primerCotitularTransmitenteBean;
	private IntervinienteTrafico representantePrimerCotitularTransmitenteBean;
	private IntervinienteTrafico segundoCotitularTransmitenteBean;
	private IntervinienteTrafico representanteSegundoCotitularTransmitenteBean;
	private IntervinienteTrafico presentadorBean;

	private IntervinienteTrafico poseedorBean;
	private IntervinienteTrafico representantePoseedorBean;
	// DRC@03-10-2012 Incidencia: 1801
	//private IntervinienteTrafico compraventaBean;
	//private IntervinienteTrafico representanteCompraventaBean;

	private IntervinienteTrafico arrendatarioBean;
	private IntervinienteTrafico representanteArrendatarioBean;
	private String electronica;
	private String cambioServicio;
	private String impresionPermiso;
	private TipoTransferencia tipoTransferencia;
	private TipoTransferenciaActual tipoTransferenciaActual;
	private ModoAdjudicacion modoAdjudicacion;
	private String consentimiento;
	private String contratoFactura;
	private String factura;
	private String actaSubasta;
	private String sentenciaJudicial;
	private AcreditacionTrafico acreditaHerenciaDonacion;
	private String tipoTasaInforme;
	private String tipoTasaCambioServicio;
	private String codigoTasaInforme;
	private String codigoTasaCambioServicio;
	private String cetItp;
	private String numAutoItp;
	private String modeloItp;
	private String exentoItp;
	private String noSujetoItp;
	private String numAutoIsd;
	private String modeloIsd;
	private String exentoIsd;
	private String noSujetoIsd;
	private Estado620 estado620;
	private OficinaLiquidadora oficinaLiquidadora620;
	private String transmitente;
	private Fecha fechaDevengoItp620;
	private String exento620;
	private String noSujeto620;
	private String fundamentoExencion620;
	private String fundamentoNoSujeccion620;
	private BigDecimal porcentajeReduccionAnual620;
	private BigDecimal valorDeclarado620;
	private BigDecimal porcentajeAdquisicion620;
	private BigDecimal baseImponible620;
	private BigDecimal tipoGravamen620;
	private BigDecimal cuotaTributaria620;
	private String direccionDistintaAdquiriente620;
	private Direccion direccionBean620;

	private TipoMotorBean tipoMotor;
	private String procedencia;
	private String subasta;

	private String altaIvtm;
	private String dua;
	private Provincia provinciaCet;
	private Provincia provinciaCem;
	private BigDecimal numTitulares;
	private String consentimientoCambio;
	//Mantis 0025415
	private BigDecimal valorReal;

	private ReduccionNoSujeccionOExencion05 idReduccion05;
	private NoSujeccionOExencion idNoSujeccion06;
	private BigDecimal idTipoCreacion;
	private BigDecimal simultanea;

	private Fecha fechaFactura;
	private Fecha fechaContrato;
	// INICIO Mantis 0024132
	private String historicoCheckImpresionPermiso;
	// FIN Mantis 0024132
	private String resCheckCtit;

	private String check620ValorManual;

	private String nre06;

	private Fecha fechaRegistroNRE;

	private String prescrita;
	private BigDecimal precioCompraventa;
	private TipoReduccionBean reduccionCodigo;
	private BigDecimal reduccionPorcentaje;
	private BigDecimal reduccionImporte;
	private String indTransmitente;
	
	private String acreditacionPago;
	
	private ArrayList<FicheroInfo> ficherosSubidos;

	//DVV
//	private Boolean cambioSocietario;
//	private Boolean modificacionNoConstante;
//	private String acreditacionPago;

	// Valor que indica si en un trámite que hay que enviar al fullCTIT,
	// que ha sido devuelto en tramitable con incidencias, hay que generar el
	// primer o el segundo xml
	// puede valer 1 o 2

	public String getIndTransmitente() {
		return indTransmitente;
	}

	public void setIndTransmitente(String indTransmitente) {
		this.indTransmitente = indTransmitente;
	}

	// CONSTRUCTORES
	public TramiteTraficoTransmisionBean() {
	}

	public TramiteTraficoTransmisionBean(boolean inicial) {
		tramiteTraficoBean = new TramiteTraficoBean(true);
		transmitenteBean = new IntervinienteTrafico(true);
		representanteTransmitenteBean = new IntervinienteTrafico(true);
		primerCotitularTransmitenteBean = new IntervinienteTrafico(true);
		representantePrimerCotitularTransmitenteBean= new IntervinienteTrafico(true);
		segundoCotitularTransmitenteBean = new IntervinienteTrafico(true);
		representanteSegundoCotitularTransmitenteBean= new IntervinienteTrafico(true);
		presentadorBean = new IntervinienteTrafico(true);
		adquirienteBean = new IntervinienteTrafico(true);
		conductorHabitualBean = new IntervinienteTrafico(true);
		representanteAdquirienteBean = new IntervinienteTrafico(true);
		poseedorBean = new IntervinienteTrafico(true);
		representantePoseedorBean = new IntervinienteTrafico(true);
		//DRC@03-10-2012 Incidencia: 1801
		//compraventaBean = new IntervinienteTrafico(true);
		//representanteCompraventaBean = new IntervinienteTrafico(true);
		arrendatarioBean = new IntervinienteTrafico(true);
		representanteArrendatarioBean = new IntervinienteTrafico(true);
		fechaDevengoItp620 = new Fecha();
		oficinaLiquidadora620 = new OficinaLiquidadora(true);
		direccionBean620 = new Direccion(true);
		provinciaCet = new Provincia();
		provinciaCem = new Provincia();
		tipoMotor = new TipoMotorBean();
		reduccionCodigo = new TipoReduccionBean();
	}

	// GETTERS & SETTERS
	public TramiteTraficoBean getTramiteTraficoBean() {
		return tramiteTraficoBean;
	}

	public String getElectronica() {
		return electronica;
	}

	public void setElectronica(String electronica) {
		this.electronica = electronica;
	}

	public void setTramiteTraficoBean(TramiteTraficoBean tramiteTraficoBean) {
		this.tramiteTraficoBean = tramiteTraficoBean;
	}

	public IntervinienteTrafico getAdquirienteBean() {
		return adquirienteBean;
	}

	public void setAdquirienteBean(IntervinienteTrafico adquirienteBean) {
		this.adquirienteBean = adquirienteBean;
	}

	public IntervinienteTrafico getConductorHabitualBean() {
		return conductorHabitualBean;
	}

	public void setConductorHabitualBean(
			IntervinienteTrafico conductorHabitualBean) {
		this.conductorHabitualBean = conductorHabitualBean;
	}

	public ReduccionNoSujeccionOExencion05 getIdReduccion05() {
		return idReduccion05;
	}

	public void setIdReduccion05(String idReduccion05) {
		this.idReduccion05 = ReduccionNoSujeccionOExencion05
				.convertir(idReduccion05);
	}

	public NoSujeccionOExencion getIdNoSujeccion06() {
		return idNoSujeccion06;
	}

	public void setIdNoSujeccion06(String idNoSujeccion) {
		this.idNoSujeccion06 = NoSujeccionOExencion.convertir(idNoSujeccion);
	}

	public IntervinienteTrafico getTransmitenteBean() {
		return transmitenteBean;
	}

	public void setTransmitenteBean(IntervinienteTrafico transmitenteBean) {
		this.transmitenteBean = transmitenteBean;
	}

	public IntervinienteTrafico getPresentadorBean() {
		return presentadorBean;
	}

	public void setPresentadorBean(IntervinienteTrafico presentadorBean) {
		this.presentadorBean = presentadorBean;
	}

	public IntervinienteTrafico getRepresentanteAdquirienteBean() {
		return representanteAdquirienteBean;
	}

	public void setRepresentanteAdquirienteBean(
			IntervinienteTrafico representanteAdquirienteBean) {
		this.representanteAdquirienteBean = representanteAdquirienteBean;
	}

	public IntervinienteTrafico getRepresentanteTransmitenteBean() {
		return representanteTransmitenteBean;
	}

	public void setRepresentanteTransmitenteBean(
			IntervinienteTrafico representanteTransmitenteBean) {
		this.representanteTransmitenteBean = representanteTransmitenteBean;
	}

	public IntervinienteTrafico getPrimerCotitularTransmitenteBean() {
		return primerCotitularTransmitenteBean;
	}

	public void setPrimerCotitularTransmitenteBean(
			IntervinienteTrafico primerCotitularTransmitenteBean) {
		this.primerCotitularTransmitenteBean = primerCotitularTransmitenteBean;
	}

	public IntervinienteTrafico getSegundoCotitularTransmitenteBean() {
		return segundoCotitularTransmitenteBean;
	}

	public void setSegundoCotitularTransmitenteBean(
			IntervinienteTrafico segundoCotitularTransmitenteBean) {
		this.segundoCotitularTransmitenteBean = segundoCotitularTransmitenteBean;
	}

	public IntervinienteTrafico getPoseedorBean() {
		return poseedorBean;
	}

	public void setPoseedorBean(IntervinienteTrafico poseedorBean) {
		this.poseedorBean = poseedorBean;
	}

	public IntervinienteTrafico getRepresentantePoseedorBean() {
		return representantePoseedorBean;
	}

	public void setRepresentantePoseedorBean(
			IntervinienteTrafico representantePoseedorBean) {
		this.representantePoseedorBean = representantePoseedorBean;
	}

	public IntervinienteTrafico getArrendatarioBean() {
		return arrendatarioBean;
	}

	public void setArrendatarioBean(IntervinienteTrafico arrendatarioBean) {
		this.arrendatarioBean = arrendatarioBean;
	}

	public IntervinienteTrafico getRepresentanteArrendatarioBean() {
		return representanteArrendatarioBean;
	}

	public void setRepresentanteArrendatarioBean(
			IntervinienteTrafico representanteArrendatarioBean) {
		this.representanteArrendatarioBean = representanteArrendatarioBean;
	}

	public String getCambioServicio() {
		return cambioServicio;
	}

	public void setCambioServicio(String cambioServicio) {
		this.cambioServicio = cambioServicio;
	}

	public String getImpresionPermiso() {
		return impresionPermiso;
	}

	public void setImpresionPermiso(String impresionPermiso) {
		this.impresionPermiso = impresionPermiso;
	}

	public TipoTransferencia getTipoTransferencia() {
		return tipoTransferencia;
	}

	public void setTipoTransferencia(String tipoTransferencia) {
		this.tipoTransferencia = TipoTransferencia.convertir(tipoTransferencia);
	}

	public TipoTransferenciaActual getTipoTransferenciaActual() {
		return tipoTransferenciaActual;
	}

	public void setTipoTransferenciaActual(String tipoTransferenciaActual) {
		this.tipoTransferenciaActual = TipoTransferenciaActual.convertir(tipoTransferenciaActual);
	}

	public ModoAdjudicacion getModoAdjudicacion() {
		return modoAdjudicacion;
	}

	public void setModoAdjudicacion(String modoAdjudicacion) {
		this.modoAdjudicacion = ModoAdjudicacion.convertir(modoAdjudicacion);
	}

	public String getConsentimiento() {
		return consentimiento;
	}

	public void setConsentimiento(String consentimiento) {
		this.consentimiento = consentimiento;
	}

	public String getContratoFactura() {
		return contratoFactura;
	}

	public void setContratoFactura(String contratoFactura) {
		this.contratoFactura = contratoFactura;
	}

	public String getFactura() {
		return factura;
	}

	public void setFactura(String factura) {
		this.factura = factura;
	}

	public String getActaSubasta() {
		return actaSubasta;
	}

	public void setActaSubasta(String actaSubasta) {
		this.actaSubasta = actaSubasta;
	}

	public String getSentenciaJudicial() {
		return sentenciaJudicial;
	}

	public void setSentenciaJudicial(String sentenciaJudicial) {
		this.sentenciaJudicial = sentenciaJudicial;
	}

	public AcreditacionTrafico getAcreditaHerenciaDonacion() {
		return acreditaHerenciaDonacion;
	}

	public void setAcreditaHerenciaDonacion(String acreditaHerenciaDonacion) {
		this.acreditaHerenciaDonacion = AcreditacionTrafico
				.convertir(acreditaHerenciaDonacion);
	}

	public String getTipoTasaInforme() {
		return tipoTasaInforme;
	}

	public void setTipoTasaInforme(String tipoTasaInforme) {
		this.tipoTasaInforme = tipoTasaInforme;
	}

	public String getTipoTasaCambioServicio() {
		return tipoTasaCambioServicio;
	}

	public void setTipoTasaCambioServicio(String tipoTasaCambioServicio) {
		this.tipoTasaCambioServicio = tipoTasaCambioServicio;
	}

	public String getCodigoTasaInforme() {
		return codigoTasaInforme;
	}

	public void setCodigoTasaInforme(String codigoTasaInforme) {
		this.codigoTasaInforme = codigoTasaInforme;
	}

	public String getCodigoTasaCambioServicio() {
		return codigoTasaCambioServicio;
	}

	public void setCodigoTasaCambioServicio(String codigoTasaCambioServicio) {
		this.codigoTasaCambioServicio = codigoTasaCambioServicio;
	}

	public String getCetItp() {
		return cetItp;
	}

	public void setCetItp(String cetItp) {
		this.cetItp = cetItp;
	}

	public String getNumAutoItp() {
		return numAutoItp;
	}

	public void setNumAutoItp(String numAutoItp) {
		this.numAutoItp = numAutoItp;
	}

	public String getModeloItp() {
		return modeloItp;
	}

	public void setModeloItp(String modeloItp) {
		this.modeloItp = modeloItp;
	}

	public String getExentoItp() {
		return exentoItp;
	}

	public void setExentoItp(String exentoItp) {
		this.exentoItp = exentoItp;
	}

	public String getNoSujetoItp() {
		return noSujetoItp;
	}

	public void setNoSujetoItp(String noSujetoItp) {
		this.noSujetoItp = noSujetoItp;
	}

	public String getNumAutoIsd() {
		return numAutoIsd;
	}

	public void setNumAutoIsd(String numAutoIsd) {
		this.numAutoIsd = numAutoIsd;
	}

	public String getModeloIsd() {
		return modeloIsd;
	}

	public void setModeloIsd(String modeloIsd) {
		this.modeloIsd = modeloIsd;
	}

	public String getExentoIsd() {
		return exentoIsd;
	}

	public void setExentoIsd(String exentoIsd) {
		this.exentoIsd = exentoIsd;
	}

	public String getNoSujetoIsd() {
		return noSujetoIsd;
	}

	public void setNoSujetoIsd(String noSujetoIsd) {
		this.noSujetoIsd = noSujetoIsd;
	}

	public Estado620 getEstado620() {
		return estado620;
	}

	public void setEstado620(String estado620) {
		this.estado620 = Estado620.convertir(estado620);
	}

	public OficinaLiquidadora getOficinaLiquidadora620() {
		return oficinaLiquidadora620;
	}

	public void setOficinaLiquidadora620(
			OficinaLiquidadora oficinaLiquidadora620) {
		this.oficinaLiquidadora620 = oficinaLiquidadora620;
	}

	public String getTransmitente() {
		return transmitente;
	}

	public void setTransmitente(String transmitente) {
		this.transmitente = transmitente;
	}

	public Fecha getFechaDevengoItp620() {
		return fechaDevengoItp620;
	}

	public void setFechaDevengoItp620(Fecha fechaDevengoItp620) {
		this.fechaDevengoItp620 = fechaDevengoItp620;
	}

	public String getExento620() {
		return exento620;
	}

	public void setExento620(String exento620) {
		this.exento620 = exento620;
	}

	public String getNoSujeto620() {
		return noSujeto620;
	}

	public void setNoSujeto620(String noSujeto620) {
		this.noSujeto620 = noSujeto620;
	}

	public String getFundamentoNoSujeccion620() {
		return fundamentoNoSujeccion620;
	}

	public void setFundamentoNoSujeccion620(String fundamentoNoSujeccion620) {
		this.fundamentoNoSujeccion620 = fundamentoNoSujeccion620;
	}

	public String getFundamentoExencion620() {
		return fundamentoExencion620;
	}

	public void setFundamentoExencion620(String fundamentoExencion620) {
		this.fundamentoExencion620 = fundamentoExencion620;
	}

	public BigDecimal getPorcentajeReduccionAnual620() {
		return porcentajeReduccionAnual620;
	}

	public void setPorcentajeReduccionAnual620(
			BigDecimal porcentajeReduccionAnual620) {
		this.porcentajeReduccionAnual620 = porcentajeReduccionAnual620;
	}

	public BigDecimal getValorDeclarado620() {
		return valorDeclarado620;
	}

	public void setValorDeclarado620(BigDecimal valorDeclarado620) {
		this.valorDeclarado620 = valorDeclarado620;
	}

	public BigDecimal getPorcentajeAdquisicion620() {
		return porcentajeAdquisicion620;
	}

	public void setPorcentajeAdquisicion620(BigDecimal porcentajeAdquisicion620) {
		this.porcentajeAdquisicion620 = porcentajeAdquisicion620;
	}

	public BigDecimal getBaseImponible620() {
		return baseImponible620;
	}

	public void setBaseImponible620(BigDecimal baseImponible620) {
		this.baseImponible620 = baseImponible620;
	}

	public BigDecimal getTipoGravamen620() {
		return tipoGravamen620;
	}

	public void setTipoGravamen620(BigDecimal tipoGravamen620) {
		this.tipoGravamen620 = tipoGravamen620;
	}

	public BigDecimal getCuotaTributaria620() {
		return cuotaTributaria620;
	}

	public void setCuotaTributaria620(BigDecimal cuotaTributaria620) {
		this.cuotaTributaria620 = cuotaTributaria620;
	}

	public String getDireccionDistintaAdquiriente620() {
		return direccionDistintaAdquiriente620;
	}

	public void setDireccionDistintaAdquiriente620(
			String direccionDistintaAdquiriente620) {
		this.direccionDistintaAdquiriente620 = direccionDistintaAdquiriente620;
	}

	public Direccion getDireccionBean620() {
		return direccionBean620;
	}

	public void setDireccionBean620(Direccion direccionBean620) {
		this.direccionBean620 = direccionBean620;
	}

	public TipoMotorBean getTipoMotor() {
		return tipoMotor;
	}

	public void setTipoMotor(TipoMotorBean tipoMotor) {
		this.tipoMotor = tipoMotor;
	}

	public String getProcedencia() {
		return procedencia;
	}
	public void setProcedencia(String procedencia) {
		this.procedencia = procedencia;
	}

	public String getSubasta() {
		return subasta;
	}

	public void setSubasta(String subasta) {
		this.subasta = subasta;
	}

	public String getAltaIvtm() {
		return altaIvtm;
	}

	public void setAltaIvtm(String altaIvtm) {
		this.altaIvtm = altaIvtm;
	}

	public String getDua() {
		return dua;
	}

	public void setDua(String dua) {
		this.dua = dua;
	}

	public Provincia getProvinciaCet() {
		return provinciaCet;
	}

	public void setProvinciaCet(Provincia provinciaCet) {
		this.provinciaCet = provinciaCet;
	}

	public Provincia getProvinciaCem() {
		return provinciaCem;
	}

	public void setProvinciaCem(Provincia provinciaCem) {
		this.provinciaCem = provinciaCem;
	}

	public BigDecimal getNumTitulares() {
		return numTitulares;
	}

	public void setNumTitulares(BigDecimal numTitulares) {
		this.numTitulares = numTitulares;
	}

	public String getConsentimientoCambio() {
		return consentimientoCambio;
	}

	public void setConsentimientoCambio(String consentimientoCambio) {
		this.consentimientoCambio = consentimientoCambio;
	}

	public BigDecimal getIdTipoCreacion() {
		return idTipoCreacion;
	}

	public void setIdTipoCreacion(BigDecimal idTipoCreacion) {
		this.idTipoCreacion = idTipoCreacion;
	}

	public BigDecimal getSimultanea() {
		return simultanea;
	}

	public void setSimultanea(BigDecimal simultanea) {
		this.simultanea = simultanea;
	}

	public Fecha getFechaFactura() {
		return fechaFactura;
	}

	public void setFechaFactura(Fecha fechaFactura) {
		this.fechaFactura = fechaFactura;
	}

	public Fecha getFechaContrato() {
		return fechaContrato;
	}

	public void setFechaContrato(Fecha fechaContrato) {
		this.fechaContrato = fechaContrato;
	}

	public IntervinienteTrafico getRepresentantePrimerCotitularTransmitenteBean() {
		return representantePrimerCotitularTransmitenteBean;
	}

	public void setRepresentantePrimerCotitularTransmitenteBean(
			IntervinienteTrafico representantePrimerCotitularTransmitenteBean) {
		this.representantePrimerCotitularTransmitenteBean = representantePrimerCotitularTransmitenteBean;
	}

	public IntervinienteTrafico getRepresentanteSegundoCotitularTransmitenteBean() {
		return representanteSegundoCotitularTransmitenteBean;
	}

	public void setRepresentanteSegundoCotitularTransmitenteBean(
			IntervinienteTrafico representanteSegundoCotitularTransmitenteBean) {
		this.representanteSegundoCotitularTransmitenteBean = representanteSegundoCotitularTransmitenteBean;
	}

	public String getResCheckCtit() {
		return resCheckCtit;
	}

	public void setResCheckCtit(String resCheckCtit) {
		this.resCheckCtit = resCheckCtit;
	}

	public String getHistoricoCheckImpresionPermiso() {
		return historicoCheckImpresionPermiso;
	}

	public void setHistoricoCheckImpresionPermiso(String historicoCheckImpresionPermiso) {
		this.historicoCheckImpresionPermiso = historicoCheckImpresionPermiso;
	}
	//Mantis 25415
	public BigDecimal getValorReal() {
		return valorReal;
	}
	//Mantis 25415
	public void setValorReal(BigDecimal valorReal) {
		this.valorReal = valorReal;
	}

	public String getCheck620ValorManual() {
		return check620ValorManual;
	}

	public void setCheck620ValorManual(String check620ValorManual) {
		this.check620ValorManual = check620ValorManual;
	}

	public String getNre06() {
		return nre06;
	}

	public void setNre06(String nre06) {
		this.nre06 = nre06;
	}

	public Fecha getFechaRegistroNRE() {
		return fechaRegistroNRE;
	}

	public void setFechaRegistroNRE(Fecha fechaRegistroNRE) {
		this.fechaRegistroNRE = fechaRegistroNRE;
	}

	public String getPrescrita() {
		return prescrita;
	}

	public void setPrescrita(String prescrita) {
		this.prescrita = prescrita;
	}

	public BigDecimal getPrecioCompraventa() {
		return precioCompraventa;
	}

	public void setPrecioCompraventa(BigDecimal precioCompraventa) {
		this.precioCompraventa = precioCompraventa;
	}

	public BigDecimal getReduccionImporte() {
		return reduccionImporte;
	}

	public void setReduccionImporte(BigDecimal reduccionImporte) {
		this.reduccionImporte = reduccionImporte;
	}

//	public String getReduccionCodigo() {
//		return reduccionCodigo;
//	}
//	public void setReduccionCodigo(String reduccionCodigo) {
//		this.reduccionCodigo = reduccionCodigo;
//	}

	public TipoReduccionBean getReduccionCodigo() {
		return reduccionCodigo;
	}

	public void setReduccionCodigo(TipoReduccionBean reduccionCodigo) {
		this.reduccionCodigo = reduccionCodigo;
	}

	public BigDecimal getReduccionPorcentaje() {
		return reduccionPorcentaje;
	}

	public void setReduccionPorcentaje(BigDecimal reduccionPorcentaje) {
		this.reduccionPorcentaje = reduccionPorcentaje;
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

//	public String getAcreditacionPago() {
//		return acreditacionPago;
//	}

//	public void setAcreditacionPago(String acreditacionPago) {
//		this.acreditacionPago = acreditacionPago;
//	}

}