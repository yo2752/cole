package trafico.beans.daos.pq_contratos;

import general.beans.RespuestaGenerica;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import trafico.beans.daos.BeanPQGeneral;
import utilidades.constantes.ValoresCatalog;

public class BeanPQGUARDAR extends BeanPQGeneral {

	public static final String PROCEDURE = "GUARDAR";

	public List<Object> execute(Class<?> claseCursor) {
		RespuestaGenerica respuestaGenerica = ejecutarProc(this, SCHEMA, ValoresCatalog.PQ_CONTRATOS, PROCEDURE,
				claseCursor, true);
		return respuestaGenerica.getListaCursor();
	}

	public void execute() {
		ejecutarProc(this, SCHEMA, ValoresCatalog.PQ_CONTRATOS, PROCEDURE, null, true);
	}

	private BigDecimal P_ID_CONTRATO;

	private BigDecimal P_ID_TIPO_CONTRATO;

	private BigDecimal P_ESTADO_CONTRATO;

	private String P_CIF;

	private String P_RAZON_SOCIAL;

	private String P_ANAGRAMA_CONTRATO;

	private String P_COLEGIO;

	private String P_ID_TIPO_VIA;

	private String P_VIA;

	private String P_NUMERO;

	private String P_LETRA;

	private String P_ESCALERA;

	private String P_PISO;

	private String P_PUERTA;

	private String P_ID_PROVINCIA;

	private String P_ID_MUNICIPIO;

	private String P_COD_POSTAL;

	private BigDecimal P_TELEFONO;

	private String P_CORREO_ELECTRONICO;

	private String P_JEFATURA_PROVINCIAL;

	private Timestamp P_FECHA_INICIO;

	private Timestamp P_FECHA_FIN;

	private String P_NUM_COLEGIADO;

	private BigDecimal P_ID_USUARIO;

	private BigDecimal P_ESTADO_USUARIO;

	private String P_NIF;

	private String P_APELLIDO1;

	private String P_APELLIDO2;

	private String P_NOMBRE;

	private String P_NCORPME;

	private String P_ANAGRAMA;

	private String P_CORREO_ELECTRONICO_USU;

	private Timestamp P_ULTIMA_CONEXION;

	private Timestamp P_FECHA_RENOVACION;

	private BigDecimal P_ID_USUARIO_CON;

	private BigDecimal P_ID_CONTRATO_CON;

	public BigDecimal getP_ID_CONTRATO() {
		return P_ID_CONTRATO;
	}

	public void setP_ID_CONTRATO(BigDecimal P_ID_CONTRATO) {
		this.P_ID_CONTRATO = P_ID_CONTRATO;
	}

	public BigDecimal getP_ID_TIPO_CONTRATO() {
		return P_ID_TIPO_CONTRATO;
	}

	public void setP_ID_TIPO_CONTRATO(BigDecimal P_ID_TIPO_CONTRATO) {
		this.P_ID_TIPO_CONTRATO = P_ID_TIPO_CONTRATO;
	}

	public BigDecimal getP_ESTADO_CONTRATO() {
		return P_ESTADO_CONTRATO;
	}

	public void setP_ESTADO_CONTRATO(BigDecimal P_ESTADO_CONTRATO) {
		this.P_ESTADO_CONTRATO = P_ESTADO_CONTRATO;
	}

	public String getP_CIF() {
		return P_CIF;
	}

	public void setP_CIF(String P_CIF) {
		this.P_CIF = P_CIF;
	}

	public String getP_RAZON_SOCIAL() {
		return P_RAZON_SOCIAL;
	}

	public void setP_RAZON_SOCIAL(String P_RAZON_SOCIAL) {
		this.P_RAZON_SOCIAL = P_RAZON_SOCIAL;
	}

	public String getP_ANAGRAMA_CONTRATO() {
		return P_ANAGRAMA_CONTRATO;
	}

	public void setP_ANAGRAMA_CONTRATO(String P_ANAGRAMA_CONTRATO) {
		this.P_ANAGRAMA_CONTRATO = P_ANAGRAMA_CONTRATO;
	}

	public String getP_COLEGIO() {
		return P_COLEGIO;
	}

	public void setP_COLEGIO(String P_COLEGIO) {
		this.P_COLEGIO = P_COLEGIO;
	}

	public String getP_ID_TIPO_VIA() {
		return P_ID_TIPO_VIA;
	}

	public void setP_ID_TIPO_VIA(String P_ID_TIPO_VIA) {
		this.P_ID_TIPO_VIA = P_ID_TIPO_VIA;
	}

	public String getP_VIA() {
		return P_VIA;
	}

	public void setP_VIA(String P_VIA) {
		this.P_VIA = P_VIA;
	}

	public String getP_NUMERO() {
		return P_NUMERO;
	}

	public void setP_NUMERO(String P_NUMERO) {
		this.P_NUMERO = P_NUMERO;
	}

	public String getP_LETRA() {
		return P_LETRA;
	}

	public void setP_LETRA(String P_LETRA) {
		this.P_LETRA = P_LETRA;
	}

	public String getP_ESCALERA() {
		return P_ESCALERA;
	}

	public void setP_ESCALERA(String P_ESCALERA) {
		this.P_ESCALERA = P_ESCALERA;
	}

	public String getP_PISO() {
		return P_PISO;
	}

	public void setP_PISO(String P_PISO) {
		this.P_PISO = P_PISO;
	}

	public String getP_PUERTA() {
		return P_PUERTA;
	}

	public void setP_PUERTA(String P_PUERTA) {
		this.P_PUERTA = P_PUERTA;
	}

	public String getP_ID_PROVINCIA() {
		return P_ID_PROVINCIA;
	}

