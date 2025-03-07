/**
 * Usuario.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.dgt.www.nostra.esquemas.consultaDEV.datos_especificos;

public class Usuario  implements java.io.Serializable {
    private java.lang.String idUsuario;

    private java.lang.String datosPersonales;

    private java.lang.String docIdentidad;

    public Usuario() {
    }

    public Usuario(
           java.lang.String idUsuario,
           java.lang.String datosPersonales,
           java.lang.String docIdentidad) {
           this.idUsuario = idUsuario;
           this.datosPersonales = datosPersonales;
           this.docIdentidad = docIdentidad;
    }


    /**
     * Gets the idUsuario value for this Usuario.
     * 
     * @return idUsuario
     */
    public java.lang.String getIdUsuario() {
        return idUsuario;
    }


    /**
     * Sets the idUsuario value for this Usuario.
     * 
     * @param idUsuario
     */
    public void setIdUsuario(java.lang.String idUsuario) {
        this.idUsuario = idUsuario;
    }


    /**
     * Gets the datosPersonales value for this Usuario.
     * 
     * @return datosPersonales
     */
    public java.lang.String getDatosPersonales() {
        return datosPersonales;
    }


    /**
     * Sets the datosPersonales value for this Usuario.
     * 
     * @param datosPersonales
     */
    public void setDatosPersonales(java.lang.String datosPersonales) {
        this.datosPersonales = datosPersonales;
    }


    /**
     * Gets the docIdentidad value for this Usuario.
     * 
     * @return docIdentidad
     */
    public java.lang.String getDocIdentidad() {
        return docIdentidad;
    }


    /**
     * Sets the docIdentidad value for this Usuario.
     * 
     * @param docIdentidad
     */
    public void setDocIdentidad(java.lang.String docIdentidad) {
        this.docIdentidad = docIdentidad;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Usuario)) return false;
        Usuario other = (Usuario) obj;
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
              this.docIdentidad.equals(other.getDocIdentidad())));
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
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Usuario.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.dgt.es/nostra/esquemas/consultaDEV/datos-especificos", ">Usuario"));
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
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("docIdentidad");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.dgt.es/nostra/esquemas/consultaDEV/datos-especificos", "DocIdentidad"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.dgt.es/nostra/esquemas/consultaDEV/datos-especificos", ">DocIdentidad"));
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
