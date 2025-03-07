package org.gestoresmadrid.core.licencias.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the LC_DIRECCION database table.
 */
@Entity
@Table(name = "LC_DIRECCION")
public class LcDireccionVO implements Serializable {

	private static final long serialVersionUID = 379144774389081747L;

	@Id
	@SequenceGenerator(name = "lc_direccion", sequenceName = "LC_DIRECCION_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "lc_direccion")
	@Column(name = "ID_DIRECCION")
	private Long idDireccion;

	@Column(name = "CALIFICADOR")
	private String calificador;

	@Column(name = "CLASE_DIRECCION")
	private String claseDireccion;

	@Column(name = "TIPO_VIA")
	private String tipoVia;

	@Column(name = "COD_DIRECCION")
	private BigDecimal codDireccion;

	@Column(name = "ESCALERA")
	private String escalera;

	@Column(name = "NOMBRE_VIA")
	private String nombreVia;

	@Column(name = "NUM_CALLE")
	private BigDecimal numCalle;

	@Column(name = "PLANTA")
	private String planta;

	@Column(name = "PUERTA")
	private String puerta;

	@Column(name = "TIPO_NUMERACION")
	private String tipoNumeracion;

	@Column(name = "AMBITO")
	private String ambito;

	@Column(name = "COORDENADA_X")
	private String coordenadaX;

	@Column(name = "COORDENADA_Y")
	private String coordenadaY;

	@Column(name = "DISTRITO")
	private String distrito;

	@Column(name = "EMPL_NO_NORMALIZADO")
	private String emplNoNormalizado;

	@Column(name = "REFERENCIA_CATASTRAL")
	private String referenciaCatastral;

	@Column(name = "PAIS")
	private String pais;

	@Column(name = "PROVINCIA")
	private String provincia;

	@Column(name = "MUNICIPIO")
	private String municipio;

	@Column(name = "POBLACION")
	private String poblacion;

	@Column(name = "CODIGO_POSTAL")
	private String codigoPostal;

	@Column(name = "LOCAL")
	private String local;

	@Column(name = "COD_LOCAL")
	private String codLocal;

	public Long getIdDireccion() {
		return idDireccion;
	}

	public void setIdDireccion(Long idDireccion) {
		this.idDireccion = idDireccion;
	}

	public String getCalificador() {
		return calificador;
	}

	public void setCalificador(String calificador) {
		this.calificador = calificador;
	}

	public String getClaseDireccion() {
		return claseDireccion;
	}

	public void setClaseDireccion(String claseDireccion) {
		this.claseDireccion = claseDireccion;
	}

	public String getTipoVia() {
		return tipoVia;
	}

	public void setTipoVia(String tipoVia) {
		this.tipoVia = tipoVia;
	}

	public BigDecimal getCodDireccion() {
		return codDireccion;
	}

	public void setCodDireccion(BigDecimal codDireccion) {
		this.codDireccion = codDireccion;
	}

	public String getEscalera() {
		return escalera;
	}

	public void setEscalera(String escalera) {
		this.escalera = escalera;
	}

	public String getNombreVia() {
		return nombreVia;
	}

	public void setNombreVia(String nombreVia) {
		this.nombreVia = nombreVia;
	}

	public BigDecimal getNumCalle() {
		return numCalle;
	}

	public void setNumCalle(BigDecimal numCalle) {
		this.numCalle = numCalle;
	}

	public String getPlanta() {
		return planta;
	}

	public void setPlanta(String planta) {
		this.planta = planta;
	}

	public String getPuerta() {
		return puerta;
	}

	public void setPuerta(String puerta) {
		this.puerta = puerta;
	}

	public String getTipoNumeracion() {
		return tipoNumeracion;
	}

	public void setTipoNumeracion(String tipoNumeracion) {
		this.tipoNumeracion = tipoNumeracion;
	}

	public String getAmbito() {
		return ambito;
	}

	public void setAmbito(String ambito) {
		this.ambito = ambito;
	}

	public String getCoordenadaX() {
		return coordenadaX;
	}

	public void setCoordenadaX(String coordenadaX) {
		this.coordenadaX = coordenadaX;
	}

	public String getCoordenadaY() {
		return coordenadaY;
	}

	public void setCoordenadaY(String coordenadaY) {
		this.coordenadaY = coordenadaY;
	}

	public String getDistrito() {
		return distrito;
	}

	public void setDistrito(String distrito) {
		this.distrito = distrito;
	}

	public String getEmplNoNormalizado() {
		return emplNoNormalizado;
	}

	public void setEmplNoNormalizado(String emplNoNormalizado) {
		this.emplNoNormalizado = emplNoNormalizado;
	}

	public String getReferenciaCatastral() {
		return referenciaCatastral;
	}

	public void setReferenciaCatastral(String referenciaCatastral) {
		this.referenciaCatastral = referenciaCatastral;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getPoblacion() {
		return poblacion;
	}

	public void setPoblacion(String poblacion) {
		this.poblacion = poblacion;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getCodLocal() {
		return codLocal;
	}

	public void setCodLocal(String codLocal) {
		this.codLocal = codLocal;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

}