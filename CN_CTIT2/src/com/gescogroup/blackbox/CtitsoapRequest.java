/**
 * CtitsoapRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gescogroup.blackbox;

public class CtitsoapRequest  implements java.io.Serializable {
    private java.lang.String agencyFiscalId;

    private java.lang.String customDossierNumber;

    private java.lang.String externalSystemFiscalId;

    private java.lang.String xmlB64;

    public CtitsoapRequest() {
    }

    public CtitsoapRequest(
           java.lang.String agencyFiscalId,
           java.lang.String customDossierNumber,
           java.lang.String externalSystemFiscalId,
           java.lang.String xmlB64) {
           this.agencyFiscalId = agencyFiscalId;
           this.customDossierNumber = customDossierNumber;
           this.externalSystemFiscalId = externalSystemFiscalId;
           this.xmlB64 = xmlB64;
    }


    /**
     * Gets the agencyFiscalId value for this CtitsoapRequest.
     * 
     * @return agencyFiscalId
     */
    public java.lang.String getAgencyFiscalId() {
        return agencyFiscalId;
    }


    /**
     * Sets the agencyFiscalId value for this CtitsoapRequest.
     * 
     * @param agencyFiscalId
     */
    public void setAgencyFiscalId(java.lang.String agencyFiscalId) {
        this.agencyFiscalId = agencyFiscalId;
    }


    /**
     * Gets the customDossierNumber value for this CtitsoapRequest.
     * 
     * @return customDossierNumber
     */
    public java.lang.String getCustomDossierNumber() {
        return customDossierNumber;
    }


    /**
     * Sets the customDossierNumber value for this CtitsoapRequest.
     * 
     * @param customDossierNumber
     */
    public void setCustomDossierNumber(java.lang.String customDossierNumber) {
        this.customDossierNumber = customDossierNumber;
    }


    /**
     * Gets the externalSystemFiscalId value for this CtitsoapRequest.
     * 
     * @return externalSystemFiscalId
     */
    public java.lang.String getExternalSystemFiscalId() {
        return externalSystemFiscalId;
    }


    /**
     * Sets the externalSystemFiscalId value for this CtitsoapRequest.
     * 
     * @param externalSystemFiscalId
     */
    public void setExternalSystemFiscalId(java.lang.String externalSystemFiscalId) {
        this.externalSystemFiscalId = externalSystemFiscalId;
    }


    /**
     * Gets the xmlB64 value for this CtitsoapRequest.
     * 
     * @return xmlB64
     */
    public java.lang.String getXmlB64() {
        return xmlB64;
    }


    /**
     * Sets the xmlB64 value for this CtitsoapRequest.
     * 
     * @param xmlB64
     */
    public void setXmlB64(java.lang.String xmlB64) {
        this.xmlB64 = xmlB64;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CtitsoapRequest)) return false;
        CtitsoapRequest other = (CtitsoapRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.agencyFiscalId==null && other.getAgencyFiscalId()==null) || 
             (this.agencyFiscalId!=null &&
              this.agencyFiscalId.equals(other.getAgencyFiscalId()))) &&
            ((this.customDossierNumber==null && other.getCustomDossierNumber()==null) || 
             (this.customDossierNumber!=null &&
              this.customDossierNumber.equals(other.getCustomDossierNumber()))) &&
            ((this.externalSystemFiscalId==null && other.getExternalSystemFiscalId()==null) || 
             (this.externalSystemFiscalId!=null &&
              this.externalSystemFiscalId.equals(other.getExternalSystemFiscalId()))) &&
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
        if (getAgencyFiscalId() != null) {
            _hashCode += getAgencyFiscalId().hashCode();
        }
        if (getCustomDossierNumber() != null) {
            _hashCode += getCustomDossierNumber().hashCode();
        }
        if (getExternalSystemFiscalId() != null) {
            _hashCode += getExternalSystemFiscalId().hashCode();
        }
        if (getXmlB64() != null) {
            _hashCode += getXmlB64().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CtitsoapRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "ctitsoapRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agencyFiscalId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "agencyFiscalId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customDossierNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "customDossierNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("externalSystemFiscalId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "externalSystemFiscalId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("xmlB64");
        elemField.setXmlName(new javax.xml.namespace.QName("", "xmlB64"));
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
