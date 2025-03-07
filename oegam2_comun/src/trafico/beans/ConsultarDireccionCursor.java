package trafico.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/*
 * Cursor de consulta de direcciones
 * 'SELECT d.ID_DIRECCION, d.ID_PROVINCIA, pr.NOMBRE provincia, d.ID_MUNICIPIO, m.NOMBRE municipio, d.ID_TIPO_VIA, d.NOMBRE_VIA,
 d.NUMERO, d.COD_POSTAL, d.PUEBLO, d.LETRA, d.ESCALERA, d.BLOQUE, d.PLANTA, d.PUERTA, d.NUM_LOCAL, d.KM, d.HM, 
 p.FECHA_INICIO, p.FECHA_FIN
 FROM PERSONA_DIRECCION p, DIRECCION d, PROVINCIA pr, MUNICIPIO m';
 */

public class ConsultarDireccionCursor implements Serializable {

	private static final long serialVersionUID = 1035134067820437151L;

	BigDecimal ID_DIRECCION;
	String ID_PROVINCIA;
	String provincia;
	String ID_MUNICIPIO;
	String municipio;
	String ID_TIPO_VIA;
	String NOMBRE_VIA;
	String NUMERO;
	String COD_POSTAL;
	String PUEBLO;
	String COD_POSTAL_CORREOS;
	String PUEBLO_CORREOS;
	String LETRA;
	String ESCALERA;
	String BLOQUE;
	String PLANTA;
	String PUERTA;
	BigDecimal NUM_LOCAL;
	BigDecimal KM;
	BigDecimal HM;
	Timestamp FECHA_INICIO;
	Timestamp FECHA_FIN;

	public BigDecimal getID_DIRECCION() {
		return ID_DIRECCION;
	}

	public void setID_DIRECCION(BigDecimal iDDIRECCION) {
		ID_DIRECCION = iDDIRECCION;
	}

	public String getID_PROVINCIA() {
		return ID_PROVINCIA;
	}

	public void setID_PROVINCIA(String iDPROVINCIA) {
		ID_PROVINCIA = iDPROVINCIA;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getID_MUNICIPIO() {
		return ID_MUNICIPIO;
	}

	public void setID_MUNICIPIO(String iDMUNICIPIO) {
		ID_MUNICIPIO = iDMUNICIPIO;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getID_TIPO_VIA() {
		return ID_TIPO_VIA;
	}

	public void setID_TIPO_VIA(String iDTIPOVIA) {
		ID_TIPO_VIA = iDTIPOVIA;
	}

	public String getNOMBRE_VIA() {
		return NOMBRE_VIA;
	}

	public void setNOMBRE_VIA(String nOMBREVIA) {
		NOMBRE_VIA = nOMBREVIA;
	}

	public String getNUMERO() {
		return NUMERO;
	}

	public void setNUMERO(String nUMERO) {
		NUMERO = nUMERO;
	}

	public String getCOD_POSTAL() {
		return COD_POSTAL;
	}

	public void setCOD_POSTAL(String cODPOSTAL) {
		COD_POSTAL = cODPOSTAL;
	}

	public String getPUEBLO() {
		return PUEBLO;
	}

	public void setPUEBLO(String pUEBLO) {
		PUEBLO = pUEBLO;
	}

	public String getLETRA() {
		return LETRA;
	}

	public void setLETRA(String lETRA) {
		LETRA = lETRA;
	}

	public String getESCALERA() {
		return ESCALERA;
	}

	public void setESCALERA(String eSCALERA) {
		ESCALERA = eSCALERA;
	}

	public String getBLOQUE() {
		return BLOQUE;
	}

	public void setBLOQUE(String bLOQUE) {
		BLOQUE = bLOQUE;
	}

	public String getPLANTA() {
		return PLANTA;
	}

	public void setPLANTA(String pLANTA) {
		PLANTA = pLANTA;
	}

	public String getPUERTA() {
		return PUERTA;
	}

	public void setPUERTA(String pUERTA) {
		PUERTA = pUERTA;
	}

	public BigDecimal getNUM_LOCAL() {
		return NUM_LOCAL;
	}

	public void setNUM_LOCAL(BigDecimal nUMLOCAL) {
		NUM_LOCAL = nUMLOCAL;
	}

	public BigDecimal getKM() {
		return KM;
	}

	public void setKM(BigDecimal kM) {
		KM = kM;
	}

	public BigDecimal getHM() {
		return HM;
	}

	public void setHM(BigDecimal hM) {
		HM = hM;
	}

	public Timestamp getFECHA_INICIO() {
		return FECHA_INICIO;
	}

	public void setFECHA_INICIO(Timestamp fECHAINICIO) {
		FECHA_INICIO = fECHAINICIO;
	}

	public Timestamp getFECHA_FIN() {
		return FECHA_FIN;
	}

	public void setFECHA_FIN(Timestamp fECHAFIN) {
		FECHA_FIN = fECHAFIN;
	}

	public String getCOD_POSTAL_CORREOS() {
		return COD_POSTAL_CORREOS;
	}

	public void setCOD_POSTAL_CORREOS(String cOD_POSTAL_CORREOS) {
		COD_POSTAL_CORREOS = cOD_POSTAL_CORREOS;
	}

	public String getPUEBLO_CORREOS() {
		return PUEBLO_CORREOS;
	}

	public void setPUEBLO_CORREOS(String pUEBLO_CORREOS) {
		PUEBLO_CORREOS = pUEBLO_CORREOS;
	}

}