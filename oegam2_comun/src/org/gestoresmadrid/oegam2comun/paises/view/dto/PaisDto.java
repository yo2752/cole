package org.gestoresmadrid.oegam2comun.paises.view.dto;

import java.io.Serializable;
import java.math.BigDecimal;



public class PaisDto implements Serializable {

	
	private static final long serialVersionUID = 7688904015569700060L;
	
	private Long idPais;

	private String nombre;

	private String sigla2;
	
	private String sigla3;

	private BigDecimal tipoPais;
	
	
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
