package trafico.beans.daos;


/**
 * contiene los beans necesarios que llaman a los procedimientos almacenados para un alta de matriculacion. 
 * @author JoseA.Bzreski
 *
 */
public class BeanPQAltaMatriculacion {
	
	
	
	private BeanPQTramiteTraficoGuardarMatriculacion beanGuardarMatriculacion; 
	private BeanPQTramiteTraficoGuardarInterviniente beanGuardarTitular;
	private BeanPQTramiteTraficoGuardarInterviniente beanGuardarRepresentante;
	private BeanPQTramiteTraficoGuardarInterviniente beanGuardarConductorHabitual;
	private BeanPQTramiteTraficoGuardarInterviniente beanGuardarArrendatario;
	private BeanPQTramiteTraficoGuardarInterviniente beanGuardarRepresentanteConductorHabitual;
	private BeanPQTramiteTraficoGuardarInterviniente beanGuardarRepresentanteArrendatario;
	private BeanPQVehiculosGuardar beanGuardarVehiculo;
	
	public BeanPQTramiteTraficoGuardarMatriculacion getBeanGuardarMatriculacion() {
		return beanGuardarMatriculacion;
	}
	public void setBeanGuardarMatriculacion(
			BeanPQTramiteTraficoGuardarMatriculacion beanGuardarMatriculacion) {
		this.beanGuardarMatriculacion = beanGuardarMatriculacion;
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
	public BeanPQTramiteTraficoGuardarInterviniente getBeanGuardarConductorHabitual() {
		return beanGuardarConductorHabitual;
	}
	public void setBeanGuardarConductorHabitual(
			BeanPQTramiteTraficoGuardarInterviniente beanGuardarConductorHabitual) {
		this.beanGuardarConductorHabitual = beanGuardarConductorHabitual;
	}
	public BeanPQVehiculosGuardar getBeanGuardarVehiculo() {
		return beanGuardarVehiculo;
	}
	public void setBeanGuardarVehiculo(BeanPQVehiculosGuardar beanGuardarVehiculo) {
		this.beanGuardarVehiculo = beanGuardarVehiculo;
	}
	public BeanPQTramiteTraficoGuardarInterviniente getBeanGuardarArrendatario() {
		return beanGuardarArrendatario;
	}
	public void setBeanGuardarArrendatario(
			BeanPQTramiteTraficoGuardarInterviniente beanGuardarArrendatario) {
		this.beanGuardarArrendatario = beanGuardarArrendatario;
	}
	public BeanPQTramiteTraficoGuardarInterviniente getBeanGuardarRepresentanteConductorHabitual() {
		return beanGuardarRepresentanteConductorHabitual;
	}
	public void setBeanGuardarRepresentanteConductorHabitual(
			BeanPQTramiteTraficoGuardarInterviniente beanGuardarRepresentanteConductorHabitual) {
		this.beanGuardarRepresentanteConductorHabitual = beanGuardarRepresentanteConductorHabitual;
	}
	public BeanPQTramiteTraficoGuardarInterviniente getBeanGuardarRepresentanteArrendatario() {
		return beanGuardarRepresentanteArrendatario;
	}
	public void setBeanGuardarRepresentanteArrendatario(
			BeanPQTramiteTraficoGuardarInterviniente beanGuardarRepresentanteArrendatario) {
		this.beanGuardarRepresentanteArrendatario = beanGuardarRepresentanteArrendatario;
	}
	
	
	
	
	

}
