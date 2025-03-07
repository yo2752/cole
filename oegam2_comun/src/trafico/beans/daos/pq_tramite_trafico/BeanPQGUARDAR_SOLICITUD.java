package trafico.beans.daos.pq_tramite_trafico;
 
import general.beans.RespuestaGenerica;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import trafico.beans.daos.BeanPQGeneral;
import utilidades.constantes.ValoresCatalog;

public class BeanPQGUARDAR_SOLICITUD extends BeanPQGeneral{
 
 
public static final String PROCEDURE="GUARDAR_SOLICITUD";
 
public List<Object> execute(Class<?> claseCursor){
RespuestaGenerica respuestaGenerica = ejecutarProc(this,SCHEMA,ValoresCatalog.PQ_TRAMITE_TRAFICO,PROCEDURE,claseCursor,true);
return respuestaGenerica.getListaCursor();
}
public void execute(){
ejecutarProc(this,SCHEMA,ValoresCatalog.PQ_TRAMITE_TRAFICO,PROCEDURE,null,true);
}
	private BigDecimal P_ID_USUARIO; 
	private BigDecimal P_ID_CONTRATO_SESSION; 
	private BigDecimal P_NUM_EXPEDIENTE; 
	private BigDecimal P_ID_CONTRATO; 
	private String P_NUM_COLEGIADO; 
	private BigDecimal P_ESTADO; 
	private String P_REF_PROPIA; 
	private Timestamp P_FECHA_ALTA; 
	private Timestamp P_FECHA_PRESENTACION; 
	private Timestamp P_FECHA_ULT_MODIF; 
	private Timestamp P_FECHA_IMPRESION; 
	private String P_ANOTACIONES;	
	private BigDecimal P_ID_TIPO_CREACION;
 
	public BigDecimal getP_ID_USUARIO(){
		return P_ID_USUARIO;}
 
	public void setP_ID_USUARIO(BigDecimal P_ID_USUARIO){
		this.P_ID_USUARIO=P_ID_USUARIO;}
 
	public BigDecimal getP_ID_CONTRATO_SESSION(){
		return P_ID_CONTRATO_SESSION;}
 
	public void setP_ID_CONTRATO_SESSION(BigDecimal P_ID_CONTRATO_SESSION){
		this.P_ID_CONTRATO_SESSION=P_ID_CONTRATO_SESSION;}
 
	public BigDecimal getP_NUM_EXPEDIENTE(){
		return P_NUM_EXPEDIENTE;}
 
	public void setP_NUM_EXPEDIENTE(BigDecimal P_NUM_EXPEDIENTE){
		this.P_NUM_EXPEDIENTE=P_NUM_EXPEDIENTE;}
 
	public BigDecimal getP_ID_CONTRATO(){
		return P_ID_CONTRATO;}
 
	public void setP_ID_CONTRATO(BigDecimal P_ID_CONTRATO){
		this.P_ID_CONTRATO=P_ID_CONTRATO;}
 
	public String getP_NUM_COLEGIADO(){
		return P_NUM_COLEGIADO;}
 
	public void setP_NUM_COLEGIADO(String P_NUM_COLEGIADO){
		this.P_NUM_COLEGIADO=P_NUM_COLEGIADO;}
 
	public BigDecimal getP_ESTADO(){
		return P_ESTADO;}
 
	public void setP_ESTADO(BigDecimal P_ESTADO){
		this.P_ESTADO=P_ESTADO;}
 
	public String getP_REF_PROPIA(){
		return P_REF_PROPIA;}
 
	public void setP_REF_PROPIA(String P_REF_PROPIA){
		this.P_REF_PROPIA=P_REF_PROPIA;}
 
	public Timestamp getP_FECHA_ALTA(){
		return P_FECHA_ALTA;}
 
	public void setP_FECHA_ALTA(Timestamp P_FECHA_ALTA){
		this.P_FECHA_ALTA=P_FECHA_ALTA;}
 
	public Timestamp getP_FECHA_PRESENTACION(){
		return P_FECHA_PRESENTACION;}
 
	public void setP_FECHA_PRESENTACION(Timestamp P_FECHA_PRESENTACION){
		this.P_FECHA_PRESENTACION=P_FECHA_PRESENTACION;}
 
	public Timestamp getP_FECHA_ULT_MODIF(){
		return P_FECHA_ULT_MODIF;}
 
	public void setP_FECHA_ULT_MODIF(Timestamp P_FECHA_ULT_MODIF){
		this.P_FECHA_ULT_MODIF=P_FECHA_ULT_MODIF;}
 
	public Timestamp getP_FECHA_IMPRESION(){
		return P_FECHA_IMPRESION;}
 
	public void setP_FECHA_IMPRESION(Timestamp P_FECHA_IMPRESION){
		this.P_FECHA_IMPRESION=P_FECHA_IMPRESION;}
 
	public String getP_ANOTACIONES(){
		return P_ANOTACIONES;}
 
	public void setP_ANOTACIONES(String P_ANOTACIONES){
		this.P_ANOTACIONES=P_ANOTACIONES;}
	
	public BigDecimal getP_ID_TIPO_CREACION() {
		return P_ID_TIPO_CREACION;
	}
	public void setP_ID_TIPO_CREACION(BigDecimal pIDTIPOCREACION) {
		P_ID_TIPO_CREACION = pIDTIPOCREACION;
	} 
}