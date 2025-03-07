package trafico.beans.daos;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class BeanPQTasasBuscar extends BeanPQGeneral{

//	-- Valores propios de la tasa
	private String CODIGO_TASA;
	private String TIPO_TASA;
	private BigDecimal ID_CONTRATO;
	private BigDecimal ID_CONTRATO_SESSION;
	private BigDecimal PRECIO;
	private Timestamp FECHA_ALTA;
	private Timestamp FECHA_ASIGNACION;
	private Timestamp FECHA_FIN_VIGENCIA;
	private BigDecimal ID_USUARIO;

//	 -- Valores propios del trámite general
	private BigDecimal NUM_EXPEDIENTE;
	private String REF_PROPIA;
	private String TIPO_TRAMITE;
	private BigDecimal ESTADO;

	public BeanPQTasasBuscar() {
		super();
	}

	public String getCODIGO_TASA() {
		return CODIGO_TASA;
	}
	public void setCODIGO_TASA(String cODIGOTASA) {
		CODIGO_TASA = cODIGOTASA;
	}
	public String getTIPO_TASA() {
		return TIPO_TASA;
	}
	public void setTIPO_TASA(String tIPOTASA) {
		TIPO_TASA = tIPOTASA;
	}
	public BigDecimal getID_CONTRATO() {
		return ID_CONTRATO;
	}
	public void setID_CONTRATO(BigDecimal iDCONTRATO) {
		ID_CONTRATO = iDCONTRATO;
	}
	public BigDecimal getID_CONTRATO_SESSION() {
		return ID_CONTRATO_SESSION;
	}
	public void setID_CONTRATO_SESSION(BigDecimal iDCONTRATOSESSION) {
		ID_CONTRATO_SESSION = iDCONTRATOSESSION;
	}
	public BigDecimal getPRECIO() {
		return PRECIO;
	}
	public void setPRECIO(BigDecimal pRECIO) {
		PRECIO = pRECIO;
	}
	public Timestamp getFECHA_ALTA() {
		return FECHA_ALTA;
	}
	public void setFECHA_ALTA(Timestamp fECHAALTA) {
		FECHA_ALTA = fECHAALTA;
	}
	public Timestamp getFECHA_ASIGNACION() {
		return FECHA_ASIGNACION;
	}
	public void setFECHA_ASIGNACION(Timestamp fECHAASIGNACION) {
		FECHA_ASIGNACION = fECHAASIGNACION;
	}
	public Timestamp getFECHA_FIN_VIGENCIA() {
		return FECHA_FIN_VIGENCIA;
	}
	public void setFECHA_FIN_VIGENCIA(Timestamp fECHAFINVIGENCIA) {
		FECHA_FIN_VIGENCIA = fECHAFINVIGENCIA;
	}
	public BigDecimal getID_USUARIO() {
		return ID_USUARIO;
	}
	public void setID_USUARIO(BigDecimal iDUSUARIO) {
		ID_USUARIO = iDUSUARIO;
	}
	public BigDecimal getNUM_EXPEDIENTE() {
		return NUM_EXPEDIENTE;
	}
	public void setNUM_EXPEDIENTE(BigDecimal nUMEXPEDIENTE) {
		NUM_EXPEDIENTE = nUMEXPEDIENTE;
	}
	public String getREF_PROPIA() {
		return REF_PROPIA;
	}
	public void setREF_PROPIA(String rEFPROPIA) {
		REF_PROPIA = rEFPROPIA;
	}
	public String getTIPO_TRAMITE() {
		return TIPO_TRAMITE;
	}
	public void setTIPO_TRAMITE(String tIPOTRAMITE) {
		TIPO_TRAMITE = tIPOTRAMITE;
	}
	public BigDecimal getESTADO() {
		return ESTADO;
	}
	public void setESTADO(BigDecimal eSTADO) {
		ESTADO = eSTADO;
	}
}