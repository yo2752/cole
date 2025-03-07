package administracion.beans;

public class CertificateBean {
	
	private String alias;
	private String validoDesde;
	private String validoHasta;
	private long diasValidezRestantes;
	private String info;
	
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getValidoDesde() {
		return validoDesde;
	}
	public void setValidoDesde(String validoDesde) {
		this.validoDesde = validoDesde;
	}
	public String getValidoHasta() {
		return validoHasta;
	}
	public void setValidoHasta(String validoHasta) {
		this.validoHasta = validoHasta;
	}
	public long getDiasValidezRestantes() {
		return diasValidezRestantes;
	}
	public void setDiasValidezRestantes(long diasValidezRestantes) {
		this.diasValidezRestantes = diasValidezRestantes;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	
}
