package org.gestoresmadrid.core.tasas.model.enumeration;

public enum TipoErrorCompraTasas {

	ERROR_GENERICO_WS("Error en la comunicación con el WS.") {},
	ERROR_GENERICO_PRESUPUESTO("Ocurrió un error en la generación del presupuesto, por lo que no se realiza la compra.") {},
	ERROR_GENERICO_PAGO("Ocurrió un error en la realización del pago.") {},
	ERROR_COMPRA_INEXISTENTE("No existe compra") {},
	ERROR_CANTIDADES_OBTENIDAS("La cantidad de tasas adquiridas no coincide con las solicitadas") {},
	ERROR_GENERICO_JUSTIFICANTEPAGO("Ocurrió un error al obtener el justificante de pago.") {},
	ERROR_FICHEROS_JUSTIFICANTEPAGO("No se pudo guardar el justificante de pago.") {},
	ERROR_GENERICO_RELACIONTASAS("Ocurrió un error al obtener la relación de tasas.") {};

	private String descripcion;

	private TipoErrorCompraTasas(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	@Override
	public String toString() {
		return descripcion;
	}

}
