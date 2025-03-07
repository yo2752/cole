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
 * The persistent class for the LC_PARTE_AUTONOMA database table.
 */
@Entity
@Table(name = "LC_PARTE_AUTONOMA")
public class LcParteAutonomaVO implements Serializable {

	private static final long serialVersionUID = 1129953923245032314L;

	@Id
	@SequenceGenerator(name = "lc_parte_autonoma", sequenceName = "LC_PARTE_AUTONOMA_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "lc_parte_autonoma")
	@Column(name = "ID_PARTE_AUTONOMA")
	private Long idParteAutonoma;

	@Column(name="DESCRIPCION")
	private String descripcion;

	@Column(name="NUMERO")
	private BigDecimal numero;

	@Column(name = "ID_DATOS_OBRA")
	private Long idDatosObra;

	// bi-directional many-to-one association to LcObra
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_DATOS_OBRA", referencedColumnName = "ID_DATOS_OBRA", insertable = false, updatable = false)
	private LcObraVO lcObra;

	public Long getIdParteAutonoma() {
		return this.idParteAutonoma;
	}

	public void setIdParteAutonoma(Long idParteAutonoma) {
		this.idParteAutonoma = idParteAutonoma;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public BigDecimal getNumero() {
		return this.numero;
	}

	public void setNumero(BigDecimal numero) {
		this.numero = numero;
	}

	public Long getIdDatosObra() {
		return idDatosObra;
	}

	public void setIdDatosObra(Long idDatosObra) {
		this.idDatosObra = idDatosObra;
	}

	public LcObraVO getLcObra() {
		return lcObra;
	}

	public void setLcObra(LcObraVO lcObra) {
		this.lcObra = lcObra;
	}

}