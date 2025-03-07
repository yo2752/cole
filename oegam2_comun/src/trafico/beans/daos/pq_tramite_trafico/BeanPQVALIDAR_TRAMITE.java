package trafico.beans.daos.pq_tramite_trafico;
 
import general.beans.RespuestaGenerica;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import trafico.beans.daos.BeanPQGeneral;
import utilidades.constantes.ValoresCatalog;

public class BeanPQVALIDAR_TRAMITE extends BeanPQGeneral{
 
 
public static final String PROCEDURE="VALIDAR_TRAMITE";
 
public List<Object> execute(Class<?> claseCursor){
RespuestaGenerica respuestaGenerica = ejecutarProc(this,SCHEMA,ValoresCatalog.PQ_TRAMITE_TRAFICO,PROCEDURE,claseCursor,true);
return respuestaGenerica.getListaCursor();
}
public void execute(){
ejecutarProc(this,SCHEMA,ValoresCatalog.PQ_TRAMITE_TRAFICO,PROCEDURE,null,true);
}
	private BigDecimal P_NUM_EXPEDIENTE;
 
	private BigDecimal P_ESTADO;
 
	private Timestamp P_FECHA_ULT_MODIF;
 
	private BigDecimal P_ID_USUARIO;
	
	private BigDecimal P_ID_CONTRATO_SESSION;
 
	public BigDecimal getP_NUM_EXPEDIENTE(){
		return P_NUM_EXPEDIENTE;}
 
	public void setP_NUM_EXPEDIENTE(BigDecimal P_NUM_EXPEDIENTE){
		this.P_NUM_EXPEDIENTE=P_NUM_EXPEDIENTE;}
 
	public BigDecimal getP_ESTADO(){
		return P_ESTADO;}
 
	public void setP_ESTADO(BigDecimal P_ESTADO){
		this.P_ESTADO=P_ESTADO;}
 
	public Timestamp getP_FECHA_ULT_MODIF(){
		return P_FECHA_ULT_MODIF;}
 
	public void setP_FECHA_ULT_MODIF(Timestamp P_FECHA_ULT_MODIF){
		this.P_FECHA_ULT_MODIF=P_FECHA_ULT_MODIF;}
 
	public BigDecimal getP_ID_USUARIO(){
		return P_ID_USUARIO;}
 
	public void setP_ID_USUARIO(BigDecimal P_ID_USUARIO){
		this.P_ID_USUARIO=P_ID_USUARIO;}
	
	public BigDecimal getP_ID_CONTRATO_SESSION() {
		return P_ID_CONTRATO_SESSION;
	}
	
	public void setP_ID_CONTRATO_SESSION(BigDecimal p_ID_CONTRATO_SESSION) {
		P_ID_CONTRATO_SESSION = p_ID_CONTRATO_SESSION;
	}
 
}