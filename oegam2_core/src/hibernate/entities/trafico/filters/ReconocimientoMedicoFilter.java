package hibernate.entities.trafico.filters;

import java.math.BigDecimal;
import java.util.Date;

import utilidades.estructuras.FechaFraccionada;

public class ReconocimientoMedicoFilter {
	private Long idReconocimiento;
	private Long idClinica;
	private String numColegiado;
	private Long idContrato;
	private String nif;
	private String nombre;
	private String apellido1RazonSocial;
	private String apellido2;
	private Date fechaAltaIni;
	private Date fechaAltaFin;
	private Date fechaReconocimientoIni;
	private Date fechaReconocimientoFin;
	private FechaFraccionada periodoAlta;
	private FechaFraccionada periodoPresentacion;
	
	private BigDecimal estado;
	private String sort;
	private String dir;

	public Long getIdReconocimiento() {
		return idReconocimiento;
	}

	public void setIdReconocimiento(Long idReconocimiento) {
		this.idReconocimiento = idReconocimiento;
	}

	public Long getIdClinica() {
		return idClinica;
	}

	public void setIdClinica(Long idClinica) {
		this.idClinica = idClinica;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido1RazonSocial() {
		return apellido1RazonSocial;
	}

	public void setApellido1RazonSocial(String apellido1RazonSocial) {
		this.apellido1RazonSocial = apellido1RazonSocial;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public Date getFechaAltaIni() {
		return fechaAltaIni;
	}

	public void setFechaAltaIni(Date fechaAltaIni) {
		this.fechaAltaIni = fechaAltaIni;
	}

	public Date getFechaAltaFin() {
		return fechaAltaFin;
	}

	public void setFechaAltaFin(Date fechaAltaFin) {
		this.fechaAltaFin = fechaAltaFin;
	}

	public Date getFechaReconocimientoIni() {
		return fechaReconocimientoIni;
	}

	public void setFechaReconocimientoIni(Date fechaReconocimientoIni) {
		this.fechaReconocimientoIni = fechaReconocimientoIni;
	}

	public Date getFechaReconocimientoFin() {
		return fechaReconocimientoFin;
	}

	public void setFechaReconocimientoFin(Date fechaReconocimientoFin) {
		this.fechaReconocimientoFin = fechaReconocimientoFin;
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

}
