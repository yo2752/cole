package com.matriculasIvtmWS.integracion.bean;
//TODO MPC. Cambio IVTM. Clase añadida.

public class IvtmDatosSalidaMatriculasWS implements java.io.Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4502165919619437260L;
	protected org.gestoresmadrid.core.ivtmMatriculacion.model.beans.IvtmResultadoWSMatriculasWS[] listaResultados;
	protected IvtmCodigoResultadoMatriculasWS codigoResultado;
	
	public IvtmDatosSalidaMatriculasWS() {
		// TODO Auto-generated constructor stub
	}

	public org.gestoresmadrid.core.ivtmMatriculacion.model.beans.IvtmResultadoWSMatriculasWS[] getListaResultados() {
		return listaResultados;
	}

	public void setListaResultados(org.gestoresmadrid.core.ivtmMatriculacion.model.beans.IvtmResultadoWSMatriculasWS[] listaResultados2) {
		this.listaResultados = listaResultados2;
	}

	public IvtmCodigoResultadoMatriculasWS getCodigoResultado() {
		return codigoResultado;
	}

	public void setCodigoResultado(IvtmCodigoResultadoMatriculasWS codigoResultado) {
		this.codigoResultado = codigoResultado;
	}
	
	 
	
}
