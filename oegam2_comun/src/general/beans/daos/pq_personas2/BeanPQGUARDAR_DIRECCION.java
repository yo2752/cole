package general.beans.daos.pq_personas2;
 
import general.beans.RespuestaGenerica;

import java.math.BigDecimal;
import java.util.List;

import trafico.beans.daos.BeanPQGeneral;
import utilidades.constantes.ValoresCatalog;

public class BeanPQGUARDAR_DIRECCION extends BeanPQGeneral{
 

 
public static final String PROCEDURE="GUARDAR_DIRECCION";
 
public List<Object> execute(Class<?> claseCursor){
RespuestaGenerica respuestaGenerica = ejecutarProc(this,SCHEMA,ValoresCatalog.PQ_PERSONAS,PROCEDURE,claseCursor,true);
return respuestaGenerica.getListaCursor();
}
public void execute(){
ejecutarProc(this,SCHEMA,ValoresCatalog.PQ_PERSONAS,PROCEDURE,null,true);
}
	private BigDecimal P_ID_DIRECCION;
 
	private String P_ID_PROVINCIA;
 
	private String P_ID_MUNICIPIO;
 
	private String P_ID_TIPO_VIA;
 
	private String P_NOMBRE_VIA;
 
	private String P_NUMERO;
 
	private String P_COD_POSTAL;
 
	private String P_PUEBLO;
 
	private String P_LETRA;
 
	private String P_ESCALERA;
 
	private String P_BLOQUE;
 
	private String P_PLANTA;
 
	private String P_PUERTA;
 
	private BigDecimal P_NUM_LOCAL;
 
	private BigDecimal P_KM;
 
	private BigDecimal P_HM;
 
	public BigDecimal getP_ID_DIRECCION(){
		return P_ID_DIRECCION;}
 
	public void setP_ID_DIRECCION(BigDecimal P_ID_DIRECCION){
		this.P_ID_DIRECCION=P_ID_DIRECCION;}
 
	public String getP_ID_PROVINCIA(){
		return P_ID_PROVINCIA;}
 
	public void setP_ID_PROVINCIA(String P_ID_PROVINCIA){
		this.P_ID_PROVINCIA=P_ID_PROVINCIA;}
 
	public String getP_ID_MUNICIPIO(){
		return P_ID_MUNICIPIO;}
 
	public void setP_ID_MUNICIPIO(String P_ID_MUNICIPIO){
		this.P_ID_MUNICIPIO=P_ID_MUNICIPIO;}
 
	public String getP_ID_TIPO_VIA(){
		return P_ID_TIPO_VIA;}
 
	public void setP_ID_TIPO_VIA(String P_ID_TIPO_VIA){
		this.P_ID_TIPO_VIA=P_ID_TIPO_VIA;}
 
	public String getP_NOMBRE_VIA(){
		return P_NOMBRE_VIA;}
 
	public void setP_NOMBRE_VIA(String P_NOMBRE_VIA){
		this.P_NOMBRE_VIA=P_NOMBRE_VIA;}
 
	public String getP_NUMERO(){
		return P_NUMERO;}
 
	public void setP_NUMERO(String P_NUMERO){
		this.P_NUMERO=P_NUMERO;}
 
	public String getP_COD_POSTAL(){
		return P_COD_POSTAL;}
 
	public void setP_COD_POSTAL(String P_COD_POSTAL){
		this.P_COD_POSTAL=P_COD_POSTAL;}
 
	public String getP_PUEBLO(){
		return P_PUEBLO;}
 
	public void setP_PUEBLO(String P_PUEBLO){
		this.P_PUEBLO=P_PUEBLO;}
 
	public String getP_LETRA(){
		return P_LETRA;}
 
	public void setP_LETRA(String P_LETRA){
		this.P_LETRA=P_LETRA;}
 
	public String getP_ESCALERA(){
		return P_ESCALERA;}
 
	public void setP_ESCALERA(String P_ESCALERA){
		this.P_ESCALERA=P_ESCALERA;}
 
	public String getP_BLOQUE(){
		return P_BLOQUE;}
 
	public void setP_BLOQUE(String P_BLOQUE){
		this.P_BLOQUE=P_BLOQUE;}
 
	public String getP_PLANTA(){
		return P_PLANTA;}
 
	public void setP_PLANTA(String P_PLANTA){
		this.P_PLANTA=P_PLANTA;}
 
	public String getP_PUERTA(){
		return P_PUERTA;}
 
	public void setP_PUERTA(String P_PUERTA){
		this.P_PUERTA=P_PUERTA;}
 
	public BigDecimal getP_NUM_LOCAL(){
		return P_NUM_LOCAL;}
 
	public void setP_NUM_LOCAL(BigDecimal P_NUM_LOCAL){
		this.P_NUM_LOCAL=P_NUM_LOCAL;}
 
	public BigDecimal getP_KM(){
		return P_KM;}
 
	public void setP_KM(BigDecimal P_KM){
		this.P_KM=P_KM;}
 
	public BigDecimal getP_HM(){
		return P_HM;}
 
	public void setP_HM(BigDecimal P_HM){
		this.P_HM=P_HM;}
 
}