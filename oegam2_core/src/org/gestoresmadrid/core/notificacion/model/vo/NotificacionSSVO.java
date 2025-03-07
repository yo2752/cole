package org.gestoresmadrid.core.notificacion.model.vo;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="NOTIFICACION_SS")
public class NotificacionSSVO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public NotificacionSSVO() {
	}

	@EmbeddedId
	private IdNumColegiadoPK id;
	
	@Column(name="DESCRIPCION_ESTADO")
    private java.lang.String descripcionEstado;
	
	@Column(name="DESCRIPCION_TIPO_DESTINATARIO")
    private java.lang.String descripcionTipoDestinatario;
	
	@Column(name="DESTINATARIO")
    private java.lang.String destinatario;
	
	@Column(name="ESTADO")
    private int estado;
	
	@Column(name="FECHA_FIN_DISPONIBILIDAD")
    private java.util.Date fechaFinDisponibilidad;
	
	@Column(name="FECHA_PUESTA_DISPOSICION")
    private java.util.Date fechaPuestaDisposicion;
	
	@Column(name="NOMBRE_APE_RAZON_SOCIAL")
    private java.lang.String nombreAppRazonSocial;
	
	@Column(name="PROCEDIMIENTO")
    private java.lang.String procedimiento;
	
	@Column(name="TIPO_DESTINATARIO")
    private java.lang.String tipoDestinatario;
	
	@Column(name="ROL")
    private int rol;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "notificacion")
	private List<RespuestaSSVO> respuesta;
	
	public List<RespuestaSSVO> getRespuesta() {
		return respuesta;
	}
	
	public void setRespuesta(List<RespuestaSSVO> respuesta) {
		this.respuesta = respuesta;
	}
	
	public IdNumColegiadoPK getId() {
		return id;
	}

	public void setId(IdNumColegiadoPK id) {
		this.id = id;
	}

	public java.lang.String getDescripcionEstado() {
		return descripcionEstado;
	}

	public void setDescripcionEstado(java.lang.String descripcionEstado) {
		this.descripcionEstado = descripcionEstado;
	}

	public java.lang.String getDescripcionTipoDestinatario() {
		return descripcionTipoDestinatario;
	}

	public void setDescripcionTipoDestinatario(
			java.lang.String descripcionTipoDestinatario) {
		this.descripcionTipoDestinatario = descripcionTipoDestinatario;
	}

	public java.lang.String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(java.lang.String destinatario) {
		this.destinatario = destinatario;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public java.lang.String getNombreAppRazonSocial() {
		return nombreAppRazonSocial;
	}

	public void setNombreAppRazonSocial(java.lang.String nombreAppRazonSocial) {
		this.nombreAppRazonSocial = nombreAppRazonSocial;
	}

	public java.lang.String getProcedimiento() {
		return procedimiento;
	}

	public void setProcedimiento(java.lang.String procedimiento) {
		this.procedimiento = procedimiento;
	}

	public java.lang.String getTipoDestinatario() {
		return tipoDestinatario;
	}

	public void setTipoDestinatario(java.lang.String tipoDestinatario) {
		this.tipoDestinatario = tipoDestinatario;
	}

	public int getRol() {
		return rol;
	}

	public void setRol(int rol) {
		this.rol = rol;
	}

	public java.util.Date getFechaFinDisponibilidad() {
		return fechaFinDisponibilidad;
	}

	public void setFechaFinDisponibilidad(java.util.Date fechaFinDisponibilidad) {
		this.fechaFinDisponibilidad = fechaFinDisponibilidad;
	}

	public java.util.Date getFechaPuestaDisposicion() {
		return fechaPuestaDisposicion;
	}

	public void setFechaPuestaDisposicion(java.util.Date fechaPuestaDisposicion) {
		this.fechaPuestaDisposicion = fechaPuestaDisposicion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id.getCodigo();
		result = prime
				* result
				+ ((descripcionEstado == null) ? 0 : descripcionEstado
						.hashCode());
		result = prime
				* result
				+ ((descripcionTipoDestinatario == null) ? 0
						: descripcionTipoDestinatario.hashCode());
		result = prime * result
				+ ((destinatario == null) ? 0 : destinatario.hashCode());
		result = prime * result + estado;
		result = prime
				* result
				+ ((fechaFinDisponibilidad == null) ? 0
						: fechaFinDisponibilidad.hashCode());
		result = prime
				* result
				+ ((fechaPuestaDisposicion == null) ? 0
						: fechaPuestaDisposicion.hashCode());
		result = prime
				* result
				+ ((nombreAppRazonSocial == null) ? 0 : nombreAppRazonSocial
						.hashCode());
		result = prime * result
				+ ((procedimiento == null) ? 0 : procedimiento.hashCode());
		result = prime * result + rol;
		result = prime
				* result
				+ ((tipoDestinatario == null) ? 0 : tipoDestinatario.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NotificacionSSVO other = (NotificacionSSVO) obj;		
		if (id.getCodigo() != other.id.getCodigo())
			return false;
		if (descripcionEstado == null) {
			if (other.descripcionEstado != null)
				return false;
		} else if (!descripcionEstado.equals(other.descripcionEstado))
			return false;
		if (descripcionTipoDestinatario == null) {
			if (other.descripcionTipoDestinatario != null)
				return false;
		} else if (!descripcionTipoDestinatario
				.equals(other.descripcionTipoDestinatario))
			return false;
		if (destinatario == null) {
			if (other.destinatario != null)
				return false;
		} else if (!destinatario.equals(other.destinatario))
			return false;
		if (estado != other.estado)
			return false;
		if (fechaFinDisponibilidad == null) {
			if (other.fechaFinDisponibilidad != null)
				return false;
		} else if (!formatFecha(fechaFinDisponibilidad).equals(formatFecha(other.fechaFinDisponibilidad)))
			return false;
		if (fechaPuestaDisposicion == null) {
			if (other.fechaPuestaDisposicion != null)
				return false;
		} else if (!formatFecha(fechaPuestaDisposicion).equals(formatFecha(other.fechaPuestaDisposicion)))
			return false;
		if (nombreAppRazonSocial == null) {
			if (other.nombreAppRazonSocial != null)
				return false;
		} else if (!nombreAppRazonSocial.equals(other.nombreAppRazonSocial))
			return false;
		if (procedimiento == null) {
			if (other.procedimiento != null)
				return false;
		} else if (!procedimiento.equals(other.procedimiento))
			return false;
		if (rol != other.rol)
			return false;
		if (tipoDestinatario == null) {
			if (other.tipoDestinatario != null)
				return false;
		} else if (!tipoDestinatario.equals(other.tipoDestinatario))
			return false;
		return true;
	}
	
	
	private Date formatFecha(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		
		return cal.getTime();
	}
	
}