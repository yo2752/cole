package trafico.beans.daos.pq_tramite_trafico;
 
import general.beans.RespuestaGenerica;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import trafico.beans.daos.BeanPQGeneral;
import utilidades.constantes.ValoresCatalog;

public class BeanPQDETALLE_TRANSMISION extends BeanPQGeneral{

public static final String PROCEDURE="DETALLE_TRANSMISION";

public List<Object> execute(Class<?> claseCursor){
RespuestaGenerica respuestaGenerica = ejecutarProc(this,SCHEMA,ValoresCatalog.PQ_TRAMITE_TRAFICO,PROCEDURE,claseCursor,true);
return respuestaGenerica.getListaCursor();
}
public void execute(){
ejecutarProc(this,SCHEMA,ValoresCatalog.PQ_TRAMITE_TRAFICO,PROCEDURE,null,true);
}
	private BigDecimal P_ID_CONTRATO_SESSION;

	private BigDecimal P_NUM_EXPEDIENTE;

	private BigDecimal P_ID_CONTRATO;

	private String P_NUM_COLEGIADO;

	private BigDecimal P_ID_VEHICULO;

	private String P_TIPO_TASA;

	private String P_CODIGO_TASA;

	private BigDecimal P_ESTADO;

	private String P_REF_PROPIA;

	private Timestamp P_FECHA_ALTA;

	private Timestamp P_FECHA_PRESENTACION;

	private Timestamp P_FECHA_ULT_MODIF;

	private Timestamp P_FECHA_IMPRESION;

	private String P_JEFATURA_PROVINCIAL;

	private String P_ANOTACIONES;

	private String P_RENTING;

	private String P_CAMBIO_DOMICILIO;

	private String P_IEDTM;

	private String P_MODELO_IEDTM;

	private Timestamp P_FECHA_IEDTM;

	private String P_N_REG_IEDTM;

	private String P_FINANCIERA_IEDTM;

	private String P_EXENTO_IEDTM;

	private String P_NO_SUJECION_IEDTM;

	private String P_CEM;

	private String P_EXENTO_CEM;

	private String P_CEMA;

	private String P_RESPUESTA;

	private String P_RES_CHECK_CTIT;

	private String P_MODO_ADJUDICACION;

	private String P_TIPO_TRANSFERENCIA;

	private String P_ACEPTACION_RESPONS_ITV;

	private String P_CAMBIO_SERVICIO;

	private BigDecimal P_ESTADO_620;

	private String P_MODELO_ITP;

	private String P_ID_PROVINCIA;

	private String P_OFICINA_LIQUIDADORA;

	private String P_FUNDAMENTO_EXENCION;

	private String P_EXENTO_ITP;

	private String P_NO_SUJETO_ITP;

	private String P_FUNDAMENTO_NO_SUJETO_ITP;

	private Timestamp P_FECHA_DEVENGO_ITP;

	private BigDecimal P_P_REDUCCION_ANUAL;

	private BigDecimal P_VALOR_DECLARADO;

	private BigDecimal P_P_ADQUISICION;

	private BigDecimal P_BASE_IMPONIBLE;

	private BigDecimal P_TIPO_GRAVAMEN;

	private BigDecimal P_CUOTA_TRIBUTARIA;

	private String P_CODIGO_TASA_INF;

	private String P_CODIGO_TASA_CAMSER;

	private String P_IMPR_PERMISO_CIRCU;

	private String P_CONSENTIMIENTO;

	private String P_CONTRATO_FACTURA;

	private String P_FACTURA;

	private String P_ACTA_SUBASTA;

	private String P_SENTENCIA_JUDICIAL;

	private String P_ACREDITA_HERENCIA_DONACION;

	private String P_CET_ITP;

	private String P_NUM_AUTO_ITP;

	private String P_MODELO_ISD;

	private String P_NUM_AUTO_ISD;

	private String P_EXENCION_ISD;

	private String P_NO_SUJETO_ISD;

	private String P_ID_REDUCCION_05;

