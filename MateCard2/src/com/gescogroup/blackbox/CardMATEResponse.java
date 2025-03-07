/**
 * CardMATEResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gescogroup.blackbox;

public class CardMATEResponse  implements java.io.Serializable {
    private java.lang.String codeHG;

    private java.lang.String dossierNumber;

    private com.gescogroup.blackbox.MateCardError[] errorCodes;

    private java.lang.String license;

    private java.lang.String plateNumber;

    public CardMATEResponse() {
    }

    public CardMATEResponse(
           java.lang.String codeHG,
           java.lang.String dossierNumber,
           com.gescogroup.blackbox.MateCardError[] errorCodes,
           java.lang.String license,
           java.lang.String plateNumber) {
           this.codeHG = codeHG;
           this.dossierNumber = dossierNumber;
           this.errorCodes = errorCodes;
           this.license = license;
           this.plateNumber = plateNumber;
    }


    /**
     * Gets the codeHG value for this CardMATEResponse.
     * 
     * @return codeHG
     */
    public java.lang.String getCodeHG() {
        return codeHG;
    }


    /**
     * Sets the codeHG value for this CardMATEResponse.
     * 
     * @param codeHG
     */
    public void setCodeHG(java.lang.String codeHG) {
        this.codeHG = codeHG;
    }


    /**
     * Gets the dossierNumber value for this CardMATEResponse.
     * 
     * @return dossierNumber
     */
    public java.lang.String getDossierNumber() {
        return dossierNumber;
    }


    /**
     * Sets the dossierNumber value for this CardMATEResponse.
     * 
     * @param dossierNumber
     */
    public void setDossierNumber(java.lang.String dossierNumber) {
        this.dossierNumber = dossierNumber;
    }


    /**
     * Gets the errorCodes value for this CardMATEResponse.
     * 
     * @return errorCodes
     */
    public com.gescogroup.blackbox.MateCardError[] getErrorCodes() {
        return errorCodes;
    }


    /**
     * Sets the errorCodes value for this CardMATEResponse.
     * 
     * @param errorCodes
     */
    public void setErrorCodes(com.gescogroup.blackbox.MateCardError[] errorCodes) {
        this.errorCodes = errorCodes;
    }

    public com.gescogroup.blackbox.MateCardError getErrorCodes(int i) {
        return this.errorCodes[i];
    }

    public void setErrorCodes(int i, com.gescogroup.blackbox.MateCardError _value) {
        this.errorCodes[i] = _value;
    }


    /**
     * Gets the license value for this CardMATEResponse.
     * 
     * @return license
     */
    public java.lang.String getLicense() {
        return license;
    }


    /**
     * Sets the license value for this CardMATEResponse.
     * 
     * @param license
     */
    public void setLicense(java.lang.String license) {
        this.license = license;
    }


    /**
     * Gets the plateNumber value for this CardMATEResponse.
     * 
     * @return plateNumber
     */
    public java.lang.String getPlateNumber() {
        return plateNumber;
    }


    /**
     * Sets the plateNumber value for this CardMATEResponse.
     * 
     * @param plateNumber
     */
    public void setPlateNumber(java.lang.String plateNumber) {
        this.plateNumber = plateNumber;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CardMATEResponse)) return false;
        CardMATEResponse other = (CardMATEResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codeHG==null && other.getCodeHG()==null) || 
             (this.codeHG!=null &&
              this.codeHG.equals(other.getCodeHG()))) &&
            ((this.dossierNumber==null && other.getDossierNumber()==null) || 
             (this.dossierNumber!=null &&
              this.dossierNumber.equals(other.getDossierNumber()))) &&
            ((this.errorCodes==null && other.getErrorCodes()==null) || 
             (this.errorCodes!=null &&
              java.util.Arrays.equals(this.errorCodes, other.getErrorCodes()))) &&
            ((this.license==null && other.getLicense()==null) || 
             (this.license!=null &&
              this.license.equals(other.getLicense()))) &&
            ((this.plateNumber==null && other.getPlateNumber()==null) || 
             (this.plateNumber!=null &&
              this.plateNumber.equals(other.getPlateNumber())));
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
        if (getCodeHG() != null) {
            _hashCode += getCodeHG().hashCode();
        }
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
        if (getLicense() != null) {
            _hashCode += getLicense().hashCode();
        }
        if (getPlateNumber() != null) {
            _hashCode += getPlateNumber().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CardMATEResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "cardMATEResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codeHG");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codeHG"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dossierNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dossierNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("errorCodes");
        elemField.setXmlName(new javax.xml.namespace.QName("", "errorCodes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "mateCardError"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("license");
        elemField.setXmlName(new javax.xml.namespace.QName("", "license"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("plateNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "plateNumber"));
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
