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

@Entity
@Table(name = "LC_EDIFICACION")
public class LcEdificacionVO implements Serializable {

	private static final long serialVersionUID = 117559099691232167L;

	@Id
	@SequenceGenerator(name = "lc_edificacion", sequenceName = "LC_EDIFICACION_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "lc_edificacion")
	@Column(name = "ID_EDIFICACION")
	private Long idEdificacion;

	@Column(name = "TIPO_EDIFICACION")
	private String tipoEdificacion;

	@Column(name = "NUM_EDIFICIOS")
	private BigDecimal numEdificios;

	@Column(name = "DESCRIPCION")
	private String descripcion;

	@Column(name = "TIPOLOGIA")
	private String tipologia;

	@Column(name = "TIPO_DEMOLICION")
	private String tipoDemolicion;

	@Column(name = "INDUSTRIAL")
	private String industrial;

	@Column(name = "EXP_PLAN_ESPECIAL_URBANISTICO")
	private String expPlanEspecialUrbanistico;

	@Column(name = "SUPERA_DOCE_MIL")
	private String superaDoceMil;

	@Column(name = "SUPERA_SEIS_MIL")
	private String superaSeisMil;

	@Column(name = "DOTACIONAL_EDIF")
	private BigDecimal dotacionalEnEdificio;

	@Column(name = "LIBRE_DISP_EDIF")
	private BigDecimal libreDisposicionEnEdificio;

	@Column(name = "DOTACIONAL_SUPERF")
	private BigDecimal dotacionalEnSuperficie;

	@Column(name = "LIBRE_DISP_SUPERF")
	private BigDecimal libreDisposicionEnSuperficie;

	@Column(name = "DOTACIONAL_DISCAP")
	private BigDecimal dotacionalDispacitados;

	@Column(name = "LIBRE_DISP_DISCAP")
	private BigDecimal libreDisposicionDiscapacitados;

	@OneToMany(mappedBy = "lcEdificacion")
	private Set<LcResumenEdificacionVO> lcResumenEdificacion;

	@OneToMany(mappedBy = "lcEdificacion")
	private Set<LcInfoEdificioAltaVO> lcInfoEdificiosAlta;

	@OneToMany(mappedBy = "lcEdificacion")
	private Set<LcInfoEdificioBajaVO> lcInfoEdificiosBaja;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NUM_EXPEDIENTE", referencedColumnName = "NUM_EXPEDIENTE", insertable = false, updatable = false)
	private LcTramiteVO lcTramite;

	@Column(name = "NUM_EXPEDIENTE")
	private BigDecimal numExpediente;

	public List<LcResumenEdificacionVO> getLcResumenEdificacionAsList() {
		List<LcResumenEdificacionVO> lista;
		if (lcResumenEdificacion != null && !lcResumenEdificacion.isEmpty()) {
			lista = new ArrayList<LcResumenEdificacionVO>(lcResumenEdificacion);
		} else {
			lista = Collections.emptyList();
		}
		return lista;
	}

	public List<LcInfoEdificioAltaVO> getLcInfoEdificioAltaAsList() {
		List<LcInfoEdificioAltaVO> lista;
		if (lcInfoEdificiosAlta != null && !lcInfoEdificiosAlta.isEmpty()) {
			lista = new ArrayList<LcInfoEdificioAltaVO>(lcInfoEdificiosAlta);
		} else {
			lista = Collections.emptyList();
		}
		return lista;
	}

	public List<LcInfoEdificioBajaVO> getLcInfoEdificiosBajaAsList() {
		List<LcInfoEdificioBajaVO> lista;
		if (lcInfoEdificiosBaja != null && !lcInfoEdificiosBaja.isEmpty()) {
			lista = new ArrayList<LcInfoEdificioBajaVO>(lcInfoEdificiosBaja);
		} else {
			lista = Collections.emptyList();
		}
		return lista;
	}

	public Long getIdEdificacion() {
		return idEdificacion;
	}

	public void setIdEdificacion(Long idEdificacion) {
		this.idEdificacion = idEdificacion;
	}

	public String getTipoEdificacion() {
		return tipoEdificacion;
	}

	public void setTipoEdificacion(String tipoEdificacion) {
		this.tipoEdificacion = tipoEdificacion;
	}

	public BigDecimal getNumEdificios() {
		return numEdificios;
	}

	public void setNumEdificios(BigDecimal numEdificios) {
		this.numEdificios = numEdificios;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTipologia() {
		return tipologia;
	}

	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}

	public String getTipoDemolicion() {
		return tipoDemolicion;
	}

	public void setTipoDemolicion(String tipoDemolicion) {
		this.tipoDemolicion = tipoDemolicion;
	}

	public String getIndustrial() {
		return industrial;
	}

	public void setIndustrial(String industrial) {
		this.industrial = industrial;
	}

	public String getExpPlanEspecialUrbanistico() {
		return expPlanEspecialUrbanistico;
	}

	public void setExpPlanEspecialUrbanistico(String expPlanEspecialUrbanistico) {
		this.expPlanEspecialUrbanistico = expPlanEspecialUrbanistico;
	}

	public String getSuperaDoceMil() {
		return superaDoceMil;
	}

	public void setSuperaDoceMil(String superaDoceMil) {
		this.superaDoceMil = superaDoceMil;
	}

	public String getSuperaSeisMil() {
		return superaSeisMil;
	}

	public void setSuperaSeisMil(String superaSeisMil) {
		this.superaSeisMil = superaSeisMil;
	}

	public BigDecimal getDotacionalEnEdificio() {
		return dotacionalEnEdificio;
	}

	public void setDotacionalEnEdificio(BigDecimal dotacionalEnEdificio) {
		this.dotacionalEnEdificio = dotacionalEnEdificio;
	}

	public BigDecimal getLibreDisposicionEnEdificio() {
		return libreDisposicionEnEdificio;
	}

	public void setLibreDisposicionEnEdificio(BigDecimal libreDisposicionEnEdificio) {
		this.libreDisposicionEnEdificio = libreDisposicionEnEdificio;
	}

	public BigDecimal getDotacionalEnSuperficie() {
		return dotacionalEnSuperficie;
	}

	public void setDotacionalEnSuperficie(BigDecimal dotacionalEnSuperficie) {
		this.dotacionalEnSuperficie = dotacionalEnSuperficie;
	}

	public BigDecimal getLibreDisposicionEnSuperficie() {
		return libreDisposicionEnSuperficie;
	}

	public void setLibreDisposicionEnSuperficie(BigDecimal libreDisposicionEnSuperficie) {
		this.libreDisposicionEnSuperficie = libreDisposicionEnSuperficie;
	}

	public BigDecimal getDotacionalDispacitados() {
		return dotacionalDispacitados;
	}

	public void setDotacionalDispacitados(BigDecimal dotacionalDispacitados) {
		this.dotacionalDispacitados = dotacionalDispacitados;
	}

	public BigDecimal getLibreDisposicionDiscapacitados() {
		return libreDisposicionDiscapacitados;
	}

	public void setLibreDisposicionDiscapacitados(BigDecimal libreDisposicionDiscapacitados) {
		this.libreDisposicionDiscapacitados = libreDisposicionDiscapacitados;
	}

	public Set<LcResumenEdificacionVO> getLcResumenEdificacion() {
		return lcResumenEdificacion;
	}

	public void setLcResumenEdificacion(Set<LcResumenEdificacionVO> lcResumenEdificacion) {
		this.lcResumenEdificacion = lcResumenEdificacion;
	}

	public Set<LcInfoEdificioAltaVO> getLcInfoEdificiosAlta() {
		return lcInfoEdificiosAlta;
	}

	public void setLcInfoEdificiosAlta(Set<LcInfoEdificioAltaVO> lcInfoEdificiosAlta) {
		this.lcInfoEdificiosAlta = lcInfoEdificiosAlta;
	}

	public Set<LcInfoEdificioBajaVO> getLcInfoEdificiosBaja() {
		return lcInfoEdificiosBaja;
	}

	public void setLcInfoEdificiosBaja(Set<LcInfoEdificioBajaVO> lcInfoEdificiosBaja) {
		this.lcInfoEdificiosBaja = lcInfoEdificiosBaja;
	}

	public LcTramiteVO getLcTramite() {
		return lcTramite;
	}

	public void setLcTramite(LcTramiteVO lcTramite) {
		this.lcTramite = lcTramite;
	}

	public BigDecimal getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(BigDecimal numExpediente) {
		this.numExpediente = numExpediente;
	}
}
