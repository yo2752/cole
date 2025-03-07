
package trafico.beans;

import java.math.BigDecimal;

import utilidades.estructuras.FechaFraccionada;
import escrituras.beans.Persona;

public class ConsultaTramiteTraficoFacturacionBean {

	private Integer estado;
	private String tipoTramite;
	private String numColegiado;
	private Persona titular;
	private String referenciaPropia;
	private BigDecimal numExpediente;
	private String matricula;
	private String numBastidor;
	private String nive;
	private FechaFraccionada fechaAlta;
	private FechaFraccionada fechaPresentacion;
	private String nifFacturacion;

	public ConsultaTramiteTraficoFacturacionBean() {
	}

	public ConsultaTramiteTraficoFacturacionBean(boolean inicio) {
		titular = new Persona(true);
		fechaAlta = new FechaFraccionada();
		fechaPresentacion = new FechaFraccionada();
	}

	public String getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public Persona getTitular() {
		return titular;
	}

	public void setTitular(Persona titular) {
		this.titular = titular;
	}

	public String getReferenciaPropia() {
		return referenciaPropia;
	}

	public void setReferenciaPropia(String referenciaPropia) {
		this.referenciaPropia = referenciaPropia;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getNumBastidor() {
		return numBastidor;
	}

	public void setNumBastidor(String numBastidor) {
		this.numBastidor = numBastidor;
	}

	public String getNive() {
		return nive;
	}

	public void setNive(String nive) {
		this.nive = nive;
	}

	public FechaFraccionada getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(FechaFraccionada fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public FechaFraccionada getFechaPresentacion() {
		return fechaPresentacion;
	}

	public void setFechaPresentacion(FechaFraccionada fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}

	public String getNifFacturacion() {
		return nifFacturacion;
	}

	public void setNifFacturacion(String nifFacturacion) {
		if (nifFacturacion != null && !nifFacturacion.equals("")) {
			this.nifFacturacion = nifFacturacion.toUpperCase();
		}
	}

}