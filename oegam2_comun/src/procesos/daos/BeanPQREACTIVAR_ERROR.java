package procesos.daos;
 
import general.beans.RespuestaGenerica;

import java.math.BigDecimal;
import java.util.List;

import trafico.beans.daos.BeanPQGeneral;
import utilidades.constantes.ValoresCatalog;
 
public class BeanPQREACTIVAR_ERROR extends BeanPQGeneral{
 
 
public static final String PROCEDURE="REACTIVAR_ERROR";
 
public List<Object> execute(Class<?> claseCursor){
RespuestaGenerica respuestaGenerica = ejecutarProc(this,SCHEMA,ValoresCatalog.PQ_PROCESO,PROCEDURE,claseCursor,true);
return respuestaGenerica.getListaCursor();
}
public void execute(){
ejecutarProc(this,SCHEMA,ValoresCatalog.PQ_PROCESO,PROCEDURE,null,true);
}
	private BigDecimal P_ID_ENVIO;
 
	public BigDecimal getP_ID_ENVIO(){
		return P_ID_ENVIO;}
 
	public void setP_ID_ENVIO(BigDecimal P_ID_ENVIO){
		this.P_ID_ENVIO=P_ID_ENVIO;}
 
}