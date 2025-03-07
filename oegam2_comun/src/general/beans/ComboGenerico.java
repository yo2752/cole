package general.beans;

/*
 * AMV.
 * Este bean se usar� para crear combos personalizados.
 * La select deber� devolver obligatoriamente dos campos:
 * - indice: ser� el key del combo.
 * - descripci�n: ser� la descripci�n del combo.
 */

public class ComboGenerico {
	private String indice; //El indice del combo.
	private String descripcion; //La descripci�n del combo.
	
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
