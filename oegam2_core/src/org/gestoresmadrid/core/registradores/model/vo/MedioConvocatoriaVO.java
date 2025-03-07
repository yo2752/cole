package org.gestoresmadrid.core.registradores.model.vo;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The persistent class for the MEDIO_CONVOCATORIA database table.
 */
@Entity
@Table(name = "MEDIO_CONVOCATORIA")
public class MedioConvocatoriaVO implements Serializable {

	private static final long serialVersionUID = 1674759043177162135L;

	@EmbeddedId
	private MedioConvocatoriaPK id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_MEDIO", referencedColumnName = "ID_MEDIO", insertable = false, updatable = false)
	private MedioVO medio;

	public MedioConvocatoriaPK getId() {
		return id;
	}

	public void setId(MedioConvocatoriaPK id) {
		this.id = id;
	}

	public MedioVO getMedio() {
		return medio;
	}

	public void setMedio(MedioVO medio) {
		this.medio = medio;
	}
}