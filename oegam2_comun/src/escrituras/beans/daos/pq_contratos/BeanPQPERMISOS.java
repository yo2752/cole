package escrituras.beans.daos.pq_contratos;
 
import general.beans.RespuestaGenerica;

import java.math.BigDecimal;
import java.util.List;

import trafico.beans.daos.BeanPQGeneral;
import utilidades.constantes.ValoresCatalog;
 
public class BeanPQPERMISOS extends BeanPQGeneral{
 
 
public static final String PROCEDURE="PERMISOS";
 
public List<Object> execute(Class<?> claseCursor){
RespuestaGenerica respuestaGenerica = ejecutarProc(this,SCHEMA,ValoresCatalog.PQ_CONTRATOS,PROCEDURE,claseCursor,true);
return respuestaGenerica.getListaCursor();
}
public void execute(){
ejecutarProc(this,SCHEMA,ValoresCatalog.PQ_CONTRATOS,PROCEDURE,null,true);
}
	private BigDecimal P_ID_CONTRATO;
 
	private String P_CODIGO_APLICACION;
 
	private Object C_FUNCIONES;
 
	public BigDecimal getP_ID_CONTRATO(){
		return P_ID_CONTRATO;}
 
	public void setP_ID_CONTRATO(BigDecimal P_ID_CONTRATO){
		this.P_ID_CONTRATO=P_ID_CONTRATO;}
 
	public String getP_CODIGO_APLICACION(){
		return P_CODIGO_APLICACION;}
 
	public void setP_CODIGO_APLICACION(String P_CODIGO_APLICACION){
		this.P_CODIGO_APLICACION=P_CODIGO_APLICACION;}
 
	public Object getC_FUNCIONES(){
		return C_FUNCIONES;}
 
	public void setC_FUNCIONES(Object C_FUNCIONES){
		this.C_FUNCIONES=C_FUNCIONES;}
 
}