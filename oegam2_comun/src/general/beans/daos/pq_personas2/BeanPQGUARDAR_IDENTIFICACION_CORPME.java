package general.beans.daos.pq_personas2;

import general.beans.RespuestaGenerica;

import java.util.List;

import trafico.beans.daos.BeanPQGeneral;
import utilidades.constantes.ValoresCatalog;

public class BeanPQGUARDAR_IDENTIFICACION_CORPME extends BeanPQGeneral {

	public static final String PROCEDURE = "GUARDAR_IDENTIFICACION_CORPME";

	public List<Object> execute(Class<?> claseCursor) {
		RespuestaGenerica respuestaGenerica = ejecutarProc(this, SCHEMA, ValoresCatalog.PQ_PERSONAS, PROCEDURE, claseCursor, true);
		return respuestaGenerica.getListaCursor();
	}

	public void execute() {
		ejecutarProc(this, SCHEMA, ValoresCatalog.PQ_PERSONAS, PROCEDURE, null, true);
	}

	private String P_NUM_COLEGIADO;

	private String P_NIF;

	private String P_NCORPME;

	private String P_USUARIO_CORPME;

	private String P_PASSWORD_CORPME;

	public String getP_NUM_COLEGIADO() {
		return P_NUM_COLEGIADO;
	}

	public void setP_NUM_COLEGIADO(String P_NUM_COLEGIADO) {
		this.P_NUM_COLEGIADO = P_NUM_COLEGIADO;
	}

	public String getP_NIF() {
		return P_NIF;
	}

	public void setP_NIF(String P_NIF) {
		this.P_NIF = P_NIF;
	}

	public String getP_NCORPME() {
		return P_NCORPME;
	}

	public void setP_NCORPME(String P_NCORPME) {
		this.P_NCORPME = P_NCORPME;
	}

	public String getP_USUARIO_CORPME() {
		return P_USUARIO_CORPME;
	}

	public void setP_USUARIO_CORPME(String P_USUARIO_CORPME) {
		this.P_USUARIO_CORPME = P_USUARIO_CORPME;
	}

	public String getP_PASSWORD_CORPME() {
		return P_PASSWORD_CORPME;
	}

	public void setP_PASSWORD_CORPME(String P_PASSWORD_CORPME) {
		this.P_PASSWORD_CORPME = P_PASSWORD_CORPME;
	}
}