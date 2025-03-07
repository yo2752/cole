/**
 * JustificanteCodigoRetorno.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.gestoresmadrid.justificanteprofesional.ws.blackbox;

public class JustificanteCodigoRetorno  extends org.gestoresmadrid.justificanteprofesional.ws.blackbox.AbstractEntity  implements java.io.Serializable {
    private java.lang.String codigo;

    private java.lang.String descripcion;

    private org.gestoresmadrid.justificanteprofesional.ws.blackbox.JustificantePeticion justificantePeticion;

    public JustificanteCodigoRetorno() {
    }

    public JustificanteCodigoRetorno(
           java.util.Calendar createdOn,
           java.util.Calendar deletedOn,
           java.lang.Integer id,
           java.util.Calendar modifiedOn,
           org.gestoresmadrid.justificanteprofesional.ws.blackbox.UserLabel[] userLabels,
           java.lang.String codigo,
           java.lang.String descripcion,
           org.gestoresmadrid.justificanteprofesional.ws.blackbox.JustificantePeticion justificantePeticion) {
        super(
            createdOn,
            deletedOn,
            id,
            modifiedOn,
            userLabels);
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.justificantePeticion = justificantePeticion;
    }


    /**
     * Gets the codigo value for this JustificanteCodigoRetorno.
     * 
     * @return codigo
     */
    public java.lang.String getCodigo() {
        return codigo;
    }


    /**
     * Sets the codigo value for this JustificanteCodigoRetorno.
     * 
     * @param codigo
     */
    public void setCodigo(java.lang.String codigo) {
        this.codigo = codigo;
    }


    /**
     * Gets the descripcion value for this JustificanteCodigoRetorno.
     * 
     * @return descripcion
     */
    public java.lang.String getDescripcion() {
        return descripcion;
    }


    /**
     * Sets the descripcion value for this JustificanteCodigoRetorno.
     * 
     * @param descripcion
     */
    public void setDescripcion(java.lang.String descripcion) {
        this.descripcion = descripcion;
    }


    /**
     * Gets the justificantePeticion value for this JustificanteCodigoRetorno.
     * 
     * @return justificantePeticion
     */
    public org.gestoresmadrid.justificanteprofesional.ws.blackbox.JustificantePeticion getJustificantePeticion() {
        return justificantePeticion;
    }


    /**
     * Sets the justificantePeticion value for this JustificanteCodigoRetorno.
     * 
     * @param justificantePeticion
     */
    public void setJustificantePeticion(org.gestoresmadrid.justificanteprofesional.ws.blackbox.JustificantePeticion justificantePeticion) {
        this.justificantePeticion = justificantePeticion;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof JustificanteCodigoRetorno)) return false;
        JustificanteCodigoRetorno other = (JustificanteCodigoRetorno) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.codigo==null && other.getCodigo()==null) || 
             (this.codigo!=null &&
              this.codigo.equals(other.getCodigo()))) &&
            ((this.descripcion==null && other.getDescripcion()==null) || 
             (this.descripcion!=null &&
              this.descripcion.equals(other.getDescripcion()))) &&
            ((this.justificantePeticion==null && other.getJustificantePeticion()==null) || 
             (this.justificantePeticion!=null &&
              this.justificantePeticion.equals(other.getJustificantePeticion())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getCodigo() != null) {
            _hashCode += getCodigo().hashCode();
        }
        if (getDescripcion() != null) {
            _hashCode += getDescripcion().hashCode();
        }
        if (getJustificantePeticion() != null) {
            _hashCode += getJustificantePeticion().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(JustificanteCodigoRetorno.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "justificanteCodigoRetorno"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codigo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descripcion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descripcion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("justificantePeticion");
        elemField.setXmlName(new javax.xml.namespace.QName("", "justificantePeticion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "justificantePeticion"));
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
