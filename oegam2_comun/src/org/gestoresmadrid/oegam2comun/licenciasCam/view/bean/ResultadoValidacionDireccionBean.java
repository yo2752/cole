package org.gestoresmadrid.oegam2comun.licenciasCam.view.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gestoresmadrid.oegam2comun.licenciasCam.restWS.response.objetos.Numero;
import org.gestoresmadrid.oegam2comun.licenciasCam.restWS.response.objetos.Pais;
import org.gestoresmadrid.oegam2comun.licenciasCam.restWS.response.objetos.Poblacion;
import org.gestoresmadrid.oegam2comun.licenciasCam.restWS.response.objetos.Provincia;
import org.gestoresmadrid.oegam2comun.licenciasCam.restWS.response.objetos.Vial;

public class ResultadoValidacionDireccionBean implements Serializable {

	private static final long serialVersionUID = 8608093068331411433L;

	private Boolean error;

	private String mensaje;

	private Boolean errorPais;

	private List<Pais> paises;

	private Boolean errorProvincia;

	private List<Provincia> provincias;

	private Boolean errorPoblacion;

	private List<Poblacion> poblaciones;

	private Boolean errorVial;

	private List<Vial> viales;

	private Boolean errorNumero;

	private List<Numero> numeros;

	private Boolean errorLocal;

	private Map<String, Object> attachments;

	public ResultadoValidacionDireccionBean(Boolean error) {
		super();
		this.error = error;
	}

	public Boolean getError() {
		return error;
	}

	public void setError(Boolean error) {
		this.error = error;
	}

	public Boolean getErrorPais() {
		return errorPais;
	}

	public void setErrorPais(Boolean errorPais) {
		this.errorPais = errorPais;
	}

	public List<Pais> getPaises() {
		return paises;
	}

	public void setPaises(List<Pais> paises) {
		this.paises = paises;
	}

	public Boolean getErrorProvincia() {
		return errorProvincia;
	}

	public void setErrorProvincia(Boolean errorProvincia) {
		this.errorProvincia = errorProvincia;
	}

	public List<Provincia> getProvincias() {
		return provincias;
	}

	public void setProvincias(List<Provincia> provincias) {
		this.provincias = provincias;
	}

	public Boolean getErrorPoblacion() {
		return errorPoblacion;
	}

	public void setErrorPoblacion(Boolean errorPoblacion) {
		this.errorPoblacion = errorPoblacion;
	}

	public List<Poblacion> getPoblaciones() {
		return poblaciones;
	}

	public void setPoblaciones(List<Poblacion> poblaciones) {
		this.poblaciones = poblaciones;
	}

	public Boolean getErrorVial() {
		return errorVial;
	}

	public void setErrorVial(Boolean errorVial) {
		this.errorVial = errorVial;
	}

	public List<Vial> getViales() {
		return viales;
	}

	public void setViales(List<Vial> viales) {
		this.viales = viales;
	}

	public Boolean getErrorNumero() {
		return errorNumero;
	}

	public void setErrorNumero(Boolean errorNumero) {
		this.errorNumero = errorNumero;
	}

	public List<Numero> getNumeros() {
		return numeros;
	}

	public void setNumeros(List<Numero> numeros) {
		this.numeros = numeros;
	}

	public Boolean getErrorLocal() {
		return errorLocal;
	}

	public void setErrorLocal(Boolean errorLocal) {
		this.errorLocal = errorLocal;
	}

	public Map<String, Object> getAttachments() {
		return attachments;
	}

	public void setAttachments(Map<String, Object> attachments) {
		this.attachments = attachments;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public void addAttachment(String key, Object value) {
		if (null == attachments) {
			attachments = new HashMap<String, Object>();
		}
		attachments.put(key, value);
	}

	public Object getAttachment(String key) {
		if (null == attachments) {
			return null;
		} else {
			return attachments.get(key);
		}
	}
}
