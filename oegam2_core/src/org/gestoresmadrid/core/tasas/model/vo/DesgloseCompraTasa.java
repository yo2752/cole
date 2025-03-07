package org.gestoresmadrid.core.tasas.model.vo;

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
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the DESGLOSE_COMPRA_TASAS database table.
 */
@Entity
@Table(name = "DESGLOSE_COMPRA_TASAS")
@NamedQuery(name = "DesgloseCompraTasa.findAll", query = "SELECT d FROM DesgloseCompraTasa d")
public class DesgloseCompraTasa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "desglose_compra_tasas_seq_gen")
	@SequenceGenerator(name = "desglose_compra_tasas_seq_gen", sequenceName = "SEC_DESGLOSE_SEQ")
	@Column(name = "ID_DESGLOSE")
	private Long idDesglose;

	@Column(name = "CANTIDAD")
	private BigDecimal cantidad;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CODIGO_TASA", referencedColumnName = "CODIGO_TASA")
	private TasaDgtVO tasaDgt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_COMPRA", referencedColumnName = "ID_COMPRA")
	private CompraTasaVO compraTasa;

	public DesgloseCompraTasa() {}

	public Long getIdDesglose() {
		return this.idDesglose;
	}

	public void setIdDesglose(Long idDesglose) {
		this.idDesglose = idDesglose;
	}

	public BigDecimal getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}

	public TasaDgtVO getTasaDgt() {
		return tasaDgt;
	}

	public void setTasaDgt(TasaDgtVO tasaDgt) {
		this.tasaDgt = tasaDgt;
	}

	public CompraTasaVO getCompraTasa() {
		return this.compraTasa;
	}

	public void setCompraTasa(CompraTasaVO compraTasa) {
		this.compraTasa = compraTasa;
	}

}