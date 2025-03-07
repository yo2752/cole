package trafico.beans.daos.pq_tramite_trafico;
 
import general.beans.RespuestaGenerica;

import java.math.BigDecimal;
import java.util.List;

import trafico.beans.daos.BeanPQGeneral;
import utilidades.constantes.ValoresCatalog;


public class BeanPQCONTROL_IMPORTACION extends BeanPQGeneral{
 
 
public static final String PROCEDURE="CONTROL_IMPORTACION";
 
public List<Object> execute(Class<?> claseCursor){
RespuestaGenerica respuestaGenerica = ejecutarProc(this,SCHEMA,ValoresCatalog.PQ_TRAMITE_TRAFICO,PROCEDURE,claseCursor,true);
return respuestaGenerica.getListaCursor();
}
public void execute(){
ejecutarProc(this,SCHEMA,ValoresCatalog.PQ_TRAMITE_TRAFICO,PROCEDURE,null,true);
}
	private BigDecimal P_NUM_EXPEDIENTE;
 
	private String P_TOKEN_SEGURIDAD;
 
	public BigDecimal getP_NUM_EXPEDIENTE(){
		return P_NUM_EXPEDIENTE;}
 
	public void setP_NUM_EXPEDIENTE(BigDecimal P_NUM_EXPEDIENTE){
		this.P_NUM_EXPEDIENTE=P_NUM_EXPEDIENTE;}
 
	public String getP_TOKEN_SEGURIDAD(){
		return P_TOKEN_SEGURIDAD;}
 
	public void setP_TOKEN_SEGURIDAD(String P_TOKEN_SEGURIDAD){
		this.P_TOKEN_SEGURIDAD=P_TOKEN_SEGURIDAD;}
 
}