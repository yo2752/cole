package trafico.beans.daos.pq_tramite_trafico;

import general.beans.RespuestaGenerica;

import java.math.BigDecimal;
import java.util.List;

import trafico.beans.daos.BeanPQGeneral;
import utilidades.constantes.ValoresCatalog;

public class BeanPQGUARDAR_SOL_VEHICULO extends BeanPQGeneral {

	public static final String PROCEDURE = "GUARDAR_SOL_VEHICULO";

	public List<Object> execute(Class<?> claseCursor) {
		RespuestaGenerica respuestaGenerica = ejecutarProc(this, SCHEMA, ValoresCatalog.PQ_TRAMITE_TRAFICO, PROCEDURE, claseCursor, true);
		return respuestaGenerica.getListaCursor();
	}

	public void execute() {
		ejecutarProc(this, SCHEMA, ValoresCatalog.PQ_TRAMITE_TRAFICO, PROCEDURE, null, true);
	}

	private BigDecimal P_ID_USUARIO;
	private BigDecimal P_ID_CONTRATO_SESSION;
	private BigDecimal P_NUM_EXPEDIENTE;
	private BigDecimal P_ID_CONTRATO;
	private String P_TIPO_TRAMITE;
	private String P_NUM_COLEGIADO;
	private BigDecimal P_ID_VEHICULO;
	private String P_BASTIDOR;
	private String P_MATRICULA;
	private BigDecimal P_ESTADO;
	private String P_CODIGO_TASA;
	private String P_RESULTADO;
	private BigDecimal P_DIFERENCIA_ANYO;
	private String P_REFERENCIA;
	private String P_MOTIVO_INTEVE;
	private String P_NIVE;
	private String P_TIPO_INFORME;

	public BigDecimal getP_ID_USUARIO() {
		return P_ID_USUARIO;
	}

	public void setP_ID_USUARIO(BigDecimal P_ID_USUARIO) {
		this.P_ID_USUARIO = P_ID_USUARIO;
	}

	public BigDecimal getP_ID_CONTRATO_SESSION() {
		return P_ID_CONTRATO_SESSION;
	}

	public void setP_ID_CONTRATO_SESSION(BigDecimal P_ID_CONTRATO_SESSION) {
		this.P_ID_CONTRATO_SESSION = P_ID_CONTRATO_SESSION;
	}

	public BigDecimal getP_NUM_EXPEDIENTE() {
		return P_NUM_EXPEDIENTE;
	}

	public void setP_NUM_EXPEDIENTE(BigDecimal P_NUM_EXPEDIENTE) {
		this.P_NUM_EXPEDIENTE = P_NUM_EXPEDIENTE;
	}

	public BigDecimal getP_ID_CONTRATO() {
		return P_ID_CONTRATO;
	}

	public void setP_ID_CONTRATO(BigDecimal P_ID_CONTRATO) {
		this.P_ID_CONTRATO = P_ID_CONTRATO;
	}

	public String getP_TIPO_TRAMITE() {
		return P_TIPO_TRAMITE;
	}

	public void setP_TIPO_TRAMITE(String P_TIPO_TRAMITE) {
		this.P_TIPO_TRAMITE = P_TIPO_TRAMITE;
	}

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

	public String getP_BASTIDOR() {
		return P_BASTIDOR;
	}

	public void setP_BASTIDOR(String P_BASTIDOR) {
		this.P_BASTIDOR = P_BASTIDOR;
	}

	public String getP_MATRICULA() {
		return P_MATRICULA;
	}

	public void setP_MATRICULA(String P_MATRICULA) {
		this.P_MATRICULA = P_MATRICULA;
	}

	public BigDecimal getP_ESTADO() {
		return P_ESTADO;
	}

	public void setP_ESTADO(BigDecimal P_ESTADO) {
		this.P_ESTADO = P_ESTADO;
	}

	public String getP_CODIGO_TASA() {
		return P_CODIGO_TASA;
	}

	public void setP_CODIGO_TASA(String P_CODIGO_TASA) {
		this.P_CODIGO_TASA = P_CODIGO_TASA;
	}

	public String getP_RESULTADO() {
		return P_RESULTADO;
	}

	public void setP_RESULTADO(String P_RESULTADO) {
		this.P_RESULTADO = P_RESULTADO;
	}

	public BigDecimal getP_DIFERENCIA_ANYO() {
		return P_DIFERENCIA_ANYO;
	}

	public void setP_DIFERENCIA_ANYO(BigDecimal pDIFERENCIAANYO) {
		P_DIFERENCIA_ANYO = pDIFERENCIAANYO;
	}

	public String getP_REFERENCIA() {
		return P_REFERENCIA;
	}

	public void setP_REFERENCIA(String p_REFERENCIA) {
		P_REFERENCIA = p_REFERENCIA;
	}

	public String getP_MOTIVO_INTEVE() {
		return P_MOTIVO_INTEVE;
	}

	public void setP_MOTIVO_INTEVE(String p_MOTIVO_INTEVE) {
		P_MOTIVO_INTEVE = p_MOTIVO_INTEVE;
	}

	public String getP_NIVE() {
		return P_NIVE;
	}

	public void setP_NIVE(String p_NIVE) {
		P_NIVE = p_NIVE;
	}

	public String getP_TIPO_INFORME() {
		return P_TIPO_INFORME;
	}

	public void setP_TIPO_INFORME(String p_TIPO_INFORME) {
		P_TIPO_INFORME = p_TIPO_INFORME;
	}
}