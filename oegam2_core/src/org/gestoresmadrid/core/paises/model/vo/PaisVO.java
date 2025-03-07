package org.gestoresmadrid.core.paises.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "PAIS")
public class PaisVO implements Serializable {

	private static final long serialVersionUID = -2215882093245642463L;

	/**
	 * 
	 */
	@Id
	@SequenceGenerator(name = "pais_secuencia", sequenceName = "ID_PAIS_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "pais_secuencia")
	@Column(name = "ID_PAIS")
	private Long idPais;
	
	@Column(name = "NOMBRE")
	private String nombre;
	
	@Column(name = "SIGLA1")
	private String sigla1;
	
	@Column(name = "SIGLA2")
	private String sigla2;
	
	@Column(name = "SIGLA3")
	private String sigla3;
	
	@Column(name = "TIPO_PAIS")
	private BigDecimal tipoPais;

	
	public PaisVO() {}


	public Long getIdPais() {
		return idPais;
	}


	public void setIdPais(Long idPais) {
		this.idPais = idPais;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	
	public String getSigla1() {
		return sigla1;
	}


	public void setSigla1(String sigla1) {
		this.sigla1 = sigla1;
	}
	

	public String getSigla2() {
		return sigla2;
	}


	public void setSigla2(String sigla2) {
		this.sigla2 = sigla2;
	}


	public String getSigla3() {
		return sigla3;
	}


	public void setSigla3(String sigla3) {
		this.sigla3 = sigla3;
	}


	public BigDecimal getTipoPais() {
		return tipoPais;
	}


	public void setTipoPais(BigDecimal tipoPais) {
		this.tipoPais = tipoPais;
	}
	
	
	
}
