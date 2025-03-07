package trafico.beans.daos.pq_tramite_trafico;
 
import general.beans.RespuestaGenerica;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import trafico.beans.daos.BeanPQGeneral;
import utilidades.constantes.ValoresCatalog;

public class BeanPQBUSCAR extends BeanPQGeneral{
 
 
public static final String PROCEDURE="BUSCAR";
 
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
 
	private BigDecimal P_NUM_EXPEDIENTE;
 
	private String P_TIPO_TRAMITE;
 
	private String P_NUM_COLEGIADO;
 
	private String P_NIF;
 
	private String P_MATRICULA;
 
	private String P_BASTIDOR;
 
	private String P_CODIGO_TASA;
 
	private BigDecimal P_ESTADO;
 
	private String P_REF_PROPIA;
 
	private Timestamp P_FECHA_ALTA_DESDE;
 
	private Timestamp P_FECHA_ALTA_HASTA;
 
	private Timestamp P_FECHA_PRESENTACION_DESDE;
 
	private Timestamp P_FECHA_PRESENTACION_HASTA;
 
	private Timestamp P_FECHA_ULT_MODIF_DESDE;
 
	private Timestamp P_FECHA_ULT_MODIF_HASTA;
 
	private Timestamp P_FECHA_IMPRESION_DESDE;
 
	private Timestamp P_FECHA_IMPRESION_HASTA;
 
	private String P_NIF_FACTURACION;
 
	private BigDecimal PAGINA;
 
	private BigDecimal NUM_REG;
 
	private String COLUMNA_ORDEN;
 
	private String ORDEN;
 
	private BigDecimal CUENTA;
 
	private Object C_TRAMITES;
 
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
 
	public BigDecimal getP_NUM_EXPEDIENTE(){
		return P_NUM_EXPEDIENTE;}
 
	public void setP_NUM_EXPEDIENTE(BigDecimal P_NUM_EXPEDIENTE){
		this.P_NUM_EXPEDIENTE=P_NUM_EXPEDIENTE;}
 
	public String getP_TIPO_TRAMITE(){
		return P_TIPO_TRAMITE;}
 
	public void setP_TIPO_TRAMITE(String P_TIPO_TRAMITE){
		this.P_TIPO_TRAMITE=P_TIPO_TRAMITE;}
 
	public String getP_NUM_COLEGIADO(){
		return P_NUM_COLEGIADO;}
 
	public void setP_NUM_COLEGIADO(String P_NUM_COLEGIADO){
		this.P_NUM_COLEGIADO=P_NUM_COLEGIADO;}
 
	public String getP_NIF(){
		return P_NIF;}
 
	public void setP_NIF(String P_NIF){
		this.P_NIF=P_NIF;}
 
	public String getP_MATRICULA(){
		return P_MATRICULA;}
 
	public void setP_MATRICULA(String P_MATRICULA){
		this.P_MATRICULA=P_MATRICULA;}
 
	public String getP_BASTIDOR(){
		return P_BASTIDOR;}
 
	public void setP_BASTIDOR(String P_BASTIDOR){
		this.P_BASTIDOR=P_BASTIDOR;}
 
	public String getP_CODIGO_TASA(){
		return P_CODIGO_TASA;}
 
	public void setP_CODIGO_TASA(String P_CODIGO_TASA){
		this.P_CODIGO_TASA=P_CODIGO_TASA;}
 
	public BigDecimal getP_ESTADO(){
		return P_ESTADO;}
 
	public void setP_ESTADO(BigDecimal P_ESTADO){
		this.P_ESTADO=P_ESTADO;}
 
	public String getP_REF_PROPIA(){
		return P_REF_PROPIA;}
 
	public void setP_REF_PROPIA(String P_REF_PROPIA){
		this.P_REF_PROPIA=P_REF_PROPIA;}
 
	public Timestamp getP_FECHA_ALTA_DESDE(){
		return P_FECHA_ALTA_DESDE;}
 
	public void setP_FECHA_ALTA_DESDE(Timestamp P_FECHA_ALTA_DESDE){
		this.P_FECHA_ALTA_DESDE=P_FECHA_ALTA_DESDE;}
 
	public Timestamp getP_FECHA_ALTA_HASTA(){
		return P_FECHA_ALTA_HASTA;}
 
	public void setP_FECHA_ALTA_HASTA(Timestamp P_FECHA_ALTA_HASTA){
		this.P_FECHA_ALTA_HASTA=P_FECHA_ALTA_HASTA;}
 
	public Timestamp getP_FECHA_PRESENTACION_DESDE(){
		return P_FECHA_PRESENTACION_DESDE;}
 
	public void setP_FECHA_PRESENTACION_DESDE(Timestamp P_FECHA_PRESENTACION_DESDE){
		this.P_FECHA_PRESENTACION_DESDE=P_FECHA_PRESENTACION_DESDE;}
 
	public Timestamp getP_FECHA_PRESENTACION_HASTA(){
		return P_FECHA_PRESENTACION_HASTA;}
 
	public void setP_FECHA_PRESENTACION_HASTA(Timestamp P_FECHA_PRESENTACION_HASTA){
		this.P_FECHA_PRESENTACION_HASTA=P_FECHA_PRESENTACION_HASTA;}
 
	public Timestamp getP_FECHA_ULT_MODIF_DESDE(){
		return P_FECHA_ULT_MODIF_DESDE;}
 
	public void setP_FECHA_ULT_MODIF_DESDE(Timestamp P_FECHA_ULT_MODIF_DESDE){
		this.P_FECHA_ULT_MODIF_DESDE=P_FECHA_ULT_MODIF_DESDE;}
 
	public Timestamp getP_FECHA_ULT_MODIF_HASTA(){
		return P_FECHA_ULT_MODIF_HASTA;}
 
	public void setP_FECHA_ULT_MODIF_HASTA(Timestamp P_FECHA_ULT_MODIF_HASTA){
		this.P_FECHA_ULT_MODIF_HASTA=P_FECHA_ULT_MODIF_HASTA;}
 
	public Timestamp getP_FECHA_IMPRESION_DESDE(){
		return P_FECHA_IMPRESION_DESDE;}
 
	public void setP_FECHA_IMPRESION_DESDE(Timestamp P_FECHA_IMPRESION_DESDE){
		this.P_FECHA_IMPRESION_DESDE=P_FECHA_IMPRESION_DESDE;}
 
	public Timestamp getP_FECHA_IMPRESION_HASTA(){
		return P_FECHA_IMPRESION_HASTA;}
 
	public void setP_FECHA_IMPRESION_HASTA(Timestamp P_FECHA_IMPRESION_HASTA){
		this.P_FECHA_IMPRESION_HASTA=P_FECHA_IMPRESION_HASTA;}
 
	public String getP_NIF_FACTURACION(){
		return P_NIF_FACTURACION;}
 
	public void setP_NIF_FACTURACION(String P_NIF_FACTURACION){
		this.P_NIF_FACTURACION=P_NIF_FACTURACION;}
 
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
 
	public Object getC_TRAMITES(){
		return C_TRAMITES;}
 
	public void setC_TRAMITES(Object C_TRAMITES){
		this.C_TRAMITES=C_TRAMITES;}
 
}