package trafico.beans.daos.pq_tramite_trafico;
 
import general.beans.RespuestaGenerica;

import java.math.BigDecimal;
import java.util.List;

import trafico.beans.daos.BeanPQGeneral;
import utilidades.constantes.ValoresCatalog;

public class BeanPQINTERVINIENTES extends BeanPQGeneral{
 
 
public static final String PROCEDURE="INTERVINIENTES";
 
public List<Object> execute(Class<?> claseCursor){
RespuestaGenerica respuestaGenerica = ejecutarProc(this,SCHEMA,ValoresCatalog.PQ_TRAMITE_TRAFICO,PROCEDURE,claseCursor,true);
return respuestaGenerica.getListaCursor();
}
public void execute(){
ejecutarProc(this,SCHEMA,ValoresCatalog.PQ_TRAMITE_TRAFICO,PROCEDURE,null,true);
}
	private BigDecimal P_ID_CONTRATO_SESSION;
 
	private BigDecimal P_NUM_EXPEDIENTE;
 
	private BigDecimal PAGINA;
 
	private BigDecimal NUM_REG;
 
	private String COLUMNA_ORDEN;
 
	private String ORDEN;
 
	private BigDecimal CUENTA;
 
	private Object C_INTERVINIENTES;
 
	public BigDecimal getP_ID_CONTRATO_SESSION(){
		return P_ID_CONTRATO_SESSION;}
 
	public void setP_ID_CONTRATO_SESSION(BigDecimal P_ID_CONTRATO_SESSION){
		this.P_ID_CONTRATO_SESSION=P_ID_CONTRATO_SESSION;}
 
	public BigDecimal getP_NUM_EXPEDIENTE(){
		return P_NUM_EXPEDIENTE;}
 
	public void setP_NUM_EXPEDIENTE(BigDecimal P_NUM_EXPEDIENTE){
		this.P_NUM_EXPEDIENTE=P_NUM_EXPEDIENTE;}
 
	public BigDecimal getPAGINA(){
		return PAGINA;}
 
	public void setPAGINA(BigDecimal PAGINA){
		this.PAGINA=PAGINA;}
 
	public BigDecimal getNUM_REG(){
		return NUM_REG;}
 
	public void setNUM_REG(BigDecimal NUM_REG){
		this.NUM_REG=NUM_REG;}
 
	public String getCOLUMNA_ORDEN(){
		return COLUMNA_ORDEN;}
 
	public void setCOLUMNA_ORDEN(String COLUMNA_ORDEN){
		this.COLUMNA_ORDEN=COLUMNA_ORDEN;}
 
	public String getORDEN(){
		return ORDEN;}
 
	public void setORDEN(String ORDEN){
		this.ORDEN=ORDEN;}
 
	public BigDecimal getCUENTA(){
		return CUENTA;}
 
	public void setCUENTA(BigDecimal CUENTA){
		this.CUENTA=CUENTA;}
 
	public Object getC_INTERVINIENTES(){
		return C_INTERVINIENTES;}
 
	public void setC_INTERVINIENTES(Object C_INTERVINIENTES){
		this.C_INTERVINIENTES=C_INTERVINIENTES;}
 
}