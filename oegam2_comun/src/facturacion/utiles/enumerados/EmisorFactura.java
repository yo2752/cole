package facturacion.utiles.enumerados;

public enum EmisorFactura {

	GESTORIA("GESTORIA"),
	GESTOR("GESTOR");

	private String nombre;

	private EmisorFactura(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}
}