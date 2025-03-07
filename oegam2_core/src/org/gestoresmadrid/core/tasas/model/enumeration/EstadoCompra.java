package org.gestoresmadrid.core.tasas.model.enumeration;

import java.math.BigDecimal;

public enum EstadoCompra {

	INICIADO(0, "Iniciado"),
	ANULADO(3, "Anulado"),
	PENDIENTE_WS(10, "Pendiente de respuesta de la DGT"),
	FINALIZADO_ERROR(11, "Finalizado con error"),
	FINALIZADO_OK(12, "Finalizado OK");

	private int codigo;
	private String descripcion;

	private EstadoCompra(int codigo, String descripcion) {
		this.codigo = codigo;
		this.descripcion = descripcion;
	}

	public static EstadoCompra convertir(int codigo) {
		for (EstadoCompra element : EstadoCompra.values()) {
			if (element.codigo == codigo) {
				return element;
			}
		}
		return null;
	}

	public static EstadoCompra convertir(BigDecimal codigo) {
		return codigo != null ? convertir(codigo.intValue()) : null;
	}

	public static EstadoCompra convertir(String codigo) {
		return codigo != null ? convertir(Integer.parseInt(codigo)) : null;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	@Override
	public String toString() {
		return descripcion;
	}
}
