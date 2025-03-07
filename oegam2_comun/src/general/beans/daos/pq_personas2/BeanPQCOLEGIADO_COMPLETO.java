package general.beans.daos.pq_personas2;
 
 
import general.beans.RespuestaGenerica;
import general.modelo.ModeloGenerico;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import trafico.beans.daos.BeanPQGeneral;
import utilidades.constantes.ValoresCatalog;
 
public class BeanPQCOLEGIADO_COMPLETO extends BeanPQGeneral{
 
 
public static final String PROCEDURE="COLEGIADO_COMPLETO";
 
public List<Object> execute(Class<?> claseCursor){
	RespuestaGenerica respuestaGenerica = new ModeloGenerico().ejecutarProc(this,SCHEMA,ValoresCatalog.PQ_PERSONAS,PROCEDURE,claseCursor,true);
	return respuestaGenerica.getListaCursor();
}

public void execute(){
	new ModeloGenerico().ejecutarProc(this,SCHEMA,ValoresCatalog.PQ_PERSONAS,PROCEDURE,null,true);
}
	private BigDecimal P_ID_CONTRATO;
 
	private String P_NIF;
 
	private String P_APELLIDO1_RAZON_SOCIAL;
 
	private String P_APELLIDO2;
 
	private String P_NOMBRE;
 
	private String P_TELEFONOS;
 
	private String P_TIPO_PERSONA;
 
	private BigDecimal P_ESTADO;
 
	private String P_ESTADO_CIVIL;
 
	private String P_CORREO_ELECTRONICO;
 
	private String P_ANAGRAMA;
 
	private Timestamp P_FECHA_NACIMIENTO;
 
	private String P_SEXO;
 
	private String IBAN;
 
	private String P_NCORPME;
	
	private String P_USUARIO_CORPME;
	
	private String P_PASSWORD_CORPME;
 
	private BigDecimal P_PIRPF;
 
	private BigDecimal P_ID_DIRECCION;
 
	private String P_ID_PROVINCIA;
 
	private String P_ID_MUNICIPIO;
 
	private String P_ID_TIPO_VIA;
 
	private String P_NOMBRE_VIA;
 
	private String P_NUMERO;
 
	private String P_COD_POSTAL;
 
	private String P_ESCALERA;
 
	private String P_PLANTA;
 
	private String P_PUERTA;
 
	private BigDecimal P_NUM_LOCAL;
 
	private Timestamp P_FECHA_INICIO;
 
	public BigDecimal getP_ID_CONTRATO(){
		return P_ID_CONTRATO;}
 
	public void setP_ID_CONTRATO(BigDecimal P_ID_CONTRATO){
		this.P_ID_CONTRATO=P_ID_CONTRATO;}
 
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
 
 
	public String getIBAN() {
		return IBAN;
	}

	public void setIBAN(String iBAN) {
		IBAN = iBAN;
	}

	public String getP_NCORPME(){
		return P_NCORPME;}
 
	public void setP_NCORPME(String P_NCORPME){
		this.P_NCORPME=P_NCORPME;}
	
	public String getP_USUARIO_CORPME(){
		return P_USUARIO_CORPME;}
 
	public void setP_USUARIO_CORPME(String P_USUARIO_CORPME){
		this.P_USUARIO_CORPME=P_USUARIO_CORPME;}
	
	public String getP_PASSWORD_CORPME(){
		return P_PASSWORD_CORPME;}
 
	public void setP_PASSWORD_CORPME(String P_PASSWORD_CORPME){
		this.P_PASSWORD_CORPME=P_PASSWORD_CORPME;}
 
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
 
}