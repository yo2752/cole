package org.gestoresmadrid.core.personas.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.gestoresmadrid.core.general.model.vo.UsuarioVO;

/**
 * The persistent class for the EVOLUCION_PERSONA database table.
 */
@Entity
@Table(name = "EVOLUCION_PERSONA")
public class EvolucionPersonaVO implements Serializable {

	private static final long serialVersionUID = -1172723796271640667L;

	@EmbeddedId
	private EvolucionPersonaPK id;

	@Column(name = "APELLIDO1_ANT")
	private String apellido1Ant;

	@Column(name = "APELLIDO1_NUE")
	private String apellido1Nue;

	@Column(name = "APELLIDO2_ANT")
	private String apellido2Ant;

	@Column(name = "APELLIDO2_NUE")
	private String apellido2Nue;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_NACIMIENTO_ANT")
	private Date fechaNacimientoAnt;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_NACIMIENTO_NUE")
	private Date fechaNacimientoNue;

	@Column(name = "NOMBRE_ANT")
	private String nombreAnt;

	@Column(name = "NOMBRE_NUE")
	private String nombreNue;

	@Column(name = "NUM_EXPEDIENTE")
	private BigDecimal numExpediente;

	private String otros;

	@Column(name = "TIPO_ACTUACION")
	private String tipoActuacion;

	@Column(name = "TIPO_TRAMITE")
	private String tipoTramite;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_USUARIO")
	private UsuarioVO usuario;

	public EvolucionPersonaPK getId() {
		return this.id;
	}

	public void setId(EvolucionPersonaPK id) {
		this.id = id;
	}

	public String getApellido1Ant() {
		return apellido1Ant;
	}

	public void setApellido1Ant(String apellido1Ant) {
		this.apellido1Ant = apellido1Ant;
	}

	public String getApellido1Nue() {
		return apellido1Nue;
	}

	public void setApellido1Nue(String apellido1Nue) {
		this.apellido1Nue = apellido1Nue;
	}

	public String getApellido2Ant() {
		return apellido2Ant;
	}

	public void setApellido2Ant(String apellido2Ant) {
		this.apellido2Ant = apellido2Ant;
	}

	public String getApellido2Nue() {
		return apellido2Nue;
	}

	public void setApellido2Nue(String apellido2Nue) {
		this.apellido2Nue = apellido2Nue;
	}

	public Date getFechaNacimientoAnt() {
		return fechaNacimientoAnt;
	}

	public void setFechaNacimientoAnt(Date fechaNacimientoAnt) {
		this.fechaNacimientoAnt = fechaNacimientoAnt;
	}

	public Date getFechaNacimientoNue() {
		return fechaNacimientoNue;
	}

	public void setFechaNacimientoNue(Date fechaNacimientoNue) {
		this.fechaNacimientoNue = fechaNacimientoNue;
	}

	public String getNombreAnt() {
		return nombreAnt;
	}

	public void setNombreAnt(String nombreAnt) {
		this.nombreAnt = nombreAnt;
	}

	public String getNombreNue() {
		return nombreNue;
	}

	public void setNombreNue(String nombreNue) {
		this.nombreNue = nombreNue;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getOtros() {
		return otros;
	}

	public void setOtros(String otros) {
		this.otros = otros;
	}

	public String getTipoActuacion() {
		return tipoActuacion;
	}

	public void setTipoActuacion(String tipoActuacion) {
		this.tipoActuacion = tipoActuacion;
	}

	public String getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public UsuarioVO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioVO usuario) {
		this.usuario = usuario;
	}
}