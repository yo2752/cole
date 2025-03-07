package trafico.beans.daos.pq_tramite_trafico;
 
import general.beans.RespuestaGenerica;

import java.math.BigDecimal;
import java.util.List;

import trafico.beans.daos.BeanPQGeneral;
import utilidades.constantes.ValoresCatalog;

public class BeanPQDESCRIPCIONES extends BeanPQGeneral{
 
 
public static final String PROCEDURE="DESCRIPCIONES";
 
public List<Object> execute(Class<?> claseCursor){
RespuestaGenerica respuestaGenerica = ejecutarProc(this,SCHEMA,ValoresCatalog.PQ_TRAMITE_TRAFICO,PROCEDURE,claseCursor,true);
return respuestaGenerica.getListaCursor();
}
public void execute(){
ejecutarProc(this,SCHEMA,ValoresCatalog.PQ_TRAMITE_TRAFICO,PROCEDURE,null,true);
}
	private BigDecimal P_NUM_EXPEDIENTE;
 
	private String P_CODIGO_TASA;
 
	private String P_TIPO_TASA;
 
	private String P_JEFATURA_PROVINCIAL;
 
	private String P_JEFATURA_PROV;
 
	private String P_DESC_JEFATURA;
 
	private String P_DESC_SUCURSAL;
 
	private String P_CIF;
 
	private String P_RAZON_SOCIAL;
 
	private BigDecimal P_ID_VEHICULO;
 
	private BigDecimal P_CODIGO_MARCA;
 
	private String P_MARCA;
 
	private String P_MARCA_SIN_EDITAR;
 
	private String P_TIPO_VEHICULO;
 
	private String P_DESC_TIPO_VEHI;
 
	private String P_ID_SERVICIO;
 
	private String P_DESC_SERVICIO;
 
	private String P_ID_CRITERIO_CONSTRUCCION;
 
	private String P_DESC_CRITER_CONSTRUCCION;
 
	private String P_ID_CRITERIO_UTILIZACION;
 
	private String P_DESC_CRITER_UTILIZACION;
 
	private String P_ID_DIRECTIVA_CEE;
 
	private String P_DESC_DIRECTIVA_CEE;
 
	private String P_ID_MOTIVO_ITV;
 
	private String P_DESC_MOTIVO_ITV;
 
	private String P_ESTACION_ITV;
 
	private String P_PROV_ESTACION_ITV;
 
	private String P_MUNI_ESTACION_ITV;
 
	private String P_NIVE;
 
	private String P_OTROS_SINCODIG;
 
	private String P_CODIGO_TASA_INF;
 
	private String P_TIPO_TASA_INF;
 
	private String P_CODIGO_TASA_CAMSER;
 
	private String P_TIPO_TASA_CAMSER;
 
	public BigDecimal getP_NUM_EXPEDIENTE(){
		return P_NUM_EXPEDIENTE;}
 
	public void setP_NUM_EXPEDIENTE(BigDecimal P_NUM_EXPEDIENTE){
		this.P_NUM_EXPEDIENTE=P_NUM_EXPEDIENTE;}
 
	public String getP_CODIGO_TASA(){
		return P_CODIGO_TASA;}
 
	public void setP_CODIGO_TASA(String P_CODIGO_TASA){
		this.P_CODIGO_TASA=P_CODIGO_TASA;}
 
	public String getP_TIPO_TASA(){
		return P_TIPO_TASA;}
 
	public void setP_TIPO_TASA(String P_TIPO_TASA){
		this.P_TIPO_TASA=P_TIPO_TASA;}
 
	public String getP_JEFATURA_PROVINCIAL(){
		return P_JEFATURA_PROVINCIAL;}
 
	public void setP_JEFATURA_PROVINCIAL(String P_JEFATURA_PROVINCIAL){
		this.P_JEFATURA_PROVINCIAL=P_JEFATURA_PROVINCIAL;}
 
	public String getP_JEFATURA_PROV(){
		return P_JEFATURA_PROV;}
 
	public void setP_JEFATURA_PROV(String P_JEFATURA_PROV){
		this.P_JEFATURA_PROV=P_JEFATURA_PROV;}
 
