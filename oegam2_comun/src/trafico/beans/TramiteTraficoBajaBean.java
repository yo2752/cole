package trafico.beans;

import java.math.BigDecimal;

import org.gestoresmadrid.core.model.enumerados.MotivoBaja;

import utilidades.estructuras.Fecha;

public class TramiteTraficoBajaBean { 

	//atributos
	private TramiteTraficoBean tramiteTrafico; 
	private IntervinienteTrafico titular;
	private IntervinienteTrafico representanteTitular;
	private IntervinienteTrafico adquiriente;
	private IntervinienteTrafico representanteAdquiriente;
	private Fecha fechaNacimientoAdquiriente; //Porque la fecha en Persona es TimeStamp, aquí necesito Fecha
	private Fecha fechaNacimientoTitular;
	//private VehiculoBean vehiculo;
	private String datosTarjetaITV;
	//private Tasa tasa;
	private Fecha fechaPresentacion;
	//private JefaturaTrafico jefaturaTrafico;
	private MotivoBaja motivoBaja;
	private Boolean permisoCirculacion;
	private Boolean tarjetaInspeccion;
	private Boolean bajaImpuestoMunicipal;
	private Boolean justificanteDenuncia;
	private Boolean propiedadDesguace;
	private Boolean pagoImpuestoMunicipal;
	private Boolean declaracionNOResiduo;
	private Boolean imprPermisoCircu;
	private String tasaDuplicado;
	private String dniCotitulares;
	private BigDecimal idTipoCreacion;	
	private BigDecimal simultanea;
	private Boolean declaracionNoEntregaCatV;
	private Boolean cartaDGTVehiculoMasDiezAnios;
	private Boolean certificadoMedioAmbiental;
	private String resCheckBtv;
	private String consentimientoCambio;
	
	private String cet;
		
	public Boolean getCertificadoMedioAmbiental() {
		return certificadoMedioAmbiental;
	}

	public void setCertificadoMedioAmbiental(Boolean certificadoMedioAmbiental) {
		this.certificadoMedioAmbiental = certificadoMedioAmbiental;
	}

	
	public Boolean getDeclaracionNoEntregaCatV() {
		return declaracionNoEntregaCatV;
	}

	public void setDeclaracionNoEntregaCatV(Boolean declaracionNoEntregaCatV) {
		this.declaracionNoEntregaCatV = declaracionNoEntregaCatV;
	}

	public Boolean getCartaDGTVehiculoMasDiezAnios() {
		return cartaDGTVehiculoMasDiezAnios;
	}

	public void setCartaDGTVehiculoMasDiezAnios(Boolean cartaDGTVehiculoMasDiezAnios) {
		this.cartaDGTVehiculoMasDiezAnios = cartaDGTVehiculoMasDiezAnios;
	}

	
	
	public String getTasaDuplicado() {
		return tasaDuplicado;
	}

	public void setTasaDuplicado(String tasaDuplicado) {
		this.tasaDuplicado = tasaDuplicado;
	}

	///////////////////////// CONSTRUCTORS
	public TramiteTraficoBajaBean() {
	
	}
	
	public TramiteTraficoBajaBean(boolean inicial) {
		tramiteTrafico= new TramiteTraficoBean(true);
		titular= new IntervinienteTrafico(true);
		representanteTitular= new IntervinienteTrafico(true);
		adquiriente= new IntervinienteTrafico(true);
		representanteAdquiriente = new IntervinienteTrafico(true);
//		vehiculo = new VehiculoBean(true);
//		tasa = new Tasa();
		fechaNacimientoAdquiriente = new Fecha();
		fechaNacimientoTitular = new Fecha();
		fechaPresentacion = new Fecha();
//		jefaturaTrafico = new JefaturaTrafico(true);
	}
	
	
	/////////////// GETTERS & SETTERS
	public TramiteTraficoBean getTramiteTrafico() {
		return tramiteTrafico;
	}
	public void setTramiteTrafico(TramiteTraficoBean tramiteTrafico) {
		this.tramiteTrafico = tramiteTrafico;
	}
	
	public Boolean getImprPermisoCircu() {
		return imprPermisoCircu;
	}

