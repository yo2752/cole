/**
 * BsnRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gaa9.bsn;

public class BsnRequest  implements java.io.Serializable {
    private java.lang.Boolean aviso;

    private java.lang.Boolean bloqueado;

    private java.lang.String identificador;

    public BsnRequest() {
    }

    public BsnRequest(
           java.lang.Boolean aviso,
           java.lang.Boolean bloqueado,
           java.lang.String identificador) {
           this.aviso = aviso;
           this.bloqueado = bloqueado;
           this.identificador = identificador;
    }


    /**
     * Gets the aviso value for this BsnRequest.
     * 
     * @return aviso
     */
    public java.lang.Boolean getAviso() {
        return aviso;
    }


    /**
     * Sets the aviso value for this BsnRequest.
     * 
     * @param aviso
     */
    public void setAviso(java.lang.Boolean aviso) {
        this.aviso = aviso;
    }


    /**
     * Gets the bloqueado value for this BsnRequest.
     * 
     * @return bloqueado
     */
    public java.lang.Boolean getBloqueado() {
        return bloqueado;
    }


    /**
     * Sets the bloqueado value for this BsnRequest.
     * 
     * @param bloqueado
     */
    public void setBloqueado(java.lang.Boolean bloqueado) {
        this.bloqueado = bloqueado;
    }


    /**
     * Gets the identificador value for this BsnRequest.
     * 
     * @return identificador
     */
    public java.lang.String getIdentificador() {
        return identificador;
    }


    /**
     * Sets the identificador value for this BsnRequest.
     * 
     * @param identificador
     */
    public void setIdentificador(java.lang.String identificador) {
        this.identificador = identificador;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BsnRequest)) return false;
        BsnRequest other = (BsnRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.aviso==null && other.getAviso()==null) || 
             (this.aviso!=null &&
              this.aviso.equals(other.getAviso()))) &&
            ((this.bloqueado==null && other.getBloqueado()==null) || 
             (this.bloqueado!=null &&
              this.bloqueado.equals(other.getBloqueado()))) &&
            ((this.identificador==null && other.getIdentificador()==null) || 
             (this.identificador!=null &&
              this.identificador.equals(other.getIdentificador())));
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
        if (getAviso() != null) {
            _hashCode += getAviso().hashCode();
        }
        if (getBloqueado() != null) {
            _hashCode += getBloqueado().hashCode();
        }
        if (getIdentificador() != null) {
            _hashCode += getIdentificador().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BsnRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://bsn.gaa9.com/", "bsnRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("aviso");
        elemField.setXmlName(new javax.xml.namespace.QName("", "aviso"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bloqueado");
        elemField.setXmlName(new javax.xml.namespace.QName("", "bloqueado"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("identificador");
        elemField.setXmlName(new javax.xml.namespace.QName("", "identificador"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
