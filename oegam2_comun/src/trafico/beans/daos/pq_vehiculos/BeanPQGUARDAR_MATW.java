package trafico.beans.daos.pq_vehiculos;

import general.beans.RespuestaGenerica;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import trafico.beans.daos.BeanPQGeneral;
import utilidades.constantes.ValoresCatalog;

/**
 * @author usuario
 *
 */
public class BeanPQGUARDAR_MATW extends BeanPQGeneral {

	public static final String PROCEDURE = "GUARDAR_MATW";

	public List<Object> execute(Class<?> claseCursor) {
		RespuestaGenerica respuestaGenerica = ejecutarProc(this, SCHEMA, ValoresCatalog.PQ_VEHICULOS, PROCEDURE,
				claseCursor, true);
		return respuestaGenerica.getListaCursor();
	}

	public void execute() {
		ejecutarProc(this, SCHEMA, ValoresCatalog.PQ_VEHICULOS, PROCEDURE, null, true);
	}

	private BigDecimal P_ID_USUARIO;

	private String P_NIVE;

	private BigDecimal P_ID_CONTRATO_SESSION;

	private BigDecimal P_NUM_EXPEDIENTE;

	private BigDecimal P_ID_CONTRATO;

	private String P_TIPO_TRAMITE;

	private String P_NUM_COLEGIADO;

	private BigDecimal P_ID_VEHICULO;

	private String P_BASTIDOR;

	private String P_MATRICULA;

	private BigDecimal P_CODIGO_MARCA;

	private String P_MODELO;

	private String P_TIPVEHI;

	private String P_CDMARCA;

	private String P_CDMODVEH;

	private Timestamp P_FECDESDE;

	private String P_TIPO_VEHICULO;

	private String P_ID_SERVICIO;

	private String P_ID_SERVICIO_ANTERIOR;

	private Timestamp P_FECHA_MATRICULACION;

	private String P_VEHICULO_AGRICOLA;

	private String P_VEHICULO_TRANSPORTE;

	private String P_ID_COLOR;

	private String P_ID_CARBURANTE;

	private String P_ID_PROCEDENCIA;

	private String P_PROCEDENCIA;

	private String P_ID_LUGAR_ADQUISICION;

	private String P_ID_CRITERIO_CONSTRUCCION;

	private String P_ID_CRITERIO_UTILIZACION;

	private String P_ID_DIRECTIVA_CEE;

	private String P_DIPLOMATICO;

	private BigDecimal P_PLAZAS;

	private String P_CODITV;

	private String P_TIPO_ITV;

	private BigDecimal P_POTENCIA_FISCAL;

	private BigDecimal P_POTENCIA_NETA;

	private BigDecimal P_POTENCIA_PESO;

	private String P_CILINDRADA;

	private String P_CO2;

	private String P_TARA;

	private String P_PESO_MMA;

	private String P_MTMA_ITV;

	private String P_VARIANTE;

	private String P_VERSION;

	private String P_EXCESO_PESO;

	private String P_TIPO_INDUSTRIA;

	private String P_ID_MOTIVO_ITV;

	private Timestamp P_FECHA_ITV;

	private String P_CLASIFICACION_ITV;

	private String P_ID_TIPO_TARJETA_ITV;

	private String P_CONCEPTO_ITV;

	private String P_ESTACION_ITV;

	private Timestamp P_FECHA_INSPECCION;

	private BigDecimal P_N_PLAZAS_PIE;

	private String P_N_HOMOLOGACION;

	private BigDecimal P_N_RUEDAS;

	private String P_N_SERIE;

	private String P_ID_EPIGRAFE;

	private String P_NIF_INTEGRADOR;

	private String P_VEHI_USADO;

	private String P_MATRI_AYUNTAMIENTO;

	private Timestamp P_LIMITE_MATR_TURIS;

	private Timestamp P_FECHA_PRIM_MATRI;

	private String P_ID_LUGAR_MATRICULACION;

	private BigDecimal P_KM_USO;

	private BigDecimal P_HORAS_USO;

	private BigDecimal P_ID_VEHICULO_PREVER;

	private BigDecimal P_N_CILINDROS;

	private String P_CARACTERISTICAS;

	private String P_ANIO_FABRICA;

	private BigDecimal P_ID_DIRECCION;

	private String P_ID_PROVINCIA;

	private String P_ID_MUNICIPIO;

	private String P_ID_TIPO_VIA;

	private String P_NOMBRE_VIA;

	private String P_NUMERO;

	private String P_COD_POSTAL;

	private String P_PUEBLO;

	private String P_LETRA;

	private String P_ESCALERA;

	private String P_BLOQUE;

	private String P_PLANTA;

	private String P_PUERTA;

	private BigDecimal P_NUM_LOCAL;

	private BigDecimal P_KM;

	private BigDecimal P_HM;

	private String P_PREV_MATRICULA;

	private String P_PREV_BASTIDOR;

	private BigDecimal P_PREV_CODIGO_MARCA;

