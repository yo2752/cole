
package org.gestoresmadrid.oegam2comun.licenciasCam.restWS.response.objetos;

import java.io.Serializable;
import java.util.List;

public class Numeros implements Serializable {

	private static final long serialVersionUID = -1967933566697092807L;

	private List<Numero> numero;

	private Integer numeroNumeros;

	public List<Numero> getNumero() {
		return numero;
	}

	public void setNumero(List<Numero> numero) {
		this.numero = numero;
	}

	public Integer getNumeroNumeros() {
		return numeroNumeros;
	}

	public void setNumeroNumeros(Integer numeroNumeros) {
		this.numeroNumeros = numeroNumeros;
	}
}
