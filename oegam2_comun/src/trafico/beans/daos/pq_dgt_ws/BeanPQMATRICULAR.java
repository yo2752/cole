package trafico.beans.daos.pq_dgt_ws;

import general.beans.RespuestaGenerica;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import trafico.beans.daos.BeanPQGeneral;
import utilidades.constantes.ValoresCatalog;

public class BeanPQMATRICULAR extends BeanPQGeneral {

	public static final String PROCEDURE = "MATRICULAR";

	public List<Object> execute(Class<?> claseCursor) {
		RespuestaGenerica respuestaGenerica = ejecutarProc(this, SCHEMA, ValoresCatalog.PQ_DGT_WS, PROCEDURE,
				claseCursor, true);
		return respuestaGenerica.getListaCursor();
	}

	public void execute() {
		ejecutarProc(this, SCHEMA, ValoresCatalog.PQ_DGT_WS, PROCEDURE, null, true);
	}

	private BigDecimal P_NUM_EXPEDIENTE;

	private BigDecimal P_ESTADO;

	private String P_MATRICULA;

	private Timestamp P_FECHA_MATRICULACION;

	private Timestamp P_FECHA_PRESENTACION;

	private String P_RESPUESTA;

	public BigDecimal getP_NUM_EXPEDIENTE() {
		return P_NUM_EXPEDIENTE;
	}

	public void setP_NUM_EXPEDIENTE(BigDecimal P_NUM_EXPEDIENTE) {
		this.P_NUM_EXPEDIENTE = P_NUM_EXPEDIENTE;
	}

	public BigDecimal getP_ESTADO() {
		return P_ESTADO;
	}

	public void setP_ESTADO(BigDecimal P_ESTADO) {
		this.P_ESTADO = P_ESTADO;
	}

	public String getP_MATRICULA() {
		return P_MATRICULA;
	}

	public void setP_MATRICULA(String P_MATRICULA) {
		this.P_MATRICULA = P_MATRICULA;
	}

	public Timestamp getP_FECHA_MATRICULACION() {
		return P_FECHA_MATRICULACION;
	}

	public void setP_FECHA_MATRICULACION(Timestamp P_FECHA_MATRICULACION) {
		this.P_FECHA_MATRICULACION = P_FECHA_MATRICULACION;
	}

	public Timestamp getP_FECHA_PRESENTACION() {
		return P_FECHA_PRESENTACION;
	}

	public void setP_FECHA_PRESENTACION(Timestamp P_FECHA_PRESENTACION) {
		this.P_FECHA_PRESENTACION = P_FECHA_PRESENTACION;
	}

	public String getP_RESPUESTA() {
		return P_RESPUESTA;
	}

	public void setP_RESPUESTA(String P_RESPUESTA) {
		this.P_RESPUESTA = P_RESPUESTA;
	}

}