/**
 * Translation.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gescogroup.blackbox;

public class Translation  implements java.io.Serializable {
    private java.lang.String languageId;

    private com.gescogroup.blackbox.MlString mlString;

    private java.lang.Integer mlStringId;

    private java.lang.String value;

    public Translation() {
    }

    public Translation(
           java.lang.String languageId,
           com.gescogroup.blackbox.MlString mlString,
           java.lang.Integer mlStringId,
           java.lang.String value) {
           this.languageId = languageId;
           this.mlString = mlString;
           this.mlStringId = mlStringId;
           this.value = value;
    }


    /**
     * Gets the languageId value for this Translation.
     * 
     * @return languageId
     */
    public java.lang.String getLanguageId() {
        return languageId;
    }


    /**
     * Sets the languageId value for this Translation.
     * 
     * @param languageId
     */
    public void setLanguageId(java.lang.String languageId) {
        this.languageId = languageId;
    }


    /**
     * Gets the mlString value for this Translation.
     * 
     * @return mlString
     */
    public com.gescogroup.blackbox.MlString getMlString() {
        return mlString;
    }


    /**
     * Sets the mlString value for this Translation.
     * 
     * @param mlString
     */
    public void setMlString(com.gescogroup.blackbox.MlString mlString) {
        this.mlString = mlString;
    }


    /**
     * Gets the mlStringId value for this Translation.
     * 
     * @return mlStringId
     */
    public java.lang.Integer getMlStringId() {
        return mlStringId;
    }


    /**
     * Sets the mlStringId value for this Translation.
     * 
     * @param mlStringId
     */
    public void setMlStringId(java.lang.Integer mlStringId) {
        this.mlStringId = mlStringId;
    }


    /**
     * Gets the value value for this Translation.
     * 
     * @return value
     */
    public java.lang.String getValue() {
        return value;
    }


    /**
     * Sets the value value for this Translation.
     * 
     * @param value
     */
    public void setValue(java.lang.String value) {
        this.value = value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Translation)) return false;
        Translation other = (Translation) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.languageId==null && other.getLanguageId()==null) || 
             (this.languageId!=null &&
              this.languageId.equals(other.getLanguageId()))) &&
            ((this.mlString==null && other.getMlString()==null) || 
             (this.mlString!=null &&
              this.mlString.equals(other.getMlString()))) &&
            ((this.mlStringId==null && other.getMlStringId()==null) || 
             (this.mlStringId!=null &&
              this.mlStringId.equals(other.getMlStringId()))) &&
            ((this.value==null && other.getValue()==null) || 
             (this.value!=null &&
              this.value.equals(other.getValue())));
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
        if (getLanguageId() != null) {
            _hashCode += getLanguageId().hashCode();
        }
        if (getMlString() != null) {
            _hashCode += getMlString().hashCode();
        }
        if (getMlStringId() != null) {
            _hashCode += getMlStringId().hashCode();
        }
        if (getValue() != null) {
            _hashCode += getValue().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Translation.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "translation"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("languageId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "languageId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mlString");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mlString"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "mlString"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mlStringId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mlStringId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("value");
        elemField.setXmlName(new javax.xml.namespace.QName("", "value"));
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
