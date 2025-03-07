package org.gestoresmadrid.oegam2comun.trafico.estadisticas.ctit.diarias.bean;

import java.math.BigDecimal;

public class EstadisticasTransmisionBean {

	private String numColegiado;
	private BigDecimal media6Meses;
	private BigDecimal mediaDiaria;
	private BigDecimal mediaUltimaSemana;

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public BigDecimal getMedia6Meses() {
		return media6Meses;
	}

	public void setMedia6Meses(BigDecimal media6Meses) {
		this.media6Meses = media6Meses;
	}

	public BigDecimal getMediaDiaria() {
		return mediaDiaria;
	}

	public void setMediaDiaria(BigDecimal mediaDiaria) {
		this.mediaDiaria = mediaDiaria;
	}

	public BigDecimal getMediaUltimaSemana() {
		return mediaUltimaSemana;
	}

	public void setMediaUltimaSemana(BigDecimal mediaUltimaSemana) {
		this.mediaUltimaSemana = mediaUltimaSemana;
	}

}