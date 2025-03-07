package escrituras.beans.daos.pq_usuarios;
 
import general.beans.RespuestaGenerica;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import trafico.beans.daos.BeanPQGeneral;
import utilidades.constantes.ValoresCatalog;

public class BeanPQDETALLE extends BeanPQGeneral{
 
 
public static final String PROCEDURE="DETALLE";
 
public List<Object> execute(Class<?> claseCursor){
RespuestaGenerica respuestaGenerica = ejecutarProc(this,SCHEMA,ValoresCatalog.PQ_USUARIOS,PROCEDURE,claseCursor,true);
return respuestaGenerica.getListaCursor();
}
public void execute(){
ejecutarProc(this,SCHEMA,ValoresCatalog.PQ_USUARIOS,PROCEDURE,null,true);
}	
 
	private BigDecimal P_ID_CONTRATO;
 
	private BigDecimal P_ID_USUARIO;
 
	private String P_NUM_COLEGIADO;
 
	private BigDecimal P_ESTADO_USUARIO;
 
	private String P_NIF;
 
	private String P_APELLIDOS_NOMBRE;
 
	private String P_ANAGRAMA;
 
	private String P_CORREO_ELECTRONICO;
 
	private Timestamp P_ULTIMA_CONEXION;
 
	private Timestamp P_FECHA_RENOVACION;
 
	private Object C_PERMISOS;
	
	private String P_ID_GRUPO;
	
	// Mantis 11562
	private Timestamp P_FECHA_ALTA;
	
	private Timestamp P_FECHA_FIN;
 
	public BigDecimal getP_ID_CONTRATO(){
		return P_ID_CONTRATO;}
 
	public void setP_ID_CONTRATO(BigDecimal P_ID_CONTRATO){
		this.P_ID_CONTRATO=P_ID_CONTRATO;}
 
	public BigDecimal getP_ID_USUARIO(){
		return P_ID_USUARIO;}
 
	public void setP_ID_USUARIO(BigDecimal P_ID_USUARIO){
		this.P_ID_USUARIO=P_ID_USUARIO;}
 
	public String getP_NUM_COLEGIADO(){
		return P_NUM_COLEGIADO;}
 
	public void setP_NUM_COLEGIADO(String P_NUM_COLEGIADO){
		this.P_NUM_COLEGIADO=P_NUM_COLEGIADO;}
 
	public BigDecimal getP_ESTADO_USUARIO(){
		return P_ESTADO_USUARIO;}
 
	public void setP_ESTADO_USUARIO(BigDecimal P_ESTADO_USUARIO){
		this.P_ESTADO_USUARIO=P_ESTADO_USUARIO;}
 
	public String getP_NIF(){
		return P_NIF;}
 
	public void setP_NIF(String P_NIF){
		this.P_NIF=P_NIF;}
 
	public String getP_APELLIDOS_NOMBRE(){
		return P_APELLIDOS_NOMBRE;}
 
	public void setP_APELLIDOS_NOMBRE(String P_APELLIDOS_NOMBRE){
		this.P_APELLIDOS_NOMBRE=P_APELLIDOS_NOMBRE;}
 
	public String getP_ANAGRAMA(){
		return P_ANAGRAMA;}
 
	public void setP_ANAGRAMA(String P_ANAGRAMA){
		this.P_ANAGRAMA=P_ANAGRAMA;}
 
	public String getP_CORREO_ELECTRONICO(){
		return P_CORREO_ELECTRONICO;}
 
	public void setP_CORREO_ELECTRONICO(String P_CORREO_ELECTRONICO){
		this.P_CORREO_ELECTRONICO=P_CORREO_ELECTRONICO;}
 
	public Timestamp getP_ULTIMA_CONEXION(){
		return P_ULTIMA_CONEXION;}
 
	public void setP_ULTIMA_CONEXION(Timestamp P_ULTIMA_CONEXION){
		this.P_ULTIMA_CONEXION=P_ULTIMA_CONEXION;}
 
	public Timestamp getP_FECHA_RENOVACION(){
		return P_FECHA_RENOVACION;}
 
	public void setP_FECHA_RENOVACION(Timestamp P_FECHA_RENOVACION){
		this.P_FECHA_RENOVACION=P_FECHA_RENOVACION;}
 
	public Object getC_PERMISOS(){
		return C_PERMISOS;}
 
	public void setC_PERMISOS(Object C_PERMISOS){
		this.C_PERMISOS=C_PERMISOS;}
	
	public String getP_ID_GRUPO(){
		return P_ID_GRUPO;}
 
	public void setP_ID_GRUPO(String P_ID_GRUPO){
		this.P_ID_GRUPO=P_ID_GRUPO;}
	public Timestamp getP_FECHA_ALTA() {
		return P_FECHA_ALTA;
	}
	public void setP_FECHA_ALTA(Timestamp p_FECHA_ALTA) {
		P_FECHA_ALTA = p_FECHA_ALTA;
	}
	public Timestamp getP_FECHA_FIN() {
		return P_FECHA_FIN;
	}
	public void setP_FECHA_FIN(Timestamp p_FECHA_FIN) {
		P_FECHA_FIN = p_FECHA_FIN;
	}
 
}