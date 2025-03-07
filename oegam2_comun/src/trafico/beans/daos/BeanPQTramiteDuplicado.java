package trafico.beans.daos;

import trafico.beans.daos.pq_tramite_trafico.BeanPQGUARDAR_DUPLICADO;

public class BeanPQTramiteDuplicado {

	private BeanPQVehiculosGuardar beanGuardarVehiculo;
	private BeanPQGUARDAR_DUPLICADO beanGuardarDuplicado;
	private BeanPQTramiteTraficoGuardarInterviniente beanGuardarTitular;
	private BeanPQTramiteTraficoGuardarInterviniente beanGuardarRepresentante;
	private BeanPQTramiteTraficoGuardarInterviniente beanGuardarCotitular;

	// CONSTRUCTORES
	public BeanPQTramiteDuplicado() {
		beanGuardarVehiculo = new BeanPQVehiculosGuardar();
		beanGuardarDuplicado = new BeanPQGUARDAR_DUPLICADO();
		beanGuardarTitular = new BeanPQTramiteTraficoGuardarInterviniente();
		beanGuardarRepresentante = new BeanPQTramiteTraficoGuardarInterviniente();
		beanGuardarCotitular = new BeanPQTramiteTraficoGuardarInterviniente();
	}

	// GETTERS & SETTERS
	public BeanPQVehiculosGuardar getBeanGuardarVehiculo() {
		return beanGuardarVehiculo;
	}
	public void setBeanGuardarVehiculo(BeanPQVehiculosGuardar beanGuardarVehiculo) {
		this.beanGuardarVehiculo = beanGuardarVehiculo;
	}
	public BeanPQGUARDAR_DUPLICADO getBeanGuardarDuplicado() {
		return beanGuardarDuplicado;
	}
	public void setBeanGuardarDuplicado(BeanPQGUARDAR_DUPLICADO beanGuardarDuplicado) {
		this.beanGuardarDuplicado = beanGuardarDuplicado;
	}
	public BeanPQTramiteTraficoGuardarInterviniente getBeanGuardarTitular() {
		return beanGuardarTitular;
	}
	public void setBeanGuardarTitular(
			BeanPQTramiteTraficoGuardarInterviniente beanGuardarTitular) {
		this.beanGuardarTitular = beanGuardarTitular;
	}
	public BeanPQTramiteTraficoGuardarInterviniente getBeanGuardarRepresentante() {
		return beanGuardarRepresentante;
	}
	public void setBeanGuardarRepresentante(
			BeanPQTramiteTraficoGuardarInterviniente beanGuardarRepresentante) {
		this.beanGuardarRepresentante = beanGuardarRepresentante;
	}
	public BeanPQTramiteTraficoGuardarInterviniente getBeanGuardarCotitular() {
		return beanGuardarCotitular;
	}
	public void setBeanGuardarCotitular(
			BeanPQTramiteTraficoGuardarInterviniente beanGuardarCotitular) {
		this.beanGuardarCotitular = beanGuardarCotitular;
	}
}