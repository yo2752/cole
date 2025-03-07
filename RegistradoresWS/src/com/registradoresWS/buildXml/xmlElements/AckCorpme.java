package com.registradoresWS.buildXml.xmlElements;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "CORPME-eDoc")
public class AckCorpme {

	String Version;
	String Tipo_Mensaje;
	String Id_Tramite;
	String Tipo_Entidad;
	String Codigo_Entidad;
	String Tipo_Registro;
	String Codigo_Registro;
	String Codigo_Retorno;
	String Descripcion_Retorno;

	public String getVersion() {
		return Version;
	}

	@XmlAttribute(name = "Version")
	public void setVersion(String version) {
		Version = version;
	}

	public String getTipo_Mensaje() {
		return Tipo_Mensaje;
	}

	@XmlAttribute(name = "Tipo_Mensaje")
	public void setTipo_Mensaje(String tipo_Mensaje) {
		Tipo_Mensaje = tipo_Mensaje;
	}

	public String getId_Tramite() {
		return Id_Tramite;
	}

	@XmlAttribute(name = "Id_Tramite")
	public void setId_Tramite(String id_Tramite) {
		Id_Tramite = id_Tramite;
	}

	public String getTipo_Entidad() {
		return Tipo_Entidad;
	}

	@XmlAttribute(name = "Tipo_Entidad")
	public void setTipo_Entidad(String tipo_Entidad) {
		Tipo_Entidad = tipo_Entidad;
	}

	public String getCodigo_Entidad() {
		return Codigo_Entidad;
	}

	@XmlAttribute(name = "Codigo_Entidad")
	public void setCodigo_Entidad(String codigo_Entidad) {
		Codigo_Entidad = codigo_Entidad;
	}

	public String getTipo_Registro() {
		return Tipo_Registro;
	}

	@XmlAttribute(name = "Tipo_Registro")
	public void setTipo_Registro(String tipo_Registro) {
		Tipo_Registro = tipo_Registro;
	}

	public String getCodigo_Registro() {
		return Codigo_Registro;
	}

	@XmlAttribute(name = "Codigo_Registro")
	public void setCodigo_Registro(String codigo_Registro) {
		Codigo_Registro = codigo_Registro;
	}

	public String getCodigo_Retorno() {
		return Codigo_Retorno;
	}

	@XmlAttribute(name = "Codigo_Retorno")
	public void setCodigo_Retorno(String codigo_Retorno) {
		Codigo_Retorno = codigo_Retorno;
	}

	public String getDescripcion_Retorno() {
		return Descripcion_Retorno;
	}

	@XmlAttribute(name = "Descripcion_Retorno")
	public void setDescripcion_Retorno(String descripcion_Retorno) {
		Descripcion_Retorno = descripcion_Retorno;
	}

}