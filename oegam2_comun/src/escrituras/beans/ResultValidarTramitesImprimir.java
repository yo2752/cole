package escrituras.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import trafico.utiles.enumerados.TipoTramiteMatriculacion;
import trafico.utiles.enumerados.TipoTransferencia;
import trafico.utiles.enumerados.TipoTransferenciaActual;

public class ResultValidarTramitesImprimir extends ResultBean implements Serializable {

	private static final long serialVersionUID = -8380895007411035966L;

	private TipoTramiteTrafico tipoTramite;
	private TipoTramiteMatriculacion tipoTramiteMatriculacion;
	private TipoTransferenciaActual tipoTransmisionActual;
	private TipoTransferencia tipoTransmision;

	public ResultValidarTramitesImprimir() {
		init();
	}

	public void init() {
		setError(Boolean.FALSE);
		setListaMensajes(new ArrayList<String>());
	}

	public ResultValidarTramitesImprimir(boolean error, String mensajeError) {
		setError(error);
		List<String> listaMensajes = new ArrayList<String>();
		listaMensajes.add(mensajeError);
		setListaMensajes(listaMensajes);
	}

	public TipoTramiteTrafico getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(TipoTramiteTrafico tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public TipoTramiteMatriculacion getTipoTramiteMatriculacion() {
		return tipoTramiteMatriculacion;
	}

	public void setTipoTramiteMatriculacion(TipoTramiteMatriculacion tipoTramiteMatriculacion) {
		this.tipoTramiteMatriculacion = tipoTramiteMatriculacion;
	}

	public TipoTransferenciaActual getTipoTransmisionActual() {
		return tipoTransmisionActual;
	}

	public void setTipoTransmisionActual(TipoTransferenciaActual tipoTransmisionActual) {
		this.tipoTransmisionActual = tipoTransmisionActual;
	}

	public TipoTransferencia getTipoTransmision() {
		return tipoTransmision;
	}

	public void setTipoTransmision(TipoTransferencia tipoTransmision) {
		this.tipoTransmision = tipoTransmision;
	}
}
