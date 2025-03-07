package trafico.beans.daos.pq_tasas;

import general.beans.RespuestaGenerica;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import trafico.beans.daos.BeanPQGeneral;
import utilidades.constantes.ValoresCatalog;

public class BeanPQGUARDAR extends BeanPQGeneral {

	public static final String PROCEDURE = "GUARDAR";

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

	private String P_TIPO_TASA;

	private BigDecimal P_ID_CONTRATO;

	private BigDecimal P_PRECIO;

	private Timestamp P_FECHA_ALTA;

	private Timestamp P_FECHA_ASIGNACION;

	private Timestamp P_FECHA_FIN_VIGENCIA;

	private BigDecimal P_ID_USUARIO;

	private String P_NUM_COLEGIADO;

	public BigDecimal getP_ID_CONTRATO_SESSION() {
		return P_ID_CONTRATO_SESSION;
	}

	public void setP_ID_CONTRATO_SESSION(BigDecimal P_ID_CONTRATO_SESSION) {
		this.P_ID_CONTRATO_SESSION = P_ID_CONTRATO_SESSION;
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

	public BigDecimal getP_ID_CONTRATO() {
		return P_ID_CONTRATO;
	}

	public void setP_ID_CONTRATO(BigDecimal P_ID_CONTRATO) {
		this.P_ID_CONTRATO = P_ID_CONTRATO;
	}

	public BigDecimal getP_PRECIO() {
		return P_PRECIO;
	}

	public void setP_PRECIO(BigDecimal P_PRECIO) {
		this.P_PRECIO = P_PRECIO;
	}

	public Timestamp getP_FECHA_ALTA() {
		return P_FECHA_ALTA;
	}

	public void setP_FECHA_ALTA(Timestamp P_FECHA_ALTA) {
		this.P_FECHA_ALTA = P_FECHA_ALTA;
	}

	public Timestamp getP_FECHA_ASIGNACION() {
		return P_FECHA_ASIGNACION;
	}

	public void setP_FECHA_ASIGNACION(Timestamp P_FECHA_ASIGNACION) {
		this.P_FECHA_ASIGNACION = P_FECHA_ASIGNACION;
	}

	public Timestamp getP_FECHA_FIN_VIGENCIA() {
		return P_FECHA_FIN_VIGENCIA;
	}

	public void setP_FECHA_FIN_VIGENCIA(Timestamp P_FECHA_FIN_VIGENCIA) {
		this.P_FECHA_FIN_VIGENCIA = P_FECHA_FIN_VIGENCIA;
	}

	public BigDecimal getP_ID_USUARIO() {
		return P_ID_USUARIO;
	}

	public void setP_ID_USUARIO(BigDecimal P_ID_USUARIO) {
		this.P_ID_USUARIO = P_ID_USUARIO;
	}

	public String getP_NUM_COLEGIADO() {
		return P_NUM_COLEGIADO;
	}

	public void setP_NUM_COLEGIADO(String P_NUM_COLEGIADO) {
		this.P_NUM_COLEGIADO = P_NUM_COLEGIADO;
	}

}