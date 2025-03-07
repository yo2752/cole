package trafico.beans.daos;

/**
 * Contiene los beans necesarios que llaman a los procedimientos almacenados
 * para un tramite de transmision de un vehiculo.
 * 
 * @author antonio.miguez
 */
public class BeanPQTramiteTransmisionImport {

	private BeanPQVehiculosGuardarImport beanGuardarVehiculo;
	private BeanPQTramiteTraficoGuardarTransmision beanGuardarTransmision; 
	private BeanPQTramiteTraficoGuardarIntervinienteImport beanGuardarAdquiriente;
	private BeanPQTramiteTraficoGuardarIntervinienteImport beanGuardarRepresentanteAdquiriente;
	private BeanPQTramiteTraficoGuardarIntervinienteImport beanGuardarTransmitente;
	private BeanPQTramiteTraficoGuardarIntervinienteImport beanGuardarRepresentanteTransmitente;
	private BeanPQTramiteTraficoGuardarIntervinienteImport beanGuardarPoseedor;
	private BeanPQTramiteTraficoGuardarIntervinienteImport beanGuardarRepresentantePoseedor;
	private BeanPQTramiteTraficoGuardarIntervinienteImport beanGuardarArrendador;
	private BeanPQTramiteTraficoGuardarIntervinienteImport beanGuardarRepresentanteArrendador;
	private BeanPQTramiteTraficoGuardarIntervinienteImport beanGuardarPresentador;
	private BeanPQTramiteTraficoGuardarIntervinienteImport beanGuardarConductorHabitual;
	private BeanPQTramiteTraficoGuardarIntervinienteImport beanGuardarPrimerCotitular;
	private BeanPQTramiteTraficoGuardarIntervinienteImport beanGuardarSegundoCotitular;

	// CONSTRUCTORES
	public BeanPQTramiteTransmisionImport() {
		beanGuardarVehiculo = new BeanPQVehiculosGuardarImport();
		beanGuardarTransmision = new BeanPQTramiteTraficoGuardarTransmision();
		beanGuardarAdquiriente = new BeanPQTramiteTraficoGuardarIntervinienteImport();
		beanGuardarRepresentanteAdquiriente = new BeanPQTramiteTraficoGuardarIntervinienteImport();
		beanGuardarTransmitente = new BeanPQTramiteTraficoGuardarIntervinienteImport();
		beanGuardarRepresentanteTransmitente = new BeanPQTramiteTraficoGuardarIntervinienteImport();
		beanGuardarPoseedor = new BeanPQTramiteTraficoGuardarIntervinienteImport();
		beanGuardarRepresentantePoseedor = new BeanPQTramiteTraficoGuardarIntervinienteImport();
		beanGuardarPresentador = new BeanPQTramiteTraficoGuardarIntervinienteImport();
		beanGuardarConductorHabitual = new BeanPQTramiteTraficoGuardarIntervinienteImport();
		beanGuardarPrimerCotitular = new BeanPQTramiteTraficoGuardarIntervinienteImport();
		beanGuardarSegundoCotitular = new BeanPQTramiteTraficoGuardarIntervinienteImport();
		beanGuardarArrendador = new BeanPQTramiteTraficoGuardarIntervinienteImport();
		beanGuardarRepresentanteArrendador = new BeanPQTramiteTraficoGuardarIntervinienteImport();
	}

	// GETTERS & SETTERS
	public BeanPQVehiculosGuardarImport getBeanGuardarVehiculo() {
		return beanGuardarVehiculo;
	}

	public void setBeanGuardarVehiculo(BeanPQVehiculosGuardarImport beanGuardarVehiculo) {
		this.beanGuardarVehiculo = beanGuardarVehiculo;
	}

	public BeanPQTramiteTraficoGuardarTransmision getBeanGuardarTransmision() {
		return beanGuardarTransmision;
	}

	public void setBeanGuardarTransmision(
			BeanPQTramiteTraficoGuardarTransmision beanGuardarTransmision) {
		this.beanGuardarTransmision = beanGuardarTransmision;
	}

	public BeanPQTramiteTraficoGuardarIntervinienteImport getBeanGuardarConductorHabitual() {
		return beanGuardarConductorHabitual;
	}

	public void setBeanGuardarConductorHabitual(
			BeanPQTramiteTraficoGuardarIntervinienteImport beanGuardarConductorHabitual) {
		this.beanGuardarConductorHabitual = beanGuardarConductorHabitual;
	}

