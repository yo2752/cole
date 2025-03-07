package trafico.acciones;

import general.accionTramite.beans.paginacion.BeanCriteriosAccionTramite;
import general.accionTramite.factoria.FactoriaAccionTramite;
import general.acciones.AbstractFactoryPaginatedList;
import general.acciones.PaginatedListActionSkeleton;

import org.apache.struts2.interceptor.SessionAware;
import org.displaytag.pagination.PaginatedList;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

/**
 * Clase que se encargará de controlar la navegación y consulta de las acciones de un trámite.
 * @author juan.gomez
 *
 */
public class AccionesAction extends PaginatedListActionSkeleton implements SessionAware {

	private static final long serialVersionUID = 1L;
	// log de errores	
	private static final ILoggerOegam log = LoggerOegam.getLogger(AccionesAction.class);

	private static final String COLUMDEFECT = "id.fechaInicio";

	private String numExpediente; // Número de expediente para realizar el control de acciones sobre este.

	// Para utilizar en la consulta de trámites, ya que el nombre de la tabla de resultados es distinto.
	// Aunque parece que nadie lo llama, no se puede borrar, ya que se llama desde jsp.
	private PaginatedList listaAcciones;
	public PaginatedList getListaAcciones() {
		return lista;
	}

	// GETTER & SETTER

	public String getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(String numExpediente) {
		this.numExpediente = numExpediente;
	}

	@Override
	public AbstractFactoryPaginatedList getFactory() {
		return new FactoriaAccionTramite();
	}

	@Override
	public String getColumnaPorDefecto() {
		return COLUMDEFECT;
	}

	@Override
	public String getResultadoPorDefecto() {
		return SUCCESS;
	}

	@Override
	public String getCriterioPaginatedList() {
		return "accionesActionBean";
	}

	@Override
	public String getCriteriosSession() {
		return "accionesActionSession";
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargaRestricciones() {
		if (getNumExpediente() != null && !getNumExpediente().equals("")) {
			((BeanCriteriosAccionTramite)beanCriterios).setNumExpediente(numExpediente);
		}
	}

	@Override
	public String getOrdenPorDefecto() {
		return ORDEN_DESC;
	}

	@Override
	public String getResultadosPorPaginaSession() {
		return "resultadosPorPaginaAcciones";
	}

}