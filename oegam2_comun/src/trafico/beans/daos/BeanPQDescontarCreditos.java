package trafico.beans.daos;

import java.math.BigDecimal;

/*********************************************************************************************************
PROCEDURE: DECONTAR_CREDITOS
PARAMETROS: 
    DESCRIPCION:
*****************************************************************************************************
                   
PROCEDURE DECONTAR_CREDITOS (P_ID_USUARIO IN USUARIO.ID_USUARIO%TYPE,
                          P_NUM_COLEGIADO IN CREDITOS.NUM_COLEGIADO%TYPE,
                          P_TIPO_TRAMITE IN TIPO_TRAMITE.TIPO_TRAMITE%TYPE,
                          NUMERO IN NUMBER,
                          P_CREDITOS OUT NUMBER,
                          P_CODE OUT NUMBER,
                          P_SQLERRM OUT VARCHAR2); 
*****************************************************************************************************/                            

public class BeanPQDescontarCreditos extends BeanPQGeneral{

	private BigDecimal P_ID_USUARIO;
	private BigDecimal P_ID_CONTRATO;
    private String P_TIPO_TRAMITE;
	private BigDecimal NUMERO;
	private BigDecimal P_CREDITOS;
	
	public BeanPQDescontarCreditos() {
	
	}

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

	
	
	
}
