package trafico.beans.daos;

import java.math.BigDecimal;
import java.sql.Timestamp;


public class BeanPQSolicitudDatosGuardar extends BeanPQGeneral{

	
	private BigDecimal P_ID_USUARIO;
	private BigDecimal P_ID_CONTRATO_SESSION;
	private BigDecimal P_NUM_EXPEDIENTE;
	private BigDecimal P_ID_CONTRATO;
	private String P_NUM_COLEGIADO;
	private BigDecimal P_ESTADO;
	private String P_REF_PROPIA;
	private Timestamp P_FECHA_ALTA;
	private Timestamp P_FECHA_PRESENTACION;
	private Timestamp P_FECHA_ULT_MODIF;
	private Timestamp P_FECHA_IMPRESION;
	private String P_ANOTACIONES;
	private BigDecimal P_ID_TIPO_CREACION;
	
	public BeanPQSolicitudDatosGuardar() {
	}
	
	public BigDecimal getP_ID_USUARIO() {
		return P_ID_USUARIO;
	}
	public void setP_ID_USUARIO(BigDecimal pIDUSUARIO) {
		P_ID_USUARIO = pIDUSUARIO;
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
	public String getP_ANOTACIONES() {
		return P_ANOTACIONES;
	}
	public void setP_ANOTACIONES(String pANOTACIONES) {
		P_ANOTACIONES = pANOTACIONES;
	}
	
	public String getP_REF_PROPIA() {
		return P_REF_PROPIA;
	}
	public void setP_REF_PROPIA(String pREFPROPIA) {
		P_REF_PROPIA = pREFPROPIA;
	}
	public BigDecimal getP_ESTADO() {
		return P_ESTADO;
	}
	public void setP_ESTADO(BigDecimal pESTADO) {
		P_ESTADO = pESTADO;
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

	public BigDecimal getP_ID_TIPO_CREACION() {
		return P_ID_TIPO_CREACION;
	}

	public void setP_ID_TIPO_CREACION(BigDecimal pIDTIPOCREACION) {
		P_ID_TIPO_CREACION = pIDTIPOCREACION;
	}

}
