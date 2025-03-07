package trafico.beans;

import java.util.ArrayList;
import java.util.List;
import utilidades.estructuras.Fecha;
import escrituras.beans.ResultBean;


public class SolicitudDatosBean {

	//T4
	//Campos a rellenar en la presentación
	/* DATOS NECESARIOS PARA LA LLAMADA VPO
	private String matricula;
	private String tasa;
	private String fechaMatr;
	private String jefatura;
	private String titular;
	private String bastidor;
	private String marca;
	private String modelo;
	private String tipoVehiculo;
	private String color;
	private String procedencia;
	private String servDest;
	private String plazas;
	private String carburante;
	private String autTrans;
	private String potenciaFiscal;
	private String cilindrada;
	private String mma;
	private String tara;
	private String tipoVarVer;
	private String contrHomolog;
	private String mmCarga;
	private String masaCirculacion;
	private String potenciaNetaMax;
	private String relPotenciaPeso;
	private String numPlazasPie;
	private String municipio;
	private String pueblo;
	private String provincia;
	private String domicilio;
	private String codigoPostal;*/
	private SolicitudVehiculoBean solicitudVehiculo;
	private TramiteTraficoBean tramiteTrafico;
	private IntervinienteTrafico solicitante;
	private List<String> listaDatos;
	private Long idContadorUso; //Identificador del contador de uso que se inserta, por si falla en la validación de tasa eliminarlo.
	private Fecha fechaInforme;
	private ResultBean resultado;
	private List <SolicitudVehiculoBean> solicitudesVehiculos;
	

	
	//Campos que indican si la presentación ha ido bien o mal
	/**
	 * Indica si se ha producido algún error en el proceso de consulta de la matrícula
	 */
	private Boolean error;
	
	/**
	 * Mensaje que obtenido en el posible error producido durante la consulta de la matrícula. 
	 */
	private String mensaje;
	
	
	public SolicitudDatosBean() {
		
	}

	public SolicitudDatosBean(boolean inicio) {
		solicitudVehiculo = new SolicitudVehiculoBean(inicio);
		solicitante = new IntervinienteTrafico(inicio);
		fechaInforme = new Fecha();
		tramiteTrafico = new TramiteTraficoBean(inicio);
		solicitudesVehiculos = new ArrayList<SolicitudVehiculoBean>();
	}

	
	public IntervinienteTrafico getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(IntervinienteTrafico solicitante) {
		this.solicitante = solicitante;
	}


	public List<String> getListaDatos() {
		return listaDatos;
	}


	public void setListaDatos(List<String> listaDatos) {
		this.listaDatos = listaDatos;
	}


	public Long getIdContadorUso() {
		return idContadorUso;
	}


	public void setIdContadorUso(Long idContadorUso) {
		this.idContadorUso = idContadorUso;
	}


	public Fecha getFechaInforme() {
		return fechaInforme;
	}


	public void setFechaInforme(Fecha fechaInforme) {
		this.fechaInforme = fechaInforme;
	}


	public Boolean getError() {
		return error;
	}


	public void setError(Boolean error) {
		this.error = error;
	}


	public String getMensaje() {
		return mensaje;
	}


	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}


	
	public ResultBean getResultado() {
		return resultado;
	}

	public void setResultado(ResultBean resultado) {
		this.resultado = resultado;
	}

	public TramiteTraficoBean getTramiteTrafico() {
		return tramiteTrafico;
	}

	public void setTramiteTrafico(TramiteTraficoBean tramiteTrafico) {
		this.tramiteTrafico = tramiteTrafico;
	}

	public List<SolicitudVehiculoBean> getSolicitudesVehiculos() {
		return solicitudesVehiculos;
	}

	public void setSolicitudesVehiculos(
			List<SolicitudVehiculoBean> solicitudesVehiculos) {
		this.solicitudesVehiculos = solicitudesVehiculos;
	}

	public SolicitudVehiculoBean getSolicitudVehiculo() {
		return solicitudVehiculo;
	}

	public void setSolicitudVehiculo(SolicitudVehiculoBean solicitudVehiculo) {
		this.solicitudVehiculo = solicitudVehiculo;
	}



}
