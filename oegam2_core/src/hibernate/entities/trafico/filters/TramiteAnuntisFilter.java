package hibernate.entities.trafico.filters;

import java.math.BigDecimal;
import java.util.Date;

import utilidades.estructuras.FechaFraccionada;

public class TramiteAnuntisFilter {

	private Long numExpediente;
	private String nifAnunits;
	private String emailAnuntis;
	private String numColegiado;
	private Long idContrato;
	private String matricula;
	private Date fechaSolicitudInicio;
	private Date fechaSolicitudFin;
	private FechaFraccionada periodoSolicitud;
	private Date fechaAltaInicio;
	private Date fechaAltaFin;
	private FechaFraccionada periodoAlta;
	private Date fechaPresentacionInicio;
	private Date fechaPresentacionFin;
	private FechaFraccionada periodoPresentacion;
	private BigDecimal estado;
	private String sort;
	private String dir;
	private Integer idRequest;

	public Long getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(Long numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getNifAnunits() {
		return nifAnunits;
	}

	public void setNifAnunits(String nifAnunits) {
		this.nifAnunits = nifAnunits;
	}

	public String getEmailAnuntis() {
		return emailAnuntis;
	}

	public void setEmailAnuntis(String emailAnuntis) {
		this.emailAnuntis = emailAnuntis;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public Date getFechaSolicitudInicio() {
		return fechaSolicitudInicio;
	}

	public void setFechaSolicitudInicio(Date fechaSolicitudInicio) {
		this.fechaSolicitudInicio = fechaSolicitudInicio;
	}

	public Date getFechaSolicitudFin() {
		return fechaSolicitudFin;
	}

	public void setFechaSolicitudFin(Date fechaSolicitudFin) {
		this.fechaSolicitudFin = fechaSolicitudFin;
	}

	public Date getFechaAltaInicio() {
		return fechaAltaInicio;
	}

	public void setFechaAltaInicio(Date fechaAltaInicio) {
		this.fechaAltaInicio = fechaAltaInicio;
	}

	public Date getFechaAltaFin() {
		return fechaAltaFin;
	}

	public void setFechaAltaFin(Date fechaAltaFin) {
		this.fechaAltaFin = fechaAltaFin;
	}

	public Date getFechaPresentacionInicio() {
		return fechaPresentacionInicio;
	}

	public void setFechaPresentacionInicio(Date fechaPresentacionInicio) {
		this.fechaPresentacionInicio = fechaPresentacionInicio;
	}

	public Date getFechaPresentacionFin() {
		return fechaPresentacionFin;
	}

	public void setFechaPresentacionFin(Date fechaPresentacionFin) {
		this.fechaPresentacionFin = fechaPresentacionFin;
	}

	public BigDecimal getEstado() {
		return estado;
	}

	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public FechaFraccionada getPeriodoSolicitud() {
		return periodoSolicitud;
	}

	public void setPeriodoSolicitud(FechaFraccionada periodoSolicitud) {
		this.periodoSolicitud = periodoSolicitud;
	}

	public FechaFraccionada getPeriodoAlta() {
		return periodoAlta;
	}

	public void setPeriodoAlta(FechaFraccionada periodoAlta) {
		this.periodoAlta = periodoAlta;
	}

	public FechaFraccionada getPeriodoPresentacion() {
		return periodoPresentacion;
	}

	public void setPeriodoPresentacion(FechaFraccionada periodoPresentacion) {
		this.periodoPresentacion = periodoPresentacion;
	}

	public Integer getIdRequest() {
		return idRequest;
	}

	public void setIdRequest(Integer idRequest) {
		this.idRequest = idRequest;
	}

}
