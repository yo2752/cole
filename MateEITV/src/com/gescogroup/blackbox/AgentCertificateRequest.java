/**
 * AgentCertificateRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gescogroup.blackbox;

public class AgentCertificateRequest  extends com.gescogroup.blackbox.AbstractEntity  implements java.io.Serializable {
    private com.gescogroup.blackbox.AgentCertificate agentCertificate;

    private com.gescogroup.blackbox.AgentCertificateState certificateState;

    private com.gescogroup.blackbox.AgentCertificateRequestState requestState;

    private java.lang.String response;

    public AgentCertificateRequest() {
    }

    public AgentCertificateRequest(
           java.util.Calendar createdOn,
           java.util.Calendar deletedOn,
           java.lang.Integer id,
           java.util.Calendar modifiedOn,
           com.gescogroup.blackbox.UserLabel[] userLabels,
           com.gescogroup.blackbox.AgentCertificate agentCertificate,
           com.gescogroup.blackbox.AgentCertificateState certificateState,
           com.gescogroup.blackbox.AgentCertificateRequestState requestState,
           java.lang.String response) {
        super(
            createdOn,
            deletedOn,
            id,
            modifiedOn,
            userLabels);
        this.agentCertificate = agentCertificate;
        this.certificateState = certificateState;
        this.requestState = requestState;
        this.response = response;
    }


    /**
     * Gets the agentCertificate value for this AgentCertificateRequest.
     * 
     * @return agentCertificate
     */
    public com.gescogroup.blackbox.AgentCertificate getAgentCertificate() {
        return agentCertificate;
    }


    /**
     * Sets the agentCertificate value for this AgentCertificateRequest.
     * 
     * @param agentCertificate
     */
    public void setAgentCertificate(com.gescogroup.blackbox.AgentCertificate agentCertificate) {
        this.agentCertificate = agentCertificate;
    }


    /**
     * Gets the certificateState value for this AgentCertificateRequest.
     * 
     * @return certificateState
     */
    public com.gescogroup.blackbox.AgentCertificateState getCertificateState() {
        return certificateState;
    }


    /**
     * Sets the certificateState value for this AgentCertificateRequest.
     * 
     * @param certificateState
     */
    public void setCertificateState(com.gescogroup.blackbox.AgentCertificateState certificateState) {
        this.certificateState = certificateState;
    }


    /**
     * Gets the requestState value for this AgentCertificateRequest.
     * 
     * @return requestState
     */
    public com.gescogroup.blackbox.AgentCertificateRequestState getRequestState() {
        return requestState;
    }


    /**
     * Sets the requestState value for this AgentCertificateRequest.
     * 
     * @param requestState
     */
    public void setRequestState(com.gescogroup.blackbox.AgentCertificateRequestState requestState) {
        this.requestState = requestState;
    }


    /**
     * Gets the response value for this AgentCertificateRequest.
     * 
     * @return response
     */
    public java.lang.String getResponse() {
        return response;
    }


    /**
     * Sets the response value for this AgentCertificateRequest.
     * 
     * @param response
     */
    public void setResponse(java.lang.String response) {
        this.response = response;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AgentCertificateRequest)) return false;
        AgentCertificateRequest other = (AgentCertificateRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.agentCertificate==null && other.getAgentCertificate()==null) || 
             (this.agentCertificate!=null &&
              this.agentCertificate.equals(other.getAgentCertificate()))) &&
            ((this.certificateState==null && other.getCertificateState()==null) || 
             (this.certificateState!=null &&
              this.certificateState.equals(other.getCertificateState()))) &&
            ((this.requestState==null && other.getRequestState()==null) || 
             (this.requestState!=null &&
              this.requestState.equals(other.getRequestState()))) &&
            ((this.response==null && other.getResponse()==null) || 
             (this.response!=null &&
              this.response.equals(other.getResponse())));
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
        if (getAgentCertificate() != null) {
            _hashCode += getAgentCertificate().hashCode();
        }
        if (getCertificateState() != null) {
            _hashCode += getCertificateState().hashCode();
        }
        if (getRequestState() != null) {
            _hashCode += getRequestState().hashCode();
        }
        if (getResponse() != null) {
            _hashCode += getResponse().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AgentCertificateRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "agentCertificateRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agentCertificate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "agentCertificate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "agentCertificate"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("certificateState");
        elemField.setXmlName(new javax.xml.namespace.QName("", "certificateState"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "agentCertificateState"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("requestState");
        elemField.setXmlName(new javax.xml.namespace.QName("", "requestState"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com", "agentCertificateRequestState"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("response");
        elemField.setXmlName(new javax.xml.namespace.QName("", "response"));
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
