package trafico.beans.daos.pq_tasas;

import general.beans.RespuestaGenerica;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import trafico.beans.daos.BeanPQGeneral;
import utilidades.constantes.ValoresCatalog;

public class BeanPQRESUMEN extends BeanPQGeneral {

	public static final String PROCEDURE = "RESUMEN";

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

	private String P_TIPO_TASA;

	private Timestamp P_FECHA_ALTA_DESDE;

	private Timestamp P_FECHA_ALTA_HASTA;

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

	public Object getC_TASAS() {
		return C_TASAS;
	}

	public void setC_TASAS(Object C_TASAS) {
		this.C_TASAS = C_TASAS;
	}

}