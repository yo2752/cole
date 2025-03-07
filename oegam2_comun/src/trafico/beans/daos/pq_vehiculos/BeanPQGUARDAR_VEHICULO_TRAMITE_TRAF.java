package trafico.beans.daos.pq_vehiculos;

import general.beans.RespuestaGenerica;

import java.math.BigDecimal;
import java.util.List;

import trafico.beans.daos.BeanPQGeneral;
import utilidades.constantes.ValoresCatalog;

public class BeanPQGUARDAR_VEHICULO_TRAMITE_TRAF extends BeanPQGeneral {
	public static final String PROCEDURE = "GUARDAR_VEHICULO_TRAMITE_TRAF";

	public List<Object> execute(Class<?> claseCursor) {
		RespuestaGenerica respuestaGenerica = ejecutarProc(this, SCHEMA, ValoresCatalog.PQ_VEHICULOS, PROCEDURE,
				claseCursor, true);
		return respuestaGenerica.getListaCursor();
	}

	public void execute() {
		ejecutarProc(this, SCHEMA, ValoresCatalog.PQ_VEHICULOS, PROCEDURE, null, true);
	}

	private BigDecimal P_ID_VEHICULO;
	private String P_NUM_COLEGIADO;
	private BigDecimal P_NUM_EXPEDIENTE;
	private BigDecimal P_KILOMETROS;

	public BigDecimal getP_ID_VEHICULO() {
		return P_ID_VEHICULO;
	}

	public void setP_ID_VEHICULO(BigDecimal P_ID_VEHICULO) {
		this.P_ID_VEHICULO = P_ID_VEHICULO;
	}

	public String getP_NUM_COLEGIADO() {
		return P_NUM_COLEGIADO;
	}

	public void setP_NUM_COLEGIADO(String P_NUM_COLEGIADO) {
		this.P_NUM_COLEGIADO = P_NUM_COLEGIADO;
	}

	public BigDecimal getP_NUM_EXPEDIENTE() {
		return P_NUM_EXPEDIENTE;
	}

	public void setP_NUM_EXPEDIENTE(BigDecimal P_NUM_EXPEDIENTE) {
		this.P_NUM_EXPEDIENTE = P_NUM_EXPEDIENTE;
	}

	public BigDecimal getP_KILOMETROS() {
		return P_KILOMETROS;
	}

	public void setP_KILOMETROS(BigDecimal P_KILOMETROS) {
		this.P_KILOMETROS = P_KILOMETROS;
	}
}