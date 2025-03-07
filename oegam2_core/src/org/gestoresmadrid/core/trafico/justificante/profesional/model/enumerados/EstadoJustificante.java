package org.gestoresmadrid.core.trafico.justificante.profesional.model.enumerados;

import java.math.BigDecimal;

public enum EstadoJustificante {

	Iniciado("1", "Iniciado") {},
	Pendiente_DGT("2", "Pendiente DGT") {},
	Ok("3", "OK") {},
	Anulado("4", "Anulado") {},
	Pendiente_autorizacion_colegio("5", "Pendiente autorizacion colegio") {},
	Autorizado_icogam("6", "Autorizado por ICOGAM") {},
	Finalizado_Con_Error("11", "Finalizado con Error") {};

	private String valorEnum;
	private String nombreEnum;

	private EstadoJustificante(String valorEnum, String nombreEnum) {
		this.valorEnum = valorEnum;
		this.nombreEnum = nombreEnum;
	}

	public String getValorEnum() {
		return valorEnum;
	}

	public String getNombreEnum() {
		return nombreEnum;
	}

	public String toString() {
		return valorEnum;
	}

	public static EstadoJustificante convertir(String valorEnum) {
		for (EstadoJustificante estadoJustificante : EstadoJustificante.values()) {
			if (estadoJustificante.getValorEnum().equals(valorEnum)) {
				return estadoJustificante;
			}
		}
		return null;
	}

	public static EstadoJustificante convertir(BigDecimal valorEnum) {
		return valorEnum != null ? convertir(valorEnum.toString()) : null;
	}

	public static String convertirTexto(String valorEnum) {
		EstadoJustificante estadoJustificante = convertir(valorEnum);
		return estadoJustificante != null ? estadoJustificante.getNombreEnum() : null;
	}

}