	private String P_PREV_MODELO;

	private String P_PREV_ID_CONSTRUCCION;

	private String P_PREV_ID_UTILIZACION;

	private String P_PREV_CLASIFICACION_ITV;

	private String P_PREV_TIPO_ITV;

	private String P_TIPO_PERSONA_INTE;

	private String P_APELLIDO1_RAZON_SOCIAL_INTE;

	private String P_APELLIDO2_INTE;

	private String P_NOMBRE_INTE;

	// MATE 2.5
	private String P_IMPORTADO;
	private String P_SUBASTADO;
	private String P_FABRICACION;
	private String P_PAIS_IMPORTACION;
	private String P_FABRICANTE;
	private String P_CARROCERIA;
	private BigDecimal P_CONSUMO;
	private BigDecimal P_DIST_EJES;
	private BigDecimal P_VIA_ANT;
	private BigDecimal P_VIA_POS;
	private String P_ALIMENTACION;
	private String P_EMISIONES;
	private String P_ECO_INNOVACION;
	private BigDecimal P_REDUCCION_ECO;
	private String P_CODIGO_ECO;
	private BigDecimal P_MOM;
	private BigDecimal P_KILOMETROS;
	private String P_CHECK_FECHA_CADUCIDAD_ITV;
	private String P_PROVINCIA_PRIMERA_MATRICULA;
	private String P_DIFERENCIA_ANYO;

	// MATW
	// DESAPARECEN DE LA VERSION INICIAL DE MATW
//	private Timestamp P_FECHA_INICIAL_ITV;
//	private String P_ID_PROVINCIA_ITV;
	private String P_MATRICULA_ORIGEN;
	private String P_PAIS_FABRICACION;
	private String P_MATRICULA_ORIG_EXTR;
	private BigDecimal P_FICHA_TECNICA_RD750;

	private BigDecimal P_BASTIDOR_MATRICULADO;

	// VEHICULOS MULTIFASICOS
	private BigDecimal P_CODIGO_MARCA_BASE;
	private String P_FABRICANTE_BASE;
	private String P_TIPO_BASE;
	private String P_VARIANTE_BASE;
	private String P_VERSION_BASE;
	private String P_N_HOMOLOGACION_BASE;
	private BigDecimal P_MOM_BASE;
	private String P_CATEGORIA_ELECTRICA;
	private BigDecimal P_AUTONOMIA_ELECTRICA;

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

	public BigDecimal getP_CODIGO_MARCA() {
		return P_CODIGO_MARCA;
	}

	public void setP_CODIGO_MARCA(BigDecimal P_CODIGO_MARCA) {
		this.P_CODIGO_MARCA = P_CODIGO_MARCA;
	}

	public String getP_MODELO() {
		return P_MODELO;
	}

	public void setP_MODELO(String P_MODELO) {
		this.P_MODELO = P_MODELO;
	}

	public String getP_TIPVEHI() {
		return P_TIPVEHI;
	}

	public void setP_TIPVEHI(String P_TIPVEHI) {
		this.P_TIPVEHI = P_TIPVEHI;
	}

	public String getP_CDMARCA() {
		return P_CDMARCA;
	}

	public void setP_CDMARCA(String P_CDMARCA) {
		this.P_CDMARCA = P_CDMARCA;
	}

	public String getP_CDMODVEH() {
		return P_CDMODVEH;
	}

	public void setP_CDMODVEH(String P_CDMODVEH) {
		this.P_CDMODVEH = P_CDMODVEH;
	}

	public Timestamp getP_FECDESDE() {
		return P_FECDESDE;
	}

	public void setP_FECDESDE(Timestamp P_FECDESDE) {
		this.P_FECDESDE = P_FECDESDE;
	}

	public String getP_TIPO_VEHICULO() {
		return P_TIPO_VEHICULO;
	}

	public void setP_TIPO_VEHICULO(String P_TIPO_VEHICULO) {
		this.P_TIPO_VEHICULO = P_TIPO_VEHICULO;
	}

	public String getP_ID_SERVICIO() {
		return P_ID_SERVICIO;
	}

	public void setP_ID_SERVICIO(String P_ID_SERVICIO) {
		this.P_ID_SERVICIO = P_ID_SERVICIO;
	}

	public String getP_ID_SERVICIO_ANTERIOR() {
		return P_ID_SERVICIO_ANTERIOR;
	}

	public void setP_ID_SERVICIO_ANTERIOR(String P_ID_SERVICIO_ANTERIOR) {
		this.P_ID_SERVICIO_ANTERIOR = P_ID_SERVICIO_ANTERIOR;
	}

	public Timestamp getP_FECHA_MATRICULACION() {
		return P_FECHA_MATRICULACION;
	}

	public void setP_FECHA_MATRICULACION(Timestamp P_FECHA_MATRICULACION) {
		this.P_FECHA_MATRICULACION = P_FECHA_MATRICULACION;
	}

	public String getP_VEHICULO_AGRICOLA() {
		return P_VEHICULO_AGRICOLA;
	}

