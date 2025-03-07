package utilidades.basedatos.metadata;

public class BeanInfoColumna {

	private String nombre;
	private String tipo;
	private int tamanho;

	public String getNombre() {
		return nombre;
	}

	public void setNombreColumna(String nombreColumna) {
		this.nombre= nombreColumna;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getTamanho() {
		return tamanho;
	}

	public void setTamanho(int i) {
		this.tamanho = i;
	}

}
