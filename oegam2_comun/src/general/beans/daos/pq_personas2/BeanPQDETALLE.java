package general.beans.daos.pq_personas2;
 
import general.beans.RespuestaGenerica;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import trafico.beans.daos.BeanPQGeneral;
import utilidades.constantes.ValoresCatalog;

public class BeanPQDETALLE extends BeanPQGeneral{
 
 
public static final String PROCEDURE="DETALLE";
 
public List<Object> execute(Class<?> claseCursor){
RespuestaGenerica respuestaGenerica = ejecutarProc(this,SCHEMA,ValoresCatalog.PQ_PERSONAS,PROCEDURE,claseCursor,true);
return respuestaGenerica.getListaCursor();
}
public void execute(){
ejecutarProc(this,SCHEMA,ValoresCatalog.PQ_PERSONAS,PROCEDURE,null,true);
}
	private String P_NUM_COLEGIADO;

	private String P_NIF;
 
	private String P_APELLIDO1_RAZON_SOCIAL;
 
	private String P_APELLIDO2;
 
	private String P_NOMBRE;
 
	private String P_TELEFONOS;
 
	private String P_TIPO_PERSONA;
 
	private String P_SUBTIPO;
 
	private BigDecimal P_SECCION;
 
	private BigDecimal P_HOJA;
 
	private String P_HOJA_BIS;
 
	private BigDecimal P_IUS;
 
	private BigDecimal P_ESTADO;
 
	private String P_ESTADO_CIVIL;
 
	private String P_CORREO_ELECTRONICO;
 
	private String P_ANAGRAMA;
 
	private Timestamp P_FECHA_NACIMIENTO;
 
	private String P_SEXO;
 
	private String P_IBAN;
 
	private String P_NCORPME;
 
	private BigDecimal P_PIRPF;
	
	private String P_FA;

	private Timestamp P_FECHA_CADUCIDAD_NIF;

	private Timestamp P_FECHA_CADUCIDAD_ALTERNATIVO;

	private String P_TIPO_DOCUMENTO_ALTERNATIVO;
	
	private String P_INDEFINIDO;
 
	private BigDecimal P_ID_DIRECCION;
 
	private String P_ID_PROVINCIA;
 
	private String P_ID_MUNICIPIO;
 
	private String P_ID_TIPO_VIA;
 
	private String P_NOMBRE_VIA;
 
	private String P_NUMERO;
 
	private String P_COD_POSTAL;
	
	private String P_COD_POSTAL_CORREOS; 
 
	private String P_LETRA;
 
	private String P_ESCALERA;
 
	private String P_PLANTA;
 
	private String P_PUERTA;
 
	private BigDecimal P_NUM_LOCAL;
 
	private Timestamp P_FECHA_INICIO;
 
	private String P_PUEBLO;
 
	private String P_PUEBLO_CORREOS;
	
	private String P_BLOQUE;
 
	private BigDecimal P_KM;
 
	private BigDecimal P_HM;
	
	private String P_CODIGO_MANDATO;
	
	private BigDecimal P_PODERES_EN_FICHA;
	
 
	public String getP_NUM_COLEGIADO(){
		return P_NUM_COLEGIADO;}
 
	public void setP_NUM_COLEGIADO(String P_NUM_COLEGIADO){
		this.P_NUM_COLEGIADO=P_NUM_COLEGIADO;}
 
	public String getP_NIF(){
		return P_NIF;}
 
	public void setP_NIF(String P_NIF){
		this.P_NIF=P_NIF;}
 
	public String getP_APELLIDO1_RAZON_SOCIAL(){
		return P_APELLIDO1_RAZON_SOCIAL;}
 
	public void setP_APELLIDO1_RAZON_SOCIAL(String P_APELLIDO1_RAZON_SOCIAL){
		this.P_APELLIDO1_RAZON_SOCIAL=P_APELLIDO1_RAZON_SOCIAL;}
 
	public String getP_APELLIDO2(){
		return P_APELLIDO2;}
 
	public void setP_APELLIDO2(String P_APELLIDO2){
		this.P_APELLIDO2=P_APELLIDO2;}
 
	public String getP_NOMBRE(){
		return P_NOMBRE;}
 
	public void setP_NOMBRE(String P_NOMBRE){
		this.P_NOMBRE=P_NOMBRE;}
 
	public String getP_TELEFONOS(){
		return P_TELEFONOS;}
 
	public void setP_TELEFONOS(String P_TELEFONOS){
		this.P_TELEFONOS=P_TELEFONOS;}
 
	public String getP_TIPO_PERSONA(){
		return P_TIPO_PERSONA;}
 
	public void setP_TIPO_PERSONA(String P_TIPO_PERSONA){
		this.P_TIPO_PERSONA=P_TIPO_PERSONA;}
 
	public String getP_SUBTIPO(){
		return P_SUBTIPO;}
 
