package general.acciones;

/**
 * Bean para definir las características básicas de un script que debe ser cargado dinámicamente. Tipo, posición, etc...
 * @author Santiago Cuenca
 *
 */
public class ScriptFeaturesBean {
	
	/* Inicio declaración de atributos */
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
