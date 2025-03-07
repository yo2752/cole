package org.gestoresmadrid.core.registradores.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;


/**
 * The persistent class for the MAQUINARIA_REGISTRO database table.
 * 
 */
@Entity
@Table(name="MAQUINARIA_REGISTRO")
public class MaquinariaRegistroVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2492062110365113047L;

	@Id
	@SequenceGenerator(name = "maq_registro_secuencia", sequenceName = "MAQ_REGISTRO_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "maq_registro_secuencia")
	@Column(name="ID_MAQUINARIA")
	private long idMaquinaria;

	private String bloque;

	@Column(name="COD_MUNICIPIO")
	private String codMunicipio;

	@Column(name="COD_MUNICIPIO_DAT_REG")
	private String codMunicipioDatReg;

	@Column(name="COD_PROVINCIA")
	private String codProvincia;

	@Column(name="COD_PROVINCIA_DAT_REG")
	private String codProvinciaDatReg;

	@Column(name="COD_REGISTRO_PROPIEDAD")
	private String codRegistroPropiedad;

	@Column(name="COD_TIPOVIA")
	private String codTipoVia;

	@Column(name="CODIGO_POSTAL")
	private String codigoPostal;

	@Column(name="ENTIDAD_LOCAL_MENOR")
	private String entidadLocalMenor;

	private String escalera;

	private String grupo;

	private String idufir;

	@Column(name="LUGAR_UBICACION")
	private String lugarUbicacion;

	private String marca;

	private String modelo;

	@Column(name="NOMBRE_VIA")
	private String nombreVia;

	@Column(name="NUM_FINCA")
	private String numFinca;

	@Column(name="NUM_FINCA_BIS")
	private String numFincaBis;

	@Column(name="NUM_SERIE")
	private String numSerie;

	@Column(name="NUM_SUB_FINCA")
	private String numSubFinca;

	private String numero;

	@Column(name="NUMERO_BIS")
	private String numeroBis;

	private String pais = "ES";

	private String planta;

	private String portal;

	private String puerta;

	@Column(name="PUNTO_KILOMETRICO")
	private String puntoKilometrico;

	@Column(name="REFERENCIA_CATASTRAL")
	private String referenciaCatastral;

	@Column(name="REGION_PROVINCIA_EXTRANJERA")
	private String regionProvinciaExtranjera;

	@Column(name="SECCION_PROPIEDAD")
	private String seccionPropiedad;

	private String tipo;

	@Column(name = "ID_PROPIEDAD")
	private BigDecimal idPropiedad;
	
	@OneToOne
	@JoinColumn(name="ID_PROPIEDAD", insertable=false, updatable=false)
	private PropiedadVO propiedad;

	public MaquinariaRegistroVO() {
	}

	public long getIdMaquinaria() {
		return this.idMaquinaria;
	}

	public void setIdMaquinaria(long idMaquinaria) {
		this.idMaquinaria = idMaquinaria;
	}

	public String getBloque() {
		return this.bloque;
	}

	public void setBloque(String bloque) {
		this.bloque = bloque;
	}

	public String getCodMunicipio() {
		return this.codMunicipio;
	}

	public void setCodMunicipio(String codMunicipio) {
		this.codMunicipio = codMunicipio;
	}

	public String getCodMunicipioDatReg() {
		return this.codMunicipioDatReg;
	}

	public void setCodMunicipioDatReg(String codMunicipioDatReg) {
		this.codMunicipioDatReg = codMunicipioDatReg;
	}

	public String getCodProvincia() {
		return this.codProvincia;
	}

	public void setCodProvincia(String codProvincia) {
		this.codProvincia = codProvincia;
	}

	public String getCodProvinciaDatReg() {
		return this.codProvinciaDatReg;
	}

	public void setCodProvinciaDatReg(String codProvinciaDatReg) {
		this.codProvinciaDatReg = codProvinciaDatReg;
	}

	public String getCodRegistroPropiedad() {
		return this.codRegistroPropiedad;
	}

	public void setCodRegistroPropiedad(String codRegistroPropiedad) {
		this.codRegistroPropiedad = codRegistroPropiedad;
	}

	public String getCodTipoVia() {
		return this.codTipoVia;
	}

	public void setCodTipoVia(String codTipoVia) {
		this.codTipoVia = codTipoVia;
	}

	public String getCodigoPostal() {
		return this.codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getEntidadLocalMenor() {
		return this.entidadLocalMenor;
	}

	public void setEntidadLocalMenor(String entidadLocalMenor) {
		this.entidadLocalMenor = entidadLocalMenor;
	}

	public String getEscalera() {
		return this.escalera;
	}

	public void setEscalera(String escalera) {
		this.escalera = escalera;
	}

	public String getGrupo() {
		return this.grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public String getIdufir() {
		return this.idufir;
	}

	public void setIdufir(String idufir) {
		this.idufir = idufir;
	}

	public String getLugarUbicacion() {
		return this.lugarUbicacion;
	}

	public void setLugarUbicacion(String lugarUbicacion) {
		this.lugarUbicacion = lugarUbicacion;
	}

	public String getMarca() {
		return this.marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return this.modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getNombreVia() {
		return this.nombreVia;
	}

	public void setNombreVia(String nombreVia) {
		this.nombreVia = nombreVia;
	}

	public String getNumFinca() {
		return this.numFinca;
	}

	public void setNumFinca(String numFinca) {
		this.numFinca = numFinca;
	}

	public String getNumFincaBis() {
		return this.numFincaBis;
	}

	public void setNumFincaBis(String numFincaBis) {
		this.numFincaBis = numFincaBis;
	}

	public String getNumSerie() {
		return this.numSerie;
	}

	public void setNumSerie(String numSerie) {
		this.numSerie = numSerie;
	}

	public String getNumSubFinca() {
		return this.numSubFinca;
	}

	public void setNumSubFinca(String numSubFinca) {
		this.numSubFinca = numSubFinca;
	}

	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getNumeroBis() {
		return this.numeroBis;
	}

	public void setNumeroBis(String numeroBis) {
		this.numeroBis = numeroBis;
	}

	public String getPais() {
		return this.pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getPlanta() {
		return this.planta;
	}

	public void setPlanta(String planta) {
		this.planta = planta;
	}

	public String getPortal() {
		return this.portal;
	}

	public void setPortal(String portal) {
		this.portal = portal;
	}

	public String getPuerta() {
		return this.puerta;
	}

	public void setPuerta(String puerta) {
		this.puerta = puerta;
	}

	public String getPuntoKilometrico() {
		return this.puntoKilometrico;
	}

	public void setPuntoKilometrico(String puntoKilometrico) {
		this.puntoKilometrico = puntoKilometrico;
	}

	public String getReferenciaCatastral() {
		return this.referenciaCatastral;
	}

	public void setReferenciaCatastral(String referenciaCatastral) {
		this.referenciaCatastral = referenciaCatastral;
	}

	public String getRegionProvinciaExtranjera() {
		return this.regionProvinciaExtranjera;
	}

	public void setRegionProvinciaExtranjera(String regionProvinciaExtranjera) {
		this.regionProvinciaExtranjera = regionProvinciaExtranjera;
	}

	public String getSeccionPropiedad() {
		return this.seccionPropiedad;
	}

	public void setSeccionpPropiedad(String seccionPropiedad) {
		this.seccionPropiedad = seccionPropiedad;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public PropiedadVO getPropiedad() {
		return this.propiedad;
	}

	public void setPropiedad(PropiedadVO propiedad) {
		this.propiedad = propiedad;
	}

	public BigDecimal getIdPropiedad() {
		return idPropiedad;
	}

	public void setIdPropiedad(BigDecimal idPropiedad) {
		this.idPropiedad = idPropiedad;
	}

	public void setSeccionPropiedad(String seccionPropiedad) {
		this.seccionPropiedad = seccionPropiedad;
	}

}