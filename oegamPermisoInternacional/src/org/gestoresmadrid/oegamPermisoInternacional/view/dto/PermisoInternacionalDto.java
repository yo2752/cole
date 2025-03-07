package org.gestoresmadrid.oegamPermisoInternacional.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.oegamImpresion.view.dto.ImpresionDto;
import org.gestoresmadrid.oegamInterga.view.dto.IntervinienteIntergaDto;
import org.gestoresmadrid.oegamPermisoInternacional.view.bean.FicheroInfoBean;
import org.springframework.stereotype.Component;

import utilidades.estructuras.Fecha;

@Component
public class PermisoInternacionalDto implements Serializable {

	private static final long serialVersionUID = 3269156664266846925L;

	private Long idPermiso;

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

	private String documentacionAportada;

	private String numReferencia;

	private List<FicheroInfoBean> listaDocumentos;

	private Fecha fechaAlta;

	private Fecha fechaPresentacion;

	private Fecha fechaUltModif;

	private Long idContrato;

	private List<ImpresionDto> mandatos;

	private String estadoDeclaracion;

	public void addListaFicheroInfo(String nombreFichero, Long idPermisoInternacional, String numReferencia) {
		if (listaDocumentos == null || listaDocumentos.isEmpty()) {
			listaDocumentos = new ArrayList<>();
		}
		listaDocumentos.add(new FicheroInfoBean(nombreFichero, idPermisoInternacional, numReferencia));
	}

	public Long getIdPermiso() {
		return idPermiso;
	}

	public void setIdPermiso(Long idPermiso) {
		this.idPermiso = idPermiso;
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

	public String getEstadoImpresion() {
		return estadoImpresion;
	}

	public void setEstadoImpresion(String estadoImpresion) {
		this.estadoImpresion = estadoImpresion;
	}

	public String getJefatura() {
		return jefatura;
	}

	public void setJefatura(String jefatura) {
		this.jefatura = jefatura;
	}

	public String getDocumentacionAportada() {
		return documentacionAportada;
	}

	public void setDocumentacionAportada(String documentacionAportada) {
		this.documentacionAportada = documentacionAportada;
	}

	public List<FicheroInfoBean> getListaDocumentos() {
		return listaDocumentos;
	}

	public void setListaDocumentos(List<FicheroInfoBean> listaDocumentos) {
		this.listaDocumentos = listaDocumentos;
	}

	public String getNumReferencia() {
		return numReferencia;
	}

	public void setNumReferencia(String numReferencia) {
		this.numReferencia = numReferencia;
	}

	public List<ImpresionDto> getMandatos() {
		return mandatos;
	}

	public void setMandatos(List<ImpresionDto> mandatos) {
		this.mandatos = mandatos;
	}

	public void addMandato(ImpresionDto mandato) {
		if (mandatos == null) {
			mandatos = new ArrayList<>();
		}
		mandatos.add(mandato);
	}

	public String getEstadoDeclaracion() {
		return estadoDeclaracion;
	}

	public void setEstadoDeclaracion(String estadoDeclaracion) {
		this.estadoDeclaracion = estadoDeclaracion;
	}
}