	public void setP_SUBTIPO(String P_SUBTIPO){
		this.P_SUBTIPO=P_SUBTIPO;}
 
	public BigDecimal getP_SECCION(){
		return P_SECCION;}
 
	public void setP_SECCION(BigDecimal P_SECCION){
		this.P_SECCION=P_SECCION;}
 
	public BigDecimal getP_HOJA(){
		return P_HOJA;}
 
	public void setP_HOJA(BigDecimal P_HOJA){
		this.P_HOJA=P_HOJA;}
 
	public String getP_HOJA_BIS(){
		return P_HOJA_BIS;}
 
	public void setP_HOJA_BIS(String P_HOJA_BIS){
		this.P_HOJA_BIS=P_HOJA_BIS;}
 
	public BigDecimal getP_IUS(){
		return P_IUS;}
 
	public void setP_IUS(BigDecimal P_IUS){
		this.P_IUS=P_IUS;}
 
	public BigDecimal getP_ESTADO(){
		return P_ESTADO;}
 
	public void setP_ESTADO(BigDecimal P_ESTADO){
		this.P_ESTADO=P_ESTADO;}
 
	public String getP_ESTADO_CIVIL(){
		return P_ESTADO_CIVIL;}
 
	public void setP_ESTADO_CIVIL(String P_ESTADO_CIVIL){
		this.P_ESTADO_CIVIL=P_ESTADO_CIVIL;}
 
	public String getP_CORREO_ELECTRONICO(){
		return P_CORREO_ELECTRONICO;}
 
	public void setP_CORREO_ELECTRONICO(String P_CORREO_ELECTRONICO){
		this.P_CORREO_ELECTRONICO=P_CORREO_ELECTRONICO;}
 
	public String getP_ANAGRAMA(){
		return P_ANAGRAMA;}
 
	public void setP_ANAGRAMA(String P_ANAGRAMA){
		this.P_ANAGRAMA=P_ANAGRAMA;}
 
	public Timestamp getP_FECHA_NACIMIENTO(){
		return P_FECHA_NACIMIENTO;}
 
	public void setP_FECHA_NACIMIENTO(Timestamp P_FECHA_NACIMIENTO){
		this.P_FECHA_NACIMIENTO=P_FECHA_NACIMIENTO;}
 
	public String getP_SEXO(){
		return P_SEXO;}
 
	public void setP_SEXO(String P_SEXO){
		this.P_SEXO=P_SEXO;}
 
	public String getP_IBAN() {
		return P_IBAN;
	}
	public void setP_IBAN(String p_IBAN) {
		P_IBAN = p_IBAN;
	}
	public String getP_NCORPME(){
		return P_NCORPME;}
 
	public void setP_NCORPME(String P_NCORPME){
		this.P_NCORPME=P_NCORPME;}
 
	public BigDecimal getP_PIRPF(){
		return P_PIRPF;}
 
	public void setP_PIRPF(BigDecimal P_PIRPF){
		this.P_PIRPF=P_PIRPF;}
 
	public BigDecimal getP_ID_DIRECCION(){
		return P_ID_DIRECCION;}
 
	public void setP_ID_DIRECCION(BigDecimal P_ID_DIRECCION){
		this.P_ID_DIRECCION=P_ID_DIRECCION;}
 
	public String getP_ID_PROVINCIA(){
		return P_ID_PROVINCIA;}
 
	public void setP_ID_PROVINCIA(String P_ID_PROVINCIA){
		this.P_ID_PROVINCIA=P_ID_PROVINCIA;}
 
	public String getP_ID_MUNICIPIO(){
		return P_ID_MUNICIPIO;}
 
	public void setP_ID_MUNICIPIO(String P_ID_MUNICIPIO){
		this.P_ID_MUNICIPIO=P_ID_MUNICIPIO;}
 
	public String getP_ID_TIPO_VIA(){
		return P_ID_TIPO_VIA;}
 
	public void setP_ID_TIPO_VIA(String P_ID_TIPO_VIA){
		this.P_ID_TIPO_VIA=P_ID_TIPO_VIA;}
 
	public String getP_NOMBRE_VIA(){
		return P_NOMBRE_VIA;}
 
	public void setP_NOMBRE_VIA(String P_NOMBRE_VIA){
		this.P_NOMBRE_VIA=P_NOMBRE_VIA;}
 
	public String getP_NUMERO(){
		return P_NUMERO;}
 
	public void setP_NUMERO(String P_NUMERO){
		this.P_NUMERO=P_NUMERO;}
 
	public String getP_COD_POSTAL(){
		return P_COD_POSTAL;}
 
	public void setP_COD_POSTAL(String P_COD_POSTAL){
		this.P_COD_POSTAL=P_COD_POSTAL;}
 
	
	public String getP_COD_POSTAL_CORREOS(){
		return P_COD_POSTAL_CORREOS;}
 