	private String P_ID_NO_SUJECCION_06;

	private String P_DUA;

	private String P_ALTA_IVTM;

	private String P_ID_PROVINCIA_CEM;

	private String P_CONSENTIMIENTO_CAMBIO;

	private Timestamp P_FECHA_FACTURA;

	private Timestamp P_FECHA_CONTRATO;

	private String P_SUBASTA;

	// ----------------------------------

	public BigDecimal getP_ID_CONTRATO_SESSION(){
		return P_ID_CONTRATO_SESSION;}

	public void setP_ID_CONTRATO_SESSION(BigDecimal P_ID_CONTRATO_SESSION){
		this.P_ID_CONTRATO_SESSION=P_ID_CONTRATO_SESSION;}

	public BigDecimal getP_NUM_EXPEDIENTE(){
		return P_NUM_EXPEDIENTE;}

	public void setP_NUM_EXPEDIENTE(BigDecimal P_NUM_EXPEDIENTE){
		this.P_NUM_EXPEDIENTE=P_NUM_EXPEDIENTE;}

	public BigDecimal getP_ID_CONTRATO(){
		return P_ID_CONTRATO;}

	public void setP_ID_CONTRATO(BigDecimal P_ID_CONTRATO){
		this.P_ID_CONTRATO=P_ID_CONTRATO;}

	public String getP_NUM_COLEGIADO(){
		return P_NUM_COLEGIADO;}

	public void setP_NUM_COLEGIADO(String P_NUM_COLEGIADO){
		this.P_NUM_COLEGIADO=P_NUM_COLEGIADO;}

	public BigDecimal getP_ID_VEHICULO(){
		return P_ID_VEHICULO;}

	public void setP_ID_VEHICULO(BigDecimal P_ID_VEHICULO){
		this.P_ID_VEHICULO=P_ID_VEHICULO;}

	public String getP_TIPO_TASA(){
		return P_TIPO_TASA;}

	public void setP_TIPO_TASA(String P_TIPO_TASA){
		this.P_TIPO_TASA=P_TIPO_TASA;}

	public String getP_CODIGO_TASA(){
		return P_CODIGO_TASA;}

	public void setP_CODIGO_TASA(String P_CODIGO_TASA){
		this.P_CODIGO_TASA=P_CODIGO_TASA;}

	public BigDecimal getP_ESTADO(){
		return P_ESTADO;}

	public void setP_ESTADO(BigDecimal P_ESTADO){
		this.P_ESTADO=P_ESTADO;}

	public String getP_REF_PROPIA(){
		return P_REF_PROPIA;}

	public void setP_REF_PROPIA(String P_REF_PROPIA){
		this.P_REF_PROPIA=P_REF_PROPIA;}

	public Timestamp getP_FECHA_ALTA(){
		return P_FECHA_ALTA;}

	public void setP_FECHA_ALTA(Timestamp P_FECHA_ALTA){
		this.P_FECHA_ALTA=P_FECHA_ALTA;}

	public Timestamp getP_FECHA_PRESENTACION(){
		return P_FECHA_PRESENTACION;}

	public void setP_FECHA_PRESENTACION(Timestamp P_FECHA_PRESENTACION){
		this.P_FECHA_PRESENTACION=P_FECHA_PRESENTACION;}

	public Timestamp getP_FECHA_ULT_MODIF(){
		return P_FECHA_ULT_MODIF;}

	public void setP_FECHA_ULT_MODIF(Timestamp P_FECHA_ULT_MODIF){
		this.P_FECHA_ULT_MODIF=P_FECHA_ULT_MODIF;}

	public Timestamp getP_FECHA_IMPRESION(){
		return P_FECHA_IMPRESION;}

	public void setP_FECHA_IMPRESION(Timestamp P_FECHA_IMPRESION){
		this.P_FECHA_IMPRESION=P_FECHA_IMPRESION;}

	public String getP_JEFATURA_PROVINCIAL(){
		return P_JEFATURA_PROVINCIAL;}

