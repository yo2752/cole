package trafico.utiles.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.gestoresmadrid.core.model.transformer.BeanResultTransformPaginatedList;

import trafico.utiles.enumerados.TipoTramiteTrafico;

public class ResultTransformerDatosSensiblesNif implements BeanResultTransformPaginatedList {

	private HashMap<String, ResultTransformerDatosSensibles> datosSensibles;

	private ResultTransformerDatosSensiblesNif() {
	}

	public ResultTransformerDatosSensiblesNif(List<ResultTransformerDatosSensibles> resultTransformerDatosSensibles) {
		setDatosSensibles(resultTransformerDatosSensibles);
	}

	@Override
	public Object transformTuple(Object[] tuple, String[] aliases) {
		if (tuple[0] != null) {
			String exp = tuple[0] + "";
			if (datosSensibles.get(exp) == null) {
				ResultTransformerDatosSensibles r = new ResultTransformerDatosSensibles();
				r.setNumExpediente(exp);
				if (tuple[2] != null) {
					r.setTipoTramite(TipoTramiteTrafico.convertir((String) tuple[2]));
				}
				if (tuple[3] != null) {
					r.setNumColegiado((String) tuple[3]);
				}
				datosSensibles.put(exp, r);
			}
			ResultTransformerDatosSensibles r = datosSensibles.get(exp);
			if (tuple[1] != null) {
				r.addNif((String) tuple[1]);
				datosSensibles.put(exp, r);
			}
		}
		return null;
	}

	@Override
	public List transformList(List collection) {
		return collection;
	}

	@Override
	public Vector<String> crearProyecciones() {
		Vector<String> proyecciones = new Vector<>();
		proyecciones.add("numExpediente");
		proyecciones.add("interviniente.id.nif");
		proyecciones.add("tipoTramite");
		proyecciones.add("id.numColegiado");
		return proyecciones;
	}

	public HashMap<String, ResultTransformerDatosSensibles> getDatosSensibles() {
		return datosSensibles;
	}

	public void setDatosSensibles(HashMap<String, ResultTransformerDatosSensibles> datosSensibles) {
		this.datosSensibles = datosSensibles;
	}

	public void setDatosSensibles(List<ResultTransformerDatosSensibles> datos) {
		datosSensibles = new HashMap<>();
		for (int i = 0; i < datos.size(); i++) {
			datosSensibles.put(datos.get(i).getNumExpediente(), datos.get(i));
		}
	}

	public List<ResultTransformerDatosSensibles> recuperarDatosSensibles() {
		List<ResultTransformerDatosSensibles> datos = new ArrayList<>();
		Iterator<ResultTransformerDatosSensibles> d = datosSensibles.values().iterator();
		while (d.hasNext()) {
			datos.add(d.next());
		}
		return datos;
	}

}