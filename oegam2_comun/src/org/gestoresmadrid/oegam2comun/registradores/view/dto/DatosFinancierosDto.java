package org.gestoresmadrid.oegam2comun.registradores.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import utilidades.estructuras.Fecha;

public class DatosFinancierosDto implements Serializable {

	private static final long serialVersionUID = -4031321862663385783L;

	private long idDatosFinancieros;

	private String cccPago;

	private String codMunicipioPago;

	private String codProvinciaPago;

	private BigDecimal diferencialFijo;

	private String domicilioEntidadPago;

	private String entidadPago;

	private Timestamp fecCreacion;

	private Date fecRevision;
	
	private Fecha fecRevisionDatFinancieros;

	private Date fecUltVencimiento;

	private Fecha fecUltVencimientoDatFinancieros;
	
	private BigDecimal impCancelacionRdLd;

	private BigDecimal impCapitalPrestamo;

	private BigDecimal impDerechoDes;

	private BigDecimal impDesembolsoIni;

	private BigDecimal impEntregaCuenta;

	private BigDecimal impFianza;

	private BigDecimal impImpuesto;

	private BigDecimal impImpuestoMatriculacion;

	private BigDecimal impNetoMat;

	private BigDecimal impOpcionCompra;

	private BigDecimal impPrecioCompraventa;

	private BigDecimal impSeguro;

	private BigDecimal impTotalAdeudado;

	private BigDecimal impTotalArrend;

	private BigDecimal impTotalPrestamo;

	private BigDecimal imtIntereses;

	private BigDecimal interesesDemora;

	private BigDecimal numeroMeses;

	private BigDecimal periodoRevision;

	private BigDecimal porcentajeTaePago;

	private BigDecimal tasaAnualEquiv;

	private BigDecimal tasaAnualEquivFi;

	private BigDecimal tasaAnualEquivFs;

	private BigDecimal tipoDeudor;

	private BigDecimal tipoInteresNominalAnual;

	private BigDecimal tipoInteresNominalAnualFi;

	private BigDecimal tipoInteresNominalAnualFs;

	private String tipoInteresRef;

	private BigDecimal topeMaxIntNominal;

	private String unidadCuenta = "EUR";

	private List<ComisionDto> comisiones;

	private List<CuadroAmortizacionDto> cuadrosAmortizacion;
	
	private List<CuadroAmortizacionDto> cuadrosAmortizacionGE;

	private List<CuadroAmortizacionDto> cuadrosAmortizacionFI;
	
	private List<CuadroAmortizacionDto> cuadrosAmortizacionFS;

	private List<OtroImporteDto> otroImportes;

	private List<ReconocimientoDeudaDto> reconocimientoDeudas;

	public long getIdDatosFinancieros() {
		return idDatosFinancieros;
	}

	public void setIdDatosFinancieros(long idDatosFinancieros) {
		this.idDatosFinancieros = idDatosFinancieros;
	}

	public String getCccPago() {
		return cccPago;
	}

	public void setCccPago(String cccPago) {
		this.cccPago = cccPago;
	}

	public String getCodMunicipioPago() {
		return codMunicipioPago;
	}

	public void setCodMunicipioPago(String codMunicipioPago) {
		this.codMunicipioPago = codMunicipioPago;
	}

	public String getCodProvinciaPago() {
		return codProvinciaPago;
	}

	public void setCodProvinciaPago(String codProvinciaPago) {
		this.codProvinciaPago = codProvinciaPago;
	}

	public BigDecimal getDiferencialFijo() {
		return diferencialFijo;
	}

	public void setDiferencialFijo(BigDecimal diferencialFijo) {
		this.diferencialFijo = diferencialFijo;
	}

	public String getDomicilioEntidadPago() {
		return domicilioEntidadPago;
	}

	public void setDomicilioEntidadPago(String domicilioEntidadPago) {
		this.domicilioEntidadPago = domicilioEntidadPago;
	}

	public String getEntidadPago() {
		return entidadPago;
	}

	public void setEntidadPago(String entidadPago) {
		this.entidadPago = entidadPago;
	}

	public Timestamp getFecCreacion() {
		return fecCreacion;
	}

	public void setFecCreacion(Timestamp fecCreacion) {
		this.fecCreacion = fecCreacion;
	}

	public Date getFecRevision() {
		return fecRevision;
	}

	public void setFecRevision(Date fecRevision) {
		this.fecRevision = fecRevision;
	}

	public Fecha getFecRevisionDatFinancieros() {
		return fecRevisionDatFinancieros;
	}

	public void setFecRevisionDatFinancieros(Fecha fecRevisionDatFinancieros) {
		this.fecRevisionDatFinancieros = fecRevisionDatFinancieros;
	}

	public Date getFecUltVencimiento() {
		return fecUltVencimiento;
	}

