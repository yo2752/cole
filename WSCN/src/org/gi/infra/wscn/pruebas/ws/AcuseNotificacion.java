/**
 * AcuseNotificacion.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.gi.infra.wscn.pruebas.ws;

public class AcuseNotificacion  implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7660222124662605959L;

	private int codigoNotificacion;

    private org.gi.infra.wscn.pruebas.ws.Error error;

    private byte[] XML;

    public AcuseNotificacion() {
    }

    public AcuseNotificacion(
           int codigoNotificacion,
           org.gi.infra.wscn.pruebas.ws.Error error,
           byte[] XML) {
           this.codigoNotificacion = codigoNotificacion;
           this.error = error;
           this.XML = XML;
    }


    /**
     * Gets the codigoNotificacion value for this AcuseNotificacion.
     * 
     * @return codigoNotificacion
     */
    public int getCodigoNotificacion() {
        return codigoNotificacion;
    }


    /**
     * Sets the codigoNotificacion value for this AcuseNotificacion.
     * 
     * @param codigoNotificacion
     */
    public void setCodigoNotificacion(int codigoNotificacion) {
        this.codigoNotificacion = codigoNotificacion;
    }


    /**
     * Gets the error value for this AcuseNotificacion.
     * 
     * @return error
     */
    public org.gi.infra.wscn.pruebas.ws.Error getError() {
        return error;
    }


    /**
     * Sets the error value for this AcuseNotificacion.
     * 
     * @param error
     */
    public void setError(org.gi.infra.wscn.pruebas.ws.Error error) {
        this.error = error;
    }


    /**
     * Gets the XML value for this AcuseNotificacion.
     * 
     * @return XML
     */
    public byte[] getXML() {
        return XML;
    }


    /**
     * Sets the XML value for this AcuseNotificacion.
     * 
     * @param XML
     */
    public void setXML(byte[] XML) {
        this.XML = XML;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AcuseNotificacion)) return false;
        AcuseNotificacion other = (AcuseNotificacion) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.codigoNotificacion == other.getCodigoNotificacion() &&
            ((this.error==null && other.getError()==null) || 
             (this.error!=null &&
              this.error.equals(other.getError()))) &&
            ((this.XML==null && other.getXML()==null) || 
             (this.XML!=null &&
              java.util.Arrays.equals(this.XML, other.getXML())));
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
        _hashCode += getCodigoNotificacion();
        if (getError() != null) {
            _hashCode += getError().hashCode();
        }
        if (getXML() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getXML());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getXML(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AcuseNotificacion.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.pruebas.wscn.infra.gi.org/", "acuseNotificacion"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codigoNotificacion");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.pruebas.wscn.infra.gi.org/", "codigoNotificacion"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("error");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.pruebas.wscn.infra.gi.org/", "error"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.pruebas.wscn.infra.gi.org/", "error"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("XML");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.pruebas.wscn.infra.gi.org/", "XML"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "base64Binary"));
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
