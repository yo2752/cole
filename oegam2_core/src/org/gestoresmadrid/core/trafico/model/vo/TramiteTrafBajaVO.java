package org.gestoresmadrid.core.trafico.model.vo;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SecondaryTable;

import org.gestoresmadrid.core.paises.model.vo.PaisVO;
import org.hibernate.annotations.FetchMode;

/**
 * The persistent class for the TRAMITE_TRAF_BAJA database table.
 * 
 */
@Entity
@SecondaryTable(name = "TRAMITE_TRAF_BAJA")
@org.hibernate.annotations.Table(appliesTo = "TRAMITE_TRAF_BAJA", fetch = FetchMode.SELECT)
@DiscriminatorValue("T3")
public class TramiteTrafBajaVO extends TramiteTraficoVO {

	private static final long serialVersionUID = 6192238277387914180L;

	@Column(table = "TRAMITE_TRAF_BAJA", name = "BAJA_IMP_MUNICIPAL")
	private String bajaImpMunicipal;

	@Column(table = "TRAMITE_TRAF_BAJA", name = "DECLARACION_RESIDUO")
	private String declaracionResiduo;

	@Column(table = "TRAMITE_TRAF_BAJA", name = "DNI_COTITULARES")
	private String dniCotitulares;

	@Column(table = "TRAMITE_TRAF_BAJA", name = "IMPR_PERMISO_CIRCU")
	private String imprPermisoCircu;

	@Column(table = "TRAMITE_TRAF_BAJA", name = "JUSTIFICANTE_DENUNCIA")
	private String justificanteDenuncia;

	@Column(table = "TRAMITE_TRAF_BAJA", name = "MOTIVO_BAJA")
	private String motivoBaja;

	@Column(table = "TRAMITE_TRAF_BAJA", name = "PAGO_IMP_MUNICIPAL")
	private String pagoImpMunicipal;

	@Column(table = "TRAMITE_TRAF_BAJA", name = "PERMISO_CIRCU")
	private String permisoCircu;

	@Column(table = "TRAMITE_TRAF_BAJA", name = "DECLARACION_JURADA_EXTV")
	private String declaracionJuradaExtravio;

	@Column(table = "TRAMITE_TRAF_BAJA", name = "PROPIEDAD_DESGUACE")
	private String propiedadDesguace;

	@Column(table = "TRAMITE_TRAF_BAJA", name = "TARJETA_INSPECCION")
	private String tarjetaInspeccion;

	@Column(table = "TRAMITE_TRAF_BAJA", name = "TASA_DUPLICADO")
	private String tasaDuplicado;

	@Column(table = "TRAMITE_TRAF_BAJA", name = "DECLARACION_NO_ENTREGA_CATV")
	private Boolean declaracionNoEntregaCatV;

	@Column(table = "TRAMITE_TRAF_BAJA", name = "CARTA_DGT_VEHICULO_MAS_DIEZ_AN")
	private Boolean cartaDGTVehiculoMasDiezAnios;

	@Column(table = "TRAMITE_TRAF_BAJA", name = "CERTIFICADO_MEDIOAMBIENTAL")
	private Boolean certificadoMedioambiental;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(table = "TRAMITE_TRAF_BAJA", name = "ID_PAIS")
	private PaisVO pais;

	@Column(table = "TRAMITE_TRAF_BAJA", name = "RES_CHECK_BTV")
	private String resCheckBtv;

	@Column(table = "TRAMITE_TRAF_BAJA", name = "AUTO_INFORME_BTV")
	private String autoInformeBtv;

	public TramiteTrafBajaVO() {
	}

	public String getBajaImpMunicipal() {
		return bajaImpMunicipal;
	}

	public void setBajaImpMunicipal(String bajaImpMunicipal) {
		this.bajaImpMunicipal = bajaImpMunicipal;
	}

	public String getDeclaracionResiduo() {
		return declaracionResiduo;
	}

	public void setDeclaracionResiduo(String declaracionResiduo) {
		this.declaracionResiduo = declaracionResiduo;
	}

	public String getDniCotitulares() {
		return dniCotitulares;
	}

	public void setDniCotitulares(String dniCotitulares) {
		this.dniCotitulares = dniCotitulares;
	}

	public String getImprPermisoCircu() {
		return imprPermisoCircu;
	}

	public void setImprPermisoCircu(String imprPermisoCircu) {
		this.imprPermisoCircu = imprPermisoCircu;
	}

	public String getJustificanteDenuncia() {
		return justificanteDenuncia;
	}

	public void setJustificanteDenuncia(String justificanteDenuncia) {
		this.justificanteDenuncia = justificanteDenuncia;
	}

	public String getMotivoBaja() {
		return motivoBaja;
	}

	public void setMotivoBaja(String motivoBaja) {
		this.motivoBaja = motivoBaja;
	}

	public String getPagoImpMunicipal() {
		return pagoImpMunicipal;
	}

	public void setPagoImpMunicipal(String pagoImpMunicipal) {
		this.pagoImpMunicipal = pagoImpMunicipal;
	}

	public String getPermisoCircu() {
		return permisoCircu;
	}

	public void setPermisoCircu(String permisoCircu) {
		this.permisoCircu = permisoCircu;
	}

	public String getPropiedadDesguace() {
		return propiedadDesguace;
	}

	public void setPropiedadDesguace(String propiedadDesguace) {
		this.propiedadDesguace = propiedadDesguace;
	}

	public String getTarjetaInspeccion() {
		return tarjetaInspeccion;
	}

	public void setTarjetaInspeccion(String tarjetaInspeccion) {
		this.tarjetaInspeccion = tarjetaInspeccion;
	}

	public String getTasaDuplicado() {
		return tasaDuplicado;
	}

	public void setTasaDuplicado(String tasaDuplicado) {
		this.tasaDuplicado = tasaDuplicado;
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

	public Boolean getCertificadoMedioambiental() {
		return certificadoMedioambiental;
	}

	public void setCertificadoMedioambiental(Boolean certificadoMedioambiental) {
		this.certificadoMedioambiental = certificadoMedioambiental;
	}

	public PaisVO getPais() {
		return pais;
	}

	public void setPais(PaisVO pais) {
		this.pais = pais;
	}

	public String getResCheckBtv() {
		return resCheckBtv;
	}

	public void setResCheckBtv(String resCheckBtv) {
		this.resCheckBtv = resCheckBtv;
	}

	public String getDeclaracionJuradaExtravio() {
		return declaracionJuradaExtravio;
	}

	public void setDeclaracionJuradaExtravio(String declaracionJuradaExtravio) {
		this.declaracionJuradaExtravio = declaracionJuradaExtravio;
	}

	public String getAutoInformeBtv() {
		return autoInformeBtv;
	}

	public void setAutoInformeBtv(String autoInformeBtv) {
		this.autoInformeBtv = autoInformeBtv;
	}

}