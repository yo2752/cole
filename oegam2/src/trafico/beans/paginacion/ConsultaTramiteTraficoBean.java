package trafico.beans.paginacion;

import java.math.BigDecimal;

import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import escrituras.beans.Persona;
import general.beans.BeanCriteriosSkeletonPaginatedList;
import utilidades.estructuras.FechaFraccionada;

public class ConsultaTramiteTraficoBean implements BeanCriteriosSkeletonPaginatedList {

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
	private String tipoTasa;
	private Short presentadoJPT;
	private Short esLiberableEEFF;
	private Short conNive;
	private String respuesta;
	private String jefaturaProvincial;

	@Autowired
	UtilesFecha utilesFecha;

	public ConsultaTramiteTraficoBean() {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	public ConsultaTramiteTraficoBean(boolean inicio) {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
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

	public void setTipoTasa(String tipoTasa) {
		this.tipoTasa = tipoTasa;
	}

	public String getTipoTasa() {
		return tipoTasa;
	}

	@Override
	public BeanCriteriosSkeletonPaginatedList iniciarBean() {
		fechaAlta = utilesFecha.getFechaFracionadaActual();
		return this;
	}

	public Short getPresentadoJPT() {
		return presentadoJPT;
	}

	public void setPresentadoJPT(Short presentadoJPT) {
		this.presentadoJPT = presentadoJPT;
	}

	public Short getEsLiberableEEFF() {
		return esLiberableEEFF;
	}

	public void setEsLiberableEEFF(Short esLiberableEEFF) {
		this.esLiberableEEFF = esLiberableEEFF;
	}

	public Short getConNive() {
		return conNive;
	}

	public void setConNive(Short conNive) {
		this.conNive = conNive;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public String getJefaturaProvincial() {
		return jefaturaProvincial;
	}

	public void setJefaturaProvincial(String jefaturaProvincial) {
		this.jefaturaProvincial = jefaturaProvincial;
	}
}
