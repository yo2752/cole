package general.beans.daos.pq_personas2;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;





public class ExpedienteCursor implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8361940720669029370L;

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
	BigDecimal NUM_EXPEDIENTE;
	String TIPO_INTERVINIENTE;
	Boolean esPadre;
	Boolean esHijo;
	Boolean esPadreSoltero;
    String DESCRIP_INTERVINIENTE;
	
    
	
	
    
	public Boolean getEsPadreSoltero() {
		return esPadreSoltero;
	}
	public void setEsPadreSoltero(Boolean esPadreSoltero) {
		this.esPadreSoltero = esPadreSoltero;
	}
	String COD_PADRE;
	
	public String getCOD_PADRE() {
		return COD_PADRE;
	}
	public void setCOD_PADRE(String cODPADRE) {
		COD_PADRE = cODPADRE;
	}
	public Boolean getEsPadre() {
		return esPadre;
	}
	public void setEsPadre(Boolean esPadre) {
		this.esPadre = esPadre;
	}
	public Boolean getEsHijo() {
		return esHijo;
	}
	public void setEsHijo(Boolean esHijo) {
		this.esHijo = esHijo;
	}

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
	public String getDESCRIP_INTERVINIENTE() {
		return DESCRIP_INTERVINIENTE;
	}
	public void setDESCRIP_INTERVINIENTE(String dESCRIPINTERVINIENTE) {
		DESCRIP_INTERVINIENTE = dESCRIPINTERVINIENTE;
	}
	public void setFECHA_FIN(Timestamp fECHAFIN) {
		FECHA_FIN = fECHAFIN;
	}
	public BigDecimal getNUM_EXPEDIENTE() {
		return NUM_EXPEDIENTE;
	}
	public void setNUM_EXPEDIENTE(BigDecimal nUMEXPEDIENTE) {
		NUM_EXPEDIENTE = nUMEXPEDIENTE;
	}
	public String getTIPO_INTERVINIENTE() {
		return TIPO_INTERVINIENTE;
	}
	public void setTIPO_INTERVINIENTE(String tIPOINTERVINIENTE) {
		TIPO_INTERVINIENTE = tIPOINTERVINIENTE;
		
		
	}
	
	
	
	
	
	
}