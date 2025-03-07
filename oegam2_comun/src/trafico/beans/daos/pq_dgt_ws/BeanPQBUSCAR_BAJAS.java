package trafico.beans.daos.pq_dgt_ws;

import general.beans.RespuestaGenerica;

import java.sql.Timestamp;
import java.util.List;

import trafico.beans.daos.BeanPQGeneral;
import utilidades.constantes.ValoresCatalog;

public class BeanPQBUSCAR_BAJAS extends BeanPQGeneral {

	public static final String PROCEDURE = "BUSCAR_BAJAS";

	public List<Object> execute(Class<?> claseCursor) {
		RespuestaGenerica respuestaGenerica = ejecutarProc(this, SCHEMA, ValoresCatalog.PQ_DGT_WS, PROCEDURE,
				claseCursor, true);
		return respuestaGenerica.getListaCursor();
	}

	public void execute() {
		ejecutarProc(this, SCHEMA, ValoresCatalog.PQ_DGT_WS, PROCEDURE, null, true);
	}

	private Timestamp P_FECHA_PRESENTACION_DESDE;

	private Timestamp P_FECHA_PRESENTACION_HASTA;

	private Object C_TRAMITES;

	public Timestamp getP_FECHA_PRESENTACION_DESDE() {
		return P_FECHA_PRESENTACION_DESDE;
	}

	public void setP_FECHA_PRESENTACION_DESDE(Timestamp P_FECHA_PRESENTACION_DESDE) {
		this.P_FECHA_PRESENTACION_DESDE = P_FECHA_PRESENTACION_DESDE;
	}

	public Timestamp getP_FECHA_PRESENTACION_HASTA() {
		return P_FECHA_PRESENTACION_HASTA;
	}

	public void setP_FECHA_PRESENTACION_HASTA(Timestamp P_FECHA_PRESENTACION_HASTA) {
		this.P_FECHA_PRESENTACION_HASTA = P_FECHA_PRESENTACION_HASTA;
	}

	public Object getC_TRAMITES() {
		return C_TRAMITES;
	}

	public void setC_TRAMITES(Object C_TRAMITES) {
		this.C_TRAMITES = C_TRAMITES;
	}

}