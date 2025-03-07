package general.acciones;

/**
 * Bean para definir las caracter�sticas b�sicas de un script que debe ser cargado din�micamente. Tipo, posici�n, etc...
 * @author Santiago Cuenca
 *
 */
public class ScriptFeaturesBean {
	
	/* Inicio declaraci�n de atributos */
	private String name;
	private TipoScript tipo;
	private TipoPosicionScript posicion;
	
	/* Getters y setters */
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public TipoScript getTipo() {
		return tipo;
	}
	public void setTipo(TipoScript tipo) {
		this.tipo = tipo;
	}
	public TipoPosicionScript getPosicion() {
		return posicion;
	}
	public void setPosicion(TipoPosicionScript posicion) {
		this.posicion = posicion;
	}

}
