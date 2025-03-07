package trafico.beans.daos.pq_justificantes;

import general.beans.RespuestaGenerica;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import trafico.beans.daos.BeanPQGeneral;
import utilidades.constantes.ValoresCatalog;

public class BeanPQBUSCAR extends BeanPQGeneral {

	public static final String PROCEDURE = "BUSCAR";

	public List<Object> execute(Class<?> claseCursor) {
		RespuestaGenerica respuestaGenerica = ejecutarProc(this, SCHEMA, ValoresCatalog.PQ_JUSTIFICANTES, PROCEDURE,
				claseCursor, true);
		return respuestaGenerica.getListaCursor();
	}

	public void execute() {
		ejecutarProc(this, SCHEMA, ValoresCatalog.PQ_JUSTIFICANTES, PROCEDURE, null, true);
	}

	private BigDecimal P_ID_USUARIO;

	private BigDecimal P_ID_CONTRATO_SESSION;

	private BigDecimal P_NUM_EXPEDIENTE;

	private BigDecimal P_ID_JUSTIFICANTE;

	private BigDecimal P_DIAS_VALIDEZ;

	private Timestamp P_FECHA_INICIO;

	private Timestamp P_FECHA_FIN;

	private String P_MATRICULA;

	private BigDecimal P_ESTADO;

	private BigDecimal PAGINA;

	private BigDecimal NUM_REG;

	private String COLUMNA_ORDEN;

	private String ORDEN;

	private BigDecimal CUENTA;

	private Object C_JUSTIFICANTES;

	public BigDecimal getP_ID_USUARIO() {
		return P_ID_USUARIO;
	}

	public void setP_ID_USUARIO(BigDecimal P_ID_USUARIO) {
		this.P_ID_USUARIO = P_ID_USUARIO;
	}

	public BigDecimal getP_ID_CONTRATO_SESSION() {
		return P_ID_CONTRATO_SESSION;
	}

	public void setP_ID_CONTRATO_SESSION(BigDecimal P_ID_CONTRATO_SESSION) {
		this.P_ID_CONTRATO_SESSION = P_ID_CONTRATO_SESSION;
	}

	public BigDecimal getP_NUM_EXPEDIENTE() {
		return P_NUM_EXPEDIENTE;
	}

	public void setP_NUM_EXPEDIENTE(BigDecimal P_NUM_EXPEDIENTE) {
		this.P_NUM_EXPEDIENTE = P_NUM_EXPEDIENTE;
	}

	public BigDecimal getP_ID_JUSTIFICANTE() {
		return P_ID_JUSTIFICANTE;
	}

	public void setP_ID_JUSTIFICANTE(BigDecimal P_ID_JUSTIFICANTE) {
		this.P_ID_JUSTIFICANTE = P_ID_JUSTIFICANTE;
	}

	public BigDecimal getP_DIAS_VALIDEZ() {
		return P_DIAS_VALIDEZ;
	}

	public void setP_DIAS_VALIDEZ(BigDecimal P_DIAS_VALIDEZ) {
		this.P_DIAS_VALIDEZ = P_DIAS_VALIDEZ;
	}

	public Timestamp getP_FECHA_INICIO() {
		return P_FECHA_INICIO;
	}

	public void setP_FECHA_INICIO(Timestamp P_FECHA_INICIO) {
		this.P_FECHA_INICIO = P_FECHA_INICIO;
	}

	public Timestamp getP_FECHA_FIN() {
		return P_FECHA_FIN;
	}

	public void setP_FECHA_FIN(Timestamp P_FECHA_FIN) {
		this.P_FECHA_FIN = P_FECHA_FIN;
	}

	public String getP_MATRICULA() {
		return P_MATRICULA;
	}

	public void setP_MATRICULA(String P_MATRICULA) {
		this.P_MATRICULA = P_MATRICULA;
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

	public Object getC_JUSTIFICANTES() {
		return C_JUSTIFICANTES;
	}

	public void setC_JUSTIFICANTES(Object C_JUSTIFICANTES) {
		this.C_JUSTIFICANTES = C_JUSTIFICANTES;
	}

	public BigDecimal getP_ESTADO() {
		return P_ESTADO;
	}

	public void setP_ESTADO(BigDecimal p_ESTADO) {
		P_ESTADO = p_ESTADO;
	}

}