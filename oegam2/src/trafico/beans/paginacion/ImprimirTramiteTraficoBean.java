package trafico.beans.paginacion;

import escrituras.beans.ResultValidarTramitesImprimir;
import general.beans.BeanCriteriosSkeletonPaginatedList;

public class ImprimirTramiteTraficoBean implements BeanCriteriosSkeletonPaginatedList {

	private String[] numExpedientes;
	private ResultValidarTramitesImprimir resultBeanImprimir;
	private Boolean electronica;
	
	@Override
	public BeanCriteriosSkeletonPaginatedList iniciarBean() {
		return this;
	}
	
	public String[] getNumExpedientes() {
		return numExpedientes;
	}
	
	public void setNumExpedientes(String[] numExpedientes) {
		this.numExpedientes = numExpedientes;
	}

	public ResultValidarTramitesImprimir getResultBeanImprimir() {
		return resultBeanImprimir;
	}

	public void setResultBeanImprimir(
			ResultValidarTramitesImprimir resultBeanImprimir) {
		this.resultBeanImprimir = resultBeanImprimir;
	}

	public Boolean getElectronica() {
		return electronica;
	}

	public void setElectronica(Boolean electronica) {
		this.electronica = electronica;
	}
	
	
	

}
