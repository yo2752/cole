package trafico.beans;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.Map.Entry;

public class ImportacionDGTBeanGeneral{
	

	private LinkedHashMap<Long, TramitesCampo> campos;
	private int TIPO_TRAMITE = 1;
	
	public ImportacionDGTBeanGeneral(){
		init();
	}
	
	public void init(){
		if (this.campos == null){
			this.campos = new LinkedHashMap<Long, TramitesCampo>();
		}else{
			this.campos.clear();
		}	
	}
	
	public void addCampo(long id_tramite_campo, String nb_casilla, String valor){
		this.campos.put(id_tramite_campo, new TramitesCampo(nb_casilla, valor));
	}
	
	public TramitesCampo getCampo (long id_tramite_campo) {
		return this.campos.get(id_tramite_campo);
	}
	
	public String getValor(long id_tramite_campo){
		TramitesCampo campo = this.campos.get(id_tramite_campo);
		if (null != campo){
			return campo.getValor();
		}
		return null;
	}
	
	public String getTipoTramite() {
		return getValor(this.TIPO_TRAMITE);
	}
	
	
	
	/**
	 * Devuelve un Iterator de objetos de tipo {@link TramitesCampo} en el mismo orden en que
	 * han sido añadidos a este objeto.
	 * @return
	 */
	public Iterator<Entry<Long, TramitesCampo>> getCampos(){
		
		Iterator<Entry<Long, TramitesCampo>> it = null;
		if (this.campos != null){
			Set<Entry<Long, TramitesCampo>> camposSet = this.campos.entrySet();
			if (camposSet != null){
				it = camposSet.iterator();
			}
		}
		return it;
	}
}