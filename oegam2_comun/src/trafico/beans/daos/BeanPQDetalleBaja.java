package trafico.beans.daos;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class BeanPQDetalleBaja extends BeanPQGeneral{

	// VALORES PROPIOS DEL TRAMITE GENERAL
	private BigDecimal  P_NUM_EXPEDIENTE;
	private BigDecimal  P_ID_CONTRATO;
	private BigDecimal  P_ID_CONTRATO_SESSION;
	private String  P_NUM_COLEGIADO;
	private BigDecimal  P_ID_VEHICULO; 
	private String  P_CODIGO_TASA;
	private BigDecimal P_ESTADO;
	private String  P_REF_PROPIA;
	private Timestamp  P_FECHA_ALTA; 
	private Timestamp  P_FECHA_PRESENTACION; 
	private Timestamp  P_FECHA_ULT_MODIF;
    private Timestamp  P_FECHA_IMPRESION ;
    private String  P_JEFATURA_PROVINCIAL;
    private String  P_ANOTACIONES;
    
    // VALORES PROPIOS DE LA BAJA
    private String P_MOTIVO_BAJA;
    private String P_PERMISO_CIRCU;
    private String P_DECLARACION_RESIDUO;
    private String P_DNI_COTITULARES;//(SITEX )
    private String P_TARJETA_INSPECCION;
    private String P_BAJA_IMP_MUNICIPAL;
    private String P_JUSTIFICANTE_DENUNCIA;
    private String P_PROPIEDAD_DESGUACE;
    private String P_PAGO_IMP_MUNICIPAL;
    private String P_CEMA;
    
    private BigDecimal P_SIMULTANEA;
    private BigDecimal P_ID_TIPO_CREACION;
    private String P_TASA_DUPLICADO;
	private String P_IMPR_PERMISO_CIRCU;
	private String P_RESPUESTA;
	private String P_TIPO_TASA;
	private String P_DECLARACION_NO_ENTREGA_CATV;
	private String P_CARTA_DGT_VEHICULO_MAS_DIEZ;
	private String P_CERTIFICADO_MEDIOAMBIENTAL;
    
    // ------------------------- GET & SET -------------------------
	public BeanPQDetalleBaja() { }

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
	public String getP_MOTIVO_BAJA() {
		return P_MOTIVO_BAJA;
	}
	public void setP_MOTIVO_BAJA(String pMOTIVOBAJA) {
		P_MOTIVO_BAJA = pMOTIVOBAJA;
	}
	public String getP_PERMISO_CIRCU() {
		return P_PERMISO_CIRCU;
	}
	public void setP_PERMISO_CIRCU(String pPERMISOCIRCU) {
		P_PERMISO_CIRCU = pPERMISOCIRCU;
	}
	public String getP_DECLARACION_RESIDUO() {
		return P_DECLARACION_RESIDUO;
	}
	public void setP_DECLARACION_RESIDUO(String pDECLARACIONRESIDUO) {
		P_DECLARACION_RESIDUO = pDECLARACIONRESIDUO;
	}	
	public String getP_DNI_COTITULARES() {
		return P_DNI_COTITULARES;
	}
	public void setP_DNI_COTITULARES(String pDNICOTITULARES) {
		P_DNI_COTITULARES = pDNICOTITULARES;
	}	
	public String getP_TARJETA_INSPECCION() {
		return P_TARJETA_INSPECCION;
	}
	public void setP_TARJETA_INSPECCION(String pTARJETAINSPECCION) {
		P_TARJETA_INSPECCION = pTARJETAINSPECCION;
	}
	public String getP_BAJA_IMP_MUNICIPAL() {
		return P_BAJA_IMP_MUNICIPAL;
	}
	public void setP_BAJA_IMP_MUNICIPAL(String pBAJAIMPMUNICIPAL) {
		P_BAJA_IMP_MUNICIPAL = pBAJAIMPMUNICIPAL;
	}
	public String getP_JUSTIFICANTE_DENUNCIA() {
		return P_JUSTIFICANTE_DENUNCIA;
	}
	public void setP_JUSTIFICANTE_DENUNCIA(String pJUSTIFICANTEDENUNCIA) {
		P_JUSTIFICANTE_DENUNCIA = pJUSTIFICANTEDENUNCIA;
	}
	public String getP_PROPIEDAD_DESGUACE() {
		return P_PROPIEDAD_DESGUACE;
	}
	public void setP_PROPIEDAD_DESGUACE(String pPROPIEDADDESGUACE) {
		P_PROPIEDAD_DESGUACE = pPROPIEDADDESGUACE;
	}
	public String getP_PAGO_IMP_MUNICIPAL() {
		return P_PAGO_IMP_MUNICIPAL;
	}
	public void setP_PAGO_IMP_MUNICIPAL(String pPAGOIMPMUNICIPAL) {
		P_PAGO_IMP_MUNICIPAL = pPAGOIMPMUNICIPAL;
	}
	public String getP_CEMA() {
		return P_CEMA;
	}
	public void setP_CEMA(String pCEMA) {
		P_CEMA = pCEMA;
	}
	public BigDecimal getP_SIMULTANEA() {
		return P_SIMULTANEA;
	}
	public void setP_SIMULTANEA(BigDecimal pSIMULTANEA) {
		P_SIMULTANEA = pSIMULTANEA;
	}
	public BigDecimal getP_ID_TIPO_CREACION() {
		return P_ID_TIPO_CREACION;
	}
	public void setP_ID_TIPO_CREACION(BigDecimal pIDTIPOCREACION) {
		P_ID_TIPO_CREACION = pIDTIPOCREACION;
	}
	public String getP_TASA_DUPLICADO() {
		return P_TASA_DUPLICADO;
	}
	public void setP_TASA_DUPLICADO(String pTASADUPLICADO) {
		P_TASA_DUPLICADO = pTASADUPLICADO;
	}
	public String getP_IMPR_PERMISO_CIRCU() {
		return P_IMPR_PERMISO_CIRCU;
	}
	public void setP_IMPR_PERMISO_CIRCU(String pIMPRPERMISOCIRCU) {
		P_IMPR_PERMISO_CIRCU = pIMPRPERMISOCIRCU;
	}
	public String getP_RESPUESTA() {
		return P_RESPUESTA;
	}
	public void setP_RESPUESTA(String pRESPUESTA) {
		P_RESPUESTA = pRESPUESTA;
	}
	public String getP_TIPO_TASA() {
		return P_TIPO_TASA;
	}
	public void setP_TIPO_TASA(String pTIPOTASA) {
		P_TIPO_TASA = pTIPOTASA;
	}

	public String getP_DECLARACION_NO_ENTREGA_CATV() {
		return P_DECLARACION_NO_ENTREGA_CATV;
	}

	public void setP_DECLARACION_NO_ENTREGA_CATV(
			String p_DECLARACION_NO_ENTREGA_CATV) {
		P_DECLARACION_NO_ENTREGA_CATV = p_DECLARACION_NO_ENTREGA_CATV;
	}

	public String getP_CARTA_DGT_VEHICULO_MAS_DIEZ() {
		return P_CARTA_DGT_VEHICULO_MAS_DIEZ;
	}

	public void setP_CARTA_DGT_VEHICULO_MAS_DIEZ(
			String p_CARTA_DGT_VEHICULO_MAS_DIEZ) {
		P_CARTA_DGT_VEHICULO_MAS_DIEZ = p_CARTA_DGT_VEHICULO_MAS_DIEZ;
	}

	public String getP_CERTIFICADO_MEDIOAMBIENTAL() {
		return P_CERTIFICADO_MEDIOAMBIENTAL;
	}

	public void setP_CERTIFICADO_MEDIOAMBIENTAL(String p_CERTIFICADO_MEDIOAMBIENTAL) {
		P_CERTIFICADO_MEDIOAMBIENTAL = p_CERTIFICADO_MEDIOAMBIENTAL;
	}
}