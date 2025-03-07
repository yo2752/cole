package org.gestoresmadrid.oegam2comun.registradores.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import org.gestoresmadrid.oegamComun.direccion.view.dto.DireccionDto;

public class PropiedadDto implements Serializable {

	private static final long serialVersionUID = -6201627897540664724L;
	
	private long idPropiedad;
	private String categoria;
	private Timestamp fecCreacion;
	private Timestamp fecModificacion;
	private BigDecimal impBase;
	private BigDecimal impImpuesto;
	private BigDecimal impTotal;
	private BigDecimal impuestoMatr;
	private String unidadCuenta = "EUR";
	private BigDecimal valor;
	private String numeroRegistral;
	private DireccionDto direccion;
	private IntervinienteRegistroDto intervinienteRegistro;
	private AeronaveRegistroDto aeronave;
	private BuqueRegistroDto buque;
	private MaquinariaRegistroDto maquinaria;
	private EstablecimientoRegistroDto establecimiento;
	private PropiedadIndustrialRegistroDto propiedadIndustrial;
	private PropiedadIntelectualRegistroDto propiedadIntelectual;
	private OtrosBienesRegistroDto otrosBienes;
	private VehiculoRegistroDto vehiculo;
	private BigDecimal idTramiteRegistro;

	public long getIdPropiedad() {
		return idPropiedad;
	}

	public void setIdPropiedad(long idPropiedad) {
		this.idPropiedad = idPropiedad;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public Timestamp getFecCreacion() {
		return fecCreacion;
	}

	public void setFecCreacion(Timestamp fecCreacion) {
		this.fecCreacion = fecCreacion;
	}

	public Timestamp getFecModificacion() {
		return fecModificacion;
	}

	public void setFecModificacion(Timestamp fecModificacion) {
		this.fecModificacion = fecModificacion;
	}

	public BigDecimal getImpBase() {
		return impBase;
	}

	public void setImpBase(BigDecimal impBase) {
		this.impBase = impBase;
	}

	public BigDecimal getImpImpuesto() {
		return impImpuesto;
	}

	public void setImpImpuesto(BigDecimal impImpuesto) {
		this.impImpuesto = impImpuesto;
	}

	public BigDecimal getImpTotal() {
		return impTotal;
	}

	public void setImpTotal(BigDecimal impTotal) {
		this.impTotal = impTotal;
	}

	public BigDecimal getImpuestoMatr() {
		return impuestoMatr;
	}

	public void setImpuestoMatr(BigDecimal impuestoMatr) {
		this.impuestoMatr = impuestoMatr;
	}

	public String getUnidadCuenta() {
		return unidadCuenta;
	}

	public void setUnidadCuenta(String unidadCuenta) {
		this.unidadCuenta = unidadCuenta;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public DireccionDto getDireccion() {
		return direccion;
	}

	public void setDireccion(DireccionDto direccion) {
		this.direccion = direccion;
	}

	public IntervinienteRegistroDto getIntervinienteRegistro() {
		return intervinienteRegistro;
	}

	public void setIntervinienteRegistro(IntervinienteRegistroDto intervinienteRegistro) {
		this.intervinienteRegistro = intervinienteRegistro;
	}

	public BigDecimal getIdTramiteRegistro() {
		return idTramiteRegistro;
	}

	public void setIdTramiteRegistro(BigDecimal idTramiteRegistro) {
		this.idTramiteRegistro = idTramiteRegistro;
	}

	public AeronaveRegistroDto getAeronave() {
		return aeronave;
	}

	public void setAeronave(AeronaveRegistroDto aeronave) {
		this.aeronave = aeronave;
	}

	public BuqueRegistroDto getBuque() {
		return buque;
	}

	public void setBuque(BuqueRegistroDto buque) {
		this.buque = buque;
	}

	public MaquinariaRegistroDto getMaquinaria() {
		if(maquinaria==null){
			maquinaria = new MaquinariaRegistroDto();
		}
		return maquinaria;
	}

	public void setMaquinaria(MaquinariaRegistroDto maquinaria) {
		this.maquinaria = maquinaria;
	}

	public EstablecimientoRegistroDto getEstablecimiento() {
		if(establecimiento==null){
			establecimiento = new EstablecimientoRegistroDto();
		}
		return establecimiento;
	}

	public void setEstablecimiento(EstablecimientoRegistroDto establecimiento) {
		this.establecimiento = establecimiento;
	}

	public PropiedadIndustrialRegistroDto getPropiedadIndustrial() {
		return propiedadIndustrial;
	}

	public void setPropiedadIndustrial(PropiedadIndustrialRegistroDto propiedadIndustrial) {
		this.propiedadIndustrial = propiedadIndustrial;
	}

	public PropiedadIntelectualRegistroDto getPropiedadIntelectual() {
		return propiedadIntelectual;
	}

	public void setPropiedadIntelectual(PropiedadIntelectualRegistroDto propiedadIntelectual) {
		this.propiedadIntelectual = propiedadIntelectual;
	}

	public OtrosBienesRegistroDto getOtrosBienes() {
		if(otrosBienes==null){
			otrosBienes = new OtrosBienesRegistroDto();
		}
		return otrosBienes;
	}

	public void setOtrosBienes(OtrosBienesRegistroDto otrosBienes) {
		this.otrosBienes = otrosBienes;
	}

	public VehiculoRegistroDto getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(VehiculoRegistroDto vehiculo) {
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
