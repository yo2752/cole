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
 * The persistent class for the LC_DATOS_PLANTA_ALTA database table.
 */
@Entity
@Table(name = "LC_DATOS_PLANTA_ALTA")
public class LcDatosPlantaAltaVO implements Serializable {

	private static final long serialVersionUID = -7045802661559587464L;

	@Id
	@SequenceGenerator(name = "lc_planta_alta", sequenceName = "LC_DATOS_PLANTA_ALTA_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "lc_planta_alta")
	@Column(name = "ID_DATOS_PLANTA_ALTA")
	private Long idDatosPlantaAlta;

	@Column(name = "ALTURA_LIBRE")
	private BigDecimal alturaLibre;

	@Column(name = "ALTURA_PISO")
	private BigDecimal alturaPiso;

	@Column(name = "NUM_PLANTA")
	private String numPlanta;

	@Column(name = "NUM_UNIDADES")
	private BigDecimal numUnidades;

	@Column(name = "SUP_COMPUTABLE")
	private BigDecimal supComputable;

	@Column(name = "SUP_CONSTRUIDA")
	private BigDecimal supConstruida;

	@Column(name = "TIPO_PLANTA")
	private String tipoPlanta;

	@Column(name = "USO_PLANTA")
	private String usoPlanta;

	// bi-directional One-to-many association to LcSupNoComputablePlanta
	@OneToMany(mappedBy = "lcDatosPlantaAlta")
	private Set<LcSupNoComputablePlantaVO> lcSupNoComputablesPlanta;

	@Column(name = "ID_DATOS_PORTAL_ALTA")
	private Long idDatosPortalAlta;

	// bi-directional many-to-one association to LcDatosPortalAlta
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_DATOS_PORTAL_ALTA", referencedColumnName = "ID_DATOS_PORTAL_ALTA", insertable = false, updatable = false)
	private LcDatosPortalAltaVO lcDatosPortalAlta;

	public List<LcSupNoComputablePlantaVO> getLcSupNoComputablesPlantaAsList() {
		List<LcSupNoComputablePlantaVO> lista;
		if (lcSupNoComputablesPlanta != null && lcSupNoComputablesPlanta.isEmpty()) {
			lista = new ArrayList<LcSupNoComputablePlantaVO>(lcSupNoComputablesPlanta);
		} else {
			lista = Collections.emptyList();
		}
		return lista;
	}
	
	public Long getIdDatosPlantaAlta() {
		return this.idDatosPlantaAlta;
	}

	public void setIdDatosPlantaAlta(Long idDatosPlantaAlta) {
		this.idDatosPlantaAlta = idDatosPlantaAlta;
	}

	public BigDecimal getAlturaLibre() {
		return this.alturaLibre;
	}

	public void setAlturaLibre(BigDecimal alturaLibre) {
		this.alturaLibre = alturaLibre;
	}

	public BigDecimal getAlturaPiso() {
		return this.alturaPiso;
	}

	public void setAlturaPiso(BigDecimal alturaPiso) {
		this.alturaPiso = alturaPiso;
	}

	public BigDecimal getNumUnidades() {
		return this.numUnidades;
	}

	public void setNumUnidades(BigDecimal numUnidades) {
		this.numUnidades = numUnidades;
	}

	public BigDecimal getSupComputable() {
		return this.supComputable;
	}

	public void setSupComputable(BigDecimal supComputable) {
		this.supComputable = supComputable;
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

	public String getUsoPlanta() {
		return this.usoPlanta;
	}

	public void setUsoPlanta(String usoPlanta) {
		this.usoPlanta = usoPlanta;
	}

	public Set<LcSupNoComputablePlantaVO> getLcSupNoComputablesPlanta() {
		return lcSupNoComputablesPlanta;
	}

	public void setLcSupNoComputablesPlanta(Set<LcSupNoComputablePlantaVO> lcSupNoComputablesPlanta) {
		this.lcSupNoComputablesPlanta = lcSupNoComputablesPlanta;
	}

	public Long getIdDatosPortalAlta() {
		return idDatosPortalAlta;
	}

	public void setIdDatosPortalAlta(Long idDatosPortalAlta) {
		this.idDatosPortalAlta = idDatosPortalAlta;
	}

	public LcDatosPortalAltaVO getLcDatosPortalAlta() {
		return lcDatosPortalAlta;
	}

	public void setLcDatosPortalAlta(LcDatosPortalAltaVO lcDatosPortalAlta) {
		this.lcDatosPortalAlta = lcDatosPortalAlta;
	}

	public String getNumPlanta() {
		return numPlanta;
	}

	public void setNumPlanta(String numPlanta) {
		this.numPlanta = numPlanta;
	}

}