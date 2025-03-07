package administracion.beans.paginacion;

import general.beans.BeanCriteriosSkeletonPaginatedList;

public class BeanCriteriosPropiedades implements BeanCriteriosSkeletonPaginatedList {

	private String nombreFormBusquedaPropiedades;
	private String valorFormBusquedaPropiedades;
	private String entorno;

	public String getNombreFormBusquedaPropiedades() {
		return nombreFormBusquedaPropiedades;
	}

	public void setNombreFormBusquedaPropiedades(
			String nombreFormBusquedaPropiedades) {
		this.nombreFormBusquedaPropiedades = nombreFormBusquedaPropiedades;
	}

	public String getValorFormBusquedaPropiedades() {
		return valorFormBusquedaPropiedades;
	}

	public void setValorFormBusquedaPropiedades(String valorFormBusquedaPropiedades) {
		this.valorFormBusquedaPropiedades = valorFormBusquedaPropiedades;
	}

	public String getEntorno() {
		return entorno;
	}

	public void setEntorno(String entorno) {
		this.entorno = entorno;
	}

	@Override
	public BeanCriteriosSkeletonPaginatedList iniciarBean() {
		return this;
	}

}