	public void setP_VEHICULO_AGRICOLA(String P_VEHICULO_AGRICOLA) {
		this.P_VEHICULO_AGRICOLA = P_VEHICULO_AGRICOLA;
	}

	public String getP_VEHICULO_TRANSPORTE() {
		return P_VEHICULO_TRANSPORTE;
	}

	public void setP_VEHICULO_TRANSPORTE(String P_VEHICULO_TRANSPORTE) {
		this.P_VEHICULO_TRANSPORTE = P_VEHICULO_TRANSPORTE;
	}

	public String getP_NIVE() {
		return P_NIVE;
	}

	public void setP_NIVE(String pNIVE) {
		P_NIVE = pNIVE;
	}

	public String getP_ID_COLOR() {
		return P_ID_COLOR;
	}

	public void setP_ID_COLOR(String P_ID_COLOR) {
		this.P_ID_COLOR = P_ID_COLOR;
	}

	public String getP_ID_CARBURANTE() {
		return P_ID_CARBURANTE;
	}

	public void setP_ID_CARBURANTE(String P_ID_CARBURANTE) {
		this.P_ID_CARBURANTE = P_ID_CARBURANTE;
	}

	public String getP_ID_PROCEDENCIA() {
		return P_ID_PROCEDENCIA;
	}

	public void setP_ID_PROCEDENCIA(String P_ID_PROCEDENCIA) {
		this.P_ID_PROCEDENCIA = P_ID_PROCEDENCIA;
	}

	public String getP_ID_LUGAR_ADQUISICION() {
		return P_ID_LUGAR_ADQUISICION;
	}

	public void setP_ID_LUGAR_ADQUISICION(String P_ID_LUGAR_ADQUISICION) {
		this.P_ID_LUGAR_ADQUISICION = P_ID_LUGAR_ADQUISICION;
	}

	public String getP_ID_CRITERIO_CONSTRUCCION() {
		return P_ID_CRITERIO_CONSTRUCCION;
	}

	public void setP_ID_CRITERIO_CONSTRUCCION(String P_ID_CRITERIO_CONSTRUCCION) {
		this.P_ID_CRITERIO_CONSTRUCCION = P_ID_CRITERIO_CONSTRUCCION;
	}

	public String getP_ID_CRITERIO_UTILIZACION() {
		return P_ID_CRITERIO_UTILIZACION;
	}

	public void setP_ID_CRITERIO_UTILIZACION(String P_ID_CRITERIO_UTILIZACION) {
		this.P_ID_CRITERIO_UTILIZACION = P_ID_CRITERIO_UTILIZACION;
	}

	public String getP_ID_DIRECTIVA_CEE() {
		return P_ID_DIRECTIVA_CEE;
	}

	public void setP_ID_DIRECTIVA_CEE(String P_ID_DIRECTIVA_CEE) {
		this.P_ID_DIRECTIVA_CEE = P_ID_DIRECTIVA_CEE;
	}

	public String getP_DIPLOMATICO() {
		return P_DIPLOMATICO;
	}

	public void setP_DIPLOMATICO(String P_DIPLOMATICO) {
		this.P_DIPLOMATICO = P_DIPLOMATICO;
	}

	public BigDecimal getP_PLAZAS() {
		return P_PLAZAS;
	}

	public void setP_PLAZAS(BigDecimal P_PLAZAS) {
		this.P_PLAZAS = P_PLAZAS;
	}

	public String getP_CODITV() {
		return P_CODITV;
	}

	public void setP_CODITV(String P_CODITV) {
		this.P_CODITV = P_CODITV;
	}

	public String getP_TIPO_ITV() {
		return P_TIPO_ITV;
	}

	public void setP_TIPO_ITV(String P_TIPO_ITV) {
		this.P_TIPO_ITV = P_TIPO_ITV;
	}

	public BigDecimal getP_POTENCIA_FISCAL() {
		return P_POTENCIA_FISCAL;
	}

	public void setP_POTENCIA_FISCAL(BigDecimal P_POTENCIA_FISCAL) {
		this.P_POTENCIA_FISCAL = P_POTENCIA_FISCAL;
	}

	public BigDecimal getP_POTENCIA_NETA() {
		return P_POTENCIA_NETA;
	}

	public void setP_POTENCIA_NETA(BigDecimal P_POTENCIA_NETA) {
		this.P_POTENCIA_NETA = P_POTENCIA_NETA;
	}

	public BigDecimal getP_POTENCIA_PESO() {
		return P_POTENCIA_PESO;
	}

	public void setP_POTENCIA_PESO(BigDecimal P_POTENCIA_PESO) {
		this.P_POTENCIA_PESO = P_POTENCIA_PESO;
	}

	public String getP_CILINDRADA() {
		return P_CILINDRADA;
	}

	public void setP_CILINDRADA(String P_CILINDRADA) {
		this.P_CILINDRADA = P_CILINDRADA;
	}

