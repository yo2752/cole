package org.gestoresmadrid.core.trafico.model.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "USUARIO_ACCION")
public class UsuarioAccionVO implements Serializable {

	private static final long serialVersionUID = -1741232033840427493L;

	@Id
	@Column(name = "ID_USUARIO_ACCION")
	@SequenceGenerator(name = "usuario_accion_secuencia", sequenceName = "USUARIO_ACCION_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "usuario_accion_secuencia")
	private Long idUsuarioAccion;

	@Column(name = "FECHA")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;
	
	@Column(name = "NUM_EXPEDIENTE")
	private String numExpediente;

	@Column(name = "IP_ACCESO")
	private String ipAcceso;

	@Column(name = "ACCION")
	private String accion;

	@Column(name = "DATO")
	private String dato;

	public Long getIdUsuarioAccion() {
		return idUsuarioAccion;
	}

	public void setIdUsuarioAccion(Long idUsuarioAccion) {
		this.idUsuarioAccion = idUsuarioAccion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(String numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getIpAcceso() {
		return ipAcceso;
	}

	public void setIpAcceso(String ipAcceso) {
		this.ipAcceso = ipAcceso;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public String getDato() {
		return dato;
	}

	public void setDato(String dato) {
		this.dato = dato;
	}
}