	public void setP_JEFATURA_PROVINCIAL(String P_JEFATURA_PROVINCIAL){
		this.P_JEFATURA_PROVINCIAL=P_JEFATURA_PROVINCIAL;}

	public String getP_ANOTACIONES(){
		return P_ANOTACIONES;}

	public void setP_ANOTACIONES(String P_ANOTACIONES){
		this.P_ANOTACIONES=P_ANOTACIONES;}

	public String getP_RENTING(){
		return P_RENTING;}

	public void setP_RENTING(String P_RENTING){
		this.P_RENTING=P_RENTING;}

	public String getP_CAMBIO_DOMICILIO(){
		return P_CAMBIO_DOMICILIO;}

	public void setP_CAMBIO_DOMICILIO(String P_CAMBIO_DOMICILIO){
		this.P_CAMBIO_DOMICILIO=P_CAMBIO_DOMICILIO;}

	public String getP_IEDTM(){
		return P_IEDTM;}

	public void setP_IEDTM(String P_IEDTM){
		this.P_IEDTM=P_IEDTM;}

	public String getP_MODELO_IEDTM(){
		return P_MODELO_IEDTM;}

	public void setP_MODELO_IEDTM(String P_MODELO_IEDTM){
		this.P_MODELO_IEDTM=P_MODELO_IEDTM;}

	public Timestamp getP_FECHA_IEDTM(){
		return P_FECHA_IEDTM;}

	public void setP_FECHA_IEDTM(Timestamp P_FECHA_IEDTM){
		this.P_FECHA_IEDTM=P_FECHA_IEDTM;}

	public String getP_N_REG_IEDTM(){
		return P_N_REG_IEDTM;}

	public void setP_N_REG_IEDTM(String P_N_REG_IEDTM){
		this.P_N_REG_IEDTM=P_N_REG_IEDTM;}

	public String getP_FINANCIERA_IEDTM(){
		return P_FINANCIERA_IEDTM;}

	public void setP_FINANCIERA_IEDTM(String P_FINANCIERA_IEDTM){
		this.P_FINANCIERA_IEDTM=P_FINANCIERA_IEDTM;}

	public String getP_EXENTO_IEDTM(){
		return P_EXENTO_IEDTM;}

	public void setP_EXENTO_IEDTM(String P_EXENTO_IEDTM){
		this.P_EXENTO_IEDTM=P_EXENTO_IEDTM;}

	public String getP_NO_SUJECION_IEDTM(){
		return P_NO_SUJECION_IEDTM;}

	public void setP_NO_SUJECION_IEDTM(String P_NO_SUJECION_IEDTM){
		this.P_NO_SUJECION_IEDTM=P_NO_SUJECION_IEDTM;}

	public String getP_CEM(){
		return P_CEM;}

	public void setP_CEM(String P_CEM){
		this.P_CEM=P_CEM;}

	public String getP_EXENTO_CEM(){
		return P_EXENTO_CEM;}

	public void setP_EXENTO_CEM(String P_EXENTO_CEM){
		this.P_EXENTO_CEM=P_EXENTO_CEM;}

	public String getP_CEMA(){
		return P_CEMA;}

	public void setP_CEMA(String P_CEMA){
		this.P_CEMA=P_CEMA;}

	public String getP_RESPUESTA(){
		return P_RESPUESTA;}

	public void setP_RESPUESTA(String P_RESPUESTA){
		this.P_RESPUESTA=P_RESPUESTA;}

	public String getP_RES_CHECK_CTIT(){
		return P_RES_CHECK_CTIT;}

	public void setP_RES_CHECK_CTIT(String P_RES_CHECK_CTIT){
		this.P_RES_CHECK_CTIT=P_RES_CHECK_CTIT;}

	public String getP_MODO_ADJUDICACION(){
		return P_MODO_ADJUDICACION;}

	public void setP_MODO_ADJUDICACION(String P_MODO_ADJUDICACION){
		this.P_MODO_ADJUDICACION=P_MODO_ADJUDICACION;}

