package escrituras.beans.daos.pq_usuarios;
 
import general.beans.RespuestaGenerica;

import java.util.List;

import trafico.beans.daos.BeanPQGeneral;
import utilidades.constantes.ValoresCatalog;

public class BeanPQUSUARIOS extends BeanPQGeneral{
 
 
public static final String PROCEDURE="USUARIOS";
 
public List<Object> execute(Class<?> claseCursor){
RespuestaGenerica respuestaGenerica = ejecutarProc(this,SCHEMA,ValoresCatalog.PQ_USUARIOS,PROCEDURE,claseCursor,true);
return respuestaGenerica.getListaCursor();
}
public void execute(){
ejecutarProc(this,SCHEMA,ValoresCatalog.PQ_USUARIOS,PROCEDURE,null,true);
}
	private String P_NUM_COLEGIADO;
 
	private Object C_USUARIOS;
 
	public String getP_NUM_COLEGIADO(){
		return P_NUM_COLEGIADO;}
 
	public void setP_NUM_COLEGIADO(String P_NUM_COLEGIADO){
		this.P_NUM_COLEGIADO=P_NUM_COLEGIADO;}
 
	public Object getC_USUARIOS(){
		return C_USUARIOS;}
 
	public void setC_USUARIOS(Object C_USUARIOS){
		this.C_USUARIOS=C_USUARIOS;}
 
}