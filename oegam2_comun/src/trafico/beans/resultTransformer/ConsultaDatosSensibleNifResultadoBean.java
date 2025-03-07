package trafico.beans.resultTransformer;

import java.util.List;
import java.util.Vector;

import org.hibernate.transform.ResultTransformer;

public class ConsultaDatosSensibleNifResultadoBean implements ResultTransformer {

	private static final long serialVersionUID = 1L;
	private String nif;
	private String numExpediente;

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(String numExpediente) {
		this.numExpediente = numExpediente;
	}

	@Override
	public List transformList(List arg0) {
		//No es necesario hacer nada con la lista
		return arg0;
	}

	@Override
	public Object transformTuple(Object[] valores, String[] arg1) {
		ConsultaDatosSensibleNifResultadoBean c = new ConsultaDatosSensibleNifResultadoBean();
		c.setNif((String)valores[0]);
		c.setNumExpediente((String)valores[1]);
		return c;
	}

	/**
	 * Este método genera las proyecciones que se van a realizar en la query, y además, otorga el orden de las mismas.
	 * En el método transformTuple se debe utilizar el mismo orden que se indica aquí.
	 * @return
	 */
	public Vector<String> crearProyecciones() {
		Vector<String> proyecciones = new Vector<>();
		proyecciones.add("nif");
		proyecciones.add("numExpediente");
		return proyecciones;
	}
}