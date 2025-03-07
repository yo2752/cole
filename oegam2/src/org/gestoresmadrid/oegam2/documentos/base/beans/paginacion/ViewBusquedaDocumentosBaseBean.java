package org.gestoresmadrid.oegam2.documentos.base.beans.paginacion;

import org.springframework.stereotype.Component;

import utilidades.estructuras.FechaFraccionada;

/**
 * Bean para recoger y mostrar los datos del AUC Consulta de Documentos Base.
 * 
 * @author ext_ihgl
 *
 */
@Component
public class ViewBusquedaDocumentosBaseBean {

	private FechaFraccionada fechaPresentacion;
	private String numColegiado;
	private String numExpediente;
	private String matricula;
	private String tipoDocumento;

	public FechaFraccionada getFechaPresentacion() {
		return fechaPresentacion;
	}

	public void setFechaPresentacion(FechaFraccionada fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
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

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

}