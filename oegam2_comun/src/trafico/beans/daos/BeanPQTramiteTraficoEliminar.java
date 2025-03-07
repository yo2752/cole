package trafico.beans.daos;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Bean que contendrá los campos que le vamos a pasar al modelo
 * @author juan.gomez
 *
 */
/*----------- CAMB_ESTADO-------------------------------------------------------------------

PROCEDURE CAMB_ESTADO (ROWID_TRAMITE IN OUT UROWID,
                P_NUM_EXPEDIENTE IN OUT TRAMITE_TRAFICO.NUM_EXPEDIENTE%TYPE,
                P_ESTADO IN OUT TRAMITE_TRAFICO.ESTADO%TYPE,
                P_RESPUESTA IN OUT TRAMITE_TRAFICO.RESPUESTA%TYPE,
                P_FECHA_ULT_MODIF IN OUT TRAMITE_TRAFICO.FECHA_ULT_MODIF%TYPE,
                P_ID_USUARIO IN OUT TRAMITE_TRAFICO.ID_USUARIO%TYPE,
                P_INFORMACION OUT VARCHAR2,
                P_CODE OUT NUMBER,
                P_SQLERRM OUT VARCHAR2)

**/

public class BeanPQTramiteTraficoEliminar extends BeanPQGeneral{
		
	private BigDecimal P_NUM_EXPEDIENTE;
	private BigDecimal P_ESTADO;
	private String P_RESPUESTA;
	private Timestamp P_FECHA_ULT_MODIF;
	private BigDecimal P_ID_USUARIO;
	
	public BeanPQTramiteTraficoEliminar() {
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

	public String getP_RESPUESTA() {
		return P_RESPUESTA;
	}

	public void setP_RESPUESTA(String pRESPUESTA) {
		P_RESPUESTA = pRESPUESTA;
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

	
	
}
