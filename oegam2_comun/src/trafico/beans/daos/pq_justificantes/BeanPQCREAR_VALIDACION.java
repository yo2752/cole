package trafico.beans.daos.pq_justificantes;

import general.beans.RespuestaGenerica;

import java.util.List;

import trafico.beans.daos.BeanPQGeneral;
import utilidades.constantes.ValoresCatalog;

public class BeanPQCREAR_VALIDACION extends BeanPQGeneral {

	public static final String PROCEDURE = "CREAR_VALIDACION";

	public List<Object> execute(Class<?> claseCursor) {
		RespuestaGenerica respuestaGenerica = ejecutarProc(this, SCHEMA, ValoresCatalog.PQ_JUSTIFICANTES, PROCEDURE,
				claseCursor, true);
		return respuestaGenerica.getListaCursor();
	}

	public void execute() {
		ejecutarProc(this, SCHEMA, ValoresCatalog.PQ_JUSTIFICANTES, PROCEDURE, null, true);
	}

	private String P_CODIGO_VERIFICACION;

	private String P_VERIFICADO;

	public String getP_CODIGO_VERIFICACION() {
		return P_CODIGO_VERIFICACION;
	}

	public void setP_CODIGO_VERIFICACION(String pCODIGOVERIFICACION) {
		P_CODIGO_VERIFICACION = pCODIGOVERIFICACION;
	}

	public String getP_VERIFICADO() {
		return P_VERIFICADO;
	}

	public void setP_VERIFICADO(String P_VERIFICADO) {
		this.P_VERIFICADO = P_VERIFICADO;
	}

}