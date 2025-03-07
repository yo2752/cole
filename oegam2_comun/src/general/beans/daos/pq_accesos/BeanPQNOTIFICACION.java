package general.beans.daos.pq_accesos;
 
import general.beans.RespuestaGenerica;

import java.math.BigDecimal;
import java.util.List;

import trafico.beans.daos.BeanPQGeneral;
import utilidades.constantes.ValoresCatalog;
 
public class BeanPQNOTIFICACION extends BeanPQGeneral{
 
 
public static final String PROCEDURE="NOTIFICACION";
 
public List<Object> execute(Class<?> claseCursor){
RespuestaGenerica respuestaGenerica = ejecutarProc(this,SCHEMA,ValoresCatalog.PQ_ACCESOS,PROCEDURE,claseCursor,true);
return respuestaGenerica.getListaCursor();
}
public void execute(){
ejecutarProc(this,SCHEMA,ValoresCatalog.PQ_ACCESOS,PROCEDURE,null,true);
}
	private BigDecimal P_ID_USUARIO;
 
	private BigDecimal P_RENOVAR;
 
	public BigDecimal getP_ID_USUARIO(){
		return P_ID_USUARIO;}
 
	public void setP_ID_USUARIO(BigDecimal P_ID_USUARIO){
		this.P_ID_USUARIO=P_ID_USUARIO;}
 
	public BigDecimal getP_RENOVAR(){
		return P_RENOVAR;}
 
	public void setP_RENOVAR(BigDecimal P_RENOVAR){
		this.P_RENOVAR=P_RENOVAR;}
 
}