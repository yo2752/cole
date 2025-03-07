package trafico.evolucion;

import trafico.evolucion.beans.paginacion.CriteriosBusquedaEvolucionTramiteTrafico;
import trafico.evolucion.factoria.FactoriaEvolucionTramiteTrafico;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import general.acciones.AbstractFactoryPaginatedList;
import general.acciones.PaginatedListActionSkeleton;

public class EvolucionTramiteTraficoAction extends PaginatedListActionSkeleton {

	private static final long serialVersionUID = 5936796059602607293L;
	private static final ILoggerOegam log = LoggerOegam
			.getLogger(EvolucionTramiteTraficoAction.class);
	private static final String COLUMDEFECT = "id.fechaCambio";

	private String numExpediente;

	@Override
	public AbstractFactoryPaginatedList getFactory() {
		return new FactoriaEvolucionTramiteTrafico();
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
		return "evolucionTramiteTraficoBean";
	}

	@Override
	public String getCriteriosSession() {
		return "evolucionTramiteTraficoSession";
	}

	@Override
	public String getResultadosPorPaginaSession() {
		return "resultadosPorPaginaEvolucion";
	}

	@Override
	protected ILoggerOegam getLog() {
		return log;
	}

	@Override
	protected void cargaRestricciones() {
		if (((CriteriosBusquedaEvolucionTramiteTrafico) beanCriterios)
				.getNumExpediente() == null) {
			((CriteriosBusquedaEvolucionTramiteTrafico) beanCriterios)
					.setNumExpediente(numExpediente);
		}
	}

	public String getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(String numExpediente) {
		this.numExpediente = numExpediente;
	}

}
