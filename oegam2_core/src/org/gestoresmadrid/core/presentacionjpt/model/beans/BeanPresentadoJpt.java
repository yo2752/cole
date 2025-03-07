package org.gestoresmadrid.core.presentacionjpt.model.beans;

import java.util.Date;

import org.gestoresmadrid.core.model.enumerados.EstadoPresentacionJPT;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class BeanPresentadoJpt {

	private String numColegiado;
	private String numExpediente;
	private String matricula;
	private String bastidor;
	private EstadoPresentacionJPT presentado;
	private Date fechaPresentacion;
	private Date fechaPresentacionJpt;
	private TipoTramiteTrafico tipoTramiteTrafico;

	@Autowired
	UtilesFecha utilesFecha;

	public BeanPresentadoJpt() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	public BeanPresentadoJpt(String numColegiado, String numExpediente, TipoTramiteTrafico tipoTramiteTrafico, String matricula,
			String bastidor, EstadoPresentacionJPT presentado, Date fechaPresentacion, Date fechaPresentacionJpt) {
		super();
		this.numColegiado = numColegiado;
		this.numExpediente = numExpediente;
		this.tipoTramiteTrafico = tipoTramiteTrafico;
		this.matricula = matricula;
		this.bastidor = bastidor;
		this.presentado = presentado;
		this.fechaPresentacion = fechaPresentacion;
		this.fechaPresentacionJpt = fechaPresentacionJpt;
	}

	public String getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(String numExpediente) {
		this.numExpediente = numExpediente;
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

	public EstadoPresentacionJPT getPresentado() {
		return presentado;
	}

	public void setPresentado(EstadoPresentacionJPT presentado) {
		this.presentado = presentado;
	}

	public Date getFechaPresentacionJpt() {
		return fechaPresentacionJpt;
	}

	public void setFechaPresentacionJpt(Date fechaPresentacionJpt) {
		this.fechaPresentacionJpt = fechaPresentacionJpt;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public Date getFechaPresentacion() {
		return fechaPresentacion;
	}

	public void setFechaPresentacion(Date fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}

	public void setTipoTramiteTrafico(TipoTramiteTrafico tipoTramiteTrafico) {
		this.tipoTramiteTrafico = tipoTramiteTrafico;
	}

	public TipoTramiteTrafico getTipoTramiteTrafico() {
		return tipoTramiteTrafico;
	}

	public String getFechaPresentacionFormateada(){
		return utilesFecha.formatoFecha(this.fechaPresentacion);
	}

}