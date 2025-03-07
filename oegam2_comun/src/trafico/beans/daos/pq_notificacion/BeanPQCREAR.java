package trafico.beans.daos.pq_notificacion;

import general.beans.RespuestaGenerica;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import trafico.beans.daos.BeanPQGeneral;
import utilidades.constantes.ValoresCatalog;

public class BeanPQCREAR extends BeanPQGeneral {

	public static final String PROCEDURE = "CREAR";

	public List<Object> execute(Class<?> claseCursor) {
		RespuestaGenerica respuestaGenerica = ejecutarProc(this, SCHEMA, ValoresCatalog.PQ_NOTIFICACION, PROCEDURE,
				claseCursor, true);
		return respuestaGenerica.getListaCursor();
	}

	public void execute() {
		ejecutarProc(this, SCHEMA, ValoresCatalog.PQ_NOTIFICACION, PROCEDURE, null, true);
	}

	private BigDecimal P_ID_USUARIO;

	private String P_TIPO_TRAMITE;

	private BigDecimal P_ID_TRAMITE;

	private Timestamp P_FECHA_HORA;

	private BigDecimal P_ESTADO_ANT;

	private BigDecimal P_ESTADO_NUE;

	public BigDecimal getP_ID_USUARIO() {
		return P_ID_USUARIO;
	}

	public void setP_ID_USUARIO(BigDecimal P_ID_USUARIO) {
		this.P_ID_USUARIO = P_ID_USUARIO;
	}

	public String getP_TIPO_TRAMITE() {
		return P_TIPO_TRAMITE;
	}

	public void setP_TIPO_TRAMITE(String P_TIPO_TRAMITE) {
		this.P_TIPO_TRAMITE = P_TIPO_TRAMITE;
	}

	public BigDecimal getP_ID_TRAMITE() {
		return P_ID_TRAMITE;
	}

	public void setP_ID_TRAMITE(BigDecimal P_ID_TRAMITE) {
		this.P_ID_TRAMITE = P_ID_TRAMITE;
	}

	public Timestamp getP_FECHA_HORA() {
		return P_FECHA_HORA;
	}

	public void setP_FECHA_HORA(Timestamp P_FECHA_HORA) {
		this.P_FECHA_HORA = P_FECHA_HORA;
	}

	public BigDecimal getP_ESTADO_ANT() {
		return P_ESTADO_ANT;
	}

	public void setP_ESTADO_ANT(BigDecimal P_ESTADO_ANT) {
		this.P_ESTADO_ANT = P_ESTADO_ANT;
	}

	public BigDecimal getP_ESTADO_NUE() {
		return P_ESTADO_NUE;
	}

	public void setP_ESTADO_NUE(BigDecimal P_ESTADO_NUE) {
		this.P_ESTADO_NUE = P_ESTADO_NUE;
	}

}