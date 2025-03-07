package trafico.beans;

import java.io.Serializable;
import java.math.BigDecimal;

public class VehiculoTramiteTraficoBean implements Serializable {

	private static final long serialVersionUID = 2280912927081090499L;

	private BigDecimal idVehiculo;
	private String numColegiado;
	private BigDecimal numExpediente;
	private BigDecimal kilometros;

	public BigDecimal getIdVehiculo() {
		return idVehiculo;
	}

	public void setIdVehiculo(BigDecimal idVehiculo) {
		this.idVehiculo = idVehiculo;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public BigDecimal getKilometros() {
		return kilometros;
	}

	public void setKilometros(BigDecimal kilometros) {
		this.kilometros = kilometros;
	}

}