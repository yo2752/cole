package trafico.beans;

import java.math.BigDecimal;
import java.sql.Timestamp;

import utilidades.estructuras.Fecha;

public class AccionBean {

	private BigDecimal numExpediente;
	private BigDecimal idUsuario;
	private String nombreApellidos;
	private String accion;
	private Fecha fechaInicio;
	private Fecha fechaFin;
	private Timestamp fechaInicioT;
	private Timestamp fechaFinT;
	private String respuesta;
	private Boolean pendiente;

	public AccionBean() {
		fechaInicio = new Fecha();
		fechaFin = new Fecha();
	}

	public BigDecimal getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(BigDecimal idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombreApellidos() {
		return nombreApellidos;
	}

	public void setNombreApellidos(String nombreApellidos) {
		this.nombreApellidos = nombreApellidos;
	}

	public Timestamp getFechaInicioT() {
		return fechaInicioT;
	}

	public void setFechaInicioT(Timestamp fechaInicioT) {
		this.fechaInicioT = fechaInicioT;
	}

	public Timestamp getFechaFinT() {
		return fechaFinT;
	}

	public void setFechaFinT(Timestamp fechaFinT) {
		this.fechaFinT = fechaFinT;
	}

	public Boolean getPendiente() {
		return pendiente;
	}

	public void setPendiente(Boolean pendiente) {
		this.pendiente = pendiente;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public Fecha getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Fecha fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Fecha getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Fecha fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

}