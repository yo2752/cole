package general.acciones;

import java.util.ArrayList;

/**
 * Bean utilizado para cargar scripts externos para secciones específicas, y que no vayan incrustados directamente en la plantilla.
 * @author Santiago Cuenca
 *
 */
public class AddScriptsBean {
	
	/* Inicio declaración de atributos */
	private ArrayList<ScriptFeaturesBean> scripts;
	
	
	/* Getters y setters */
	public ArrayList<ScriptFeaturesBean> getScripts() {
		return scripts;
	}
	public void setScripts(ArrayList<ScriptFeaturesBean> scripts) {
		this.scripts = scripts;
	}	

}
