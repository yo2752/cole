package org.gestoresmadrid.oegam2comun.remesa.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.oegam2comun.bien.view.dto.BienDto;
import org.gestoresmadrid.oegam2comun.bien.view.dto.BienRemesaDto;
import org.gestoresmadrid.oegam2comun.intervinienteModelos.view.dto.IntervinienteModelosDto;
import org.gestoresmadrid.oegam2comun.modelo600_601.view.dto.Modelo600_601Dto;
import org.gestoresmadrid.oegam2comun.modelos.view.dto.BonificacionDto;
import org.gestoresmadrid.oegam2comun.modelos.view.dto.ConceptoDto;
import org.gestoresmadrid.oegam2comun.modelos.view.dto.FundamentoExencionDto;
import org.gestoresmadrid.oegam2comun.modelos.view.dto.ModeloDto;
import org.gestoresmadrid.oegam2comun.modelos.view.dto.OficinaLiquidadoraDto;
import org.gestoresmadrid.oegam2comun.modelos.view.dto.TipoImpuestoDto;
import org.gestoresmadrid.oegam2comun.notario.view.dto.NotarioDto;
import org.gestoresmadrid.oegam2comun.participacion.view.dto.ParticipacionDto;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;

import utilidades.estructuras.Fecha;

public class RemesaDto implements Serializable{

	private static final long serialVersionUID = 660243578931034610L;
	
	private Long idRemesa;
	private BigDecimal numExpediente;
	private Fecha fechaAlta;
	private String estado;
	private BigDecimal importeTotal;
	private BigDecimal numBienes;
	private Fecha fechaPresentacion;
	private Boolean exento;
	private Boolean noSujeto;
	private BigDecimal numProtocolo;
	private Boolean protocoloBis;
	private String fundamentoNoSujeccion;
	private BigDecimal prescrito;
	private Boolean liquidacionComplementaria;
	private String numJustiPrimeraAutoliq;
	private Fecha fechaPrimLiquidacion;
	private BigDecimal numPrimPresentacion;
	private String descDerechoReal;
	private String duracionDerechoReal;
	private BigDecimal numAnio;
	private Fecha nacimientoUsuFructuario;
	private BigDecimal importeRenta;
	private String duracionRenta;
	private String tipoPeriodoRenta;
	private BigDecimal numPeriodoRenta;
	private Fecha fechaGeneracion;
	private String tipoBien;
	private Fecha fechaDevengo;
	private String tipoDocumento;
	private List<BienRemesaDto> listaBienesUrbanos;
	private List<BienRemesaDto> listaBienesRusticos;
	private List<BienRemesaDto> listaOtrosBienes;
	private List<IntervinienteModelosDto> listaSujetosPasivos;
	private List<IntervinienteModelosDto> listaTransmitentes;
	private List<ParticipacionDto> listaPartSujPasivo;
	private List<ParticipacionDto> listaPartTransmitente;
	private List<Modelo600_601Dto> listaModelos;
	private IntervinienteModelosDto presentador;
	private OficinaLiquidadoraDto oficinaLiquidadora;
	private NotarioDto notario;
	private ConceptoDto concepto;
	private ContratoDto contrato;
	private BonificacionDto bonificacion;
	private FundamentoExencionDto fundamentoExencion;
	private ModeloDto modelo;
	private TipoImpuestoDto tipoImpuesto;
	private String numColegiado;
	private IntervinienteModelosDto sujetoPasivo;
	private IntervinienteModelosDto transmitente;
	private BienDto bienUrbanoDto;
	private BienDto bienRusticoDto;
	private BienDto otroBienDto;
	private String referenciaPropia;
	
	public Fecha getFechaDevengo() {
		return fechaDevengo;
	}
	public void setFechaDevengo(Fecha fechaDevengo) {
		this.fechaDevengo = fechaDevengo;
	}

	public ModeloDto getModelo() {
		return modelo;
	}

	public void setModelo(ModeloDto modelo) {
		this.modelo = modelo;
	}

	public TipoImpuestoDto getTipoImpuesto() {
		return tipoImpuesto;
	}

