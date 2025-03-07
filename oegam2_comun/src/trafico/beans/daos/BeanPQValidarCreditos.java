package trafico.beans.daos;

import java.math.BigDecimal;

/*********************************************************************************************************
PROCEDURE: VALIDAR_CREDITOS
PARAMETROS:
    DESCRIPCION:
*****************************************************************************************************
                   
PROCEDURE VALIDAR_CREDITOS (P_NUM_COLEGIADO IN CREDITOS.NUM_COLEGIADO%TYPE,
                          P_TIPO_TRAMITE IN TIPO_TRAMITE.TIPO_TRAMITE%TYPE,
                          NUMERO IN NUMBER,
                           P_CREDITOS OUT NUMBER,
                           P_CODE OUT NUMBER,
                           P_SQLERRM OUT VARCHAR2);
*****************************************************************************************************/   

public class BeanPQValidarCreditos extends BeanPQGeneral{

	private BigDecimal P_ID_CONTRATO;
	private String P_TIPO_TRAMITE;
	private BigDecimal NUMERO;
	private BigDecimal P_CREDITOS;
	private BigDecimal P_CREDITOS_DISPONIBLES;

	public BeanPQValidarCreditos() {
	}

	public BigDecimal getP_ID_CONTRATO() {
		return P_ID_CONTRATO;
	}

	public void setP_ID_CONTRATO(BigDecimal pIDCONTRATO) {
		P_ID_CONTRATO = pIDCONTRATO;
	}

	public String getP_TIPO_TRAMITE() {
		return P_TIPO_TRAMITE;
	}

	public void setP_TIPO_TRAMITE(String pTIPOTRAMITE) {
		P_TIPO_TRAMITE = pTIPOTRAMITE;
	}

	public BigDecimal getNUMERO() {
		return NUMERO;
	}

	public void setNUMERO(BigDecimal nUMERO) {
		NUMERO = nUMERO;
	}

	public BigDecimal getP_CREDITOS() {
		return P_CREDITOS;
	}

	public void setP_CREDITOS(BigDecimal pCREDITOS) {
		P_CREDITOS = pCREDITOS;
	}

	public BigDecimal getP_CREDITOS_DISPONIBLES() {
		return P_CREDITOS_DISPONIBLES;
	}

	public void setP_CREDITOS_DISPONIBLES(BigDecimal pCREDITOS_DISPONIBLES) {
		P_CREDITOS_DISPONIBLES = pCREDITOS_DISPONIBLES;
	}

}