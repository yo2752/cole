package trafico.beans;

import utilidades.estructuras.Fecha;
import utilidades.estructuras.FechaFraccionada;

public class ConsultaTasaBean {
	private Integer estado;
	private Integer tipoTramite;
	private String numExpediente;
	private String refPropia;
	private String codigoTasa;
	private FechaFraccionada fechaAlta;
	private FechaFraccionada fechaAsignacion;
	private Fecha fechaFinVigencia;

	public ConsultaTasaBean() {
		super();
	}

	public ConsultaTasaBean(boolean inicio) {
		super();
		fechaAlta = new FechaFraccionada();
		fechaAsignacion = new FechaFraccionada();
		fechaFinVigencia = new Fecha();
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public Integer getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(Integer tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public String getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(String numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getRefPropia() {
		return refPropia;
	}

	public void setRefPropia(String refPropia) {
		this.refPropia = refPropia;
	}

	public String getCodigoTasa() {
		return codigoTasa;
	}

	public void setCodigoTasa(String codigoTasa) {
		this.codigoTasa = codigoTasa;
	}

	public FechaFraccionada getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(FechaFraccionada fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public FechaFraccionada getFechaAsignacion() {
		return fechaAsignacion;
	}

	public void setFechaAsignacion(FechaFraccionada fechaAsignacion) {
		this.fechaAsignacion = fechaAsignacion;
	}

	public Fecha getFechaFinVigencia() {
		return fechaFinVigencia;
	}

	public void setFechaFinVigencia(Fecha fechaFinVigencia) {
		this.fechaFinVigencia = fechaFinVigencia;
	}

}