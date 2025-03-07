package general.accionTramite.dto;

import java.math.BigDecimal;
import java.util.Date;

public class AccionTramiteDto {

	private long numExpediente;

	private String accion;

	private Date fechaInicio;

	private Date fechaFin;
	
	private BigDecimal idUsuario;

	private String respuesta;

	public long getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(long numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public BigDecimal getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(BigDecimal idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

}
