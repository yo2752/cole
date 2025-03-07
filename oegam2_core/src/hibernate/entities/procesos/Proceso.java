package hibernate.entities.procesos;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;

/**
 * The persistent class for the PROCESO database table.
 * 
 */
@Entity
public class Proceso implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ProcesoPK id;

	private String accion;

	@Column(name = "ACCION_NOTIFICACION")
	private BigDecimal accionNotificacion;

	private String clase;

	private String descripcion;

	private BigDecimal estado;

	@Column(name = "HILOS_COLAS")
	private BigDecimal hilosColas;

	@Column(name = "HORA_FIN")
	private String horaFin;

	@Column(name = "HORA_INICIO")
	private String horaInicio;

	@Column(name = "N_INTENTOS_MAX")
	private BigDecimal nIntentosMax;

	@Column(name = "TIEMPO_CORRECTO")
	private BigDecimal tiempoCorrecto;

	@Column(name = "TIEMPO_ERRONEO_NO_RECUPERABLE")
	private BigDecimal tiempoErroneoNoRecuperable;

	@Column(name = "TIEMPO_ERRONEO_RECUPERABLE")
	private BigDecimal tiempoErroneoRecuperable;

	@Column(name = "TIEMPO_SIN_DATOS")
	private BigDecimal tiempoSinDatos;

	private BigDecimal tipo;
	
	@Column(name = "ORDEN")
	private int orden;
	
//	@Column(name = "PROCESO")
//	private String proceso;
//	
//	@Column(name = "NODO")
//	private String nodo;

	public Proceso() {
	}

	public ProcesoPK getId() {
		return this.id;
	}

	public void setId(ProcesoPK id) {
		this.id = id;
	}

	public String getAccion() {
		return this.accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public BigDecimal getAccionNotificacion() {
		return this.accionNotificacion;
	}

	public void setAccionNotificacion(BigDecimal accionNotificacion) {
		this.accionNotificacion = accionNotificacion;
	}

	public String getClase() {
		return this.clase;
	}

	public void setClase(String clase) {
		this.clase = clase;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public BigDecimal getEstado() {
		return this.estado;
	}

	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}

	public BigDecimal getHilosColas() {
		return this.hilosColas;
	}

	public void setHilosColas(BigDecimal hilosColas) {
		this.hilosColas = hilosColas;
	}

	public String getHoraFin() {
		return this.horaFin;
	}

	public void setHoraFin(String horaFin) {
		this.horaFin = horaFin;
	}

	public String getHoraInicio() {
		return this.horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	public BigDecimal getNIntentosMax() {
		return this.nIntentosMax;
	}

	public void setNIntentosMax(BigDecimal nIntentosMax) {
		this.nIntentosMax = nIntentosMax;
	}

	public BigDecimal getTiempoCorrecto() {
		return this.tiempoCorrecto;
	}

	public void setTiempoCorrecto(BigDecimal tiempoCorrecto) {
		this.tiempoCorrecto = tiempoCorrecto;
	}

	public BigDecimal getTiempoErroneoNoRecuperable() {
		return this.tiempoErroneoNoRecuperable;
	}

	public void setTiempoErroneoNoRecuperable(
			BigDecimal tiempoErroneoNoRecuperable) {
		this.tiempoErroneoNoRecuperable = tiempoErroneoNoRecuperable;
	}

	public BigDecimal getTiempoErroneoRecuperable() {
		return this.tiempoErroneoRecuperable;
	}

	public void setTiempoErroneoRecuperable(BigDecimal tiempoErroneoRecuperable) {
		this.tiempoErroneoRecuperable = tiempoErroneoRecuperable;
	}

	public BigDecimal getTiempoSinDatos() {
		return this.tiempoSinDatos;
	}

	public void setTiempoSinDatos(BigDecimal tiempoSinDatos) {
		this.tiempoSinDatos = tiempoSinDatos;
	}

	public BigDecimal getTipo() {
		return this.tipo;
	}

	public void setTipo(BigDecimal tipo) {
		this.tipo = tipo;
	}

	public int getOrden() {
		return orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}

//	public String getProceso() {
//		return proceso;
//	}
//
//	public void setProceso(String proceso) {
//		this.proceso = proceso;
//	}
//
//	public String getNodo() {
//		return nodo;
//	}
//
//	public void setNodo(String nodo) {
//		this.nodo = nodo;
//	}

}