	public String getP_CO2() {
		return P_CO2;
	}

	public void setP_CO2(String P_CO2) {
		this.P_CO2 = P_CO2;
	}

	public String getP_TARA() {
		return P_TARA;
	}

	public void setP_TARA(String P_TARA) {
		this.P_TARA = P_TARA;
	}

	public String getP_PESO_MMA() {
		return P_PESO_MMA;
	}

	public void setP_PESO_MMA(String P_PESO_MMA) {
		this.P_PESO_MMA = P_PESO_MMA;
	}

	public String getP_MTMA_ITV() {
		return P_MTMA_ITV;
	}

	public void setP_MTMA_ITV(String P_MTMA_ITV) {
		this.P_MTMA_ITV = P_MTMA_ITV;
	}

	public String getP_VARIANTE() {
		return P_VARIANTE;
	}

	public void setP_VARIANTE(String P_VARIANTE) {
		this.P_VARIANTE = P_VARIANTE;
	}

	public String getP_VERSION() {
		return P_VERSION;
	}

	public void setP_VERSION(String P_VERSION) {
		this.P_VERSION = P_VERSION;
	}

	public String getP_EXCESO_PESO() {
		return P_EXCESO_PESO;
	}

	public void setP_EXCESO_PESO(String P_EXCESO_PESO) {
		this.P_EXCESO_PESO = P_EXCESO_PESO;
	}

	public String getP_TIPO_INDUSTRIA() {
		return P_TIPO_INDUSTRIA;
	}

	public void setP_TIPO_INDUSTRIA(String P_TIPO_INDUSTRIA) {
		this.P_TIPO_INDUSTRIA = P_TIPO_INDUSTRIA;
	}

	public String getP_ID_MOTIVO_ITV() {
		return P_ID_MOTIVO_ITV;
	}

	public void setP_ID_MOTIVO_ITV(String P_ID_MOTIVO_ITV) {
		this.P_ID_MOTIVO_ITV = P_ID_MOTIVO_ITV;
	}

	public Timestamp getP_FECHA_ITV() {
		return P_FECHA_ITV;
	}

	public void setP_FECHA_ITV(Timestamp P_FECHA_ITV) {
		this.P_FECHA_ITV = P_FECHA_ITV;
	}

	public String getP_CLASIFICACION_ITV() {
		return P_CLASIFICACION_ITV;
	}

	public void setP_CLASIFICACION_ITV(String P_CLASIFICACION_ITV) {
		this.P_CLASIFICACION_ITV = P_CLASIFICACION_ITV;
	}

	public String getP_ID_TIPO_TARJETA_ITV() {
		return P_ID_TIPO_TARJETA_ITV;
	}

	public void setP_ID_TIPO_TARJETA_ITV(String P_ID_TIPO_TARJETA_ITV) {
		this.P_ID_TIPO_TARJETA_ITV = P_ID_TIPO_TARJETA_ITV;
	}

	public String getP_CONCEPTO_ITV() {
		return P_CONCEPTO_ITV;
	}

	public void setP_CONCEPTO_ITV(String P_CONCEPTO_ITV) {
		this.P_CONCEPTO_ITV = P_CONCEPTO_ITV;
	}

	public String getP_ESTACION_ITV() {
		return P_ESTACION_ITV;
	}

	public void setP_ESTACION_ITV(String P_ESTACION_ITV) {
		this.P_ESTACION_ITV = P_ESTACION_ITV;
	}

	public Timestamp getP_FECHA_INSPECCION() {
		return P_FECHA_INSPECCION;
	}

	public void setP_FECHA_INSPECCION(Timestamp P_FECHA_INSPECCION) {
		this.P_FECHA_INSPECCION = P_FECHA_INSPECCION;
	}

	public BigDecimal getP_N_PLAZAS_PIE() {
		return P_N_PLAZAS_PIE;
	}

	public void setP_N_PLAZAS_PIE(BigDecimal P_N_PLAZAS_PIE) {
		this.P_N_PLAZAS_PIE = P_N_PLAZAS_PIE;
	}

	public String getP_N_HOMOLOGACION() {
		return P_N_HOMOLOGACION;
	}

	public void setP_N_HOMOLOGACION(String P_N_HOMOLOGACION) {
		this.P_N_HOMOLOGACION = P_N_HOMOLOGACION;
	}

	public BigDecimal getP_N_RUEDAS() {
		return P_N_RUEDAS;
	}

	public void setP_N_RUEDAS(BigDecimal P_N_RUEDAS) {
		this.P_N_RUEDAS = P_N_RUEDAS;
	}

	public String getP_N_SERIE() {
		return P_N_SERIE;
	}

	public void setP_N_SERIE(String P_N_SERIE) {
		this.P_N_SERIE = P_N_SERIE;
	}

	public String getP_ID_EPIGRAFE() {
		return P_ID_EPIGRAFE;
	}

	public void setP_ID_EPIGRAFE(String P_ID_EPIGRAFE) {
		this.P_ID_EPIGRAFE = P_ID_EPIGRAFE;
	}

