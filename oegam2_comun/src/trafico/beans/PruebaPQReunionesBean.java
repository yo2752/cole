package trafico.beans;

import java.math.BigDecimal;
import java.sql.Timestamp;

import trafico.beans.daos.BeanPQGeneral;

public class PruebaPQReunionesBean extends BeanPQGeneral{
	
	public String ROWID_REUNION;
	public BigDecimal P_ID_TRAMITE_REGISTRO; 
	public BigDecimal P_ID_REUNION;
	public String P_TIPO_REUNION; 
	public String P_LUGAR;
	
	public Timestamp P_FECHA; 
	 
	public String P_AMBITO;
	public String P_CARACTER;
	public Timestamp P_FECHA_APRO_ACTA; 
	public String P_FORMA_APRO_ACTA;
	
	public String P_CONTENIDO_CONVO; 
	public BigDecimal P_PORCENTAJE_CAPITAL;
	public BigDecimal P_PORCENTAJE_SOCIOS;
	
	
	
	
	public String getP_CARACTER() {
		return P_CARACTER;
	}
	public void setP_CARACTER(String pCARACTER) {
		P_CARACTER = pCARACTER;
	}
	
	
	
	public String getP_TIPO_REUNION() {
		return P_TIPO_REUNION;
	}
	public void setP_TIPO_REUNION(String pTIPOREUNION) {
		P_TIPO_REUNION = pTIPOREUNION;
	}
	public String getP_LUGAR() {
		return P_LUGAR;
	}
	public void setP_LUGAR(String pLUGAR) {
		P_LUGAR = pLUGAR;
	}
	public Timestamp getP_FECHA() {
		return P_FECHA;
	}
	public void setP_FECHA(Timestamp ts) {
		P_FECHA = ts;
	}
	public String getP_AMBITO() {
		return P_AMBITO;
	}
	public BigDecimal getP_ID_TRAMITE_REGISTRO() {
		return P_ID_TRAMITE_REGISTRO;
	}
	public void setP_ID_TRAMITE_REGISTRO(BigDecimal pIDTRAMITEREGISTRO) {
		P_ID_TRAMITE_REGISTRO = pIDTRAMITEREGISTRO;
	}
	public BigDecimal getP_ID_REUNION() {
		return P_ID_REUNION;
	}
	public void setP_ID_REUNION(BigDecimal pIDREUNION) {
		P_ID_REUNION = pIDREUNION;
	}
	public void setP_AMBITO(String pAMBITO) {
		P_AMBITO = pAMBITO;
	}
	public Timestamp getP_FECHA_APRO_ACTA() {
		return P_FECHA_APRO_ACTA;
	}
	public void setP_FECHA_APRO_ACTA(Timestamp fechaActaSQL) {
		P_FECHA_APRO_ACTA = fechaActaSQL;
	}
	public String getP_FORMA_APRO_ACTA() {
		return P_FORMA_APRO_ACTA;
	}
	public void setP_FORMA_APRO_ACTA(String pFORMAAPROACTA) {
		P_FORMA_APRO_ACTA = pFORMAAPROACTA;
	}
	public String getP_CONTENIDO_CONVO() {
		return P_CONTENIDO_CONVO;
	}
	public void setP_CONTENIDO_CONVO(String pCONTENIDOCONVO) {
		P_CONTENIDO_CONVO = pCONTENIDOCONVO;
	}
	public BigDecimal getP_PORCENTAJE_CAPITAL() {
		return P_PORCENTAJE_CAPITAL;
	}
	public void setP_PORCENTAJE_CAPITAL(BigDecimal porcentajeCapitalAcuerdo) {
		P_PORCENTAJE_CAPITAL = porcentajeCapitalAcuerdo;
	}
	public BigDecimal getP_PORCENTAJE_SOCIOS() {
		return P_PORCENTAJE_SOCIOS;
	}
	public void setP_PORCENTAJE_SOCIOS(BigDecimal porcentajeSociosAcuerdo) {
		P_PORCENTAJE_SOCIOS = porcentajeSociosAcuerdo;
	}
	public String getROWID_REUNION() {
		return ROWID_REUNION;
	}
	public void setROWID_REUNION(String rOWIDREUNION) {
		ROWID_REUNION = rOWIDREUNION;
	}
	
	
	

}