	public void setP_COD_POSTAL_CORREOS(String P_COD_POSTAL_CORREOS){
		this.P_COD_POSTAL_CORREOS=P_COD_POSTAL_CORREOS;}
	
	
	public String getP_LETRA(){
		return P_LETRA;}
 
	public void setP_LETRA(String P_LETRA){
		this.P_LETRA=P_LETRA;}
 
	public String getP_ESCALERA(){
		return P_ESCALERA;}
 
	public void setP_ESCALERA(String P_ESCALERA){
		this.P_ESCALERA=P_ESCALERA;}
 
	public String getP_PLANTA(){
		return P_PLANTA;}
 
	public void setP_PLANTA(String P_PLANTA){
		this.P_PLANTA=P_PLANTA;}
 
	public String getP_PUERTA(){
		return P_PUERTA;}
 
	public void setP_PUERTA(String P_PUERTA){
		this.P_PUERTA=P_PUERTA;}
 
	public BigDecimal getP_NUM_LOCAL(){
		return P_NUM_LOCAL;}
 
	public void setP_NUM_LOCAL(BigDecimal P_NUM_LOCAL){
		this.P_NUM_LOCAL=P_NUM_LOCAL;}
 
	public Timestamp getP_FECHA_INICIO(){
		return P_FECHA_INICIO;}
 
	public void setP_FECHA_INICIO(Timestamp P_FECHA_INICIO){
		this.P_FECHA_INICIO=P_FECHA_INICIO;}
 
	public String getP_PUEBLO(){
		return P_PUEBLO;}
 
	public void setP_PUEBLO(String P_PUEBLO){
		this.P_PUEBLO=P_PUEBLO;}
 
	
	public String getP_PUEBLO_CORREOS(){
		return P_PUEBLO_CORREOS;}
 
	public void setP_PUEBLO_CORREOS(String P_PUEBLO_CORREOS){
		this.P_PUEBLO_CORREOS=P_PUEBLO_CORREOS;}
	
	
	public String getP_BLOQUE(){
		return P_BLOQUE;}
 
	public void setP_BLOQUE(String P_BLOQUE){
		this.P_BLOQUE=P_BLOQUE;}
 
	public BigDecimal getP_KM(){
		return P_KM;}
 
	public void setP_KM(BigDecimal P_KM){
		this.P_KM=P_KM;}
 
	public BigDecimal getP_HM(){
		return P_HM;}
 
	public void setP_HM(BigDecimal P_HM){
		this.P_HM=P_HM;}
	
	public String getP_FA() {
		return P_FA;
	}
	public void setP_FA(String p_FA) {
		P_FA = p_FA;
	}
	public String getP_CODIGO_MANDATO() {
		return P_CODIGO_MANDATO;
	}
	public void setP_CODIGO_MANDATO(String pCODIGOMANDATO) {
		P_CODIGO_MANDATO = pCODIGOMANDATO;
	}
	public BigDecimal getP_PODERES_EN_FICHA() {
		return P_PODERES_EN_FICHA;
	}
	public void setP_PODERES_EN_FICHA(BigDecimal pPODERESENFICHA) {
		P_PODERES_EN_FICHA = pPODERESENFICHA;
	}
	public Timestamp getP_FECHA_CADUCIDAD_NIF() {
		return P_FECHA_CADUCIDAD_NIF;
	}
	public void setP_FECHA_CADUCIDAD_NIF(Timestamp p_FECHA_CADUCIDAD_NIF) {
		P_FECHA_CADUCIDAD_NIF = p_FECHA_CADUCIDAD_NIF;
	}
	public Timestamp getP_FECHA_CADUCIDAD_ALTERNATIVO() {
		return P_FECHA_CADUCIDAD_ALTERNATIVO;
	}
	public void setP_FECHA_CADUCIDAD_ALTERNATIVO(
			Timestamp p_FECHA_CADUCIDAD_ALTERNATIVO) {
		P_FECHA_CADUCIDAD_ALTERNATIVO = p_FECHA_CADUCIDAD_ALTERNATIVO;
	}
	public String getP_TIPO_DOCUMENTO_ALTERNATIVO() {
		return P_TIPO_DOCUMENTO_ALTERNATIVO;
	}
	public void setP_TIPO_DOCUMENTO_ALTERNATIVO(String p_TIPO_DOCUMENTO_ALTERNATIVO) {
		P_TIPO_DOCUMENTO_ALTERNATIVO = p_TIPO_DOCUMENTO_ALTERNATIVO;
	}
	public String getP_INDEFINIDO() {
		return P_INDEFINIDO;
	}
	public void setP_INDEFINIDO(String p_INDEFINIDO) {
		P_INDEFINIDO = p_INDEFINIDO;
	}
	
 
}