package trafico.beans.daos;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class BeanPQTasasDetalle extends BeanPQGeneral {
	// Valores propios de la tasa
	private String P_CODIGO_TASA;
	private String P_TIPO_TASA;
	private BigDecimal P_ID_CONTRATO;
	private BigDecimal P_ID_CONTRATO_SESSION;
	private BigDecimal P_PRECIO;
	private Timestamp P_FECHA_ALTA;
	private Timestamp P_FECHA_ASIGNACION;
	private Timestamp P_FECHA_FIN_VIGENCIA;
	private BigDecimal P_ID_USUARIO;
	private String P_TIPO; // Es un parámetro adicional de entrada del "ASIGNAR" de PQ_TASAS, que se setea a mano desde muchos PL.

//	-- Valores propios del tramite general
	private BigDecimal P_NUM_EXPEDIENTE; 
	private String P_REF_PROPIA;

// -- Valores propios del colegiado que tiene asignada la tasa
	private String P_NUM_COLEGIADO;

	private String W_TASA;
	private BigDecimal P_ID_VEHICULO;

	// Se añade para que no de error por el pq
	private BigDecimal P_IMPORTADO_ICOGAM;

	public BeanPQTasasDetalle() {
		super();
	}

	// GET & SET
	public String getP_CODIGO_TASA() {
		return P_CODIGO_TASA;
	}
	public void setP_CODIGO_TASA(String pCODIGOTASA) {
		P_CODIGO_TASA = pCODIGOTASA;
	}
	public String getP_TIPO_TASA() {
		return P_TIPO_TASA;
	}
	public void setP_TIPO_TASA(String pTIPOTASA) {
		P_TIPO_TASA = pTIPOTASA;
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
	public BigDecimal getP_PRECIO() {
		return P_PRECIO;
	}
	public void setP_PRECIO(BigDecimal pPRECIO) {
		P_PRECIO = pPRECIO;
	}
	public Timestamp getP_FECHA_ALTA() {
		return P_FECHA_ALTA;
	}
	public void setP_FECHA_ALTA(Timestamp pFECHAALTA) {
		P_FECHA_ALTA = pFECHAALTA;
	}
	public Timestamp getP_FECHA_ASIGNACION() {
		return P_FECHA_ASIGNACION;
	}
	public void setP_FECHA_ASIGNACION(Timestamp pFECHAASIGNACION) {
		P_FECHA_ASIGNACION = pFECHAASIGNACION;
	}
	public Timestamp getP_FECHA_FIN_VIGENCIA() {
		return P_FECHA_FIN_VIGENCIA;
	}
	public void setP_FECHA_FIN_VIGENCIA(Timestamp pFECHAFINVIGENCIA) {
		P_FECHA_FIN_VIGENCIA = pFECHAFINVIGENCIA;
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
	public String getP_REF_PROPIA() {
		return P_REF_PROPIA;
	}
	public void setP_REF_PROPIA(String pREFPROPIA) {
		P_REF_PROPIA = pREFPROPIA;
	}	
	public String getP_NUM_COLEGIADO() {
		return P_NUM_COLEGIADO;
	}	
	public void setP_NUM_COLEGIADO(String pNUMCOLEGIADO) {
		P_NUM_COLEGIADO = pNUMCOLEGIADO;
	}
	public String getP_TIPO() {
		return P_TIPO;
	}
	public void setP_TIPO(String pTIPO) {
		P_TIPO = pTIPO;
	}
	public String getW_TASA() {
		return W_TASA;
	}
	public void setW_TASA(String wTASA) {
		W_TASA = wTASA;
	}
	public BigDecimal getP_ID_VEHICULO() {
		return P_ID_VEHICULO;
	}
	public void setP_ID_VEHICULO(BigDecimal pIDVEHICULO) {
		P_ID_VEHICULO = pIDVEHICULO;
	}

	public BigDecimal getP_IMPORTADO_ICOGAM() {
		return P_IMPORTADO_ICOGAM;
	}

	public void setP_IMPORTADO_ICOGAM(BigDecimal p_IMPORTADO_ICOGAM) {
		P_IMPORTADO_ICOGAM = p_IMPORTADO_ICOGAM;
	}
}