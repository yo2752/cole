package trafico.beans.daos;

import java.math.BigDecimal;

public class BeanPQDuplicarTramite extends BeanPQGeneral {	
	private BigDecimal P_ID_USUARIO;                                                  
	private BigDecimal P_ID_CONTRATO;     
	private BigDecimal P_ID_CONTRATO_SESSION;     
	private BigDecimal P_NUM_EXPEDIENTE_ORI;                                                   
	private BigDecimal P_NUM_EXPEDIENTE_NUE;
	private BigDecimal P_ID_TIPO_CREACION;

	// ---------------------- GET & SET ---------------------- 
	public BigDecimal getP_ID_USUARIO() {
		return P_ID_USUARIO;
	}
	public void setP_ID_USUARIO(BigDecimal pIDUSUARIO) {
		P_ID_USUARIO = pIDUSUARIO;
	}
	public BigDecimal getP_ID_CONTRATO() {
		return P_ID_CONTRATO;
	}
	public void setP_ID_CONTRATO(BigDecimal pIDCONTRATO) {
		P_ID_CONTRATO = pIDCONTRATO;
	}
	public BigDecimal getP_ID_CONTRATO_SESSION() {
		return P_ID_CONTRATO_SESSION;
	}
	public void setP_ID_CONTRATO_SESSION(BigDecimal pIDCONTRATOSESSION) {
		P_ID_CONTRATO_SESSION = pIDCONTRATOSESSION;
	}
	public BigDecimal getP_NUM_EXPEDIENTE_ORI() {
		return P_NUM_EXPEDIENTE_ORI;
	}
	public void setP_NUM_EXPEDIENTE_ORI(BigDecimal pNUMEXPEDIENTEORI) {
		P_NUM_EXPEDIENTE_ORI = pNUMEXPEDIENTEORI;
	}
	public BigDecimal getP_NUM_EXPEDIENTE_NUE() {
		return P_NUM_EXPEDIENTE_NUE;
	}
	public void setP_NUM_EXPEDIENTE_NUE(BigDecimal pNUMEXPEDIENTENUE) {
		P_NUM_EXPEDIENTE_NUE = pNUMEXPEDIENTENUE;
	}
	public BigDecimal getP_ID_TIPO_CREACION() {
		return P_ID_TIPO_CREACION;
	}
	public void setP_ID_TIPO_CREACION(BigDecimal pIDTIPOCREACION) {
		P_ID_TIPO_CREACION = pIDTIPOCREACION;
	}
}