package org.gestoresmadrid.oegam2comun.modelo600_601.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.oegam2comun.bien.view.dto.BienDto;
import org.gestoresmadrid.oegam2comun.bien.view.dto.ModeloBienDto;
import org.gestoresmadrid.oegam2comun.datosBancariosFavoritos.view.dto.DatosBancariosFavoritosDto;
import org.gestoresmadrid.oegam2comun.intervinienteModelos.view.dto.IntervinienteModelosDto;
import org.gestoresmadrid.oegam2comun.modelos.view.dto.BonificacionDto;
import org.gestoresmadrid.oegam2comun.modelos.view.dto.ConceptoDto;
import org.gestoresmadrid.oegam2comun.modelos.view.dto.FundamentoExencionDto;
import org.gestoresmadrid.oegam2comun.modelos.view.dto.ModeloDto;
import org.gestoresmadrid.oegam2comun.modelos.view.dto.OficinaLiquidadoraDto;
import org.gestoresmadrid.oegam2comun.modelos.view.dto.TipoImpuestoDto;
import org.gestoresmadrid.oegam2comun.notario.view.dto.NotarioDto;
import org.gestoresmadrid.oegam2comun.remesa.view.dto.RemesaDto;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;

import utilidades.estructuras.Fecha;

public class Modelo600_601Dto implements Serializable{

	private static final long serialVersionUID = 8193794749877716684L;

	private Long idModelo;
	private ModeloDto modelo;
	private ConceptoDto concepto;
	private Fecha fechaAlta;
	private Fecha fechaDevengo;
	private Fecha fechaPresentacion;
	private Fecha nacimientoUsufructuario;
	private Fecha fechaPrimLiquidacion;
	private Fecha fechaGeneracion;
	private TipoImpuestoDto tipoImpuesto;
	private String estado;
	private String baseLiquidable;
	private String ingresar;
	private String totalIngresar;
	private ContratoDto contrato;
	private NotarioDto notario;
	private BonificacionDto bonificacion;
	private FundamentoExencionDto fundamentoExencion;
	private BigDecimal valorDeclarado;
	private BigDecimal numProtocolo;
	private BigDecimal numAnio;
	private BigDecimal importeRenta;
	private BigDecimal destVivienda;
	private BigDecimal numeroPrimPresentacion;
	private BigDecimal numPeriodoRenta;
	private BigDecimal numExpediente;
	private String cuota;
	private String valorTipoImpuesto;
	private String idFundamentoExencion;
	private String idBonificacion;
	private String baseImponible;
	private Boolean exento;
	private Boolean noSujeto;
	private Boolean protocoloBis;
	private Boolean prescrito;
	private Boolean liquidacionComplementaria;
	private String fundamentoNoSujeccion;
	private String bonificacionCuota;
	private String numColegiado;
	private RemesaDto remesa;
	private String tipoDocumento;
	private String duracionDerechoReal;
	private String duracionRenta;
	private String tipoPeriodoRenta;
	private String descDerechoReal;
	private OficinaLiquidadoraDto oficinaLiquidadora;
	private IntervinienteModelosDto sujetoPasivo;
	private IntervinienteModelosDto transmitente;
	private IntervinienteModelosDto presentador;
	private BienDto bienUrbano;
	private BienDto bienRustico;
	private BienDto otroBien;
	private List<ResultadoModelo600601Dto> listaResultadoModelo;
	private List<ModeloBienDto> listaBienesRusticos;
	private List<ModeloBienDto> listaBienesUrbanos;
	private List<ModeloBienDto> listaOtrosBienes;
	private String tipoBien;
	private String nombreEscritura;
	private String referenciaPropia;
	private DatosBancariosFavoritosDto datosBancarios;
	private boolean liquidacionManual;
	private boolean readOnly;
	private boolean bonoMueble;
	private int numBienes;

	public Long getIdModelo() {
		return idModelo;
	}

	public void setIdModelo(Long idModelo) {
		this.idModelo = idModelo;
	}

	public ModeloDto getModelo() {
		return modelo;
	}

	public void setModelo(ModeloDto modelo) {
		this.modelo = modelo;
	}

	public ConceptoDto getConcepto() {
		return concepto;
	}

	public void setConcepto(ConceptoDto concepto) {
		this.concepto = concepto;
	}

	public Fecha getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Fecha fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public TipoImpuestoDto getTipoImpuesto() {
		return tipoImpuesto;
	}

