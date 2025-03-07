package trafico.beans.daos.pq_tramite_trafico;
 
import general.beans.RespuestaGenerica;

import java.math.BigDecimal;
import java.util.List;

import trafico.beans.daos.BeanPQGeneral;
import utilidades.constantes.ValoresCatalog;

public class BeanPQGUARDAR_FACTURACION extends BeanPQGeneral{
 
 
public static final String PROCEDURE="GUARDAR_FACTURACION";
 
public List<Object> execute(Class<?> claseCursor){
RespuestaGenerica respuestaGenerica = ejecutarProc(this,SCHEMA,ValoresCatalog.PQ_TRAMITE_TRAFICO,PROCEDURE,claseCursor,true);
return respuestaGenerica.getListaCursor();
}
public void execute(){
ejecutarProc(this,SCHEMA,ValoresCatalog.PQ_TRAMITE_TRAFICO,PROCEDURE,null,true);
}
	private BigDecimal P_NUM_EXPEDIENTE;
 
	private String P_MATRICULA;
 
	private String P_NIF;
 
	private String P_CODIGO_TASA;
 
	private String P_TIPO_TRAMITE;
 
	private String P_BASTIDOR;
 
	public BigDecimal getP_NUM_EXPEDIENTE(){
		return P_NUM_EXPEDIENTE;}
 
	public void setP_NUM_EXPEDIENTE(BigDecimal P_NUM_EXPEDIENTE){
		this.P_NUM_EXPEDIENTE=P_NUM_EXPEDIENTE;}
 
	public String getP_MATRICULA(){
		return P_MATRICULA;}
 
	public void setP_MATRICULA(String P_MATRICULA){
		this.P_MATRICULA=P_MATRICULA;}
 
	public String getP_NIF(){
		return P_NIF;}
 
	public void setP_NIF(String P_NIF){
		this.P_NIF=P_NIF;}
 
	public String getP_CODIGO_TASA(){
		return P_CODIGO_TASA;}
 
	public void setP_CODIGO_TASA(String P_CODIGO_TASA){
		this.P_CODIGO_TASA=P_CODIGO_TASA;}
 
	public String getP_TIPO_TRAMITE(){
		return P_TIPO_TRAMITE;}
 
	public void setP_TIPO_TRAMITE(String P_TIPO_TRAMITE){
		this.P_TIPO_TRAMITE=P_TIPO_TRAMITE;}
 
	public String getP_BASTIDOR(){
		return P_BASTIDOR;}
 
	public void setP_BASTIDOR(String P_BASTIDOR){
		this.P_BASTIDOR=P_BASTIDOR;}
 
}