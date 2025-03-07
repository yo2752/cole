package org.gestoresmadrid.core.registradores.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;


/**
 * The persistent class for the PROPIEDAD database table.
 * 
 */
@Entity
@Table(name="PROPIEDAD")
public class PropiedadVO implements Serializable {


	private static final long serialVersionUID = 5830261661798730614L;

	@Id
	@SequenceGenerator(name = "propiedad_secuencia", sequenceName = "PROPIEDAD_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "propiedad_secuencia")
	@Column(name="ID_PROPIEDAD")
	private long idPropiedad;

	private String categoria;

	@Column(name="FEC_CREACION")
	private Timestamp fecCreacion;

	@Column(name="FEC_MODIFICACION")
	private Timestamp fecModificacion;

	@Column(name="IMP_BASE")
	private BigDecimal impBase;

	@Column(name="IMP_IMPUESTO")
	private BigDecimal impImpuesto;

	@Column(name="IMP_TOTAL")
	private BigDecimal impTotal;

	@Column(name="IMPUESTO_MATR")
	private BigDecimal impuestoMatr;

	@Column(name="UNIDAD_CUENTA")
	private String unidadCuenta = "EUR";

	private BigDecimal valor;
	
	@Column(name="NUMERO_REGISTRAL")
	private String numeroRegistral;

	//One-to-one association to Direccion
	@OneToOne
	@JoinColumn(name="ID_DIRECCION")
	private DireccionVO direccion;
	
	//One-to-one association to IntervinienteRegistro
	@OneToOne
	@JoinColumn(name="ID_PROVEEDOR")
	private IntervinienteRegistroVO intervinienteRegistro;
	
	//One-to-many association to AeronaveRegistroVO
	@OneToOne(mappedBy="propiedad")
	private AeronaveRegistroVO aeronave;

	//One-to-many association to BuqueRegistroVO
	@OneToOne(mappedBy="propiedad")
	private BuqueRegistroVO buque;

	//One-to-many association to EstablecimientoRegistroVO
	@OneToOne(mappedBy="propiedad")
	private EstablecimientoRegistroVO establecimiento;

	//One-to-many association to MaquinariaRegistroVO
	@OneToOne(mappedBy="propiedad")
	private MaquinariaRegistroVO maquinaria;

	//One-to-many association to OtrosBienesRegistroVO
	@OneToOne(mappedBy="propiedad")
	private OtrosBienesRegistroVO otrosBienes;

	//One-to-many association to PropiedadIndustrialRegistroVO
	@OneToOne(mappedBy="propiedad")
	private PropiedadIndustrialRegistroVO propiedadIndustrial;

	//One-to-many association to PropiedadIntelectualRegistroVO
	@OneToOne(mappedBy="propiedad")
	private PropiedadIntelectualRegistroVO propiedadIntelectual;

	//One-to-many association to VehiculoRegistro
	@OneToOne(mappedBy="propiedad")
	private VehiculoRegistroVO vehiculo;

	@Column(name="ID_TRAMITE_REGISTRO")
	private BigDecimal idTramiteRegistro;

	@ManyToOne
	@JoinColumn(name="ID_TRAMITE_REGISTRO", insertable=false, updatable=false)
	private TramiteRegRbmVO tramiteRegRbm;

	public PropiedadVO() {
	}

	public long getIdPropiedad() {
		return this.idPropiedad;
	}

	public void setIdPropiedad(long idPropiedad) {
		this.idPropiedad = idPropiedad;
	}

	public String getCategoria() {
		return this.categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
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

	public BigDecimal getImpBase() {
		return this.impBase;
	}

	public void setImpBase(BigDecimal impBase) {
		this.impBase = impBase;
	}

	public BigDecimal getImpImpuesto() {
		return this.impImpuesto;
	}

	public void setImpImpuesto(BigDecimal impImpuesto) {
		this.impImpuesto = impImpuesto;
	}

	public BigDecimal getImpTotal() {
		return this.impTotal;
	}

	public void setImpTotal(BigDecimal impTotal) {
		this.impTotal = impTotal;
	}

	public BigDecimal getImpuestoMatr() {
		return this.impuestoMatr;
	}

	public void setImpuestoMatr(BigDecimal impuestoMatr) {
		this.impuestoMatr = impuestoMatr;
	}

	public String getUnidadCuenta() {
		return this.unidadCuenta;
	}

	public void setUnidadCuenta(String unidadCuenta) {
		this.unidadCuenta = unidadCuenta;
	}

	public BigDecimal getValor() {
		return this.valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public DireccionVO getDireccion() {
		return this.direccion;
	}

	public void setDireccion(DireccionVO direccion) {
		this.direccion = direccion;
	}

	public IntervinienteRegistroVO getIntervinienteRegistro() {
		return this.intervinienteRegistro;
	}

	public void setIntervinienteRegistro(IntervinienteRegistroVO intervinienteRegistro) {
		this.intervinienteRegistro = intervinienteRegistro;
	}

	public BigDecimal getIdTramiteRegistro() {
		return idTramiteRegistro;
	}

	public void setIdTramiteRegistro(BigDecimal idTramiteRegistro) {
		this.idTramiteRegistro = idTramiteRegistro;
	}

	public TramiteRegRbmVO getTramiteRegRbm() {
		return tramiteRegRbm;
	}

	public void setTramiteRegRbm(TramiteRegRbmVO tramiteRegRbm) {
		this.tramiteRegRbm = tramiteRegRbm;
	}

	public AeronaveRegistroVO getAeronave() {
		return aeronave;
	}

	public void setAeronave(AeronaveRegistroVO aeronave) {
		this.aeronave = aeronave;
	}

	public BuqueRegistroVO getBuque() {
		return buque;
	}

	public void setBuque(BuqueRegistroVO buque) {
		this.buque = buque;
	}

	public MaquinariaRegistroVO getMaquinaria() {
		return maquinaria;
	}

	public void setMaquinaria(MaquinariaRegistroVO maquinaria) {
		this.maquinaria = maquinaria;
	}

	public EstablecimientoRegistroVO getEstablecimiento() {
		return establecimiento;
	}

	public void setEstablecimiento(EstablecimientoRegistroVO establecimiento) {
		this.establecimiento = establecimiento;
	}

	public PropiedadIndustrialRegistroVO getPropiedadIndustrial() {
		return propiedadIndustrial;
	}

	public void setPropiedadIndustrial(PropiedadIndustrialRegistroVO propiedadIndustrial) {
		this.propiedadIndustrial = propiedadIndustrial;
	}

	public PropiedadIntelectualRegistroVO getPropiedadIntelectual() {
		return propiedadIntelectual;
	}

	public void setPropiedadIntelectual(PropiedadIntelectualRegistroVO propiedadIntelectual) {
		this.propiedadIntelectual = propiedadIntelectual;
	}

	public OtrosBienesRegistroVO getOtrosBienes() {
		return otrosBienes;
	}

	public void setOtrosBienes(OtrosBienesRegistroVO otrosBienes) {
		this.otrosBienes = otrosBienes;
	}

	public VehiculoRegistroVO getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(VehiculoRegistroVO vehiculo) {
		this.vehiculo = vehiculo;
	}

	/**
	 * @return the numeroRegistral
	 */
	public String getNumeroRegistral() {
		return numeroRegistral;
	}

	/**
	 * @param numeroRegistral the numeroRegistral to set
	 */
	public void setNumeroRegistral(String numeroRegistral) {
		this.numeroRegistral = numeroRegistral;
	}


}