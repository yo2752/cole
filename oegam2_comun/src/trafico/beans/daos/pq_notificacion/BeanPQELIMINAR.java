package trafico.beans.daos.pq_notificacion;

import general.beans.RespuestaGenerica;

import java.math.BigDecimal;
import java.util.List;

import trafico.beans.daos.BeanPQGeneral;
import utilidades.constantes.ValoresCatalog;

public class BeanPQELIMINAR extends BeanPQGeneral {

	public static final String PROCEDURE = "ELIMINAR";

	public List<Object> execute(Class<?> claseCursor) {
		RespuestaGenerica respuestaGenerica = ejecutarProc(this, SCHEMA, ValoresCatalog.PQ_NOTIFICACION, PROCEDURE,
				claseCursor, true);
		return respuestaGenerica.getListaCursor();
	}

	public void execute() {
		ejecutarProc(this, SCHEMA, ValoresCatalog.PQ_NOTIFICACION, PROCEDURE, null, true);
	}

	private BigDecimal P_ID_NOTIFICACION;

	public BigDecimal getP_ID_NOTIFICACION() {
		return P_ID_NOTIFICACION;
	}

	public void setP_ID_NOTIFICACION(BigDecimal P_ID_NOTIFICACION) {
		this.P_ID_NOTIFICACION = P_ID_NOTIFICACION;
	}

}