package trafico.beans.daos.pq_tramite_trafico;
 
import general.beans.RespuestaGenerica;

import java.math.BigDecimal;
import java.util.List;

import trafico.beans.daos.BeanPQGeneral;
import utilidades.constantes.ValoresCatalog;

public class BeanPQCARGAR_CET extends BeanPQGeneral{
 
 
public static final String PROCEDURE="CARGAR_CET";
 
public List<Object> execute(Class<?> claseCursor){
RespuestaGenerica respuestaGenerica = ejecutarProc(this,SCHEMA,ValoresCatalog.PQ_TRAMITE_TRAFICO,PROCEDURE,claseCursor,true);
return respuestaGenerica.getListaCursor();
}
public void execute(){
ejecutarProc(this,SCHEMA,ValoresCatalog.PQ_TRAMITE_TRAFICO,PROCEDURE,null,true);
}
	private BigDecimal P_ID_USUARIO;
 
	private BigDecimal P_ID_CONTRATO_SESSION;
 
	private BigDecimal P_ID_CONTRATO;
 
	private String P_MATRICULA;
 
	private String P_CET;
 
	private BigDecimal P_NUM_EXPEDIENTE;
 
	public BigDecimal getP_ID_USUARIO(){
		return P_ID_USUARIO;}
 
	public void setP_ID_USUARIO(BigDecimal P_ID_USUARIO){
		this.P_ID_USUARIO=P_ID_USUARIO;}
 
	public BigDecimal getP_ID_CONTRATO_SESSION(){
		return P_ID_CONTRATO_SESSION;}
 
	public void setP_ID_CONTRATO_SESSION(BigDecimal P_ID_CONTRATO_SESSION){
		this.P_ID_CONTRATO_SESSION=P_ID_CONTRATO_SESSION;}
 
	public BigDecimal getP_ID_CONTRATO(){
		return P_ID_CONTRATO;}
 
	public void setP_ID_CONTRATO(BigDecimal P_ID_CONTRATO){
		this.P_ID_CONTRATO=P_ID_CONTRATO;}
 
	public String getP_MATRICULA(){
		return P_MATRICULA;}
 
	public void setP_MATRICULA(String P_MATRICULA){
		this.P_MATRICULA=P_MATRICULA;}
 
	public String getP_CET(){
		return P_CET;}
 
	public void setP_CET(String P_CET){
		this.P_CET=P_CET;}
 
	public BigDecimal getP_NUM_EXPEDIENTE(){
		return P_NUM_EXPEDIENTE;}
 
	public void setP_NUM_EXPEDIENTE(BigDecimal P_NUM_EXPEDIENTE){
		this.P_NUM_EXPEDIENTE=P_NUM_EXPEDIENTE;}
 
}