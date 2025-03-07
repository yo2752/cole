package trafico.beans.daos;

/**
 * Contiene los beans necesarios que llaman a los procedimientos almacenados 
 * para un tramite de transmision de un vehiculo.
 * 
 * @author antonio.miguez
 */
public class BeanPQTramiteTransmision {

	private BeanPQVehiculosGuardar beanGuardarVehiculo;
	private BeanPQTramiteTraficoGuardarTransmision beanGuardarTransmision; 
	private BeanPQTramiteTraficoGuardarInterviniente beanGuardarAdquiriente;
	private BeanPQTramiteTraficoGuardarInterviniente beanGuardarConductorHabitual;
	private BeanPQTramiteTraficoGuardarInterviniente beanGuardarRepresentanteAdquiriente;
	private BeanPQTramiteTraficoGuardarInterviniente beanGuardarTransmitente;
	private BeanPQTramiteTraficoGuardarInterviniente beanGuardarRepresentanteTransmitente;
	private BeanPQTramiteTraficoGuardarInterviniente beanGuardarPrimerCotitularTransmitente;
	private BeanPQTramiteTraficoGuardarInterviniente beanGuardarRepresentantePrimerCotitularTransmitente;
	private BeanPQTramiteTraficoGuardarInterviniente beanGuardarSegundoCotitularTransmitente;
	private BeanPQTramiteTraficoGuardarInterviniente beanGuardarRepresentanteSegundoCotitularTransmitente;
	private BeanPQTramiteTraficoGuardarInterviniente beanGuardarPresentador;
	private BeanPQTramiteTraficoGuardarInterviniente beanGuardarRepresentantePresentador;
	private BeanPQTramiteTraficoGuardarInterviniente beanGuardarPoseedor;
	private BeanPQTramiteTraficoGuardarInterviniente beanGuardarRepresentantePoseedor;
	private BeanPQTramiteTraficoGuardarInterviniente beanGuardarArrendatario;
	private BeanPQTramiteTraficoGuardarInterviniente beanGuardarRepresentanteArrendatario;
	private BeanPQTramiteTraficoGuardarInterviniente beanGuardarCompraVenta;
	private BeanPQTramiteTraficoGuardarInterviniente beanGuardarRepresentanteCompraventa;

	// CONSTRUCTORES
	public BeanPQTramiteTransmision() {
		beanGuardarVehiculo = new BeanPQVehiculosGuardar();
		beanGuardarTransmision = new BeanPQTramiteTraficoGuardarTransmision();
		beanGuardarAdquiriente = new BeanPQTramiteTraficoGuardarInterviniente();
		beanGuardarConductorHabitual = new BeanPQTramiteTraficoGuardarInterviniente();
		beanGuardarRepresentanteAdquiriente = new BeanPQTramiteTraficoGuardarInterviniente();
		beanGuardarTransmitente = new BeanPQTramiteTraficoGuardarInterviniente();
		beanGuardarRepresentanteTransmitente = new BeanPQTramiteTraficoGuardarInterviniente();
		beanGuardarPrimerCotitularTransmitente = new BeanPQTramiteTraficoGuardarInterviniente();
		beanGuardarRepresentantePrimerCotitularTransmitente = new BeanPQTramiteTraficoGuardarInterviniente();
		beanGuardarSegundoCotitularTransmitente = new BeanPQTramiteTraficoGuardarInterviniente();
		beanGuardarRepresentanteSegundoCotitularTransmitente = new BeanPQTramiteTraficoGuardarInterviniente();
		beanGuardarPresentador = new BeanPQTramiteTraficoGuardarInterviniente();
		beanGuardarRepresentantePresentador = new BeanPQTramiteTraficoGuardarInterviniente();
		beanGuardarPoseedor = new BeanPQTramiteTraficoGuardarInterviniente();
		beanGuardarRepresentantePoseedor = new BeanPQTramiteTraficoGuardarInterviniente();
		beanGuardarArrendatario = new BeanPQTramiteTraficoGuardarInterviniente();
		beanGuardarRepresentanteArrendatario = new BeanPQTramiteTraficoGuardarInterviniente();
		// DRC@03-10-2012 Incidenca: 1801
		beanGuardarCompraVenta = new BeanPQTramiteTraficoGuardarInterviniente();
		beanGuardarRepresentanteCompraventa  = new BeanPQTramiteTraficoGuardarInterviniente();
	}

