/**
 * Agent.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gescogroup.blackbox;

public class Agent  extends com.gescogroup.blackbox.User  implements java.io.Serializable {
    private com.gescogroup.blackbox.Agency[] agencies;

    private com.gescogroup.blackbox.AgentCertificate agentCertificate;

    private com.gescogroup.blackbox.BranchOffice branchOffice;

    private com.gescogroup.blackbox.Address fiscalAddress;

    private int memberId;

    public Agent() {
    }

    public Agent(
           java.util.Calendar createdOn,
           java.util.Calendar deletedOn,
           java.lang.Integer id,
           java.util.Calendar modifiedOn,
           com.gescogroup.blackbox.UserLabel[] userLabels,
           java.util.Calendar activationDate,
           com.gescogroup.blackbox.Address address,
           java.util.Calendar creationDate,
           java.lang.String defaultLanguage,
           java.util.Calendar dropOutDate,
           java.lang.String emailAddress,
           java.lang.Boolean enabled,
           java.util.Calendar expirationDate,
           java.lang.String name,
           java.lang.String nif,
           java.lang.String observations,
           java.lang.String role,
           java.lang.String surname,
           java.lang.String surname2,
           java.lang.String telephoneNumber,
           com.gescogroup.blackbox.UserProfile[] userProfiles,
           com.gescogroup.blackbox.UserRPTProfile[] userRptProfiles,
           com.gescogroup.blackbox.Agency[] agencies,
           com.gescogroup.blackbox.AgentCertificate agentCertificate,
           com.gescogroup.blackbox.BranchOffice branchOffice,
           com.gescogroup.blackbox.Address fiscalAddress,
           int memberId) {
        super(
            createdOn,
            deletedOn,
            id,
            modifiedOn,
            userLabels,
            activationDate,
            address,
            creationDate,
            defaultLanguage,
            dropOutDate,
            emailAddress,
            enabled,
            expirationDate,
            name,
            nif,
            observations,
            role,
            surname,
            surname2,
            telephoneNumber,
            userProfiles,
            userRptProfiles);
        this.agencies = agencies;
        this.agentCertificate = agentCertificate;
        this.branchOffice = branchOffice;
        this.fiscalAddress = fiscalAddress;
        this.memberId = memberId;
    }


    /**
     * Gets the agencies value for this Agent.
     * 
     * @return agencies
     */
    public com.gescogroup.blackbox.Agency[] getAgencies() {
        return agencies;
    }


    /**
     * Sets the agencies value for this Agent.
     * 
     * @param agencies
     */
    public void setAgencies(com.gescogroup.blackbox.Agency[] agencies) {
        this.agencies = agencies;
    }

    public com.gescogroup.blackbox.Agency getAgencies(int i) {
        return this.agencies[i];
    }

    public void setAgencies(int i, com.gescogroup.blackbox.Agency _value) {
        this.agencies[i] = _value;
    }


    /**
     * Gets the agentCertificate value for this Agent.
     * 
     * @return agentCertificate
     */
    public com.gescogroup.blackbox.AgentCertificate getAgentCertificate() {
        return agentCertificate;
    }


    /**
     * Sets the agentCertificate value for this Agent.
     * 
     * @param agentCertificate
     */
    public void setAgentCertificate(com.gescogroup.blackbox.AgentCertificate agentCertificate) {
        this.agentCertificate = agentCertificate;
    }


    /**
     * Gets the branchOffice value for this Agent.
     * 
     * @return branchOffice
     */
    public com.gescogroup.blackbox.BranchOffice getBranchOffice() {
        return branchOffice;
    }


    /**
     * Sets the branchOffice value for this Agent.
     * 
     * @param branchOffice
     */
    public void setBranchOffice(com.gescogroup.blackbox.BranchOffice branchOffice) {
        this.branchOffice = branchOffice;
    }


    /**
     * Gets the fiscalAddress value for this Agent.
     * 
     * @return fiscalAddress
     */
    public com.gescogroup.blackbox.Address getFiscalAddress() {
        return fiscalAddress;
    }


    /**
     * Sets the fiscalAddress value for this Agent.
     * 
     * @param fiscalAddress
     */
    public void setFiscalAddress(com.gescogroup.blackbox.Address fiscalAddress) {
        this.fiscalAddress = fiscalAddress;
    }


    /**
     * Gets the memberId value for this Agent.
     * 
     * @return memberId
     */
    public int getMemberId() {
        return memberId;
    }


    /**
     * Sets the memberId value for this Agent.
     * 
     * @param memberId
     */
    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Agent)) return false;
        Agent other = (Agent) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.agencies==null && other.getAgencies()==null) || 
             (this.agencies!=null &&
              java.util.Arrays.equals(this.agencies, other.getAgencies()))) &&
            ((this.agentCertificate==null && other.getAgentCertificate()==null) || 
             (this.agentCertificate!=null &&
              this.agentCertificate.equals(other.getAgentCertificate()))) &&
            ((this.branchOffice==null && other.getBranchOffice()==null) || 
             (this.branchOffice!=null &&
              this.branchOffice.equals(other.getBranchOffice()))) &&
            ((this.fiscalAddress==null && other.getFiscalAddress()==null) || 
             (this.fiscalAddress!=null &&
              this.fiscalAddress.equals(other.getFiscalAddress()))) &&
            this.memberId == other.getMemberId();
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
        if (getAgencies() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAgencies());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAgencies(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getAgentCertificate() != null) {
            _hashCode += getAgentCertificate().hashCode();
        }
        if (getBranchOffice() != null) {
            _hashCode += getBranchOffice().hashCode();
        }
        if (getFiscalAddress() != null) {
            _hashCode += getFiscalAddress().hashCode();
        }
        _hashCode += getMemberId();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Agent.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "agent"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agencies");
        elemField.setXmlName(new javax.xml.namespace.QName("", "agencies"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "agency"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agentCertificate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "agentCertificate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "agentCertificate"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("branchOffice");
        elemField.setXmlName(new javax.xml.namespace.QName("", "branchOffice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "branchOffice"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fiscalAddress");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fiscalAddress"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "address"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("memberId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "memberId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
