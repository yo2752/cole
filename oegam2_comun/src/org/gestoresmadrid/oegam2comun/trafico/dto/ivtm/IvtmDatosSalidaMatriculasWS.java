package org.gestoresmadrid.oegam2comun.trafico.dto.ivtm;
//TODO MPC. Cambio IVTM. Clase añadida.

public class IvtmDatosSalidaMatriculasWS implements java.io.Serializable {

	private static final long serialVersionUID = 9200303810359080060L;
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