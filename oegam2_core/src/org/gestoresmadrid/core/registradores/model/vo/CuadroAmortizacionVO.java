package org.gestoresmadrid.core.registradores.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the CUADRO_AMORTIZACION database table.
 * 
 */
@Entity
@Table(name="CUADRO_AMORTIZACION")
public class CuadroAmortizacionVO implements Serializable {


	private static final long serialVersionUID = 1760336314725045083L;

	@Id
	@SequenceGenerator(name = "cuadro_amortizacion_inscrp_secuencia", sequenceName = "CUADRO_AMORTIZACION_INSCRP_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "cuadro_amortizacion_inscrp_secuencia")
	@Column(name="ID_CUADRO_AMORTIZACION")
	private long idCuadroAmortizacion;

	@Column(name="FEC_CREACION")
	private Timestamp fecCreacion;

	@Column(name="FEC_MODIFICACION")
	private Timestamp fecModificacion;

	@Temporal(TemporalType.DATE)
	@Column(name="FECHA_VENCIMIENTO")
	private Date fechaVencimiento;

	@Column(name="IMP_AMORTIZACION")
	private BigDecimal impAmortizacion;

	@Column(name="IMP_CAPITAL_AMORTIZADO")
	private BigDecimal impCapitalAmortizado;

	@Column(name="IMP_CAPITAL_PENDIENTE")
	private BigDecimal impCapitalPendiente;

	@Column(name="IMP_CARGA_FINAN")
	private BigDecimal impCargaFinan;

	@Column(name="IMP_CUOTA_NETA")
	private BigDecimal impCuotaNeta;

	@Column(name="IMP_DISTI_ENTRE_CUENTA")
	private BigDecimal impDistiEntreCuenta;

	@Column(name="IMP_IMPUESTO")
	private BigDecimal impImpuesto;

	@Column(name="IMP_INTERES_DEVENGADO")
	private BigDecimal impInteresDevengado;

	@Column(name="IMP_INTERESES_PLAZO")
	private BigDecimal impInteresesPlazo;

	@Column(name="IMP_PLAZO")
	private BigDecimal impPlazo;

	@Column(name="IMP_RECUP_COSTE")
	private BigDecimal impRecupCoste;

	@Column(name="IMP_RECUP_COSTE_PTE")
	private BigDecimal impRecupCostePte;

	@Column(name="IMP_TOTAL_CUOTA")
	private BigDecimal impTotalCuota;

	@Column(name="TIPO_PLAZO")
	private String tipoPlazo;

	@Column(name="ID_DATOS_FINANCIEROS")
	private BigDecimal idDatosFinancieros;

	@ManyToOne
	@JoinColumn(name="ID_DATOS_FINANCIEROS", insertable=false, updatable=false)
	private DatosFinancierosVO datosFinancieros;

	public CuadroAmortizacionVO() {
	}

	public long getIdCuadroAmortizacion() {
		return this.idCuadroAmortizacion;
	}

	public void setIdCuadroAmortizacion(long idCuadroAmortizacion) {
		this.idCuadroAmortizacion = idCuadroAmortizacion;
	}

	public Timestamp getFecCreacion() {
		return this.fecCreacion;
	}

	public void setFecCreacion(Timestamp fecCreacion) {
		this.fecCreacion = fecCreacion;
	}

	public Timestamp getFecModificacion() {
		return this.fecModificacion;
	}

	public void setFecModificacion(Timestamp fecModificacion) {
		this.fecModificacion = fecModificacion;
	}

	public Date getFechaVencimiento() {
		return this.fechaVencimiento;
	}

	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public BigDecimal getImpAmortizacion() {
		return this.impAmortizacion;
	}

	public void setImpAmortizacion(BigDecimal impAmortizacion) {
		this.impAmortizacion = impAmortizacion;
	}

	public BigDecimal getImpCapitalAmortizado() {
		return this.impCapitalAmortizado;
	}

	public void setImpCapitalAmortizado(BigDecimal impCapitalAmortizado) {
		this.impCapitalAmortizado = impCapitalAmortizado;
	}

	public BigDecimal getImpCapitalPendiente() {
		return this.impCapitalPendiente;
	}

	public void setImpCapitalPendiente(BigDecimal impCapitalPendiente) {
		this.impCapitalPendiente = impCapitalPendiente;
	}

	public BigDecimal getImpCargaFinan() {
		return this.impCargaFinan;
	}

	public void setImpCargaFinan(BigDecimal impCargaFinan) {
		this.impCargaFinan = impCargaFinan;
	}

	public BigDecimal getImpCuotaNeta() {
		return this.impCuotaNeta;
	}

	public void setImpCuotaNeta(BigDecimal impCuotaNeta) {
		this.impCuotaNeta = impCuotaNeta;
	}

	public BigDecimal getImpDistiEntreCuenta() {
		return this.impDistiEntreCuenta;
	}

	public void setImpDistiEntreCuenta(BigDecimal impDistiEntreCuenta) {
		this.impDistiEntreCuenta = impDistiEntreCuenta;
	}

	public BigDecimal getImpImpuesto() {
		return this.impImpuesto;
	}

	public void setImpImpuesto(BigDecimal impImpuesto) {
		this.impImpuesto = impImpuesto;
	}

	public BigDecimal getImpInteresDevengado() {
		return this.impInteresDevengado;
	}

	public void setImpInteresDevengado(BigDecimal impInteresDevengado) {
		this.impInteresDevengado = impInteresDevengado;
	}

	public BigDecimal getImpInteresesPlazo() {
		return this.impInteresesPlazo;
	}

	public void setImpInteresesPlazo(BigDecimal impInteresesPlazo) {
		this.impInteresesPlazo = impInteresesPlazo;
	}

	public BigDecimal getImpPlazo() {
		return this.impPlazo;
	}

	public void setImpPlazo(BigDecimal impPlazo) {
		this.impPlazo = impPlazo;
	}

	public BigDecimal getImpRecupCoste() {
		return this.impRecupCoste;
	}

	public void setImpRecupCoste(BigDecimal impRecupCoste) {
		this.impRecupCoste = impRecupCoste;
	}

	public BigDecimal getImpRecupCostePte() {
		return this.impRecupCostePte;
	}

	public void setImpRecupCostePte(BigDecimal impRecupCostePte) {
		this.impRecupCostePte = impRecupCostePte;
	}

	public BigDecimal getImpTotalCuota() {
		return this.impTotalCuota;
	}

	public void setImpTotalCuota(BigDecimal impTotalCuota) {
		this.impTotalCuota = impTotalCuota;
	}

	public String getTipoPlazo() {
		return this.tipoPlazo;
	}

	public void setTipoPlazo(String tipoPlazo) {
		this.tipoPlazo = tipoPlazo;
	}

	public BigDecimal getIdDatosFinancieros() {
		return idDatosFinancieros;
	}

	public void setIdDatosFinancieros(BigDecimal idDatosFinancieros) {
		this.idDatosFinancieros = idDatosFinancieros;
	}

	public DatosFinancierosVO getDatosFinancieros() {
		return datosFinancieros;
	}

	public void setDatosFinancieros(DatosFinancierosVO datosFinancieros) {
		this.datosFinancieros = datosFinancieros;
	}

}