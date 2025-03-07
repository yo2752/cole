package escrituras.beans;

import java.io.Serializable;

/**
 * Bean que almacena datos de la tabla TIPO_VIA.
 *
 */
public class TipoVia implements Serializable {

	 /**
	 * 
	 */
	private static final long serialVersionUID = 7782982791795083001L;

	private String idTipoVia;
	private String idTipoViaGata;
	private String nombre;
	private String obsoleto;
	private String idTipoViaDgt;
	 
	public TipoVia() {
		super();
	}

	 
	public TipoVia(String idTipoVia, String nombre) {
		super();
		this.idTipoVia = idTipoVia;
		this.nombre = nombre;
	}

	public String getIdTipoVia() {
		return idTipoVia;
	}

	public void setIdTipoVia(String idTipoVia) {
		this.idTipoVia = idTipoVia;
	}

	public String getIdTipoViaGata() {
		return idTipoViaGata;
	}

	public void setIdTipoViaGata(String idTipoViaGata) {
		this.idTipoViaGata = idTipoViaGata;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getObsoleto() {
		return obsoleto;
	}

	public void setObsoleto(String obsoleto) {
		this.obsoleto = obsoleto;
	}


	public String getIdTipoViaDgt() {
		return idTipoViaDgt;
	}

	public void setIdTipoViaDgt(String idTipoViaDgt) {
		this.idTipoViaDgt = idTipoViaDgt;
	}

	public String imprimir() {
		return "TipoVia [idTipoVia=" + idTipoVia + ", idTipoViaGata="
				+ idTipoViaGata + ", nombre=" + nombre + "]";
	}

	
}

	
