package hibernate.entities.trafico;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the TRAMITE_TRAF_BAJA database table.
 * 
 */
@Entity
@Table(name = "TRAMITE_TRAF_BAJA")
public class TramiteTrafBaja implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "NUM_EXPEDIENTE")
	private Long numExpediente;

	@Column(name = "BAJA_IMP_MUNICIPAL")
	private String bajaImpMunicipal;

	@Column(name = "DECLARACION_RESIDUO")
	private String declaracionResiduo;

	@Column(name = "DNI_COTITULARES")
	private String dniCotitulares;

	@Column(name = "IMPR_PERMISO_CIRCU")
	private String imprPermisoCircu;

	@Column(name = "JUSTIFICANTE_DENUNCIA")
	private String justificanteDenuncia;

	@Column(name = "MOTIVO_BAJA")
	private String motivoBaja;

	@Column(name = "PAGO_IMP_MUNICIPAL")
	private String pagoImpMunicipal;

	@Column(name = "PERMISO_CIRCU")
	private String permisoCircu;

	@Column(name = "PROPIEDAD_DESGUACE")
	private String propiedadDesguace;

	@Column(name = "TARJETA_INSPECCION")
	private String tarjetaInspeccion;

	@Column(name = "TASA_DUPLICADO")
	private String tasaDuplicado;

	@Column(name = "DECLARACION_NO_ENTREGA_CATV")
	private Boolean declaracionNoEntregaCatV;

	@Column(name = "CARTA_DGT_VEHICULO_MAS_DIEZ_AN")
	private Boolean cartaDGTVehiculoMasDiezAnios;

	@Column(name = "DECLARACION_JURADA_EXTV")
	private String declaracionJuradaExtravio;

	@Column(name = "CERTIFICADO_MEDIOAMBIENTAL")
	private Boolean certificadoMedioambiental;

	@Column(name = "RES_CHECK_BTV")
	private String resCheckBtv;

	// bi-directional one-to-one association to TramiteTrafico
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "NUM_EXPEDIENTE")
	private List<TramiteTrafico> tramiteTrafico;

	public TramiteTrafBaja() {
	}

	public Long getNumExpediente() {
		return this.numExpediente;
	}

	public void setNumExpediente(Long numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getBajaImpMunicipal() {
		return this.bajaImpMunicipal;
	}

	public void setBajaImpMunicipal(String bajaImpMunicipal) {
		this.bajaImpMunicipal = bajaImpMunicipal;
	}

	public String getDeclaracionResiduo() {
		return this.declaracionResiduo;
	}

	public void setDeclaracionResiduo(String declaracionResiduo) {
		this.declaracionResiduo = declaracionResiduo;
	}

	public String getDniCotitulares() {
		return this.dniCotitulares;
	}

	public void setDniCotitulares(String dniCotitulares) {
		this.dniCotitulares = dniCotitulares;
	}

	public String getImprPermisoCircu() {
		return this.imprPermisoCircu;
	}

	public void setImprPermisoCircu(String imprPermisoCircu) {
		this.imprPermisoCircu = imprPermisoCircu;
	}

	public String getJustificanteDenuncia() {
		return this.justificanteDenuncia;
	}

	public void setJustificanteDenuncia(String justificanteDenuncia) {
		this.justificanteDenuncia = justificanteDenuncia;
	}

	public String getMotivoBaja() {
		return this.motivoBaja;
	}

	public void setMotivoBaja(String motivoBaja) {
		this.motivoBaja = motivoBaja;
	}

	public String getPagoImpMunicipal() {
		return this.pagoImpMunicipal;
	}

	public void setPagoImpMunicipal(String pagoImpMunicipal) {
		this.pagoImpMunicipal = pagoImpMunicipal;
	}

	public String getPermisoCircu() {
		return this.permisoCircu;
	}

	public void setPermisoCircu(String permisoCircu) {
		this.permisoCircu = permisoCircu;
	}

	public String getPropiedadDesguace() {
		return this.propiedadDesguace;
	}

	public void setPropiedadDesguace(String propiedadDesguace) {
		this.propiedadDesguace = propiedadDesguace;
	}

	public String getTarjetaInspeccion() {
		return this.tarjetaInspeccion;
	}

	public void setTarjetaInspeccion(String tarjetaInspeccion) {
		this.tarjetaInspeccion = tarjetaInspeccion;
	}

	public String getTasaDuplicado() {
		return this.tasaDuplicado;
	}

	public void setTasaDuplicado(String tasaDuplicado) {
		this.tasaDuplicado = tasaDuplicado;
	}

	public List<TramiteTrafico> getTramiteTrafico() {
		return this.tramiteTrafico;
	}

	public void setTramiteTrafico(List<TramiteTrafico> tramiteTrafico) {
		this.tramiteTrafico = tramiteTrafico;
	}

	public Boolean isDeclaracionNoEntregaCatV() {
		return declaracionNoEntregaCatV;
	}

	public void setDeclaracionNoEntregaCatV(Boolean declaracionNoEntregaCatV) {
		this.declaracionNoEntregaCatV = declaracionNoEntregaCatV;
	}

	public Boolean isCartaDGTVehiculoMasDiezAnios() {
		return cartaDGTVehiculoMasDiezAnios;
	}

	public void setCartaDGTVehiculoMasDiezAnios(Boolean cartaDGTVehiculoMasDiezAnios) {
		this.cartaDGTVehiculoMasDiezAnios = cartaDGTVehiculoMasDiezAnios;
	}

	public Boolean certificadoMedioambiental() {
		return certificadoMedioambiental;
	}

	public void setCertificadoMedioambiental(Boolean certificadoMedioambiental) {
		this.certificadoMedioambiental = certificadoMedioambiental;
	}

	public String getResCheckBtv() {
		return resCheckBtv;
	}

	public void setResChecKBtv(String resCheckBtv) {
		this.resCheckBtv = resCheckBtv;
	}

	public String getDeclaracionJuradaExtravio() {
		return declaracionJuradaExtravio;
	}

	public void setDeclaracionJuradaExtravio(String declaracionJuradaExtravio) {
		this.declaracionJuradaExtravio = declaracionJuradaExtravio;
	}

}