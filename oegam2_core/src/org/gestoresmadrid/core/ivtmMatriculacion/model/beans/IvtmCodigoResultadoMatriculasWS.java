package org.gestoresmadrid.core.ivtmMatriculacion.model.beans;
//TODO MPC. Cambio IVTM. Clase a�adida.

public class IvtmCodigoResultadoMatriculasWS implements java.io.Serializable{

	protected java.lang.String resultado;//OK o NOK
	protected java.lang.String codigo;//000, 001,002
	protected java.lang.String mensaje;//Descripci�n del error

	public IvtmCodigoResultadoMatriculasWS() {
		// TODO Auto-generated constructor stub
	}

	public IvtmCodigoResultadoMatriculasWS(java.lang.String resultado, java.lang.String codigoResultado,java.lang.String mensajeResultado) {
		this.resultado=resultado;
		this.codigo=codigoResultado;
		this.mensaje=mensajeResultado;
	}

	public java.lang.String getCodigo() {
		return codigo;
	}

	public void setCodigo(java.lang.String codigo) {
		this.codigo = codigo;
	}

	public java.lang.String getMensaje() {
		return mensaje;
	}

	public void setMensaje(java.lang.String mensaje) {
		this.mensaje = mensaje;
	}

	public java.lang.String getResultado() {
		return resultado;
	}

	public void setResultado(java.lang.String resultado) {
		this.resultado = resultado;
	}

}