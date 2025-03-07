package trafico.beans.daos.pq_dgt_ws;

import general.beans.RespuestaGenerica;

import java.math.BigDecimal;
import java.util.List;

import trafico.beans.daos.BeanPQGeneral;
import utilidades.constantes.ValoresCatalog;

public class BeanPQDATOS_CTIT extends BeanPQGeneral {

	public static final String PROCEDURE = "DATOS_CTIT";

	public List<Object> execute(Class<?> claseCursor) {
		RespuestaGenerica respuestaGenerica = ejecutarProc(this, SCHEMA, ValoresCatalog.PQ_DGT_WS, PROCEDURE,
				claseCursor, true);
		return respuestaGenerica.getListaCursor();
	}

	public void execute() {
		ejecutarProc(this, SCHEMA, ValoresCatalog.PQ_DGT_WS, PROCEDURE, null, true);
	}

	private BigDecimal P_NUM_EXPEDIENTE;

	private String P_NPASOS;

	private String P_NUM_COLEGIADO;

	private String P_CONSENTIMIENTO;

	private String P_AGENCYFISCALLD;

	private String P_EXTERNALSYSTEMFISCALL;

	public BigDecimal getP_NUM_EXPEDIENTE() {
		return P_NUM_EXPEDIENTE;
	}

	public void setP_NUM_EXPEDIENTE(BigDecimal P_NUM_EXPEDIENTE) {
		this.P_NUM_EXPEDIENTE = P_NUM_EXPEDIENTE;
	}

	public String getP_NPASOS() {
		return P_NPASOS;
	}

	public void setP_NPASOS(String P_NPASOS) {
		this.P_NPASOS = P_NPASOS;
	}

	public String getP_NUM_COLEGIADO() {
		return P_NUM_COLEGIADO;
	}

	public void setP_NUM_COLEGIADO(String P_NUM_COLEGIADO) {
		this.P_NUM_COLEGIADO = P_NUM_COLEGIADO;
	}

	public String getP_CONSENTIMIENTO() {
		return P_CONSENTIMIENTO;
	}

	public void setP_CONSENTIMIENTO(String P_CONSENTIMIENTO) {
		this.P_CONSENTIMIENTO = P_CONSENTIMIENTO;
	}

	public String getP_AGENCYFISCALLD() {
		return P_AGENCYFISCALLD;
	}

	public void setP_AGENCYFISCALLD(String P_AGENCYFISCALLD) {
		this.P_AGENCYFISCALLD = P_AGENCYFISCALLD;
	}

	public String getP_EXTERNALSYSTEMFISCALL() {
		return P_EXTERNALSYSTEMFISCALL;
	}

	public void setP_EXTERNALSYSTEMFISCALL(String P_EXTERNALSYSTEMFISCALL) {
		this.P_EXTERNALSYSTEMFISCALL = P_EXTERNALSYSTEMFISCALL;
	}

}