package org.gestoresmadrid.oegamComun.trafico.view.dto;

import java.io.Serializable;

public class FormularioAutorizarFichaADto implements Serializable{

	private static final long serialVersionUID = -4056813688456311445L;

	//FICHAS A
	private String nombre;
	private String numExpediente;
	private String bastiMatri;
	private String doiTitular;
	private String doiAdquiriente;
	private String doiTransmitente;
	private String jefatura;
	private String numColegiado;
	private String estacionItv;
	private boolean validacionEstacion;
	private String kilometraje;
	private boolean validacionKm;
	private String paisPreviaMatri;
	private boolean validacionPaisPrevMatr;
	private String observaciones;
	private String version;
	
	private boolean tarjetaTipoA1;
	private boolean impuestoIedmt1;
	private boolean impuestoIvtm1;
	private boolean acreditacionPropiedad1;
	private boolean justificanteIva1;
	private boolean acreditarCenso1;
	private boolean acreditarServicioVeh1;
	private boolean cema1;
	private boolean noJustifTransp1;

	private boolean tarjetaTipoA2;
	private boolean impuestoIedmt2;
	private boolean impuestoIvtm2;
	private boolean acreditacionPropiedad2;
	private boolean acreditarServicioVeh2;
	private boolean cema2;
	private boolean dua2;
	private boolean noJustifTransp2;
	
	private boolean docOriginal13;
	private boolean docOriginal23;
	private boolean placaVerde3;
	private boolean tarjetaTipoA3;
	private boolean impuestoIedmt3;
	private boolean impuestoIvtm3;
	private boolean acreditacionPropiedad3;
	private boolean traduccionContrato3;
	private boolean itp3;
	private boolean iae3;
	private boolean acreditarServicioVeh3;
	private boolean cema3;
	private boolean noJustifTransp3;
	
