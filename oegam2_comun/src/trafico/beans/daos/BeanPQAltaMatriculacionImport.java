package trafico.beans.daos;



/**
 * contiene los beans necesarios que llaman a los procedimientos almacenados para un alta de matriculacion importación
 *
 */
public class BeanPQAltaMatriculacionImport{
	
	
	
	private BeanPQTramiteTraficoGuardarMatriculacion beanGuardarMatriculacion; 
	private BeanPQTramiteTraficoGuardarIntervinienteImport beanGuardarTitular;
	private BeanPQTramiteTraficoGuardarIntervinienteImport beanGuardarRepresentante;
	private BeanPQTramiteTraficoGuardarIntervinienteImport beanGuardarConductorHabitual;
	private BeanPQTramiteTraficoGuardarIntervinienteImport beanGuardarReprConductorHabitual;
	private BeanPQTramiteTraficoGuardarIntervinienteImport beanGuardarArrendatario;
	private BeanPQTramiteTraficoGuardarIntervinienteImport beanGuardarReprArrendatario;
	private BeanPQVehiculosGuardarImport beanGuardarVehiculo;
	
	public BeanPQTramiteTraficoGuardarMatriculacion getBeanGuardarMatriculacion() {
		return beanGuardarMatriculacion;
	}
	public void setBeanGuardarMatriculacion(
			BeanPQTramiteTraficoGuardarMatriculacion beanGuardarMatriculacion) {
		this.beanGuardarMatriculacion = beanGuardarMatriculacion;
	}
	public BeanPQTramiteTraficoGuardarIntervinienteImport getBeanGuardarTitular() {
		return beanGuardarTitular;
	}
	public void setBeanGuardarTitular(
			BeanPQTramiteTraficoGuardarIntervinienteImport beanGuardarTitular) {
		this.beanGuardarTitular = beanGuardarTitular;
	}
	public BeanPQTramiteTraficoGuardarIntervinienteImport getBeanGuardarRepresentante() {
		return beanGuardarRepresentante;
	}
	public void setBeanGuardarRepresentante(
			BeanPQTramiteTraficoGuardarIntervinienteImport beanGuardarRepresentante) {
		this.beanGuardarRepresentante = beanGuardarRepresentante;
	}
	public BeanPQVehiculosGuardarImport getBeanGuardarVehiculo() {
		return beanGuardarVehiculo;
	}
	public void setBeanGuardarVehiculo(BeanPQVehiculosGuardarImport beanGuardarVehiculo) {
		this.beanGuardarVehiculo = beanGuardarVehiculo;
	}
	public BeanPQTramiteTraficoGuardarIntervinienteImport getBeanGuardarConductorHabitual() {
		return beanGuardarConductorHabitual;
	}
	public void setBeanGuardarConductorHabitual(
			BeanPQTramiteTraficoGuardarIntervinienteImport beanGuardarConductorHabitual) {
		this.beanGuardarConductorHabitual = beanGuardarConductorHabitual;
	}
	public BeanPQTramiteTraficoGuardarIntervinienteImport getBeanGuardarArrendatario() {
		return beanGuardarArrendatario;
	}
	public void setBeanGuardarArrendatario(
			BeanPQTramiteTraficoGuardarIntervinienteImport beanGuardarArrendatario) {
		this.beanGuardarArrendatario = beanGuardarArrendatario;
	}
	public BeanPQTramiteTraficoGuardarIntervinienteImport getBeanGuardarReprConductorHabitual() {
		return beanGuardarReprConductorHabitual;
	}
	public void setBeanGuardarReprConductorHabitual(
			BeanPQTramiteTraficoGuardarIntervinienteImport beanGuardarReprConductorHabitual) {
		this.beanGuardarReprConductorHabitual = beanGuardarReprConductorHabitual;
	}
	public BeanPQTramiteTraficoGuardarIntervinienteImport getBeanGuardarReprArrendatario() {
		return beanGuardarReprArrendatario;
	}
	public void setBeanGuardarReprArrendatario(
			BeanPQTramiteTraficoGuardarIntervinienteImport beanGuardarReprArrendatario) {
		this.beanGuardarReprArrendatario = beanGuardarReprArrendatario;
	}
	
	
	
	
	

}