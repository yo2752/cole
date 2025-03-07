package general.beans.daos.pq_personas2;
 
import general.beans.RespuestaGenerica;

import java.util.List;

import trafico.beans.daos.BeanPQGeneral;
import utilidades.constantes.ValoresCatalog;

public class BeanPQVALIDA_NIF_CIF_NIE extends BeanPQGeneral{
 
 
public static final String PROCEDURE="VALIDA_NIF_CIF_NIE";
 
public List<Object> execute(Class<?> claseCursor){
RespuestaGenerica respuestaGenerica = ejecutarProc(this,SCHEMA,ValoresCatalog.PQ_PERSONAS,PROCEDURE,claseCursor,true);
return respuestaGenerica.getListaCursor();
}
public void execute(){
ejecutarProc(this,SCHEMA,ValoresCatalog.PQ_PERSONAS,PROCEDURE,null,true);
}
	private String P_CIF;
 
	public String getP_CIF(){
		return P_CIF;}
 
	public void setP_CIF(String P_CIF){
		this.P_CIF=P_CIF;}
 
}