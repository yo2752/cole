package org.gestoresmadrid.core.general.model.vo;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * The persistent class for the CONTRATO_APLICACION database table.
 */
@Entity
@Table(name = "CONTRATO_APLICACION")
public class ContratoAplicacionVO implements Serializable {

	private static final long serialVersionUID = -9051305647382336047L;

	@EmbeddedId
	private ContratoAplicacionPK id;

	public ContratoAplicacionVO() {}

	public ContratoAplicacionPK getId() {
		return id;
	}

	public void setId(ContratoAplicacionPK id) {
		this.id = id;
	}
}