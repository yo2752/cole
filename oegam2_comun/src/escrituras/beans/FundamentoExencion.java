package escrituras.beans;

/**
 * Bean que almacena datos de la tabla FUNDAMENTO_EXCENCION.
 *
 */
public class FundamentoExencion {
	 private String fundamentoExencion;
	 private String descFundamentoExe; 
	 
	 public FundamentoExencion() {
		
	}

	public String getFundamentoExencion() {
		return fundamentoExencion;
	}

	public void setFundamentoExencion(String fundamentoExencion) {
		this.fundamentoExencion = fundamentoExencion;
	}

	public String getDescFundamentoExe() {
		return descFundamentoExe;
	}

	public void setDescFundamentoExe(String descFundamentoExe) {
		this.descFundamentoExe = descFundamentoExe;
	}

	public String imprimir() {
		return "FundamentoExencion [descFundamentoExe=" + descFundamentoExe
				+ ", fundamentoExencion=" + fundamentoExencion + "]";
	}


	
	 
	 
}
