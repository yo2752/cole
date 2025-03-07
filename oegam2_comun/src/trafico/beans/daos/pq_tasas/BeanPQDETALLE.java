package trafico.beans.daos.pq_tasas;

import general.beans.RespuestaGenerica;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import trafico.beans.daos.BeanPQGeneral;
import utilidades.constantes.ValoresCatalog;

public class BeanPQDETALLE extends BeanPQGeneral {

	public static final String PROCEDURE = "DETALLE";

	public List<Object> execute(Class<?> claseCursor) {
		RespuestaGenerica respuestaGenerica = ejecutarProc(this, SCHEMA, ValoresCatalog.PQ_TASAS, PROCEDURE,
				claseCursor, true);
		return respuestaGenerica.getListaCursor();
	}

	public void execute() {
		ejecutarProc(this, SCHEMA, ValoresCatalog.PQ_TASAS, PROCEDURE, null, true);
	}

	private BigDecimal P_ID_CONTRATO_SESSION;

	private String P_CODIGO_TASA;

	private BigDecimal P_PRECIO;

	private String P_NUM_COLEGIADO;

	private BigDecimal P_ID_CONTRATO;

	private BigDecimal P_ID_USUARIO;

	private String P_TIPO_TASA;

	private Timestamp P_FECHA_ALTA;

	private Timestamp P_FECHA_ASIGNACION;

	private Timestamp P_FECHA_FIN_VIGENCIA;

	private String P_REF_PROPIA;

	private BigDecimal P_NUM_EXPEDIENTE;

	/*
	 * PROCEDURE DETALLE (P_ID_CONTRATO_SESSION IN TASA.ID_CONTRATO%TYPE,
	 * P_CODIGO_TASA IN OUT TASA.CODIGO_TASA%TYPE, P_TIPO_TASA OUT
	 * TASA.TIPO_TASA%TYPE, P_ID_CONTRATO OUT TASA.ID_CONTRATO%TYPE, P_PRECIO OUT
	 * TASA.PRECIO%TYPE, P_FECHA_ALTA OUT TASA.FECHA_ALTA%TYPE, P_FECHA_ASIGNACION
	 * OUT TASA.FECHA_ASIGNACION%TYPE, P_FECHA_FIN_VIGENCIA OUT
	 * TASA.FECHA_FIN_VIGENCIA%TYPE, P_ID_USUARIO OUT TASA.ID_USUARIO%TYPE, --
	 * INFORMACION DEL TRAMITE P_NUM_EXPEDIENTE OUT
	 * TRAMITE_TRAFICO.NUM_EXPEDIENTE%TYPE, P_REF_PROPIA OUT
	 * TRAMITE_TRAFICO.REF_PROPIA%TYPE, ---- P_CODE OUT NUMBER, P_SQLERRM OUT
	 * VARCHAR2);
	 */

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

	public String getP_TIPO_TASA() {
		return P_TIPO_TASA;
	}

	public void setP_TIPO_TASA(String P_TIPO_TASA) {
		this.P_TIPO_TASA = P_TIPO_TASA;
	}

	public String getP_CODIGO_TASA() {
		return P_CODIGO_TASA;
	}

	public void setP_CODIGO_TASA(String pCODIGOTASA) {
		P_CODIGO_TASA = pCODIGOTASA;
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

	public String getP_REF_PROPIA() {
		return P_REF_PROPIA;
	}

	public void setP_REF_PROPIA(String pREFPROPIA) {
		P_REF_PROPIA = pREFPROPIA;
	}

	public BigDecimal getP_NUM_EXPEDIENTE() {
		return P_NUM_EXPEDIENTE;
	}

	public void setP_NUM_EXPEDIENTE(BigDecimal pNUMEXPEDIENTE) {
		P_NUM_EXPEDIENTE = pNUMEXPEDIENTE;
	}

}