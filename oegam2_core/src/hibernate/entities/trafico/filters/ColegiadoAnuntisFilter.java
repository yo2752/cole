package hibernate.entities.trafico.filters;

import java.util.Date;

import utilidades.estructuras.FechaFraccionada;


public class ColegiadoAnuntisFilter {

	private Integer identificador;
	private String numColegiado;
	private Long idContrato;
	private Date fechaInicio;
	private Date fechaFin;
	private Date fecha;
	private FechaFraccionada periodo;
	private String sort;
	private String dir;

	public Integer getIdentificador() {
		return identificador;
	}

	public void setIdentificador(Integer identificador) {
		this.identificador = identificador;
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

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public FechaFraccionada getPeriodo() {
		return periodo;
	}

	public void setPeriodo(FechaFraccionada periodo) {
		this.periodo = periodo;
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
