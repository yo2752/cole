package trafico.beans.daos.pq_tramite_trafico;
 
import general.beans.RespuestaGenerica;

import java.math.BigDecimal;
import java.util.List;

import trafico.beans.daos.BeanPQGeneral;
import utilidades.constantes.ValoresCatalog;

public class BeanPQDUPLICAR extends BeanPQGeneral{
 
 
public static final String PROCEDURE="DUPLICAR";
 
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
 
	private BigDecimal P_NUM_EXPEDIENTE_ORI;
 
	private BigDecimal P_NUM_EXPEDIENTE_NUE;
 
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
 
	public BigDecimal getP_NUM_EXPEDIENTE_ORI(){
		return P_NUM_EXPEDIENTE_ORI;}
 
	public void setP_NUM_EXPEDIENTE_ORI(BigDecimal P_NUM_EXPEDIENTE_ORI){
		this.P_NUM_EXPEDIENTE_ORI=P_NUM_EXPEDIENTE_ORI;}
 
	public BigDecimal getP_NUM_EXPEDIENTE_NUE(){
		return P_NUM_EXPEDIENTE_NUE;}
 
	public void setP_NUM_EXPEDIENTE_NUE(BigDecimal P_NUM_EXPEDIENTE_NUE){
		this.P_NUM_EXPEDIENTE_NUE=P_NUM_EXPEDIENTE_NUE;}
 
}