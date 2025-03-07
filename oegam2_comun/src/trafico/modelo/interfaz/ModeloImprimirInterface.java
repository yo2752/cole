package trafico.modelo.interfaz;

import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;

import utilidades.web.OegamExcepcion;
import escrituras.beans.ResultBean;

public interface ModeloImprimirInterface {

	public ResultBean crearSolicitud(String nombreFichero, String tipoTramiteTrafico, String idTramite)
			throws OegamExcepcion;

	public TipoTramiteTrafico recuperarTipoTramiteSiEsElMismo(Long[] numExpedientes);

}