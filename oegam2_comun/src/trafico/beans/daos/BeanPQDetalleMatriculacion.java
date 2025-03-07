package trafico.beans.daos;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class BeanPQDetalleMatriculacion extends BeanPQGeneral {

	// -- VALORES PROPIOS DEL TRAMITE GENERAL
	private BigDecimal P_NUM_EXPEDIENTE;
	private BigDecimal P_ID_CONTRATO;
	private BigDecimal  P_ID_CONTRATO_SESSION;
	private String P_NUM_COLEGIADO;
	private BigDecimal P_ID_VEHICULO;
	private String P_CODIGO_TASA;
	private String P_TIPO_TASA;
	private BigDecimal P_ESTADO;
	private String P_REF_PROPIA;
	private Timestamp P_FECHA_ALTA;
	private Timestamp P_FECHA_PRESENTACION;
	private Timestamp P_FECHA_ULT_MODIF;
	private Timestamp P_FECHA_IMPRESION;
	private String P_JEFATURA_PROVINCIAL;
	private String P_ANOTACIONES;
	private String P_RENTING;
	private String P_CAMBIO_DOMICILIO;
	private String P_IEDTM;
	private String P_MODELO_IEDTM;
	private Timestamp P_FECHA_IEDTM;
	private String P_N_REG_IEDTM;
	private String P_FINANCIERA_IEDTM;
	private String P_EXENTO_IEDTM;
	private String P_NO_SUJECION_IEDTM;
	private String P_CEM;
	/*private String P_CEMU;
	private String P_SATISFECHO_CET;*/
	private String P_RESPUESTA;
	// -- VALORES PROPIOS DE MATRICULACIÓN
	private BigDecimal P_BASE_IMPONIBLE_576;
	private String P_REDUCCION_576;
	private BigDecimal P_BASE_IMPO_REDUCIDA_576;
	private BigDecimal P_TIPO_GRAVAMEN_576;
	private BigDecimal P_CUOTA_576;
	private BigDecimal P_DEDUCCION_LINEAL_576;
	private BigDecimal P_CUOTA_INGRESAR_576;
	private BigDecimal P_A_DEDUCIR_576;
	private BigDecimal P_LIQUIDACION_576;
	private String P_N_DECLARACION_COMP_576;
	private BigDecimal P_EJERCICIO_576;
	private String P_CAUSA_HECHO_IMPON_576;
	private String P_OBSERVACIONES_576;
	private String P_EXENTO_576;
	private String P_NRC_576;
	private Timestamp P_FECHA_PAGO_576;
	private BigDecimal P_IMPORTE_576;
	private String P_ID_REDUCCION_05;
	private String P_ID_NO_SUJECCION_06;
	private String P_EXENTO_CEM;
	private String P_CEMA;
	private String P_ENTREGA_FACT_MATRICULACION;
	private BigDecimal P_ID_TIPO_CREACION;
	private String P_TIPO_TRAMITE_MATR;
	private String P_JUSTIFICADO_IVTM;
    private String P_RESPUESTA_EITV;
    private String P_CARSHARING;
    private String P_PERMISO;
    private String P_DISTINTIVO;
    private String P_CHECK_JUSTIF_IVTM;
	
	public BigDecimal getP_IMPORTE_576() {
		return P_IMPORTE_576;
	}

	public void setP_IMPORTE_576(BigDecimal pIMPORTE_576) {
		P_IMPORTE_576 = pIMPORTE_576;
	}

	public BigDecimal getP_NUM_EXPEDIENTE() {
		return P_NUM_EXPEDIENTE;
	}

	public void setP_NUM_EXPEDIENTE(BigDecimal pNUMEXPEDIENTE) {
		P_NUM_EXPEDIENTE = pNUMEXPEDIENTE;
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

	public BigDecimal getP_ID_VEHICULO() {
		return P_ID_VEHICULO;
	}

	public void setP_ID_VEHICULO(BigDecimal pIDVEHICULO) {
		P_ID_VEHICULO = pIDVEHICULO;
	}

	public String getP_TIPO_TASA() {
		return P_TIPO_TASA;
	}

	public void setP_TIPO_TASA(String pTIPOTASA) {
		P_TIPO_TASA = pTIPOTASA;
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

	public Timestamp getP_FECHA_ALTA() {
		return P_FECHA_ALTA;
	}

	public void setP_FECHA_ALTA(Timestamp pFECHAALTA) {
		P_FECHA_ALTA = pFECHAALTA;
	}

	public Timestamp getP_FECHA_PRESENTACION() {
		return P_FECHA_PRESENTACION;
	}

	public void setP_FECHA_PRESENTACION(Timestamp pFECHAPRESENTACION) {
		P_FECHA_PRESENTACION = pFECHAPRESENTACION;
	}

	public Timestamp getP_FECHA_ULT_MODIF() {
		return P_FECHA_ULT_MODIF;
	}

	public void setP_FECHA_ULT_MODIF(Timestamp pFECHAULTMODIF) {
		P_FECHA_ULT_MODIF = pFECHAULTMODIF;
	}

	public Timestamp getP_FECHA_IMPRESION() {
		return P_FECHA_IMPRESION;
	}

	public void setP_FECHA_IMPRESION(Timestamp pFECHAIMPRESION) {
		P_FECHA_IMPRESION = pFECHAIMPRESION;
	}

	public String getP_JEFATURA_PROVINCIAL() {
		return P_JEFATURA_PROVINCIAL;
	}

	public void setP_JEFATURA_PROVINCIAL(String pJEFATURA_PROVINCIAL) {
		P_JEFATURA_PROVINCIAL = pJEFATURA_PROVINCIAL;
	}

	public String getP_ANOTACIONES() {
		return P_ANOTACIONES;
	}

	public void setP_ANOTACIONES(String pANOTACIONES) {
		P_ANOTACIONES = pANOTACIONES;
	}

	public String getP_RENTING() {
		return P_RENTING;
	}

	public void setP_RENTING(String pRENTING) {
		P_RENTING = pRENTING;
	}

	public String getP_CAMBIO_DOMICILIO() {
		return P_CAMBIO_DOMICILIO;
	}

	public void setP_CAMBIO_DOMICILIO(String pCAMBIODOMICILIO) {
		P_CAMBIO_DOMICILIO = pCAMBIODOMICILIO;
	}

	public String getP_IEDTM() {
		return P_IEDTM;
	}

	public void setP_IEDTM(String pIEDTM) {
		P_IEDTM = pIEDTM;
	}

	public String getP_MODELO_IEDTM() {
		return P_MODELO_IEDTM;
	}

	public void setP_MODELO_IEDTM(String pMODELOIEDTM) {
		P_MODELO_IEDTM = pMODELOIEDTM;
	}

	public Timestamp getP_FECHA_IEDTM() {
		return P_FECHA_IEDTM;
	}

	public void setP_FECHA_IEDTM(Timestamp pFECHAIEDTM) {
		P_FECHA_IEDTM = pFECHAIEDTM;
	}

	public String getP_N_REG_IEDTM() {
		return P_N_REG_IEDTM;
	}

	public void setP_N_REG_IEDTM(String pNREGIEDTM) {
		P_N_REG_IEDTM = pNREGIEDTM;
	}

	public String getP_FINANCIERA_IEDTM() {
		return P_FINANCIERA_IEDTM;
	}

	public void setP_FINANCIERA_IEDTM(String pFINANCIERAIEDTM) {
		P_FINANCIERA_IEDTM = pFINANCIERAIEDTM;
	}

	public String getP_EXENTO_IEDTM() {
		return P_EXENTO_IEDTM;
	}

	public void setP_EXENTO_IEDTM(String pEXENTOIEDTM) {
		P_EXENTO_IEDTM = pEXENTOIEDTM;
	}

	public String getP_NO_SUJECION_IEDTM() {
		return P_NO_SUJECION_IEDTM;
	}

	public void setP_NO_SUJECION_IEDTM(String pNOSUJECIONIEDTM) {
		P_NO_SUJECION_IEDTM = pNOSUJECIONIEDTM;
	}

	public String getP_CEM() {
		return P_CEM;
	}

	public void setP_CEM(String pCEM) {
		P_CEM = pCEM;
	}
	
	/*public String getP_CEMU() {
		return P_CEMU;
	}
	
	public void setP_CEMU(String pCEMU) {
		P_CEMU = pCEMU;
	}
	
	public String getP_SATISFECHO_CET() {
		return P_SATISFECHO_CET;
	}
	
	public void setP_SATISFECHO_CET(String pSATISFECHOCET) {
		P_SATISFECHO_CET = pSATISFECHOCET;
	}*/

	public String getP_RESPUESTA() {
		return P_RESPUESTA;
	}

	public void setP_RESPUESTA(String pRESPUESTA) {
		P_RESPUESTA = pRESPUESTA;
	}

	public BigDecimal getP_BASE_IMPONIBLE_576() {
		return P_BASE_IMPONIBLE_576;
	}

	public void setP_BASE_IMPONIBLE_576(BigDecimal pBASEIMPONIBLE_576) {
		P_BASE_IMPONIBLE_576 = pBASEIMPONIBLE_576;
	}

	public String getP_REDUCCION_576() {
		return P_REDUCCION_576;
	}

	public void setP_REDUCCION_576(String pREDUCCION_576) {
		P_REDUCCION_576 = pREDUCCION_576;
	}

	public BigDecimal getP_BASE_IMPO_REDUCIDA_576() {
		return P_BASE_IMPO_REDUCIDA_576;
	}

	public void setP_BASE_IMPO_REDUCIDA_576(BigDecimal pBASEIMPOREDUCIDA_576) {
		P_BASE_IMPO_REDUCIDA_576 = pBASEIMPOREDUCIDA_576;
	}

	public BigDecimal getP_TIPO_GRAVAMEN_576() {
		return P_TIPO_GRAVAMEN_576;
	}

	public void setP_TIPO_GRAVAMEN_576(BigDecimal pTIPOGRAVAMEN_576) {
		P_TIPO_GRAVAMEN_576 = pTIPOGRAVAMEN_576;
	}

	public BigDecimal getP_CUOTA_576() {
		return P_CUOTA_576;
	}

	public void setP_CUOTA_576(BigDecimal pCUOTA_576) {
		P_CUOTA_576 = pCUOTA_576;
	}

	public BigDecimal getP_DEDUCCION_LINEAL_576() {
		return P_DEDUCCION_LINEAL_576;
	}

	public void setP_DEDUCCION_LINEAL_576(BigDecimal pDEDUCCIONLINEAL_576) {
		P_DEDUCCION_LINEAL_576 = pDEDUCCIONLINEAL_576;
	}

	public BigDecimal getP_CUOTA_INGRESAR_576() {
		return P_CUOTA_INGRESAR_576;
	}

	public void setP_CUOTA_INGRESAR_576(BigDecimal pCUOTAINGRESAR_576) {
		P_CUOTA_INGRESAR_576 = pCUOTAINGRESAR_576;
	}

	public BigDecimal getP_A_DEDUCIR_576() {
		return P_A_DEDUCIR_576;
	}

	public void setP_A_DEDUCIR_576(BigDecimal pADEDUCIR_576) {
		P_A_DEDUCIR_576 = pADEDUCIR_576;
	}

	public BigDecimal getP_LIQUIDACION_576() {
		return P_LIQUIDACION_576;
	}

	public void setP_LIQUIDACION_576(BigDecimal pLIQUIDACION_576) {
		P_LIQUIDACION_576 = pLIQUIDACION_576;
	}

	public String getP_N_DECLARACION_COMP_576() {
		return P_N_DECLARACION_COMP_576;
	}

	public void setP_N_DECLARACION_COMP_576(String pNDECLARACIONCOMP_576) {
		P_N_DECLARACION_COMP_576 = pNDECLARACIONCOMP_576;
	}

	public BigDecimal getP_EJERCICIO_576() {
		return P_EJERCICIO_576;
	}

	public void setP_EJERCICIO_576(BigDecimal pEJERCICIO_576) {
		P_EJERCICIO_576 = pEJERCICIO_576;
	}

	public String getP_CAUSA_HECHO_IMPON_576() {
		return P_CAUSA_HECHO_IMPON_576;
	}

	public void setP_CAUSA_HECHO_IMPON_576(String pCAUSAHECHOIMPON_576) {
		P_CAUSA_HECHO_IMPON_576 = pCAUSAHECHOIMPON_576;
	}

	public String getP_OBSERVACIONES_576() {
		return P_OBSERVACIONES_576;
	}

	public void setP_OBSERVACIONES_576(String pOBSERVACIONES_576) {
		P_OBSERVACIONES_576 = pOBSERVACIONES_576;
	}

	public String getP_EXENTO_576() {
		return P_EXENTO_576;
	}

	public void setP_EXENTO_576(String pEXENTO_576) {
		P_EXENTO_576 = pEXENTO_576;
	}

	public String getP_NRC_576() {
		return P_NRC_576;
	}

	public void setP_NRC_576(String pNRC_576) {
		P_NRC_576 = pNRC_576;
	}

	public Timestamp getP_FECHA_PAGO_576() {
		return P_FECHA_PAGO_576;
	}

	public void setP_FECHA_PAGO_576(Timestamp pFECHAPAGO_576) {
		P_FECHA_PAGO_576 = pFECHAPAGO_576;
	}

	public String getP_ID_REDUCCION_05() {
		return P_ID_REDUCCION_05;
	}

	public void setP_ID_REDUCCION_05(String pIDREDUCCION_05) {
		P_ID_REDUCCION_05 = pIDREDUCCION_05;
	}

	public String getP_ID_NO_SUJECCION_06() {
		return P_ID_NO_SUJECCION_06;
	}

	public void setP_ID_NO_SUJECCION_06(String pIDNOSUJECCION_06) {
		P_ID_NO_SUJECCION_06 = pIDNOSUJECCION_06;
	}

	public String getP_EXENTO_CEM() {
		return P_EXENTO_CEM;
	}

	public void setP_EXENTO_CEM(String pEXENTOCEM) {
		P_EXENTO_CEM = pEXENTOCEM;
	}

	public String getP_CEMA() {
		return P_CEMA;
	}

	public void setP_CEMA(String pCEMA) {
		P_CEMA = pCEMA;
	}

	public String getP_ENTREGA_FACT_MATRICULACION() {
		return P_ENTREGA_FACT_MATRICULACION;
	}

	public void setP_ENTREGA_FACT_MATRICULACION(String p_ENTREGA_FACT_MATRICULACION) {
		P_ENTREGA_FACT_MATRICULACION = p_ENTREGA_FACT_MATRICULACION;
	}

	public BigDecimal getP_ID_TIPO_CREACION() {
		return P_ID_TIPO_CREACION;
	}
	
	public void setP_ID_TIPO_CREACION(BigDecimal pIDTIPOCREACION) {
		P_ID_TIPO_CREACION = pIDTIPOCREACION;
	}
	
	public String getP_TIPO_TRAMITE_MATR() {
		return P_TIPO_TRAMITE_MATR;
	}
	
	public void setP_TIPO_TRAMITE_MATR(String pTIPOTRAMITEMATR) {
		P_TIPO_TRAMITE_MATR = pTIPOTRAMITEMATR;
	}
	
	public String getP_JUSTIFICADO_IVTM() {
		return P_JUSTIFICADO_IVTM;
	}
	public void setP_JUSTIFICADO_IVTM(String p_JUSTIFICADO_IVTM) {
		P_JUSTIFICADO_IVTM = p_JUSTIFICADO_IVTM;
	}

	public String getP_RESPUESTA_EITV() {
		return P_RESPUESTA_EITV;
	}

	public void setP_RESPUESTA_EITV(String p_RESPUESTA_EITV) {
		P_RESPUESTA_EITV = p_RESPUESTA_EITV;
	}

	public String getP_CARSHARING() {
		return P_CARSHARING;
	}

	public void setP_CARSHARING(String p_CARSHARING) {
		P_CARSHARING = p_CARSHARING;
	}

	public String getP_PERMISO() {
		return P_PERMISO;
	}

	public void setP_PERMISO(String p_PERMISO) {
		P_PERMISO = p_PERMISO;
	}

	public String getP_DISTINTIVO() {
		return P_DISTINTIVO;
	}

	public void setP_DISTINTIVO(String p_DISTINTIVO) {
		P_DISTINTIVO = p_DISTINTIVO;
	}
	
	public String getP_CHECK_JUSTIF_IVTM() {
		return P_CHECK_JUSTIF_IVTM;
	}

	public void setP_CHECK_JUSTIF_IVTM(String p_CHECK_JUSTIF_IVTM) {
		P_CHECK_JUSTIF_IVTM = p_CHECK_JUSTIF_IVTM;
	}
	
}