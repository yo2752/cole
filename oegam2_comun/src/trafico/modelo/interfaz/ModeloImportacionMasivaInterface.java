package trafico.modelo.interfaz;

import utilidades.web.OegamExcepcion;
import escrituras.beans.ResultBean;

public interface ModeloImportacionMasivaInterface {

	public ResultBean crearSolicitud(String nombreFichero, String tipoTramiteTrafico, String idTramite) throws OegamExcepcion;
}