	public String getP_NIF_INTEGRADOR() {
		return P_NIF_INTEGRADOR;
	}

	public void setP_NIF_INTEGRADOR(String P_NIF_INTEGRADOR) {
		this.P_NIF_INTEGRADOR = P_NIF_INTEGRADOR;
	}

	public String getP_VEHI_USADO() {
		return P_VEHI_USADO;
	}

	public void setP_VEHI_USADO(String P_VEHI_USADO) {
		this.P_VEHI_USADO = P_VEHI_USADO;
	}

	public String getP_MATRI_AYUNTAMIENTO() {
		return P_MATRI_AYUNTAMIENTO;
	}

	public void setP_MATRI_AYUNTAMIENTO(String P_MATRI_AYUNTAMIENTO) {
		this.P_MATRI_AYUNTAMIENTO = P_MATRI_AYUNTAMIENTO;
	}

	public Timestamp getP_LIMITE_MATR_TURIS() {
		return P_LIMITE_MATR_TURIS;
	}

	public void setP_LIMITE_MATR_TURIS(Timestamp P_LIMITE_MATR_TURIS) {
		this.P_LIMITE_MATR_TURIS = P_LIMITE_MATR_TURIS;
	}

	public Timestamp getP_FECHA_PRIM_MATRI() {
		return P_FECHA_PRIM_MATRI;
	}

	public void setP_FECHA_PRIM_MATRI(Timestamp P_FECHA_PRIM_MATRI) {
		this.P_FECHA_PRIM_MATRI = P_FECHA_PRIM_MATRI;
	}

	public String getP_ID_LUGAR_MATRICULACION() {
		return P_ID_LUGAR_MATRICULACION;
	}

	public void setP_ID_LUGAR_MATRICULACION(String P_ID_LUGAR_MATRICULACION) {
		this.P_ID_LUGAR_MATRICULACION = P_ID_LUGAR_MATRICULACION;
	}

	public BigDecimal getP_KM_USO() {
		return P_KM_USO;
	}

	public void setP_KM_USO(BigDecimal P_KM_USO) {
		this.P_KM_USO = P_KM_USO;
	}

	public BigDecimal getP_HORAS_USO() {
		return P_HORAS_USO;
	}

	public void setP_HORAS_USO(BigDecimal P_HORAS_USO) {
		this.P_HORAS_USO = P_HORAS_USO;
	}

	public BigDecimal getP_ID_VEHICULO_PREVER() {
		return P_ID_VEHICULO_PREVER;
	}

	public void setP_ID_VEHICULO_PREVER(BigDecimal P_ID_VEHICULO_PREVER) {
		this.P_ID_VEHICULO_PREVER = P_ID_VEHICULO_PREVER;
	}

	public BigDecimal getP_N_CILINDROS() {
		return P_N_CILINDROS;
	}

	public void setP_N_CILINDROS(BigDecimal P_N_CILINDROS) {
		this.P_N_CILINDROS = P_N_CILINDROS;
	}

	public String getP_CARACTERISTICAS() {
		return P_CARACTERISTICAS;
	}

	public void setP_CARACTERISTICAS(String P_CARACTERISTICAS) {
		this.P_CARACTERISTICAS = P_CARACTERISTICAS;
	}

	public String getP_ANIO_FABRICA() {
		return P_ANIO_FABRICA;
	}

	public void setP_ANIO_FABRICA(String P_ANIO_FABRICA) {
		this.P_ANIO_FABRICA = P_ANIO_FABRICA;
	}

	public BigDecimal getP_ID_DIRECCION() {
		return P_ID_DIRECCION;
	}