	// SETTERS & GETTERS
	public BeanPQVehiculosGuardar getBeanGuardarVehiculo() {
		return beanGuardarVehiculo;
	}

	public void setBeanGuardarVehiculo(BeanPQVehiculosGuardar beanGuardarVehiculo) {
		this.beanGuardarVehiculo = beanGuardarVehiculo;
	}

	public BeanPQTramiteTraficoGuardarTransmision getBeanGuardarTransmision() {
		return beanGuardarTransmision;
	}

	public void setBeanGuardarTransmision(
			BeanPQTramiteTraficoGuardarTransmision beanGuardarTransmision) {
		this.beanGuardarTransmision = beanGuardarTransmision;
	}

	public BeanPQTramiteTraficoGuardarInterviniente getBeanGuardarAdquiriente() {
		return beanGuardarAdquiriente;
	}

	public void setBeanGuardarAdquiriente(
			BeanPQTramiteTraficoGuardarInterviniente beanGuardarAdquiriente) {
		this.beanGuardarAdquiriente = beanGuardarAdquiriente;
	}

	public BeanPQTramiteTraficoGuardarInterviniente getBeanGuardarConductorHabitual() {
		return beanGuardarConductorHabitual;
	}

	public void setBeanGuardarConductorHabitual(
			BeanPQTramiteTraficoGuardarInterviniente beanGuardarConductorHabitual) {
		this.beanGuardarConductorHabitual = beanGuardarConductorHabitual;
	}

	public BeanPQTramiteTraficoGuardarInterviniente getBeanGuardarRepresentanteAdquiriente() {
		return beanGuardarRepresentanteAdquiriente;
	}

	public void setBeanGuardarRepresentanteAdquiriente(
			BeanPQTramiteTraficoGuardarInterviniente beanGuardarRepresentanteAdquiriente) {
		this.beanGuardarRepresentanteAdquiriente = beanGuardarRepresentanteAdquiriente;
	}

	public BeanPQTramiteTraficoGuardarInterviniente getBeanGuardarTransmitente() {
		return beanGuardarTransmitente;
	}

	public void setBeanGuardarTransmitente(
			BeanPQTramiteTraficoGuardarInterviniente beanGuardarTransmitente) {
		this.beanGuardarTransmitente = beanGuardarTransmitente;
	}

	public BeanPQTramiteTraficoGuardarInterviniente getBeanGuardarPrimerCotitularTransmitente() {
		return beanGuardarPrimerCotitularTransmitente;
	}

	public void setBeanGuardarPrimerCotitularTransmitente(
			BeanPQTramiteTraficoGuardarInterviniente beanGuardarPrimerCotitularTransmitente) {
		this.beanGuardarPrimerCotitularTransmitente = beanGuardarPrimerCotitularTransmitente;
	}

	public BeanPQTramiteTraficoGuardarInterviniente getBeanGuardarRepresentantePrimerCotitularTransmitente() {
		return beanGuardarRepresentantePrimerCotitularTransmitente;
	}

	public void setBeanGuardarRepresentantePrimerCotitularTransmitente(
			BeanPQTramiteTraficoGuardarInterviniente beanGuardarRepresentantePrimerCotitularTransmitente) {
		this.beanGuardarRepresentantePrimerCotitularTransmitente = beanGuardarRepresentantePrimerCotitularTransmitente;
	}

	public BeanPQTramiteTraficoGuardarInterviniente getBeanGuardarSegundoCotitularTransmitente() {
		return beanGuardarSegundoCotitularTransmitente;
	}

	public void setBeanGuardarSegundoCotitularTransmitente(
			BeanPQTramiteTraficoGuardarInterviniente beanGuardarSegundoCotitularTransmitente) {
		this.beanGuardarSegundoCotitularTransmitente = beanGuardarSegundoCotitularTransmitente;
	}

