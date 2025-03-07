package trafico.beans.daos;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * w_select := 'SELECT t.rowid rowid_tramite, t.NUM_EXPEDIENTE, t.TIPO_TRAMITE, t.ID_CONTRATO, t.NUM_COLEGIADO, 
      p.nif, p.apellido1_razon_social, p.apellido2, p.nombre,
      t.ID_VEHICULO, v.matricula, v.bastidor, v.nive, v.tipo_vehiculo, t.CODIGO_TASA, t.ESTADO, t.REF_PROPIA,
      t.FECHA_ALTA, t.FECHA_PRESENTACION, t.FECHA_ULT_MODIF, t.FECHA_IMPRESION, t.ANOTACIONES, t.RESPUESTA 
      FROM tramite_trafico t, vehiculo v, persona p, interviniente_trafico i';
 * @author juan.gomez
 *
 */

public class BeanPQTramiteBuscarResult{

	private BigDecimal NUM_EXPEDIENTE;
	private String TIPO_TRAMITE;
	private BigDecimal ID_CONTRATO;
	private String NUM_COLEGIADO;

	private String nif;
	private String apellido1_razon_social;
	private String apellido2;
	private String nombre;

	private BigDecimal ID_VEHICULO;
	private String matricula;
	private String bastidor;
	private String TIPO_VEHICULO;
	private String CODIGO_TASA;
	private String TIPO_TASA;
	private BigDecimal ESTADO;
	private String REF_PROPIA;

	private Timestamp FECHA_ALTA;
	private Timestamp FECHA_PRESENTACION;
	private Timestamp FECHA_ULT_MODIF;
	private Timestamp FECHA_IMPRESION;
	private String ANOTACIONES;
	private String RESPUESTA;
	private BigDecimal ID_TIPO_CREACION;

	public BeanPQTramiteBuscarResult() {
		super();
	}

	public BigDecimal getNUM_EXPEDIENTE() {
		return NUM_EXPEDIENTE;
	}

	public void setNUM_EXPEDIENTE(BigDecimal nUMEXPEDIENTE) {
		NUM_EXPEDIENTE = nUMEXPEDIENTE;
	}

	public String getTIPO_TASA() {
		return TIPO_TASA;
	}

	public void setTIPO_TASA(String tIPOTASA) {
		TIPO_TASA = tIPOTASA;
	}

	public String getTIPO_TRAMITE() {
		return TIPO_TRAMITE;
	}

	public void setTIPO_TRAMITE(String tIPOTRAMITE) {
		TIPO_TRAMITE = tIPOTRAMITE;
	}

	public BigDecimal getID_CONTRATO() {
		return ID_CONTRATO;
	}

	public void setID_CONTRATO(BigDecimal iDCONTRATO) {
		ID_CONTRATO = iDCONTRATO;
	}

	public String getNUM_COLEGIADO() {
		return NUM_COLEGIADO;
	}

	public void setNUM_COLEGIADO(String nUMCOLEGIADO) {
		NUM_COLEGIADO = nUMCOLEGIADO;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getApellido1_razon_social() {
		return apellido1_razon_social;
	}

	public void setApellido1_razon_social(String apellido1RazonSocial) {
		apellido1_razon_social = apellido1RazonSocial;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getBastidor() {
		return bastidor;
	}

	public void setBastidor(String bastidor) {
		this.bastidor = bastidor;
	}

	public String getTIPO_VEHICULO() {
		return TIPO_VEHICULO;
	}

	public void setTIPO_VEHICULO(String TIPO_VEHICULO) {
		this.TIPO_VEHICULO = TIPO_VEHICULO;
	}

	public BigDecimal getID_VEHICULO() {
		return ID_VEHICULO;
	}

	public void setID_VEHICULO(BigDecimal iDVEHICULO) {
		ID_VEHICULO = iDVEHICULO;
	}

	public String getCODIGO_TASA() {
		return CODIGO_TASA;
	}

	public void setCODIGO_TASA(String cODIGOTASA) {
		CODIGO_TASA = cODIGOTASA;
	}

	public BigDecimal getESTADO() {
		return ESTADO;
	}

	public void setESTADO(BigDecimal eSTADO) {
		ESTADO = eSTADO;
	}

	public String getREF_PROPIA() {
		return REF_PROPIA;
	}

	public void setREF_PROPIA(String rEFPROPIA) {
		REF_PROPIA = rEFPROPIA;
	}

	public Timestamp getFECHA_ALTA() {
		return FECHA_ALTA;
	}

	public void setFECHA_ALTA(Timestamp fECHAALTA) {
		FECHA_ALTA = fECHAALTA;
	}

	public Timestamp getFECHA_PRESENTACION() {
		return FECHA_PRESENTACION;
	}

	public void setFECHA_PRESENTACION(Timestamp fECHAPRESENTACION) {
		FECHA_PRESENTACION = fECHAPRESENTACION;
	}

	public Timestamp getFECHA_ULT_MODIF() {
		return FECHA_ULT_MODIF;
	}

	public void setFECHA_ULT_MODIF(Timestamp fECHAULTMODIF) {
		FECHA_ULT_MODIF = fECHAULTMODIF;
	}

	public Timestamp getFECHA_IMPRESION() {
		return FECHA_IMPRESION;
	}

	public void setFECHA_IMPRESION(Timestamp fECHAIMPRESION) {
		FECHA_IMPRESION = fECHAIMPRESION;
	}

	public String getANOTACIONES() {
		return ANOTACIONES;
	}

	public void setANOTACIONES(String aNOTACIONES) {
		ANOTACIONES = aNOTACIONES;
	}
	public String getRESPUESTA() {
		return RESPUESTA;
	}
	public void setRESPUESTA(String rESPUESTA) {
		RESPUESTA = rESPUESTA;
	}
	public BigDecimal getID_TIPO_CREACION() {
		return ID_TIPO_CREACION;
	}
	public void setID_TIPO_CREACION(BigDecimal iDTIPOCREACION) {
		ID_TIPO_CREACION = iDTIPOCREACION;
	}
}