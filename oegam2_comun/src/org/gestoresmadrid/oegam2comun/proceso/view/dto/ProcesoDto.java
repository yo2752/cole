package org.gestoresmadrid.oegam2comun.proceso.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ProcesoDto implements Serializable {

	private static final long serialVersionUID = 1082376016732211084L;

	private String nodo;

	private String proceso;

	private String accion;

	private BigDecimal accionNotificacion;

	private String clase;

	private String descripcion;

	private BigDecimal estado;

	private Long tipo;

	private Long hilosColas;

	private String horaFin;

	private String horaInicio;

	private BigDecimal nIntentosMax;

	private BigDecimal tiempoCorrecto;

	private BigDecimal tiempoErroneoNoRecuperable;

	private BigDecimal tiempoErroneoRecuperable;

	private BigDecimal tiempoSinDatos;

	private Long orden;

	private Date fechaUltimaEjecucion;

	private Boolean activo;

	private Class classe;

	public String getNodo() {
		return nodo;
	}

	public void setNodo(String nodo) {
		this.nodo = nodo;
	}

	public String getProceso() {
		return proceso;
	}

	public void setProceso(String proceso) {
		this.proceso = proceso;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public BigDecimal getAccionNotificacion() {
		return accionNotificacion;
	}

	public void setAccionNotificacion(BigDecimal accionNotificacion) {
		this.accionNotificacion = accionNotificacion;
	}

	public String getClase() {
		return clase;
	}

	public void setClase(String clase) {
		this.clase = clase;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public BigDecimal getEstado() {
		return estado;
	}

	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}

	public Long getHilosColas() {
		return hilosColas;
	}

	public void setHilosColas(Long hilosColas) {
		this.hilosColas = hilosColas;
	}

	public String getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(String horaFin) {
		this.horaFin = horaFin;
	}

	public String getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	public BigDecimal getnIntentosMax() {
		return nIntentosMax;
	}

	public BigDecimal getNIntentosMax() {
		return nIntentosMax;
	}

	public void setnIntentosMax(BigDecimal nIntentosMax) {
		this.nIntentosMax = nIntentosMax;
	}

	public BigDecimal getTiempoCorrecto() {
		return tiempoCorrecto;
	}

	public void setTiempoCorrecto(BigDecimal tiempoCorrecto) {
		this.tiempoCorrecto = tiempoCorrecto;
	}

	public BigDecimal getTiempoErroneoNoRecuperable() {
		return tiempoErroneoNoRecuperable;
	}

	public void setTiempoErroneoNoRecuperable(BigDecimal tiempoErroneoNoRecuperable) {
		this.tiempoErroneoNoRecuperable = tiempoErroneoNoRecuperable;
	}

	public BigDecimal getTiempoErroneoRecuperable() {
		return tiempoErroneoRecuperable;
	}

	public void setTiempoErroneoRecuperable(BigDecimal tiempoErroneoRecuperable) {
		this.tiempoErroneoRecuperable = tiempoErroneoRecuperable;
	}

	public BigDecimal getTiempoSinDatos() {
		return tiempoSinDatos;
	}

	public void setTiempoSinDatos(BigDecimal tiempoSinDatos) {
		this.tiempoSinDatos = tiempoSinDatos;
	}

	public Long getOrden() {
		return orden;
	}

	public void setOrden(Long orden) {
		this.orden = orden;
	}

	public Date getFechaUltimaEjecucion() {
		return fechaUltimaEjecucion;
	}

	public void setFechaUltimaEjecucion(Date fechaUltimaEjecucion) {
		this.fechaUltimaEjecucion = fechaUltimaEjecucion;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public Class getClasse() {
		return classe;
	}

	public void setClasse(Class classe) {
		this.classe = classe;
	}

	public Long getTipo() {
		return tipo;
	}

	public void setTipo(Long tipo) {
		this.tipo = tipo;
	}

}
