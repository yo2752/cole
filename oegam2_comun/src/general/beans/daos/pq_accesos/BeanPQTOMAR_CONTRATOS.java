package general.beans.daos.pq_accesos;
 
import general.beans.RespuestaGenerica;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import trafico.beans.daos.BeanPQGeneral;
import utilidades.constantes.ValoresCatalog;
 
public class BeanPQTOMAR_CONTRATOS extends BeanPQGeneral{
 
 
public static final String PROCEDURE="TOMAR_CONTRATOS";
 
public List<Object> execute(Class<?> claseCursor){
RespuestaGenerica respuestaGenerica = ejecutarProc(this,SCHEMA,ValoresCatalog.PQ_ACCESOS,PROCEDURE,claseCursor,true);
return respuestaGenerica.getListaCursor();
}
public void execute(){
ejecutarProc(this,SCHEMA,ValoresCatalog.PQ_ACCESOS,PROCEDURE,null,true);
}
	private String P_NIF;
 
	private BigDecimal P_ID_USUARIO;
 
	private String P_NUM_COLEGIADO;
 
	private BigDecimal P_ESTADO_USUARIO;
 
	private String P_APELLIDOS_NOMBRE;
 
	private String P_ANAGRAMA;
 
	private String P_CORREO_ELECTRONICO;
 
	private Timestamp P_ULTIMA_CONEXION;
 
	private Timestamp P_FECHA_RENOVACION;
 
	private Object C_CONTRATOS;
 
	public String getP_NIF(){
		return P_NIF;}
 
	public void setP_NIF(String P_NIF){
		this.P_NIF=P_NIF;}
 
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
 
	public Object getC_CONTRATOS(){
		return C_CONTRATOS;}
 
	public void setC_CONTRATOS(Object C_CONTRATOS){
		this.C_CONTRATOS=C_CONTRATOS;}
 
}