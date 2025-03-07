package org.gestoresmadrid.core.direccion.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Formula;

/**
 * The persistent class for the DIRECCION database table.
 */
@Entity
@Table(name = "DIRECCION")
public class DireccionVO implements Serializable, Cloneable {

	private static final long serialVersionUID = 5957391704220089682L;

	@Id
	@SequenceGenerator(name = "direccion_secuencia", sequenceName = "ID_DIRECCION_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "direccion_secuencia")
	@Column(name = "ID_DIRECCION", unique = true, nullable = false, precision = 22)
	private Long idDireccion;

	private String bloque;

	@Column(name = "COD_POSTAL")
	private String codPostal;

	@Column(name = "COD_POSTAL_CORREOS")
	private String codPostalCorreos;

	private String escalera;

	private BigDecimal hm;

	@Column(name = "ID_TIPO_VIA")
	private String idTipoVia;

	private BigDecimal km;

	private String letra;

	@Column(name = "NOMBRE_VIA")
	private String nombreVia;

	@Column(name = "NUM_LOCAL")
	private BigDecimal numLocal;

	@Column(name = "NUMERO_BIS")
	private String numeroBis;

	private String numero;

	private String planta;

	private String pueblo;

	@Column(name = "PUEBLO_CORREOS")
	private String puebloCorreos;

	private String puerta;

	@Column(name = "VIA_SINEDITAR")
	private String viaSineditar;

	@Column(name = "ID_PROVINCIA")
	private String idProvincia;

	@Column(name = "ID_MUNICIPIO")
	private String idMunicipio;

	@Column(name = "PAIS")
	private String pais;

	@Column(name = "REGION_EXTRANJERA")
	private String regionExtranjera;

	@Column(name = "PORTAL")
	private String portal;

	@Column(name="LUGAR_UBICACION")
	private String lugarUbicacion;

	@Transient
	private String nombreProvincia;

	@Transient
	private String nombreMunicipio;

	@Transient
	private String tipoViaDescripcion;

	@Transient
	private String asignarPrincipal;

	@Formula(value="ID_PROVINCIA||ID_MUNICIPIO")
	private String idProvMun;

	public String getIdProvMun() {
		return idProvMun;
	}

	public void setIdProvMun(String idProvMun) {
		this.idProvMun = idProvMun;
	}

	public Long getIdDireccion() {
		return idDireccion;
	}

	public void setIdDireccion(Long idDireccion) {
		this.idDireccion = idDireccion;
	}

	public String getBloque() {
		return bloque;
	}

	public void setBloque(String bloque) {
		this.bloque = bloque;
	}

	public String getCodPostal() {
		return codPostal;
	}

	public void setCodPostal(String codPostal) {
		this.codPostal = codPostal;
	}

	public String getCodPostalCorreos() {
		return codPostalCorreos;
	}

	public void setCodPostalCorreos(String codPostalCorreos) {
		this.codPostalCorreos = codPostalCorreos;
	}

	public String getEscalera() {
		return escalera;
	}

	public void setEscalera(String escalera) {
		this.escalera = escalera;
	}

	public BigDecimal getHm() {
		return hm;
	}

	public void setHm(BigDecimal hm) {
		this.hm = hm;
	}

	public String getIdTipoVia() {
		return idTipoVia;
	}

	public void setIdTipoVia(String idTipoVia) {
		this.idTipoVia = idTipoVia;
	}

	public BigDecimal getKm() {
		return km;
	}

	public void setKm(BigDecimal km) {
		this.km = km;
	}

	public String getLetra() {
		return letra;
	}

	public void setLetra(String letra) {
		this.letra = letra;
	}

	public String getNombreVia() {
		return nombreVia;
	}

	public void setNombreVia(String nombreVia) {
		this.nombreVia = nombreVia;
	}

	public BigDecimal getNumLocal() {
		return numLocal;
	}

	public void setNumLocal(BigDecimal numLocal) {
		this.numLocal = numLocal;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getPlanta() {
		return planta;
	}

	public void setPlanta(String planta) {
		this.planta = planta;
	}

	public String getPueblo() {
		return pueblo;
	}

	public void setPueblo(String pueblo) {
		this.pueblo = pueblo;
	}

	public String getPuebloCorreos() {
		return puebloCorreos;
	}

	public void setPuebloCorreos(String puebloCorreos) {
		this.puebloCorreos = puebloCorreos;
	}

	public String getPuerta() {
		return puerta;
	}

	public void setPuerta(String puerta) {
		this.puerta = puerta;
	}

	public String getViaSineditar() {
		return viaSineditar;
	}

	public void setViaSineditar(String viaSineditar) {
		this.viaSineditar = viaSineditar;
	}

	public String getIdProvincia() {
		return idProvincia;
	}

	public void setIdProvincia(String idProvincia) {
		this.idProvincia = idProvincia;
	}

	public String getIdMunicipio() {
		return idMunicipio;
	}

	public void setIdMunicipio(String idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	public String getNombreProvincia() {
		return nombreProvincia;
	}

	public void setNombreProvincia(String nombreProvincia) {
		this.nombreProvincia = nombreProvincia;
	}

	public String getNombreMunicipio() {
		return nombreMunicipio;
	}

	public void setNombreMunicipio(String nombreMunicipio) {
		this.nombreMunicipio = nombreMunicipio;
	}

	public String getTipoViaDescripcion() {
		return tipoViaDescripcion;
	}

	public void setTipoViaDescripcion(String tipoViaDescripcion) {
		this.tipoViaDescripcion = tipoViaDescripcion;
	}

	public String getAsignarPrincipal() {
		return asignarPrincipal;
	}

	public void setAsignarPrincipal(String asignarPrincipal) {
		this.asignarPrincipal = asignarPrincipal;
	}

	public Object clone() {
		Object obj = null;
		try {
			obj = super.clone();
		} catch (CloneNotSupportedException ex) {
			System.out.println(" no se puede duplicar");
		}
		return obj;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getNumeroBis() {
		return numeroBis;
	}

	public void setNumeroBis(String numeroBis) {
		this.numeroBis = numeroBis;
	}

	public String getRegionExtranjera() {
		return regionExtranjera;
	}

	public void setRegionExtranjera(String regionExtranjera) {
		this.regionExtranjera = regionExtranjera;
	}

	public String getPortal() {
		return portal;
	}

	public void setPortal(String portal) {
		this.portal = portal;
	}

	public String getLugarUbicacion() {
		return lugarUbicacion;
	}

	public void setLugarUbicacion(String lugarUbicacion) {
		this.lugarUbicacion = lugarUbicacion;
	}

}