	public void setFecUltVencimiento(Date fecUltVencimiento) {
		this.fecUltVencimiento = fecUltVencimiento;
	}

	public Fecha getFecUltVencimientoDatFinancieros() {
		return fecUltVencimientoDatFinancieros;
	}

	public void setFecUltVencimientoDatFinancieros(Fecha fecUltVencimientoDatFinancieros) {
		this.fecUltVencimientoDatFinancieros = fecUltVencimientoDatFinancieros;
	}

	public BigDecimal getImpCancelacionRdLd() {
		return impCancelacionRdLd;
	}

	public void setImpCancelacionRdLd(BigDecimal impCancelacionRdLd) {
		this.impCancelacionRdLd = impCancelacionRdLd;
	}

	public BigDecimal getImpCapitalPrestamo() {
		return impCapitalPrestamo;
	}

	public void setImpCapitalPrestamo(BigDecimal impCapitalPrestamo) {
		this.impCapitalPrestamo = impCapitalPrestamo;
	}

	public BigDecimal getImpDerechoDes() {
		return impDerechoDes;
	}

	public void setImpDerechoDes(BigDecimal impDerechoDes) {
		this.impDerechoDes = impDerechoDes;
	}

	public BigDecimal getImpDesembolsoIni() {
		return impDesembolsoIni;
	}

	public void setImpDesembolsoIni(BigDecimal impDesembolsoIni) {
		this.impDesembolsoIni = impDesembolsoIni;
	}

	public BigDecimal getImpEntregaCuenta() {
		return impEntregaCuenta;
	}

	public void setImpEntregaCuenta(BigDecimal impEntregaCuenta) {
		this.impEntregaCuenta = impEntregaCuenta;
	}

	public BigDecimal getImpFianza() {
		return impFianza;
	}

	public void setImpFianza(BigDecimal impFianza) {
		this.impFianza = impFianza;
	}

	public BigDecimal getImpImpuesto() {
		return impImpuesto;
	}

	public void setImpImpuesto(BigDecimal impImpuesto) {
		this.impImpuesto = impImpuesto;
	}

	public BigDecimal getImpImpuestoMatriculacion() {
		return impImpuestoMatriculacion;
	}

	public void setImpImpuestoMatriculacion(BigDecimal impImpuestoMatriculacion) {
		this.impImpuestoMatriculacion = impImpuestoMatriculacion;
	}

	public BigDecimal getImpNetoMat() {
		return impNetoMat;
	}

	public void setImpNetoMat(BigDecimal impNetoMat) {
		this.impNetoMat = impNetoMat;
	}

	public BigDecimal getImpOpcionCompra() {
		return impOpcionCompra;
	}

	public void setImpOpcionCompra(BigDecimal impOpcionCompra) {
		this.impOpcionCompra = impOpcionCompra;
	}

	public BigDecimal getImpPrecioCompraventa() {
		return impPrecioCompraventa;
	}

	public void setImpPrecioCompraventa(BigDecimal impPrecioCompraventa) {
		this.impPrecioCompraventa = impPrecioCompraventa;
	}

	public BigDecimal getImpSeguro() {
		return impSeguro;
	}

	public void setImpSeguro(BigDecimal impSeguro) {
		this.impSeguro = impSeguro;
	}

	public BigDecimal getImpTotalAdeudado() {
		return impTotalAdeudado;
	}

	public void setImpTotalAdeudado(BigDecimal impTotalAdeudado) {
		this.impTotalAdeudado = impTotalAdeudado;
	}

	public BigDecimal getImpTotalArrend() {
		return impTotalArrend;
	}

	public void setImpTotalArrend(BigDecimal impTotalArrend) {
		this.impTotalArrend = impTotalArrend;
	}

	public BigDecimal getImpTotalPrestamo() {
		return impTotalPrestamo;
	}

	public void setImpTotalPrestamo(BigDecimal impTotalPrestamo) {
		this.impTotalPrestamo = impTotalPrestamo;
	}

	public BigDecimal getImtIntereses() {
		return imtIntereses;
	}

	public void setImtIntereses(BigDecimal imtIntereses) {
		this.imtIntereses = imtIntereses;
	}

	public BigDecimal getInteresesDemora() {
		return interesesDemora;
	}

	public void setInteresesDemora(BigDecimal interesesDemora) {
		this.interesesDemora = interesesDemora;
	}

	public BigDecimal getNumeroMeses() {
		return numeroMeses;
	}

	public void setNumeroMeses(BigDecimal numeroMeses) {
		this.numeroMeses = numeroMeses;
	}

	public BigDecimal getPeriodoRevision() {
		return periodoRevision;
	}

	public void setPeriodoRevision(BigDecimal periodoRevision) {
		this.periodoRevision = periodoRevision;
	}

	public BigDecimal getPorcentajeTaePago() {
		return porcentajeTaePago;
	}