	public String getP_DESC_JEFATURA(){
		return P_DESC_JEFATURA;}
 
	public void setP_DESC_JEFATURA(String P_DESC_JEFATURA){
		this.P_DESC_JEFATURA=P_DESC_JEFATURA;}
 
	public String getP_DESC_SUCURSAL(){
		return P_DESC_SUCURSAL;}
 
	public void setP_DESC_SUCURSAL(String P_DESC_SUCURSAL){
		this.P_DESC_SUCURSAL=P_DESC_SUCURSAL;}
 
	public String getP_CIF(){
		return P_CIF;}
 
	public void setP_CIF(String P_CIF){
		this.P_CIF=P_CIF;}
 
	public String getP_RAZON_SOCIAL(){
		return P_RAZON_SOCIAL;}
 
	public void setP_RAZON_SOCIAL(String P_RAZON_SOCIAL){
		this.P_RAZON_SOCIAL=P_RAZON_SOCIAL;}
 
	public BigDecimal getP_ID_VEHICULO(){
		return P_ID_VEHICULO;}
 
	public void setP_ID_VEHICULO(BigDecimal P_ID_VEHICULO){
		this.P_ID_VEHICULO=P_ID_VEHICULO;}
 
	public BigDecimal getP_CODIGO_MARCA(){
		return P_CODIGO_MARCA;}
 
	public void setP_CODIGO_MARCA(BigDecimal P_CODIGO_MARCA){
		this.P_CODIGO_MARCA=P_CODIGO_MARCA;}
 
	public String getP_MARCA(){
		return P_MARCA;}
 
	public void setP_MARCA(String P_MARCA){
		this.P_MARCA=P_MARCA;}
 
	public String getP_MARCA_SIN_EDITAR(){
		return P_MARCA_SIN_EDITAR;}
 
	public void setP_MARCA_SIN_EDITAR(String P_MARCA_SIN_EDITAR){
		this.P_MARCA_SIN_EDITAR=P_MARCA_SIN_EDITAR;}
 
	public String getP_TIPO_VEHICULO(){
		return P_TIPO_VEHICULO;}
 
	public void setP_TIPO_VEHICULO(String P_TIPO_VEHICULO){
		this.P_TIPO_VEHICULO=P_TIPO_VEHICULO;}
 
	public String getP_DESC_TIPO_VEHI(){
		return P_DESC_TIPO_VEHI;}
 
	public void setP_DESC_TIPO_VEHI(String P_DESC_TIPO_VEHI){
		this.P_DESC_TIPO_VEHI=P_DESC_TIPO_VEHI;}
 
	public String getP_ID_SERVICIO(){
		return P_ID_SERVICIO;}
 
	public void setP_ID_SERVICIO(String P_ID_SERVICIO){
		this.P_ID_SERVICIO=P_ID_SERVICIO;}
 
	public String getP_DESC_SERVICIO(){
		return P_DESC_SERVICIO;}
 
	public void setP_DESC_SERVICIO(String P_DESC_SERVICIO){
		this.P_DESC_SERVICIO=P_DESC_SERVICIO;}
 
	public String getP_ID_CRITERIO_CONSTRUCCION(){
		return P_ID_CRITERIO_CONSTRUCCION;}
 
	public void setP_ID_CRITERIO_CONSTRUCCION(String P_ID_CRITERIO_CONSTRUCCION){
		this.P_ID_CRITERIO_CONSTRUCCION=P_ID_CRITERIO_CONSTRUCCION;}
 
	public String getP_DESC_CRITER_CONSTRUCCION(){
		return P_DESC_CRITER_CONSTRUCCION;}
 
	public void setP_DESC_CRITER_CONSTRUCCION(String P_DESC_CRITER_CONSTRUCCION){
		this.P_DESC_CRITER_CONSTRUCCION=P_DESC_CRITER_CONSTRUCCION;}
 
	public String getP_ID_CRITERIO_UTILIZACION(){
		return P_ID_CRITERIO_UTILIZACION;}
 
	public void setP_ID_CRITERIO_UTILIZACION(String P_ID_CRITERIO_UTILIZACION){
		this.P_ID_CRITERIO_UTILIZACION=P_ID_CRITERIO_UTILIZACION;}
 
