package general.accionTramite.beans.paginacion;

import general.beans.BeanCriteriosSkeletonPaginatedList;

public class BeanCriteriosAccionTramite implements BeanCriteriosSkeletonPaginatedList {

	private String numExpediente;

	public String getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(String numExpediente) {
		this.numExpediente = numExpediente;
	}

	@Override
	public BeanCriteriosSkeletonPaginatedList iniciarBean() {
		return this;
	}
	
}
