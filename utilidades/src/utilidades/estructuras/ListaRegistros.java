package utilidades.estructuras;

import java.util.List;
import java.util.Map;

public class ListaRegistros {

	private List<Object> lista;
	private int tamano;
	// Mapa para guardar variables auxiliares:
	private Map<String, Object> mapa;

	public ListaRegistros() {
	}

	public ListaRegistros(List<Object> lista, int tamano) {
		super();
		this.lista = lista;
		this.tamano = tamano;
	}

	public List<Object> getLista() {
		return lista;
	}

	public void setLista(List<Object> lista) {
		this.lista = lista;
	}

	public int getTamano() {
		return tamano;
	}

	public void setTamano(int tamano) {
		this.tamano = tamano;
	}

	public Map<String, Object> getMapa() {
		return mapa;
	}

	public void setMapa(Map<String, Object> mapa) {
		this.mapa = mapa;
	}

}