	public String getP_DESC_CRITER_UTILIZACION(){
		return P_DESC_CRITER_UTILIZACION;}
 
	public void setP_DESC_CRITER_UTILIZACION(String P_DESC_CRITER_UTILIZACION){
		this.P_DESC_CRITER_UTILIZACION=P_DESC_CRITER_UTILIZACION;}
 
	public String getP_ID_DIRECTIVA_CEE(){
		return P_ID_DIRECTIVA_CEE;}
 
	public void setP_ID_DIRECTIVA_CEE(String P_ID_DIRECTIVA_CEE){
		this.P_ID_DIRECTIVA_CEE=P_ID_DIRECTIVA_CEE;}
 
	public String getP_DESC_DIRECTIVA_CEE(){
		return P_DESC_DIRECTIVA_CEE;}
 
	public void setP_DESC_DIRECTIVA_CEE(String P_DESC_DIRECTIVA_CEE){
		this.P_DESC_DIRECTIVA_CEE=P_DESC_DIRECTIVA_CEE;}
 
	public String getP_ID_MOTIVO_ITV(){
		return P_ID_MOTIVO_ITV;}
 
	public void setP_ID_MOTIVO_ITV(String P_ID_MOTIVO_ITV){
		this.P_ID_MOTIVO_ITV=P_ID_MOTIVO_ITV;}
 
	public String getP_DESC_MOTIVO_ITV(){
		return P_DESC_MOTIVO_ITV;}
 
	public void setP_DESC_MOTIVO_ITV(String P_DESC_MOTIVO_ITV){
		this.P_DESC_MOTIVO_ITV=P_DESC_MOTIVO_ITV;}
 
	public String getP_ESTACION_ITV(){
		return P_ESTACION_ITV;}
 
	public void setP_ESTACION_ITV(String P_ESTACION_ITV){
		this.P_ESTACION_ITV=P_ESTACION_ITV;}
 
	public String getP_PROV_ESTACION_ITV(){
		return P_PROV_ESTACION_ITV;}
 
	public void setP_PROV_ESTACION_ITV(String P_PROV_ESTACION_ITV){
		this.P_PROV_ESTACION_ITV=P_PROV_ESTACION_ITV;}
 
	public String getP_MUNI_ESTACION_ITV(){
		return P_MUNI_ESTACION_ITV;}
 
	public void setP_MUNI_ESTACION_ITV(String P_MUNI_ESTACION_ITV){
		this.P_MUNI_ESTACION_ITV=P_MUNI_ESTACION_ITV;}
 
	public String getP_NIVE(){
		return P_NIVE;}
 
	public void setP_NIVE(String P_NIVE){
		this.P_NIVE=P_NIVE;}
 
	public String getP_OTROS_SINCODIG(){
		return P_OTROS_SINCODIG;}
 
	public void setP_OTROS_SINCODIG(String P_OTROS_SINCODIG){
		this.P_OTROS_SINCODIG=P_OTROS_SINCODIG;}
 
	public String getP_CODIGO_TASA_INF(){
		return P_CODIGO_TASA_INF;}
 
	public void setP_CODIGO_TASA_INF(String P_CODIGO_TASA_INF){
		this.P_CODIGO_TASA_INF=P_CODIGO_TASA_INF;}
 
	public String getP_TIPO_TASA_INF(){
		return P_TIPO_TASA_INF;}
 
	public void setP_TIPO_TASA_INF(String P_TIPO_TASA_INF){
		this.P_TIPO_TASA_INF=P_TIPO_TASA_INF;}
 
	public String getP_CODIGO_TASA_CAMSER(){
		return P_CODIGO_TASA_CAMSER;}
 
	public void setP_CODIGO_TASA_CAMSER(String P_CODIGO_TASA_CAMSER){
		this.P_CODIGO_TASA_CAMSER=P_CODIGO_TASA_CAMSER;}
 
	public String getP_TIPO_TASA_CAMSER(){
		return P_TIPO_TASA_CAMSER;}
 
	public void setP_TIPO_TASA_CAMSER(String P_TIPO_TASA_CAMSER){
		this.P_TIPO_TASA_CAMSER=P_TIPO_TASA_CAMSER;}
 
}