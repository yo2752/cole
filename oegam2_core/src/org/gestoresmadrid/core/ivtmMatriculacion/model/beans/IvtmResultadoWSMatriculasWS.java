package org.gestoresmadrid.core.ivtmMatriculacion.model.beans;
//TODO MPC. Cambio IVTM. Clase añadida.
public class IvtmResultadoWSMatriculasWS implements java.io.Serializable{

	private static final long serialVersionUID = -8180211510988340228L;
	protected java.lang.String numAutoliquidacion; // Número de autoliquidación proporcionado por el ayuntamiento de Madrid
	protected java.lang.String matricula; // Campo de matrícula de vehíuclo correspondiente a ese número de autoliquidación
	protected java.lang.String bastidor; // Campo de bastidor que corresponde a ese número de autoliquidación
	protected java.lang.String descripcion; // Campo que indica si ha habido algún problema en la obtención de dicha matrícula

	public IvtmResultadoWSMatriculasWS() {
		// TODO Auto-generated constructor stub
	}

	public java.lang.String getNumAutoliquidacion() {
		return numAutoliquidacion;
	}

	public void setNumAutoliquidacion(java.lang.String numAutoliquidacion) {
		this.numAutoliquidacion = numAutoliquidacion;
	}

	public java.lang.String getMatricula() {
		return matricula;
	}

	public java.lang.String getBastidor() {
		return bastidor;
	}

	public void setBastidor(java.lang.String bastidor) {
		this.bastidor = bastidor;
	}

	public void setMatricula(java.lang.String matricula) {
		this.matricula = matricula;
	}

	public java.lang.String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(java.lang.String descripcion) {
		this.descripcion = descripcion;
	}

}