	public void setP_ID_DIRECCION(BigDecimal P_ID_DIRECCION) {
		this.P_ID_DIRECCION = P_ID_DIRECCION;
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

	public String getP_ID_TIPO_VIA() {
		return P_ID_TIPO_VIA;
	}

	public void setP_ID_TIPO_VIA(String P_ID_TIPO_VIA) {
		this.P_ID_TIPO_VIA = P_ID_TIPO_VIA;
	}

	public String getP_NOMBRE_VIA() {
		return P_NOMBRE_VIA;
	}

	public void setP_NOMBRE_VIA(String P_NOMBRE_VIA) {
		this.P_NOMBRE_VIA = P_NOMBRE_VIA;
	}

	public String getP_NUMERO() {
		return P_NUMERO;
	}

	public void setP_NUMERO(String P_NUMERO) {
		this.P_NUMERO = P_NUMERO;
	}

	public String getP_COD_POSTAL() {
		return P_COD_POSTAL;
	}

	public void setP_COD_POSTAL(String P_COD_POSTAL) {
		this.P_COD_POSTAL = P_COD_POSTAL;
	}

	public String getP_PUEBLO() {
		return P_PUEBLO;
	}

	public void setP_PUEBLO(String P_PUEBLO) {
		this.P_PUEBLO = P_PUEBLO;
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

	public String getP_BLOQUE() {
		return P_BLOQUE;
	}

	public void setP_BLOQUE(String P_BLOQUE) {
		this.P_BLOQUE = P_BLOQUE;
	}

	public String getP_PLANTA() {
		return P_PLANTA;
	}

	public void setP_PLANTA(String P_PLANTA) {
		this.P_PLANTA = P_PLANTA;
	}

	public String getP_PUERTA() {
		return P_PUERTA;
	}

	public void setP_PUERTA(String P_PUERTA) {
		this.P_PUERTA = P_PUERTA;
	}

	public BigDecimal getP_NUM_LOCAL() {
		return P_NUM_LOCAL;
	}

	public void setP_NUM_LOCAL(BigDecimal P_NUM_LOCAL) {
		this.P_NUM_LOCAL = P_NUM_LOCAL;
	}

	public BigDecimal getP_KM() {
		return P_KM;
	}

	public void setP_KM(BigDecimal P_KM) {
		this.P_KM = P_KM;
	}

	public BigDecimal getP_HM() {
		return P_HM;
	}

	public void setP_HM(BigDecimal P_HM) {
		this.P_HM = P_HM;
	}

	public String getP_PREV_MATRICULA() {
		return P_PREV_MATRICULA;
	}

	public void setP_PREV_MATRICULA(String P_PREV_MATRICULA) {
		this.P_PREV_MATRICULA = P_PREV_MATRICULA;
	}

	public String getP_PREV_BASTIDOR() {
		return P_PREV_BASTIDOR;
	}

	public void setP_PREV_BASTIDOR(String P_PREV_BASTIDOR) {
		this.P_PREV_BASTIDOR = P_PREV_BASTIDOR;
	}

	public BigDecimal getP_PREV_CODIGO_MARCA() {
		return P_PREV_CODIGO_MARCA;
	}

	public void setP_PREV_CODIGO_MARCA(BigDecimal P_PREV_CODIGO_MARCA) {
		this.P_PREV_CODIGO_MARCA = P_PREV_CODIGO_MARCA;
	}

	public String getP_PREV_MODELO() {
		return P_PREV_MODELO;
	}

	public void setP_PREV_MODELO(String P_PREV_MODELO) {
		this.P_PREV_MODELO = P_PREV_MODELO;
	}

	public String getP_PREV_ID_CONSTRUCCION() {
		return P_PREV_ID_CONSTRUCCION;
	}

	public void setP_PREV_ID_CONSTRUCCION(String P_PREV_ID_CONSTRUCCION) {
		this.P_PREV_ID_CONSTRUCCION = P_PREV_ID_CONSTRUCCION;
	}

	public String getP_PREV_ID_UTILIZACION() {
		return P_PREV_ID_UTILIZACION;
	}

	public void setP_PREV_ID_UTILIZACION(String P_PREV_ID_UTILIZACION) {
		this.P_PREV_ID_UTILIZACION = P_PREV_ID_UTILIZACION;
	}

	public String getP_PREV_CLASIFICACION_ITV() {
		return P_PREV_CLASIFICACION_ITV;
	}

	public void setP_PREV_CLASIFICACION_ITV(String P_PREV_CLASIFICACION_ITV) {
		this.P_PREV_CLASIFICACION_ITV = P_PREV_CLASIFICACION_ITV;
	}

	public String getP_PREV_TIPO_ITV() {
		return P_PREV_TIPO_ITV;
	}

	public void setP_PREV_TIPO_ITV(String P_PREV_TIPO_ITV) {
		this.P_PREV_TIPO_ITV = P_PREV_TIPO_ITV;
	}

	public String getP_TIPO_PERSONA_INTE() {
		return P_TIPO_PERSONA_INTE;
	}

	public void setP_TIPO_PERSONA_INTE(String P_TIPO_PERSONA_INTE) {
		this.P_TIPO_PERSONA_INTE = P_TIPO_PERSONA_INTE;
	}

	public String getP_APELLIDO1_RAZON_SOCIAL_INTE() {
		return P_APELLIDO1_RAZON_SOCIAL_INTE;
	}

	public void setP_APELLIDO1_RAZON_SOCIAL_INTE(String P_APELLIDO1_RAZON_SOCIAL_INTE) {
		this.P_APELLIDO1_RAZON_SOCIAL_INTE = P_APELLIDO1_RAZON_SOCIAL_INTE;
	}

	public String getP_APELLIDO2_INTE() {
		return P_APELLIDO2_INTE;
	}

	public void setP_APELLIDO2_INTE(String P_APELLIDO2_INTE) {
		this.P_APELLIDO2_INTE = P_APELLIDO2_INTE;
	}

	public String getP_NOMBRE_INTE() {
		return P_NOMBRE_INTE;
	}

	public void setP_NOMBRE_INTE(String P_NOMBRE_INTE) {
		this.P_NOMBRE_INTE = P_NOMBRE_INTE;
	}

	public String getP_IMPORTADO() {
		return P_IMPORTADO;
	}

	public void setP_IMPORTADO(String pIMPORTADO) {
		P_IMPORTADO = pIMPORTADO;
	}

	public String getP_SUBASTADO() {
		return P_SUBASTADO;
	}

	public void setP_SUBASTADO(String pSUBASTADO) {
		P_SUBASTADO = pSUBASTADO;
	}

	public String getP_FABRICACION() {
		return P_FABRICACION;
	}

	public void setP_FABRICACION(String pFABRICACION) {
		P_FABRICACION = pFABRICACION;
	}

	public String getP_PAIS_IMPORTACION() {
		return P_PAIS_IMPORTACION;
	}

	public void setP_PAIS_IMPORTACION(String pPAISIMPORTACION) {
		P_PAIS_IMPORTACION = pPAISIMPORTACION;
	}

	public String getP_FABRICANTE() {
		return P_FABRICANTE;
	}

	public void setP_FABRICANTE(String pFABRICANTE) {
		P_FABRICANTE = pFABRICANTE;
	}

	public String getP_CARROCERIA() {
		return P_CARROCERIA;
	}

	public void setP_CARROCERIA(String pCARROCERIA) {
		P_CARROCERIA = pCARROCERIA;
	}

	public BigDecimal getP_CONSUMO() {
		return P_CONSUMO;
	}

	public void setP_CONSUMO(BigDecimal pCONSUMO) {
		P_CONSUMO = pCONSUMO;
	}

	public BigDecimal getP_DIST_EJES() {
		return P_DIST_EJES;
	}

	public void setP_DIST_EJES(BigDecimal pDISTEJES) {
		P_DIST_EJES = pDISTEJES;
	}

	public BigDecimal getP_VIA_ANT() {
		return P_VIA_ANT;
	}

	public void setP_VIA_ANT(BigDecimal pVIAANT) {
		P_VIA_ANT = pVIAANT;
	}

	public BigDecimal getP_VIA_POS() {
		return P_VIA_POS;
	}

	public void setP_VIA_POS(BigDecimal pVIAPOS) {
		P_VIA_POS = pVIAPOS;
	}

	public String getP_ALIMENTACION() {
		return P_ALIMENTACION;
	}

	public void setP_ALIMENTACION(String pALIMENTACION) {
		P_ALIMENTACION = pALIMENTACION;
	}

	public String getP_EMISIONES() {
		return P_EMISIONES;
	}

	public void setP_EMISIONES(String pEMISIONES) {
		P_EMISIONES = pEMISIONES;
	}

	public String getP_ECO_INNOVACION() {
		return P_ECO_INNOVACION;
	}

	public void setP_ECO_INNOVACION(String pECOINNOVACION) {
		P_ECO_INNOVACION = pECOINNOVACION;
	}

	public BigDecimal getP_REDUCCION_ECO() {
		return P_REDUCCION_ECO;
	}

	public void setP_REDUCCION_ECO(BigDecimal pREDUCCIONECO) {
		P_REDUCCION_ECO = pREDUCCIONECO;
	}

	public String getP_CODIGO_ECO() {
		return P_CODIGO_ECO;
	}

	public void setP_CODIGO_ECO(String pCODIGOECO) {
		P_CODIGO_ECO = pCODIGOECO;
	}

	public BigDecimal getP_MOM() {
		return P_MOM;
	}

	public void setP_MOM(BigDecimal pMOM) {
		P_MOM = pMOM;
	}

	public BigDecimal getP_KILOMETROS() {
		return P_KILOMETROS;
	}

	public void setP_KILOMETROS(BigDecimal pKILOMETROS) {
		P_KILOMETROS = pKILOMETROS;
	}

	public String getP_CHECK_FECHA_CADUCIDAD_ITV() {
		return P_CHECK_FECHA_CADUCIDAD_ITV;
	}

	public void setP_CHECK_FECHA_CADUCIDAD_ITV(String pCHECKFECHACADUCIDADITV) {
		P_CHECK_FECHA_CADUCIDAD_ITV = pCHECKFECHACADUCIDADITV;
	}

//	public Timestamp getP_FECHA_INICIAL_ITV() {
//		return P_FECHA_INICIAL_ITV;
//	}
//	public void setP_FECHA_INICIAL_ITV(Timestamp p_FECHA_INICIAL_ITV) {
//		P_FECHA_INICIAL_ITV = p_FECHA_INICIAL_ITV;
//	}
//	public String getP_ID_PROVINCIA_ITV() {
//		return P_ID_PROVINCIA_ITV;
//	}
//	public void setP_ID_PROVINCIA_ITV(String p_ID_PROVINCIA_ITV) {
//		P_ID_PROVINCIA_ITV = p_ID_PROVINCIA_ITV;
//	}
	public String getP_MATRICULA_ORIGEN() {
		return P_MATRICULA_ORIGEN;
	}

	public void setP_MATRICULA_ORIGEN(String p_MATRICULA_ORIGEN) {
		P_MATRICULA_ORIGEN = p_MATRICULA_ORIGEN;
	}

	public String getP_PAIS_FABRICACION() {
		return P_PAIS_FABRICACION;
	}

	public void setP_PAIS_FABRICACION(String p_PAIS_FABRICACION) {
		P_PAIS_FABRICACION = p_PAIS_FABRICACION;
	}

	public String getP_PROCEDENCIA() {
		return P_PROCEDENCIA;
	}

	public void setP_PROCEDENCIA(String p_PROCEDENCIA) {
		P_PROCEDENCIA = p_PROCEDENCIA;
	}

	public BigDecimal getP_FICHA_TECNICA_RD750() {
		return P_FICHA_TECNICA_RD750;
	}

	public void setP_FICHA_TECNICA_RD750(BigDecimal p_FICHA_TECNICA_RD750) {
		P_FICHA_TECNICA_RD750 = p_FICHA_TECNICA_RD750;
	}

	public BigDecimal getP_CODIGO_MARCA_BASE() {
		return P_CODIGO_MARCA_BASE;
	}

	public void setP_CODIGO_MARCA_BASE(BigDecimal p_CODIGO_MARCA_BASE) {
		P_CODIGO_MARCA_BASE = p_CODIGO_MARCA_BASE;
	}

	public String getP_FABRICANTE_BASE() {
		return P_FABRICANTE_BASE;
	}

	public void setP_FABRICANTE_BASE(String p_FABRICANTE_BASE) {
		P_FABRICANTE_BASE = p_FABRICANTE_BASE;
	}

	public String getP_TIPO_BASE() {
		return P_TIPO_BASE;
	}

	public void setP_TIPO_BASE(String p_TIPO_BASE) {
		P_TIPO_BASE = p_TIPO_BASE;
	}

	public String getP_VARIANTE_BASE() {
		return P_VARIANTE_BASE;
	}

	public void setP_VARIANTE_BASE(String p_VARIANTE_BASE) {
		P_VARIANTE_BASE = p_VARIANTE_BASE;
	}

	public String getP_VERSION_BASE() {
		return P_VERSION_BASE;
	}

	public void setP_VERSION_BASE(String p_VERSION_BASE) {
		P_VERSION_BASE = p_VERSION_BASE;
	}

	public String getP_N_HOMOLOGACION_BASE() {
		return P_N_HOMOLOGACION_BASE;
	}

	public void setP_N_HOMOLOGACION_BASE(String p_N_HOMOLOGACION_BASE) {
		P_N_HOMOLOGACION_BASE = p_N_HOMOLOGACION_BASE;
	}

	public BigDecimal getP_MOM_BASE() {
		return P_MOM_BASE;
	}

	public void setP_MOM_BASE(BigDecimal p_MOM_BASE) {
		P_MOM_BASE = p_MOM_BASE;
	}

	public String getP_CATEGORIA_ELECTRICA() {
		return P_CATEGORIA_ELECTRICA;
	}

	public void setP_CATEGORIA_ELECTRICA(String p_CATEGORIA_ELECTRICA) {
		P_CATEGORIA_ELECTRICA = p_CATEGORIA_ELECTRICA;
	}

	public BigDecimal getP_AUTONOMIA_ELECTRICA() {
		return P_AUTONOMIA_ELECTRICA;
	}

	public void setP_AUTONOMIA_ELECTRICA(BigDecimal p_AUTONOMIA_ELECTRICA) {
		P_AUTONOMIA_ELECTRICA = p_AUTONOMIA_ELECTRICA;
	}

	public BigDecimal getP_BASTIDOR_MATRICULADO() {
		return P_BASTIDOR_MATRICULADO;
	}

	public void setP_BASTIDOR_MATRICULADO(BigDecimal p_BASTIDOR_MATRICULADO) {
		P_BASTIDOR_MATRICULADO = p_BASTIDOR_MATRICULADO;
	}

	public String getP_PROVINCIA_PRIMERA_MATRICULA() {
		return P_PROVINCIA_PRIMERA_MATRICULA;
	}

	public void setP_PROVINCIA_PRIMERA_MATRICULA(String p_PROVINCIA_PRIMERA_MATRICULA) {
		P_PROVINCIA_PRIMERA_MATRICULA = p_PROVINCIA_PRIMERA_MATRICULA;
	}

	public String getP_DIFERENCIA_ANYO() {
		return P_DIFERENCIA_ANYO;
	}

	public void setP_DIFERENCIA_ANYO(String p_DIFERENCIA_ANYO) {
		P_DIFERENCIA_ANYO = p_DIFERENCIA_ANYO;
	}

	public String getP_MATRICULA_ORIG_EXTR() {
		return P_MATRICULA_ORIG_EXTR;
	}

	public void setP_MATRICULA_ORIG_EXTR(String p_MATRICULA_ORIG_EXTR) {
		P_MATRICULA_ORIG_EXTR = p_MATRICULA_ORIG_EXTR;
	}

}