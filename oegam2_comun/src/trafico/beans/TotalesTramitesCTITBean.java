package trafico.beans;

import java.math.BigDecimal;

/**
 * Bean para almacenar resultado de consulta de estadísticas CTIT
 * @author alfredo.delallave
 *
 */
public class TotalesTramitesCTITBean {
	
	private BigDecimal numTransferencias;
	
	private String tipoTransferencia;
	
	public BigDecimal getNumTransferencias() {
		return numTransferencias;
	}
	public void setNumTransferencias(BigDecimal numTransferencias) {
		this.numTransferencias = numTransferencias;
	}
	public String getTipoTransferencia() {
		return tipoTransferencia;
	}
	public void setTipoTransferencia(String tipoTransferencia) {
		this.tipoTransferencia = tipoTransferencia;
	}

}
