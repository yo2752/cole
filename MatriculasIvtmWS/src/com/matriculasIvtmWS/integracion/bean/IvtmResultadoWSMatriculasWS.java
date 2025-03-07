package com.matriculasIvtmWS.integracion.bean;
//TODO MPC. Cambio IVTM. Clase a�adida.
public class IvtmResultadoWSMatriculasWS implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1743305250154081822L;
	protected java.lang.String numAutoliquidacion;//n�mero de autoliquidaci�n proporcionado por el ayuntamiento de madrid
	protected java.lang.String matricula;//campo de matr�cula de veh�uclo correspondiente a ese n�mero de autoliquidaci�n
	protected java.lang.String bastidor;//campo de bastidor que corresponde a ese n�mero de autoliquidaci�n
	protected java.lang.String descripcion;//campo que indica si ha habido alg�n problema en la obtenci�n de dicha matricula
	
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
