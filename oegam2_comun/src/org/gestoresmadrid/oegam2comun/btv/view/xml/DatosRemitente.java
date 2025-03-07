package org.gestoresmadrid.oegam2comun.btv.view.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Datos_Remitente", propOrder = {
    "nombre",
    "apellidos",
    "documentoIdentificacion",
    "correoElectronico"
})
public class DatosRemitente {
	
	 @XmlElement(name = "Nombre", required = true)
	 protected String nombre;
	 @XmlElement(name = "Apellidos")
	 protected String apellidos;
	 @XmlElement(name = "Documento_Identificacion", required = true)
	 protected DatosRemitente.DocumentoIdentificacion documentoIdentificacion;
	 @XmlElement(name = "Correo_Electronico")
	 protected String correoElectronico;
	 
	 public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public DatosRemitente.DocumentoIdentificacion getDocumentoIdentificacion() {
		return documentoIdentificacion;
	}

	public void setDocumentoIdentificacion(
			DatosRemitente.DocumentoIdentificacion documentoIdentificacion) {
		this.documentoIdentificacion = documentoIdentificacion;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	    @XmlType(name = "", propOrder = {
	        "numero"
	    })
	    public static class DocumentoIdentificacion {

	        @XmlElement(name = "Numero", required = true)
	        protected String numero;

	        public String getNumero() {
	            return numero;
	        }

	        public void setNumero(String value) {
	            this.numero = value;
	        }

	    }
	 
}
