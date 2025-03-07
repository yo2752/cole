package org.gestoresmadrid.core.general.model.vo;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The persistent class for the ENVIO_DATA database table.
 */
@Entity
@Table(name = "ENVIO_DATA")
public class EnvioDataVO implements Serializable {

	private static final long serialVersionUID = 8718044786408507274L;

	@EmbeddedId
	private EnvioDataPK id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_ENVIO")
	private Date fechaEnvio;

	@Column(name = "NUM_EXPEDIENTE")
	private BigDecimal numExpediente;

	private String respuesta;

	public EnvioDataVO() {}

	public EnvioDataPK getId() {
		return this.id;
	}

	public void setId(EnvioDataPK id) {
		this.id = id;
	}

	public Date getFechaEnvio() {
		return this.fechaEnvio;
	}

	public void setFechaEnvio(Date fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	public BigDecimal getNumExpediente() {
		return this.numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getRespuesta() {
		return this.respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
}