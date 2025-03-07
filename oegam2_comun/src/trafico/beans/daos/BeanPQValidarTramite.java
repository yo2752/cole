package trafico.beans.daos;

import java.math.BigDecimal;
import java.sql.Timestamp;


public class BeanPQValidarTramite extends BeanPQGeneral{
	private BigDecimal P_NUM_EXPEDIENTE;
	private BigDecimal P_ESTADO;
	private Timestamp P_FECHA_ULT_MODIF;
	private BigDecimal P_ID_USUARIO;
	private BigDecimal P_ID_CONTRATO_SESSION;
	private String P_REVISADO;
	
	public BeanPQValidarTramite() {
		super();
	}
	
	public BigDecimal getP_NUM_EXPEDIENTE() {
		return P_NUM_EXPEDIENTE;
	}
	public void setP_NUM_EXPEDIENTE(BigDecimal pNUMEXPEDIENTE) {
		P_NUM_EXPEDIENTE = pNUMEXPEDIENTE;
	}
	public BigDecimal getP_ESTADO() {
		return P_ESTADO;
	}
	public void setP_ESTADO(BigDecimal pESTADO) {
		P_ESTADO = pESTADO;
	}
	public Timestamp getP_FECHA_ULT_MODIF() {
		return P_FECHA_ULT_MODIF;
	}
	public void setP_FECHA_ULT_MODIF(Timestamp pFECHAULTMODIF) {
		P_FECHA_ULT_MODIF = pFECHAULTMODIF;
	}
	public BigDecimal getP_ID_USUARIO() {
		return P_ID_USUARIO;
	}
	public void setP_ID_USUARIO(BigDecimal pIDUSUARIO) {
		P_ID_USUARIO = pIDUSUARIO;
	}
	
	public BigDecimal getP_ID_CONTRATO_SESSION() {
		return P_ID_CONTRATO_SESSION;
	}

	public void setP_ID_CONTRATO_SESSION(BigDecimal p_ID_CONTRATO_SESSION) {
		P_ID_CONTRATO_SESSION = p_ID_CONTRATO_SESSION;
	}

	public String getP_REVISADO() {
		return P_REVISADO;
	}

	public void setP_REVISADO(String pREVISADO) {
		P_REVISADO = pREVISADO;
	}
}
