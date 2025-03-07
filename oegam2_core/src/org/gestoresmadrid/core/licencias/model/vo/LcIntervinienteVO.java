package org.gestoresmadrid.core.licencias.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the LC_INTERVINIENTE database table.
 */
@Entity
@Table(name = "LC_INTERVINIENTE")
public class LcIntervinienteVO implements Serializable {

	private static final long serialVersionUID = -6186255389183316466L;

	@Id
	@SequenceGenerator(name = "lc_interviniente", sequenceName = "LC_INTERVINIENTE_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "lc_interviniente")
	@Column(name = "ID_INTERVINIENTE")
	private Long idInterviniente;

	@Column(name = "TIPO_INTERVINIENTE")
	private String tipoInterviniente;

	@Column(name = "NUM_COLEGIADO")
	private String numColegiado;

	@Column(name = "NUM_EXPEDIENTE")
	private BigDecimal numExpediente;

	@Column(name = "ID_DIRECCION")
	private Long idDireccion;

	@Column(name = "ID_PERSONA")
	private Long idPersona;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_PERSONA", referencedColumnName = "ID_PERSONA", insertable = false, updatable = false)
	private LcPersonaVO lcPersona;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NUM_EXPEDIENTE", referencedColumnName = "NUM_EXPEDIENTE", insertable = false, updatable = false)
	private LcTramiteVO lcIntervinientes;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_DIRECCION", referencedColumnName = "ID_DIRECCION", insertable = false, updatable = false)
	private LcDireccionVO lcDireccion;

	public Long getIdInterviniente() {
		return this.idInterviniente;
	}

	public void setIdInterviniente(Long idInterviniente) {
		this.idInterviniente = idInterviniente;
	}

	public String getTipoInterviniente() {
		return tipoInterviniente;
	}

	public void setTipoInterviniente(String tipoInterviniente) {
		this.tipoInterviniente = tipoInterviniente;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}

	public LcPersonaVO getLcPersona() {
		return lcPersona;
	}

	public void setLcPersona(LcPersonaVO lcPersona) {
		this.lcPersona = lcPersona;
	}

	public LcTramiteVO getLcIntervinientes() {
		return lcIntervinientes;
	}

	public void setLcIntervinientes(LcTramiteVO lcIntervinientes) {
		this.lcIntervinientes = lcIntervinientes;
	}

	public Long getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}

	public Long getIdDireccion() {
		return idDireccion;
	}

	public void setIdDireccion(Long idDireccion) {
		this.idDireccion = idDireccion;
	}

	public LcDireccionVO getLcDireccion() {
		return lcDireccion;
	}

	public void setLcDireccion(LcDireccionVO lcDireccion) {
		this.lcDireccion = lcDireccion;
	}
}