package procesos.daos;

import general.beans.RespuestaGenerica;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import trafico.beans.daos.BeanPQGeneral;
import utilidades.constantes.ValoresCatalog;

public class BeanPQBUSCAR_ERROR extends BeanPQGeneral{


	public static final String PROCEDURE="BUSCAR_ERROR_2";

	public List<Object> execute(Class<?> claseCursor){
		RespuestaGenerica respuestaGenerica = ejecutarProc(this,SCHEMA,ValoresCatalog.PQ_PROCESO,PROCEDURE,claseCursor,true);
		return respuestaGenerica.getListaCursor();
	}
	
	public void execute(){
		ejecutarProc(this,SCHEMA,ValoresCatalog.PQ_PROCESO,PROCEDURE,null,true);
	}
	
	private BigDecimal PAGINA;

	private BigDecimal NUM_REG;

	private String COLUMNA_ORDEN;

	private String ORDEN;

	private BigDecimal CUENTA;

	private BigDecimal ESTADO;

	private String TIPO_TRAMITE;

	private BigDecimal ID_TRAMITE;

	private String NUM_COLEGIADO;

	private Timestamp FECHA_ENTRADA_DESDE;

	private Timestamp FECHA_ENTRADA_HASTA;
	
	private Object C_COLAS;

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

	public Object getC_COLAS(){
		return C_COLAS;}

	public void setC_COLAS(Object C_COLAS){
		this.C_COLAS=C_COLAS;}
	public BigDecimal getESTADO() {
		return ESTADO;
	}
	public void setESTADO(BigDecimal eSTADO) {
		ESTADO = eSTADO;
	}
	public String getTIPO_TRAMITE() {
		return TIPO_TRAMITE;
	}
	public void setTIPO_TRAMITE(String tIPOTRAMITE) {
		TIPO_TRAMITE = tIPOTRAMITE;
	}
	public BigDecimal getID_TRAMITE() {
		return ID_TRAMITE;
	}
	public void setID_TRAMITE(BigDecimal iDTRAMITE) {
		ID_TRAMITE = iDTRAMITE;
	}
	public String getNUM_COLEGIADO() {
		return NUM_COLEGIADO;
	}
	public void setNUM_COLEGIADO(String nUMCOLEGIADO) {
		NUM_COLEGIADO = nUMCOLEGIADO;
	}
	public Timestamp getFECHA_ENTRADA_DESDE() {
		return FECHA_ENTRADA_DESDE;
	}
	public void setFECHA_ENTRADA_DESDE(Timestamp fECHAENTRADADESDE) {
		FECHA_ENTRADA_DESDE = fECHAENTRADADESDE;
	}
	public Timestamp getFECHA_ENTRADA_HASTA() {
		return FECHA_ENTRADA_HASTA;
	}
	public void setFECHA_ENTRADA_HASTA(Timestamp fECHAENTRADAHASTA) {
		FECHA_ENTRADA_HASTA = fECHAENTRADAHASTA;
	}
	public static String getProcedure() {
		return PROCEDURE;
	}
	
}