	private boolean docOriginal14;
	private boolean docOriginal24;
	private boolean tarjetaTipoA4;
	private boolean impuestoIedmt4;
	private boolean impuestoIvtm4;
	private boolean acreditacionPropiedad4;
	private boolean traduccionContrato4;
	private boolean itp4;
	private boolean iae4;
	private boolean acreditarServicioVeh4;
	private boolean cema4;
	private boolean dua4;
	private boolean noJustifTransp4;
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getNumExpediente() {
		return numExpediente;
	}
	public void setNumExpediente(String numExpediente) {
		this.numExpediente = numExpediente;
	}
	public String getBastiMatri() {
		return bastiMatri;
	}
	public void setBastiMatri(String bastiMatri) {
		this.bastiMatri = bastiMatri;
	}
	public String getDoiTitular() {
		return doiTitular;
	}
	public void setDoiTitular(String doiTitular) {
		this.doiTitular = doiTitular;
	}
	public String getJefatura() {
		return jefatura;
	}
	public void setJefatura(String jefatura) {
		this.jefatura = jefatura;
	}
	public String getNumColegiado() {
		return numColegiado;
	}
	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}
	public String getEstacionItv() {
		return estacionItv;
	}
	public void setEstacionItv(String estacionItv) {
		this.estacionItv = estacionItv;
	}
	public String getKilometraje() {
		return kilometraje;
	}
	public void setKilometraje(String kilometraje) {
		this.kilometraje = kilometraje;
	}
	public String getPaisPreviaMatri() {
		return paisPreviaMatri;
	}
	public void setPaisPreviaMatri(String paisPreviaMatri) {
		this.paisPreviaMatri = paisPreviaMatri;
	}
	public boolean isTarjetaTipoA1() {
		return tarjetaTipoA1;
	}
	public void setTarjetaTipoA1(boolean tarjetaTipoA1) {
		this.tarjetaTipoA1 = tarjetaTipoA1;
	}
	public boolean isImpuestoIedmt1() {
		return impuestoIedmt1;
	}
	public void setImpuestoIedmt1(boolean impuestoIedmt1) {
		this.impuestoIedmt1 = impuestoIedmt1;
	}
	public boolean isImpuestoIvtm1() {
		return impuestoIvtm1;
	}
	public void setImpuestoIvtm1(boolean impuestoIvtm1) {
		this.impuestoIvtm1 = impuestoIvtm1;
	}
	public boolean isAcreditacionPropiedad1() {
		return acreditacionPropiedad1;
	}
	public void setAcreditacionPropiedad1(boolean acreditacionPropiedad1) {
		this.acreditacionPropiedad1 = acreditacionPropiedad1;
	}
	public boolean isJustificanteIva1() {
		return justificanteIva1;
	}
	public void setJustificanteIva1(boolean justificanteIva1) {
		this.justificanteIva1 = justificanteIva1;
	}
	public boolean isAcreditarCenso1() {
		return acreditarCenso1;
	}
	public void setAcreditarCenso1(boolean acreditarCenso1) {
		this.acreditarCenso1 = acreditarCenso1;
	}
	public boolean isAcreditarServicioVeh1() {
		return acreditarServicioVeh1;
	}
	public void setAcreditarServicioVeh1(boolean acreditarServicioVeh1) {
		this.acreditarServicioVeh1 = acreditarServicioVeh1;
	}
	public boolean isCema1() {
		return cema1;
	}
	public void setCema1(boolean cema1) {
		this.cema1 = cema1;
	}
	public boolean isNoJustifTransp1() {
		return noJustifTransp1;
	}
	public void setNoJustifTransp1(boolean noJustifTransp1) {
		this.noJustifTransp1 = noJustifTransp1;
	}
	public boolean isTarjetaTipoA2() {
		return tarjetaTipoA2;
	}
	public void setTarjetaTipoA2(boolean tarjetaTipoA2) {
		this.tarjetaTipoA2 = tarjetaTipoA2;
	}
	public boolean isImpuestoIedmt2() {
		return impuestoIedmt2;
	}
	public void setImpuestoIedmt2(boolean impuestoIedmt2) {
		this.impuestoIedmt2 = impuestoIedmt2;
	}
	public boolean isImpuestoIvtm2() {
		return impuestoIvtm2;
	}
	public void setImpuestoIvtm2(boolean impuestoIvtm2) {
		this.impuestoIvtm2 = impuestoIvtm2;
	}
	public boolean isAcreditacionPropiedad2() {
		return acreditacionPropiedad2;
	}
	public void setAcreditacionPropiedad2(boolean acreditacionPropiedad2) {
		this.acreditacionPropiedad2 = acreditacionPropiedad2;
	}
	public boolean isAcreditarServicioVeh2() {
		return acreditarServicioVeh2;
	}
	public void setAcreditarServicioVeh2(boolean acreditarServicioVeh2) {
		this.acreditarServicioVeh2 = acreditarServicioVeh2;
	}
	public boolean isCema2() {
		return cema2;
	}
	public void setCema2(boolean cema2) {
		this.cema2 = cema2;
	}
	public boolean isDua2() {
		return dua2;
	}
	public void setDua2(boolean dua2) {
		this.dua2 = dua2;
	}
	public boolean isNoJustifTransp2() {
		return noJustifTransp2;
	}
	public void setNoJustifTransp2(boolean noJustifTransp2) {
		this.noJustifTransp2 = noJustifTransp2;
	}
	public boolean isDocOriginal13() {
		return docOriginal13;
	}
	public void setDocOriginal13(boolean docOriginal13) {
		this.docOriginal13 = docOriginal13;
	}
	public boolean isDocOriginal23() {
		return docOriginal23;
	}
	public void setDocOriginal23(boolean docOriginal23) {
		this.docOriginal23 = docOriginal23;
	}
	public boolean isPlacaVerde3() {
		return placaVerde3;
	}
	public void setPlacaVerde3(boolean placaVerde3) {
		this.placaVerde3 = placaVerde3;
	}
	public boolean isTarjetaTipoA3() {
		return tarjetaTipoA3;
	}
	public void setTarjetaTipoA3(boolean tarjetaTipoA3) {
		this.tarjetaTipoA3 = tarjetaTipoA3;
	}
	public boolean isImpuestoIedmt3() {
		return impuestoIedmt3;
	}
	public void setImpuestoIedmt3(boolean impuestoIedmt3) {
		this.impuestoIedmt3 = impuestoIedmt3;
	}
	public boolean isImpuestoIvtm3() {
		return impuestoIvtm3;
	}
	public void setImpuestoIvtm3(boolean impuestoIvtm3) {
		this.impuestoIvtm3 = impuestoIvtm3;
	}
	public boolean isAcreditacionPropiedad3() {
		return acreditacionPropiedad3;
	}
	public void setAcreditacionPropiedad3(boolean acreditacionPropiedad3) {
		this.acreditacionPropiedad3 = acreditacionPropiedad3;
	}
	public boolean isTraduccionContrato3() {
		return traduccionContrato3;
	}
	public void setTraduccionContrato3(boolean traduccionContrato3) {
		this.traduccionContrato3 = traduccionContrato3;
	}
	public boolean isItp3() {
		return itp3;
	}
	public void setItp3(boolean itp3) {
		this.itp3 = itp3;
	}
	public boolean isIae3() {
		return iae3;
	}
	public void setIae3(boolean iae3) {
		this.iae3 = iae3;
	}
	public boolean isAcreditarServicioVeh3() {
		return acreditarServicioVeh3;
	}
	public void setAcreditarServicioVeh3(boolean acreditarServicioVeh3) {
		this.acreditarServicioVeh3 = acreditarServicioVeh3;
	}
	public boolean isCema3() {
		return cema3;
	}
	public void setCema3(boolean cema3) {
		this.cema3 = cema3;
	}
	public boolean isNoJustifTransp3() {
		return noJustifTransp3;
	}
	public void setNoJustifTransp3(boolean noJustifTransp3) {
		this.noJustifTransp3 = noJustifTransp3;
	}
	public boolean isDocOriginal14() {
		return docOriginal14;
	}
	public void setDocOriginal14(boolean docOriginal14) {
		this.docOriginal14 = docOriginal14;
	}
	public boolean isDocOriginal24() {
		return docOriginal24;
	}
	public void setDocOriginal24(boolean docOriginal24) {
		this.docOriginal24 = docOriginal24;
	}
	public boolean isTarjetaTipoA4() {
		return tarjetaTipoA4;
	}
	public void setTarjetaTipoA4(boolean tarjetaTipoA4) {
		this.tarjetaTipoA4 = tarjetaTipoA4;
	}
	public boolean isImpuestoIedmt4() {
		return impuestoIedmt4;
	}
	public void setImpuestoIedmt4(boolean impuestoIedmt4) {
		this.impuestoIedmt4 = impuestoIedmt4;
	}
	public boolean isImpuestoIvtm4() {
		return impuestoIvtm4;
	}
	public void setImpuestoIvtm4(boolean impuestoIvtm4) {
		this.impuestoIvtm4 = impuestoIvtm4;
	}
	public boolean isAcreditacionPropiedad4() {
		return acreditacionPropiedad4;
	}
	public void setAcreditacionPropiedad4(boolean acreditacionPropiedad4) {
		this.acreditacionPropiedad4 = acreditacionPropiedad4;
	}
	public boolean isTraduccionContrato4() {
		return traduccionContrato4;
	}
	public void setTraduccionContrato4(boolean traduccionContrato4) {
		this.traduccionContrato4 = traduccionContrato4;
	}
	public boolean isItp4() {
		return itp4;
	}
	public void setItp4(boolean itp4) {
		this.itp4 = itp4;
	}
	public boolean isIae4() {
		return iae4;
	}
	public void setIae4(boolean iae4) {
		this.iae4 = iae4;
	}
	public boolean isAcreditarServicioVeh4() {
		return acreditarServicioVeh4;
	}
	public void setAcreditarServicioVeh4(boolean acreditarServicioVeh4) {
		this.acreditarServicioVeh4 = acreditarServicioVeh4;
	}
	public boolean isCema4() {
		return cema4;
	}
	public void setCema4(boolean cema4) {
		this.cema4 = cema4;
	}
	public boolean isDua4() {
		return dua4;
	}
	public void setDua4(boolean dua4) {
		this.dua4 = dua4;
	}
	public boolean isNoJustifTransp4() {
		return noJustifTransp4;
	}
	public void setNoJustifTransp4(boolean noJustifTransp4) {
		this.noJustifTransp4 = noJustifTransp4;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public boolean isValidacionEstacion() {
		return validacionEstacion;
	}
	public void setValidacionEstacion(boolean validacionEstacion) {
		this.validacionEstacion = validacionEstacion;
	}
	public boolean isValidacionKm() {
		return validacionKm;
	}
	public void setValidacionKm(boolean validacionKm) {
		this.validacionKm = validacionKm;
	}
	public boolean isValidacionPaisPrevMatr() {
		return validacionPaisPrevMatr;
	}
	public void setValidacionPaisPrevMatr(boolean validacionPaisPrevMatr) {
		this.validacionPaisPrevMatr = validacionPaisPrevMatr;
	}
	public String getDoiAdquiriente() {
		return doiAdquiriente;
	}
	public void setDoiAdquiriente(String doiAdquiriente) {
		this.doiAdquiriente = doiAdquiriente;
	}
	public String getDoiTransmitente() {
		return doiTransmitente;
	}
	public void setDoiTransmitente(String doiTransmitente) {
		this.doiTransmitente = doiTransmitente;
	}
}