	public String getP_TIPO_TRANSFERENCIA(){
		return P_TIPO_TRANSFERENCIA;}

	public void setP_TIPO_TRANSFERENCIA(String P_TIPO_TRANSFERENCIA){
		this.P_TIPO_TRANSFERENCIA=P_TIPO_TRANSFERENCIA;}

	public String getP_ACEPTACION_RESPONS_ITV(){
		return P_ACEPTACION_RESPONS_ITV;}

	public void setP_ACEPTACION_RESPONS_ITV(String P_ACEPTACION_RESPONS_ITV){
		this.P_ACEPTACION_RESPONS_ITV=P_ACEPTACION_RESPONS_ITV;}

	public String getP_CAMBIO_SERVICIO(){
		return P_CAMBIO_SERVICIO;}

	public void setP_CAMBIO_SERVICIO(String P_CAMBIO_SERVICIO){
		this.P_CAMBIO_SERVICIO=P_CAMBIO_SERVICIO;}

	public BigDecimal getP_ESTADO_620(){
		return P_ESTADO_620;}

	public void setP_ESTADO_620(BigDecimal P_ESTADO_620){
		this.P_ESTADO_620=P_ESTADO_620;}

	public String getP_MODELO_ITP(){
		return P_MODELO_ITP;}

	public void setP_MODELO_ITP(String P_MODELO_ITP){
		this.P_MODELO_ITP=P_MODELO_ITP;}

	public String getP_ID_PROVINCIA(){
		return P_ID_PROVINCIA;}

	public void setP_ID_PROVINCIA(String P_ID_PROVINCIA){
		this.P_ID_PROVINCIA=P_ID_PROVINCIA;}

	public String getP_OFICINA_LIQUIDADORA(){
		return P_OFICINA_LIQUIDADORA;}

	public void setP_OFICINA_LIQUIDADORA(String P_OFICINA_LIQUIDADORA){
		this.P_OFICINA_LIQUIDADORA=P_OFICINA_LIQUIDADORA;}

	public String getP_FUNDAMENTO_EXENCION(){
		return P_FUNDAMENTO_EXENCION;}

	public void setP_FUNDAMENTO_EXENCION(String P_FUNDAMENTO_EXENCION){
		this.P_FUNDAMENTO_EXENCION=P_FUNDAMENTO_EXENCION;}

	public String getP_EXENTO_ITP(){
		return P_EXENTO_ITP;}

	public void setP_EXENTO_ITP(String P_EXENTO_ITP){
		this.P_EXENTO_ITP=P_EXENTO_ITP;}

	public String getP_NO_SUJETO_ITP(){
		return P_NO_SUJETO_ITP;}

	public void setP_NO_SUJETO_ITP(String P_NO_SUJETO_ITP){
		this.P_NO_SUJETO_ITP=P_NO_SUJETO_ITP;}

	public String getP_FUNDAMENTO_NO_SUJETO_ITP(){
		return P_FUNDAMENTO_NO_SUJETO_ITP;}

	public void setP_FUNDAMENTO_NO_SUJETO_ITP(String P_FUNDAMENTO_NO_SUJETO_ITP){
		this.P_FUNDAMENTO_NO_SUJETO_ITP=P_FUNDAMENTO_NO_SUJETO_ITP;}

	public Timestamp getP_FECHA_DEVENGO_ITP(){
		return P_FECHA_DEVENGO_ITP;}

	public void setP_FECHA_DEVENGO_ITP(Timestamp P_FECHA_DEVENGO_ITP){
		this.P_FECHA_DEVENGO_ITP=P_FECHA_DEVENGO_ITP;}

	public BigDecimal getP_P_REDUCCION_ANUAL(){
		return P_P_REDUCCION_ANUAL;}

	public void setP_P_REDUCCION_ANUAL(BigDecimal P_P_REDUCCION_ANUAL){
		this.P_P_REDUCCION_ANUAL=P_P_REDUCCION_ANUAL;}

	public BigDecimal getP_VALOR_DECLARADO(){
		return P_VALOR_DECLARADO;}

