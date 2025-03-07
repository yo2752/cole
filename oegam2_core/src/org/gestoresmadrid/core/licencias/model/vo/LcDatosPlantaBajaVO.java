package org.gestoresmadrid.core.licencias.model.vo;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;

/**
 * The persistent class for the LC_DATOS_PLANTA_BAJA database table.
 */
@Entity
@Table(name = "LC_DATOS_PLANTA_BAJA")
public class LcDatosPlantaBajaVO implements Serializable {

	private static final long serialVersionUID = -7822223082403803875L;

	@Id
	@SequenceGenerator(name = "lc_planta_baja", sequenceName = "LC_DATOS_PLANTA_BAJA_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "lc_planta_baja")
	@Column(name = "ID_DATOS_PLANTA_BAJA")
	private Long idDatosPlantaBaja;

	@Column(name = "SUP_CONSTRUIDA")
	private BigDecimal supConstruida;

	@Column(name = "TIPO_PLANTA")
	private String tipoPlanta;

	@Column(name = "ID_INFO_EDIFICIO_BAJA")
	private Long idInfoEdificioBaja;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_INFO_EDIFICIO_BAJA", referencedColumnName = "ID_INFO_EDIFICIO_BAJA", insertable = false, updatable = false)
	private LcInfoEdificioBajaVO lcInfoEdificioBaja;

	public Long getIdDatosPlantaBaja() {
		return this.idDatosPlantaBaja;
	}

	public void setIdDatosPlantaBaja(Long idDatosPlantaBaja) {
		this.idDatosPlantaBaja = idDatosPlantaBaja;
	}

	public BigDecimal getSupConstruida() {
		return this.supConstruida;
	}

	public void setSupConstruida(BigDecimal supConstruida) {
		this.supConstruida = supConstruida;
	}

	public String getTipoPlanta() {
		return this.tipoPlanta;
	}

	public void setTipoPlanta(String tipoPlanta) {
		this.tipoPlanta = tipoPlanta;
	}

	public Long getIdInfoEdificioBaja() {
		return idInfoEdificioBaja;
	}

	public void setIdInfoEdificioBaja(Long idInfoEdificioBaja) {
		this.idInfoEdificioBaja = idInfoEdificioBaja;
	}

	public LcInfoEdificioBajaVO getLcInfoEdificioBaja() {
		return lcInfoEdificioBaja;
	}

	public void setLcInfoEdificioBaja(LcInfoEdificioBajaVO lcInfoEdificioBaja) {
		this.lcInfoEdificioBaja = lcInfoEdificioBaja;
	}

}