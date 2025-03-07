package escrituras.beans.daos.pq_contratos;
 
import general.beans.RespuestaGenerica;

import java.math.BigDecimal;
import java.util.List;

import trafico.beans.daos.BeanPQGeneral;
import utilidades.constantes.ValoresCatalog;
 
public class BeanPQBUSQUEDA extends BeanPQGeneral{
 
 
public static final String PROCEDURE="BUSQUEDA";
 
public List<Object> execute(Class<?> claseCursor){
RespuestaGenerica respuestaGenerica = ejecutarProc(this,SCHEMA,ValoresCatalog.PQ_CONTRATOS,PROCEDURE,claseCursor,true);
return respuestaGenerica.getListaCursor();
}
public void execute(){
ejecutarProc(this,SCHEMA,ValoresCatalog.PQ_CONTRATOS,PROCEDURE,null,true);
}
	private String P_NUM_COLEGIADO;
 
	private String P_RAZON_SOCIAL;
 
	private BigDecimal P_ESTADO;
 
	private String P_CIF;
 
	private BigDecimal P_ID_USUARIO;
 
	private BigDecimal P_ID_CONTRATO;
 
	private BigDecimal PAGINA;
 
	private BigDecimal NUM_REG;
 
	private String COLUMNA_ORDEN;
 
	private String ORDEN;
 
	private BigDecimal CUENTA;
 
	private Object C_CONTRATOS;
 
	public String getP_NUM_COLEGIADO(){
		return P_NUM_COLEGIADO;}
 
	public void setP_NUM_COLEGIADO(String P_NUM_COLEGIADO){
		this.P_NUM_COLEGIADO=P_NUM_COLEGIADO;}
 
	public String getP_RAZON_SOCIAL(){
		return P_RAZON_SOCIAL;}
 
	public void setP_RAZON_SOCIAL(String P_RAZON_SOCIAL){
		this.P_RAZON_SOCIAL=P_RAZON_SOCIAL;}
 
	public BigDecimal getP_ESTADO(){
		return P_ESTADO;}
 
	public void setP_ESTADO(BigDecimal P_ESTADO){
		this.P_ESTADO=P_ESTADO;}
 
	public String getP_CIF(){
		return P_CIF;}
 
	public void setP_CIF(String P_CIF){
		this.P_CIF=P_CIF;}
 
	public BigDecimal getP_ID_USUARIO(){
		return P_ID_USUARIO;}
 
	public void setP_ID_USUARIO(BigDecimal P_ID_USUARIO){
		this.P_ID_USUARIO=P_ID_USUARIO;}
 
	public BigDecimal getP_ID_CONTRATO(){
		return P_ID_CONTRATO;}
 
	public void setP_ID_CONTRATO(BigDecimal P_ID_CONTRATO){
		this.P_ID_CONTRATO=P_ID_CONTRATO;}
 
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
 
	public Object getC_CONTRATOS(){
		return C_CONTRATOS;}
 
	public void setC_CONTRATOS(Object C_CONTRATOS){
		this.C_CONTRATOS=C_CONTRATOS;}
 
}