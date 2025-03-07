package org.gestoresmadrid.core.licencias.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the LC_ACTUACION database table.
 */
@Entity
@Table(name = "LC_ACTUACION")
public class LcActuacionVO implements Serializable {

	private static final long serialVersionUID = -6345288253648090388L;

	@Id
	@SequenceGenerator(name = "lc_actuacion_secuencia", sequenceName = "LC_ACTUACION_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "lc_actuacion_secuencia")
	@Column(name = "ID_ACTUACION")
	private Long idActuacion;

	@Column(name = "NUM_EXPEDIENTE_CEDULA")
	private String numExpedienteCedula;

	@Column(name = "NUM_EXPEDIENTE_PARCELACION")
	private String numExpedienteParcelacion;

	@Column(name = "NUM_EXPEDIENTE_MODIFICAR")
	private String numExpedienteModificar;

	@Column(name = "SOLICITUD_AYUDA_REHABILITACION")
	private String solicitudAyudaRehabilitacion;

	@Column(name = "MODIFICA_ACTIVIDAD")
	private BigDecimal modificaActividad;

	@Column(name = "NUM_EXPEDIENTE_CONSULTA")
	private String numExpedienteConsulta;

	@Column(name = "NUM_EXPEDIENTE_LICENCIA")
	private String numExpedienteLicencia;

	@Column(name = "NUM_CC_LICENCIA")
	private String numCcLicencia;
	
	@Column(name = "DOTACIONAL_TRANSPORTE")
	private String dotacionalTransporte;

	@Column(name = "GESTIONADO_ADMIN_PUBLICA")
	private String gestionadoAdminPublica;

	@Column(name = "NIVEL_PROTECCION")
	private String nivelProteccion;

	@Column(name = "NORMA_ZONAL_FIGURA_ORDENACION")
	private String normaZonalFiguraOrdenacion;

	public Long getIdActuacion() {
		return this.idActuacion;
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

	public String getSolicitudAyudaRehabilitacion() {
		return solicitudAyudaRehabilitacion;
	}

	public void setSolicitudAyudaRehabilitacion(String solicitudAyudaRehabilitacion) {
		this.solicitudAyudaRehabilitacion = solicitudAyudaRehabilitacion;
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

	public BigDecimal getModificaActividad() {
		return modificaActividad;
	}

	public void setModificaActividad(BigDecimal modificaActividad) {
		this.modificaActividad = modificaActividad;
	}

	public String getDotacionalTransporte() {
		return dotacionalTransporte;
	}

	public void setDotacionalTransporte(String dotacionalTransporte) {
		this.dotacionalTransporte = dotacionalTransporte;
	}

	public String getGestionadoAdminPublica() {
		return gestionadoAdminPublica;
	}

	public void setGestionadoAdminPublica(String gestionadoAdminPublica) {
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