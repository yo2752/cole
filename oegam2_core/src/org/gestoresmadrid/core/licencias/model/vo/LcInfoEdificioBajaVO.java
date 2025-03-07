package org.gestoresmadrid.core.licencias.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the LC_INFO_EDIFICIO_BAJA database table.
 */
@Entity
@Table(name = "LC_INFO_EDIFICIO_BAJA")
public class LcInfoEdificioBajaVO implements Serializable {

	private static final long serialVersionUID = -673658962009254440L;

	@Id
	@SequenceGenerator(name = "lc_info_edificio_baja", sequenceName = "LC_INFO_EDIFICIO_BAJA_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "lc_info_edificio_baja")
	@Column(name = "ID_INFO_EDIFICIO_BAJA")
	private Long idInfoEdificioBaja;

	@Column(name = "NUM_EDIFICIO")
	private BigDecimal numEdificio;

	@Column(name="ID_DIR_EDIFICACION")
	private Long idLcDirEdificacion;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_DIR_EDIFICACION", insertable=false, updatable=false)
	private LcDireccionVO lcDirEdificacion;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_EDIFICACION", referencedColumnName = "ID_EDIFICACION", insertable = false, updatable = false)
	private LcEdificacionVO lcEdificacion;
	
	@Column(name = "ID_EDIFICACION")
	private Long idEdificacion;

	// bi-directional One-to-many association to LcDatosPlantaBaja
	@OneToMany(mappedBy = "lcInfoEdificioBaja")
	private Set<LcDatosPlantaBajaVO> lcDatosPlantasBaja;

	public List<LcDatosPlantaBajaVO> getLcIntervicnientesAsList() {
		List<LcDatosPlantaBajaVO> lista;
		if (lcDatosPlantasBaja != null && lcDatosPlantasBaja.isEmpty()) {
			lista = new ArrayList<LcDatosPlantaBajaVO>(lcDatosPlantasBaja);
		} else {
			lista = Collections.emptyList();
		}
		return lista;
	}
	
	public Long getIdInfoEdificioBaja() {
		return this.idInfoEdificioBaja;
	}

	public void setIdInfoEdificioBaja(Long idInfoEdificioBaja) {
		this.idInfoEdificioBaja = idInfoEdificioBaja;
	}

	public BigDecimal getNumEdificio() {
		return this.numEdificio;
	}

	public void setNumEdificio(BigDecimal numEdificio) {
		this.numEdificio = numEdificio;
	}

	public Set<LcDatosPlantaBajaVO> getLcDatosPlantasBaja() {
		return lcDatosPlantasBaja;
	}

	public void setLcDatosPlantasBaja(Set<LcDatosPlantaBajaVO> lcDatosPlantasBaja) {
		this.lcDatosPlantasBaja = lcDatosPlantasBaja;
	}

	public Long getIdLcDirEdificacion() {
		return idLcDirEdificacion;
	}

	public void setIdLcDirEdificacion(Long idLcDirEdificacion) {
		this.idLcDirEdificacion = idLcDirEdificacion;
	}

	public LcDireccionVO getLcDirEdificacion() {
		return lcDirEdificacion;
	}

	public void setLcDirEdificacion(LcDireccionVO lcDirEdificacion) {
		this.lcDirEdificacion = lcDirEdificacion;
	}

	public LcEdificacionVO getLcEdificacion() {
		return lcEdificacion;
	}

	public void setLcEdificacion(LcEdificacionVO lcEdificacion) {
		this.lcEdificacion = lcEdificacion;
	}

	public Long getIdEdificacion() {
		return idEdificacion;
	}

	public void setIdEdificacion(Long idEdificacion) {
		this.idEdificacion = idEdificacion;
	}


}