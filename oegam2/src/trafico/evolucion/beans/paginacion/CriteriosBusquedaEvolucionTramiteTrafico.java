package trafico.evolucion.beans.paginacion;

import general.beans.BeanCriteriosSkeletonPaginatedList;

public class CriteriosBusquedaEvolucionTramiteTrafico implements BeanCriteriosSkeletonPaginatedList {

	private String numExpediente;
	
	@Override
	public BeanCriteriosSkeletonPaginatedList iniciarBean() {
		return null;
	}

	public String getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(String numExpediente) {
		this.numExpediente = numExpediente;
	}
	
	

}
