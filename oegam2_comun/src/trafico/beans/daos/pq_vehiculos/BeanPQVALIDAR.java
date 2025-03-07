package trafico.beans.daos.pq_vehiculos;

import general.beans.RespuestaGenerica;

import java.math.BigDecimal;
import java.util.List;

import trafico.beans.daos.BeanPQGeneral;
import utilidades.constantes.ValoresCatalog;

public class BeanPQVALIDAR extends BeanPQGeneral {

	public static final String PROCEDURE = "VALIDAR";

	public List<Object> execute(Class<?> claseCursor) {
		RespuestaGenerica respuestaGenerica = ejecutarProc(this, SCHEMA, ValoresCatalog.PQ_VEHICULOS, PROCEDURE,
				claseCursor, true);
		return respuestaGenerica.getListaCursor();
	}

	public void execute() {
		ejecutarProc(this, SCHEMA, ValoresCatalog.PQ_VEHICULOS, PROCEDURE, null, true);
	}

	private String P_NUM_COLEGIADO;

	private BigDecimal P_ID_VEHICULO;

	private BigDecimal P_NUM_EXPEDIENTE;

	private String P_TIPO_TRAMITE;

	private Object P_TELEMATICO;

	public String getP_NUM_COLEGIADO() {
		return P_NUM_COLEGIADO;
	}

	public void setP_NUM_COLEGIADO(String P_NUM_COLEGIADO) {
		this.P_NUM_COLEGIADO = P_NUM_COLEGIADO;
	}

	public BigDecimal getP_ID_VEHICULO() {
		return P_ID_VEHICULO;
	}

	public void setP_ID_VEHICULO(BigDecimal P_ID_VEHICULO) {
		this.P_ID_VEHICULO = P_ID_VEHICULO;
	}

	public BigDecimal getP_NUM_EXPEDIENTE() {
		return P_NUM_EXPEDIENTE;
	}

	public void setP_NUM_EXPEDIENTE(BigDecimal P_NUM_EXPEDIENTE) {
		this.P_NUM_EXPEDIENTE = P_NUM_EXPEDIENTE;
	}

	public String getP_TIPO_TRAMITE() {
		return P_TIPO_TRAMITE;
	}

	public void setP_TIPO_TRAMITE(String P_TIPO_TRAMITE) {
		this.P_TIPO_TRAMITE = P_TIPO_TRAMITE;
	}

	public Object getP_TELEMATICO() {
		return P_TELEMATICO;
	}

	public void setP_TELEMATICO(Object P_TELEMATICO) {
		this.P_TELEMATICO = P_TELEMATICO;
	}

}