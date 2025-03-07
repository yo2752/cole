package trafico.utiles.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.gestoresmadrid.core.model.transformer.BeanResultTransformPaginatedList;

import trafico.utiles.enumerados.TipoTramiteTrafico;

public class ResultTransformerDatosSensibles implements BeanResultTransformPaginatedList {

	private static final long serialVersionUID = -8016537025989917988L;
	private String numExpediente;
	private String matricula;
	// Mantis 13848. David Sierra
	private String matriculaOrigen;
	private String bastidor;
	private TipoTramiteTrafico tipoTramite;
	private List<String> nifs;
	private String numColegiado;

	public String getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(String numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getMatriculaOrigen() {
		return matriculaOrigen;
	}

	public void setMatriculaOrigen(String matriculaOrigen) {
		this.matriculaOrigen = matriculaOrigen;
	}

	public String getBastidor() {
		return bastidor;
	}

	public void setBastidor(String bastidor) {
		this.bastidor = bastidor;
	}

	public TipoTramiteTrafico getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(TipoTramiteTrafico tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public List<String> getNifs() {
		return nifs;
	}

	public void setNifs(List<String> nifs) {
		this.nifs = nifs;
	}

	public boolean addNif(String nif) {
		if (nifs == null) {
			nifs = new ArrayList<>();
		}
		return nifs.add(nif);
	}

	public String get(int index) {
		if (nifs == null) {
			return null;
		}
		return nifs.get(index);
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	@Override
	public Object transformTuple(Object[] tuple, String[] aliases) {
		ResultTransformerDatosSensibles objeto = new ResultTransformerDatosSensibles();
		if (tuple[0] != null) {
			objeto.setNumExpediente(((Long) tuple[0]) + "");
		}
		if (tuple[1] != null) {
			objeto.setMatricula((String) tuple[1]);
		}
		if (tuple[2] != null) {
			objeto.setMatriculaOrigen((String) tuple[2]);
		}
		if (tuple[3] != null) {
			objeto.setBastidor((String) tuple[3]);
		}
		if (tuple[4] != null) {
			objeto.setTipoTramite(TipoTramiteTrafico.convertir((String) tuple[4]));
		}
		if (tuple[5] != null) {
			objeto.setNumColegiado((String) tuple[5]);
		}
		return objeto;
	}

	@Override
	public List transformList(List collection) {
		return collection;
	}

	@Override
	public Vector<String> crearProyecciones() {
		Vector<String> proyecciones = new Vector<>();
		proyecciones.add("tramiteTrafico.numExpediente");
		proyecciones.add("matricula");
		proyecciones.add("matriculaOrigen");
		proyecciones.add("bastidor");
		proyecciones.add("tramiteTrafico.tipoTramite");
		proyecciones.add("id.numColegiado");
		return proyecciones;
	}

}