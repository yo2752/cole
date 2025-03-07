package trafico.beans.daos.pq_notificacion;

import general.beans.RespuestaGenerica;

import java.math.BigDecimal;
import java.util.List;

import trafico.beans.daos.BeanPQGeneral;
import utilidades.constantes.ValoresCatalog;

public class BeanPQBUSCAR extends BeanPQGeneral {

	public static final String PROCEDURE = "BUSCAR";

	public List<Object> execute(Class<?> claseCursor) {
		RespuestaGenerica respuestaGenerica = ejecutarProc(this, SCHEMA, ValoresCatalog.PQ_NOTIFICACION, PROCEDURE,
				claseCursor, true);
		return respuestaGenerica.getListaCursor();
	}

	public void execute() {
		ejecutarProc(this, SCHEMA, ValoresCatalog.PQ_NOTIFICACION, PROCEDURE, null, true);
	}

	private BigDecimal P_ID_USUARIO;

	private BigDecimal PAGINA;

	private BigDecimal NUM_REG;

	private String COLUMNA_ORDEN;

	private String ORDEN;

	private BigDecimal CUENTA;

	private Object C_NOTIFICACIONES;

	public BigDecimal getP_ID_USUARIO() {
		return P_ID_USUARIO;
	}

	public void setP_ID_USUARIO(BigDecimal P_ID_USUARIO) {
		this.P_ID_USUARIO = P_ID_USUARIO;
	}

	public BigDecimal getPAGINA() {
		return PAGINA;
	}

	public void setPAGINA(BigDecimal PAGINA) {
		this.PAGINA = PAGINA;
	}

	public BigDecimal getNUM_REG() {
		return NUM_REG;
	}

	public void setNUM_REG(BigDecimal NUM_REG) {
		this.NUM_REG = NUM_REG;
	}

	public String getCOLUMNA_ORDEN() {
		return COLUMNA_ORDEN;
	}

	public void setCOLUMNA_ORDEN(String COLUMNA_ORDEN) {
		this.COLUMNA_ORDEN = COLUMNA_ORDEN;
	}

	public String getORDEN() {
		return ORDEN;
	}

	public void setORDEN(String ORDEN) {
		this.ORDEN = ORDEN;
	}

	public BigDecimal getCUENTA() {
		return CUENTA;
	}

	public void setCUENTA(BigDecimal CUENTA) {
		this.CUENTA = CUENTA;
	}

	public Object getC_NOTIFICACIONES() {
		return C_NOTIFICACIONES;
	}

	public void setC_NOTIFICACIONES(Object C_NOTIFICACIONES) {
		this.C_NOTIFICACIONES = C_NOTIFICACIONES;
	}

}