package org.gestoresmadrid.core.licencias.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the LC_TRAMITE database table.
 */
@Entity
@Table(name = "LC_TRAMITE")
@Inheritance(strategy = InheritanceType.JOINED)
public class LcTramiteVO implements Serializable {

	private static final long serialVersionUID = 780813578098158390L;

	@Id
	@Column(name = "NUM_EXPEDIENTE")
	private BigDecimal numExpediente;

	@Column(name = "REF_PROPIA")
	private String refPropia;

	@Column(name = "ID_SOLICITUD")
	private String idSolicitud;

	@Column(name = "CORREO_ELECTRONICO")
	private String correoElectronico;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_ALTA")
	private Date fechaAlta;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_PRESENTACION")
	private Date fechaPresentacion;

	@Column(name = "ID_CONTRATO")
	private BigDecimal idContrato;

	@Column(name = "NUM_COLEGIADO")
	private String numColegiado;

	@Column(name = "TELEFONO")
	private String telefono;

	@Column(name = "ESTADO")
	private BigDecimal estado;

	@Column(name = "NUM_AUTOLIQUIDACION_PAGADA")
	private String numAutoliquidacionPagada;

	@Column(name = "RESPUESTA_REG")
	private String respuestaReg;

	@Column(name = "RESPUESTA_ENV")
	private String respuestaEnv;

	@Column(name = "RESPUESTA_VAL")
	private String respuestaVal;

	@Column(name = "RESPUESTA_CON")
	private String respuestaCon;

	@Column(name = "RESPUESTA_DOC")
	private String respuestaDoc;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_ENVIO")
	private Date fechaEnvio;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_ENVIO_DOC")
	private Date fechaEnvioDoc;

	@Column(name = "ANOTACION")
	private String anotacion;

	@Column(name = "CODIGO_INTERESADO")
	private String codigoInteresado;

	@Column(name = "DESCRIPCION")
	private String descripcion;

	@Column(name = "TIPO_ACTUACION")
	private String tipoActuacion;

	@Column(name = "ID_DATOS_SUMINISTROS")
	private Long idLcDatosSuministros;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_DATOS_SUMINISTROS", insertable = false, updatable = false)
	private LcDatosSuministrosVO lcDatosSuministros;

	@Column(name = "ID_DIR_EMPL_PRINCIPAL")
	private Long idLcDirEmplazamiento;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_DIR_EMPL_PRINCIPAL", insertable = false, updatable = false)
	private LcDireccionVO lcIdDirEmplazamiento;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_ACTUACION", insertable = false, updatable = false)
	private LcActuacionVO lcActuacion;

	@Column(name = "ID_ACTUACION")
	private Long idLcActuacion;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_INFO_LOCAL", insertable = false, updatable = false)
	private LcInfoLocalVO lcInfoLocal;

	@Column(name = "ID_INFO_LOCAL")
	private Long idLcInfoLocal;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_DATOS_OBRA", insertable = false, updatable = false)
	private LcObraVO lcObra;

	@Column(name = "ID_DATOS_OBRA")
	private Long idLcObra;

	@OneToMany(mappedBy = "lcTramite")
	private Set<LcEdificacionVO> lcEdificaciones;

	@Column(name = "OCUPACION_VIARIO")
	private String ocupacionViario;

	private String otra;

	@Column(name = "SOLICITA_OTRA_AUTORIZACION")
	private String solicitaOtraAutorizacion;

	@OneToMany(mappedBy = "lcIntervinientes")
	private Set<LcIntervinienteVO> lcIntervinientes;

	public List<LcEdificacionVO> getLcEdificacionesAsList() {
		List<LcEdificacionVO> lista;
		if (lcEdificaciones != null && !lcEdificaciones.isEmpty()) {
			lista = new ArrayList<LcEdificacionVO>(lcEdificaciones);
		} else {
			lista = Collections.emptyList();
		}
		return lista;
	}

