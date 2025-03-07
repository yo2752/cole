package trafico.beans.resultTransformer;

import hibernate.entities.trafico.TramiteTrafico;

import java.util.List;
import java.util.Vector;

import org.hibernate.transform.ResultTransformer;

public class ConsultaDatosSensibleMatriculacionResultadoBean implements ResultTransformer {

	private static final long serialVersionUID = 1L;
	private String matricula;
	private TramiteTrafico tramiteTrafico;

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	
	public TramiteTrafico getTramiteTrafico() {
		return tramiteTrafico;
	}

	public void setTramiteTrafico(TramiteTrafico tramiteTrafico) {
		this.tramiteTrafico = tramiteTrafico;
	}

	@Override
	public List transformList(List arg0) {
		//No es necesario hacer nada con la lista
		return arg0;
	}

	@Override
	public Object transformTuple(Object[] valores, String[] arg1) {
		ConsultaDatosSensibleMatriculacionResultadoBean c = new ConsultaDatosSensibleMatriculacionResultadoBean();
		c.setMatricula((String)valores[0]);
		TramiteTrafico t = new TramiteTrafico();
		t.setNumExpediente((Long)valores[1]);
		c.setTramiteTrafico(t);
		return c;
	}

	/**
	 * Este método genera las proyecciones que se van a realizar en la query, y además, otorga el orden de las mismas.
	 * En el método transformTuple se debe utilizar el mismo orden que se indica aquí.
	 * @return
	 */
	public Vector<String> crearProyecciones() {
		Vector<String> proyecciones = new Vector<>();
		proyecciones.add("matricula");
		proyecciones.add("t.numExpediente");
		return proyecciones;
	}
}