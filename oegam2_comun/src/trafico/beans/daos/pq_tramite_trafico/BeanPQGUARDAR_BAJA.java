package trafico.beans.daos.pq_tramite_trafico;
 
import general.beans.RespuestaGenerica;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import trafico.beans.daos.BeanPQGeneral;
import utilidades.constantes.ValoresCatalog;

public class BeanPQGUARDAR_BAJA extends BeanPQGeneral{
 
 
public static final String PROCEDURE="GUARDAR_BAJA";
 
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
 
	private BigDecimal P_ID_VEHICULO;
 
	private String P_TIPO_TASA;
 
	private String P_CODIGO_TASA;
 
	private BigDecimal P_ESTADO;
 
	private String P_REF_PROPIA;
 
	private Timestamp P_FECHA_ALTA;
 
	private Timestamp P_FECHA_PRESENTACION;
 
	private Timestamp P_FECHA_ULT_MODIF;
 
	private Timestamp P_FECHA_IMPRESION;
 
	private String P_JEFATURA_PROVINCIAL;
 
	private String P_ANOTACIONES;
 
	private String P_CEMA;
 
	private String P_MOTIVO_BAJA;
 
	private String P_PERMISO_CIRCU;
 
	private String P_TARJETA_INSPECCION;
 
	private String P_BAJA_IMP_MUNICIPAL;
 
	private String P_JUSTIFICANTE_DENUNCIA;
 
	private String P_PROPIEDAD_DESGUACE;
 
	private String P_PAGO_IMP_MUNICIPAL;
 
	private String P_IMPR_PERMISO_CIRCU;
 
	private String P_TASA_DUPLICADO;
	
	private String P_DECLARACION_RESIDUO;
	
	private String P_DNI_COTITULARES;
 
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
 
	public BigDecimal getP_ID_VEHICULO(){
		return P_ID_VEHICULO;}
 
	public void setP_ID_VEHICULO(BigDecimal P_ID_VEHICULO){
		this.P_ID_VEHICULO=P_ID_VEHICULO;}
 
	public String getP_TIPO_TASA(){
		return P_TIPO_TASA;}
 
	public void setP_TIPO_TASA(String P_TIPO_TASA){
		this.P_TIPO_TASA=P_TIPO_TASA;}
 
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
 
	public String getP_JEFATURA_PROVINCIAL(){
		return P_JEFATURA_PROVINCIAL;}
 
	public void setP_JEFATURA_PROVINCIAL(String P_JEFATURA_PROVINCIAL){
		this.P_JEFATURA_PROVINCIAL=P_JEFATURA_PROVINCIAL;}
 
	public String getP_ANOTACIONES(){
		return P_ANOTACIONES;}
 
	public void setP_ANOTACIONES(String P_ANOTACIONES){
		this.P_ANOTACIONES=P_ANOTACIONES;}
 
	public String getP_CEMA(){
		return P_CEMA;}
 
	public void setP_CEMA(String P_CEMA){
		this.P_CEMA=P_CEMA;}
 
	public String getP_MOTIVO_BAJA(){
		return P_MOTIVO_BAJA;}
 
	public void setP_MOTIVO_BAJA(String P_MOTIVO_BAJA){
		this.P_MOTIVO_BAJA=P_MOTIVO_BAJA;}
 
	public String getP_PERMISO_CIRCU(){
		return P_PERMISO_CIRCU;}
 
	public void setP_PERMISO_CIRCU(String P_PERMISO_CIRCU){
		this.P_PERMISO_CIRCU=P_PERMISO_CIRCU;}
 
	public String getP_TARJETA_INSPECCION(){
		return P_TARJETA_INSPECCION;}
 
	public void setP_TARJETA_INSPECCION(String P_TARJETA_INSPECCION){
		this.P_TARJETA_INSPECCION=P_TARJETA_INSPECCION;}
 
	public String getP_BAJA_IMP_MUNICIPAL(){
		return P_BAJA_IMP_MUNICIPAL;}
 
	public void setP_BAJA_IMP_MUNICIPAL(String P_BAJA_IMP_MUNICIPAL){
		this.P_BAJA_IMP_MUNICIPAL=P_BAJA_IMP_MUNICIPAL;}
 
	public String getP_JUSTIFICANTE_DENUNCIA(){
		return P_JUSTIFICANTE_DENUNCIA;}
 
	public void setP_JUSTIFICANTE_DENUNCIA(String P_JUSTIFICANTE_DENUNCIA){
		this.P_JUSTIFICANTE_DENUNCIA=P_JUSTIFICANTE_DENUNCIA;}
 
	public String getP_PROPIEDAD_DESGUACE(){
		return P_PROPIEDAD_DESGUACE;}
 
	public void setP_PROPIEDAD_DESGUACE(String P_PROPIEDAD_DESGUACE){
		this.P_PROPIEDAD_DESGUACE=P_PROPIEDAD_DESGUACE;}
 
	public String getP_PAGO_IMP_MUNICIPAL(){
		return P_PAGO_IMP_MUNICIPAL;}
 
	public void setP_PAGO_IMP_MUNICIPAL(String P_PAGO_IMP_MUNICIPAL){
		this.P_PAGO_IMP_MUNICIPAL=P_PAGO_IMP_MUNICIPAL;}
 
	public String getP_IMPR_PERMISO_CIRCU(){
		return P_IMPR_PERMISO_CIRCU;}
 
	public void setP_IMPR_PERMISO_CIRCU(String P_IMPR_PERMISO_CIRCU){
		this.P_IMPR_PERMISO_CIRCU=P_IMPR_PERMISO_CIRCU;}
	
		
	public String getP_DECLARACION_RESIDUO(){
		return P_DECLARACION_RESIDUO;}
 
	public void setP_DECLARACION_RESIDUO(String P_DECLARACION_RESIDUO){
		this.P_DECLARACION_RESIDUO=P_DECLARACION_RESIDUO;}
	
	
	
	public String getP_DNI_COTITULARES(){
		return P_DNI_COTITULARES;}
 
	public void setP_DNI_COTITULARES(String P_DNICOTITULARES){
		this.P_DNI_COTITULARES=P_DNICOTITULARES;}
 
	
 
	public String getP_TASA_DUPLICADO(){
		return P_TASA_DUPLICADO;}
 
	public void setP_TASA_DUPLICADO(String P_TASA_DUPLICADO){
		this.P_TASA_DUPLICADO=P_TASA_DUPLICADO;}
 
}