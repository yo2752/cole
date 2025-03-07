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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the LC_INFO_LOCAL database table.
 */
@Entity
@Table(name = "LC_INFO_LOCAL")
public class LcInfoLocalVO implements Serializable {

	private static final long serialVersionUID = 5643176628795247644L;

	@Id
	@SequenceGenerator(name = "lc_info_local", sequenceName = "LC_INFO_LOCAL_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "lc_info_local")
	@Column(name = "ID_INFO_LOCAL")
	private Long idInfoLocal;

	@Column(name = "ACCESO_PRINCIPAL_IGUAL")
	private String accesoPrincipalIgual;

	@Column(name = "ACTIVIDAD_ANTERIOR")
	private String actividadAnterior;

	@Column(name = "COD_LOCAL")
	private String codLocal;

	@Column(name = "DESC_OTROS")
	private String descOtros;

	@Column(name = "DESCRIPCION_ACTIVIDAD")
	private String descripcionActividad;

	@Column(name="ESCALERA")
	private String escalera;

	@Column(name = "EXPEDIENTE_ACTIVIDAD_ANTERIOR")
	private String expedienteActividadAnterior;

	@Column(name = "ID_LOCAL")
	private BigDecimal idLocal;

	@Column(name="KWA")
	private BigDecimal kwa;

	@Column(name="LOCALIZACION")
	private String localizacion;

	@Column(name = "NOMBRE_AGRUPACION")
	private String nombreAgrupacion;

	@Column(name = "NUM_LOCAL")
	private String numLocal;

	@Column(name="PLANTA")
	private String planta;

	@Column(name = "POTENCIA_NOMINAL")
	private BigDecimal potenciaNominal;

	@Column(name = "PRESUPUESTO_OBRA_ACTIVIDAD")
	private BigDecimal presupuestoObraActividad;

	@Column(name="PUERTA")
	private String puerta;

	@Column(name = "REFERENCIA_CATASTRAL")
	private String referenciaCatastral;

	@Column(name = "ROTULO_SOLICITADO")
	private String rotuloSolicitado;

	@Column(name = "SOLICITA_ROTULO")
	private String solicitaRotulo;

	@Column(name = "SUJETA_A_OTROS")
	private String sujetaAOtros;

	@Column(name = "SUPERFICIE_UTIL_LOCAL")
	private BigDecimal superficieUtilLocal;

	@Column(name = "SUPERFICIE_UTIL_USO_PUBLICO")
	private BigDecimal superficieUtilUsoPublico;

	// bi-directional One-to-many association to DatoFirma
	@OneToMany(mappedBy = "lcInfoLocal")
	private Set<LcEpigrafeVO> lcEpigrafes;

	// bi-directional many-to-one association to LcDireccionCorta
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_DIR_INFO_LOCAL", insertable=false, updatable=false)
	private LcDireccionVO lcDirInfoLocal;
	
	@Column(name="ID_DIR_INFO_LOCAL")
	private Long idLcDirInfoLocal;


	public List<LcEpigrafeVO> getLcEpigrafesAsList() {
		List<LcEpigrafeVO> lista;
		if (lcEpigrafes != null && lcEpigrafes.isEmpty()) {
			lista = new ArrayList<LcEpigrafeVO>(lcEpigrafes);
		} else {
			lista = Collections.emptyList();
		}
		return lista;
	}
	
	public Long getIdInfoLocal() {
		return idInfoLocal;
	}

	public void setIdInfoLocal(Long idInfoLocal) {
		this.idInfoLocal = idInfoLocal;
	}

	public String getAccesoPrincipalIgual() {
		return accesoPrincipalIgual;
	}

	public void setAccesoPrincipalIgual(String accesoPrincipalIgual) {
		this.accesoPrincipalIgual = accesoPrincipalIgual;
	}

	public String getActividadAnterior() {
		return actividadAnterior;
	}

	public void setActividadAnterior(String actividadAnterior) {
		this.actividadAnterior = actividadAnterior;
	}

	public String getCodLocal() {
		return codLocal;
	}

	public void setCodLocal(String codLocal) {
		this.codLocal = codLocal;
	}

	public String getDescOtros() {
		return descOtros;
	}

	public void setDescOtros(String descOtros) {
		this.descOtros = descOtros;
	}

	public String getDescripcionActividad() {
		return descripcionActividad;
	}

	public void setDescripcionActividad(String descripcionActividad) {
		this.descripcionActividad = descripcionActividad;
	}

	public String getEscalera() {
		return escalera;
	}

	public void setEscalera(String escalera) {
		this.escalera = escalera;
	}

	public String getExpedienteActividadAnterior() {
		return expedienteActividadAnterior;
	}

	public void setExpedienteActividadAnterior(String expedienteActividadAnterior) {
		this.expedienteActividadAnterior = expedienteActividadAnterior;
	}

	public BigDecimal getIdLocal() {
		return idLocal;
	}

	public void setIdLocal(BigDecimal idLocal) {
		this.idLocal = idLocal;
	}

	public BigDecimal getKwa() {
		return kwa;
	}

	public void setKwa(BigDecimal kwa) {
		this.kwa = kwa;
	}

	public String getLocalizacion() {
		return localizacion;
	}

	public void setLocalizacion(String localizacion) {
		this.localizacion = localizacion;
	}

	public String getNombreAgrupacion() {
		return nombreAgrupacion;
	}

	public void setNombreAgrupacion(String nombreAgrupacion) {
		this.nombreAgrupacion = nombreAgrupacion;
	}

	public String getNumLocal() {
		return numLocal;
	}

	public void setNumLocal(String numLocal) {
		this.numLocal = numLocal;
	}

	public String getPlanta() {
		return planta;
	}

	public void setPlanta(String planta) {
		this.planta = planta;
	}

	public BigDecimal getPotenciaNominal() {
		return potenciaNominal;
	}

	public void setPotenciaNominal(BigDecimal potenciaNominal) {
		this.potenciaNominal = potenciaNominal;
	}

	public BigDecimal getPresupuestoObraActividad() {
		return presupuestoObraActividad;
	}

	public void setPresupuestoObraActividad(BigDecimal presupuestoObraActividad) {
		this.presupuestoObraActividad = presupuestoObraActividad;
	}

	public String getPuerta() {
		return puerta;
	}

	public void setPuerta(String puerta) {
		this.puerta = puerta;
	}

	public String getReferenciaCatastral() {
		return referenciaCatastral;
	}

	public void setReferenciaCatastral(String referenciaCatastral) {
		this.referenciaCatastral = referenciaCatastral;
	}

	public String getRotuloSolicitado() {
		return rotuloSolicitado;
	}

	public void setRotuloSolicitado(String rotuloSolicitado) {
		this.rotuloSolicitado = rotuloSolicitado;
	}

	public String getSolicitaRotulo() {
		return solicitaRotulo;
	}

	public void setSolicitaRotulo(String solicitaRotulo) {
		this.solicitaRotulo = solicitaRotulo;
	}

	public String getSujetaAOtros() {
		return sujetaAOtros;
	}

	public void setSujetaAOtros(String sujetaAOtros) {
		this.sujetaAOtros = sujetaAOtros;
	}

	public BigDecimal getSuperficieUtilLocal() {
		return superficieUtilLocal;
	}

	public void setSuperficieUtilLocal(BigDecimal superficieUtilLocal) {
		this.superficieUtilLocal = superficieUtilLocal;
	}

	public BigDecimal getSuperficieUtilUsoPublico() {
		return superficieUtilUsoPublico;
	}

	public void setSuperficieUtilUsoPublico(BigDecimal superficieUtilUsoPublico) {
		this.superficieUtilUsoPublico = superficieUtilUsoPublico;
	}

	public Set<LcEpigrafeVO> getLcEpigrafes() {
		return lcEpigrafes;
	}

	public void setLcEpigrafes(Set<LcEpigrafeVO> lcEpigrafes) {
		this.lcEpigrafes = lcEpigrafes;
	}

	public LcDireccionVO getLcDirInfoLocal() {
		return lcDirInfoLocal;
	}

	public void setLcDirInfoLocal(LcDireccionVO lcDirInfoLocal) {
		this.lcDirInfoLocal = lcDirInfoLocal;
	}

	public Long getIdLcDirInfoLocal() {
		return idLcDirInfoLocal;
	}

	public void setIdLcDirInfoLocal(Long idLcDirInfoLocal) {
		this.idLcDirInfoLocal = idLcDirInfoLocal;
	}

}