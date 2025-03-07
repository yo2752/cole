package trafico.beans;

import java.util.Map;

import escrituras.beans.ResultBean;

public class RespuestaAtemBean {

	private Boolean 				error;
	private Boolean 				referencia;
	private String 					respuestaCompleta;
	private Map<Long, ResultBean>	resultadosUnitarios;
	private String					referenciaAtem;
	
	public Boolean getError() {
		return error;
	}
	public void setError(Boolean error) {
		this.error = error;
	}
	public Boolean getReferencia() {
		return referencia;
	}
	public void setReferencia(Boolean referencia) {
		this.referencia = referencia;
	}
	public String getRespuestaCompleta() {
		return respuestaCompleta;
	}
	public void setRespuestaCompleta(String respuestaCompleta) {
		this.respuestaCompleta = respuestaCompleta;
	}
	public String getReferenciaAtem() {
		return referenciaAtem;
	}
	public void setReferenciaAtem(String referenciaAtem) {
		this.referenciaAtem = referenciaAtem;
	}
	public Map<Long, ResultBean> getResultadosUnitarios() {
		return resultadosUnitarios;
	}
	public void setResultadosUnitarios(Map<Long, ResultBean> resultadosUnitarios) {
		this.resultadosUnitarios = resultadosUnitarios;
	}
	
}
