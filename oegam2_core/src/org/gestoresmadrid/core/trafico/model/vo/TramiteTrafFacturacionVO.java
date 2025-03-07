package org.gestoresmadrid.core.trafico.model.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "TRAMITE_TRAF_FACTURACION")
public class TramiteTrafFacturacionVO implements Serializable {

	private static final long serialVersionUID = 8683885019521258764L;

	@EmbeddedId
	private TramiteTrafFacturacionPK id;

	private String matricula;

	private String nif;

	private String bastidor;

	private Boolean generado;

	@Column(name = "NUM_COLEGIADO")
	private String numColegiado;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FECHA_APLICACION")
	private Date fechaAplicacion;

	@Column(name = "ID_CONTRATO")
	private Long idContrato;

	public TramiteTrafFacturacionPK getId() {
		return this.id;
	}

	public void setId(TramiteTrafFacturacionPK id) {
		this.id = id;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getBastidor() {
		return bastidor;
	}

	public void setBastidor(String bastidor) {
		this.bastidor = bastidor;
	}

	public Boolean getGenerado() {
		return generado;
	}

	public void setGenerado(Boolean generado) {
		this.generado = generado;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public Date getFechaAplicacion() {
		return fechaAplicacion;
	}

	public void setFechaAplicacion(Date fechaAplicacion) {
		this.fechaAplicacion = fechaAplicacion;
	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}
}