package org.gestoresmadrid.oegam2comun.licenciasCam.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;

import utilidades.estructuras.Fecha;

public class LcTramiteDto implements Serializable {

	private static final long serialVersionUID = -5484990707092296195L;

	private BigDecimal numExpediente;

	private String refPropia;

	private String idSolicitud;

	private String correoElectronico;

	private Fecha fechaAlta;

	private Fecha fechaPresentacion;

	private BigDecimal idContrato;

	private String numColegiado;

	private String telefono;

	private BigDecimal estado;

	private String numAutoliquidacionPagada;

	private String respuestaReg;

	private String respuestaEnv;

	private String respuestaVal;

	private String respuestaCon;

	private String respuestaDoc;

	private Fecha fechaEnvio;

	private Fecha fechaEnvioDoc;

	private String anotacion;

	private String codigoInteresado;

	private String descripcion;

	private String tipoActuacion;

	private Long idLcDatosSuministros;

	private LcDatosSuministrosDto lcDatosSuministros;

	private Long idLcDirEmplazamiento;

	private LcDireccionDto lcIdDirEmplazamiento;

	private LcActuacionDto lcActuacion;

	private Long idLcActuacion;

	private LcInfoLocalDto lcInfoLocal;

	private Long idLcInfoLocal;

	private LcObraDto lcObra;

	private Long idLcObra;

	private List<LcEdificacionDto> lcEdificaciones;

	private Boolean ocupacionViario;

	private String otra;

	private Boolean solicitaOtraAutorizacion;

	private List<LcIntervinienteDto> lcIntervinientes;

	private PersonaDto presentador;

	// Elementos de pantalla
	private LcIntervinienteDto intervinienteInteresado;

	private LcIntervinienteDto intervinienteRepresentante;

	private LcIntervinienteDto intervinienteOtrosInteresados;

	private List<LcIntervinienteDto> otrosInteresados;

	private LcIntervinienteDto intervinienteNotificacion;

	private LcEdificacionDto lcEdificacionAlta;

	private LcEdificacionDto lcEdificacionBaja;

	private ArrayList<FicheroInfo> ficherosSubidos;

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getRefPropia() {
		return refPropia;
	}

	public void setRefPropia(String refPropia) {
		this.refPropia = refPropia;
	}

	public String getIdSolicitud() {
		return idSolicitud;
	}

