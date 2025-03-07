/**
 * CtitCheckRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gescogroup.blackbox;

public class CtitCheckRequest  extends com.gescogroup.blackbox.AbstractProcessEntity  implements java.io.Serializable {
    private com.gescogroup.blackbox.Agency agency;

    private com.gescogroup.blackbox.Agent agent;

    private com.gescogroup.blackbox.BranchOffice branchOffice;

    private com.gescogroup.blackbox.CtitCheck ctitCheck;

    private com.gescogroup.blackbox.CtitCheckError[] ctitCheckErrors;

    private com.gescogroup.blackbox.CtitCheckResponse ctitCheckResponse;

    private com.gescogroup.blackbox.CtitCheckXMLRequest ctitCheckXMLRequest;

    private java.lang.String customDossierNumber;

    private java.lang.String dossierNumber;

    private com.gescogroup.blackbox.ExternalSystem externalSystem;

    private com.gescogroup.blackbox.ProfessionalOrder professionalOrder;

    public CtitCheckRequest() {
    }

    public CtitCheckRequest(
           java.util.Calendar createdOn,
           java.util.Calendar deletedOn,
           java.lang.Integer id,
           java.util.Calendar modifiedOn,
           com.gescogroup.blackbox.UserLabel[] userLabels,
           com.gescogroup.blackbox.User createdBy,
           com.gescogroup.blackbox.User modifiedBy,
           com.gescogroup.blackbox.Agency agency,
           com.gescogroup.blackbox.Agent agent,
           com.gescogroup.blackbox.BranchOffice branchOffice,
           com.gescogroup.blackbox.CtitCheck ctitCheck,
           com.gescogroup.blackbox.CtitCheckError[] ctitCheckErrors,
           com.gescogroup.blackbox.CtitCheckResponse ctitCheckResponse,
           com.gescogroup.blackbox.CtitCheckXMLRequest ctitCheckXMLRequest,
           java.lang.String customDossierNumber,
           java.lang.String dossierNumber,
           com.gescogroup.blackbox.ExternalSystem externalSystem,
           com.gescogroup.blackbox.ProfessionalOrder professionalOrder) {
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
        this.branchOffice = branchOffice;
        this.ctitCheck = ctitCheck;
        this.ctitCheckErrors = ctitCheckErrors;
        this.ctitCheckResponse = ctitCheckResponse;
        this.ctitCheckXMLRequest = ctitCheckXMLRequest;
        this.customDossierNumber = customDossierNumber;
        this.dossierNumber = dossierNumber;
        this.externalSystem = externalSystem;
        this.professionalOrder = professionalOrder;
    }


    /**
     * Gets the agency value for this CtitCheckRequest.
     * 
     * @return agency
     */
    public com.gescogroup.blackbox.Agency getAgency() {
        return agency;
    }


    /**
     * Sets the agency value for this CtitCheckRequest.
     * 
     * @param agency
     */
    public void setAgency(com.gescogroup.blackbox.Agency agency) {
        this.agency = agency;
    }


    /**
     * Gets the agent value for this CtitCheckRequest.
     * 
     * @return agent
     */
    public com.gescogroup.blackbox.Agent getAgent() {
        return agent;
    }


    /**
     * Sets the agent value for this CtitCheckRequest.
     * 
     * @param agent
     */
    public void setAgent(com.gescogroup.blackbox.Agent agent) {
        this.agent = agent;
    }


    /**
     * Gets the branchOffice value for this CtitCheckRequest.
     * 
     * @return branchOffice
     */
    public com.gescogroup.blackbox.BranchOffice getBranchOffice() {
        return branchOffice;
    }


    /**
     * Sets the branchOffice value for this CtitCheckRequest.
     * 
     * @param branchOffice
     */
    public void setBranchOffice(com.gescogroup.blackbox.BranchOffice branchOffice) {
        this.branchOffice = branchOffice;
    }


    /**
     * Gets the ctitCheck value for this CtitCheckRequest.
     * 
     * @return ctitCheck
     */
    public com.gescogroup.blackbox.CtitCheck getCtitCheck() {
        return ctitCheck;
    }


    /**
     * Sets the ctitCheck value for this CtitCheckRequest.
     * 
     * @param ctitCheck
     */
    public void setCtitCheck(com.gescogroup.blackbox.CtitCheck ctitCheck) {
        this.ctitCheck = ctitCheck;
    }


    /**
     * Gets the ctitCheckErrors value for this CtitCheckRequest.
     * 
     * @return ctitCheckErrors
     */
    public com.gescogroup.blackbox.CtitCheckError[] getCtitCheckErrors() {
        return ctitCheckErrors;
    }


    /**
     * Sets the ctitCheckErrors value for this CtitCheckRequest.
     * 
     * @param ctitCheckErrors
     */
    public void setCtitCheckErrors(com.gescogroup.blackbox.CtitCheckError[] ctitCheckErrors) {
        this.ctitCheckErrors = ctitCheckErrors;
    }

    public com.gescogroup.blackbox.CtitCheckError getCtitCheckErrors(int i) {
        return this.ctitCheckErrors[i];
    }

    public void setCtitCheckErrors(int i, com.gescogroup.blackbox.CtitCheckError _value) {
        this.ctitCheckErrors[i] = _value;
    }


    /**
     * Gets the ctitCheckResponse value for this CtitCheckRequest.
     * 
     * @return ctitCheckResponse
     */
    public com.gescogroup.blackbox.CtitCheckResponse getCtitCheckResponse() {
        return ctitCheckResponse;
    }


    /**
     * Sets the ctitCheckResponse value for this CtitCheckRequest.
     * 
     * @param ctitCheckResponse
     */
    public void setCtitCheckResponse(com.gescogroup.blackbox.CtitCheckResponse ctitCheckResponse) {
        this.ctitCheckResponse = ctitCheckResponse;
    }


    /**
     * Gets the ctitCheckXMLRequest value for this CtitCheckRequest.
     * 
     * @return ctitCheckXMLRequest
     */
    public com.gescogroup.blackbox.CtitCheckXMLRequest getCtitCheckXMLRequest() {
        return ctitCheckXMLRequest;
    }


    /**
     * Sets the ctitCheckXMLRequest value for this CtitCheckRequest.
     * 
     * @param ctitCheckXMLRequest
     */
    public void setCtitCheckXMLRequest(com.gescogroup.blackbox.CtitCheckXMLRequest ctitCheckXMLRequest) {
        this.ctitCheckXMLRequest = ctitCheckXMLRequest;
    }


    /**
     * Gets the customDossierNumber value for this CtitCheckRequest.
     * 
     * @return customDossierNumber
     */
    public java.lang.String getCustomDossierNumber() {
        return customDossierNumber;
    }


    /**
     * Sets the customDossierNumber value for this CtitCheckRequest.
     * 
     * @param customDossierNumber
     */
    public void setCustomDossierNumber(java.lang.String customDossierNumber) {
        this.customDossierNumber = customDossierNumber;
    }


    /**
     * Gets the dossierNumber value for this CtitCheckRequest.
     * 
     * @return dossierNumber
     */
    public java.lang.String getDossierNumber() {
        return dossierNumber;
    }


    /**
     * Sets the dossierNumber value for this CtitCheckRequest.
     * 
     * @param dossierNumber
     */
    public void setDossierNumber(java.lang.String dossierNumber) {
        this.dossierNumber = dossierNumber;
    }


    /**
     * Gets the externalSystem value for this CtitCheckRequest.
     * 
     * @return externalSystem
     */
    public com.gescogroup.blackbox.ExternalSystem getExternalSystem() {
        return externalSystem;
    }


    /**
     * Sets the externalSystem value for this CtitCheckRequest.
     * 
     * @param externalSystem
     */
    public void setExternalSystem(com.gescogroup.blackbox.ExternalSystem externalSystem) {
        this.externalSystem = externalSystem;
    }


    /**
     * Gets the professionalOrder value for this CtitCheckRequest.
     * 
     * @return professionalOrder
     */
    public com.gescogroup.blackbox.ProfessionalOrder getProfessionalOrder() {
        return professionalOrder;
    }


    /**
     * Sets the professionalOrder value for this CtitCheckRequest.
     * 
     * @param professionalOrder
     */
    public void setProfessionalOrder(com.gescogroup.blackbox.ProfessionalOrder professionalOrder) {
        this.professionalOrder = professionalOrder;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CtitCheckRequest)) return false;
        CtitCheckRequest other = (CtitCheckRequest) obj;
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
            ((this.branchOffice==null && other.getBranchOffice()==null) || 
             (this.branchOffice!=null &&
              this.branchOffice.equals(other.getBranchOffice()))) &&
            ((this.ctitCheck==null && other.getCtitCheck()==null) || 
             (this.ctitCheck!=null &&
              this.ctitCheck.equals(other.getCtitCheck()))) &&
            ((this.ctitCheckErrors==null && other.getCtitCheckErrors()==null) || 
             (this.ctitCheckErrors!=null &&
              java.util.Arrays.equals(this.ctitCheckErrors, other.getCtitCheckErrors()))) &&
            ((this.ctitCheckResponse==null && other.getCtitCheckResponse()==null) || 
             (this.ctitCheckResponse!=null &&
              this.ctitCheckResponse.equals(other.getCtitCheckResponse()))) &&
            ((this.ctitCheckXMLRequest==null && other.getCtitCheckXMLRequest()==null) || 
             (this.ctitCheckXMLRequest!=null &&
              this.ctitCheckXMLRequest.equals(other.getCtitCheckXMLRequest()))) &&
            ((this.customDossierNumber==null && other.getCustomDossierNumber()==null) || 
             (this.customDossierNumber!=null &&
              this.customDossierNumber.equals(other.getCustomDossierNumber()))) &&
            ((this.dossierNumber==null && other.getDossierNumber()==null) || 
             (this.dossierNumber!=null &&
              this.dossierNumber.equals(other.getDossierNumber()))) &&
            ((this.externalSystem==null && other.getExternalSystem()==null) || 
             (this.externalSystem!=null &&
              this.externalSystem.equals(other.getExternalSystem()))) &&
            ((this.professionalOrder==null && other.getProfessionalOrder()==null) || 
             (this.professionalOrder!=null &&
              this.professionalOrder.equals(other.getProfessionalOrder())));
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
        if (getBranchOffice() != null) {
            _hashCode += getBranchOffice().hashCode();
        }
        if (getCtitCheck() != null) {
            _hashCode += getCtitCheck().hashCode();
        }
        if (getCtitCheckErrors() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCtitCheckErrors());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCtitCheckErrors(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getCtitCheckResponse() != null) {
            _hashCode += getCtitCheckResponse().hashCode();
        }
        if (getCtitCheckXMLRequest() != null) {
            _hashCode += getCtitCheckXMLRequest().hashCode();
        }
        if (getCustomDossierNumber() != null) {
            _hashCode += getCustomDossierNumber().hashCode();
        }
        if (getDossierNumber() != null) {
            _hashCode += getDossierNumber().hashCode();
        }
        if (getExternalSystem() != null) {
            _hashCode += getExternalSystem().hashCode();
        }
        if (getProfessionalOrder() != null) {
            _hashCode += getProfessionalOrder().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CtitCheckRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "ctitCheckRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agency");
        elemField.setXmlName(new javax.xml.namespace.QName("", "agency"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "agency"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agent");
        elemField.setXmlName(new javax.xml.namespace.QName("", "agent"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "agent"));
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
        elemField.setFieldName("ctitCheck");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ctitCheck"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "ctitCheck"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ctitCheckErrors");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ctitCheckErrors"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "ctitCheckError"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ctitCheckResponse");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ctitCheckResponse"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "ctitCheckResponse"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ctitCheckXMLRequest");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ctitCheckXMLRequest"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "ctitCheckXMLRequest"));
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
        elemField.setFieldName("dossierNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dossierNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("externalSystem");
        elemField.setXmlName(new javax.xml.namespace.QName("", "externalSystem"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "externalSystem"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("professionalOrder");
        elemField.setXmlName(new javax.xml.namespace.QName("", "professionalOrder"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://blackbox.gescogroup.com/", "professionalOrder"));
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
