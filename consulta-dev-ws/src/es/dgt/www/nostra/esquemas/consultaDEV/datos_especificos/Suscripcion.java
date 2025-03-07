/**
 * Suscripcion.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.dgt.www.nostra.esquemas.consultaDEV.datos_especificos;

public class Suscripcion  implements java.io.Serializable {
    private java.lang.String idUsuario;

    private java.lang.String datosPersonales;

    private java.lang.String docIdentidad;

    private java.lang.String codProcedimiento;

    private java.lang.String desProcedimiento;

    private java.lang.String emisor;

    private es.dgt.www.nostra.esquemas.consultaDEV.datos_especificos.CodEstado codEstado;

    private java.lang.String fechaSuscripcion;

    public Suscripcion() {
    }

    public Suscripcion(
           java.lang.String idUsuario,
           java.lang.String datosPersonales,
           java.lang.String docIdentidad,
           java.lang.String codProcedimiento,
           java.lang.String desProcedimiento,
           java.lang.String emisor,
           es.dgt.www.nostra.esquemas.consultaDEV.datos_especificos.CodEstado codEstado,
           java.lang.String fechaSuscripcion) {
           this.idUsuario = idUsuario;
           this.datosPersonales = datosPersonales;
           this.docIdentidad = docIdentidad;
           this.codProcedimiento = codProcedimiento;
           this.desProcedimiento = desProcedimiento;
           this.emisor = emisor;
           this.codEstado = codEstado;
           this.fechaSuscripcion = fechaSuscripcion;
    }


    /**
     * Gets the idUsuario value for this Suscripcion.
     * 
     * @return idUsuario
     */
    public java.lang.String getIdUsuario() {
        return idUsuario;
    }


    /**
     * Sets the idUsuario value for this Suscripcion.
     * 
     * @param idUsuario
     */
    public void setIdUsuario(java.lang.String idUsuario) {
        this.idUsuario = idUsuario;
    }


    /**
     * Gets the datosPersonales value for this Suscripcion.
     * 
     * @return datosPersonales
     */
    public java.lang.String getDatosPersonales() {
        return datosPersonales;
    }


    /**
     * Sets the datosPersonales value for this Suscripcion.
     * 
     * @param datosPersonales
     */
    public void setDatosPersonales(java.lang.String datosPersonales) {
        this.datosPersonales = datosPersonales;
    }


    /**
     * Gets the docIdentidad value for this Suscripcion.
     * 
     * @return docIdentidad
     */
    public java.lang.String getDocIdentidad() {
        return docIdentidad;
    }


    /**
     * Sets the docIdentidad value for this Suscripcion.
     * 
     * @param docIdentidad
     */
    public void setDocIdentidad(java.lang.String docIdentidad) {
        this.docIdentidad = docIdentidad;
    }


    /**
     * Gets the codProcedimiento value for this Suscripcion.
     * 
     * @return codProcedimiento
     */
    public java.lang.String getCodProcedimiento() {
        return codProcedimiento;
    }


    /**
     * Sets the codProcedimiento value for this Suscripcion.
     * 
     * @param codProcedimiento
     */
    public void setCodProcedimiento(java.lang.String codProcedimiento) {
        this.codProcedimiento = codProcedimiento;
    }


    /**
     * Gets the desProcedimiento value for this Suscripcion.
     * 
     * @return desProcedimiento
     */
    public java.lang.String getDesProcedimiento() {
        return desProcedimiento;
    }


    /**
     * Sets the desProcedimiento value for this Suscripcion.
     * 
     * @param desProcedimiento
     */
    public void setDesProcedimiento(java.lang.String desProcedimiento) {
        this.desProcedimiento = desProcedimiento;
    }


    /**
     * Gets the emisor value for this Suscripcion.
     * 
     * @return emisor
     */
    public java.lang.String getEmisor() {
        return emisor;
    }


    /**
     * Sets the emisor value for this Suscripcion.
     * 
     * @param emisor
     */
    public void setEmisor(java.lang.String emisor) {
        this.emisor = emisor;
    }


    /**
     * Gets the codEstado value for this Suscripcion.
     * 
     * @return codEstado
     */
    public es.dgt.www.nostra.esquemas.consultaDEV.datos_especificos.CodEstado getCodEstado() {
        return codEstado;
    }


    /**
     * Sets the codEstado value for this Suscripcion.
     * 
     * @param codEstado
     */
    public void setCodEstado(es.dgt.www.nostra.esquemas.consultaDEV.datos_especificos.CodEstado codEstado) {
        this.codEstado = codEstado;
    }


    /**
     * Gets the fechaSuscripcion value for this Suscripcion.
     * 
     * @return fechaSuscripcion
     */
    public java.lang.String getFechaSuscripcion() {
        return fechaSuscripcion;
    }


    /**
     * Sets the fechaSuscripcion value for this Suscripcion.
     * 
     * @param fechaSuscripcion
     */
    public void setFechaSuscripcion(java.lang.String fechaSuscripcion) {
        this.fechaSuscripcion = fechaSuscripcion;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Suscripcion)) return false;
        Suscripcion other = (Suscripcion) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.idUsuario==null && other.getIdUsuario()==null) || 
             (this.idUsuario!=null &&
              this.idUsuario.equals(other.getIdUsuario()))) &&
            ((this.datosPersonales==null && other.getDatosPersonales()==null) || 
             (this.datosPersonales!=null &&
              this.datosPersonales.equals(other.getDatosPersonales()))) &&
            ((this.docIdentidad==null && other.getDocIdentidad()==null) || 
             (this.docIdentidad!=null &&
              this.docIdentidad.equals(other.getDocIdentidad()))) &&
            ((this.codProcedimiento==null && other.getCodProcedimiento()==null) || 
             (this.codProcedimiento!=null &&
              this.codProcedimiento.equals(other.getCodProcedimiento()))) &&
            ((this.desProcedimiento==null && other.getDesProcedimiento()==null) || 
             (this.desProcedimiento!=null &&
              this.desProcedimiento.equals(other.getDesProcedimiento()))) &&
            ((this.emisor==null && other.getEmisor()==null) || 
             (this.emisor!=null &&
              this.emisor.equals(other.getEmisor()))) &&
            ((this.codEstado==null && other.getCodEstado()==null) || 
             (this.codEstado!=null &&
              this.codEstado.equals(other.getCodEstado()))) &&
            ((this.fechaSuscripcion==null && other.getFechaSuscripcion()==null) || 
             (this.fechaSuscripcion!=null &&
              this.fechaSuscripcion.equals(other.getFechaSuscripcion())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getIdUsuario() != null) {
            _hashCode += getIdUsuario().hashCode();
        }
        if (getDatosPersonales() != null) {
            _hashCode += getDatosPersonales().hashCode();
        }
        if (getDocIdentidad() != null) {
            _hashCode += getDocIdentidad().hashCode();
        }
        if (getCodProcedimiento() != null) {
            _hashCode += getCodProcedimiento().hashCode();
        }
        if (getDesProcedimiento() != null) {
            _hashCode += getDesProcedimiento().hashCode();
        }
        if (getEmisor() != null) {
            _hashCode += getEmisor().hashCode();
        }
        if (getCodEstado() != null) {
            _hashCode += getCodEstado().hashCode();
        }
        if (getFechaSuscripcion() != null) {
            _hashCode += getFechaSuscripcion().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Suscripcion.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.dgt.es/nostra/esquemas/consultaDEV/datos-especificos", ">Suscripcion"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idUsuario");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.dgt.es/nostra/esquemas/consultaDEV/datos-especificos", "IdUsuario"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.dgt.es/nostra/esquemas/consultaDEV/datos-especificos", ">IdUsuario"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("datosPersonales");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.dgt.es/nostra/esquemas/consultaDEV/datos-especificos", "DatosPersonales"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.dgt.es/nostra/esquemas/consultaDEV/datos-especificos", ">DatosPersonales"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("docIdentidad");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.dgt.es/nostra/esquemas/consultaDEV/datos-especificos", "DocIdentidad"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.dgt.es/nostra/esquemas/consultaDEV/datos-especificos", ">DocIdentidad"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codProcedimiento");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.dgt.es/nostra/esquemas/consultaDEV/datos-especificos", "CodProcedimiento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.dgt.es/nostra/esquemas/consultaDEV/datos-especificos", ">CodProcedimiento"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("desProcedimiento");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.dgt.es/nostra/esquemas/consultaDEV/datos-especificos", "DesProcedimiento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.dgt.es/nostra/esquemas/consultaDEV/datos-especificos", ">DesProcedimiento"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("emisor");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.dgt.es/nostra/esquemas/consultaDEV/datos-especificos", "Emisor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.dgt.es/nostra/esquemas/consultaDEV/datos-especificos", ">Emisor"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codEstado");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.dgt.es/nostra/esquemas/consultaDEV/datos-especificos", "CodEstado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.dgt.es/nostra/esquemas/consultaDEV/datos-especificos", ">CodEstado"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fechaSuscripcion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.dgt.es/nostra/esquemas/consultaDEV/datos-especificos", "FechaSuscripcion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.dgt.es/nostra/esquemas/consultaDEV/datos-especificos", ">FechaSuscripcion"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