	public void setImprPermisoCircu(Boolean imprPermisoCircu) {
		this.imprPermisoCircu = imprPermisoCircu;
	}

	
	public IntervinienteTrafico getTitular() {
		return titular;
	}
	public void setTitular(IntervinienteTrafico titular) {
		this.titular = titular;
	}
	public Fecha getFechaNacimientoTitular() {
		return fechaNacimientoTitular;
	}
	public void setFechaNacimientoTitular(Fecha fechaNacimientoTitular) {
		this.fechaNacimientoTitular = fechaNacimientoTitular;
	}
	public IntervinienteTrafico getRepresentanteTitular() {
		return representanteTitular;
	}
	public void setRepresentanteTitular(IntervinienteTrafico representante) {
		this.representanteTitular = representante;
	}
	public IntervinienteTrafico getRepresentanteAdquiriente() {
		return representanteAdquiriente;
	}
	public void setRepresentanteAdquiriente(IntervinienteTrafico representante) {
		this.representanteAdquiriente = representante;
	}
	public IntervinienteTrafico getAdquiriente() {
		return adquiriente;
	}
	public void setAdquiriente(IntervinienteTrafico adquiriente) {
		this.adquiriente = adquiriente;
	}
	public Fecha getFechaNacimientoAdquiriente() {
		return fechaNacimientoAdquiriente;
	}
	public void setFechaNacimientoAdquiriente(Fecha fechaNacimientoAdquiriente) {
		this.fechaNacimientoAdquiriente = fechaNacimientoAdquiriente;
	}
//	public VehiculoBean getVehiculo() {
//		return vehiculo;
//	}
//	public void setVehiculo(VehiculoBean vehiculo) {
//		this.vehiculo = vehiculo;
//	}
	public String getDatosTarjetaITV() {
		return datosTarjetaITV;
	}
	public void setDatosTarjetaITV(String datosTarjetaITV) {
		this.datosTarjetaITV = datosTarjetaITV;
	}
//	public Tasa getTasa() {
//		return tasa;
//	}
//	public void setTasa(Tasa tasa) {
//		this.tasa = tasa;
//	}
	public Fecha getFechaPresentacion() {
		return fechaPresentacion;
	}
	public void setFechaPresentacion(Fecha fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}
//	public JefaturaTrafico getJefaturaTrafico() {
//		return jefaturaTrafico;
//	}
//	public void setJefaturaTrafico(JefaturaTrafico jefaturaTrafico) {
//		this.jefaturaTrafico = jefaturaTrafico;
//	}
	
	public String getDniCotitulares() {
		return dniCotitulares;
	}

	public void setDniCotitulares(String dniCotitulares) {
		this.dniCotitulares = dniCotitulares;
	}
	
	public MotivoBaja getMotivoBaja() {
		return motivoBaja;
	}
	public void setMotivoBaja(String motivoBaja) {
		this.motivoBaja = MotivoBaja.convertir(motivoBaja);
	}
	public Boolean getPermisoCirculacion() {
		return permisoCirculacion;
	}
	public void setPermisoCirculacion(Boolean permisoCirculacion) {
		this.permisoCirculacion = permisoCirculacion;
	}
	public Boolean getTarjetaInspeccion() {
		return tarjetaInspeccion;
	}
	public void setTarjetaInspeccion(Boolean tarjetaInspeccion) {
		this.tarjetaInspeccion = tarjetaInspeccion;
	}
	
	public Boolean getDeclaracionNOResiduo() {
		return declaracionNOResiduo;
	}
	public void setDeclaracionNOResiduo(Boolean declaracionNOResiduo) {
		this.declaracionNOResiduo = declaracionNOResiduo;
	}
	
	public Boolean getBajaImpuestoMunicipal() {
		return bajaImpuestoMunicipal;
	}
	public void setBajaImpuestoMunicipal(Boolean bajaImpuestoMunicipal) {
		this.bajaImpuestoMunicipal = bajaImpuestoMunicipal;
	}
	public Boolean getJustificanteDenuncia() {
		return justificanteDenuncia;
	}
	public void setJustificanteDenuncia(Boolean justificanteDenuncia) {
		this.justificanteDenuncia = justificanteDenuncia;
	}
	public Boolean getPropiedadDesguace() {
		return propiedadDesguace;
	}
	public void setPropiedadDesguace(Boolean propiedadDesguace) {
		this.propiedadDesguace = propiedadDesguace;
	}
	public Boolean getPagoImpuestoMunicipal() {
		return pagoImpuestoMunicipal;
	}
	public void setPagoImpuestoMunicipal(Boolean pagoImpuestoMunicipal) {
		this.pagoImpuestoMunicipal = pagoImpuestoMunicipal;
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

	public String getConsentimientoCambio() {
		return consentimientoCambio;
	}

	public void setConsentimientoCambio(String consentimientoCambio) {
		this.consentimientoCambio = consentimientoCambio;
	}

	public void setMotivoBaja(MotivoBaja motivoBaja) {
		this.motivoBaja = motivoBaja;
	}

	public String getResCheckBtv() {
		return resCheckBtv;
	}

	public void setResCheckBtv(String resCheckBtv) {
		this.resCheckBtv = resCheckBtv;
	}

	public String getCet() {
		return cet;
	}

	public void setCet(String cet) {
		this.cet = cet;
	}
	
	}