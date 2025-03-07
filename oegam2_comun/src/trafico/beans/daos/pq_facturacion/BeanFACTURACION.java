package trafico.beans.daos.pq_facturacion;

import general.beans.RespuestaGenerica;

import java.math.BigDecimal;
import java.util.List;

import trafico.beans.daos.BeanPQGeneral;
import utilidades.constantes.ValoresCatalog;

public class BeanFACTURACION extends BeanPQGeneral {

	public static final String PROCEDURE = "GUARDAR_FACTURACION";

	public List<Object> execute(Class<?> claseCursor) {
		RespuestaGenerica respuestaGenerica = ejecutarProc(this, SCHEMA, ValoresCatalog.PQ_TRAMITE_TRAFICO, PROCEDURE,
				claseCursor, true);
		return respuestaGenerica.getListaCursor();
	}

	public void execute() {
		ejecutarProc(this, SCHEMA, ValoresCatalog.PQ_TRAMITE_TRAFICO, PROCEDURE, null, true);
	}

	private Object ROWID_FACTURACION;

	private String P_MATRICULA;

	private String P_BASTIDOR;

	private String P_NIF;

	private String P_TIPO_TRAMITE;

	private String P_CODIGO_TASA;

	private BigDecimal P_NUM_EXPEDIENTE;

	public Object getROWID_FACTURACION() {
		return ROWID_FACTURACION;
	}

	public void setROWID_FACTURACION(Object rOWIDFACTURACION) {
		ROWID_FACTURACION = rOWIDFACTURACION;
	}

	public String getP_MATRICULA() {
		return P_MATRICULA;
	}

	public void setP_MATRICULA(String pMATRICULA) {
		P_MATRICULA = pMATRICULA;
	}

	public String getP_BASTIDOR() {
		return P_BASTIDOR;
	}

	public void setP_BASTIDOR(String pBASTIDOR) {
		P_BASTIDOR = pBASTIDOR;
	}

	public String getP_NIF() {
		return P_NIF;
	}

	public void setP_NIF(String pNIF) {
		P_NIF = pNIF;
	}

	public String getP_TIPO_TRAMITE() {
		return P_TIPO_TRAMITE;
	}

	public void setP_TIPO_TRAMITE(String pTIPOTRAMITE) {
		P_TIPO_TRAMITE = pTIPOTRAMITE;
	}

	public String getP_CODIGO_TASA() {
		return P_CODIGO_TASA;
	}

	public void setP_CODIGO_TASA(String pCODIGOTASA) {
		P_CODIGO_TASA = pCODIGOTASA;
	}

	public BigDecimal getP_NUM_EXPEDIENTE() {
		return P_NUM_EXPEDIENTE;
	}

	public void setP_NUM_EXPEDIENTE(BigDecimal pNUMEXPEDIENTE) {
		P_NUM_EXPEDIENTE = pNUMEXPEDIENTE;
	}

}