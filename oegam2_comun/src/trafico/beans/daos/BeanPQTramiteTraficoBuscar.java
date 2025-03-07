package trafico.beans.daos;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class BeanPQTramiteTraficoBuscar extends BeanPQGeneral{

	private BigDecimal P_ID_USUARIO;
	private BigDecimal P_ID_CONTRATO;
	private BigDecimal  P_ID_CONTRATO_SESSION;
	private BigDecimal P_NUM_EXPEDIENTE;
	private String P_TIPO_TRAMITE;
	private String P_NUM_COLEGIADO;
	private String P_NIF;
	private String P_MATRICULA;
	private String P_BASTIDOR;
	private String P_NIVE;
	private String P_CODIGO_TASA;
	private BigDecimal P_ESTADO;
	private String P_REF_PROPIA;
	private Timestamp P_FECHA_ALTA_DESDE;
	private Timestamp P_FECHA_ALTA_HASTA;
	private Timestamp P_FECHA_PRESENTACION_DESDE;
	private Timestamp P_FECHA_PRESENTACION_HASTA;
	private Timestamp P_FECHA_ULT_MODIF_DESDE;
	private Timestamp P_FECHA_ULT_MODIF_HASTA;
	private Timestamp P_FECHA_IMPRESION_DESDE;
	private Timestamp P_FECHA_IMPRESION_HASTA;
	private String P_NIF_FACTURACION;
	private String P_TIPO_TASA;
	private BigDecimal P_ID_TIPO_CREACION;

	// Valores para paginación.
	private BigDecimal PAGINA;
	private BigDecimal NUM_REG;
	private String COLUMNA_ORDEN;
	private String ORDEN;
	private BigDecimal CUENTA;
	private BeanPQTramiteBuscarResult C_TRAMITES;

	public BeanPQTramiteTraficoBuscar() {}

	// GET & SET
	public BigDecimal getCUENTA() {
		return CUENTA;
	}	
	public void setCUENTA(BigDecimal cUENTA) {
		CUENTA = cUENTA;
	}
	public BeanPQTramiteBuscarResult getC_TRAMITES() {
		return C_TRAMITES;
	}
	public String getP_TIPO_TASA() {
		return P_TIPO_TASA;
	}
	public void setP_TIPO_TASA(String pTIPOTASA) {
		P_TIPO_TASA = pTIPOTASA;
	}
	public void setC_TRAMITES(BeanPQTramiteBuscarResult cTRAMITES) {
		C_TRAMITES = cTRAMITES;
	}
	public BigDecimal getPAGINA() {
		return PAGINA;
	}
	public void setPAGINA(BigDecimal pAGINA) {
		PAGINA = pAGINA;
	}
	public BigDecimal getNUM_REG() {
		return NUM_REG;
	}
	public void setNUM_REG(BigDecimal nUMREG) {
		NUM_REG = nUMREG;
	}
	public String getCOLUMNA_ORDEN() {
		return COLUMNA_ORDEN;
	}
	public void setCOLUMNA_ORDEN(String cOLUMNAORDEN) {
		COLUMNA_ORDEN = cOLUMNAORDEN;
	}
	public String getORDEN() {
		return ORDEN;
	}
	public void setORDEN(String oRDEN) {
		ORDEN = oRDEN;
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
	public BigDecimal getP_ID_CONTRATO_SESSION() {
		return P_ID_CONTRATO_SESSION;
	}
	public void setP_ID_CONTRATO_SESSION(BigDecimal pIDCONTRATOSESSION) {
		P_ID_CONTRATO_SESSION = pIDCONTRATOSESSION;
	}
	public BigDecimal getP_NUM_EXPEDIENTE() {
		return P_NUM_EXPEDIENTE;
	}
	public void setP_NUM_EXPEDIENTE(BigDecimal pNUMEXPEDIENTE) {
		P_NUM_EXPEDIENTE = pNUMEXPEDIENTE;
	}
	public String getP_TIPO_TRAMITE() {
		return P_TIPO_TRAMITE;
	}
	public void setP_TIPO_TRAMITE(String pTIPOTRAMITE) {
		P_TIPO_TRAMITE = pTIPOTRAMITE;
	}
	public String getP_NUM_COLEGIADO() {
		return P_NUM_COLEGIADO;
	}
	public void setP_NUM_COLEGIADO(String pNUMCOLEGIADO) {
		P_NUM_COLEGIADO = pNUMCOLEGIADO;
	}
	public String getP_NIF() {
		return P_NIF;
	}
	public void setP_NIF(String pNIF) {
		P_NIF = pNIF;
	}
	public String getP_MATRICULA() {
		return P_MATRICULA;
	}
	public void setP_MATRICULA(String pMATRICULA) {
		P_MATRICULA = pMATRICULA;
	}
	public String getP_BASTIDOR() {
		return P_BASTIDOR;
	}
	public void setP_BASTIDOR(String pBASTIDOR) {
		P_BASTIDOR = pBASTIDOR;
	}
	public String getP_NIVE() {
		return P_NIVE;
	}
	public void setP_NIVE(String pNIVE) {
		P_NIVE = pNIVE;
	}
	public String getP_CODIGO_TASA() {
		return P_CODIGO_TASA;
	}
	public void setP_CODIGO_TASA(String pCODIGOTASA) {
		P_CODIGO_TASA = pCODIGOTASA;
	}
	public BigDecimal getP_ESTADO() {
		return P_ESTADO;
	}
	public void setP_ESTADO(BigDecimal pESTADO) {
		P_ESTADO = pESTADO;
	}
	public String getP_REF_PROPIA() {
		return P_REF_PROPIA;
	}
	public void setP_REF_PROPIA(String pREFPROPIA) {
		P_REF_PROPIA = pREFPROPIA;
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
	public Timestamp getP_FECHA_PRESENTACION_DESDE() {
		return P_FECHA_PRESENTACION_DESDE;
	}
	public void setP_FECHA_PRESENTACION_DESDE(Timestamp pFECHAPRESENTACIONDESDE) {
		P_FECHA_PRESENTACION_DESDE = pFECHAPRESENTACIONDESDE;
	}
	public Timestamp getP_FECHA_PRESENTACION_HASTA() {
		return P_FECHA_PRESENTACION_HASTA;
	}
	public void setP_FECHA_PRESENTACION_HASTA(Timestamp pFECHAPRESENTACIONHASTA) {
		P_FECHA_PRESENTACION_HASTA = pFECHAPRESENTACIONHASTA;
	}
	public Timestamp getP_FECHA_ULT_MODIF_DESDE() {
		return P_FECHA_ULT_MODIF_DESDE;
	}
	public void setP_FECHA_ULT_MODIF_DESDE(Timestamp pFECHAULTMODIFDESDE) {
		P_FECHA_ULT_MODIF_DESDE = pFECHAULTMODIFDESDE;
	}
	public Timestamp getP_FECHA_ULT_MODIF_HASTA() {
		return P_FECHA_ULT_MODIF_HASTA;
	}
	public void setP_FECHA_ULT_MODIF_HASTA(Timestamp pFECHAULTMODIFHASTA) {
		P_FECHA_ULT_MODIF_HASTA = pFECHAULTMODIFHASTA;
	}
	public Timestamp getP_FECHA_IMPRESION_DESDE() {
		return P_FECHA_IMPRESION_DESDE;
	}
	public void setP_FECHA_IMPRESION_DESDE(Timestamp pFECHAIMPRESIONDESDE) {
		P_FECHA_IMPRESION_DESDE = pFECHAIMPRESIONDESDE;
	}
	public Timestamp getP_FECHA_IMPRESION_HASTA() {
		return P_FECHA_IMPRESION_HASTA;
	}
	public void setP_FECHA_IMPRESION_HASTA(Timestamp pFECHAIMPRESIONHASTA) {
		P_FECHA_IMPRESION_HASTA = pFECHAIMPRESIONHASTA;
	}
	public String getP_NIF_FACTURACION() {
		return P_NIF_FACTURACION;
	}
	public void setP_NIF_FACTURACION(String pNIFFACTURACION) {
		P_NIF_FACTURACION = pNIFFACTURACION;
	}
	public BigDecimal getP_ID_TIPO_CREACION() {
		return P_ID_TIPO_CREACION;
	}
	public void setP_ID_TIPO_CREACION(BigDecimal pIDTIPOCREACION) {
		P_ID_TIPO_CREACION = pIDTIPOCREACION;
	}
}