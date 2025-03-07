package org.gestoresmadrid.oegam2comun.consultaDev.model.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;

import utilidades.estructuras.Fecha;

public class ConsultaDevDto implements Serializable{

	private static final long serialVersionUID = 2325786963758638166L;
	
	private Long idConsultaDev;
	private String cif;
	private String estado;
	private String estadoCif;
	private String codProcedimiento;
	private String descProcedimiento;
	private String emisor;
	private String codEstado;
	private Fecha fechaSuscripcion;
	private String codRespuesta;
	private String respuesta;
	private Date fechaAlta;
	private ContratoDto contrato;
	private List<SuscripcionDevDto> suscripciones;
	
	public String getCif() {
		return cif;
	}
	public void setCif(String cif) {
		this.cif = cif;
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
	public Long getIdConsultaDev() {
		return idConsultaDev;
	}
	public void setIdConsultaDev(Long idConsultaDev) {
		this.idConsultaDev = idConsultaDev;
	}
	public String getCodProcedimiento() {
		return codProcedimiento;
	}
	public void setCodProcedimiento(String codProcedimiento) {
		this.codProcedimiento = codProcedimiento;
	}
	public String getDescProcedimiento() {
		return descProcedimiento;
	}
	public void setDescProcedimiento(String descProcedimiento) {
		this.descProcedimiento = descProcedimiento;
	}
	public String getEmisor() {
		return emisor;
	}
	public void setEmisor(String emisor) {
		this.emisor = emisor;
	}
	public String getCodEstado() {
		return codEstado;
	}
	public void setCodEstado(String codEstado) {
		this.codEstado = codEstado;
	}
	public Fecha getFechaSuscripcion() {
		return fechaSuscripcion;
	}
	public void setFechaSuscripcion(Fecha fechaSuscripcion) {
		this.fechaSuscripcion = fechaSuscripcion;
	}
	public String getCodRespuesta() {
		return codRespuesta;
	}
	public void setCodRespuesta(String codRespuesta) {
		this.codRespuesta = codRespuesta;
	}
	public Date getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public ContratoDto getContrato() {
		return contrato;
	}
	public void setContrato(ContratoDto contrato) {
		this.contrato = contrato;
	}
	public List<SuscripcionDevDto> getSuscripciones() {
		return suscripciones;
	}
	public void setSuscripciones(List<SuscripcionDevDto> suscripciones) {
		this.suscripciones = suscripciones;
	}
	public String getEstadoCif() {
		return estadoCif;
	}
	public void setEstadoCif(String estadoCif) {
		this.estadoCif = estadoCif;
	}
}
