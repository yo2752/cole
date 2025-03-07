package org.gestoresmadrid.oegam2comun.licenciasCam.view.dto;

import java.io.Serializable;

public class LcActuacionDto implements Serializable {

	private static final long serialVersionUID = 6380999034773298111L;

	private Long idActuacion;

	private String numExpedienteCedula;

	private String numExpedienteParcelacion;

	private String numExpedienteModificar;

	private Boolean solicitudAyudaRehabilitacion;

	private Boolean modificaActividad;

	private String numExpedienteConsulta;

	private String numExpedienteLicencia;

	private String numCcLicencia;

	private String dotacionalTransporte;

	private Boolean gestionadoAdminPublica;

	private String nivelProteccion;

	private String normaZonalFiguraOrdenacion;

	public LcActuacionDto() {}

	public Long getIdActuacion() {
		return idActuacion;
	}

	public void setIdActuacion(Long idActuacion) {
		this.idActuacion = idActuacion;
	}

	public String getNumExpedienteCedula() {
		return numExpedienteCedula;
	}

	public void setNumExpedienteCedula(String numExpedienteCedula) {
		this.numExpedienteCedula = numExpedienteCedula;
	}

	public String getNumExpedienteParcelacion() {
		return numExpedienteParcelacion;
	}

	public void setNumExpedienteParcelacion(String numExpedienteParcelacion) {
		this.numExpedienteParcelacion = numExpedienteParcelacion;
	}

	public String getNumExpedienteModificar() {
		return numExpedienteModificar;
	}

	public void setNumExpedienteModificar(String numExpedienteModificar) {
		this.numExpedienteModificar = numExpedienteModificar;
	}

	public Boolean getSolicitudAyudaRehabilitacion() {
		return solicitudAyudaRehabilitacion;
	}

	public void setSolicitudAyudaRehabilitacion(Boolean solicitudAyudaRehabilitacion) {
		this.solicitudAyudaRehabilitacion = solicitudAyudaRehabilitacion;
	}

	public Boolean getModificaActividad() {
		return modificaActividad;
	}

	public void setModificaActividad(Boolean modificaActividad) {
		this.modificaActividad = modificaActividad;
	}

	public String getNumExpedienteConsulta() {
		return numExpedienteConsulta;
	}

	public void setNumExpedienteConsulta(String numExpedienteConsulta) {
		this.numExpedienteConsulta = numExpedienteConsulta;
	}

	public String getNumExpedienteLicencia() {
		return numExpedienteLicencia;
	}

	public void setNumExpedienteLicencia(String numExpedienteLicencia) {
		this.numExpedienteLicencia = numExpedienteLicencia;
	}

	public String getNumCcLicencia() {
		return numCcLicencia;
	}

	public void setNumCcLicencia(String numCcLicencia) {
		this.numCcLicencia = numCcLicencia;
	}

	public String getDotacionalTransporte() {
		return dotacionalTransporte;
	}

	public void setDotacionalTransporte(String dotacionalTransporte) {
		this.dotacionalTransporte = dotacionalTransporte;
	}

	public Boolean getGestionadoAdminPublica() {
		return gestionadoAdminPublica;
	}

	public void setGestionadoAdminPublica(Boolean gestionadoAdminPublica) {
		this.gestionadoAdminPublica = gestionadoAdminPublica;
	}

	public String getNivelProteccion() {
		return nivelProteccion;
	}

	public void setNivelProteccion(String nivelProteccion) {
		this.nivelProteccion = nivelProteccion;
	}

	public String getNormaZonalFiguraOrdenacion() {
		return normaZonalFiguraOrdenacion;
	}

	public void setNormaZonalFiguraOrdenacion(String normaZonalFiguraOrdenacion) {
		this.normaZonalFiguraOrdenacion = normaZonalFiguraOrdenacion;
	}

}