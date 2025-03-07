package org.gestoresmadrid.core.registradores.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the MEDIO database table.
 */
@Entity
@Table(name = "MEDIO")
public class MedioVO implements Serializable {

	private static final long serialVersionUID = -7099132180693970884L;

	@Id
	@SequenceGenerator(name = "medio_secuencia", sequenceName = "ID_MEDIO_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "medio_secuencia")
	@Column(name = "ID_MEDIO")
	private Long idMedio;

	@Column(name = "DESC_MEDIO")
	private String descMedio;

	@Column(name = "TIPO_MEDIO")
	private String tipoMedio;

	public Long getIdMedio() {
		return idMedio;
	}

	public void setIdMedio(Long idMedio) {
		this.idMedio = idMedio;
	}

	public String getDescMedio() {
		return descMedio;
	}

	public void setDescMedio(String descMedio) {
		this.descMedio = descMedio;
	}

	public String getTipoMedio() {
		return tipoMedio;
	}

	public void setTipoMedio(String tipoMedio) {
		this.tipoMedio = tipoMedio;
	}
}