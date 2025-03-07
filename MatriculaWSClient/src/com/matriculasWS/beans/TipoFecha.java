/**
 * TipoFecha.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.matriculasWS.beans;

public class TipoFecha  implements java.io.Serializable {
    private java.lang.Integer dia;

    private java.lang.Integer mes;

    private java.lang.Integer anio;

    public TipoFecha() {
    }

    public TipoFecha(
           java.lang.Integer dia,
           java.lang.Integer mes,
           java.lang.Integer anio) {
           this.dia = dia;
           this.mes = mes;
           this.anio = anio;
    }


    /**
     * Gets the dia value for this TipoFecha.
     * 
     * @return dia
     */
    public java.lang.Integer getDia() {
        return dia;
    }


    /**
     * Sets the dia value for this TipoFecha.
     * 
     * @param dia
     */
    public void setDia(java.lang.Integer dia) {
        this.dia = dia;
    }


    /**
     * Gets the mes value for this TipoFecha.
     * 
     * @return mes
     */
    public java.lang.Integer getMes() {
        return mes;
    }


    /**
     * Sets the mes value for this TipoFecha.
     * 
     * @param mes
     */
    public void setMes(java.lang.Integer mes) {
        this.mes = mes;
    }


    /**
     * Gets the anio value for this TipoFecha.
     * 
     * @return anio
     */
    public java.lang.Integer getAnio() {
        return anio;
    }


    /**
     * Sets the anio value for this TipoFecha.
     * 
     * @param anio
     */
    public void setAnio(java.lang.Integer anio) {
        this.anio = anio;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TipoFecha)) return false;
        TipoFecha other = (TipoFecha) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.dia==null && other.getDia()==null) || 
             (this.dia!=null &&
              this.dia.equals(other.getDia()))) &&
            ((this.mes==null && other.getMes()==null) || 
             (this.mes!=null &&
              this.mes.equals(other.getMes()))) &&
            ((this.anio==null && other.getAnio()==null) || 
             (this.anio!=null &&
              this.anio.equals(other.getAnio())));
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
        if (getDia() != null) {
            _hashCode += getDia().hashCode();
        }
        if (getMes() != null) {
            _hashCode += getMes().hashCode();
        }
        if (getAnio() != null) {
            _hashCode += getAnio().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TipoFecha.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://beans.matriculasWS.com", "TipoFecha"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dia");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.matriculasWS.com", "dia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mes");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.matriculasWS.com", "mes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("anio");
        elemField.setXmlName(new javax.xml.namespace.QName("http://beans.matriculasWS.com", "anio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
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
