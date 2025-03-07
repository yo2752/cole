package trafico.beans.daos.pq_contratos;

import general.beans.RespuestaGenerica;

import java.math.BigDecimal;
import java.util.List;

import trafico.beans.daos.BeanPQGeneral;
import utilidades.constantes.ValoresCatalog;

public class BeanPQMODIFICACION_EMAIL extends BeanPQGeneral {

	public static final String PROCEDURE = "MODIFICACION_EMAIL";

	public List<Object> execute(Class<?> claseCursor) {
		RespuestaGenerica respuestaGenerica = ejecutarProc(this, SCHEMA, ValoresCatalog.PQ_CONTRATOS, PROCEDURE,
				claseCursor, true);
		return respuestaGenerica.getListaCursor();
	}

	public void execute() {
		ejecutarProc(this, SCHEMA, ValoresCatalog.PQ_CONTRATOS, PROCEDURE, null, true);
	}

	private String P_CORREO_ELECTRONICO;

	private BigDecimal P_ID_CONTRATO;

	public String getP_CORREO_ELECTRONICO() {
		return P_CORREO_ELECTRONICO;
	}

	public void setP_CORREO_ELECTRONICO(String pCORREOELECTRONICO) {
		P_CORREO_ELECTRONICO = pCORREOELECTRONICO;
	}

	public BigDecimal getP_ID_CONTRATO() {
		return P_ID_CONTRATO;
	}

	public void setP_ID_CONTRATO(BigDecimal pIDCONTRATO) {
		P_ID_CONTRATO = pIDCONTRATO;
	}

}