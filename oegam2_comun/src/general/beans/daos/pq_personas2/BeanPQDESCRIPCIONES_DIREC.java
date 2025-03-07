package general.beans.daos.pq_personas2;
 
import general.beans.RespuestaGenerica;

import java.math.BigDecimal;
import java.util.List;

import trafico.beans.daos.BeanPQGeneral;
import utilidades.constantes.ValoresCatalog;
 
public class BeanPQDESCRIPCIONES_DIREC extends BeanPQGeneral{
 
 
 
public static final String PROCEDURE="DESCRIPCIONES_DIREC";
 
public List<Object> execute(Class<?> claseCursor){
RespuestaGenerica respuestaGenerica = ejecutarProc(this,SCHEMA,ValoresCatalog.PQ_PERSONAS,PROCEDURE,claseCursor,true);
return respuestaGenerica.getListaCursor();
}
public void execute(){
ejecutarProc(this,SCHEMA,ValoresCatalog.PQ_PERSONAS,PROCEDURE,null,true);
}
	private BigDecimal P_ID_DIRECCION;
 
	private String P_ID_PROVINCIA;
 
	private String P_PROV_NOMBRE;
 
	private String P_ID_MUNICIPIO;
 
	private String P_MUNI_NOMBRE;
 
	private String P_ID_TIPO_VIA;
 
	private String P_TIP_VIA_NOMBRE;
 
	public BigDecimal getP_ID_DIRECCION(){
		return P_ID_DIRECCION;}
 
	public void setP_ID_DIRECCION(BigDecimal P_ID_DIRECCION){
		this.P_ID_DIRECCION=P_ID_DIRECCION;}
 
	public String getP_ID_PROVINCIA(){
		return P_ID_PROVINCIA;}
 
	public void setP_ID_PROVINCIA(String P_ID_PROVINCIA){
		this.P_ID_PROVINCIA=P_ID_PROVINCIA;}
 
	public String getP_PROV_NOMBRE(){
		return P_PROV_NOMBRE;}
 
	public void setP_PROV_NOMBRE(String P_PROV_NOMBRE){
		this.P_PROV_NOMBRE=P_PROV_NOMBRE;}
 
	public String getP_ID_MUNICIPIO(){
		return P_ID_MUNICIPIO;}
 
	public void setP_ID_MUNICIPIO(String P_ID_MUNICIPIO){
		this.P_ID_MUNICIPIO=P_ID_MUNICIPIO;}
 
	public String getP_MUNI_NOMBRE(){
		return P_MUNI_NOMBRE;}
 
	public void setP_MUNI_NOMBRE(String P_MUNI_NOMBRE){
		this.P_MUNI_NOMBRE=P_MUNI_NOMBRE;}
 
	public String getP_ID_TIPO_VIA(){
		return P_ID_TIPO_VIA;}
 
	public void setP_ID_TIPO_VIA(String P_ID_TIPO_VIA){
		this.P_ID_TIPO_VIA=P_ID_TIPO_VIA;}
 
	public String getP_TIP_VIA_NOMBRE(){
		return P_TIP_VIA_NOMBRE;}
 
	public void setP_TIP_VIA_NOMBRE(String P_TIP_VIA_NOMBRE){
		this.P_TIP_VIA_NOMBRE=P_TIP_VIA_NOMBRE;}
 
}