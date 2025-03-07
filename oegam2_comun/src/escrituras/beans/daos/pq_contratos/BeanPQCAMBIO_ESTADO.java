package escrituras.beans.daos.pq_contratos;
 
import general.beans.RespuestaGenerica;

import java.math.BigDecimal;
import java.util.List;

import trafico.beans.daos.BeanPQGeneral;
import utilidades.constantes.ValoresCatalog;
 
public class BeanPQCAMBIO_ESTADO extends BeanPQGeneral{
 
 
public static final String PROCEDURE="CAMBIO_ESTADO";
 
public List<Object> execute(Class<?> claseCursor){
RespuestaGenerica respuestaGenerica = ejecutarProc(this,SCHEMA,ValoresCatalog.PQ_CONTRATOS,PROCEDURE,claseCursor,true);
return respuestaGenerica.getListaCursor();
}
public void execute(){
ejecutarProc(this,SCHEMA,ValoresCatalog.PQ_CONTRATOS,PROCEDURE,null,true);
}
	private BigDecimal P_ID_CONTRATO;
 
	private BigDecimal P_ESTADO;
	
	private BigDecimal P_USUARIO_ACT;
	
	private String P_MOTIVO;
	
	private String P_SOLICITANTE;
	
	private String P_TIPO_ACT;
 
	public BigDecimal getP_ID_CONTRATO(){
		return P_ID_CONTRATO;}
 
	public void setP_ID_CONTRATO(BigDecimal P_ID_CONTRATO){
		this.P_ID_CONTRATO=P_ID_CONTRATO;}
 
	public BigDecimal getP_ESTADO(){
		return P_ESTADO;}
 
	public void setP_ESTADO(BigDecimal P_ESTADO){
		this.P_ESTADO=P_ESTADO;}
	public BigDecimal getP_USUARIO_ACT() {
		return P_USUARIO_ACT;
	}
	public void setP_USUARIO_ACT(BigDecimal p_USUARIO_ACT) {
		P_USUARIO_ACT = p_USUARIO_ACT;
	}
	public String getP_MOTIVO() {
		return P_MOTIVO;
	}
	public void setP_MOTIVO(String p_MOTIVO) {
		P_MOTIVO = p_MOTIVO;
	}
	public String getP_SOLICITANTE() {
		return P_SOLICITANTE;
	}
	public void setP_SOLICITANTE(String p_SOLICITANTE) {
		P_SOLICITANTE = p_SOLICITANTE;
	}
	public String getP_TIPO_ACT() {
		return P_TIPO_ACT;
	}
	public void setP_TIPO_ACT(String p_TIPO_ACT) {
		P_TIPO_ACT = p_TIPO_ACT;
	}
 
}