package trafico.beans.daos;

import java.math.BigDecimal;

public class BeanPQIntervinientes extends BeanPQLista{

	private BigDecimal  P_NUM_EXPEDIENTE;
	private BigDecimal  P_ID_CONTRATO_SESSION;
	private String  P_NUM_COLEGIADO;
	private BigDecimal  P_ID_VEHICULO; 
	private IntervinientesCursor C_INTERVINIENTES;
    
	public BeanPQIntervinientes() {
	}

	public BigDecimal getP_NUM_EXPEDIENTE() {
		return P_NUM_EXPEDIENTE;
	}

	public void setP_NUM_EXPEDIENTE(BigDecimal pNUMEXPEDIENTE) {
		P_NUM_EXPEDIENTE = pNUMEXPEDIENTE;
	}

	public BigDecimal getP_ID_CONTRATO_SESSION() {
		return P_ID_CONTRATO_SESSION;
	}

	public void setP_ID_CONTRATO_SESSION(BigDecimal pIDCONTRATOSESSION) {
		P_ID_CONTRATO_SESSION = pIDCONTRATOSESSION;
	}

	public String getP_NUM_COLEGIADO() {
		return P_NUM_COLEGIADO;
	}

	public void setP_NUM_COLEGIADO(String pNUMCOLEGIADO) {
		P_NUM_COLEGIADO = pNUMCOLEGIADO;
	}

	public BigDecimal getP_ID_VEHICULO() {
		return P_ID_VEHICULO;
	}

	public void setP_ID_VEHICULO(BigDecimal pIDVEHICULO) {
		P_ID_VEHICULO = pIDVEHICULO;
	}

	public IntervinientesCursor getC_INTERVINIENTES() {
		return C_INTERVINIENTES;
	}

	public void setC_INTERVINIENTES(IntervinientesCursor cINTERVINIENTES) {
		C_INTERVINIENTES = cINTERVINIENTES;
	}

	
	
	
}
