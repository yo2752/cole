package org.gestoresmadrid.oegam2comun.trafico.solicitudesplacas.view.beans;

import hibernate.entities.general.Usuario;
import hibernate.entities.trafico.Vehiculo;

import org.gestoresmadrid.oegam2comun.trafico.solicitudesplacas.enumerados.EstadoSolicitudPlacasEnum;
import org.gestoresmadrid.oegam2comun.trafico.solicitudesplacas.enumerados.TipoPlacasMatriculasEnum;



import escrituras.beans.Persona;

public class SolicitudPlacaBean {
	
	/* Inicio declaración de atributos */
	
	private Integer idSolicitud;
	private Long numExpediente;
	private Integer idContrato;
	private String numColegiado;
	
	private Usuario usuario;
	private String nombreUsuario;
	private TipoPlacasMatriculasEnum tipoPlacaDelantera;
	private TipoPlacasMatriculasEnum tipoPlacaTrasera;
	private TipoPlacasMatriculasEnum tipoPlacaAdicional;
	private String fechaSolicitud;
	private EstadoSolicitudPlacasEnum estado;
	private Persona titular;
	private Vehiculo vehiculo;
	private boolean individual;
	private boolean duplicada;
	
	// atributos de persona. Solo en caso de que la persona no exista en el sistema
	private String nombre;
	private String primerApellido; 
	private String segundoApellido;
	private String nif;
	private String mostrarSeccionPersona;
	
	private String matricula;	
	private String bastidor;	
	private String tipoVehiculo;
	
	/* Fin declaración de atributos */
	
	/* Inicio getters & setters */
	
	public Integer getIdSolicitud() {
		return idSolicitud;
	}

	public void setIdSolicitud(Integer idSolicitud) {
		this.idSolicitud = idSolicitud;
	}
	
	public Long getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(Long numExpediente) {
		this.numExpediente = numExpediente;
	}
	
	public Integer getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Integer idContrato) {
		this.idContrato = idContrato;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	
	public TipoPlacasMatriculasEnum getTipoPlacaDelantera() {
		return tipoPlacaDelantera;
	}
	
	public void setTipoPlacaDelantera(String tipoPlacaDelantera) {
		this.tipoPlacaDelantera = TipoPlacasMatriculasEnum.convertir(tipoPlacaDelantera);
	}
	
	public TipoPlacasMatriculasEnum getTipoPlacaTrasera() {
		return tipoPlacaTrasera;
	}
	
	public void setTipoPlacaTrasera(String tipoPlacaTrasera) {
		this.tipoPlacaTrasera = TipoPlacasMatriculasEnum.convertir(tipoPlacaTrasera);
	}
	
	public TipoPlacasMatriculasEnum getTipoPlacaAdicional() {
		return tipoPlacaAdicional;
	}

	public void setTipoPlacaAdicional(String tipoPlacaAdicional) {
		this.tipoPlacaAdicional = TipoPlacasMatriculasEnum.convertir(tipoPlacaAdicional);
	}

	public String getFechaSolicitud() {
		return fechaSolicitud;
	}
	
	public void setFechaSolicitud(String fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public EstadoSolicitudPlacasEnum getEstado() {
		return estado;
	}
	
	public void setEstado(String estado) {
		this.estado = EstadoSolicitudPlacasEnum.convertir(estado);
	}

	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}

	public Persona getTitular() {
		return titular;
	}

	public void setTitular(Persona titular) {
		this.titular = titular;
	}

	public boolean isIndividual() {
		return individual;
	}

	public void setIndividual(boolean individual) {
		this.individual = individual;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public boolean isDuplicada() {
		return duplicada;
	}

	public void setDuplicada(boolean duplicada) {
		this.duplicada = duplicada;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPrimerApellido() {
		return primerApellido;
	}

	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	public String getSegundoApellido() {
		return segundoApellido;
	}

	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getMostrarSeccionPersona() {
		return mostrarSeccionPersona;
	}

	public void setMostrarSeccionPersona(String mostrarSeccionPersona) {
		this.mostrarSeccionPersona = mostrarSeccionPersona;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getBastidor() {
		return bastidor;
	}

	public void setBastidor(String bastidor) {
		this.bastidor = bastidor;
	}

	public String getTipoVehiculo() {
		return tipoVehiculo;
	}

	public void setTipoVehiculo(String tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	/* Fin getters & setters */
	
}
