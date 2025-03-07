package org.gestoresmadrid.oegamComun.acceso.model.bean;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuAplicacionBean implements Serializable{

	private static final long serialVersionUID = -1010429017748050536L;
	
	String codigoAplicacion; 
	String descAplicacion;
	HashMap<Long, List<MenuFuncionBean>> mapaMenuFuncion;
	
	public MenuAplicacionBean() {
		super();
		mapaMenuFuncion = new HashMap<Long, List<MenuFuncionBean>>();
	}
	
	public String getCodigoAplicacion() {
		return codigoAplicacion;
	}
	public void setCodigoAplicacion(String codigoAplicacion) {
		this.codigoAplicacion = codigoAplicacion;
	}
	public String getDescAplicacion() {
		return descAplicacion;
	}
	public void setDescAplicacion(String descAplicacion) {
		this.descAplicacion = descAplicacion;
	}
	public HashMap<Long, List<MenuFuncionBean>> getMapaMenuFuncion() {
		return mapaMenuFuncion;
	}
	public void setMapaMenuFuncion(HashMap<Long, List<MenuFuncionBean>> mapaMenuFuncion) {
		this.mapaMenuFuncion = mapaMenuFuncion;
	}

	public void ordenarListas() {
		if(mapaMenuFuncion != null){
			for(Map.Entry<Long, List<MenuFuncionBean>> entry : mapaMenuFuncion.entrySet()){
				mapaMenuFuncion.put(entry.getKey(), ordenarListaMenu(entry.getValue()));
			}
		}
	}
	
	private List<MenuFuncionBean> ordenarListaMenu(List<MenuFuncionBean> listaMenu) {
		Collections.sort(listaMenu, new Comparator<MenuFuncionBean>() {
			@Override
		    public int compare(MenuFuncionBean o1, MenuFuncionBean o2) {
				if (o1.getOrden() == null) {
				    if (o2.getOrden() == null) {
				        return 0; 
				    } else {
				    	return 1;
				    }
				} else if(o2.getOrden() == null) {
				    return -1;          
				} else {
		        	return o1.getOrden().compareTo(o2.getOrden());
		        }
		    }
		});
		return listaMenu;
	}
	
}