	public void setP_ID_PROVINCIA(String P_ID_PROVINCIA) {
		this.P_ID_PROVINCIA = P_ID_PROVINCIA;
	}

	public String getP_ID_MUNICIPIO() {
		return P_ID_MUNICIPIO;
	}

	public void setP_ID_MUNICIPIO(String P_ID_MUNICIPIO) {
		this.P_ID_MUNICIPIO = P_ID_MUNICIPIO;
	}

	public String getP_COD_POSTAL() {
		return P_COD_POSTAL;
	}

	public void setP_COD_POSTAL(String P_COD_POSTAL) {
		this.P_COD_POSTAL = P_COD_POSTAL;
	}

	public BigDecimal getP_TELEFONO() {
		return P_TELEFONO;
	}

	public void setP_TELEFONO(BigDecimal P_TELEFONO) {
		this.P_TELEFONO = P_TELEFONO;
	}

	public String getP_CORREO_ELECTRONICO() {
		return P_CORREO_ELECTRONICO;
	}

	public void setP_CORREO_ELECTRONICO(String P_CORREO_ELECTRONICO) {
		this.P_CORREO_ELECTRONICO = P_CORREO_ELECTRONICO;
	}

	public String getP_JEFATURA_PROVINCIAL() {
		return P_JEFATURA_PROVINCIAL;
	}

	public void setP_JEFATURA_PROVINCIAL(String P_JEFATURA_PROVINCIAL) {
		this.P_JEFATURA_PROVINCIAL = P_JEFATURA_PROVINCIAL;
	}

	public Timestamp getP_FECHA_INICIO() {
		return P_FECHA_INICIO;
	}

	public void setP_FECHA_INICIO(Timestamp P_FECHA_INICIO) {
		this.P_FECHA_INICIO = P_FECHA_INICIO;
	}

	public Timestamp getP_FECHA_FIN() {
		return P_FECHA_FIN;
	}

	public void setP_FECHA_FIN(Timestamp P_FECHA_FIN) {
		this.P_FECHA_FIN = P_FECHA_FIN;
	}

	public String getP_NUM_COLEGIADO() {
		return P_NUM_COLEGIADO;
	}

	public void setP_NUM_COLEGIADO(String P_NUM_COLEGIADO) {
		this.P_NUM_COLEGIADO = P_NUM_COLEGIADO;
	}

	public BigDecimal getP_ID_USUARIO() {
		return P_ID_USUARIO;
	}

	public void setP_ID_USUARIO(BigDecimal P_ID_USUARIO) {
		this.P_ID_USUARIO = P_ID_USUARIO;
	}

	public BigDecimal getP_ESTADO_USUARIO() {
		return P_ESTADO_USUARIO;
	}

	public void setP_ESTADO_USUARIO(BigDecimal P_ESTADO_USUARIO) {
		this.P_ESTADO_USUARIO = P_ESTADO_USUARIO;
	}

	public String getP_NIF() {
		return P_NIF;
	}

	public void setP_NIF(String P_NIF) {
		this.P_NIF = P_NIF;
	}

	public String getP_APELLIDO1() {
		return P_APELLIDO1;
	}

	public void setP_APELLIDO1(String P_APELLIDO1) {
		this.P_APELLIDO1 = P_APELLIDO1;
	}

	public String getP_APELLIDO2() {
		return P_APELLIDO2;
	}

	public void setP_APELLIDO2(String P_APELLIDO2) {
		this.P_APELLIDO2 = P_APELLIDO2;
	}

	public String getP_NOMBRE() {
		return P_NOMBRE;
	}

	public void setP_NOMBRE(String P_NOMBRE) {
		this.P_NOMBRE = P_NOMBRE;
	}

	public String getP_NCORPME() {
		return P_NCORPME;
	}

	public void setP_NCORPME(String P_NCORPME) {
		this.P_NCORPME = P_NCORPME;
	}

	public String getP_ANAGRAMA() {
		return P_ANAGRAMA;
	}

	public void setP_ANAGRAMA(String P_ANAGRAMA) {
		this.P_ANAGRAMA = P_ANAGRAMA;
	}

	public String getP_CORREO_ELECTRONICO_USU() {
		return P_CORREO_ELECTRONICO_USU;
	}

	public void setP_CORREO_ELECTRONICO_USU(String P_CORREO_ELECTRONICO_USU) {
		this.P_CORREO_ELECTRONICO_USU = P_CORREO_ELECTRONICO_USU;
	}

	public Timestamp getP_ULTIMA_CONEXION() {
		return P_ULTIMA_CONEXION;
	}

	public void setP_ULTIMA_CONEXION(Timestamp P_ULTIMA_CONEXION) {
		this.P_ULTIMA_CONEXION = P_ULTIMA_CONEXION;
	}

	public Timestamp getP_FECHA_RENOVACION() {
		return P_FECHA_RENOVACION;
	}

	public void setP_FECHA_RENOVACION(Timestamp P_FECHA_RENOVACION) {
		this.P_FECHA_RENOVACION = P_FECHA_RENOVACION;
	}

	public BigDecimal getP_ID_USUARIO_CON() {
		return P_ID_USUARIO_CON;
	}

	public void setP_ID_USUARIO_CON(BigDecimal P_ID_USUARIO_CON) {
		this.P_ID_USUARIO_CON = P_ID_USUARIO_CON;
	}

	public BigDecimal getP_ID_CONTRATO_CON() {
		return P_ID_CONTRATO_CON;
	}

	public void setP_ID_CONTRATO_CON(BigDecimal P_ID_CONTRATO_CON) {
		this.P_ID_CONTRATO_CON = P_ID_CONTRATO_CON;
	}

}