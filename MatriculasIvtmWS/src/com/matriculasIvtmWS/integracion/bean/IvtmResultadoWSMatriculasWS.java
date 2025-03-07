package com.matriculasIvtmWS.integracion.bean;
//TODO MPC. Cambio IVTM. Clase añadida.
public class IvtmResultadoWSMatriculasWS implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1743305250154081822L;
	protected java.lang.String numAutoliquidacion;//número de autoliquidación proporcionado por el ayuntamiento de madrid
	protected java.lang.String matricula;//campo de matrícula de vehíuclo correspondiente a ese número de autoliquidación
	protected java.lang.String bastidor;//campo de bastidor que corresponde a ese número de autoliquidación
	protected java.lang.String descripcion;//campo que indica si ha habido algún problema en la obtención de dicha matricula
	
	public IvtmResultadoWSMatriculasWS() {
		// TODO Auto-generated constructor stub
	}
	
	public IvtmResultadoWSMatriculasWS(String numAutoliquidacion, String matricula, String bastidor, String descripcion) {
		this.bastidor = bastidor;
		this.descripcion = descripcion;
		this.matricula = matricula;
		this.numAutoliquidacion = numAutoliquidacion;
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
