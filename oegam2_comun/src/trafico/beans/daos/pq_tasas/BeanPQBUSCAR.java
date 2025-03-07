package trafico.beans.daos.pq_tasas;

import general.beans.RespuestaGenerica;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import trafico.beans.daos.BeanPQGeneral;
import utilidades.constantes.ValoresCatalog;

public class BeanPQBUSCAR extends BeanPQGeneral {

	public static final String PROCEDURE = "BUSCAR";

	public List<Object> execute(Class<?> claseCursor) {
		RespuestaGenerica respuestaGenerica = ejecutarProc(this, SCHEMA, ValoresCatalog.PQ_TASAS, PROCEDURE,
				claseCursor, true);
		return respuestaGenerica.getListaCursor();
	}

	public void execute() {
		ejecutarProc(this, SCHEMA, ValoresCatalog.PQ_TASAS, PROCEDURE, null, true);
	}

	private BigDecimal P_ID_CONTRATO_SESSION;

	private String P_NUM_COLEGIADO;

	private BigDecimal P_ID_CONTRATO;

	private BigDecimal P_ID_USUARIO;

	private String P_ASIGNADA;

	private String P_CODIGO_TASA;

	private String P_TIPO_TASA;

	private Timestamp P_FECHA_ALTA_DESDE;

	private Timestamp P_FECHA_ALTA_HASTA;

	private Timestamp P_FECHA_ASIGNACION_DESDE;

	private Timestamp P_FECHA_ASIGNACION_HASTA;

	private Timestamp P_FECHA_FIN_VIGENCIA;

	private BigDecimal P_NUM_EXPEDIENTE;

	private String P_TIPO_TRAMITE;

	private String P_REF_PROPIA;

	private BigDecimal PAGINA;

	private BigDecimal NUM_REG;

	private String COLUMNA_ORDEN;

	private String ORDEN;

	private BigDecimal CUENTA;

	private Object C_TASAS;

	public BigDecimal getP_ID_CONTRATO_SESSION() {
		return P_ID_CONTRATO_SESSION;
	}

	public void setP_ID_CONTRATO_SESSION(BigDecimal P_ID_CONTRATO_SESSION) {
		this.P_ID_CONTRATO_SESSION = P_ID_CONTRATO_SESSION;
	}

	public String getP_NUM_COLEGIADO() {
		return P_NUM_COLEGIADO;
	}

	public void setP_NUM_COLEGIADO(String P_NUM_COLEGIADO) {
		this.P_NUM_COLEGIADO = P_NUM_COLEGIADO;
	}

	public BigDecimal getP_ID_CONTRATO() {
		return P_ID_CONTRATO;
	}

	public void setP_ID_CONTRATO(BigDecimal P_ID_CONTRATO) {
		this.P_ID_CONTRATO = P_ID_CONTRATO;
	}

	public BigDecimal getP_ID_USUARIO() {
		return P_ID_USUARIO;
	}

	public void setP_ID_USUARIO(BigDecimal P_ID_USUARIO) {
		this.P_ID_USUARIO = P_ID_USUARIO;
	}

	public String getP_ASIGNADA() {
		return P_ASIGNADA;
	}

	public void setP_ASIGNADA(String P_ASIGNADA) {
		this.P_ASIGNADA = P_ASIGNADA;
	}

	public String getP_CODIGO_TASA() {
		return P_CODIGO_TASA;
	}

	public void setP_CODIGO_TASA(String P_CODIGO_TASA) {
		this.P_CODIGO_TASA = P_CODIGO_TASA;
	}

	public String getP_TIPO_TASA() {
		return P_TIPO_TASA;
	}

	public void setP_TIPO_TASA(String P_TIPO_TASA) {
		this.P_TIPO_TASA = P_TIPO_TASA;
	}

	public Timestamp getP_FECHA_ALTA_DESDE() {
		return P_FECHA_ALTA_DESDE;
	}

	public void setP_FECHA_ALTA_DESDE(Timestamp P_FECHA_ALTA_DESDE) {
		this.P_FECHA_ALTA_DESDE = P_FECHA_ALTA_DESDE;
	}

	public Timestamp getP_FECHA_ALTA_HASTA() {
		return P_FECHA_ALTA_HASTA;
	}

	public void setP_FECHA_ALTA_HASTA(Timestamp P_FECHA_ALTA_HASTA) {
		this.P_FECHA_ALTA_HASTA = P_FECHA_ALTA_HASTA;
	}

	public Timestamp getP_FECHA_ASIGNACION_DESDE() {
		return P_FECHA_ASIGNACION_DESDE;
	}

	public void setP_FECHA_ASIGNACION_DESDE(Timestamp P_FECHA_ASIGNACION_DESDE) {
		this.P_FECHA_ASIGNACION_DESDE = P_FECHA_ASIGNACION_DESDE;
	}

	public Timestamp getP_FECHA_ASIGNACION_HASTA() {
		return P_FECHA_ASIGNACION_HASTA;
	}

	public void setP_FECHA_ASIGNACION_HASTA(Timestamp P_FECHA_ASIGNACION_HASTA) {
		this.P_FECHA_ASIGNACION_HASTA = P_FECHA_ASIGNACION_HASTA;
	}

	public Timestamp getP_FECHA_FIN_VIGENCIA() {
		return P_FECHA_FIN_VIGENCIA;
	}

	public void setP_FECHA_FIN_VIGENCIA(Timestamp P_FECHA_FIN_VIGENCIA) {
		this.P_FECHA_FIN_VIGENCIA = P_FECHA_FIN_VIGENCIA;
	}

	public BigDecimal getP_NUM_EXPEDIENTE() {
		return P_NUM_EXPEDIENTE;
	}

	public void setP_NUM_EXPEDIENTE(BigDecimal P_NUM_EXPEDIENTE) {
		this.P_NUM_EXPEDIENTE = P_NUM_EXPEDIENTE;
	}

	public String getP_TIPO_TRAMITE() {
		return P_TIPO_TRAMITE;
	}

	public void setP_TIPO_TRAMITE(String P_TIPO_TRAMITE) {
		this.P_TIPO_TRAMITE = P_TIPO_TRAMITE;
	}

	public String getP_REF_PROPIA() {
		return P_REF_PROPIA;
	}

	public void setP_REF_PROPIA(String P_REF_PROPIA) {
		this.P_REF_PROPIA = P_REF_PROPIA;
	}

	public BigDecimal getPAGINA() {
		return PAGINA;
	}

	public void setPAGINA(BigDecimal PAGINA) {
		this.PAGINA = PAGINA;
	}

	public BigDecimal getNUM_REG() {
		return NUM_REG;
	}

	public void setNUM_REG(BigDecimal NUM_REG) {
		this.NUM_REG = NUM_REG;
	}

	public String getCOLUMNA_ORDEN() {
		return COLUMNA_ORDEN;
	}

	public void setCOLUMNA_ORDEN(String COLUMNA_ORDEN) {
		this.COLUMNA_ORDEN = COLUMNA_ORDEN;
	}

	public String getORDEN() {
		return ORDEN;
	}

	public void setORDEN(String ORDEN) {
		this.ORDEN = ORDEN;
	}

	public BigDecimal getCUENTA() {
		return CUENTA;
	}

	public void setCUENTA(BigDecimal CUENTA) {
		this.CUENTA = CUENTA;
	}

	public Object getC_TASAS() {
		return C_TASAS;
	}

	public void setC_TASAS(Object C_TASAS) {
		this.C_TASAS = C_TASAS;
	}

}