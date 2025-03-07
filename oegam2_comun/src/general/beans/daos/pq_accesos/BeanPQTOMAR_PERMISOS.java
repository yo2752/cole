package general.beans.daos.pq_accesos;
 
import general.beans.RespuestaGenerica;

import java.math.BigDecimal;
import java.util.List;

import trafico.beans.daos.BeanPQGeneral;
import utilidades.constantes.ValoresCatalog;
 
public class BeanPQTOMAR_PERMISOS extends BeanPQGeneral{
 
 
public static final String PROCEDURE="TOMAR_PERMISOS";
 
public List<Object> execute(Class<?> claseCursor){
RespuestaGenerica respuestaGenerica = ejecutarProc(this,SCHEMA,ValoresCatalog.PQ_ACCESOS,PROCEDURE,claseCursor,true);
return respuestaGenerica.getListaCursor();
}
public void execute(){
ejecutarProc(this,SCHEMA,ValoresCatalog.PQ_ACCESOS,PROCEDURE,null,true);
}
	private BigDecimal P_ID_USUARIO;
 
	private BigDecimal P_ID_CONTRATO;
 
	private String P_TIPO;
 
	private Object C_FUNCIONES_USUARIO;
 
	public BigDecimal getP_ID_USUARIO(){
		return P_ID_USUARIO;}
 
	public void setP_ID_USUARIO(BigDecimal P_ID_USUARIO){
		this.P_ID_USUARIO=P_ID_USUARIO;}
 
	public BigDecimal getP_ID_CONTRATO(){
		return P_ID_CONTRATO;}
 
	public void setP_ID_CONTRATO(BigDecimal P_ID_CONTRATO){
		this.P_ID_CONTRATO=P_ID_CONTRATO;}
 
	public String getP_TIPO(){
		return P_TIPO;}
 
	public void setP_TIPO(String P_TIPO){
		this.P_TIPO=P_TIPO;}
 
	public Object getC_FUNCIONES_USUARIO(){
		return C_FUNCIONES_USUARIO;}
 
	public void setC_FUNCIONES_USUARIO(Object C_FUNCIONES_USUARIO){
		this.C_FUNCIONES_USUARIO=C_FUNCIONES_USUARIO;}
 
}