package org.gestoresmadrid.oegam2comun.licenciasCam.restWS.response.objetos;

import java.io.Serializable;

public class BloqueVial implements Serializable {

	private static final long serialVersionUID = 2395092060176447720L;

	private Integer codVial;

	private Integer codVial5;

	private String nomClase;

	private String nomVial;

	private Integer estadoNumero;

	private BloqueVialFinDTO bloqueVialFinDTO;

	private BloqueVialNoFinDTO bloqueVialNoFinDTO;

	public Integer getCodVial() {
		return codVial;
	}

	public void setCodVial(Integer codVial) {
		this.codVial = codVial;
	}

	public Integer getCodVial5() {
		return codVial5;
	}

	public void setCodVial5(Integer codVial5) {
		this.codVial5 = codVial5;
	}

	public String getNomClase() {
		return nomClase;
	}

	public void setNomClase(String nomClase) {
		this.nomClase = nomClase;
	}

	public String getNomVial() {
		return nomVial;
	}

	public void setNomVial(String nomVial) {
		this.nomVial = nomVial;
	}

	public Integer getEstadoNumero() {
		return estadoNumero;
	}

	public void setEstadoNumero(Integer estadoNumero) {
		this.estadoNumero = estadoNumero;
	}

	public BloqueVialFinDTO getBloqueVialFinDTO() {
		return bloqueVialFinDTO;
	}

	public void setBloqueVialFinDTO(BloqueVialFinDTO bloqueVialFinDTO) {
		this.bloqueVialFinDTO = bloqueVialFinDTO;
	}

	public BloqueVialNoFinDTO getBloqueVialNoFinDTO() {
		return bloqueVialNoFinDTO;
	}

	public void setBloqueVialNoFinDTO(BloqueVialNoFinDTO bloqueVialNoFinDTO) {
		this.bloqueVialNoFinDTO = bloqueVialNoFinDTO;
	}
}
