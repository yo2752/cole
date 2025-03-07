/**
 * MateCardError.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gescogroup.blackbox;

public class MateCardError  extends com.gescogroup.blackbox.AbstractEntity  implements java.io.Serializable {
    private java.lang.String key;

    private com.gescogroup.blackbox.MateCardRequest mateCardRequest;

    private java.lang.String message;

    public MateCardError() {
    }

    public MateCardError(
           java.util.Calendar createdOn,
           java.util.Calendar deletedOn,
           java.lang.Integer id,
           java.util.Calendar modifiedOn,
           java.lang.String key,
           com.gescogroup.blackbox.MateCardRequest mateCardRequest,
           java.lang.String message) {
        super(
            createdOn,
            deletedOn,
            id,
            modifiedOn);
        this.key = key;
        this.mateCardRequest = mateCardRequest;
        this.message = message;
    }


    /**
     * Gets the key value for this MateCardError.
     * 
     * @return key
     */
    public java.lang.String getKey() {
        return key;
    }


    /**
     * Sets the key value for this MateCardError.
     * 
     * @param key
     */
    public void setKey(java.lang.String key) {
        this.key = key;
    }


    /**
     * Gets the mateCardRequest value for this MateCardError.
     * 
     * @return mateCardRequest
     */
    public com.gescogroup.blackbox.MateCardRequest getMateCardRequest() {
        return mateCardRequest;
    }


    /**
     * Sets the mateCardRequest value for this MateCardError.
     * 
     * @param mateCardRequest
     */
    public void setMateCardRequest(com.gescogroup.blackbox.MateCardRequest mateCardRequest) {
        this.mateCardRequest = mateCardRequest;
    }


    /**
     * Gets the message value for this MateCardError.
     * 
     * @return message
     */
    public java.lang.String getMessage() {
        return message;
    }


    /**
     * Sets the message value for this MateCardError.
     * 
     * @param message
     */
    public void setMessage(java.lang.String message) {
        this.message = message;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MateCardError)) return false;
        MateCardError other = (MateCardError) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.key==null && other.getKey()==null) || 
             (this.key!=null &&
              this.key.equals(other.getKey()))) &&
            ((this.mateCardRequest==null && other.getMateCardRequest()==null) || 
             (this.mateCardRequest!=null &&
              this.mateCardRequest.equals(other.getMateCardRequest()))) &&
            ((this.message==null && other.getMessage()==null) || 
             (this.message!=null &&
              this.message.equals(other.getMessage())));
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
        if (getKey() != null) {
            _hashCode += getKey().hashCode();
        }
        if (getMateCardRequest() != null) {
            _hashCode += getMateCardRequest().hashCode();
        }
        if (getMessage() != null) {
            _hashCode += getMessage().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MateCardError.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "mateCardError"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("key");
        elemField.setXmlName(new javax.xml.namespace.QName("", "key"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mateCardRequest");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mateCardRequest"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "mateCardRequest"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("message");
        elemField.setXmlName(new javax.xml.namespace.QName("", "message"));
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