	public void setTipoImpuesto(TipoImpuestoDto tipoImpuesto) {
		this.tipoImpuesto = tipoImpuesto;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public ContratoDto getContrato() {
		return contrato;
	}

	public void setContrato(ContratoDto contrato) {
		this.contrato = contrato;
	}

	public NotarioDto getNotario() {
		return notario;
	}

	public void setNotario(NotarioDto notario) {
		this.notario = notario;
	}

	public BonificacionDto getBonificacion() {
		return bonificacion;
	}

	public void setBonificacion(BonificacionDto bonificacion) {
		this.bonificacion = bonificacion;
	}

	public FundamentoExencionDto getFundamentoExencion() {
		return fundamentoExencion;
	}

	public void setFundamentoExencion(FundamentoExencionDto fundamentoExencion) {
		this.fundamentoExencion = fundamentoExencion;
	}

	public Fecha getFechaDevengo() {
		return fechaDevengo;
	}

	public void setFechaDevengo(Fecha fechaDevengo) {
		this.fechaDevengo = fechaDevengo;
	}

	public BigDecimal getValorDeclarado() {
		return valorDeclarado;
	}

	public void setValorDeclarado(BigDecimal valorDeclarado) {
		this.valorDeclarado = valorDeclarado;
	}

	public String getCuota() {
		return cuota;
	}

	public void setCuota(String cuota) {
		this.cuota = cuota;
	}

	public String getBaseImponible() {
		return baseImponible;
	}

	public void setBaseImponible(String baseImponible) {
		this.baseImponible = baseImponible;
	}

	public Fecha getFechaPresentacion() {
		return fechaPresentacion;
	}

	public void setFechaPresentacion(Fecha fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}

	public String getBaseLiquidable() {
		return baseLiquidable;
	}

	public void setBaseLiquidable(String baseLiquidable) {
		this.baseLiquidable = baseLiquidable;
	}

	public String getIngresar() {
		return ingresar;
	}

	public void setIngresar(String ingresar) {
		this.ingresar = ingresar;
	}

	public String getTotalIngresar() {
		return totalIngresar;
	}

	public void setTotalIngresar(String totalIngresar) {
		this.totalIngresar = totalIngresar;
	}

	public Boolean getExento() {
		return exento;
	}

	public void setExento(Boolean exento) {
		this.exento = exento;
	}

	public Boolean getNoSujeto() {
		return noSujeto;
	}

	public void setNoSujeto(Boolean noSujeto) {
		this.noSujeto = noSujeto;
	}

	public BigDecimal getNumProtocolo() {
		return numProtocolo;
	}

	public void setNumProtocolo(BigDecimal numProtocolo) {
		this.numProtocolo = numProtocolo;
	}

	public Boolean getProtocoloBis() {
		return protocoloBis;
	}

	public void setProtocoloBis(Boolean protocoloBis) {
		this.protocoloBis = protocoloBis;
	}

	public String getFundamentoNoSujeccion() {
		return fundamentoNoSujeccion;
	}

	public void setFundamentoNoSujeccion(String fundamentoNoSujeccion) {
		this.fundamentoNoSujeccion = fundamentoNoSujeccion;
	}

	public String getBonificacionCuota() {
		return bonificacionCuota;
	}

	public void setBonificacionCuota(String bonificacionCuota) {
		this.bonificacionCuota = bonificacionCuota;
	}

	public RemesaDto getRemesa() {
		return remesa;
	}

	public void setRemesa(RemesaDto remesa) {
		this.remesa = remesa;
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

	public Fecha getNacimientoUsufructuario() {
		return nacimientoUsufructuario;
	}

	public void setNacimientoUsufructuario(Fecha nacimientoUsufructuario) {
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

	public Boolean getPrescrito() {
		return prescrito;
	}

	public void setPrescrito(Boolean prescrito) {
		this.prescrito = prescrito;
	}

	public Boolean getLiquidacionComplementaria() {
		return liquidacionComplementaria;
	}

	public void setLiquidacionComplementaria(Boolean liquidacionComplementaria) {
		this.liquidacionComplementaria = liquidacionComplementaria;
	}

	public Fecha getFechaPrimLiquidacion() {
		return fechaPrimLiquidacion;
	}

	public void setFechaPrimLiquidacion(Fecha fechaPrimLiquidacion) {
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

	public Fecha getFechaGeneracion() {
		return fechaGeneracion;
	}

	public void setFechaGeneracion(Fecha fechaGeneracion) {
		this.fechaGeneracion = fechaGeneracion;
	}

	public OficinaLiquidadoraDto getOficinaLiquidadora() {
		return oficinaLiquidadora;
	}

	public void setOficinaLiquidadora(OficinaLiquidadoraDto oficinaLiquidadora) {
		this.oficinaLiquidadora = oficinaLiquidadora;
	}

	public IntervinienteModelosDto getSujetoPasivo() {
		return sujetoPasivo;
	}

	public void setSujetoPasivo(IntervinienteModelosDto sujetoPasivo) {
		this.sujetoPasivo = sujetoPasivo;
	}

	public IntervinienteModelosDto getTransmitente() {
		return transmitente;
	}

	public void setTransmitente(IntervinienteModelosDto transmitente) {
		this.transmitente = transmitente;
	}

	public IntervinienteModelosDto getPresentador() {
		return presentador;
	}

	public void setPresentador(IntervinienteModelosDto presentador) {
		this.presentador = presentador;
	}

	public BienDto getBienUrbano() {
		return bienUrbano;
	}

	public void setBienUrbano(BienDto bienUrbano) {
		this.bienUrbano = bienUrbano;
	}

	public BienDto getBienRustico() {
		return bienRustico;
	}

	public void setBienRustico(BienDto bienRustico) {
		this.bienRustico = bienRustico;
	}

	public List<ModeloBienDto> getListaBienesRusticos() {
		return listaBienesRusticos;
	}

	public void setListaBienesRusticos(List<ModeloBienDto> listaBienesRusticos) {
		this.listaBienesRusticos = listaBienesRusticos;
	}

	public List<ModeloBienDto> getListaBienesUrbanos() {
		return listaBienesUrbanos;
	}

	public void setListaBienesUrbanos(List<ModeloBienDto> listaBienesUrbanos) {
		this.listaBienesUrbanos = listaBienesUrbanos;
	}

	public String getTipoBien() {
		return tipoBien;
	}

	public void setTipoBien(String tipoBien) {
		this.tipoBien = tipoBien;
	}

	public String getValorTipoImpuesto() {
		return valorTipoImpuesto;
	}

	public void setValorTipoImpuesto(String valorTipoImpuesto) {
		this.valorTipoImpuesto = valorTipoImpuesto;
	}

	public DatosBancariosFavoritosDto getDatosBancarios() {
		return datosBancarios;
	}

	public void setDatosBancarios(DatosBancariosFavoritosDto datosBancarios) {
		this.datosBancarios = datosBancarios;
	}

	public List<ResultadoModelo600601Dto> getListaResultadoModelo() {
		return listaResultadoModelo;
	}

	public void setListaResultadoModelo(
			List<ResultadoModelo600601Dto> listaResultadoModelo) {
		this.listaResultadoModelo = listaResultadoModelo;
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

	/**
	 * @return the listaOtrosBienes
	 */
	public List<ModeloBienDto> getListaOtrosBienes() {
		return listaOtrosBienes;
	}

	/**
	 * @param listaOtrosBienes the listaOtrosBienes to set
	 */
	public void setListaOtrosBienes(List<ModeloBienDto> listaOtrosBienes) {
		this.listaOtrosBienes = listaOtrosBienes;
	}

	/**
	 * @return the otroBien
	 */
	public BienDto getOtroBien() {
		return otroBien;
	}

	/**
	 * @param otroBien the otroBien to set
	 */
	public void setOtroBien(BienDto otroBien) {
		this.otroBien = otroBien;
	}

	public int getNumBienes() {
		if(listaBienesRusticos != null && !listaBienesRusticos.isEmpty()){
			return listaBienesRusticos.size();
		} else if(listaBienesUrbanos != null && !listaBienesUrbanos.isEmpty()) {
			return listaBienesUrbanos.size();
		} else if(listaOtrosBienes != null && !listaOtrosBienes.isEmpty()) {
			return listaOtrosBienes.size();
		}
		return 0;
	}

	public void setNumBienes(int numBienes) {
		this.numBienes = numBienes;
	}

	public BigDecimal getDestVivienda() {
		return destVivienda;
	}

	public void setDestVivienda(BigDecimal destVivienda) {
		this.destVivienda = destVivienda;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	public boolean isBonoMueble() {
		return bonoMueble;
	}

	public void setBonoMueble(boolean bonoMueble) {
		this.bonoMueble = bonoMueble;
	}

	public String getIdFundamentoExencion() {
		return idFundamentoExencion;
	}

	public void setIdFundamentoExencion(String idFundamentoExencion) {
		this.idFundamentoExencion = idFundamentoExencion;
	}

	public String getIdBonificacion() {
		return idBonificacion;
	}

	public void setIdBonificacion(String idBonificacion) {
		this.idBonificacion = idBonificacion;
	}

	public String getReferenciaPropia() {
		return referenciaPropia;
	}

	public void setReferenciaPropia(String referenciaPropia) {
		this.referenciaPropia = referenciaPropia;
	}

}