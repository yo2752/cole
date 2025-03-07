package administracion.beans.daos.pq_creditos;
 
import general.beans.RespuestaGenerica;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import trafico.beans.daos.BeanPQGeneral;
import utilidades.constantes.ValoresCatalog;
 
public class BeanPQRESUMEN extends BeanPQGeneral{
 
public static final String PROCEDURE="RESUMEN";
 
public List<Object> execute(Class<?> claseCursor){
RespuestaGenerica respuestaGenerica = ejecutarProc(this,SCHEMA,ValoresCatalog.PQ_CREDITOS,PROCEDURE,claseCursor,true);
return respuestaGenerica.getListaCursor();
}
public void execute(){
ejecutarProc(this,SCHEMA,ValoresCatalog.PQ_CREDITOS,PROCEDURE,null,true);
}
	private BigDecimal P_ID_CONTRATO;
 
	private String P_TIPO_CREDITO;
 
	private Timestamp P_FECHA_DESDE;
 
	private Timestamp P_FECHA_HASTA;
 
	private BigDecimal PAGINA;
 
	private BigDecimal NUM_REG;
 
	private String COLUMNA_ORDEN;
 
	private String ORDEN;
 
	private BigDecimal CUENTA;
 
	private BigDecimal TOTAL_SUMA;
 
	private BigDecimal TOTAL_RESTA;
	
	private Object C_RESUMEN;
	
	public BigDecimal getP_ID_CONTRATO(){
		return P_ID_CONTRATO;}
 
	public void setP_ID_CONTRATO(BigDecimal P_ID_CONTRATO){
		this.P_ID_CONTRATO=P_ID_CONTRATO;}
 
	public String getP_TIPO_CREDITO(){
		return P_TIPO_CREDITO;}
 
	public void setP_TIPO_CREDITO(String P_TIPO_CREDITO){
		this.P_TIPO_CREDITO=P_TIPO_CREDITO;}
 
	public Timestamp getP_FECHA_DESDE(){
		return P_FECHA_DESDE;}
 
	public void setP_FECHA_DESDE(Timestamp P_FECHA_DESDE){
		this.P_FECHA_DESDE=P_FECHA_DESDE;}
 
	public Timestamp getP_FECHA_HASTA(){
		return P_FECHA_HASTA;}
 
	public void setP_FECHA_HASTA(Timestamp P_FECHA_HASTA){
		this.P_FECHA_HASTA=P_FECHA_HASTA;}
 
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
 
	public BigDecimal getTOTAL_SUMA(){
		return TOTAL_SUMA;}
 
	public void setTOTAL_SUMA(BigDecimal TOTAL_SUMA){
		this.TOTAL_SUMA=TOTAL_SUMA;}
 
	public BigDecimal getTOTAL_RESTA(){
		return TOTAL_RESTA;}
 
	public void setTOTAL_RESTA(BigDecimal TOTAL_RESTA){
		this.TOTAL_RESTA=TOTAL_RESTA;}
 
	public Object getC_RESUMEN(){
		return C_RESUMEN;}
 
	public void setC_RESUMEN(Object C_RESUMEN){
		this.C_RESUMEN=C_RESUMEN;}
 
}