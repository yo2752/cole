package trafico.evolucion.resultTransform;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.transformer.BeanResultTransformPaginatedList;

public class ResultTransformEvoulucionTramiteTrafico implements BeanResultTransformPaginatedList {

	private static final long serialVersionUID = 631193691647207542L;
	private Long numExpediente;
	private Date fechaCambio;
	private String estadoAnterior;
	private String estadoNuevo;
	private String nombreUsuario;
	
	public Long getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(Long numExpediente) {
		this.numExpediente = numExpediente;
	}

	public Date getFechaCambio() {
		return fechaCambio;
	}

	public void setFechaCambio(Date fechaCambio) {
		this.fechaCambio = fechaCambio;
	}

	public String getEstadoAnterior() {
		return estadoAnterior;
	}

	public void setEstadoAnterior(String estadoAnterior) {
		this.estadoAnterior = estadoAnterior;
	}

	public String getEstadoNuevo() {
		return estadoNuevo;
	}

	public void setEstadoNuevo(String estadoNuevo) {
		this.estadoNuevo = estadoNuevo;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	@Override
	public Object transformTuple(Object[] tuple, String[] aliases) {
		ResultTransformEvoulucionTramiteTrafico r = new ResultTransformEvoulucionTramiteTrafico();
		if (tuple[0]!=null){
			r.setNumExpediente((Long)tuple[0]);
		}
		if (tuple[1]!=null){
			r.setFechaCambio((Date)tuple[1]);
		}
		if (tuple[2]!=null){
			r.setEstadoAnterior(EstadoTramiteTrafico.convertirTexto(Long.toString((Long)tuple[2])));
		}
		if (tuple[3]!=null){
			r.setEstadoNuevo(EstadoTramiteTrafico.convertirTexto(Long.toString((Long)tuple[3])));
		}
		if (tuple[4]!=null){
			r.setNombreUsuario((String)tuple[4]);
		}
		return r;
	}

	@Override
	public List transformList(List collection) {
		return collection;
	}

	@Override
	public Vector<String> crearProyecciones() {
		Vector<String> proyecciones= new Vector<String>();
		proyecciones.add("id.numExpediente");
		proyecciones.add("id.fechaCambio");
		proyecciones.add("id.estadoAnterior");
		proyecciones.add("id.estadoNuevo");
		proyecciones.add("usuario.apellidosNombre");
		return proyecciones;
	}

}