	public void setP_VALOR_DECLARADO(BigDecimal P_VALOR_DECLARADO){
		this.P_VALOR_DECLARADO=P_VALOR_DECLARADO;}

	public BigDecimal getP_P_ADQUISICION(){
		return P_P_ADQUISICION;}

	public void setP_P_ADQUISICION(BigDecimal P_P_ADQUISICION){
		this.P_P_ADQUISICION=P_P_ADQUISICION;}

	public BigDecimal getP_BASE_IMPONIBLE(){
		return P_BASE_IMPONIBLE;}

	public void setP_BASE_IMPONIBLE(BigDecimal P_BASE_IMPONIBLE){
		this.P_BASE_IMPONIBLE=P_BASE_IMPONIBLE;}

	public BigDecimal getP_TIPO_GRAVAMEN(){
		return P_TIPO_GRAVAMEN;}

	public void setP_TIPO_GRAVAMEN(BigDecimal P_TIPO_GRAVAMEN){
		this.P_TIPO_GRAVAMEN=P_TIPO_GRAVAMEN;}

	public BigDecimal getP_CUOTA_TRIBUTARIA(){
		return P_CUOTA_TRIBUTARIA;}

	public void setP_CUOTA_TRIBUTARIA(BigDecimal P_CUOTA_TRIBUTARIA){
		this.P_CUOTA_TRIBUTARIA=P_CUOTA_TRIBUTARIA;}

	public String getP_CODIGO_TASA_INF(){
		return P_CODIGO_TASA_INF;}

	public void setP_CODIGO_TASA_INF(String P_CODIGO_TASA_INF){
		this.P_CODIGO_TASA_INF=P_CODIGO_TASA_INF;}

	public String getP_CODIGO_TASA_CAMSER(){
		return P_CODIGO_TASA_CAMSER;}

	public void setP_CODIGO_TASA_CAMSER(String P_CODIGO_TASA_CAMSER){
		this.P_CODIGO_TASA_CAMSER=P_CODIGO_TASA_CAMSER;}

	public String getP_IMPR_PERMISO_CIRCU(){
		return P_IMPR_PERMISO_CIRCU;}

	public void setP_IMPR_PERMISO_CIRCU(String P_IMPR_PERMISO_CIRCU){
		this.P_IMPR_PERMISO_CIRCU=P_IMPR_PERMISO_CIRCU;}

	public String getP_CONSENTIMIENTO(){
		return P_CONSENTIMIENTO;}

	public void setP_CONSENTIMIENTO(String P_CONSENTIMIENTO){
		this.P_CONSENTIMIENTO=P_CONSENTIMIENTO;}

	public String getP_CONTRATO_FACTURA(){
		return P_CONTRATO_FACTURA;}

	public void setP_CONTRATO_FACTURA(String P_CONTRATO_FACTURA){
		this.P_CONTRATO_FACTURA=P_CONTRATO_FACTURA;}

	public String getP_FACTURA(){
		return P_FACTURA;}

	public void setP_FACTURA(String P_FACTURA){
		this.P_FACTURA=P_FACTURA;}

	public String getP_ACTA_SUBASTA(){
		return P_ACTA_SUBASTA;}

	public void setP_ACTA_SUBASTA(String P_ACTA_SUBASTA){
		this.P_ACTA_SUBASTA=P_ACTA_SUBASTA;}

	public String getP_SENTENCIA_JUDICIAL(){
		return P_SENTENCIA_JUDICIAL;}

	public void setP_SENTENCIA_JUDICIAL(String P_SENTENCIA_JUDICIAL){
		this.P_SENTENCIA_JUDICIAL=P_SENTENCIA_JUDICIAL;}

	public String getP_ACREDITA_HERENCIA_DONACION(){
		return P_ACREDITA_HERENCIA_DONACION;}

	public void setP_ACREDITA_HERENCIA_DONACION(String P_ACREDITA_HERENCIA_DONACION){
		this.P_ACREDITA_HERENCIA_DONACION=P_ACREDITA_HERENCIA_DONACION;}

