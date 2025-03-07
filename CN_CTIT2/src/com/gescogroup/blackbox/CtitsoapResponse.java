/**
 * CtitsoapResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gescogroup.blackbox;

public class CtitsoapResponse  implements java.io.Serializable {
    private java.lang.String dossierNumber;

    private com.gescogroup.blackbox.CtitCheckError[] errorCodes;

    private java.lang.String result;

    private java.lang.String resultCode;

    public CtitsoapResponse() {
    }

    public CtitsoapResponse(
           java.lang.String dossierNumber,
           com.gescogroup.blackbox.CtitCheckError[] errorCodes,
           java.lang.String result,
           java.lang.String resultCode) {
           this.dossierNumber = dossierNumber;
           this.errorCodes = errorCodes;
           this.result = result;
           this.resultCode = resultCode;
    }


    /**
     * Gets the dossierNumber value for this CtitsoapResponse.
     * 
     * @return dossierNumber
     */
    public java.lang.String getDossierNumber() {
        return dossierNumber;
    }


    /**
     * Sets the dossierNumber value for this CtitsoapResponse.
     * 
     * @param dossierNumber
     */
    public void setDossierNumber(java.lang.String dossierNumber) {
        this.dossierNumber = dossierNumber;
    }


    /**
     * Gets the errorCodes value for this CtitsoapResponse.
     * 
     * @return errorCodes
     */
    public com.gescogroup.blackbox.CtitCheckError[] getErrorCodes() {
        return errorCodes;
    }


    /**
     * Sets the errorCodes value for this CtitsoapResponse.
     * 
     * @param errorCodes
     */
    public void setErrorCodes(com.gescogroup.blackbox.CtitCheckError[] errorCodes) {
        this.errorCodes = errorCodes;
    }

    public com.gescogroup.blackbox.CtitCheckError getErrorCodes(int i) {
        return this.errorCodes[i];
    }

    public void setErrorCodes(int i, com.gescogroup.blackbox.CtitCheckError _value) {
        this.errorCodes[i] = _value;
    }


    /**
     * Gets the result value for this CtitsoapResponse.
     * 
     * @return result
     */
    public java.lang.String getResult() {
        return result;
    }


    /**
     * Sets the result value for this CtitsoapResponse.
     * 
     * @param result
     */
    public void setResult(java.lang.String result) {
        this.result = result;
    }


    /**
     * Gets the resultCode value for this CtitsoapResponse.
     * 
     * @return resultCode
     */
    public java.lang.String getResultCode() {
        return resultCode;
    }


    /**
     * Sets the resultCode value for this CtitsoapResponse.
     * 
     * @param resultCode
     */
    public void setResultCode(java.lang.String resultCode) {
        this.resultCode = resultCode;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CtitsoapResponse)) return false;
        CtitsoapResponse other = (CtitsoapResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.dossierNumber==null && other.getDossierNumber()==null) || 
             (this.dossierNumber!=null &&
              this.dossierNumber.equals(other.getDossierNumber()))) &&
            ((this.errorCodes==null && other.getErrorCodes()==null) || 
             (this.errorCodes!=null &&
              java.util.Arrays.equals(this.errorCodes, other.getErrorCodes()))) &&
            ((this.result==null && other.getResult()==null) || 
             (this.result!=null &&
              this.result.equals(other.getResult()))) &&
            ((this.resultCode==null && other.getResultCode()==null) || 
             (this.resultCode!=null &&
              this.resultCode.equals(other.getResultCode())));
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
        if (getDossierNumber() != null) {
            _hashCode += getDossierNumber().hashCode();
        }
        if (getErrorCodes() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getErrorCodes());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getErrorCodes(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getResult() != null) {
            _hashCode += getResult().hashCode();
        }
        if (getResultCode() != null) {
            _hashCode += getResultCode().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CtitsoapResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "ctitsoapResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dossierNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dossierNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("errorCodes");
        elemField.setXmlName(new javax.xml.namespace.QName("", "errorCodes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "ctitCheckError"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("result");
        elemField.setXmlName(new javax.xml.namespace.QName("", "result"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resultCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "resultCode"));
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
