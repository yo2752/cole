package org.gestoresmadrid.oegamBajas.btv.view.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Interesados", propOrder = {
    "interesado"
})
public class DatosInteresado {

	
	@XmlElement(name = "Interesado", required = true)
	protected DatosInteresado.Interesado interesado;
    
	
    public DatosInteresado.Interesado getInteresado() {
		return interesado;
	}

	public void setInteresado(DatosInteresado.Interesado interesado) {
		this.interesado = interesado;
	}

	@XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "Interesado", propOrder = {
		"nombre",
	    "apellidos",
	    "documentoIdentificacion",
	    "correoElectronico"
    })
    public static class Interesado {
    	@XmlElement(name = "Nombre", required = true)
        protected String nombre;
        @XmlElement(name = "Apellidos")
        protected String apellidos;
        @XmlElement(name = "Documento_Identificacion", required = true)
        protected DatosInteresado.Interesado.DocumentoIdentificacion documentoIdentificacion;
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

    	public DatosInteresado.Interesado.DocumentoIdentificacion getDocumentoIdentificacion() {
    		return documentoIdentificacion;
    	}

    	public void setDocumentoIdentificacion(
    			DatosInteresado.Interesado.DocumentoIdentificacion documentoIdentificacion) {
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

}
