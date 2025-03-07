package general.beans.daos.pq_personas2;
 
import general.beans.RespuestaGenerica;

import java.util.List;

import trafico.beans.daos.BeanPQGeneral;
import utilidades.constantes.ValoresCatalog;

public class BeanPQVIA_EQUIVALENTE extends BeanPQGeneral{
 
 
public static final String PROCEDURE="VIA_EQUIVALENTE";
 
public List<Object> execute(Class<?> claseCursor){
RespuestaGenerica respuestaGenerica = ejecutarProc(this,SCHEMA,ValoresCatalog.PQ_PERSONAS,PROCEDURE,claseCursor,true);
return respuestaGenerica.getListaCursor();
}
public void execute(){
ejecutarProc(this,SCHEMA,ValoresCatalog.PQ_PERSONAS,PROCEDURE,null,true);
}
	private String P_ID_TIPO_VIA;
 
	private String P_ID_PROVINCIA;
 
	private String P_ID_MUNICIPIO;
 
	private String P_ORGANISMO;
 
	private String P_VIA_EQUI;
 
	private String P_DESCRIPCION;
 
	private String P_PROVINCIA;
 
	private String P_ID_MUNICIPIO_CAM;
 
	private String P_NOMBRE;
 
	public String getP_ID_TIPO_VIA(){
		return P_ID_TIPO_VIA;}
 
	public void setP_ID_TIPO_VIA(String P_ID_TIPO_VIA){
		this.P_ID_TIPO_VIA=P_ID_TIPO_VIA;}
 
	public String getP_ID_PROVINCIA(){
		return P_ID_PROVINCIA;}
 
	public void setP_ID_PROVINCIA(String P_ID_PROVINCIA){
		this.P_ID_PROVINCIA=P_ID_PROVINCIA;}
 
	public String getP_ID_MUNICIPIO(){
		return P_ID_MUNICIPIO;}
 
	public void setP_ID_MUNICIPIO(String P_ID_MUNICIPIO){
		this.P_ID_MUNICIPIO=P_ID_MUNICIPIO;}
 
	public String getP_ORGANISMO(){
		return P_ORGANISMO;}
 
	public void setP_ORGANISMO(String P_ORGANISMO){
		this.P_ORGANISMO=P_ORGANISMO;}
 
	public String getP_VIA_EQUI(){
		return P_VIA_EQUI;}
 
	public void setP_VIA_EQUI(String P_VIA_EQUI){
		this.P_VIA_EQUI=P_VIA_EQUI;}
 
	public String getP_DESCRIPCION(){
		return P_DESCRIPCION;}
 
	public void setP_DESCRIPCION(String P_DESCRIPCION){
		this.P_DESCRIPCION=P_DESCRIPCION;}
 
	public String getP_PROVINCIA(){
		return P_PROVINCIA;}
 
	public void setP_PROVINCIA(String P_PROVINCIA){
		this.P_PROVINCIA=P_PROVINCIA;}
 
	public String getP_ID_MUNICIPIO_CAM(){
		return P_ID_MUNICIPIO_CAM;}
 
	public void setP_ID_MUNICIPIO_CAM(String P_ID_MUNICIPIO_CAM){
		this.P_ID_MUNICIPIO_CAM=P_ID_MUNICIPIO_CAM;}
 
	public String getP_NOMBRE(){
		return P_NOMBRE;}
 
	public void setP_NOMBRE(String P_NOMBRE){
		this.P_NOMBRE=P_NOMBRE;}
 
}