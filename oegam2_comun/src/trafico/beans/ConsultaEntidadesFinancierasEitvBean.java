package trafico.beans;

import java.math.BigDecimal;

public class ConsultaEntidadesFinancierasEitvBean {

	private long numExpediente;
	private String bastidor;
	private BigDecimal estado;
	private String nive;
	private String numColegiado;
	private String respuesta;

	public long getNumExpediente() {
		return numExpediente;
	}
	public void setNumExpediente(long numExpediente) {
		this.numExpediente = numExpediente;
	}
	public String getBastidor() {
		return bastidor;
	}
	public void setBastidor(String bastidor) {
		this.bastidor = bastidor;
	}
	public BigDecimal getEstado() {
		return estado;
	}
	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}
	public String getNive() {
		return nive;
	}
	public void setNive(String nive) {
		this.nive = nive;
	}
	public String getNumColegiado() {
		return numColegiado;
	}
	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}
	public String getRespuesta() {
		return respuesta;
	}
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

}