package org.gestoresmadrid.core.registradores.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the CONVOCATORIA database table.
 */
@Embeddable
public class ConvocatoriaPK implements Serializable {

	private static final long serialVersionUID = -8168516717956425256L;

	@Column(name = "ID_TRAMITE_REGISTRO")
	private BigDecimal idTramiteRegistro;

	@Column(name = "ID_REUNION")
	private Long idReunion;

	public ConvocatoriaPK() {}

	public BigDecimal getIdTramiteRegistro() {
		return idTramiteRegistro;
	}

	public void setIdTramiteRegistro(BigDecimal idTramiteRegistro) {
		this.idTramiteRegistro = idTramiteRegistro;
	}

	public Long getIdReunion() {
		return idReunion;
	}

	public void setIdReunion(Long idReunion) {
		this.idReunion = idReunion;
	}
}