	public void setIdSolicitud(String idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
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

	public Fecha getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(Fecha fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	public Fecha getFechaEnvioDoc() {
		return fechaEnvioDoc;
	}

	public void setFechaEnvioDoc(Fecha fechaEnvioDoc) {
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

	public LcDatosSuministrosDto getLcDatosSuministros() {
		return lcDatosSuministros;
	}

	public void setLcDatosSuministros(LcDatosSuministrosDto lcDatosSuministros) {
		this.lcDatosSuministros = lcDatosSuministros;
	}

	public Long getIdLcDirEmplazamiento() {
		return idLcDirEmplazamiento;
	}

	public void setIdLcDirEmplazamiento(Long idLcDirEmplazamiento) {
		this.idLcDirEmplazamiento = idLcDirEmplazamiento;
	}

	public LcDireccionDto getLcIdDirEmplazamiento() {
		return lcIdDirEmplazamiento;
	}

	public void setLcIdDirEmplazamiento(LcDireccionDto lcIdDirEmplazamiento) {
		this.lcIdDirEmplazamiento = lcIdDirEmplazamiento;
	}

	public LcActuacionDto getLcActuacion() {
		return lcActuacion;
	}

	public void setLcActuacion(LcActuacionDto lcActuacion) {
		this.lcActuacion = lcActuacion;
	}

	public Long getIdLcActuacion() {
		return idLcActuacion;
	}

	public void setIdLcActuacion(Long idLcActuacion) {
		this.idLcActuacion = idLcActuacion;
	}

	public LcInfoLocalDto getLcInfoLocal() {
		return lcInfoLocal;
	}

	public void setLcInfoLocal(LcInfoLocalDto lcInfoLocal) {
		this.lcInfoLocal = lcInfoLocal;
	}

	public Long getIdLcInfoLocal() {
		return idLcInfoLocal;
	}

	public void setIdLcInfoLocal(Long idLcInfoLocal) {
		this.idLcInfoLocal = idLcInfoLocal;
	}

	public LcObraDto getLcObra() {
		return lcObra;
	}

	public void setLcObra(LcObraDto lcObra) {
		this.lcObra = lcObra;
	}

	public Long getIdLcObra() {
		return idLcObra;
	}

	public void setIdLcObra(Long idLcObra) {
		this.idLcObra = idLcObra;
	}

	public List<LcEdificacionDto> getLcEdificaciones() {
		return lcEdificaciones;
	}

	public void setLcEdificaciones(List<LcEdificacionDto> lcEdificaciones) {
		this.lcEdificaciones = lcEdificaciones;
	}

	public Boolean getOcupacionViario() {
		return ocupacionViario;
	}

	public void setOcupacionViario(Boolean ocupacionViario) {
		this.ocupacionViario = ocupacionViario;
	}

	public String getOtra() {
		return otra;
	}

	public void setOtra(String otra) {
		this.otra = otra;
	}

	public Boolean getSolicitaOtraAutorizacion() {
		return solicitaOtraAutorizacion;
	}

	public void setSolicitaOtraAutorizacion(Boolean solicitaOtraAutorizacion) {
		this.solicitaOtraAutorizacion = solicitaOtraAutorizacion;
	}

	public List<LcIntervinienteDto> getLcIntervinientes() {
		return lcIntervinientes;
	}

	public void setLcIntervinientes(List<LcIntervinienteDto> lcIntervinientes) {
		this.lcIntervinientes = lcIntervinientes;
	}

	public LcIntervinienteDto getIntervinienteInteresado() {
		return intervinienteInteresado;
	}

	public void setIntervinienteInteresado(LcIntervinienteDto intervinienteInteresado) {
		this.intervinienteInteresado = intervinienteInteresado;
	}

	public LcIntervinienteDto getIntervinienteRepresentante() {
		return intervinienteRepresentante;
	}

	public void setIntervinienteRepresentante(LcIntervinienteDto intervinienteRepresentante) {
		this.intervinienteRepresentante = intervinienteRepresentante;
	}

	public LcIntervinienteDto getIntervinienteOtrosInteresados() {
		return intervinienteOtrosInteresados;
	}

	public void setIntervinienteOtrosInteresados(LcIntervinienteDto intervinienteOtrosInteresados) {
		this.intervinienteOtrosInteresados = intervinienteOtrosInteresados;
	}

	public List<LcIntervinienteDto> getOtrosInteresados() {
		return otrosInteresados;
	}

	public void setOtrosInteresados(List<LcIntervinienteDto> otrosInteresados) {
		this.otrosInteresados = otrosInteresados;
	}

	public LcIntervinienteDto getIntervinienteNotificacion() {
		return intervinienteNotificacion;
	}

	public void setIntervinienteNotificacion(LcIntervinienteDto intervinienteNotificacion) {
		this.intervinienteNotificacion = intervinienteNotificacion;
	}

	public LcEdificacionDto getLcEdificacionAlta() {
		return lcEdificacionAlta;
	}

	public void setLcEdificacionAlta(LcEdificacionDto lcEdificacionAlta) {
		this.lcEdificacionAlta = lcEdificacionAlta;
	}

	public LcEdificacionDto getLcEdificacionBaja() {
		return lcEdificacionBaja;
	}

	public void setLcEdificacionBaja(LcEdificacionDto lcEdificacionBaja) {
		this.lcEdificacionBaja = lcEdificacionBaja;
	}

	public ArrayList<FicheroInfo> getFicherosSubidos() {
		return ficherosSubidos;
	}

	public void setFicherosSubidos(ArrayList<FicheroInfo> ficherosSubidos) {
		this.ficherosSubidos = ficherosSubidos;
	}

	public PersonaDto getPresentador() {
		return presentador;
	}

	public void setPresentador(PersonaDto presentador) {
		this.presentador = presentador;
	}
}