package administracion.decorators;

import hibernate.entities.general.Property;

import org.displaytag.decorator.TableDecorator;

public class PropiedadesDecorator extends TableDecorator {

	public String getModificar() {
		String nombre = "";
		nombre = ((Property)this.getCurrentRowObject()).getNombre();
		long id = ((Property)this.getCurrentRowObject()).getId();
		String devolucion = "<img src=\"img/icono-guardar.jpg\" id=\"guardarPropiedad_" + id + "\" " + 
			" onmouseover=\"dameInfoSinAjax('mostrar', this.id, 'modificarPropiedad')\" onmouseout=\"dameInfoSinAjax('ocultar', this.id, '')\" " + 
				" onclick=\"modificarPropiedad('" + nombre + "|" + id + "')\" class=\"botonesInfo\" />";
		return devolucion;
	}

	public String getValor() {
		String nombre = "";
		nombre = ((Property)this.getCurrentRowObject()).getNombre();
		String valor = ((Property)this.getCurrentRowObject()).getValor();
		String devolucion = "<input type=\"text\" size=\"60\" maxlength=\"500\" id=\"" + nombre + "\" value=\"" + valor + "\"/>";
		return devolucion;
	}

	public String getComentario() {
		String comentario = "";
		if (((Property) this.getCurrentRowObject()).getComentario() == null) {
			comentario = "Comentario Propiedad:Sin comentario";
		} else {
			comentario = "Comentario Propiedad:" + ((Property)this.getCurrentRowObject()).getComentario();
		}
		long id = ((Property)this.getCurrentRowObject()).getId();
		String devolucion = "<img id=\"comentarioPropiedad_" + id + "\" src=\"img/icono_info.gif\"" + 
			" onmouseover=\"dameInfoSinAjax('mostrar', this.id, '" + comentario + "')\" onmouseout=\"dameInfoSinAjax('ocultar', this.id, '')\" class=\"botonesInfo\" />";
		return devolucion;
	}

}