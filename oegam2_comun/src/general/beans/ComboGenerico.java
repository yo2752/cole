package general.beans;

/*
 * AMV.
 * Este bean se usará para crear combos personalizados.
 * La select deberá devolver obligatoriamente dos campos:
 * - indice: será el key del combo.
 * - descripción: será la descripción del combo.
 */

public class ComboGenerico {
	private String indice; //El indice del combo.
	private String descripcion; //La descripción del combo.
	
	public String getIndice() {
		return indice;
	}
	public void setIndice(String indice) {
		this.indice = indice;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
