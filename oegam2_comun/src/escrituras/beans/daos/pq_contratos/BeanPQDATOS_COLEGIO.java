package escrituras.beans.daos.pq_contratos;
 
import general.beans.RespuestaGenerica;

import java.util.List;

import trafico.beans.daos.BeanPQGeneral;
import utilidades.constantes.ValoresCatalog;
 
public class BeanPQDATOS_COLEGIO extends BeanPQGeneral{
 
 
public static final String PROCEDURE="DATOS_COLEGIO";
 
public List<Object> execute(Class<?> claseCursor){
RespuestaGenerica respuestaGenerica = ejecutarProc(this,SCHEMA,ValoresCatalog.PQ_CONTRATOS,PROCEDURE,claseCursor,true);
return respuestaGenerica.getListaCursor();
}
public void execute(){
ejecutarProc(this,SCHEMA,ValoresCatalog.PQ_CONTRATOS,PROCEDURE,null,true);
}
	private Object ROWID_COLEGIO;
 
	private String P_COLEGIO;
 
	private String P_NOMBRE;
 
	private String P_CIF;
 
	private String P_CORREO_ELECTRONICO;
 
	public Object getROWID_COLEGIO(){
		return ROWID_COLEGIO;}
 
	public void setROWID_COLEGIO(Object ROWID_COLEGIO){
		this.ROWID_COLEGIO=ROWID_COLEGIO;}
 
	public String getP_COLEGIO(){
		return P_COLEGIO;}
 
	public void setP_COLEGIO(String P_COLEGIO){
		this.P_COLEGIO=P_COLEGIO;}
 
	public String getP_NOMBRE(){
		return P_NOMBRE;}
 
	public void setP_NOMBRE(String P_NOMBRE){
		this.P_NOMBRE=P_NOMBRE;}
 
	public String getP_CIF(){
		return P_CIF;}
 
	public void setP_CIF(String P_CIF){
		this.P_CIF=P_CIF;}
 
	public String getP_CORREO_ELECTRONICO(){
		return P_CORREO_ELECTRONICO;}
 
	public void setP_CORREO_ELECTRONICO(String P_CORREO_ELECTRONICO){
		this.P_CORREO_ELECTRONICO=P_CORREO_ELECTRONICO;}
 
}