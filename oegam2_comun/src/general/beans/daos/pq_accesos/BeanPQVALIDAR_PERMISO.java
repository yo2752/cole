package general.beans.daos.pq_accesos;
 
import general.beans.RespuestaGenerica;

import java.math.BigDecimal;
import java.util.List;

import trafico.beans.daos.BeanPQGeneral;
import utilidades.constantes.ValoresCatalog;
 
public class BeanPQVALIDAR_PERMISO extends BeanPQGeneral{
 
 
public static final String PROCEDURE="VALIDAR_PERMISO";
 
public List<Object> execute(Class<?> claseCursor){
RespuestaGenerica respuestaGenerica = ejecutarProc(this,SCHEMA,ValoresCatalog.PQ_ACCESOS,PROCEDURE,claseCursor,true);
return respuestaGenerica.getListaCursor();
}
public void execute(){
ejecutarProc(this,SCHEMA,ValoresCatalog.PQ_ACCESOS,PROCEDURE,null,true);
}
	private BigDecimal P_ID_USUARIO;
 
	private BigDecimal P_ID_CONTRATO;
 
	private String P_CODIGO_FUNCION;
 
	private BigDecimal P_ACCESO;
 
	public BigDecimal getP_ID_USUARIO(){
		return P_ID_USUARIO;}
 
	public void setP_ID_USUARIO(BigDecimal P_ID_USUARIO){
		this.P_ID_USUARIO=P_ID_USUARIO;}
 
	public BigDecimal getP_ID_CONTRATO(){
		return P_ID_CONTRATO;}
 
	public void setP_ID_CONTRATO(BigDecimal P_ID_CONTRATO){
		this.P_ID_CONTRATO=P_ID_CONTRATO;}
 
	public String getP_CODIGO_FUNCION(){
		return P_CODIGO_FUNCION;}
 
	public void setP_CODIGO_FUNCION(String P_CODIGO_FUNCION){
		this.P_CODIGO_FUNCION=P_CODIGO_FUNCION;}
 
	public BigDecimal getP_ACCESO(){
		return P_ACCESO;}
 
	public void setP_ACCESO(BigDecimal P_ACCESO){
		this.P_ACCESO=P_ACCESO;}
 
}