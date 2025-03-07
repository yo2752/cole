package org.gestoresmadrid.core.tasas.model.enumeration;

public enum TipoErrorCompraTasas {

	ERROR_GENERICO_WS("Error en la comunicaci�n con el WS.") {},
	ERROR_GENERICO_PRESUPUESTO("Ocurri� un error en la generaci�n del presupuesto, por lo que no se realiza la compra.") {},
	ERROR_GENERICO_PAGO("Ocurri� un error en la realizaci�n del pago.") {},
	ERROR_COMPRA_INEXISTENTE("No existe compra") {},
	ERROR_CANTIDADES_OBTENIDAS("La cantidad de tasas adquiridas no coincide con las solicitadas") {},
	ERROR_GENERICO_JUSTIFICANTEPAGO("Ocurri� un error al obtener el justificante de pago.") {},
	ERROR_FICHEROS_JUSTIFICANTEPAGO("No se pudo guardar el justificante de pago.") {},
	ERROR_GENERICO_RELACIONTASAS("Ocurri� un error al obtener la relaci�n de tasas.") {};

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