	public void setTipoImpuesto(TipoImpuestoDto tipoImpuesto) {
		this.tipoImpuesto = tipoImpuesto;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public NotarioDto getNotario() {
		return notario;
	}

	public void setNotario(NotarioDto notario) {
		this.notario = notario;
	}


	public ConceptoDto getConcepto() {
		return concepto;
	}

	public void setConcepto(ConceptoDto concepto) {
		this.concepto = concepto;
	}

	public List<IntervinienteModelosDto> getListaSujetosPasivos() {
		return listaSujetosPasivos;
	}

	public void setListaSujetosPasivos(List<IntervinienteModelosDto> listaSujetosPasivos) {
		this.listaSujetosPasivos = listaSujetosPasivos;
	}

	public List<IntervinienteModelosDto> getListaTransmitentes() {
		return listaTransmitentes;
	}

	public void setListaTransmitentes(
			List<IntervinienteModelosDto> listaTransmitentes) {
		this.listaTransmitentes = listaTransmitentes;
	}

	public String getTipoBien() {
		return tipoBien;
	}

	public void setTipoBien(String tipoBien) {
		this.tipoBien = tipoBien;
	}

	public ContratoDto getContrato() {
		return contrato;
	}

	public void setContrato(ContratoDto contrato) {
		this.contrato = contrato;
	}

	public IntervinienteModelosDto getPresentador() {
		return presentador;
	}

	public void setPresentador(IntervinienteModelosDto presentador) {
		this.presentador = presentador;
	}

	public List<ParticipacionDto> getListaPartSujPasivo() {
		return listaPartSujPasivo;
	}

	public void setListaPartSujPasivo(List<ParticipacionDto> listaPartSujPasivo) {
		this.listaPartSujPasivo = listaPartSujPasivo;
	}

	public List<ParticipacionDto> getListaPartTransmitente() {
		return listaPartTransmitente;
	}

	public void setListaPartTransmitente(
			List<ParticipacionDto> listaPartTransmitente) {
		this.listaPartTransmitente = listaPartTransmitente;
	}

	public Long getIdRemesa() {
		return idRemesa;
	}

	public void setIdRemesa(Long idRemesa) {
		this.idRemesa = idRemesa;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public Fecha getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Fecha fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public BigDecimal getImporteTotal() {
		return importeTotal;
	}

	public void setImporteTotal(BigDecimal importeTotal) {
		this.importeTotal = importeTotal;
	}

	public BigDecimal getNumBienes() {
		return numBienes;
	}

	public void setNumBienes(BigDecimal numBienes) {
		this.numBienes = numBienes;
	}

	public Fecha getFechaPresentacion() {
		return fechaPresentacion;
	}

	public void setFechaPresentacion(Fecha fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
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

	public BigDecimal getPrescrito() {
		return prescrito;
	}

	public void setPrescrito(BigDecimal prescrito) {
		this.prescrito = prescrito;
	}

	public Boolean getLiquidacionComplementaria() {
		return liquidacionComplementaria;
	}

	public void setLiquidacionComplementaria(Boolean liquidacionComplementaria) {
		this.liquidacionComplementaria = liquidacionComplementaria;
	}

	public String getNumJustiPrimeraAutoliq() {
		return numJustiPrimeraAutoliq;
	}

	public void setNumJustiPrimeraAutoliq(String numJustiPrimeraAutoliq) {
		this.numJustiPrimeraAutoliq = numJustiPrimeraAutoliq;
	}

	public Fecha getFechaPrimLiquidacion() {
		return fechaPrimLiquidacion;
	}

	public void setFechaPrimLiquidacion(Fecha fechaPrimLiquidacion) {
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

	public Fecha getNacimientoUsuFructuario() {
		return nacimientoUsuFructuario;
	}

	public void setNacimientoUsuFructuario(Fecha nacimientoUsuFructuario) {
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
	public String getNumColegiado() {
		return numColegiado;
	}
	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
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
	public BienDto getBienUrbanoDto() {
		return bienUrbanoDto;
	}
	public void setBienUrbanoDto(BienDto bienUrbanoDto) {
		this.bienUrbanoDto = bienUrbanoDto;
	}
	public BienDto getBienRusticoDto() {
		return bienRusticoDto;
	}
	public void setBienRusticoDto(BienDto bienRusticoDto) {
		this.bienRusticoDto = bienRusticoDto;
	}
	public List<BienRemesaDto> getListaBienesUrbanos() {
		return listaBienesUrbanos;
	}
	public void setListaBienesUrbanos(List<BienRemesaDto> listaBienesUrbanos) {
		this.listaBienesUrbanos = listaBienesUrbanos;
	}
	public List<BienRemesaDto> getListaBienesRusticos() {
		return listaBienesRusticos;
	}
	public void setListaBienesRusticos(List<BienRemesaDto> listaBienesRusticos) {
		this.listaBienesRusticos = listaBienesRusticos;
	}
	public List<Modelo600_601Dto> getListaModelos() {
		return listaModelos;
	}
	public void setListaModelos(List<Modelo600_601Dto> listaModelos) {
		this.listaModelos = listaModelos;
	}
	/**
	 * @return the otroBienDto
	 */
	public BienDto getOtroBienDto() {
		return otroBienDto;
	}
	/**
	 * @param otroBienDto the otroBienDto to set
	 */
	public void setOtroBienDto(BienDto otroBienDto) {
		this.otroBienDto = otroBienDto;
	}
	public List<BienRemesaDto> getListaOtrosBienes() {
		return listaOtrosBienes;
	}
	public void setListaOtrosBienes(List<BienRemesaDto> listaOtrosBienes) {
		this.listaOtrosBienes = listaOtrosBienes;
	}
	public String getReferenciaPropia() {
		return referenciaPropia;
	}
	public void setReferenciaPropia(String referenciaPropia) {
		this.referenciaPropia = referenciaPropia;
	}
	
}
