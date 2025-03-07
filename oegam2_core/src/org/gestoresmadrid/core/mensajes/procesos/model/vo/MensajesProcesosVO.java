package org.gestoresmadrid.core.mensajes.procesos.model.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MENSAJES_PROCESOS")
public class MensajesProcesosVO implements Serializable{

	private static final long serialVersionUID = 2687970043808737092L;

	@Id
	@Column(name = "CODIGO")
	private String codigo;
		
	@Column(name = "MENSAJE")
	private String mensaje;
	
	@Column(name = "DESCRIPCION")
	private String descripcion;
	
	@Column(name = "RECUPERABLE")
	private String recuperable;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getRecuperable() {
		return recuperable;
	}

	public void setRecuperable(String recuperable) {
		this.recuperable = recuperable;
	}
	
}
