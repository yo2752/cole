package trafico.beans.daos.pq_justificantes;

import general.beans.RespuestaGenerica;

import java.math.BigDecimal;
import java.util.List;

import trafico.beans.daos.BeanPQGeneral;
import utilidades.constantes.ValoresCatalog;

public class BeanPQVALIDAR extends BeanPQGeneral {

	public static final String PROCEDURE = "VALIDAR";

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

	private BigDecimal P_VALIDO;

	private BigDecimal P_TIPO_VALIDO;

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

	public BigDecimal getP_VALIDO() {
		return P_VALIDO;
	}

	public void setP_VALIDO(BigDecimal P_VALIDO) {
		this.P_VALIDO = P_VALIDO;
	}

	public BigDecimal getP_TIPO_VALIDO() {
		return P_TIPO_VALIDO;
	}

	public void setP_TIPO_VALIDO(BigDecimal P_TIPO_VALIDO) {
		this.P_TIPO_VALIDO = P_TIPO_VALIDO;
	}

}