/**
 * BtvsoapPeticion.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gescogroup.blackbox;

public class BtvsoapPeticion  implements java.io.Serializable {
    private java.lang.String doi_gestor;

    private java.lang.String doi_gestoria;

    private java.lang.String doi_plataforma;

    private java.lang.String num_expediente_propio;

    private java.lang.String xmlB64;

    public BtvsoapPeticion() {
    }

    public BtvsoapPeticion(
           java.lang.String doi_gestor,
           java.lang.String doi_gestoria,
           java.lang.String doi_plataforma,
           java.lang.String num_expediente_propio,
           java.lang.String xmlB64) {
           this.doi_gestor = doi_gestor;
           this.doi_gestoria = doi_gestoria;
           this.doi_plataforma = doi_plataforma;
           this.num_expediente_propio = num_expediente_propio;
           this.xmlB64 = xmlB64;
    }


    /**
     * Gets the doi_gestor value for this BtvsoapPeticion.
     * 
     * @return doi_gestor
     */
    public java.lang.String getDoi_gestor() {
        return doi_gestor;
    }


    /**
     * Sets the doi_gestor value for this BtvsoapPeticion.
     * 
     * @param doi_gestor
     */
    public void setDoi_gestor(java.lang.String doi_gestor) {
        this.doi_gestor = doi_gestor;
    }


    /**
     * Gets the doi_gestoria value for this BtvsoapPeticion.
     * 
     * @return doi_gestoria
     */
    public java.lang.String getDoi_gestoria() {
        return doi_gestoria;
    }


    /**
     * Sets the doi_gestoria value for this BtvsoapPeticion.
     * 
     * @param doi_gestoria
     */
    public void setDoi_gestoria(java.lang.String doi_gestoria) {
        this.doi_gestoria = doi_gestoria;
    }


    /**
     * Gets the doi_plataforma value for this BtvsoapPeticion.
     * 
     * @return doi_plataforma
     */
    public java.lang.String getDoi_plataforma() {
        return doi_plataforma;
    }


    /**
     * Sets the doi_plataforma value for this BtvsoapPeticion.
     * 
     * @param doi_plataforma
     */
    public void setDoi_plataforma(java.lang.String doi_plataforma) {
        this.doi_plataforma = doi_plataforma;
    }


    /**
     * Gets the num_expediente_propio value for this BtvsoapPeticion.
     * 
     * @return num_expediente_propio
     */
    public java.lang.String getNum_expediente_propio() {
        return num_expediente_propio;
    }


    /**
     * Sets the num_expediente_propio value for this BtvsoapPeticion.
     * 
     * @param num_expediente_propio
     */
    public void setNum_expediente_propio(java.lang.String num_expediente_propio) {
        this.num_expediente_propio = num_expediente_propio;
    }


    /**
     * Gets the xmlB64 value for this BtvsoapPeticion.
     * 
     * @return xmlB64
     */
    public java.lang.String getXmlB64() {
        return xmlB64;
    }


    /**
     * Sets the xmlB64 value for this BtvsoapPeticion.
     * 
     * @param xmlB64
     */
    public void setXmlB64(java.lang.String xmlB64) {
        this.xmlB64 = xmlB64;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BtvsoapPeticion)) return false;
        BtvsoapPeticion other = (BtvsoapPeticion) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.doi_gestor==null && other.getDoi_gestor()==null) || 
             (this.doi_gestor!=null &&
              this.doi_gestor.equals(other.getDoi_gestor()))) &&
            ((this.doi_gestoria==null && other.getDoi_gestoria()==null) || 
             (this.doi_gestoria!=null &&
              this.doi_gestoria.equals(other.getDoi_gestoria()))) &&
            ((this.doi_plataforma==null && other.getDoi_plataforma()==null) || 
             (this.doi_plataforma!=null &&
              this.doi_plataforma.equals(other.getDoi_plataforma()))) &&
            ((this.num_expediente_propio==null && other.getNum_expediente_propio()==null) || 
             (this.num_expediente_propio!=null &&
              this.num_expediente_propio.equals(other.getNum_expediente_propio()))) &&
            ((this.xmlB64==null && other.getXmlB64()==null) || 
             (this.xmlB64!=null &&
              this.xmlB64.equals(other.getXmlB64())));
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
        if (getDoi_gestor() != null) {
            _hashCode += getDoi_gestor().hashCode();
        }
        if (getDoi_gestoria() != null) {
            _hashCode += getDoi_gestoria().hashCode();
        }
        if (getDoi_plataforma() != null) {
            _hashCode += getDoi_plataforma().hashCode();
        }
        if (getNum_expediente_propio() != null) {
            _hashCode += getNum_expediente_propio().hashCode();
        }
        if (getXmlB64() != null) {
            _hashCode += getXmlB64().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BtvsoapPeticion.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "btvsoapPeticion"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("doi_gestor");
        elemField.setXmlName(new javax.xml.namespace.QName("", "doi_gestor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("doi_gestoria");
        elemField.setXmlName(new javax.xml.namespace.QName("", "doi_gestoria"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("doi_plataforma");
        elemField.setXmlName(new javax.xml.namespace.QName("", "doi_plataforma"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("num_expediente_propio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "num_expediente_propio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("xmlB64");
        elemField.setXmlName(new javax.xml.namespace.QName("", "xmlB64"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
