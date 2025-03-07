package org.gestoresmadrid.core.ivtmMatriculacion.model.beans;
//TODO MPC. Cambio IVTM. Clase añadida.

public class IvtmDatosSalidaMatriculasWS implements java.io.Serializable{

	protected IvtmResultadoWSMatriculasWS[] listaResultados;
	protected IvtmCodigoResultadoMatriculasWS codigoResultado;

	public IvtmDatosSalidaMatriculasWS() {
		// TODO Auto-generated constructor stub
	}

	public IvtmResultadoWSMatriculasWS[] getListaResultados() {
		return listaResultados;
	}

	public void setListaResultados(IvtmResultadoWSMatriculasWS[] listaResultados) {
		this.listaResultados = listaResultados;
	}

	public IvtmCodigoResultadoMatriculasWS getCodigoResultado() {
		return codigoResultado;
	}

	public void setCodigoResultado(IvtmCodigoResultadoMatriculasWS codigoResultado) {
		this.codigoResultado = codigoResultado;
	}

}