	public List<LcIntervinienteVO> getLcIntervinientesAsList() {
		List<LcIntervinienteVO> lista;
		if (lcIntervinientes != null && !lcIntervinientes.isEmpty()) {
			lista = new ArrayList<LcIntervinienteVO>(lcIntervinientes);
		} else {
			lista = Collections.emptyList();
		}
		return lista;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getIdSolicitud() {
		return idSolicitud;
	}

	public void setIdSolicitud(String idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	public String getRefPropia() {
		return refPropia;
	}

	public void setRefPropia(String refPropia) {
		this.refPropia = refPropia;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaPresentacion() {
		return fechaPresentacion;
	}

	public void setFechaPresentacion(Date fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}

	public BigDecimal getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(BigDecimal idContrato) {
		this.idContrato = idContrato;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public BigDecimal getEstado() {
		return estado;
	}

	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}

	public String getNumAutoliquidacionPagada() {
		return numAutoliquidacionPagada;
	}

	public void setNumAutoliquidacionPagada(String numAutoliquidacionPagada) {
		this.numAutoliquidacionPagada = numAutoliquidacionPagada;
	}

	public String getRespuestaReg() {
		return respuestaReg;
	}

	public void setRespuestaReg(String respuestaReg) {
		this.respuestaReg = respuestaReg;
	}

	public String getRespuestaEnv() {
		return respuestaEnv;
	}

	public void setRespuestaEnv(String respuestaEnv) {
		this.respuestaEnv = respuestaEnv;
	}

	public String getRespuestaVal() {
		return respuestaVal;
	}

	public void setRespuestaVal(String respuestaVal) {
		this.respuestaVal = respuestaVal;
	}

	public String getRespuestaCon() {
		return respuestaCon;
	}

	public void setRespuestaCon(String respuestaCon) {
		this.respuestaCon = respuestaCon;
	}

	public String getRespuestaDoc() {
		return respuestaDoc;
	}

	public void setRespuestaDoc(String respuestaDoc) {
		this.respuestaDoc = respuestaDoc;
	}

	public Date getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(Date fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	public Date getFechaEnvioDoc() {
		return fechaEnvioDoc;
	}

	public void setFechaEnvioDoc(Date fechaEnvioDoc) {
		this.fechaEnvioDoc = fechaEnvioDoc;
	}

	public String getAnotacion() {
		return anotacion;
	}

	public void setAnotacion(String anotacion) {
		this.anotacion = anotacion;
	}

	public String getCodigoInteresado() {
		return codigoInteresado;
	}

	public void setCodigoInteresado(String codigoInteresado) {
		this.codigoInteresado = codigoInteresado;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTipoActuacion() {
		return tipoActuacion;
	}

	public void setTipoActuacion(String tipoActuacion) {
		this.tipoActuacion = tipoActuacion;
	}

	public Long getIdLcDatosSuministros() {
		return idLcDatosSuministros;
	}

	public void setIdLcDatosSuministros(Long idLcDatosSuministros) {
		this.idLcDatosSuministros = idLcDatosSuministros;
	}

	public LcDatosSuministrosVO getLcDatosSuministros() {
		return lcDatosSuministros;
	}

	public void setLcDatosSuministros(LcDatosSuministrosVO lcDatosSuministros) {
		this.lcDatosSuministros = lcDatosSuministros;
	}

	public Long getIdLcDirEmplazamiento() {
		return idLcDirEmplazamiento;
	}

	public void setIdLcDirEmplazamiento(Long idLcDirEmplazamiento) {
		this.idLcDirEmplazamiento = idLcDirEmplazamiento;
	}

	public LcDireccionVO getLcIdDirEmplazamiento() {
		return lcIdDirEmplazamiento;
	}

	public void setLcIdDirEmplazamiento(LcDireccionVO lcIdDirEmplazamiento) {
		this.lcIdDirEmplazamiento = lcIdDirEmplazamiento;
	}

	public LcActuacionVO getLcActuacion() {
		return lcActuacion;
	}

	public void setLcActuacion(LcActuacionVO lcActuacion) {
		this.lcActuacion = lcActuacion;
	}

	public Long getIdLcActuacion() {
		return idLcActuacion;
	}

	public void setIdLcActuacion(Long idLcActuacion) {
		this.idLcActuacion = idLcActuacion;
	}

	public LcInfoLocalVO getLcInfoLocal() {
		return lcInfoLocal;
	}

	public void setLcInfoLocal(LcInfoLocalVO lcInfoLocal) {
		this.lcInfoLocal = lcInfoLocal;
	}

	public Long getIdLcInfoLocal() {
		return idLcInfoLocal;
	}

	public void setIdLcInfoLocal(Long idLcInfoLocal) {
		this.idLcInfoLocal = idLcInfoLocal;
	}

	public LcObraVO getLcObra() {
		return lcObra;
	}

	public void setLcObra(LcObraVO lcObra) {
		this.lcObra = lcObra;
	}

	public Long getIdLcObra() {
		return idLcObra;
	}

	public void setIdLcObra(Long idLcObra) {
		this.idLcObra = idLcObra;
	}

	public String getOcupacionViario() {
		return ocupacionViario;
	}

	public void setOcupacionViario(String ocupacionViario) {
		this.ocupacionViario = ocupacionViario;
	}

	public String getOtra() {
		return otra;
	}

	public void setOtra(String otra) {
		this.otra = otra;
	}

	public String getSolicitaOtraAutorizacion() {
		return solicitaOtraAutorizacion;
	}

	public void setSolicitaOtraAutorizacion(String solicitaOtraAutorizacion) {
		this.solicitaOtraAutorizacion = solicitaOtraAutorizacion;
	}

	public Set<LcIntervinienteVO> getLcIntervinientes() {
		return lcIntervinientes;
	}

	public void setLcIntervinientes(Set<LcIntervinienteVO> lcIntervinientes) {
		this.lcIntervinientes = lcIntervinientes;
	}

	public Set<LcEdificacionVO> getLcEdificaciones() {
		return lcEdificaciones;
	}

	public void setLcEdificaciones(Set<LcEdificacionVO> lcEdificaciones) {
		this.lcEdificaciones = lcEdificaciones;
	}

}