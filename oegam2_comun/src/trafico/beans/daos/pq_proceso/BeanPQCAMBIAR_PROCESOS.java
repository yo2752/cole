package trafico.beans.daos.pq_proceso;

import general.beans.RespuestaGenerica;

import java.math.BigDecimal;
import java.util.List;

import trafico.beans.daos.BeanPQGeneral;
import utilidades.constantes.ValoresCatalog;

public class BeanPQCAMBIAR_PROCESOS extends BeanPQGeneral {

	public static final String PROCEDURE = "CAMBIAR_PROCESOS";

	public List<Object> execute(Class<?> claseCursor) {
		RespuestaGenerica respuestaGenerica = ejecutarProc(this, SCHEMA, ValoresCatalog.PQ_PROCESO, PROCEDURE,
				claseCursor, true);
		return respuestaGenerica.getListaCursor();
	}

	public void execute() {
		ejecutarProc(this, SCHEMA, ValoresCatalog.PQ_PROCESO, PROCEDURE, null, true);
	}

	private String P_NODO;

	private String P_PROCESO;

	private String P_DESCRIPCION;

	private BigDecimal P_ESTADO;

	private BigDecimal P_HILOS_COLAS;

	private String P_CLASE;

	private BigDecimal P_N_INTENTOS_MAX;

	private String P_HORA_INICIO;

	private String P_HORA_FIN;

	private BigDecimal P_TIEMPO_CORRECTO;

	private BigDecimal P_TIEMPO_ERRONEO_RECUPERABLE;

	private BigDecimal P_TIEMPO_ERR_NO_RECUPERABLE;

	private BigDecimal P_TIEMPO_SIN_DATOS;

	private BigDecimal P_TIPO;

	public String getP_NODO() {
		return P_NODO;
	}

	public void setP_NODO(String P_NODO) {
		this.P_NODO = P_NODO;
	}

	public String getP_PROCESO() {
		return P_PROCESO;
	}

	public void setP_PROCESO(String P_PROCESO) {
		this.P_PROCESO = P_PROCESO;
	}

	public String getP_DESCRIPCION() {
		return P_DESCRIPCION;
	}

	public void setP_DESCRIPCION(String P_DESCRIPCION) {
		this.P_DESCRIPCION = P_DESCRIPCION;
	}

	public BigDecimal getP_ESTADO() {
		return P_ESTADO;
	}

	public void setP_ESTADO(BigDecimal P_ESTADO) {
		this.P_ESTADO = P_ESTADO;
	}

	public BigDecimal getP_HILOS_COLAS() {
		return P_HILOS_COLAS;
	}

	public void setP_HILOS_COLAS(BigDecimal P_HILOS_COLAS) {
		this.P_HILOS_COLAS = P_HILOS_COLAS;
	}

	public String getP_CLASE() {
		return P_CLASE;
	}

	public void setP_CLASE(String P_CLASE) {
		this.P_CLASE = P_CLASE;
	}

	public BigDecimal getP_N_INTENTOS_MAX() {
		return P_N_INTENTOS_MAX;
	}

	public void setP_N_INTENTOS_MAX(BigDecimal P_N_INTENTOS_MAX) {
		this.P_N_INTENTOS_MAX = P_N_INTENTOS_MAX;
	}

	public String getP_HORA_INICIO() {
		return P_HORA_INICIO;
	}

	public void setP_HORA_INICIO(String P_HORA_INICIO) {
		this.P_HORA_INICIO = P_HORA_INICIO;
	}

	public String getP_HORA_FIN() {
		return P_HORA_FIN;
	}

	public void setP_HORA_FIN(String P_HORA_FIN) {
		this.P_HORA_FIN = P_HORA_FIN;
	}

	public BigDecimal getP_TIEMPO_CORRECTO() {
		return P_TIEMPO_CORRECTO;
	}

	public void setP_TIEMPO_CORRECTO(BigDecimal P_TIEMPO_CORRECTO) {
		this.P_TIEMPO_CORRECTO = P_TIEMPO_CORRECTO;
	}

	public BigDecimal getP_TIEMPO_ERRONEO_RECUPERABLE() {
		return P_TIEMPO_ERRONEO_RECUPERABLE;
	}

	public void setP_TIEMPO_ERRONEO_RECUPERABLE(BigDecimal P_TIEMPO_ERRONEO_RECUPERABLE) {
		this.P_TIEMPO_ERRONEO_RECUPERABLE = P_TIEMPO_ERRONEO_RECUPERABLE;
	}

	public BigDecimal getP_TIEMPO_ERR_NO_RECUPERABLE() {
		return P_TIEMPO_ERR_NO_RECUPERABLE;
	}

	public void setP_TIEMPO_ERR_NO_RECUPERABLE(BigDecimal P_TIEMPO_ERR_NO_RECUPERABLE) {
		this.P_TIEMPO_ERR_NO_RECUPERABLE = P_TIEMPO_ERR_NO_RECUPERABLE;
	}

	public BigDecimal getP_TIEMPO_SIN_DATOS() {
		return P_TIEMPO_SIN_DATOS;
	}

	public void setP_TIEMPO_SIN_DATOS(BigDecimal P_TIEMPO_SIN_DATOS) {
		this.P_TIEMPO_SIN_DATOS = P_TIEMPO_SIN_DATOS;
	}

	public BigDecimal getP_TIPO() {
		return P_TIPO;
	}

	public void setP_TIPO(BigDecimal P_TIPO) {
		this.P_TIPO = P_TIPO;
	}

}