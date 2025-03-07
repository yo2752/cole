package org.gestoresmadrid.oegamDuplicadoPermisoConducir.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.oegamDuplicadoPermisoConducir.view.bean.FicheroInfoBean;
import org.gestoresmadrid.oegamImpresion.view.dto.ImpresionDto;
import org.gestoresmadrid.oegamInterga.view.dto.IntervinienteIntergaDto;
import org.springframework.stereotype.Component;

import utilidades.estructuras.Fecha;

@Component
public class DuplicadoPermisoConducirDto implements Serializable {

	private static final long serialVersionUID = -7098718444396272640L;

	private Long idDuplicadoPermCond;

	private BigDecimal numExpediente;

	private String tipoTramite;

	private String doiTipo;

	private String codigoTasa;

	private String numColegiado;

	private String estado;

	private String estadoImpresion;

	private String respuesta;

	private IntervinienteIntergaDto titular;

	private String observaciones;

	private String refPropia;

	private String jefatura;

	private Fecha fechaAlta;

	private Fecha fechaPresentacion;

	private Fecha fechaUltModif;

	private Long idContrato;

	private String tipoDuplicado;

	private String emailAUTE;

	private String estadoDeclaracion;

	private String estadoSolicitud;

	private String estadoDeclaracionTitular;

	private String estadoMandato;

	private String estadoOtroDocumento;

	private List<ImpresionDto> mandatos;

	private List<FicheroInfoBean> listaDocumentos;

	public void addListaFicheroInfo(BigDecimal numExpediente, String nombreFichero, String tipo, String puedeEliminarse, String estado) {
		if (listaDocumentos == null || listaDocumentos.isEmpty()) {
			listaDocumentos = new ArrayList<FicheroInfoBean>();
		}
		listaDocumentos.add(new FicheroInfoBean(numExpediente, nombreFichero, tipo, puedeEliminarse, estado));
	}

	public Long getIdDuplicadoPermCond() {
		return idDuplicadoPermCond;
	}

	public void setIdDuplicadoPermCond(Long idDuplicadoPermCond) {
		this.idDuplicadoPermCond = idDuplicadoPermCond;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public String getDoiTipo() {
		return doiTipo;
	}

	public void setDoiTipo(String doiTipo) {
		this.doiTipo = doiTipo;
	}

	public String getCodigoTasa() {
		return codigoTasa;
	}

	public void setCodigoTasa(String codigoTasa) {
		this.codigoTasa = codigoTasa;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getEstadoImpresion() {
		return estadoImpresion;
	}

	public void setEstadoImpresion(String estadoImpresion) {
		this.estadoImpresion = estadoImpresion;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public IntervinienteIntergaDto getTitular() {
		return titular;
	}

	public void setTitular(IntervinienteIntergaDto titular) {
		this.titular = titular;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getRefPropia() {
		return refPropia;
	}

	public void setRefPropia(String refPropia) {
		this.refPropia = refPropia;
	}

	public String getJefatura() {
		return jefatura;
	}

	public void setJefatura(String jefatura) {
		this.jefatura = jefatura;
	}

	public Fecha getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Fecha fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Fecha getFechaPresentacion() {
		return fechaPresentacion;
	}

	public void setFechaPresentacion(Fecha fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}

	public Fecha getFechaUltModif() {
		return fechaUltModif;
	}

	public void setFechaUltModif(Fecha fechaUltModif) {
		this.fechaUltModif = fechaUltModif;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public String getTipoDuplicado() {
		return tipoDuplicado;
	}

	public void setTipoDuplicado(String tipoDuplicado) {
		this.tipoDuplicado = tipoDuplicado;
	}

	public String getEmailAUTE() {
		return emailAUTE;
	}

	public void setEmailAUTE(String emailAUTE) {
		this.emailAUTE = emailAUTE;
	}

	public List<ImpresionDto> getMandatos() {
		return mandatos;
	}

	public void setMandatos(List<ImpresionDto> mandatos) {
		this.mandatos = mandatos;
	}

	public void addMandato(ImpresionDto mandato) {
		if (mandatos == null) {
			mandatos = new ArrayList<ImpresionDto>();
		}
		mandatos.add(mandato);
	}

	public List<FicheroInfoBean> getListaDocumentos() {
		return listaDocumentos;
	}

	public void setListaDocumentos(List<FicheroInfoBean> listaDocumentos) {
		this.listaDocumentos = listaDocumentos;
	}

	public String getEstadoDeclaracion() {
		return estadoDeclaracion;
	}

	public void setEstadoDeclaracion(String estadoDeclaracion) {
		this.estadoDeclaracion = estadoDeclaracion;
	}

	public String getEstadoSolicitud() {
		return estadoSolicitud;
	}

	public void setEstadoSolicitud(String estadoSolicitud) {
		this.estadoSolicitud = estadoSolicitud;
	}

	public String getEstadoDeclaracionTitular() {
		return estadoDeclaracionTitular;
	}

	public void setEstadoDeclaracionTitular(String estadoDeclaracionTitular) {
		this.estadoDeclaracionTitular = estadoDeclaracionTitular;
	}

	public String getEstadoMandato() {
		return estadoMandato;
	}

	public void setEstadoMandato(String estadoMandato) {
		this.estadoMandato = estadoMandato;
	}

	public String getEstadoOtroDocumento() {
		return estadoOtroDocumento;
	}

	public void setEstadoOtroDocumento(String estadoOtroDocumento) {
		this.estadoOtroDocumento = estadoOtroDocumento;
	}
}
