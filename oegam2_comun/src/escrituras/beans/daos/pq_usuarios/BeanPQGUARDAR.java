package escrituras.beans.daos.pq_usuarios;
 
import general.beans.RespuestaGenerica;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import trafico.beans.daos.BeanPQGeneral;
import utilidades.constantes.ValoresCatalog;

public class BeanPQGUARDAR extends BeanPQGeneral{
 
 
public static final String PROCEDURE="GUARDAR";
 
public List<Object> execute(Class<?> claseCursor){
RespuestaGenerica respuestaGenerica = ejecutarProc(this,SCHEMA,ValoresCatalog.PQ_USUARIOS,PROCEDURE,claseCursor,true);
return respuestaGenerica.getListaCursor();
}
public void execute(){
ejecutarProc(this,SCHEMA,ValoresCatalog.PQ_USUARIOS,PROCEDURE,null,true);
}	
 
	private BigDecimal P_ID_USUARIO;
 
	private String P_NUM_COLEGIADO;
	
	private String P_NUM_COLEGIADO_NACIONAL;
 
	private BigDecimal P_ESTADO_USUARIO;
 
	private String P_NIF;
 
	private String P_APELLIDOS_NOMBRE;
 
	private String P_ANAGRAMA;
 
	private String P_CORREO_ELECTRONICO;
	
	private String P_ID_GRUPO;
	
	// Mantis 11562
	private Timestamp P_FECHA_ALTA;
	
	private Timestamp P_FECHA_FIN;
 
	public BigDecimal getP_ID_USUARIO(){
		return P_ID_USUARIO;}
 
	public void setP_ID_USUARIO(BigDecimal P_ID_USUARIO){
		this.P_ID_USUARIO=P_ID_USUARIO;}
 
	public String getP_NUM_COLEGIADO(){
		return P_NUM_COLEGIADO;}
 
	public void setP_NUM_COLEGIADO(String P_NUM_COLEGIADO){
		this.P_NUM_COLEGIADO=P_NUM_COLEGIADO;}
	
	public String getP_NUM_COLEGIADO_NACIONAL(){
		return P_NUM_COLEGIADO_NACIONAL;}
 
	public void setP_NUM_COLEGIADO_NACIONAL(String P_NUM_COLEGIADO_NACIONAL){
		this.P_NUM_COLEGIADO_NACIONAL=P_NUM_COLEGIADO_NACIONAL;}
 
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