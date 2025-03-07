package trafico.beans;

import java.math.BigDecimal;

/**
 * Bean para resultado consulta estadísticas CTIT
 * @author alfredo.delallave
 *
 */
public class DetalleTramitesCTITBean {

	private BigDecimal numColegiado;

	private BigDecimal numTransferencias;

	private String estadoTransferencia;

	public BigDecimal getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(BigDecimal numColegiado) {
		this.numColegiado = numColegiado;
	}

	public BigDecimal getNumTransferencias() {
		return numTransferencias;
	}

	public void setNumTransferencias(BigDecimal numTransferencias) {
		this.numTransferencias = numTransferencias;
	}

	public String getEstadoTransferencia() {
		return estadoTransferencia;
	}

	public void setEstadoTransferencia(String estadoTransferencia) {
		this.estadoTransferencia = estadoTransferencia;
	}

}