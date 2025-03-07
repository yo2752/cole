package trafico.beans;

import utilidades.estructuras.FechaFraccionada;

public class ConsultaTramiteMatriculacionBean {
	private Integer estado;
	private String nif;
	private String refPropia;
	private String numExpediente;
	private String bastidor;
	private String matricula;
	private FechaFraccionada fechaAlta;
	private FechaFraccionada fechaPresentacion;

	public ConsultaTramiteMatriculacionBean() {

	}

	public ConsultaTramiteMatriculacionBean(boolean inicio) {
		fechaAlta = new FechaFraccionada();
		fechaPresentacion = new FechaFraccionada();
	}

	public FechaFraccionada getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(FechaFraccionada fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public FechaFraccionada getFechaPresentacion() {
		return fechaPresentacion;
	}

	public void setFechaPresentacion(FechaFraccionada fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getRefPropia() {
		return refPropia;
	}

	public void setRefPropia(String refPropia) {
		this.refPropia = refPropia;
	}

	public String getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(String numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getBastidor() {
		return bastidor;
	}

	public void setBastidor(String bastidor) {
		this.bastidor = bastidor;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

}