package org.gestoresmadrid.oegamComun.vehiculo.view.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoVO;

public class ResultadoVehiculoBean implements Serializable{

	private static final long serialVersionUID = 6022653528414498930L;
	
	Boolean error;
	String mensaje;
	List<String> listaMensajes;
	DireccionVO direccion;
	VehiculoVO vehiculo;
	Boolean guardarDir;
	
	public void addListaMensaje(String mensaje) {
		if (listaMensajes == null) {
			listaMensajes = new ArrayList<>();
		}
		listaMensajes.add(mensaje);
	}
	
	public void addListaMensajes(List<String> mensajes) {
		if (listaMensajes == null) {
			listaMensajes = new ArrayList<>();
		}
		for(String mensaje : mensajes){
			listaMensajes.add(mensaje);
		}
	}
	
	public Boolean getError() {
		return error;
	}
	public void setError(Boolean error) {
		this.error = error;
	}
	public ResultadoVehiculoBean(Boolean error) {
		super();
		this.error = error;
		this.guardarDir = Boolean.FALSE;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public DireccionVO getDireccion() {
		return direccion;
	}
	public void setDireccion(DireccionVO direccion) {
		this.direccion = direccion;
	}
	public VehiculoVO getVehiculo() {
		return vehiculo;
	}
	public void setVehiculo(VehiculoVO vehiculo) {
		this.vehiculo = vehiculo;
	}

	public List<String> getListaMensajes() {
		return listaMensajes;
	}

	public void setListaMensajes(List<String> listaMensajes) {
		this.listaMensajes = listaMensajes;
	}

	public Boolean getGuardarDir() {
		return guardarDir;
	}

	public void setGuardarDir(Boolean guardarDir) {
		this.guardarDir = guardarDir;
	}
}
