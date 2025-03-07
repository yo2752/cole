package org.gestoresmadrid.core.direccion.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The persistent class for the MUNICIPIO database table.
 */
@Entity
@Table(name = "MUNICIPIO")
public class MunicipioVO implements Serializable {

	private static final long serialVersionUID = 3461279786336333434L;

	@EmbeddedId
	private MunicipioPK id;

	@Column(name = "CODIGO_POSTAL")
	private String codigoPostal;

	private String nombre;

	@Column(name = "OFICINA_LIQUIDADORA")
	private String oficinaLiquidadora;

	@ManyToOne
	@JoinColumn(name = "ID_PROVINCIA", insertable = false, updatable = false)
	private ProvinciaVO provincia;

	public MunicipioPK getId() {
		return this.id;
	}

	public void setId(MunicipioPK id) {
		this.id = id;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getOficinaLiquidadora() {
		return oficinaLiquidadora;
	}

	public void setOficinaLiquidadora(String oficinaLiquidadora) {
		this.oficinaLiquidadora = oficinaLiquidadora;
	}

	public ProvinciaVO getProvincia() {
		return provincia;
	}

	public void setProvincia(ProvinciaVO provincia) {
		this.provincia = provincia;
	}
}