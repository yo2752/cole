package org.gestoresmadrid.oegam2comun.licenciasCam.view.dto;

import java.io.File;
import java.io.Serializable;

import org.gestoresmadrid.core.registradores.model.enumerados.TiposFormato;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class FicheroInfo implements Serializable {

	private static final long serialVersionUID = 6727947484583615440L;

	private String nombre;

	private String path;

	private String extension;

	private String tipo;

	private File fichero;

	private TiposFormato formato;

	private String tipoDocumento;

	@Autowired
	private Utiles utiles;

	public FicheroInfo(File param, int pos) {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		this.setFichero(param);
		this.setNombre(param.getName());
		this.setPath(param.getPath());
		this.extension = utiles.dameExtension(this.nombre, false);
		this.setFormato((TiposFormato.convertirExtension(extension)));
	}

	public FicheroInfo() {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public File getFichero() {
		return fichero;
	}

	public void setFichero(File fichero) {
		this.fichero = fichero;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public TiposFormato getFormato() {
		return formato;
	}

	public void setFormato(TiposFormato formato) {
		this.formato = formato;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
}
