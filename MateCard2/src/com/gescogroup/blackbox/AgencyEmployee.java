/**
 * AgencyEmployee.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gescogroup.blackbox;

public class AgencyEmployee  extends com.gescogroup.blackbox.User  implements java.io.Serializable {
    private com.gescogroup.blackbox.Agency agency;

    private com.gescogroup.blackbox.Agent agent;

    public AgencyEmployee() {
    }

    public AgencyEmployee(
           java.util.Calendar createdOn,
           java.util.Calendar deletedOn,
           java.lang.Integer id,
           java.util.Calendar modifiedOn,
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
           com.gescogroup.blackbox.Profile[] profiles,
           java.lang.String role,
           java.lang.String surname,
           java.lang.String surname2,
           java.lang.String telephoneNumber,
           com.gescogroup.blackbox.Agency agency,
           com.gescogroup.blackbox.Agent agent) {
        super(
            createdOn,
            deletedOn,
            id,
            modifiedOn,
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
            profiles,
            role,
            surname,
            surname2,
            telephoneNumber);
        this.agency = agency;
        this.agent = agent;
    }


    /**
     * Gets the agency value for this AgencyEmployee.
     * 
     * @return agency
     */
    public com.gescogroup.blackbox.Agency getAgency() {
        return agency;
    }


    /**
     * Sets the agency value for this AgencyEmployee.
     * 
     * @param agency
     */
    public void setAgency(com.gescogroup.blackbox.Agency agency) {
        this.agency = agency;
    }


    /**
     * Gets the agent value for this AgencyEmployee.
     * 
     * @return agent
     */
    public com.gescogroup.blackbox.Agent getAgent() {
        return agent;
    }


    /**
     * Sets the agent value for this AgencyEmployee.
     * 
     * @param agent
     */
    public void setAgent(com.gescogroup.blackbox.Agent agent) {
        this.agent = agent;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AgencyEmployee)) return false;
        AgencyEmployee other = (AgencyEmployee) obj;
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
              this.agent.equals(other.getAgent())));
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
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AgencyEmployee.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "agencyEmployee"));
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