	public BeanPQTramiteTraficoGuardarIntervinienteImport getBeanGuardarPrimerCotitular() {
		return beanGuardarPrimerCotitular;
	}

	public void setBeanGuardarPrimerCotitular(
			BeanPQTramiteTraficoGuardarIntervinienteImport beanGuardarPrimerCotitular) {
		this.beanGuardarPrimerCotitular = beanGuardarPrimerCotitular;
	}

	public BeanPQTramiteTraficoGuardarIntervinienteImport getBeanGuardarSegundoCotitular() {
		return beanGuardarSegundoCotitular;
	}

	public void setBeanGuardarSegundoCotitular(
			BeanPQTramiteTraficoGuardarIntervinienteImport beanGuardarSegundoCotitular) {
		this.beanGuardarSegundoCotitular = beanGuardarSegundoCotitular;
	}

	public BeanPQTramiteTraficoGuardarIntervinienteImport getBeanGuardarAdquiriente() {
		return beanGuardarAdquiriente;
	}

	public void setBeanGuardarAdquiriente(
			BeanPQTramiteTraficoGuardarIntervinienteImport beanGuardarAdquiriente) {
		this.beanGuardarAdquiriente = beanGuardarAdquiriente;
	}

	public BeanPQTramiteTraficoGuardarIntervinienteImport getBeanGuardarRepresentanteAdquiriente() {
		return beanGuardarRepresentanteAdquiriente;
	}

	public void setBeanGuardarRepresentanteAdquiriente(
			BeanPQTramiteTraficoGuardarIntervinienteImport beanGuardarRepresentanteAdquiriente) {
		this.beanGuardarRepresentanteAdquiriente = beanGuardarRepresentanteAdquiriente;
	}

	public BeanPQTramiteTraficoGuardarIntervinienteImport getBeanGuardarTransmitente() {
		return beanGuardarTransmitente;
	}

	public void setBeanGuardarTransmitente(
			BeanPQTramiteTraficoGuardarIntervinienteImport beanGuardarTransmitente) {
		this.beanGuardarTransmitente = beanGuardarTransmitente;
	}

	public BeanPQTramiteTraficoGuardarIntervinienteImport getBeanGuardarRepresentanteTransmitente() {
		return beanGuardarRepresentanteTransmitente;
	}

	public void setBeanGuardarRepresentanteTransmitente(
			BeanPQTramiteTraficoGuardarIntervinienteImport beanGuardarRepresentanteTransmitente) {
		this.beanGuardarRepresentanteTransmitente = beanGuardarRepresentanteTransmitente;
	}

	public BeanPQTramiteTraficoGuardarIntervinienteImport getBeanGuardarPoseedor() {
		return beanGuardarPoseedor;
	}

	public void setBeanGuardarPoseedor(
			BeanPQTramiteTraficoGuardarIntervinienteImport beanGuardarPoseedor) {
		this.beanGuardarPoseedor = beanGuardarPoseedor;
	}

	public BeanPQTramiteTraficoGuardarIntervinienteImport getBeanGuardarRepresentantePoseedor() {
		return beanGuardarRepresentantePoseedor;
	}

	public void setBeanGuardarRepresentantePoseedor(
			BeanPQTramiteTraficoGuardarIntervinienteImport beanGuardarRepresentantePoseedor) {
		this.beanGuardarRepresentantePoseedor = beanGuardarRepresentantePoseedor;
	}

	public BeanPQTramiteTraficoGuardarIntervinienteImport getBeanGuardarPresentador() {
		return beanGuardarPresentador;
	}

	public void setBeanGuardarPresentador(
			BeanPQTramiteTraficoGuardarIntervinienteImport beanGuardarPresentador) {
		this.beanGuardarPresentador = beanGuardarPresentador;
	}

	public BeanPQTramiteTraficoGuardarIntervinienteImport getBeanGuardarArrendador() {
		return beanGuardarArrendador;
	}

	public void setBeanGuardarArrendador(
			BeanPQTramiteTraficoGuardarIntervinienteImport beanGuardarArrendador) {
		this.beanGuardarArrendador = beanGuardarArrendador;
	}

	public BeanPQTramiteTraficoGuardarIntervinienteImport getBeanGuardarRepresentanteArrendador() {
		return beanGuardarRepresentanteArrendador;
	}

	public void setBeanGuardarRepresentanteArrendador(
			BeanPQTramiteTraficoGuardarIntervinienteImport beanGuardarRepresentanteArrendador) {
		this.beanGuardarRepresentanteArrendador = beanGuardarRepresentanteArrendador;
	}

}