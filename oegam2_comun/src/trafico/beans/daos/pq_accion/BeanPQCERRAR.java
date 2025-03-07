package trafico.beans.daos.pq_accion;

import general.beans.RespuestaGenerica;

import java.math.BigDecimal;
import java.util.List;

import trafico.beans.daos.BeanPQGeneral;
import utilidades.constantes.ValoresCatalog;

public class BeanPQCERRAR extends BeanPQGeneral {

	public static final String PROCEDURE = "CERRAR";

	public List<Object> execute(Class<?> claseCursor) {
		RespuestaGenerica respuestaGenerica = ejecutarProc(this, SCHEMA, ValoresCatalog.PQ_ACCION, PROCEDURE,
				claseCursor, true);
		return respuestaGenerica.getListaCursor();
	}

	public void execute() {
		ejecutarProc(this, SCHEMA, ValoresCatalog.PQ_ACCION, PROCEDURE, null, true);
	}

	private BigDecimal P_ID_USUARIO;

	private BigDecimal P_NUM_EXPEDIENTE;

	private String P_ACCION;

	private String P_RESPUESTA;

	public BigDecimal getP_ID_USUARIO() {
		return P_ID_USUARIO;
	}

	public void setP_ID_USUARIO(BigDecimal P_ID_USUARIO) {
		this.P_ID_USUARIO = P_ID_USUARIO;
	}

	public BigDecimal getP_NUM_EXPEDIENTE() {
		return P_NUM_EXPEDIENTE;
	}

	public void setP_NUM_EXPEDIENTE(BigDecimal P_NUM_EXPEDIENTE) {
		this.P_NUM_EXPEDIENTE = P_NUM_EXPEDIENTE;
	}

	public String getP_ACCION() {
		return P_ACCION;
	}

	public void setP_ACCION(String P_ACCION) {
		this.P_ACCION = P_ACCION;
	}

	public String getP_RESPUESTA() {
		return P_RESPUESTA;
	}

	public void setP_RESPUESTA(String P_RESPUESTA) {
		this.P_RESPUESTA = P_RESPUESTA;
	}

}