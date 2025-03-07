package escrituras.beans;

public class TipoPersonaPLSQL {

	private Integer pCode;
	private String pSqlErrm;
	private Object respuesta;
	private String tipoPersona;
	
	public Integer getPCode() {
		return pCode;
	}
	public void setPCode(Integer pCode) {
		this.pCode = pCode;
	}
	public String getPSqlErrm() {
		return pSqlErrm;
	}
	public void setPSqlErrm(String pSqlErrm) {
		this.pSqlErrm = pSqlErrm;
	}
	public Object getRespuesta() {
		return respuesta;
	}
	public void setRespuesta(Object respuesta) {
		this.respuesta = respuesta;
	}
	
	public String getTipoPersona() {
		return tipoPersona;
	}
	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}
	public String imprimir() {
		String buffer="";
		buffer = "pCode: " +pCode+
		",pSqlErrm: "+ pSqlErrm;
		return buffer.toString();
	}
}
