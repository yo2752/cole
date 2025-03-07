package escrituras.beans.contratos;

import utilidades.estructuras.FechaFraccionada;

public class ConsultaContratoBean {

	private String cif;
	private Integer estado;
	private String razonSocial;
	private String numColegiado;
	private String alias;
	private FechaFraccionada fechaActualizacion;
	
	public ConsultaContratoBean() {

	}

	public String getCif() {
		return cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public FechaFraccionada getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(FechaFraccionada fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}
	
	
	
}
