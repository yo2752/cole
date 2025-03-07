package trafico.beans.daos;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class BeanPQTasasResumen extends BeanPQGeneral{
	private String P_NUM_COLEGIADO;
	private BigDecimal P_ID_CONTRATO;
	private BigDecimal P_ID_CONTRATO_SESSION;
	private BigDecimal P_ID_USUARIO;
	private String P_TIPO_TASA;
	private Timestamp P_FECHA_ALTA_DESDE;
	private Timestamp P_FECHA_ALTA_HASTA;
	private BeanPQTasasResumenResultado C_TASAS;

	public BeanPQTasasResumen() {
		super();
	}
	public String getP_NUM_COLEGIADO() {
		return P_NUM_COLEGIADO;
	}
	public void setP_NUM_COLEGIADO(String pNUMCOLEGIADO) {
		P_NUM_COLEGIADO = pNUMCOLEGIADO;
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
	public BigDecimal getP_ID_USUARIO() {
		return P_ID_USUARIO;
	}
	public void setP_ID_USUARIO(BigDecimal pIDUSUARIO) {
		P_ID_USUARIO = pIDUSUARIO;
	}
	public String getP_TIPO_TASA() {
		return P_TIPO_TASA;
	}
	public void setP_TIPO_TASA(String pTIPOTASA) {
		P_TIPO_TASA = pTIPOTASA;
	}
	public Timestamp getP_FECHA_ALTA_DESDE() {
		return P_FECHA_ALTA_DESDE;
	}
	public void setP_FECHA_ALTA_DESDE(Timestamp pFECHAALTADESDE) {
		P_FECHA_ALTA_DESDE = pFECHAALTADESDE;
	}
	public Timestamp getP_FECHA_ALTA_HASTA() {
		return P_FECHA_ALTA_HASTA;
	}
	public void setP_FECHA_ALTA_HASTA(Timestamp pFECHAALTAHASTA) {
		P_FECHA_ALTA_HASTA = pFECHAALTAHASTA;
	}
	public BeanPQTasasResumenResultado getC_TASAS() {
		return C_TASAS;
	}
	public void setC_TASAS(BeanPQTasasResumenResultado cTASAS) {
		C_TASAS = cTASAS;
	}
}