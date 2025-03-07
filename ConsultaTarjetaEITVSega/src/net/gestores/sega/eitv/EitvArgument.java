/**
 * EitvArgument.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.gestores.sega.eitv;

public class EitvArgument  implements java.io.Serializable {
    private java.lang.String agencyFiscalId;

    private java.lang.String agentFiscalId;

    private java.lang.String customDossierNumber;

    private java.lang.String externalSystemFiscalId;

    private java.lang.String nive;

    private java.lang.String vin;

    private java.lang.String xmlB64;

    public EitvArgument() {
    }

    public EitvArgument(
           java.lang.String agencyFiscalId,
           java.lang.String agentFiscalId,
           java.lang.String customDossierNumber,
           java.lang.String externalSystemFiscalId,
           java.lang.String nive,
           java.lang.String vin,
           java.lang.String xmlB64) {
           this.agencyFiscalId = agencyFiscalId;
           this.agentFiscalId = agentFiscalId;
           this.customDossierNumber = customDossierNumber;
           this.externalSystemFiscalId = externalSystemFiscalId;
           this.nive = nive;
           this.vin = vin;
           this.xmlB64 = xmlB64;
    }


    /**
     * Gets the agencyFiscalId value for this EitvArgument.
     * 
     * @return agencyFiscalId
     */
    public java.lang.String getAgencyFiscalId() {
        return agencyFiscalId;
    }


    /**
     * Sets the agencyFiscalId value for this EitvArgument.
     * 
     * @param agencyFiscalId
     */
    public void setAgencyFiscalId(java.lang.String agencyFiscalId) {
        this.agencyFiscalId = agencyFiscalId;
    }


    /**
     * Gets the agentFiscalId value for this EitvArgument.
     * 
     * @return agentFiscalId
     */
    public java.lang.String getAgentFiscalId() {
        return agentFiscalId;
    }


    /**
     * Sets the agentFiscalId value for this EitvArgument.
     * 
     * @param agentFiscalId
     */
    public void setAgentFiscalId(java.lang.String agentFiscalId) {
        this.agentFiscalId = agentFiscalId;
    }


    /**
     * Gets the customDossierNumber value for this EitvArgument.
     * 
     * @return customDossierNumber
     */
    public java.lang.String getCustomDossierNumber() {
        return customDossierNumber;
    }


    /**
     * Sets the customDossierNumber value for this EitvArgument.
     * 
     * @param customDossierNumber
     */
    public void setCustomDossierNumber(java.lang.String customDossierNumber) {
        this.customDossierNumber = customDossierNumber;
    }


    /**
     * Gets the externalSystemFiscalId value for this EitvArgument.
     * 
     * @return externalSystemFiscalId
     */
    public java.lang.String getExternalSystemFiscalId() {
        return externalSystemFiscalId;
    }


    /**
     * Sets the externalSystemFiscalId value for this EitvArgument.
     * 
     * @param externalSystemFiscalId
     */
    public void setExternalSystemFiscalId(java.lang.String externalSystemFiscalId) {
        this.externalSystemFiscalId = externalSystemFiscalId;
    }


    /**
     * Gets the nive value for this EitvArgument.
     * 
     * @return nive
     */
    public java.lang.String getNive() {
        return nive;
    }


    /**
     * Sets the nive value for this EitvArgument.
     * 
     * @param nive
     */
    public void setNive(java.lang.String nive) {
        this.nive = nive;
    }


    /**
     * Gets the vin value for this EitvArgument.
     * 
     * @return vin
     */
    public java.lang.String getVin() {
        return vin;
    }


    /**
     * Sets the vin value for this EitvArgument.
     * 
     * @param vin
     */
    public void setVin(java.lang.String vin) {
        this.vin = vin;
    }


    /**
     * Gets the xmlB64 value for this EitvArgument.
     * 
     * @return xmlB64
     */
    public java.lang.String getXmlB64() {
        return xmlB64;
    }


    /**
     * Sets the xmlB64 value for this EitvArgument.
     * 
     * @param xmlB64
     */
    public void setXmlB64(java.lang.String xmlB64) {
        this.xmlB64 = xmlB64;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof EitvArgument)) return false;
        EitvArgument other = (EitvArgument) obj;
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
            ((this.agentFiscalId==null && other.getAgentFiscalId()==null) || 
             (this.agentFiscalId!=null &&
              this.agentFiscalId.equals(other.getAgentFiscalId()))) &&
            ((this.customDossierNumber==null && other.getCustomDossierNumber()==null) || 
             (this.customDossierNumber!=null &&
              this.customDossierNumber.equals(other.getCustomDossierNumber()))) &&
            ((this.externalSystemFiscalId==null && other.getExternalSystemFiscalId()==null) || 
             (this.externalSystemFiscalId!=null &&
              this.externalSystemFiscalId.equals(other.getExternalSystemFiscalId()))) &&
            ((this.nive==null && other.getNive()==null) || 
             (this.nive!=null &&
              this.nive.equals(other.getNive()))) &&
            ((this.vin==null && other.getVin()==null) || 
             (this.vin!=null &&
              this.vin.equals(other.getVin()))) &&
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
        if (getAgentFiscalId() != null) {
            _hashCode += getAgentFiscalId().hashCode();
        }
        if (getCustomDossierNumber() != null) {
            _hashCode += getCustomDossierNumber().hashCode();
        }
        if (getExternalSystemFiscalId() != null) {
            _hashCode += getExternalSystemFiscalId().hashCode();
        }
        if (getNive() != null) {
            _hashCode += getNive().hashCode();
        }
        if (getVin() != null) {
            _hashCode += getVin().hashCode();
        }
        if (getXmlB64() != null) {
            _hashCode += getXmlB64().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(EitvArgument.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://sega.gestores.net/eitv", "eitvArgument"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agencyFiscalId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "agencyFiscalId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agentFiscalId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "agentFiscalId"));
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
        elemField.setFieldName("nive");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nive"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vin");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vin"));
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
