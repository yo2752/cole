package org.gestoresmadrid.core.registradores.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
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

/**
 * The persistent class for the REGISTRO_FUERA_SECUENCIA database table.
 */
@Entity
@Table(name = "REGISTRO_FUERA_SECUENCIA")
public class RegistroFueraSecuenciaVO implements Serializable {

	private static final long serialVersionUID = 1296917991504513618L;

	@Id
	@SequenceGenerator(name = "registro_fuera_secuencia", sequenceName = "REGISTRO_FUERA_SECUENCIA_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "registro_fuera_secuencia")
	@Column(name = "ID_REGISTRO_FUERA_SECUENCIA")
	private Long idRegistroFueraSecuencia;

	@Column(name = "ESTADO")
	private BigDecimal estado;

	@Column(name = "TIPO_MENSAJE")
	private String tipoMensaje;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_ALTA")
	private Date fechaAlta;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_ENVIO")
	private Date fechaEnvio;

	@Column(name = "ID_USUARIO")
	private BigDecimal idUsuario;

	@Column(name = "ID_TRAMITE_REGISTRO")
	private BigDecimal idTramiteRegistro;

	public Long getIdRegistroFueraSecuencia() {
		return idRegistroFueraSecuencia;
	}

	public void setIdRegistroFueraSecuencia(Long idRegistroFueraSecuencia) {
		this.idRegistroFueraSecuencia = idRegistroFueraSecuencia;
	}

	public BigDecimal getEstado() {
		return estado;
	}

	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}

	public String getTipoMensaje() {
		return tipoMensaje;
	}

	public void setTipoMensaje(String tipoMensaje) {
		this.tipoMensaje = tipoMensaje;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(Date fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	public BigDecimal getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(BigDecimal idUsuario) {
		this.idUsuario = idUsuario;
	}

	public BigDecimal getIdTramiteRegistro() {
		return idTramiteRegistro;
	}

	public void setIdTramiteRegistro(BigDecimal idTramiteRegistro) {
		this.idTramiteRegistro = idTramiteRegistro;
	}
}