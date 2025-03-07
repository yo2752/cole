package org.gestoresmadrid.core.licencias.model.vo;

import java.io.Serializable;
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
 * The persistent class for the LC_INFO_EDIFICIO_ALTA database table.
 */
@Entity
@Table(name = "LC_INFO_EDIFICIO_ALTA")
public class LcInfoEdificioAltaVO implements Serializable {

	private static final long serialVersionUID = 2286108557759865667L;

	@Id
	@SequenceGenerator(name = "lc_info_edificio_alta", sequenceName = "LC_INFO_EDIFICIO_ALTA_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "lc_info_edificio_alta")
	@Column(name = "ID_INFO_EDIFICIO_ALTA")
	private Long idInfoEdificioAlta;

	// bi-directional One-to-many association to lcDatosPortalAlta
	@OneToMany(mappedBy = "lcInfoEdificioAlta")
	private Set<LcDatosPortalAltaVO> lcDatosPortalesAlta;
	
	@Column(name="ID_DIR_EDIFICACION_ALTA")
	private Long idLcDirEdificacionAlta;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_DIR_EDIFICACION_ALTA", insertable=false, updatable=false)
	private LcDireccionVO lcDirEdificacionAlta;

	@Column(name="ID_EDIFICACION")
	private Long idEdificacion;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_EDIFICACION", referencedColumnName = "ID_EDIFICACION", insertable = false, updatable = false)
	private LcEdificacionVO lcEdificacion;
	
	public List<LcDatosPortalAltaVO> getLcDatosPortalesAltaAsList() {
		List<LcDatosPortalAltaVO> lista;
		if (lcDatosPortalesAlta != null && lcDatosPortalesAlta.isEmpty()) {
			lista = new ArrayList<LcDatosPortalAltaVO>(lcDatosPortalesAlta);
		} else {
			lista = Collections.emptyList();
		}
		return lista;
	}
	
	public Long getIdInfoEdificioAlta() {
		return this.idInfoEdificioAlta;
	}

	public void setIdInfoEdificioAlta(Long idInfoEdificioAlta) {
		this.idInfoEdificioAlta = idInfoEdificioAlta;
	}

	public Set<LcDatosPortalAltaVO> getLcDatosPortalesAlta() {
		return lcDatosPortalesAlta;
	}

	public void setLcDatosPortalesAlta(Set<LcDatosPortalAltaVO> lcDatosPortalesAlta) {
		this.lcDatosPortalesAlta = lcDatosPortalesAlta;
	}

	public Long getIdLcDirEdificacionAlta() {
		return idLcDirEdificacionAlta;
	}

	public void setIdLcDirEdificacionAlta(Long idLcDirEdificacionAlta) {
		this.idLcDirEdificacionAlta = idLcDirEdificacionAlta;
	}

	public LcDireccionVO getLcDirEdificacionAlta() {
		return lcDirEdificacionAlta;
	}

	public void setLcDirEdificacionAlta(LcDireccionVO lcDirEdificacionAlta) {
		this.lcDirEdificacionAlta = lcDirEdificacionAlta;
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