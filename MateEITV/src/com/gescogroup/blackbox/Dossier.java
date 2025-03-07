/**
 * Dossier.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gescogroup.blackbox;

public abstract class Dossier  extends com.gescogroup.blackbox.AbstractProcessEntity  implements java.io.Serializable {
    private com.gescogroup.blackbox.Agency agency;

    private com.gescogroup.blackbox.Agent agent;

    private java.lang.String dgtTaxCode;

    private java.lang.String dossierNumber;

    public Dossier() {
    }

    public Dossier(
           java.util.Calendar createdOn,
           java.util.Calendar deletedOn,
           java.lang.Integer id,
           java.util.Calendar modifiedOn,
           com.gescogroup.blackbox.UserLabel[] userLabels,
           com.gescogroup.blackbox.User createdBy,
           com.gescogroup.blackbox.User modifiedBy,
           com.gescogroup.blackbox.Agency agency,
           com.gescogroup.blackbox.Agent agent,
           java.lang.String dgtTaxCode,
           java.lang.String dossierNumber) {
        super(
            createdOn,
            deletedOn,
            id,
            modifiedOn,
            userLabels,
            createdBy,
            modifiedBy);
        this.agency = agency;
        this.agent = agent;
        this.dgtTaxCode = dgtTaxCode;
        this.dossierNumber = dossierNumber;
    }


    /**
     * Gets the agency value for this Dossier.
     * 
     * @return agency
     */
    public com.gescogroup.blackbox.Agency getAgency() {
        return agency;
    }


    /**
     * Sets the agency value for this Dossier.
     * 
     * @param agency
     */
    public void setAgency(com.gescogroup.blackbox.Agency agency) {
        this.agency = agency;
    }


    /**
     * Gets the agent value for this Dossier.
     * 
     * @return agent
     */
    public com.gescogroup.blackbox.Agent getAgent() {
        return agent;
    }


    /**
     * Sets the agent value for this Dossier.
     * 
     * @param agent
     */
    public void setAgent(com.gescogroup.blackbox.Agent agent) {
        this.agent = agent;
    }


    /**
     * Gets the dgtTaxCode value for this Dossier.
     * 
     * @return dgtTaxCode
     */
    public java.lang.String getDgtTaxCode() {
        return dgtTaxCode;
    }


    /**
     * Sets the dgtTaxCode value for this Dossier.
     * 
     * @param dgtTaxCode
     */
    public void setDgtTaxCode(java.lang.String dgtTaxCode) {
        this.dgtTaxCode = dgtTaxCode;
    }


    /**
     * Gets the dossierNumber value for this Dossier.
     * 
     * @return dossierNumber
     */
    public java.lang.String getDossierNumber() {
        return dossierNumber;
    }


    /**
     * Sets the dossierNumber value for this Dossier.
     * 
     * @param dossierNumber
     */
    public void setDossierNumber(java.lang.String dossierNumber) {
        this.dossierNumber = dossierNumber;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Dossier)) return false;
        Dossier other = (Dossier) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.agency==null && other.getAgency()==null) || 
             (this.agency!=null &&
              this.agency.equals(other.getAgency()))) &&
            ((this.agent==null && other.getAgent()==null) || 
             (this.agent!=null &&
              this.agent.equals(other.getAgent()))) &&
            ((this.dgtTaxCode==null && other.getDgtTaxCode()==null) || 
             (this.dgtTaxCode!=null &&
              this.dgtTaxCode.equals(other.getDgtTaxCode()))) &&
            ((this.dossierNumber==null && other.getDossierNumber()==null) || 
             (this.dossierNumber!=null &&
              this.dossierNumber.equals(other.getDossierNumber())));
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
        if (getAgency() != null) {
            _hashCode += getAgency().hashCode();
        }
        if (getAgent() != null) {
            _hashCode += getAgent().hashCode();
        }
        if (getDgtTaxCode() != null) {
            _hashCode += getDgtTaxCode().hashCode();
        }
        if (getDossierNumber() != null) {
            _hashCode += getDossierNumber().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Dossier.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "dossier"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agency");
        elemField.setXmlName(new javax.xml.namespace.QName("", "agency"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "agency"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agent");
        elemField.setXmlName(new javax.xml.namespace.QName("", "agent"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "agent"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dgtTaxCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dgtTaxCode"));
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
