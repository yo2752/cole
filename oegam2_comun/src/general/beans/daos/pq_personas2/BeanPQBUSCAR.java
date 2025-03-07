package general.beans.daos.pq_personas2;
 
import general.beans.RespuestaGenerica;
import general.modelo.ModeloGenerico;

import java.math.BigDecimal;
import java.util.List;

import trafico.beans.daos.BeanPQGeneral;
import utilidades.constantes.ValoresCatalog;
 
public class BeanPQBUSCAR extends BeanPQGeneral{
 
 
public static final String PROCEDURE="BUSCAR";
 
public List<Object> execute(Class<?> claseCursor){
RespuestaGenerica respuestaGenerica = new ModeloGenerico().ejecutarProc(this,SCHEMA,ValoresCatalog.PQ_PERSONAS,PROCEDURE,claseCursor,true);
return respuestaGenerica.getListaCursor();
}
public void execute(){
	new ModeloGenerico().ejecutarProc(this,SCHEMA,ValoresCatalog.PQ_PERSONAS,PROCEDURE,null,true);
}
	private BigDecimal P_ID_USUARIO;
 
	private BigDecimal P_ID_CONTRATO_SESSION;
 
	private String P_NUM_COLEGIADO;
 
	private String P_NIF;
 
	private String P_APELLIDO1_RAZON_SOCIAL;
 
	private String P_APELLIDO2;
 
	private BigDecimal P_ESTADO;
 
	private String P_NOMBRE;
 
	private String P_TIPO_PERSONA;
 
	private String P_ESTADO_CIVIL;
 
	private BigDecimal PAGINA;
 
	private BigDecimal NUM_REG;
 
	private String COLUMNA_ORDEN;
 
	private String ORDEN;
 
	private BigDecimal CUENTA;
 
	private Object C_PERSONAS;
 
	public BigDecimal getP_ID_USUARIO(){
		return P_ID_USUARIO;}
 
	public void setP_ID_USUARIO(BigDecimal P_ID_USUARIO){
		this.P_ID_USUARIO=P_ID_USUARIO;}
 
	public BigDecimal getP_ID_CONTRATO_SESSION(){
		return P_ID_CONTRATO_SESSION;}
 
	public void setP_ID_CONTRATO_SESSION(BigDecimal P_ID_CONTRATO_SESSION){
		this.P_ID_CONTRATO_SESSION=P_ID_CONTRATO_SESSION;}
 
	public String getP_NUM_COLEGIADO(){
		return P_NUM_COLEGIADO;}
 
	public void setP_NUM_COLEGIADO(String P_NUM_COLEGIADO){
		this.P_NUM_COLEGIADO=P_NUM_COLEGIADO;}
 
	public String getP_NIF(){
		return P_NIF;}
 
	public void setP_NIF(String P_NIF){
		this.P_NIF=P_NIF;}
 
	public String getP_APELLIDO1_RAZON_SOCIAL(){
		return P_APELLIDO1_RAZON_SOCIAL;}
 
	public void setP_APELLIDO1_RAZON_SOCIAL(String P_APELLIDO1_RAZON_SOCIAL){
		this.P_APELLIDO1_RAZON_SOCIAL=P_APELLIDO1_RAZON_SOCIAL;}
 
	public String getP_APELLIDO2(){
		return P_APELLIDO2;}
 
	public void setP_APELLIDO2(String P_APELLIDO2){
		this.P_APELLIDO2=P_APELLIDO2;}
 
	public BigDecimal getP_ESTADO(){
		return P_ESTADO;}
 
	public void setP_ESTADO(BigDecimal P_ESTADO){
		this.P_ESTADO=P_ESTADO;}
 
	public String getP_NOMBRE(){
		return P_NOMBRE;}
 
	public void setP_NOMBRE(String P_NOMBRE){
		this.P_NOMBRE=P_NOMBRE;}
 
	public String getP_TIPO_PERSONA(){
		return P_TIPO_PERSONA;}
 
	public void setP_TIPO_PERSONA(String P_TIPO_PERSONA){
		this.P_TIPO_PERSONA=P_TIPO_PERSONA;}
 
	public String getP_ESTADO_CIVIL(){
		return P_ESTADO_CIVIL;}
 
	public void setP_ESTADO_CIVIL(String P_ESTADO_CIVIL){
		this.P_ESTADO_CIVIL=P_ESTADO_CIVIL;}
 
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
 
	public Object getC_PERSONAS(){
		return C_PERSONAS;}
 
	public void setC_PERSONAS(Object C_PERSONAS){
		this.C_PERSONAS=C_PERSONAS;}
 
}