	public BeanPQTramiteTraficoGuardarInterviniente getBeanGuardarRepresentanteSegundoCotitularTransmitente() {
		return beanGuardarRepresentanteSegundoCotitularTransmitente;
	}

	public void setBeanGuardarRepresentanteSegundoCotitularTransmitente(
			BeanPQTramiteTraficoGuardarInterviniente beanGuardarRepresentanteSegundoCotitularTransmitente) {
		this.beanGuardarRepresentanteSegundoCotitularTransmitente = beanGuardarRepresentanteSegundoCotitularTransmitente;
	}

	public BeanPQTramiteTraficoGuardarInterviniente getBeanGuardarRepresentanteTransmitente() {
		return beanGuardarRepresentanteTransmitente;
	}

	public void setBeanGuardarRepresentanteTransmitente(
			BeanPQTramiteTraficoGuardarInterviniente beanGuardarRepresentanteTransmitente) {
		this.beanGuardarRepresentanteTransmitente = beanGuardarRepresentanteTransmitente;
	}

	public BeanPQTramiteTraficoGuardarInterviniente getBeanGuardarPresentador() {
		return beanGuardarPresentador;
	}

	public void setBeanGuardarPresentador(
			BeanPQTramiteTraficoGuardarInterviniente beanGuardarPresentador) {
		this.beanGuardarPresentador = beanGuardarPresentador;
	}

	public BeanPQTramiteTraficoGuardarInterviniente getBeanGuardarRepresentantePresentador() {
		return beanGuardarRepresentantePresentador;
	}

	public void setBeanGuardarRepresentantePresentador(
			BeanPQTramiteTraficoGuardarInterviniente beanGuardarRepresentantePresentador) {
		this.beanGuardarRepresentantePresentador = beanGuardarRepresentantePresentador;
	}

	public BeanPQTramiteTraficoGuardarInterviniente getBeanGuardarPoseedor() {
		return beanGuardarPoseedor;
	}

	public void setBeanGuardarPoseedor(
			BeanPQTramiteTraficoGuardarInterviniente beanGuardarPoseedor) {
		this.beanGuardarPoseedor = beanGuardarPoseedor;
	}

	public BeanPQTramiteTraficoGuardarInterviniente getBeanGuardarRepresentantePoseedor() {
		return beanGuardarRepresentantePoseedor;
	}
	public void setBeanGuardarRepresentantePoseedor(
			BeanPQTramiteTraficoGuardarInterviniente beanGuardarRepresentantePoseedor) {
		this.beanGuardarRepresentantePoseedor = beanGuardarRepresentantePoseedor;
	}
	public BeanPQTramiteTraficoGuardarInterviniente getBeanGuardarArrendatario() {
		return beanGuardarArrendatario;
	}
	public void setBeanGuardarArrendatario(
			BeanPQTramiteTraficoGuardarInterviniente beanGuardarArrendatario) {
		this.beanGuardarArrendatario = beanGuardarArrendatario;
	}
	public BeanPQTramiteTraficoGuardarInterviniente getBeanGuardarRepresentanteArrendatario() {
		return beanGuardarRepresentanteArrendatario;
	}
	public void setBeanGuardarRepresentanteArrendatario(
			BeanPQTramiteTraficoGuardarInterviniente beanGuardarRepresentanteArrendatario) {
		this.beanGuardarRepresentanteArrendatario = beanGuardarRepresentanteArrendatario;
	}

	public BeanPQTramiteTraficoGuardarInterviniente getBeanGuardarCompraVenta() {
		return beanGuardarCompraVenta;
	}

	public void setBeanGuardarCompraVenta(
			BeanPQTramiteTraficoGuardarInterviniente beanGuardarCompraVenta) {
		this.beanGuardarCompraVenta = beanGuardarCompraVenta;
	}

	public BeanPQTramiteTraficoGuardarInterviniente getBeanGuardarRepresentanteCompraventa() {
		return beanGuardarRepresentanteCompraventa;
	}

	public void setBeanGuardarRepresentanteCompraventa(
			BeanPQTramiteTraficoGuardarInterviniente beanGuardarRepresentanteCompraventa) {
		this.beanGuardarRepresentanteCompraventa = beanGuardarRepresentanteCompraventa;
	}
}