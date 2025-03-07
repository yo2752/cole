package trafico.beans.daos;

import java.math.BigDecimal;

public class BeanPQTasasDesasignar extends BeanPQGeneral{

//	-- Valores propios de la tasa
	private String P_NUM_COLEGIADO;
	private BigDecimal P_ID_CONTRATO;
	private BigDecimal P_ID_CONTRATO_SESSION;
	private BigDecimal P_ID_USUARIO;
	private String P_CODIGO_TASA;

	public BeanPQTasasDesasignar() {
		super();
	}

	public String getP_CODIGO_TASA() {
		return P_CODIGO_TASA;
	}
	public void setP_CODIGO_TASA(String pCODIGOTASA) {
		P_CODIGO_TASA = pCODIGOTASA;
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
	public String getP_NUM_COLEGIADO() {
		return P_NUM_COLEGIADO;
	}
	public void setP_NUM_COLEGIADO(String pNUMCOLEGIADO) {
		P_NUM_COLEGIADO = pNUMCOLEGIADO;
	}
	public BigDecimal getP_ID_USUARIO() {
		return P_ID_USUARIO;
	}
	public void setP_ID_USUARIO(BigDecimal pIDUSUARIO) {
		P_ID_USUARIO = pIDUSUARIO;
	}
}