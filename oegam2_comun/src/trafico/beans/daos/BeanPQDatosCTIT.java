package trafico.beans.daos;

import java.math.BigDecimal;

public class BeanPQDatosCTIT extends BeanPQGeneral {
	/*
	 * --------------------------- DATOS_CTIT --------------------
	 * 
	 * PROCEDURE DATOS_CTIT (P_NUM_EXPEDIENTE IN OUT
	 * TRAMITE_TRAFICO.NUM_EXPEDIENTE%TYPE, -- Datos del tramite de
	 * matriculación -- Datos del colegiado y del colegio
	 * P_AGENCYFISCALLD OUT CONTRATO.CIF%TYPE, P_EXTERNALSYSTEMFISCALL OUT
	 * COLEGIO.CIF%TYPE, ----- P_CODE OUT NUMBER, P_SQLERRM OUT VARCHAR2
	 */

	private Object P_NUM_EXPEDIENTE;
	
	private String P_NPASOS;
	private String P_NUM_COLEGIADO;
	private String P_CONSENTIMIENTO;
    
	private String P_AGENCYFISCALLD;
	private String P_EXTERNALSYSTEMFISCALL;
    
	private BigDecimal P_PLAZAS;
	private String P_ID_SERVICIO;
	private String P_MMA;
	private String P_TARA;	
	
	private String P_SELLER_INE_CODE;
	private BigDecimal P_FIRST_MATRICULATION_INE_CODE;
	
	// --------------- GET & SET ---------------
	public Object getP_NUM_EXPEDIENTE() {
		return P_NUM_EXPEDIENTE;
	}	
	public void setP_NUM_EXPEDIENTE(Object pNUMEXPEDIENTE) {
		P_NUM_EXPEDIENTE = pNUMEXPEDIENTE;
	}
	public String getP_NPASOS() {
		return P_NPASOS;
	}
	public void setP_NPASOS(String pNPASOS) {
		P_NPASOS = pNPASOS;
	}
	public String getP_NUM_COLEGIADO() {
		return P_NUM_COLEGIADO;
	}
	public void setP_NUM_COLEGIADO(String pNUMCOLEGIADO) {
		P_NUM_COLEGIADO = pNUMCOLEGIADO;
	}
	public String getP_CONSENTIMIENTO() {
		return P_CONSENTIMIENTO;
	}
	public void setP_CONSENTIMIENTO(String pCONSENTIMIENTO) {
		P_CONSENTIMIENTO = pCONSENTIMIENTO;
	}
	public String getP_AGENCYFISCALLD() {
		return P_AGENCYFISCALLD;
	}
	public void setP_AGENCYFISCALLD(String pAGENCYFISCALLD) {
		P_AGENCYFISCALLD = pAGENCYFISCALLD;
	}
	public String getP_EXTERNALSYSTEMFISCALL() {
		return P_EXTERNALSYSTEMFISCALL;
	}
	public void setP_EXTERNALSYSTEMFISCALL(String pEXTERNALSYSTEMFISCALL) {
		P_EXTERNALSYSTEMFISCALL = pEXTERNALSYSTEMFISCALL;
	}
	public BigDecimal getP_PLAZAS() {
		return P_PLAZAS;
	}
	public void setP_PLAZAS(BigDecimal pPLAZAS) {
		P_PLAZAS = pPLAZAS;
	}
	public String getP_ID_SERVICIO() {
		return P_ID_SERVICIO;
	}
	public void setP_ID_SERVICIO(String pIDSERVICIO) {
		P_ID_SERVICIO = pIDSERVICIO;
	}
	public String getP_MMA() {
		return P_MMA;
	}
	public void setP_MMA(String pMMA) {
		P_MMA = pMMA;
	}
	public String getP_TARA() {
		return P_TARA;
	}
	public void setP_TARA(String pTARA) {
		P_TARA = pTARA;
	}
	public String getP_SELLER_INE_CODE() {
		return P_SELLER_INE_CODE;
	}
	public void setP_SELLER_INE_CODE(String pSELLERINECODE) {
		P_SELLER_INE_CODE = pSELLERINECODE;
	}
	public BigDecimal getP_FIRST_MATRICULATION_INE_CODE() {
		return P_FIRST_MATRICULATION_INE_CODE;
	}
	public void setP_FIRST_MATRICULATION_INE_CODE(BigDecimal pFIRSTMATRICULATIONINECODE) {
		P_FIRST_MATRICULATION_INE_CODE = pFIRSTMATRICULATIONINECODE;
	}	
}