	public void setPorcentajeTaePago(BigDecimal porcentajeTaePago) {
		this.porcentajeTaePago = porcentajeTaePago;
	}

	public BigDecimal getTasaAnualEquiv() {
		return tasaAnualEquiv;
	}

	public void setTasaAnualEquiv(BigDecimal tasaAnualEquiv) {
		this.tasaAnualEquiv = tasaAnualEquiv;
	}

	public BigDecimal getTasaAnualEquivFi() {
		return tasaAnualEquivFi;
	}

	public void setTasaAnualEquivFi(BigDecimal tasaAnualEquivFi) {
		this.tasaAnualEquivFi = tasaAnualEquivFi;
	}

	public BigDecimal getTasaAnualEquivFs() {
		return tasaAnualEquivFs;
	}

	public void setTasaAnualEquivFs(BigDecimal tasaAnualEquivFs) {
		this.tasaAnualEquivFs = tasaAnualEquivFs;
	}

	public BigDecimal getTipoDeudor() {
		return tipoDeudor;
	}

	public void setTipoDeudor(BigDecimal tipoDeudor) {
		this.tipoDeudor = tipoDeudor;
	}

	public BigDecimal getTipoInteresNominalAnual() {
		return tipoInteresNominalAnual;
	}

	public void setTipoInteresNominalAnual(BigDecimal tipoInteresNominalAnual) {
		this.tipoInteresNominalAnual = tipoInteresNominalAnual;
	}

	public BigDecimal getTipoInteresNominalAnualFi() {
		return tipoInteresNominalAnualFi;
	}

	public void setTipoInteresNominalAnualFi(BigDecimal tipoInteresNominalAnualFi) {
		this.tipoInteresNominalAnualFi = tipoInteresNominalAnualFi;
	}

	public BigDecimal getTipoInteresNominalAnualFs() {
		return tipoInteresNominalAnualFs;
	}

	public void setTipoInteresNominalAnualFs(BigDecimal tipoInteresNominalAnualFs) {
		this.tipoInteresNominalAnualFs = tipoInteresNominalAnualFs;
	}

	public String getTipoInteresRef() {
		return tipoInteresRef;
	}

	public void setTipoInteresRef(String tipoInteresRef) {
		this.tipoInteresRef = tipoInteresRef;
	}

	public BigDecimal getTopeMaxIntNominal() {
		return topeMaxIntNominal;
	}

	public void setTopeMaxIntNominal(BigDecimal topeMaxIntNominal) {
		this.topeMaxIntNominal = topeMaxIntNominal;
	}

	public String getUnidadCuenta() {
		return unidadCuenta;
	}

	public void setUnidadCuenta(String unidadCuenta) {
		this.unidadCuenta = unidadCuenta;
	}

	public List<OtroImporteDto> getOtroImportes() {
		return otroImportes;
	}

	public void setOtroImportes(List<OtroImporteDto> otroImportes) {
		this.otroImportes = otroImportes;
	}

	public List<ReconocimientoDeudaDto> getReconocimientoDeudas() {
		return reconocimientoDeudas;
	}

	public void setReconocimientoDeudas(List<ReconocimientoDeudaDto> reconocimientoDeudas) {
		this.reconocimientoDeudas = reconocimientoDeudas;
	}

	public List<ComisionDto> getComisiones() {
		return comisiones;
	}

	public void setComisiones(List<ComisionDto> comisiones) {
		this.comisiones = comisiones;
	}

	public List<CuadroAmortizacionDto> getCuadrosAmortizacion() {
		return cuadrosAmortizacion;
	}

	public void setCuadrosAmortizacion(List<CuadroAmortizacionDto> cuadrosAmortizacion) {
		this.cuadrosAmortizacion = cuadrosAmortizacion;
	}

	public List<CuadroAmortizacionDto> getCuadrosAmortizacionFI() {
		return cuadrosAmortizacionFI;
	}

	public void setCuadrosAmortizacionFI(List<CuadroAmortizacionDto> cuadrosAmortizacionFI) {
		this.cuadrosAmortizacionFI = cuadrosAmortizacionFI;
	}

	public List<CuadroAmortizacionDto> getCuadrosAmortizacionFS() {
		return cuadrosAmortizacionFS;
	}

	public void setCuadrosAmortizacionFS(List<CuadroAmortizacionDto> cuadrosAmortizacionFS) {
		this.cuadrosAmortizacionFS = cuadrosAmortizacionFS;
	}

	public List<CuadroAmortizacionDto> getCuadrosAmortizacionGE() {
		return cuadrosAmortizacionGE;
	}

	public void setCuadrosAmortizacionGE(List<CuadroAmortizacionDto> cuadrosAmortizacionGE) {
		this.cuadrosAmortizacionGE = cuadrosAmortizacionGE;
	}
}
