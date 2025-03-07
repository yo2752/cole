package trafico.servicio.implementacion;

import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;

import trafico.beans.utiles.ParametrosPegatinaMatriculacion;
import trafico.utiles.enumerados.TipoTramiteMatriculacion;

public class CriteriosImprimirTramiteTraficoBean {

	private TipoTramiteTrafico tipoTramite;
	private String tipoImpreso;
	private TipoTramiteMatriculacion tipoTramiteMatriculacion;
	private ParametrosPegatinaMatriculacion parametrosEtiquetasMatriculacion;
	private String tipoRepresentacion;

	public TipoTramiteTrafico getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(TipoTramiteTrafico tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public String getTipoImpreso() {
		return tipoImpreso;
	}

	public void setTipoImpreso(String tipoImpreso) {
		this.tipoImpreso = tipoImpreso;
	}

	public TipoTramiteMatriculacion getTipoTramiteMatriculacion() {
		return tipoTramiteMatriculacion;
	}

	public void setTipoTramiteMatriculacion(
			TipoTramiteMatriculacion tipoTramiteMatriculacion) {
		this.tipoTramiteMatriculacion = tipoTramiteMatriculacion;
	}

	public ParametrosPegatinaMatriculacion getParametrosEtiquetasMatriculacion() {
		return parametrosEtiquetasMatriculacion;
	}

	public void setParametrosEtiquetasMatriculacion(
			ParametrosPegatinaMatriculacion parametrosEtiquetasMatriculacion) {
		this.parametrosEtiquetasMatriculacion = parametrosEtiquetasMatriculacion;
	}

	public String getTipoRepresentacion() {
		return tipoRepresentacion;
	}

	public void setTipoRepresentacion(String tipoRepresentacion) {
		this.tipoRepresentacion = tipoRepresentacion;
	}

}