	public String getP_CET_ITP(){
		return P_CET_ITP;}

	public void setP_CET_ITP(String P_CET_ITP){
		this.P_CET_ITP=P_CET_ITP;}

	public String getP_NUM_AUTO_ITP(){
		return P_NUM_AUTO_ITP;}

	public void setP_NUM_AUTO_ITP(String P_NUM_AUTO_ITP){
		this.P_NUM_AUTO_ITP=P_NUM_AUTO_ITP;}

	public String getP_MODELO_ISD(){
		return P_MODELO_ISD;}

	public void setP_MODELO_ISD(String P_MODELO_ISD){
		this.P_MODELO_ISD=P_MODELO_ISD;}

	public String getP_NUM_AUTO_ISD(){
		return P_NUM_AUTO_ISD;}

	public void setP_NUM_AUTO_ISD(String P_NUM_AUTO_ISD){
		this.P_NUM_AUTO_ISD=P_NUM_AUTO_ISD;}

	public String getP_EXENCION_ISD(){
		return P_EXENCION_ISD;}

	public void setP_EXENCION_ISD(String P_EXENCION_ISD){
		this.P_EXENCION_ISD=P_EXENCION_ISD;}

	public String getP_NO_SUJETO_ISD(){
		return P_NO_SUJETO_ISD;}

	public void setP_NO_SUJETO_ISD(String P_NO_SUJETO_ISD){
		this.P_NO_SUJETO_ISD=P_NO_SUJETO_ISD;}

	public String getP_ID_REDUCCION_05(){
		return P_ID_REDUCCION_05;}

	public void setP_ID_REDUCCION_05(String P_ID_REDUCCION_05){
		this.P_ID_REDUCCION_05=P_ID_REDUCCION_05;}

	public String getP_ID_NO_SUJECCION_06(){
		return P_ID_NO_SUJECCION_06;}

	public void setP_ID_NO_SUJECCION_06(String P_ID_NO_SUJECCION_06){
		this.P_ID_NO_SUJECCION_06=P_ID_NO_SUJECCION_06;}

	public String getP_DUA(){
		return P_DUA;}

	public void setP_DUA(String P_DUA){
		this.P_DUA=P_DUA;}

	public String getP_ALTA_IVTM(){
		return P_ALTA_IVTM;}

	public void setP_ALTA_IVTM(String P_ALTA_IVTM){
		this.P_ALTA_IVTM=P_ALTA_IVTM;}

	public String getP_ID_PROVINCIA_CEM() {
		return P_ID_PROVINCIA_CEM;
	}
	public void setP_ID_PROVINCIA_CEM(String pIDPROVINCIACEM) {
		P_ID_PROVINCIA_CEM = pIDPROVINCIACEM;
	}

	// P_CONSENTIMIENTO_CAMBIO
	public String getP_CONSENTIMIENTO_CAMBIO() {
		return P_CONSENTIMIENTO_CAMBIO;
	}
	public void setP_CONSENTIMIENTO_CAMBIO(String pCONSENTIMIENTOCAMBIO) {
		P_CONSENTIMIENTO_CAMBIO = pCONSENTIMIENTOCAMBIO;
	}

	public Timestamp getP_FECHA_FACTURA() {
		return P_FECHA_FACTURA;
	}

	public void setP_FECHA_FACTURA(Timestamp p_FECHA_FACTURA) {
		P_FECHA_FACTURA = p_FECHA_FACTURA;
	}

	public Timestamp getP_FECHA_CONTRATO() {
		return P_FECHA_CONTRATO;
	}

	public void setP_FECHA_CONTRATO(Timestamp p_FECHA_CONTRATO) {
		P_FECHA_CONTRATO = p_FECHA_CONTRATO;
	}
	public String getP_SUBASTA() {
		return P_SUBASTA;
	}
	public void setP_SUBASTA(String p_SUBASTA) {
		P_SUBASTA = p_SUBASTA;
	}

}