package org.gestoresmadrid.core.direccion.model.vo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "MUNICIPIOS_SITES")
public class MunicipioSitesVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4079695272808755737L;
	@Id
	@Column(name = "ID_MUNICIPIO")
	private Integer idMunicipio;
	
	@Column(name = "NOMBRE")
	private String nombre;
	
	@ManyToOne
	@JoinColumn(name = "PROVINCIA", insertable = false, updatable = false)
	private ProvinciaVO provincia;
	
	@Column(name = "CODIGO_INE")
	private String codigoIne;
	

	public Integer getIdMunicipio() {
		return idMunicipio;
	}

	public void setIdMunicipio(Integer idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCodigoIne() {
		return codigoIne;
	}

	public void setCodigoIne(String codigoIne) {
		this.codigoIne = codigoIne;
	}

	public ProvinciaVO getProvincia() {
		return provincia;
	}

	public void setProvincia(ProvinciaVO provincia) {
		this.provincia = provincia;
	}

}
