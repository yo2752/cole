package org.gestoresmadrid.oegam2comun.trafico.view.dto;

import org.gestoresmadrid.oegam2comun.paises.view.dto.PaisDto;
import org.gestoresmadrid.oegamComun.interviniente.view.dto.IntervinienteTraficoDto;

public class TramiteTrafBajaDto extends TramiteTrafDto {

	private static final long serialVersionUID = -5258836128445952694L;

	private Boolean bajaImpMunicipal;

	private Boolean cartaDGTVehiculoMasDiezAn;

	private Boolean certificadoMedioAmbiental;

	private Boolean declaracionNoEntregaCatV;

	private Boolean declaracionResiduo;

	private String dniCotitulares;

	private Boolean imprPermisoCircu;

	private Boolean justificanteDenuncia;

	private String motivoBaja;

	private Boolean pagoImpMunicipal;

	private Boolean permisoCircu;

	private Boolean propiedadDesguace;

	private Boolean tarjetaInspeccion;

	private Boolean declaracionJuradaExtravio;

	private String tasaDuplicado;

	private IntervinienteTraficoDto titular;

	private IntervinienteTraficoDto representanteTitular;

	private IntervinienteTraficoDto adquiriente;

	private IntervinienteTraficoDto representanteAdquiriente;

	private PaisDto pais;

	private String resCheckBtv;

	private Boolean autoInformeBtv;

	public Boolean getBajaImpMunicipal() {
		return bajaImpMunicipal;
	}

	public void setBajaImpMunicipal(Boolean bajaImpMunicipal) {
		this.bajaImpMunicipal = bajaImpMunicipal;
	}

	public Boolean getCartaDGTVehiculoMasDiezAn() {
		return cartaDGTVehiculoMasDiezAn;
	}

	public void setCartaDGTVehiculoMasDiezAn(Boolean cartaDGTVehiculoMasDiezAn) {
		this.cartaDGTVehiculoMasDiezAn = cartaDGTVehiculoMasDiezAn;
	}

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

	public Boolean getDeclaracionResiduo() {
		return declaracionResiduo;
	}

	public void setDeclaracionResiduo(Boolean declaracionResiduo) {
		this.declaracionResiduo = declaracionResiduo;
	}

	public String getDniCotitulares() {
		return dniCotitulares;
	}

	public void setDniCotitulares(String dniCotitulares) {
		this.dniCotitulares = dniCotitulares;
	}

	public Boolean getImprPermisoCircu() {
		return imprPermisoCircu;
	}

	public void setImprPermisoCircu(Boolean imprPermisoCircu) {
		this.imprPermisoCircu = imprPermisoCircu;
	}

	public Boolean getJustificanteDenuncia() {
		return justificanteDenuncia;
	}

	public void setJustificanteDenuncia(Boolean justificanteDenuncia) {
		this.justificanteDenuncia = justificanteDenuncia;
	}

	public String getMotivoBaja() {
		return motivoBaja;
	}

	public void setMotivoBaja(String motivoBaja) {
		this.motivoBaja = motivoBaja;
	}

	public Boolean getPagoImpMunicipal() {
		return pagoImpMunicipal;
	}

	public void setPagoImpMunicipal(Boolean pagoImpMunicipal) {
		this.pagoImpMunicipal = pagoImpMunicipal;
	}

	public Boolean getPermisoCircu() {
		return permisoCircu;
	}

	public void setPermisoCircu(Boolean permisoCircu) {
		this.permisoCircu = permisoCircu;
	}

	public Boolean getPropiedadDesguace() {
		return propiedadDesguace;
	}

	public void setPropiedadDesguace(Boolean propiedadDesguace) {
		this.propiedadDesguace = propiedadDesguace;
	}

	public Boolean getTarjetaInspeccion() {
		return tarjetaInspeccion;
	}

	public void setTarjetaInspeccion(Boolean tarjetaInspeccion) {
		this.tarjetaInspeccion = tarjetaInspeccion;
	}

	public Boolean getDeclaracionJuradaExtravio() {
		return declaracionJuradaExtravio;
	}

	public void setDeclaracionJuradaExtravio(Boolean declaracionJuradaExtravio) {
		this.declaracionJuradaExtravio = declaracionJuradaExtravio;
	}

	public String getTasaDuplicado() {
		return tasaDuplicado;
	}

	public void setTasaDuplicado(String tasaDuplicado) {
		this.tasaDuplicado = tasaDuplicado;
	}

	public IntervinienteTraficoDto getTitular() {
		return titular;
	}

	public void setTitular(IntervinienteTraficoDto titular) {
		this.titular = titular;
	}

	public IntervinienteTraficoDto getRepresentanteTitular() {
		return representanteTitular;
	}

	public void setRepresentanteTitular(IntervinienteTraficoDto representanteTitular) {
		this.representanteTitular = representanteTitular;
	}

	public IntervinienteTraficoDto getAdquiriente() {
		return adquiriente;
	}

	public void setAdquiriente(IntervinienteTraficoDto adquiriente) {
		this.adquiriente = adquiriente;
	}

	public IntervinienteTraficoDto getRepresentanteAdquiriente() {
		return representanteAdquiriente;
	}

	public void setRepresentanteAdquiriente(IntervinienteTraficoDto representanteAdquiriente) {
		this.representanteAdquiriente = representanteAdquiriente;
	}

	public PaisDto getPais() {
		return pais;
	}

	public void setPais(PaisDto pais) {
		this.pais = pais;
	}

	public String getResCheckBtv() {
		return resCheckBtv;
	}

	public void setResCheckBtv(String resCheckBtv) {
		this.resCheckBtv = resCheckBtv;
	}

	public Boolean getAutoInformeBtv() {
		return autoInformeBtv;
	}

	public void setAutoInformeBtv(Boolean autoInformeBtv) {
		this.autoInformeBtv = autoInformeBtv;
	}

}