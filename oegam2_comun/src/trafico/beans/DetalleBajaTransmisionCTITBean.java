package trafico.beans;

import java.math.BigDecimal;

public class DetalleBajaTransmisionCTITBean {
	private BigDecimal numColegiado;

	private BigDecimal numTransferencias;

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

}