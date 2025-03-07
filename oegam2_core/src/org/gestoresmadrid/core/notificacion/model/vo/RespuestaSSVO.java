package org.gestoresmadrid.core.notificacion.model.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "RESULTADO_NOTIFICACION")
public class RespuestaSSVO implements Serializable {

	private static final long serialVersionUID = -1709812581375959969L;
	
	public RespuestaSSVO() {
	}

	@Id
	@Column(name = "ID_RESULTADO")
	@SequenceGenerator(name = "secuencia_respuesta_ss", sequenceName = "RESPUESTA_SS_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "secuencia_respuesta_ss")
	private Integer idResultado;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumns({
        @JoinColumn(name="ID_CODIGO", nullable = true),
        @JoinColumn(name="NUM_COLEGIADO", nullable = true)
    })
	private NotificacionSSVO notificacion;

	@Column(name = "ESTADO")
	private String estado;
	
	@Column(name = "FECHA_NOTIFICACION")
	private Date fechaNotificacion;
	
	public Date getFechaNotificacion() {
		return fechaNotificacion;
	}

	public void setFechaNotificacion(Date fechaNotificacion) {
		this.fechaNotificacion = fechaNotificacion;
	}

	public Integer getIdResultado() {
		return idResultado;
	}

	public void setIdResultado(Integer idResultado) {
		this.idResultado = idResultado;
	}

	public NotificacionSSVO getNotificacion() {
		return notificacion;
	}

	public void setNotificacion(NotificacionSSVO notificacion) {
		this.notificacion = notificacion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

}