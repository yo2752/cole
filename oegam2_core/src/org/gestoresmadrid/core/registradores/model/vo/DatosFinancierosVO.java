package org.gestoresmadrid.core.registradores.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the DATOS_FINANCIEROS database table.
 * 
 */
@Entity
@Table(name="DATOS_FINANCIEROS")
public class DatosFinancierosVO implements Serializable {


	private static final long serialVersionUID = -7872589116860906791L;

	@Id
	@SequenceGenerator(name = "datos_financieros_secuencia", sequenceName = "DATOS_FINANCIEROS_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "datos_financieros_secuencia")
	@Column(name="ID_DATOS_FINANCIEROS")
	private long idDatosFinancieros;

	@Column(name="CCC_PAGO")
	private String cccPago;

	@Column(name="COD_MUNICIPIO_PAGO")
	private String codMunicipioPago;

	@Column(name="COD_PROVINCIA_PAGO")
	private String codProvinciaPago;

	@Column(name="DIFERENCIAL_FIJO")
	private BigDecimal diferencialFijo;

	@Column(name="DOMICILIO_ENTIDAD_PAGO")
	private String domicilioEntidadPago;

	@Column(name="ENTIDAD_PAGO")
	private String entidadPago;

	@Column(name="FEC_CREACION")
	private Timestamp fecCreacion;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REVISION")
	private Date fecRevision;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_ULT_VENCIMIENTO")
	private Date fecUltVencimiento;

	@Column(name="IMP_CANCELACION_RD_LD")
	private BigDecimal impCancelacionRdLd;

	@Column(name="IMP_CAPITAL_PRESTAMO")
	private BigDecimal impCapitalPrestamo;

	@Column(name="IMP_DERECHO_DES")
	private BigDecimal impDerechoDes;

	@Column(name="IMP_DESEMBOLSO_INI")
	private BigDecimal impDesembolsoIni;

	@Column(name="IMP_ENTREGA_CUENTA")
	private BigDecimal impEntregaCuenta;

	@Column(name="IMP_FIANZA")
	private BigDecimal impFianza;

	@Column(name="IMP_IMPUESTO")
	private BigDecimal impImpuesto;

	@Column(name="IMP_IMPUESTO_MATRICULACION")
	private BigDecimal impImpuestoMatriculacion;

	@Column(name="IMP_NETO_MAT")
	private BigDecimal impNetoMat;

	@Column(name="IMP_OPCION_COMPRA")
	private BigDecimal impOpcionCompra;

	@Column(name="IMP_PRECIO_COMPRAVENTA")
	private BigDecimal impPrecioCompraventa;

	@Column(name="IMP_SEGURO")
	private BigDecimal impSeguro;

	@Column(name="IMP_TOTAL_ADEUDADO")
	private BigDecimal impTotalAdeudado;

	@Column(name="IMP_TOTAL_ARREND")
	private BigDecimal impTotalArrend;

	@Column(name="IMP_TOTAL_PRESTAMO")
	private BigDecimal impTotalPrestamo;

	@Column(name="IMT_INTERESES")
	private BigDecimal imtIntereses;

	@Column(name="INTERESES_DEMORA")
	private BigDecimal interesesDemora;

	@Column(name="NUMERO_MESES")
	private BigDecimal numeroMeses;

	@Column(name="PERIODO_REVISION")
	private BigDecimal periodoRevision;

	@Column(name="PORCENTAJE_TAE_PAGO")
	private BigDecimal porcentajeTaePago;

	@Column(name="TASA_ANUAL_EQUIV")
	private BigDecimal tasaAnualEquiv;

	@Column(name="TASA_ANUAL_EQUIV_FI")
	private BigDecimal tasaAnualEquivFi;

	@Column(name="TASA_ANUAL_EQUIV_FS")
	private BigDecimal tasaAnualEquivFs;

	@Column(name="TIPO_DEUDOR")
	private BigDecimal tipoDeudor;

	@Column(name="TIPO_INTERES_NOMINAL_ANUAL")
	private BigDecimal tipoInteresNominalAnual;

	@Column(name="TIPO_INTERES_NOMINAL_ANUAL_FI")
	private BigDecimal tipoInteresNominalAnualFi;

	@Column(name="TIPO_INTERES_NOMINAL_ANUAL_FS")
	private BigDecimal tipoInteresNominalAnualFs;

	@Column(name="TIPO_INTERES_REF")
	private String tipoInteresRef;

	@Column(name="TOPE_MAX_INT_NOMINAL")
	private BigDecimal topeMaxIntNominal;

	@Column(name="UNIDAD_CUENTA")
	private String unidadCuenta = "EUR";
	
	//bi-directional One-to-many association to Comision
	@OneToMany(mappedBy="datosFinancieros")
	private Set<ComisionVO> comisiones;
	
	//bi-directional One-to-many association to CuadroAmortizacion
	@OneToMany(mappedBy="datosFinancieros")
	private Set<CuadroAmortizacionVO> cuadrosAmortizacion;
	
	//bi-directional One-to-many association to OtroImportes
	@OneToMany(mappedBy="datosFinancieros")
	private Set<OtroImporteVO> otroImportes;
	
	//bi-directional One-to-many association to ReconocimientoDeudas
	@OneToMany(mappedBy="datosFinancieros")
	private Set<ReconocimientoDeudaVO> reconocimientoDeudas;
	
	public DatosFinancierosVO() {
	}

	public long getIdDatosFinancieros() {
		return this.idDatosFinancieros;
	}

	public void setIdDatosFinancieros(long idDatosFinancieros) {
		this.idDatosFinancieros = idDatosFinancieros;
	}

	public String getCccPago() {
		return this.cccPago;
	}

	public void setCccPago(String cccPago) {
		this.cccPago = cccPago;
	}

	public String getCodMunicipioPago() {
		return this.codMunicipioPago;
	}

	public void setCodMunicipioPago(String codMunicipioPago) {
		this.codMunicipioPago = codMunicipioPago;
	}

	public String getCodProvinciaPago() {
		return this.codProvinciaPago;
	}

	public void setCodProvinciaPago(String codProvinciaPago) {
		this.codProvinciaPago = codProvinciaPago;
	}

	public BigDecimal getDiferencialFijo() {
		return this.diferencialFijo;
	}

	public void setDiferencialFijo(BigDecimal diferencialFijo) {
		this.diferencialFijo = diferencialFijo;
	}

	public String getDomicilioEntidadPago() {
		return this.domicilioEntidadPago;
	}

	public void setDomicilioEntidadPago(String domicilioEntidadPago) {
		this.domicilioEntidadPago = domicilioEntidadPago;
	}

	public String getEntidadPago() {
		return this.entidadPago;
	}

	public void setEntidadPago(String entidadPago) {
		this.entidadPago = entidadPago;
	}

	public Timestamp getFecCreacion() {
		return this.fecCreacion;
	}

	public void setFecCreacion(Timestamp fecCreacion) {
		this.fecCreacion = fecCreacion;
	}

	public Date getFecRevision() {
		return this.fecRevision;
	}

	public void setFecRevision(Date fecRevision) {
		this.fecRevision = fecRevision;
	}

	public Date getFecUltVencimiento() {
		return this.fecUltVencimiento;
	}

	public void setFecUltVencimiento(Date fecUltVencimiento) {
		this.fecUltVencimiento = fecUltVencimiento;
	}

	public BigDecimal getImpCancelacionRdLd() {
		return this.impCancelacionRdLd;
	}

	public void setImpCancelacionRdLd(BigDecimal impCancelacionRdLd) {
		this.impCancelacionRdLd = impCancelacionRdLd;
	}

	public BigDecimal getImpCapitalPrestamo() {
		return this.impCapitalPrestamo;
	}

	public void setImpCapitalPrestamo(BigDecimal impCapitalPrestamo) {
		this.impCapitalPrestamo = impCapitalPrestamo;
	}

	public BigDecimal getImpDerechoDes() {
		return this.impDerechoDes;
	}

	public void setImpDerechoDes(BigDecimal impDerechoDes) {
		this.impDerechoDes = impDerechoDes;
	}

	public BigDecimal getImpDesembolsoIni() {
		return this.impDesembolsoIni;
	}

	public void setImpDesembolsoIni(BigDecimal impDesembolsoIni) {
		this.impDesembolsoIni = impDesembolsoIni;
	}

	public BigDecimal getImpEntregaCuenta() {
		return this.impEntregaCuenta;
	}

	public void setImpEntregaCuenta(BigDecimal impEntregaCuenta) {
		this.impEntregaCuenta = impEntregaCuenta;
	}

	public BigDecimal getImpFianza() {
		return this.impFianza;
	}

	public void setImpFianza(BigDecimal impFianza) {
		this.impFianza = impFianza;
	}

	public BigDecimal getImpImpuesto() {
		return this.impImpuesto;
	}

	public void setImpImpuesto(BigDecimal impImpuesto) {
		this.impImpuesto = impImpuesto;
	}

	public BigDecimal getImpImpuestoMatriculacion() {
		return this.impImpuestoMatriculacion;
	}

	public void setImpImpuestoMatriculacion(BigDecimal impImpuestoMatriculacion) {
		this.impImpuestoMatriculacion = impImpuestoMatriculacion;
	}

	public BigDecimal getImpNetoMat() {
		return this.impNetoMat;
	}

	public void setImpNetoMat(BigDecimal impNetoMat) {
		this.impNetoMat = impNetoMat;
	}

	public BigDecimal getImpOpcionCompra() {
		return this.impOpcionCompra;
	}

	public void setImpOpcionCompra(BigDecimal impOpcionCompra) {
		this.impOpcionCompra = impOpcionCompra;
	}

	public BigDecimal getImpPrecioCompraventa() {
		return this.impPrecioCompraventa;
	}

	public void setImpPrecioCompraventa(BigDecimal impPrecioCompraventa) {
		this.impPrecioCompraventa = impPrecioCompraventa;
	}

	public BigDecimal getImpSeguro() {
		return this.impSeguro;
	}

	public void setImpSeguro(BigDecimal impSeguro) {
		this.impSeguro = impSeguro;
	}

	public BigDecimal getImpTotalAdeudado() {
		return this.impTotalAdeudado;
	}

	public void setImpTotalAdeudado(BigDecimal impTotalAdeudado) {
		this.impTotalAdeudado = impTotalAdeudado;
	}

	public BigDecimal getImpTotalArrend() {
		return this.impTotalArrend;
	}

	public void setImpTotalArrend(BigDecimal impTotalArrend) {
		this.impTotalArrend = impTotalArrend;
	}

	public BigDecimal getImpTotalPrestamo() {
		return this.impTotalPrestamo;
	}

	public void setImpTotalPrestamo(BigDecimal impTotalPrestamo) {
		this.impTotalPrestamo = impTotalPrestamo;
	}

	public BigDecimal getImtIntereses() {
		return this.imtIntereses;
	}

	public void setImtIntereses(BigDecimal imtIntereses) {
		this.imtIntereses = imtIntereses;
	}

	public BigDecimal getInteresesDemora() {
		return this.interesesDemora;
	}

	public void setInteresesDemora(BigDecimal interesesDemora) {
		this.interesesDemora = interesesDemora;
	}

	public BigDecimal getNumeroMeses() {
		return this.numeroMeses;
	}

	public void setNumeroMeses(BigDecimal numeroMeses) {
		this.numeroMeses = numeroMeses;
	}

	public BigDecimal getPeriodoRevision() {
		return this.periodoRevision;
	}

	public void setPeriodoRevision(BigDecimal periodoRevision) {
		this.periodoRevision = periodoRevision;
	}

	public BigDecimal getPorcentajeTaePago() {
		return this.porcentajeTaePago;
	}

	public void setPorcentajeTaePago(BigDecimal porcentajeTaePago) {
		this.porcentajeTaePago = porcentajeTaePago;
	}

	public BigDecimal getTasaAnualEquiv() {
		return this.tasaAnualEquiv;
	}

	public void setTasaAnualEquiv(BigDecimal tasaAnualEquiv) {
		this.tasaAnualEquiv = tasaAnualEquiv;
	}

	public BigDecimal getTasaAnualEquivFi() {
		return this.tasaAnualEquivFi;
	}

	public void setTasaAnualEquivFi(BigDecimal tasaAnualEquivFi) {
		this.tasaAnualEquivFi = tasaAnualEquivFi;
	}

	public BigDecimal getTasaAnualEquivFs() {
		return this.tasaAnualEquivFs;
	}

	public void setTasaAnualEquivFs(BigDecimal tasaAnualEquivFs) {
		this.tasaAnualEquivFs = tasaAnualEquivFs;
	}

	public BigDecimal getTipoDeudor() {
		return this.tipoDeudor;
	}

	public void setTipoDeudor(BigDecimal tipoDeudor) {
		this.tipoDeudor = tipoDeudor;
	}

	public BigDecimal getTipoInteresNominalAnual() {
		return this.tipoInteresNominalAnual;
	}

	public void setTipoInteresNominalAnual(BigDecimal tipoInteresNominalAnual) {
		this.tipoInteresNominalAnual = tipoInteresNominalAnual;
	}

	public BigDecimal getTipoInteresNominalAnualFi() {
		return this.tipoInteresNominalAnualFi;
	}

	public void setTipoInteresNominalAnualFi(BigDecimal tipoInteresNominalAnualFi) {
		this.tipoInteresNominalAnualFi = tipoInteresNominalAnualFi;
	}

	public BigDecimal getTipoInteresNominalAnualFs() {
		return this.tipoInteresNominalAnualFs;
	}

	public void setTipoInteresNominalAnualFs(BigDecimal tipoInteresNominalAnualFs) {
		this.tipoInteresNominalAnualFs = tipoInteresNominalAnualFs;
	}

	public String getTipoInteresRef() {
		return this.tipoInteresRef;
	}

	public void setTipoInteresRef(String tipoInteresRef) {
		this.tipoInteresRef = tipoInteresRef;
	}

	public BigDecimal getTopeMaxIntNominal() {
		return this.topeMaxIntNominal;
	}

	public void setTopeMaxIntNominal(BigDecimal topeMaxIntNominal) {
		this.topeMaxIntNominal = topeMaxIntNominal;
	}

	public String getUnidadCuenta() {
		return this.unidadCuenta;
	}

	public void setUnidadCuenta(String unidadCuenta) {
		this.unidadCuenta = unidadCuenta;
	}

	public Set<ComisionVO> getComisiones() {
		return comisiones;
	}

	public void setComisiones(Set<ComisionVO> comisiones) {
		this.comisiones = comisiones;
	}

	public Set<CuadroAmortizacionVO> getCuadrosAmortizacion() {
		return cuadrosAmortizacion;
	}

	public void setCuadrosAmortizacion(Set<CuadroAmortizacionVO> cuadrosAmortizacion) {
		this.cuadrosAmortizacion = cuadrosAmortizacion;
	}

	public Set<OtroImporteVO> getOtroImportes() {
		return otroImportes;
	}

	public void setOtroImportes(Set<OtroImporteVO> otroImportes) {
		this.otroImportes = otroImportes;
	}

	public Set<ReconocimientoDeudaVO> getReconocimientoDeudas() {
		return reconocimientoDeudas;
	}

	public void setReconocimientoDeudas(Set<ReconocimientoDeudaVO> reconocimientoDeudas) {
		this.reconocimientoDeudas = reconocimientoDeudas;
	}

}