package trafico.beans.daos.pq_justificantes;

import general.beans.RespuestaGenerica;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import trafico.beans.daos.BeanPQGeneral;
import utilidades.constantes.ValoresCatalog;

public class BeanPQCREAR extends BeanPQGeneral {

	public static final String PROCEDURE = "CREAR";

	public List<Object> execute(Class<?> claseCursor) {
		RespuestaGenerica respuestaGenerica = ejecutarProc(this, SCHEMA, ValoresCatalog.PQ_JUSTIFICANTES, PROCEDURE,
				claseCursor, true);
		return respuestaGenerica.getListaCursor();
	}

	public void execute() {
		ejecutarProc(this, SCHEMA, ValoresCatalog.PQ_JUSTIFICANTES, PROCEDURE, null, true);
	}

	private BigDecimal P_NUM_EXPEDIENTE;

	private BigDecimal P_ID_JUSTIFICANTE;

	private BigDecimal P_DIAS_VALIDEZ;

	private String P_DOCUMENTOS;

	private Timestamp P_FECHA_INICIO;

	private Timestamp P_FECHA_FIN;

	private BigDecimal P_ID_USUARIO;

	public BigDecimal getP_ID_USUARIO() {
		return P_ID_USUARIO;
	}

	public void setP_ID_USUARIO(BigDecimal p_ID_USUARIO) {
		P_ID_USUARIO = p_ID_USUARIO;
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

	public String getP_DOCUMENTOS() {
		return P_DOCUMENTOS;
	}

	public void setP_DOCUMENTOS(String P_DOCUMENTOS) {
		this.P_DOCUMENTOS = P_DOCUMENTOS;
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

}